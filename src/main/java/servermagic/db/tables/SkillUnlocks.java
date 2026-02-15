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
}