package servermagic.cosmetics;

import java.util.Optional;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import servermagic.ServerMagic;

public class CosmeticItemHelper {
    private static final String COSMETIC_APPLIED_KEY = "servermagic-cosmetic-applied";
    private static final String COSMETIC_ORIGINAL_MODEL_KEY = "servermagic-cosmetic-original-model";

    public static boolean hasCosmeticApplied(ItemStack item) {
        if (item.isEmpty()) return false;
        CustomData data = item.get(DataComponents.CUSTOM_DATA);
        if (data == null) return false;
        CompoundTag tag = data.copyTag();
        Optional<Boolean> b = tag.getBoolean(COSMETIC_APPLIED_KEY);
        return b.isPresent() && b.get();
    }

    public static void applyCosmetic(ItemStack item, String newModel) {
        if (item.isEmpty()) return;
        if (hasCosmeticApplied(item)) return;

        // Store the original model before overwriting
        Identifier currentModel = item.get(DataComponents.ITEM_MODEL);
        String originalModel = currentModel != null ? currentModel.toString() : "";

        // Merge cosmetic flags into existing custom data
        CustomData existing = item.get(DataComponents.CUSTOM_DATA);
        CompoundTag tag = existing != null ? existing.copyTag() : new CompoundTag();
        tag.putBoolean(COSMETIC_APPLIED_KEY, true);
        tag.putString(COSMETIC_ORIGINAL_MODEL_KEY, originalModel);
        item.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

        // Set the new model
        try {
            item.set(DataComponents.ITEM_MODEL, Identifier.parse(newModel));
        } catch (Exception e) {
            ServerMagic.LOGGER.error("Failed to parse cosmetic model identifier: " + newModel, e);
        }
    }

    public static void revertCosmetic(ItemStack item) {
        if (item.isEmpty()) return;
        CustomData data = item.get(DataComponents.CUSTOM_DATA);
        if (data == null) return;
        CompoundTag tag = data.copyTag();
        Optional<Boolean> b = tag.getBoolean(COSMETIC_APPLIED_KEY);
        if (b.isEmpty() || !b.get()) return;

        String originalModel = tag.getStringOr(COSMETIC_ORIGINAL_MODEL_KEY, "");

        // Restore original model
        if (originalModel.isEmpty()) {
            item.remove(DataComponents.ITEM_MODEL);
        } else {
            try {
                item.set(DataComponents.ITEM_MODEL, Identifier.parse(originalModel));
            } catch (Exception e) {
                ServerMagic.LOGGER.error("Failed to parse original model identifier: " + originalModel, e);
                item.remove(DataComponents.ITEM_MODEL);
            }
        }

        // Remove cosmetic flags from custom data
        tag.remove(COSMETIC_APPLIED_KEY);
        tag.remove(COSMETIC_ORIGINAL_MODEL_KEY);
        if (tag.isEmpty()) {
            item.remove(DataComponents.CUSTOM_DATA);
        } else {
            item.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }
    }
}
