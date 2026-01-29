package mcservermagic.web;

import java.util.Optional;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.jsonwebtoken.Claims;
import mcservermagic.db.Database;
import mcservermagic.web.auth.AuthTokens;

public abstract class RouteGroup {
    protected Javalin app;
    protected Database db;

    public RouteGroup(Javalin app, Database db) {
        this.app = app;
        this.db = db;
    }

    public abstract void registerRoutes();

    // JWT Auth middleware
    protected void requireAuthForGroup(String group) {
        app.before(group, ctx -> {
            this.handleAuth(ctx);
        });
    }

    /**
     * Handles the authentication logic for this request.
     * If returned AuthResult is valid, the user is authorized and flow can
     * continue.
     * If it is NOT valid, then the context has already been handled and status set.
     * You must return.
     */
    protected AuthResult handleAuth(Context ctx) {
        String authHeader = ctx.header("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ctx.status(401).result("Missing token");
            return new AuthResult(false);
        }

        String token = authHeader.substring(7);
        Optional<Claims> claims = new AuthTokens().parseToken(token);
        if (claims.isEmpty()) {
            ctx.status(401).result("Invalid token");
            return new AuthResult(false);
        }

        ctx.attribute("username", claims.get().getSubject());
        return new AuthResult(true);
    }

    public class AuthResult {
        private boolean valid;

        public AuthResult(boolean valid) {
            this.valid = valid;
        }

        public boolean isValid() {
            return valid;

        }

        public boolean isInvalid() {
            return !valid;
        }
    }
}
