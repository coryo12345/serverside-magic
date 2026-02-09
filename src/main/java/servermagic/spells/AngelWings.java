package servermagic.spells;

import java.util.Optional;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantments;

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

        boolean shouldSummon = equipped.isEmpty() || !this.isTempArmor(equipped);

        if (shouldSummon) {
            ItemStack tempChestplate = this.summonTempArmor(equipped);
            player.setItemSlot(EquipmentSlot.CHEST, tempChestplate);
        } else {
            ItemStack original = this.removeTempArmor(equipped);
            if (original == null) {
                player.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
            } else {
                player.setItemSlot(EquipmentSlot.CHEST, original);
            }
        }
    }

    // TODO it would be good to abstract this into some shared utility somehow
    // BUT we'll need to add a property to the custom data for the temporary item type
    // if I cast a different conjure spell that creates a temporary chestplate, 
    // then i need to know that I am wearing a temporary chestplate, but it is a different "custom item"
    // otherwise, using a different spell would just un-summon the current one.
    // THEN, i'll need to transfer over the original data to the new chest
    private boolean isTempArmor(ItemStack item) {
        CustomData customData = item.get(DataComponents.CUSTOM_DATA);
        if (customData == null) {
            return false;
        }

        CompoundTag tag = customData.copyTag();
        Optional<Boolean> val = tag.getBoolean("servermagic-temporary-item");
        if (val.isEmpty()) {
            return false;
        }
        return val.get().booleanValue() == true;
    }

    private ItemStack summonTempArmor(ItemStack originalItem) {
        // Create a new temporary chestplate
        ItemStack tempChestplate = new ItemStack(Items.ELYTRA);

        // Add Curse of Binding so player cant remove it
        HolderLookup.Provider registryAccess = player.level().registryAccess();
        tempChestplate.enchant(
                registryAccess.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BINDING_CURSE),
                1);

        CompoundTag customTag = new CompoundTag();
        customTag.putBoolean("servermagic-temporary-item", true);

        // Store the original chestplate
        if (originalItem != null && !originalItem.isEmpty()) {
            try {
                DataResult<Tag> item = ItemStack.CODEC
                        .encodeStart(registryAccess.createSerializationContext(NbtOps.INSTANCE), originalItem);
                Tag storedData = item.getOrThrow();
                customTag.put("servermagic-temporary-item-original", storedData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tempChestplate.set(DataComponents.CUSTOM_DATA, CustomData.of(customTag));
        return tempChestplate;
    }

    private ItemStack removeTempArmor(ItemStack equipped) {
        ItemStack currentChestplate = player.getItemBySlot(EquipmentSlot.CHEST);

        // Check if it's a temporary chestplate
        CustomData customData = currentChestplate.get(DataComponents.CUSTOM_DATA);
        if (customData == null) {
            return null;
        }

        // Get the stored original chestplate
        CompoundTag tag = customData.copyTag();
        Optional<CompoundTag> storedData = tag.getCompound("servermagic-temporary-item-original");
        if (storedData.isEmpty()) {
            return null;
        }

        HolderLookup.Provider registryAccess = player.level().registryAccess();
        registryAccess.lookupOrThrow(Registries.ENCHANTMENT);

        try {
            DataResult<Pair<ItemStack, Tag>> result = ItemStack.CODEC
                    .decode(registryAccess.createSerializationContext(NbtOps.INSTANCE), storedData.get());
            Pair<ItemStack, Tag> pair = result.getOrThrow();
            ItemStack item = pair.getFirst();
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

}
