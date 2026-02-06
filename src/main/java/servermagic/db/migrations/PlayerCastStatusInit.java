package servermagic.db.migrations;

import servermagic.db.BaseMigration;

public class PlayerCastStatusInit extends BaseMigration {

    @Override
    public String getName() {
        return "PlayerCastStatusInit";
    }

    @Override
    public String getSql() {
        return """
                create table if not exists playercaststatus (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE not null,
                    cast_state TEXT,
                    last_updated TIMESTAMP not null
                );

                create index status_username_idx on playercaststatus (username);
                """;
    }

}
