package servermagic.db.migrations;

import java.util.List;

import servermagic.db.BaseMigration;

public class CosmeticConfigInit extends BaseMigration {

    @Override
    public String getName() {
        return "CosmeticConfigInit";
    }

    @Override
    public String[] getDependencies() {
        return new String[] { "CosmeticUnlocksInit" };
    }

    @Override
    public List<String> getSql() {
        return List.of("""
                create table if not exists cosmeticconfig (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT not null,
                    style TEXT not null,
                    slot TEXT not null,
                    UNIQUE(username, slot)
                );
                """);
    }

}
