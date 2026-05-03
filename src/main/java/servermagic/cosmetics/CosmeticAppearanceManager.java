package servermagic.cosmetics;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
    // Cache: player UUID → CosmeticSlot → selected cosmetic id (null = none)
    private static final Map<UUID, Map<CosmeticSlot, String>> selectedCache = new ConcurrentHashMap<>();

    // Tracks which ItemStack objects are currently in each slot per player
    private static final Map<UUID, Map<EquipmentSlot, ItemStack>> trackedItems = new ConcurrentHashMap<>();

    // Saves original equippable asset_id before any cosmetic overrides it (armor slots only)
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
                ItemStack tracked_ = tracked.get(mcSlot);

                // Item left the slot — stop tracking, clear saved original asset_id
                if (tracked_ != null && tracked_ != current) {
                    tracked.remove(mcSlot);
                    Map<EquipmentSlot, String> origMap = originalAssetIds.get(uuid);
                    if (origMap != null) origMap.remove(mcSlot);
                    tracked_ = null;
                }

                // New item entered the slot — apply this player's cosmetic (or default)
                if (tracked_ == null && !current.isEmpty() && isValidForSlot(current, cosmeticSlot)) {
                    String styleId = selected.get(cosmeticSlot);

                    if (cosmeticSlot == CosmeticSlot.SPELLBOOK) {
                        String modelToApply;
                        if (styleId != null) {
                            Optional<Cosmetic> cosmetic = Cosmetics.GetById(styleId);
                            modelToApply = cosmetic.map(Cosmetic::getItemModel).orElse(cosmeticSlot.getDefaultModel().orElse(null));
                        } else {
                            modelToApply = cosmeticSlot.getDefaultModel().orElse(null);
                        }
                        CosmeticItemHelper.setModel(current, modelToApply);
                    } else {
                        // Armor slot: save vanilla asset_id from a fresh item of this type (the
                        // equipped item may already have a cosmetic applied from a prior session)
                        Map<EquipmentSlot, String> origMap = originalAssetIds.computeIfAbsent(uuid, k -> new EnumMap<>(EquipmentSlot.class));
                        String vanillaAssetId = CosmeticItemHelper.getEquippableAssetId(new ItemStack(current.getItem()));
                        origMap.putIfAbsent(mcSlot, vanillaAssetId);

                        if (styleId != null) {
                            String assetId = Cosmetics.GetById(styleId).map(Cosmetic::getItemModel).orElse(null);
                            if (assetId != null) CosmeticItemHelper.setEquippableAssetId(current, assetId);
                        } else {
                            // No cosmetic selected — actively restore vanilla in case item was previously styled
                            CosmeticItemHelper.setEquippableAssetId(current, vanillaAssetId);
                        }
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

        // Immediately update appearance on any currently tracked item for this slot
        Map<EquipmentSlot, ItemStack> tracked = trackedItems.get(playerUuid);
        if (tracked != null) {
            if (slot == CosmeticSlot.SPELLBOOK) {
                String modelToApply;
                if (styleId != null) {
                    Optional<Cosmetic> cosmetic = Cosmetics.GetById(styleId);
                    modelToApply = cosmetic.map(Cosmetic::getItemModel).orElse(slot.getDefaultModel().orElse(null));
                } else {
                    modelToApply = slot.getDefaultModel().orElse(null);
                }
                for (EquipmentSlot mcSlot : slot.getEquipmentSlots()) {
                    ItemStack item = tracked.get(mcSlot);
                    if (item != null) CosmeticItemHelper.setModel(item, modelToApply);
                }
            } else {
                // Armor slot: modify equippable asset_id, never touch ITEM_MODEL
                Map<EquipmentSlot, String> origMap = originalAssetIds.get(playerUuid);
                for (EquipmentSlot mcSlot : slot.getEquipmentSlots()) {
                    ItemStack item = tracked.get(mcSlot);
                    if (item == null) continue;
                    if (styleId != null) {
                        String assetId = Cosmetics.GetById(styleId).map(Cosmetic::getItemModel).orElse(null);
                        if (assetId != null) CosmeticItemHelper.setEquippableAssetId(item, assetId);
                    } else {
                        // Restore original asset_id
                        String original = (origMap != null) ? origMap.get(mcSlot) : null;
                        CosmeticItemHelper.setEquippableAssetId(item, original);
                    }
                }
            }
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

    public static Optional<UUID> findOnlinePlayerUuid(MinecraftServer server, String username) {
        ServerPlayer player = server.getPlayerList().getPlayerByName(username);
        if (player == null) return Optional.empty();
        return Optional.of(player.getUUID());
    }
}
