package servermagic.db.migrations;

import java.util.List;

import servermagic.db.BaseMigration;

public class AuthcodesInit extends BaseMigration {

    @Override
    public String getName() {
        return "AuthcodesInit";
    }

    @Override
    public List<String> getSql() {
        return List.of("""
                create table if not exists authcode (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT unique not null,
                    code TEXT not null,
                    expires TIMESTAMP not null
                );
                """,
                "create index authcode_user_idx on authcode (username);");
    }

}
