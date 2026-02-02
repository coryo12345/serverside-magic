package servermagic.web;

import java.util.Optional;

import io.javalin.Javalin;
import net.minecraft.server.MinecraftServer;
import servermagic.db.Database;
import servermagic.db.tables.SpellSlot;
import servermagic.web.spell.Spells;

public class SpellRoutes extends RouteGroup {

    public SpellRoutes(Javalin app, Database db, MinecraftServer server) {
        super(app, db, server);
    }

    @Override
    public void registerRoutes() {
        this.requireAuthForGroup("/api/spells/");

        app.get("/api/spells/mine", ctx -> {
            // TODO this will need to be filtered down to just the user's unlocked spells
            ctx.status(200).json(Spells.Get().all());
        });

        app.post("/api/spells/slot", ctx -> {
            Optional<String> username = this.getAuthSubject(ctx);
            if (username.isEmpty()) {
                ctx.status(400).result();
                return;
            }

            String spellId = ctx.formParam("spellId");
            String slotStr = ctx.formParam("slot");
            int slot;
            try {
                slot = Integer.parseInt(slotStr);
            } catch (NumberFormatException e) {
                ctx.status(400).result("slot must be an integer");
                return;
            }

            // TODO - once we have the concept of "unlocked" spells,
            // we need to verify the user actually has this spell available

            Optional<SpellSlot> ss = SpellSlot.SetSlotForPlayer(db, username.get(), spellId, slot);
            if (ss.isEmpty()) {
                ctx.status(500).result("Unable to set spell slot");
                return;
            }
            ctx.status(200).json(ss.get());
        });
    }

}
