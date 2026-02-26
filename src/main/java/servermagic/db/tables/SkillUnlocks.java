package servermagic.db.tables;

import java.util.List;
import java.util.Optional;

import servermagic.db.Database;
import servermagic.web.skill.Skill;

public class SkillUnlocks {
    public Long id;
    public String username;
    public String skill;
    public int available_in_tree;

    public static boolean IsSkillUnlocked(Database db, String username, Skill sk, boolean includeUnavailable) {
        Optional<SkillUnlocks> row = db.query(conn -> {
            String sql = "select * from skillunlocks where username = :username and skill = :skill";
            if (!includeUnavailable) {
                sql = sql + " and available_in_tree = 1";
            }
            List<SkillUnlocks> su = conn
                    .createQuery(sql)
                    .addParameter("username", username)
                    .addParameter("skill", sk.id())
                    .executeAndFetch(SkillUnlocks.class);

            if (su.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(su.get(0));
        });
        return row.isPresent();
    }

    public static Optional<List<SkillUnlocks>> GetAllPlayerUnlockedSkills(Database db, String username,
            boolean includeUnavailable) {
        return db.query(conn -> {
            String sql = "select * from skillunlocks where username = :username";
            if (!includeUnavailable) {
                sql = sql + " and available_in_tree = 1";
            }
            List<SkillUnlocks> su = conn.createQuery(sql)
                    .addParameter("username", username)
                    .executeAndFetch(SkillUnlocks.class);

            return Optional.of(su);
        });
    }

    public static Optional<SkillUnlocks> UnlockSkillForPlayerIfAble(Database db, String username, Skill skill) {
        return db.transaction(conn -> {
            // see if the player already has this skill
            List<SkillUnlocks> su = conn
                    .createQuery("select * from skillunlocks where username = :username and skill = :skill")
                    .addParameter("username", username)
                    .addParameter("skill", skill.id())
                    .executeAndFetch(SkillUnlocks.class);

            if (!su.isEmpty()) {
                return Optional.of(su.get(0));
            }

            // TODO check player skill points
            // TODO if enough, deduct points from player

            List<SkillUnlocks> unlock = conn
                    .createQuery("insert into skillunlocks (username, skill) values (:username, :skill) returning *")
                    .addParameter("username", username)
                    .addParameter("skill", skill.id())
                    .executeAndFetch(SkillUnlocks.class);
            if (unlock.isEmpty()) {
                conn.rollback();
                return Optional.empty();
            }

            conn.commit();
            return Optional.of(unlock.get(0));
        });
    }

    public static void SetSkillAvailability(Database db, String username, String skillId, boolean available) {
        db.transaction(conn -> {
            conn.createQuery(
                    "update skillunlocks set available_in_tree = :available where username = :username and skill = :skill")
                    .addParameter("available", available ? 1 : 0)
                    .addParameter("username", username)
                    .addParameter("skill", skillId)
                    .executeUpdate();
            conn.commit();
            return null;
        });
    }
}