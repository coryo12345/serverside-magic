package servermagic.db.migrations;

import java.util.List;

import servermagic.db.BaseMigration;

public class ConfigInit extends BaseMigration {

    @Override
    public String getName() {
        return "ConfigInit";
    }

    @Override
    public List<String> getSql() {
        return List.of("""
                create table if not exists config (
                    id INTEGER PRIMARY KEY,
                    web_url TEXT NOT NULL
                );
                """);
    }

}
