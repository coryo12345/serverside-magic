package servermagic.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.reflections.Reflections;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Database {
    // need to get the DB as a singleton so mixin code can access
    // just so much simpler so we dont need to worry.
    // Not ideal but good enough for this project
    private static Database _db;

    public static void SetDB(Database db) {
        _db = db;
    }

    public static Optional<Database> GetDB() {
        if (_db == null) {
            return Optional.empty();
        }
        return Optional.of(_db);
    }

    // ===================================================

    private Sql2o sql2o;

    public Database(String dbPath) throws IOException, MigrationFailedException {
        Files.createDirectories(Paths.get(dbPath).normalize().getParent());
        sql2o = new Sql2o("jdbc:sqlite:" + dbPath, null, null);
        initTables();
        runMigrations();
    }

    private void initTables() {
        try (Connection conn = sql2o.open()) {
            conn.createQuery("""
                    CREATE TABLE IF NOT EXISTS migrations (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT UNIQUE NOT NULL,
                        completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                    """).executeUpdate();
        }
    }

    private void runMigrations() throws MigrationFailedException {
        Reflections reflections = new Reflections("servermagic.db.migrations");
        Set<Class<? extends BaseMigration>> allMigrationClasses = reflections.getSubTypesOf(BaseMigration.class);
        Set<String> ranMigrations = this.getRanMigrations();

        // determine which migrations need to be ran
        Set<Class<? extends BaseMigration>> migrationsToRun = new HashSet<>();
        for (var migrationClazz : allMigrationClasses) {
            try {
                BaseMigration m = migrationClazz.getDeclaredConstructor().newInstance();
                if (!ranMigrations.contains(m.getName())) {
                    migrationsToRun.add(migrationClazz);
                }
            } catch (Exception e) {
                throw new MigrationFailedException(e.getMessage());
            }
        }

        int migrationCountBeforeCycle = migrationsToRun.size();
        if (migrationCountBeforeCycle == 0)
            return;

        // now we run them
        do {
            Set<Class<? extends BaseMigration>> migrationsToRunCopy = new HashSet<>(migrationsToRun);
            for (Class<? extends BaseMigration> migration : migrationsToRunCopy) {
                try {
                    BaseMigration m = migration.getDeclaredConstructor().newInstance();
                    String[] deps = m.getDependencies();

                    boolean allMet = true;
                    for (String dep : deps) {
                        if (!ranMigrations.contains(dep)) {
                            allMet = false;
                        }
                    }

                    if (allMet) {
                        List<String> queries = m.getSql();
                        try (Connection conn = sql2o.beginTransaction()) {
                            for (String sql : queries) {
                                conn.createQuery(sql).executeUpdate();
                            }
                            conn.commit();
                        }

                        try (Connection conn = sql2o.open()) {
                            conn.createQuery("insert into migrations(name) values (:name)")
                                    .addParameter("name", m.getName())
                                    .executeUpdate();
                        }
                        ranMigrations.add(m.getName());
                        migrationsToRun.remove(migration);
                    }
                } catch (Exception e) {
                    throw new MigrationFailedException(e.getMessage());
                }
            }
        }
        // if we were able to run at least one migration then lets keep going, we may
        // have resolves some dependencies
        while (migrationCountBeforeCycle > migrationsToRun.size() && migrationsToRun.size() > 0);
    }

    private Set<String> getRanMigrations() {
        try (Connection conn = sql2o.open()) {
            List<String> m = conn.createQuery("select name from migrations").executeScalarList(String.class);
            return new HashSet<>(m);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> query(DBQueryCallback callback) {
        try (Connection conn = sql2o.open()) {
            return (Optional<T>) callback.callback(conn);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> transaction(DBQueryCallback callback) {
        try (Connection conn = sql2o.beginTransaction()) {
            try {
                Optional<T> res = (Optional<T>) callback.callback(conn);
                return res;
            } catch (Exception e) {
                conn.rollback();
            }
            return Optional.empty();
        }
    }
}
