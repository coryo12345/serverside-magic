package servermagic.spells.utils;

import java.util.Optional;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantments;

public class SummonedArmor {
    public static boolean IsTempArmor(ItemStack item) {
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

    public static ItemStack ConvertOriginalToSummonedItem(ServerPlayer player, ItemStack originalItem,
            ItemStack newItem) {
        // Add Curse of Binding so player cant remove it
        HolderLookup.Provider registryAccess = player.level().registryAccess();
        newItem.enchant(
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

        newItem.set(DataComponents.CUSTOM_DATA, CustomData.of(customTag));
        return newItem;
    }

    public static ItemStack RevertSummonedItemToOriginal(ServerPlayer player, ItemStack equipped) {
        // Check if it's a temporary chestplate
        CustomData customData = equipped.get(DataComponents.CUSTOM_DATA);
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
}
