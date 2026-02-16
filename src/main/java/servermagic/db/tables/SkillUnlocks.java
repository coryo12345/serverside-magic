package servermagic.db.tables;

import java.util.List;
import java.util.Optional;

import servermagic.db.Database;
import servermagic.web.skill.Skill;

public class SkillUnlocks {
    public Long id;
    public String username;
    public String skill;

    public static boolean IsSkillUnlocked(Database db, String username, Skill sk) {
        Optional<SkillUnlocks> row = db.query(conn -> {
            List<SkillUnlocks> su = conn
                    .createQuery(
                            "select * from skillunlocks where username = :username and skill = :skill")
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

    public static Optional<List<SkillUnlocks>> GetAllPlayerUnlockedSkills(Database db, String username) {
        return db.query(conn -> {
            List<SkillUnlocks> su = conn.createQuery("select * from skillunlocks where username = :username")
                    .addParameter("username", username)
                    .executeAndFetch(SkillUnlocks.class);

            return Optional.of(su);
        });
    }

    public static Optional<SkillUnlocks> UnlockSkillForPlayerIfAble(Database db, String username, Skill skill) {
        return db.transaction(conn -> {
            // see if the player already has this skill
            List<SkillUnlocks> su = conn
                    .createQuery(
                            "select * from skillunlocks where username = :username and skill = :skill")
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
}