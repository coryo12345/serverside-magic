package mcservermagic.web;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import mcservermagic.db.Database;

public class AuthRoutes extends RouteGroup {

    public AuthRoutes(Javalin app, Database db) {
        super(app, db);
    }

    @Override
    public void registerRoutes() {
        // Public login endpoint
        app.post("/api/login", ctx -> {
            // String username = ctx.formParam("username");
            // String password = ctx.formParam("password");

            // // Validate credentials (you'd check against your DB)
            // if (isValidUser(username, password)) {
            // String token = generateToken(username);
            // ctx.json(Map.of("token", token));
            // } else {
            // ctx.status(401).result("Invalid credentials");
            // }
        });
    }

}
