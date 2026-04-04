package servermagic.db.tables;

import java.util.List;
import java.util.Optional;

import servermagic.db.Database;

public class PlayerSpellConfig {
    public Long id;
    public String username;
    public String spell_id;
    public String config;

    public static Optional<PlayerSpellConfig> GetConfigForPlayer(Database db, String username, String spellId) {
        return db.query(conn -> {
            List<PlayerSpellConfig> rows = conn
                    .createQuery("select * from player_spell_config where username = :username and spell_id = :spell_id")
                    .addParameter("username", username)
                    .addParameter("spell_id", spellId)
                    .executeAndFetch(PlayerSpellConfig.class);
            if (rows == null || rows.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(rows.get(0));
        });
    }

    public static void UpsertConfigForPlayer(Database db, String username, String spellId, String configJson) {
        db.transaction(conn -> {
            conn.createQuery(
                    "INSERT INTO player_spell_config (username, spell_id, config) VALUES (:username, :spell_id, :config) " +
                    "ON CONFLICT(username, spell_id) DO UPDATE SET config = excluded.config")
                    .addParameter("username", username)
                    .addParameter("spell_id", spellId)
                    .addParameter("config", configJson)
                    .executeUpdate();
            conn.commit();
            return null;
        });
    }
}
