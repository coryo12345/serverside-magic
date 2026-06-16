package servermagic.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import servermagic.db.Database;
import servermagic.db.tables.Config;
import servermagic.db.tables.PlayerSpellConfig;
import servermagic.db.tables.SpellSlot;
import servermagic.spells.BaseSpell;
import servermagic.web.spell.PlayerSpellsResponse;
import servermagic.web.spell.SpellConfigResponse;
import servermagic.web.spell.SpellSlots;
import servermagic.web.spell.Spells;
import servermagic.web.spell.UISpellDefinition;

public class SpellRoutes extends RouteGroup {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public SpellRoutes(Javalin app, Database db, MinecraftServer server) {
        super(app, db, server);
    }

    @Override
    public void registerRoutes() {
        this.requireAuthForGroup("/api/spells/");

        app.get("/api/spells/mine", ctx -> {
            String username = this.getAuthSubject(ctx);
            Optional<List<SpellSlot>> spells = SpellSlot.GetSlotsForPlayer(db, username);
            if (spells.isEmpty()) {
                ctx.status(500).result();
                return;
            }

            boolean superuserMode = "true".equals(ctx.queryParam("superuser")) && Config.IsSuperuser(db, username);
            Optional<Map<String, UISpellDefinition>> allSpells = superuserMode
                    ? Optional.of(Spells.Get().all())
                    : Spells.Get().allForPlayer(db, username);
            if (allSpells.isEmpty()) {
                ctx.status(500).result();
                return;
            }

            PlayerSpellsResponse psr = new PlayerSpellsResponse();
            psr.setSpellSlots(spells.get());
            psr.setAvailableSpells(allSpells.get());
            ctx.status(200).json(psr);
        });

        app.post("/api/spells/slot", ctx -> {
            String username = this.getAuthSubject(ctx);
            String spellId = ctx.formParam("spellId");
            String slotStr = ctx.formParam("slot");

            int slot;
            try {
                slot = Integer.parseInt(slotStr);
            } catch (NumberFormatException e) {
                ctx.status(400).result("slot is not valid");
                return;
            }
            if (!SpellSlots.isValidSlot(slot)) {
                ctx.status(400).result("slot is not valid");
                return;
            }

            // TODO - once we have the concept of "unlocked" spells,
            // we need to verify the user actually has this spell available
            // this will also verify that people don't pass garbage in for spell Ids

            Optional<SpellSlot> ss = SpellSlot.SetSlotForPlayer(db, username, spellId, slot);
            if (ss.isEmpty()) {
                ctx.status(500).result("Unable to set spell slot");
                return;
            }
            ctx.status(200).json(ss.get());
        });

        app.delete("/api/spells/slot", ctx -> {
            String username = this.getAuthSubject(ctx);
            String slotStr = ctx.queryParam("slot");

            if (slotStr == null) {
                ctx.status(400).result("slot is required");
                return;
            }

            int slot;
            try {
                slot = Integer.parseInt(slotStr);
            } catch (NumberFormatException e) {
                ctx.status(400).result("slot is not valid");
                return;
            }
            if (!SpellSlots.isValidSlot(slot)) {
                ctx.status(400).result("slot is not valid");
                return;
            }

            SpellSlot.ClearSlotForPlayer(db, username, slot);
            ctx.status(204);
        });

        app.delete("/api/spells/slots", ctx -> {
            String username = this.getAuthSubject(ctx);
            SpellSlot.ClearAllSlotsForPlayer(db, username);
            ctx.status(204);
        });

        app.get("/api/spells/config/{spellId}", ctx -> {
            String username = this.getAuthSubject(ctx);
            String spellId = ctx.pathParam("spellId");

            Optional<UISpellDefinition> spellDef = Spells.Get().getSpell(spellId);
            if (spellDef.isEmpty()) {
                ctx.status(404).result("Spell not found");
                return;
            }

            BaseSpell spell;
            try {
                spell = spellDef.get().getClazz()
                        .getDeclaredConstructor(ServerLevel.class, ServerPlayer.class, Database.class, InteractionHand.class)
                        .newInstance(null, null, db, null);
            } catch (Exception e) {
                ctx.status(500).result("Failed to instantiate spell");
                return;
            }

            SpellConfigResponse response = new SpellConfigResponse();
            response.schema = spell.getConfigSchema(username).orElse(null);

            Optional<PlayerSpellConfig> savedConfig = PlayerSpellConfig.GetConfigForPlayer(db, username, spellId);
            if (savedConfig.isPresent()) {
                try {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> parsedConfig = OBJECT_MAPPER.readValue(savedConfig.get().config, Map.class);
                    response.config = parsedConfig;
                } catch (Exception e) {
                    response.config = null;
                }
            }

            ctx.status(200).json(response);
        });

        app.post("/api/spells/config/{spellId}", ctx -> {
            String username = this.getAuthSubject(ctx);
            String spellId = ctx.pathParam("spellId");

            Optional<UISpellDefinition> spellDef = Spells.Get().getSpell(spellId);
            if (spellDef.isEmpty()) {
                ctx.status(404).result("Spell not found");
                return;
            }

            String body = ctx.body();
            if (body == null || body.isBlank()) {
                ctx.status(400).result("Request body is required");
                return;
            }

            try {
                OBJECT_MAPPER.readTree(body);
            } catch (Exception e) {
                ctx.status(400).result("Invalid JSON body");
                return;
            }

            PlayerSpellConfig.UpsertConfigForPlayer(db, username, spellId, body);
            ctx.status(204);
        });
    }

}
