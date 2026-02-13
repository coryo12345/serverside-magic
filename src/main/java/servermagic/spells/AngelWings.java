package servermagic.spells;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import servermagic.spells.utils.SummonedArmor;

public class AngelWings extends BaseSpell {

    public AngelWings(ServerLevel world, ServerPlayer player) {
        super(world, player);
    }

    @Override
    protected void spellImplementation() {
        ItemStack equipped = player.getItemBySlot(EquipmentSlot.CHEST);
        if (equipped == null) {
            return;
        }

        boolean shouldSummon = equipped.isEmpty() || !SummonedArmor.IsTempArmor(equipped);

        if (shouldSummon) {
            // TODO we need to apply some custom model to this armor
            ItemStack tempChestplate = SummonedArmor.ConvertOriginalToSummonedItem(player, equipped, new ItemStack(Items.ELYTRA));
            player.setItemSlot(EquipmentSlot.CHEST, tempChestplate);
        } else {
            ItemStack original = SummonedArmor.RevertSummonedItemToOriginal(player, equipped);
            if (original == null) {
                player.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
            } else {
                player.setItemSlot(EquipmentSlot.CHEST, original);
            }
        }
    }

    // TODO it would be good to abstract this into some shared utility somehow
    // BUT we'll need to add a property to the custom data for the temporary item
    // type
    // if I cast a different conjure spell that creates a temporary chestplate,
    // then i need to know that I am wearing a temporary chestplate, but it is a
    // different "custom item"
    // otherwise, using a different spell would just un-summon the current one.
    // THEN, i'll need to transfer over the original data to the new chest

    @Override
    public String displayName() {
        return "Conjure: Angel Wings";
    }

    @Override
    public String description() {
        return "Replace your current armor with angel wings. 'He can fly!'";
    }

}
