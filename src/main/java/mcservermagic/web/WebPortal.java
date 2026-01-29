package mcservermagic.web;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import mcservermagic.db.Database;

public class WebPortal {
    private Javalin app;
    private Database db;
    private final int port = 8080;

    public WebPortal(Database db) {
        this.db = db;
    }

    public void start() {
        app = Javalin.create(config -> {
            // Serve static files from a directory
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "./web";
                staticFiles.location = Location.EXTERNAL;
            });
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
