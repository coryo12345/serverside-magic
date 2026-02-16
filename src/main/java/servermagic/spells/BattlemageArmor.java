package servermagic.spells;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import servermagic.db.Database;
import servermagic.spells.utils.SummonedArmor;
import servermagic.spells.utils.SummonedArmor.ArmorDescriptor;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class BattlemageArmor extends BaseSpell {
    private final String TEMP_ITEM_TYPE = "battlemage";

    private final Map<EquipmentSlot, Item> baseTempItems;

    public BattlemageArmor(ServerLevel world, ServerPlayer player, Database db) {
        super(world, player, db);
        this.baseTempItems = Map.of(
                EquipmentSlot.HEAD, Items.CHAINMAIL_HELMET,
                EquipmentSlot.CHEST, Items.CHAINMAIL_CHESTPLATE,
                EquipmentSlot.LEGS, Items.CHAINMAIL_LEGGINGS,
                EquipmentSlot.FEET, Items.CHAINMAIL_BOOTS);
    }

    @Override
    protected void spellImplementation() {
        List<EquipmentSlot> equipment = List.of(
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET);

        Boolean anyExistingTempArmor = false;
        for (EquipmentSlot slot : equipment) {
            ItemStack equipped = player.getItemBySlot(slot);
            ArmorDescriptor ad = SummonedArmor.GetInfo(equipped);
            if (TEMP_ITEM_TYPE.equals(ad.armorType())) {
                anyExistingTempArmor = true;
            }
        }

        for (EquipmentSlot slot : equipment) {
            if (anyExistingTempArmor) {
                // if we have at least one piece of this set - we need to revert any temp pieces
                ItemStack equipped = player.getItemBySlot(slot);
                ArmorDescriptor ad = SummonedArmor.GetInfo(equipped);
                if (ad.IsTempArmor()) {
                    ItemStack original = SummonedArmor.DecodeOriginalFromSummonedItem(player, equipped);
                    if (original == null) {
                        player.setItemSlot(slot, ItemStack.EMPTY);
                    } else {
                        player.setItemSlot(slot, original);
                    }
                }
            } else {
                // lets summon!
                ItemStack equipped = player.getItemBySlot(slot);
                // TODO we need to apply some custom model to this armor
                // TODO we need to handle the case of this armor piece breaking...
                // do we try t odo a mixin and revert the piece if it breaks?
                // or do we make it unbreakable?
                // TODO we need to revert temp armpr pieces on death
                ItemStack tempPiece = SummonedArmor.BuildSummonedItem(
                        player,
                        equipped,
                        new ItemStack(this.baseTempItems.get(slot)),
                        TEMP_ITEM_TYPE);
                if (tempPiece == null) {
                    continue;
                }
                player.setItemSlot(slot, tempPiece);
            }
        }

    }

    @Override
    public String displayName() {
        return "Battlemage Armor";
    }

    @Override
    public String description() {
        return "Summon bound armor, in classic battlemage fashion";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.BATTLEMAGE_ARMOR);
    }
}
