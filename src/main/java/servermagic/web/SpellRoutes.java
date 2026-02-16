package servermagic.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.javalin.Javalin;
import net.minecraft.server.MinecraftServer;
import servermagic.db.Database;
import servermagic.db.tables.SpellSlot;
import servermagic.web.spell.PlayerSpellsResponse;
import servermagic.web.spell.SpellSlots;
import servermagic.web.spell.Spells;
import servermagic.web.spell.UISpellDefinition;

public class SpellRoutes extends RouteGroup {

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

            Optional<Map<String, UISpellDefinition>> allSpells = Spells.Get().allForPlayer(db, username);
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
    }

}
