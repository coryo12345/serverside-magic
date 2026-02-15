package servermagic.db.migrations;

import java.util.List;

import servermagic.db.BaseMigration;

public class SkillUnlocksInit extends BaseMigration {

    @Override
    public String getName() {
        return "SkillUnlocksInit";
    }

    @Override
    public List<String> getSql() {
        return List.of("""
                create table if not exists skillunlocks (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT not null,
                    skill TEXT not null
                );
                """,
                "create index skillunlocks_user_idx on skillunlocks (username);");
    }

}
