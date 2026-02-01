package servermagic.db.migrations;

import servermagic.db.BaseMigration;

public class AuthcodesInit extends BaseMigration {

    @Override
    public String getName() {
        return "AuthcodesInit";
    }

    @Override
    public String getSql() {
        return """
                create table if not exists authcode (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT unique not null,
                    code TEXT not null,
                    expires TIMESTAMP not null
                );
                """;
    }

}
