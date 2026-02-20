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
    public static ItemStack BuildSummonedItem(ServerPlayer player, ItemStack originalItem,
            ItemStack newItem, String itemType) {
        // Add Curse of Binding so player cant remove it
        HolderLookup.Provider registryAccess = player.level().registryAccess();
        newItem.enchant(
                registryAccess.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BINDING_CURSE),
                1);

        CompoundTag customTag = new CompoundTag();
        customTag.putBoolean("servermagic-temporary-item", true);
        customTag.putString("servermagic-temporary-item-type", itemType);

        ArmorDescriptor ad = GetInfo(originalItem);
        if (ad.IsTempArmor) {
            boolean differentType = ad.armorType != null && !ad.armorType.equals(itemType);
            if (differentType) {
                // this means we already have a different temporary piece equipped of some kind,
                // so we need to decode the original out of it to store in the NEW temp piece
                ItemStack maybeOriginal = DecodeOriginalFromSummonedItem(player, originalItem);
                if (maybeOriginal != null) {
                    originalItem = maybeOriginal;
                } else {
                    // The user never had an original chestplate, just temporary items
                    originalItem = ItemStack.EMPTY;
                }
            } else {
                // we have the same type of armor already equipped
                // OR we can't determine the type.
                // So we can't safely encode this original
                return null;
            }
        }

        // Store the original chestplate
        if (originalItem != null && !originalItem.isEmpty()) {
            try {
                DataResult<Tag> item = ItemStack.CODEC
                        .encodeStart(registryAccess.createSerializationContext(NbtOps.INSTANCE), originalItem);
                Tag storedData = item.getOrThrow();
                customTag.put("servermagic-temporary-item-original", storedData);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        newItem.set(DataComponents.CUSTOM_DATA, CustomData.of(customTag));
        return newItem;
    }

    public static ItemStack DecodeOriginalFromSummonedItem(ServerPlayer player, ItemStack equipped) {
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

    public static ArmorDescriptor GetInfo(ItemStack item) {
        CustomData customData = item.get(DataComponents.CUSTOM_DATA);
        if (customData == null) {
            return new ArmorDescriptor(false, null);
        }

        CompoundTag tag = customData.copyTag();
        Optional<Boolean> val = tag.getBoolean("servermagic-temporary-item");
        if (val.isEmpty()) {
            return new ArmorDescriptor(false, null);
        }

        if (val.get().booleanValue() == true) {
            Optional<String> valType = tag.getString("servermagic-temporary-item-type");
            if (valType.isEmpty()) {
                return new ArmorDescriptor(true, null);
            }
            return new ArmorDescriptor(true, valType.get());
        }
        return new ArmorDescriptor(false, null);
    }

    public record ArmorDescriptor(boolean IsTempArmor, String armorType) {
    }
}
