package servermagic.spells;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import servermagic.db.Database;
import servermagic.spells.utils.SummonedArmor;
import servermagic.spells.utils.SummonedArmor.ArmorDescriptor;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class AngelWings extends BaseSpell {

    public AngelWings(ServerLevel world, ServerPlayer player, Database db) {
        super(world, player, db);
    }

    @Override
    protected void spellImplementation() {
        ItemStack equipped = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equipped == null) {
            return;
        }

        ArmorDescriptor ad = SummonedArmor.GetInfo(equipped);
        boolean shouldSummon = equipped.isEmpty() || !"angelwings".equals(ad.armorType());

        if (shouldSummon) {
            // TODO we need to apply some custom model to this armor
            ItemStack tempChestplate = SummonedArmor.BuildSummonedItem(player, equipped,
                    new ItemStack(Items.ELYTRA), "angelwings");
            if (tempChestplate == null) {
                return;
            }
            tempChestplate.setDamageValue(tempChestplate.getMaxDamage() - 100);
            player.setItemSlot(EquipmentSlot.CHEST, tempChestplate);
        } else {
            ItemStack original = SummonedArmor.DecodeOriginalFromSummonedItem(player, equipped);
            if (original == null) {
                player.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
            } else {
                player.setItemSlot(EquipmentSlot.CHEST, original);
            }
        }
    }

    @Override
    public String displayName() {
        return "Conjure: Angel Wings";
    }

    @Override
    public String description() {
        return "Replace your current armor with angel wings. 'He can fly!'";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.ANGEL_WINGS);
    }
}
