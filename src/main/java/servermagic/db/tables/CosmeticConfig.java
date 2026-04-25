package servermagic.db.tables;

import java.util.List;
import java.util.Optional;

import servermagic.db.Database;

public class CosmeticConfig {
    public Long id;
    public String username;
    public String style;
    public String slot;

    public static Optional<List<CosmeticConfig>> GetConfigsForPlayer(Database db, String username) {
        return db.query(conn -> {
            List<CosmeticConfig> configs = conn
                    .createQuery("select * from cosmeticconfig where username = :username")
                    .addParameter("username", username)
                    .executeAndFetch(CosmeticConfig.class);
            if (configs == null) return Optional.empty();
            return Optional.of(configs);
        });
    }

    public static Optional<CosmeticConfig> GetConfigForPlayer(Database db, String username, String slot) {
        return db.query(conn -> {
            List<CosmeticConfig> configs = conn
                    .createQuery("select * from cosmeticconfig where username = :username and slot = :slot")
                    .addParameter("username", username)
                    .addParameter("slot", slot)
                    .executeAndFetch(CosmeticConfig.class);
            if (configs == null || configs.isEmpty()) return Optional.empty();
            return Optional.of(configs.get(0));
        });
    }

    public static void UpsertConfig(Database db, String username, String slot, String style) {
        db.transaction(conn -> {
            conn.createQuery("insert or replace into cosmeticconfig(username, slot, style) values (:username, :slot, :style)")
                    .addParameter("username", username)
                    .addParameter("slot", slot)
                    .addParameter("style", style)
                    .executeUpdate();
            conn.commit();
            return Optional.empty();
        });
    }

    public static void ClearConfig(Database db, String username, String slot) {
        db.query(conn -> {
            conn.createQuery("delete from cosmeticconfig where username = :username and slot = :slot")
                    .addParameter("username", username)
                    .addParameter("slot", slot)
                    .executeUpdate();
            return Optional.empty();
        });
    }
}
