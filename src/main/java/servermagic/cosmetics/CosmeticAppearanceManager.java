package servermagic.cosmetics;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;
import servermagic.data.items.CustomItem;
import servermagic.data.items.SpellbookItem;
import servermagic.db.Database;
import servermagic.db.tables.CosmeticConfig;

public class CosmeticAppearanceManager {
    private static final Map<UUID, Map<CosmeticSlot, String>> selectedCache = new ConcurrentHashMap<>();
    private static final Map<UUID, Map<EquipmentSlot, ItemStack>> trackedItems = new ConcurrentHashMap<>();
    // Armor items persist cosmetic asset_id to disk; this holds the vanilla default per-slot so we can revert
    private static final Map<UUID, Map<EquipmentSlot, String>> originalAssetIds = new ConcurrentHashMap<>();

    private static int tickAccumulator = 0;
    private static final int TICK_INTERVAL = 5;

    public static void tick(MinecraftServer server) {
        tickAccumulator = (tickAccumulator + 1) % TICK_INTERVAL;
        if (tickAccumulator != 0) return;

        for (ServerLevel level : server.getAllLevels()) {
            for (ServerPlayer player : level.players()) {
                tickPlayer(player);
            }
        }
    }

    private static void tickPlayer(ServerPlayer player) {
        UUID uuid = player.getUUID();
        Map<EquipmentSlot, ItemStack> tracked = trackedItems.computeIfAbsent(uuid, k -> new EnumMap<>(EquipmentSlot.class));
        Map<CosmeticSlot, String> selected = selectedCache.get(uuid);
        if (selected == null) return;

        for (CosmeticSlot cosmeticSlot : CosmeticSlot.values()) {
            for (EquipmentSlot mcSlot : cosmeticSlot.getEquipmentSlots()) {
                ItemStack current = player.getItemBySlot(mcSlot);
                ItemStack previous = tracked.get(mcSlot);

                if (previous != null && previous != current) {
                    tracked.remove(mcSlot);
                    originalAssetIds.get(uuid).remove(mcSlot);
                    previous = null;
                }

                if (previous == null && !current.isEmpty() && isValidForSlot(current, cosmeticSlot)) {
                    String styleId = selected.get(cosmeticSlot);

                    if (cosmeticSlot.isModelBased()) {
                        String model = styleId != null
                                ? Cosmetics.GetById(styleId).map(Cosmetic::getItemModel).orElse(cosmeticSlot.getDefaultModel().orElse(null))
                                : cosmeticSlot.getDefaultModel().orElse(null);
                        CosmeticItemHelper.setModel(current, model);
                    } else {
                        String vanillaAssetId = CosmeticItemHelper.getEquippableAssetId(new ItemStack(current.getItem()));
                        originalAssetIds.get(uuid).putIfAbsent(mcSlot, vanillaAssetId);
                        applyArmorCosmetic(current, styleId, vanillaAssetId);
                    }

                    tracked.put(mcSlot, current);
                }
            }
        }
    }

    public static void loadPlayerCosmetics(ServerPlayer player, Database db) {
        UUID uuid = player.getUUID();
        String username = player.getName().getString();

        trackedItems.put(uuid, new EnumMap<>(EquipmentSlot.class));
        originalAssetIds.put(uuid, new EnumMap<>(EquipmentSlot.class));

        Map<CosmeticSlot, String> cache = new HashMap<>();
        Optional<List<CosmeticConfig>> configs = CosmeticConfig.GetConfigsForPlayer(db, username);
        if (configs.isPresent()) {
            for (CosmeticConfig cc : configs.get()) {
                CosmeticSlot.fromId(cc.slot).ifPresent(slot -> cache.put(slot, cc.style));
            }
        }
        selectedCache.put(uuid, cache);
    }

    public static void revertAndClearPlayer(ServerPlayer player) {
        UUID uuid = player.getUUID();
        trackedItems.remove(uuid);
        selectedCache.remove(uuid);
        originalAssetIds.remove(uuid);
    }

    public static void updateSelectedCosmetic(UUID playerUuid, CosmeticSlot slot, String styleId) {
        Map<CosmeticSlot, String> cache = selectedCache.computeIfAbsent(playerUuid, k -> new HashMap<>());
        if (styleId == null) {
            cache.remove(slot);
        } else {
            cache.put(slot, styleId);
        }

        Map<EquipmentSlot, ItemStack> tracked = trackedItems.get(playerUuid);
        if (tracked != null) {
            if (slot.isModelBased()) {
                String model = styleId != null
                        ? Cosmetics.GetById(styleId).map(Cosmetic::getItemModel).orElse(slot.getDefaultModel().orElse(null))
                        : slot.getDefaultModel().orElse(null);
                for (EquipmentSlot mcSlot : slot.getEquipmentSlots()) {
                    ItemStack item = tracked.get(mcSlot);
                    if (item != null) CosmeticItemHelper.setModel(item, model);
                }
            } else {
                Map<EquipmentSlot, String> origMap = originalAssetIds.get(playerUuid);
                for (EquipmentSlot mcSlot : slot.getEquipmentSlots()) {
                    ItemStack item = tracked.get(mcSlot);
                    if (item == null) continue;
                    applyArmorCosmetic(item, styleId, origMap != null ? origMap.get(mcSlot) : null);
                }
            }
        }
    }

    private static void applyArmorCosmetic(ItemStack item, @Nullable String styleId, @Nullable String vanillaAssetId) {
        if (styleId != null) {
            String assetId = Cosmetics.GetById(styleId).map(Cosmetic::getItemModel).orElse(null);
            if (assetId != null) CosmeticItemHelper.setEquippableAssetId(item, assetId);
        } else {
            CosmeticItemHelper.setEquippableAssetId(item, vanillaAssetId);
        }
    }

    private static boolean isValidForSlot(ItemStack item, CosmeticSlot cosmeticSlot) {
        if (item.isEmpty()) return false;
        return switch (cosmeticSlot) {
            case HELMET -> true;
            case CHESTPLATE -> !item.is(Items.ELYTRA);
            case LEGGINGS -> true;
            case BOOTS -> true;
            case SPELLBOOK -> isSpellbook(item);
        };
    }

    private static boolean isSpellbook(ItemStack item) {
        if (item.isEmpty()) return false;
        CustomData data = item.get(DataComponents.CUSTOM_DATA);
        if (data == null) return false;
        CompoundTag tag = data.copyTag();
        return SpellbookItem.ID.equals(tag.getStringOr(CustomItem.CUSTOM_DATA_ITEM_ID_KEY, ""));
    }

    public static Optional<String> getSelectedStyle(UUID playerUuid, CosmeticSlot slot) {
        Map<CosmeticSlot, String> cache = selectedCache.get(playerUuid);
        if (cache == null) return Optional.empty();
        return Optional.ofNullable(cache.get(slot));
    }

    public static Optional<UUID> findOnlinePlayerUuid(MinecraftServer server, String username) {
        ServerPlayer player = server.getPlayerList().getPlayerByName(username);
        if (player == null) return Optional.empty();
        return Optional.of(player.getUUID());
    }
}
