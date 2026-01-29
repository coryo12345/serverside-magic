package servermagic.web;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import servermagic.ServerMagic;
import servermagic.db.Database;

public class WebPortal {
    private Javalin app;
    private Database db;
    private final int port = 8080;

    public WebPortal(Database db) {
        this.db = db;
    }

    public void start() {
        app = Javalin.create(config -> {
            // First, try to serve from external directory (development)
            Path externalWebDir = Paths.get("config/servermagic/web").normalize();

            ServerMagic.LOGGER.info("Web file directory: " + externalWebDir.toAbsolutePath().toString());

            if (Files.exists(externalWebDir)) {
                // Development mode - serve from external directory
                config.staticFiles.add(staticFiles -> {
                    staticFiles.hostedPath = "/";
                    staticFiles.directory = externalWebDir.toString();
                    staticFiles.location = Location.EXTERNAL;
                });
                ServerMagic.LOGGER.info("[YourMod] Serving web files from: " + externalWebDir);
            } else {
                // Production mode - serve from JAR resources
                config.staticFiles.add(staticFiles -> {
                    staticFiles.hostedPath = "/";
                    staticFiles.directory = "/web";
                    staticFiles.location = Location.CLASSPATH;
                });
                ServerMagic.LOGGER.info("[YourMod] Serving web files from JAR resources");
            }
        }).start(port);

        setupRoutes();
    }

    public void stop() {
        if (app != null) {
            app.stop();
        }
    }

    private void setupRoutes() {
        new AuthRoutes(app, db).registerRoutes();
    }
}
