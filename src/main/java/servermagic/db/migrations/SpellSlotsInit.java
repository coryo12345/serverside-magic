package servermagic.db.migrations;

import servermagic.db.BaseMigration;

public class SpellSlotsInit extends BaseMigration {

    @Override
    public String getName() {
        return "SpellSlotsInit";
    }

    @Override
    public String getSql() {
        return """
                create table if not exists spellslot (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT not null,
                    spell_id TEXT not null,
                    slot int not null
                );
                """;
    }

}
