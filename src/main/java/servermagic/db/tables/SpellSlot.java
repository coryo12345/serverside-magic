package servermagic.db.tables;

import java.util.List;
import java.util.Optional;

import servermagic.db.Database;

public class SpellSlot {
    public Long id;
    public String username;
    public String spell_id;
    public Integer slot;

    public static Optional<SpellSlot> SetSlotForPlayer(Database db, String username, String spellId, int slot) {
        return db.transaction(conn -> {
            conn.createQuery("delete from spellslot where username = :username and slot = :slot")
                    .addParameter("username", username).addParameter("slot", slot).executeUpdate();

            List<SpellSlot> ss = conn.createQuery(
                    "insert into spellslot(username, spell_id, slot) values (:username, :spell_id, :slot) returning *")
                    .addParameter("username", username)
                    .addParameter("spell_id", spellId)
                    .addParameter("slot", slot)
                    .executeAndFetch(SpellSlot.class);

            conn.commit();

            if (ss == null || ss.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(ss.get(0));
        });
    }

    public static Optional<SpellSlot> ClearSlotForPlayer(Database db, String username, int slot) {
        return db.query(conn -> {
            conn.createQuery("delete from spellslot where username = :username and slot = :slot")
                    .addParameter("username", username).addParameter("slot", slot).executeUpdate();
            return Optional.empty();
        });
    }

    public static Optional<List<SpellSlot>> GetSlotsForPlayer(Database db, String username) {
        return db.query(conn -> {
            List<SpellSlot> spells = conn.createQuery("select * from spellslot where username = :username order by slot asc")
                    .addParameter("username", username)
                    .executeAndFetch(SpellSlot.class);
            if (spells == null) {
                return Optional.empty();
            }
            return Optional.of(spells);
        });
    }
}
