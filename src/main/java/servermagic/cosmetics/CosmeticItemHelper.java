package servermagic.cosmetics;

import javax.annotation.Nullable;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import servermagic.ServerMagic;

public class CosmeticItemHelper {

    public static void setModel(ItemStack item, @Nullable String model) {
        if (item.isEmpty()) return;
        if (model == null) {
            item.remove(DataComponents.ITEM_MODEL);
        } else {
            try {
                item.set(DataComponents.ITEM_MODEL, Identifier.parse(model));
            } catch (Exception e) {
                ServerMagic.LOGGER.error("Failed to parse cosmetic model identifier: " + model, e);
            }
        }
    }

    @Nullable
    public static String getEquippableAssetId(ItemStack item) {
        if (item.isEmpty()) return null;
        Equippable equippable = item.get(DataComponents.EQUIPPABLE);
        if (equippable == null) return null;
        return equippable.assetId().map(key -> key.identifier().toString()).orElse(null);
    }

    public static void setEquippableAssetId(ItemStack item, @Nullable String assetId) {
        if (item.isEmpty()) return;
        Equippable existing = item.get(DataComponents.EQUIPPABLE);
        if (existing == null) return;
        try {
            Equippable.Builder builder = Equippable.builder(existing.slot())
                    .setEquipSound(existing.equipSound())
                    .setDispensable(existing.dispensable())
                    .setSwappable(existing.swappable())
                    .setDamageOnHurt(existing.damageOnHurt())
                    .setEquipOnInteract(existing.equipOnInteract())
                    .setCanBeSheared(existing.canBeSheared())
                    .setShearingSound(existing.shearingSound());
            existing.allowedEntities().ifPresent(builder::setAllowedEntities);
            existing.cameraOverlay().ifPresent(builder::setCameraOverlay);
            if (assetId != null) {
                ResourceKey<EquipmentAsset> key = ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.parse(assetId));
                builder.setAsset(key);
            }
            item.set(DataComponents.EQUIPPABLE, builder.build());
        } catch (Exception e) {
            ServerMagic.LOGGER.error("Failed to set equippable asset_id: " + assetId, e);
        }
    }
}
