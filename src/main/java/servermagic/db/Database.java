package servermagic.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Database {
    private Sql2o sql2o;

    public Database(String dbPath) throws IOException {
        Files.createDirectories(Paths.get(dbPath).normalize().getParent());
        sql2o = new Sql2o("jdbc:sqlite:" + dbPath, null, null);
        initTables();
    }

    private void initTables() {
        try (Connection conn = sql2o.open()) {
            // conn.createQuery("""
            // CREATE TABLE IF NOT EXISTS users (
            // id INTEGER PRIMARY KEY AUTOINCREMENT,
            // username TEXT UNIQUE NOT NULL,
            // password_hash TEXT NOT NULL,
            // created_at INTEGER
            // )
            // """).executeUpdate();

            // conn.createQuery("""
            // CREATE TABLE IF NOT EXISTS player_data (
            // uuid TEXT PRIMARY KEY,
            // username TEXT,
            // last_location TEXT,
            // balance INTEGER DEFAULT 0
            // )
            // """).executeUpdate();
        }
    }

    // public User getUser(String username) {
    // try (Connection conn = sql2o.open()) {
    // return conn.createQuery("SELECT * FROM users WHERE username = :username")
    // .addParameter("username", username)
    // .executeAndFetchFirst(User.class);
    // }
    // }
}
