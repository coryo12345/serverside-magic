package servermagic.web;

import java.time.LocalDateTime;
import java.util.Optional;

import io.javalin.Javalin;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import servermagic.db.Database;
import servermagic.db.tables.Authcode;
import servermagic.web.auth.AuthTokens;

public class AuthRoutes extends RouteGroup {

    public AuthRoutes(Javalin app, Database db, MinecraftServer server) {
        super(app, db, server);
    }

    @Override
    public void registerRoutes() {
        // Public login endpoint
        app.post("/api/auth/requesttoken", ctx -> {
            String username = ctx.formParam("username");
            if (username == null || username.length() == 0) {
                ctx.status(400).result();
                return;
            }

            ServerPlayer player = server.getPlayerList().getPlayer(username);
            if (player == null) {
                ctx.status(401).result("Can't find that player. Are you logged in on the server?");
                return;
            }

            Optional<Authcode> ac = Authcode.GenerateCodeForUser(db, username);
            if (ac.isEmpty()) {
                ctx.status(500).result("Something went wrong...");
                return;
            }

            String code = ac.get().code;

            player.displayClientMessage(
                    Component.literal("Your Code is:  " + code + "  | It will expire in 5 minutes."),
                    false);
            ctx.status(200).result("Verification sent to player");
        });

        app.post("/api/auth/validate", ctx -> {
            String username = ctx.formParam("username");
            String code = ctx.formParam("code");

            if (code == null || username == null) {
                ctx.status(400).result();
            }

            Optional<Authcode> ac = Authcode.GetCodeForUser(db, username);
            if (ac.isEmpty()) {
                ctx.status(404).result("Invalid code");
                return;
            }

            LocalDateTime expiry = LocalDateTime.parse(ac.get().expires);
            if (LocalDateTime.now().isAfter(expiry)) {
                ctx.status(404).result("Invalid code");
                return;
            }

            if (!code.equals(ac.get().code)) {
                ctx.status(401).result("Invalid code");
                return;
            }

            String token = new AuthTokens().generateToken(username);
            Authcode.ClearForUser(db, username);

            ctx.status(200).result(token);
        });

        app.get("/api/auth/userinfo", ctx -> {
            AuthResult res = this.handleAuth(ctx);
            if (res.isInvalid()) {
                ctx.status(400).result();
                return;
            }
            String username = this.getAuthSubject(ctx);
            ctx.status(200).result(username);
        });
    }

}
