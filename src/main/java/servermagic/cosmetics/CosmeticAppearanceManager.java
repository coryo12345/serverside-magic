package servermagic.cosmetics;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import servermagic.data.items.CustomItem;
import servermagic.data.items.SpellbookItem;
import servermagic.db.Database;
import servermagic.db.tables.CosmeticConfig;

public class CosmeticAppearanceManager {
    // Cache: player UUID → CosmeticSlot → selected cosmetic id (null = none)
    private static final Map<UUID, Map<CosmeticSlot, String>> selectedCache = new ConcurrentHashMap<>();

    // Tracks which ItemStack objects currently have a cosmetic applied, per player per equipment slot
    private static final Map<UUID, Map<EquipmentSlot, ItemStack>> trackedItems = new ConcurrentHashMap<>();

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

                // If the tracked item is no longer in this slot, revert it
                if (tracked_ != null && tracked_ != current) {
                    if (CosmeticItemHelper.hasCosmeticApplied(tracked_)) {
                        CosmeticItemHelper.revertCosmetic(tracked_);
                    }
                    tracked.remove(mcSlot);
                    tracked_ = null;
                }

                // If no tracked item in this slot, check if current item should get a cosmetic
                if (tracked_ == null && !current.isEmpty() && isValidForSlot(current, cosmeticSlot)) {
                    String styleId = selected.get(cosmeticSlot);
                    if (styleId != null) {
                        Optional<Cosmetic> cosmetic = Cosmetics.GetById(styleId);
                        if (cosmetic.isPresent()) {
                            CosmeticItemHelper.applyCosmetic(current, cosmetic.get().getItemModel());
                            tracked.put(mcSlot, current);
                        }
                    }
                }
            }
        }
    }

    public static void loadPlayerCosmetics(ServerPlayer player, Database db) {
        UUID uuid = player.getUUID();
        String username = player.getName().getString();

        // Revert any stale cosmetics from a previous session
        Map<EquipmentSlot, ItemStack> tracked = trackedItems.get(uuid);
        if (tracked != null) {
            for (ItemStack item : tracked.values()) {
                if (item != null && CosmeticItemHelper.hasCosmeticApplied(item)) {
                    CosmeticItemHelper.revertCosmetic(item);
                }
            }
        }
        // Also scan equipped slots for any stale cosmetic flags (e.g., from disk)
        for (EquipmentSlot mcSlot : EquipmentSlot.values()) {
            ItemStack item = player.getItemBySlot(mcSlot);
            if (!item.isEmpty() && CosmeticItemHelper.hasCosmeticApplied(item)) {
                CosmeticItemHelper.revertCosmetic(item);
            }
        }

        trackedItems.put(uuid, new EnumMap<>(EquipmentSlot.class));

        // Load selected cosmetics from DB
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
        Map<EquipmentSlot, ItemStack> tracked = trackedItems.remove(uuid);
        if (tracked != null) {
            for (ItemStack item : tracked.values()) {
                if (item != null && CosmeticItemHelper.hasCosmeticApplied(item)) {
                    CosmeticItemHelper.revertCosmetic(item);
                }
            }
        }
        selectedCache.remove(uuid);
    }

    public static void revertAllEquippedCosmetics(ServerPlayer player) {
        UUID uuid = player.getUUID();
        Map<EquipmentSlot, ItemStack> tracked = trackedItems.get(uuid);
        if (tracked != null) {
            for (Map.Entry<EquipmentSlot, ItemStack> entry : tracked.entrySet()) {
                ItemStack item = entry.getValue();
                if (item != null && CosmeticItemHelper.hasCosmeticApplied(item)) {
                    CosmeticItemHelper.revertCosmetic(item);
                }
            }
            tracked.clear();
        }
        // Also scan equipment slots directly
        for (EquipmentSlot mcSlot : EquipmentSlot.values()) {
            ItemStack item = player.getItemBySlot(mcSlot);
            if (!item.isEmpty() && CosmeticItemHelper.hasCosmeticApplied(item)) {
                CosmeticItemHelper.revertCosmetic(item);
            }
        }
    }

    public static void updateSelectedCosmetic(UUID playerUuid, CosmeticSlot slot, String styleId) {
        Map<CosmeticSlot, String> cache = selectedCache.computeIfAbsent(playerUuid, k -> new HashMap<>());
        if (styleId == null) {
            cache.remove(slot);
        } else {
            cache.put(slot, styleId);
        }
        // Force re-evaluation on next tick by clearing tracked items for this cosmetic slot
        Map<EquipmentSlot, ItemStack> tracked = trackedItems.get(playerUuid);
        if (tracked != null) {
            for (EquipmentSlot mcSlot : slot.getEquipmentSlots()) {
                ItemStack item = tracked.remove(mcSlot);
                if (item != null && CosmeticItemHelper.hasCosmeticApplied(item)) {
                    CosmeticItemHelper.revertCosmetic(item);
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
