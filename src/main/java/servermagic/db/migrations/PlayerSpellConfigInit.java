package servermagic.db.migrations;

import java.util.List;

import servermagic.db.BaseMigration;

public class PlayerSpellConfigInit extends BaseMigration {

    @Override
    public String getName() {
        return "PlayerSpellConfigInit";
    }

    @Override
    public List<String> getSql() {
        return List.of("""
                create table if not exists player_spell_config (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT not null,
                    spell_id TEXT not null,
                    config TEXT not null
                );
                """,
                "create unique index player_spell_config_unique_idx on player_spell_config (username, spell_id);");
    }

}
