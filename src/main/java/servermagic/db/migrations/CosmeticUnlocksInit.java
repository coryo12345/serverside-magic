package servermagic.db.migrations;

import java.util.List;

import servermagic.db.BaseMigration;

public class CosmeticUnlocksInit extends BaseMigration {

    @Override
    public String getName() {
        return "CosmeticUnlocksInit";
    }

    @Override
    public List<String> getSql() {
        return List.of("""
                create table if not exists cosmeticunlocks (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT not null,
                    style TEXT not null,
                    slot TEXT not null
                );
                """,
                "create index cosmeticunlocks_user_idx on cosmeticunlocks (username);");
    }

}
