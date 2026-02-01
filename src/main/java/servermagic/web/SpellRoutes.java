package servermagic.web;

import io.javalin.Javalin;
import net.minecraft.server.MinecraftServer;
import servermagic.db.Database;
import servermagic.web.spell.Spells;

public class SpellRoutes extends RouteGroup {

    public SpellRoutes(Javalin app, Database db, MinecraftServer server) {
        super(app, db, server);
    }

    @Override
    public void registerRoutes() {
        app.get("/api/spells/mine", ctx -> {
            ctx.status(200).json(Spells.Get().all());
        });
    }

}
