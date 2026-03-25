package servermagic.spells.utils;

import java.util.Optional;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class BoundItems {
    private static final String BOUND_ITEM_KEY = "servermagic-bound-item";
    private static final String BOUND_ITEM_ORIGINAL_KEY = "servermagic-bound-item-original";

    public static boolean IsItemBound(ItemStack item) {
        CustomData data = item.get(DataComponents.CUSTOM_DATA);
        if (data == null) {
            return false;
        }
        CompoundTag tag = data.copyTag();
        Optional<Boolean> b = tag.getBoolean(BOUND_ITEM_KEY);
        if (b.isEmpty()) {
            return false;
        }
        return b.get();
    }

    public static ItemStack BuildBoundItem(ServerPlayer player, ItemStack originalItem, ItemStack newItem) {
        CompoundTag customTag = new CompoundTag();
        customTag.putBoolean(BOUND_ITEM_KEY, true);

        // If we are replacing an existing bound item, decode the original out of it first
        if (IsItemBound(originalItem)) {
            ItemStack actualOriginal = DecodeOriginalFromBoundItem(player, originalItem);
            if (actualOriginal != null) {
                originalItem = actualOriginal;
            } else {
                originalItem = ItemStack.EMPTY;
            }
        }

        if (originalItem != null && !originalItem.isEmpty()) {
            HolderLookup.Provider registryAccess = player.level().registryAccess();
            try {
                DataResult<Tag> item = ItemStack.CODEC
                        .encodeStart(registryAccess.createSerializationContext(NbtOps.INSTANCE), originalItem);
                Tag storedData = item.getOrThrow();
                customTag.put(BOUND_ITEM_ORIGINAL_KEY, storedData);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        newItem.set(DataComponents.CUSTOM_DATA, CustomData.of(customTag));
        return newItem;
    }

    public static ItemStack DecodeOriginalFromBoundItem(ServerPlayer player, ItemStack boundItem) {
        CustomData customData = boundItem.get(DataComponents.CUSTOM_DATA);
        if (customData == null) {
            return null;
        }

        CompoundTag tag = customData.copyTag();
        Optional<CompoundTag> storedData = tag.getCompound(BOUND_ITEM_ORIGINAL_KEY);
        if (storedData.isEmpty()) {
            return null;
        }

        HolderLookup.Provider registryAccess = player.level().registryAccess();

        try {
            DataResult<Pair<ItemStack, Tag>> result = ItemStack.CODEC
                    .decode(registryAccess.createSerializationContext(NbtOps.INSTANCE), storedData.get());
            Pair<ItemStack, Tag> pair = result.getOrThrow();
            return pair.getFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
