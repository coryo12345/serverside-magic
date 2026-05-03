package servermagic.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import io.javalin.Javalin;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import servermagic.cosmetics.Cosmetic;
import servermagic.cosmetics.CosmeticAppearanceManager;
import servermagic.cosmetics.CosmeticSlot;
import servermagic.cosmetics.Cosmetics;
import servermagic.db.Database;
import servermagic.db.tables.CosmeticConfig;
import servermagic.db.tables.CosmeticUnlock;

public class CosmeticRoutes extends RouteGroup {

    
    public CosmeticRoutes(Javalin app, Database db, MinecraftServer server) {
        super(app, db, server);
    }
    
    private boolean hasMasterControl(String username) {
        return "coryo12345".equals(username);
    }

    @Override
    public void registerRoutes() {
        this.requireAuthForGroup("/api/cosmetics/");

        app.get("/api/cosmetics/mine", ctx -> {
            String username = this.getAuthSubject(ctx);

            Optional<List<CosmeticUnlock>> unlocksOpt = CosmeticUnlock.GetUnlocksForPlayer(db, username);
            if (unlocksOpt.isEmpty()) {
                ctx.status(500).result("Failed to load unlocks");
                return;
            }

            Optional<List<CosmeticConfig>> configsOpt = CosmeticConfig.GetConfigsForPlayer(db, username);
            if (configsOpt.isEmpty()) {
                ctx.status(500).result("Failed to load cosmetic config");
                return;
            }

            // Build selected map: slot → style id (null if not set)
            Map<String, String> selected = new HashMap<>();
            for (CosmeticSlot slot : CosmeticSlot.values()) {
                selected.put(slot.getId(), null);
            }
            for (CosmeticConfig cc : configsOpt.get()) {
                selected.put(cc.slot, cc.style);
            }

            // Build unlocked list enriched with display names
            if (hasMasterControl(username)) {
                unlocksOpt = Optional.of(Cosmetics.GetAll().stream().map(c -> {
                    var cu = new CosmeticUnlock();
                    cu.id = 0L;
                    cu.slot = c.getSlot().getId();
                    cu.style = c.getId();
                    cu.username = username;
                    return cu;
                }).toList());
            }

            List<Map<String, String>> unlocked = unlocksOpt.get().stream()
                    .map(cu -> {
                        Map<String, String> entry = new HashMap<>();
                        entry.put("id", cu.style);
                        entry.put("slot", cu.slot);
                        Cosmetics.GetById(cu.style).ifPresent(c -> entry.put("displayName", c.getDisplayName()));
                        return entry;
                    })
                    .filter(e -> e.containsKey("displayName")) // skip unknown cosmetic ids
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("unlocked", unlocked);
            response.put("selected", selected);
            ctx.status(200).json(response);
        });

        app.post("/api/cosmetics/select", ctx -> {
            String username = this.getAuthSubject(ctx);
            String slotId = ctx.formParam("slot");
            String style = ctx.formParam("style");

            if (slotId == null || slotId.isBlank()) {
                ctx.status(400).result("slot is required");
                return;
            }

            Optional<CosmeticSlot> cosmeticSlotOpt = CosmeticSlot.fromId(slotId);
            if (cosmeticSlotOpt.isEmpty()) {
                ctx.status(400).result("Invalid slot");
                return;
            }
            CosmeticSlot cosmeticSlot = cosmeticSlotOpt.get();

            if (style == null || style.isBlank() || style.equals("none")) {
                // Clear the selected cosmetic for this slot
                CosmeticConfig.ClearConfig(db, username, slotId);
                applyInGame(username, cosmeticSlot, null);
                ctx.status(204);
                return;
            }

            // Validate the style exists as a cosmetic definition
            Optional<Cosmetic> cosmeticOpt = Cosmetics.GetById(style);
            if (cosmeticOpt.isEmpty()) {
                ctx.status(400).result("Unknown cosmetic style");
                return;
            }

            // Validate the cosmetic is for the correct slot
            if (cosmeticOpt.get().getSlot() != cosmeticSlot) {
                ctx.status(400).result("Cosmetic does not belong to the given slot");
                return;
            }

            // Verify the player has unlocked this cosmetic
            if (!CosmeticUnlock.IsUnlocked(db, username, style, slotId) && !hasMasterControl(username)) {
                ctx.status(403).result("Cosmetic not unlocked");
                return;
            }

            CosmeticConfig.UpsertConfig(db, username, slotId, style);
            applyInGame(username, cosmeticSlot, style);
            ctx.status(204);
        });
    }

    private void applyInGame(String username, CosmeticSlot slot, String styleId) {
        ServerPlayer player = server.getPlayerList().getPlayerByName(username);
        if (player == null) return;
        UUID uuid = player.getUUID();
        CosmeticAppearanceManager.updateSelectedCosmetic(uuid, slot, styleId);
    }
}
