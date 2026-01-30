package servermagic.web;

import io.javalin.Javalin;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import servermagic.db.Database;

public class AuthRoutes extends RouteGroup {

    public AuthRoutes(Javalin app, Database db, MinecraftServer server) {
        super(app, db, server);
    }

    @Override
    public void registerRoutes() {
        // Public login endpoint
        app.post("/api/auth/requesttoken", ctx -> {
            String username = ctx.formParam("username");

            ServerPlayer player = server.getPlayerList().getPlayer(username);
            if (player == null) {
                ctx.status(401).result("Player does not exist");
                return;
            }

            int code = (int) Math.floor(Math.random() * 9999);
            // TODO need to store this code in the DB

            player.displayClientMessage(Component.literal("Your Code is:  " + code + "  | It will expire in 5 minutes."),
                    false);
            ctx.status(200).result("Verification sent to player");
        });

        app.post("/api/auth/validate", ctx -> {
            String username = ctx.formParam("username");
            String code = ctx.formParam("code");

            // Check DB table to make sure this code is legit
            // If so, generate JWT token
            // remove / invalidate auth code entry in db
            // respond with jwt token
        });
    }

}
