package servermagic.db.tables;

import java.util.List;
import java.util.Optional;

import servermagic.db.Database;

public class CosmeticUnlock {
    public Long id;
    public String username;
    public String style;
    public String slot;

    public static Optional<List<CosmeticUnlock>> GetUnlocksForPlayer(Database db, String username) {
        return db.query(conn -> {
            List<CosmeticUnlock> unlocks = conn
                    .createQuery("select * from cosmeticunlocks where username = :username")
                    .addParameter("username", username)
                    .executeAndFetch(CosmeticUnlock.class);
            if (unlocks == null) return Optional.empty();
            return Optional.of(unlocks);
        });
    }

    public static boolean IsUnlocked(Database db, String username, String style, String slot) {
        Optional<List<CosmeticUnlock>> unlocks = db.query(conn -> {
            List<CosmeticUnlock> rows = conn
                    .createQuery("select * from cosmeticunlocks where username = :username and style = :style and slot = :slot")
                    .addParameter("username", username)
                    .addParameter("style", style)
                    .addParameter("slot", slot)
                    .executeAndFetch(CosmeticUnlock.class);
            if (rows == null) return Optional.empty();
            return Optional.of(rows);
        });
        return unlocks.isPresent() && !unlocks.get().isEmpty();
    }
}
