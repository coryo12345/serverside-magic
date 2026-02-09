package servermagic.db.migrations;

import java.util.List;

import servermagic.db.BaseMigration;

public class SpellSlotsInit extends BaseMigration {

    @Override
    public String getName() {
        return "SpellSlotsInit";
    }

    @Override
    public List<String> getSql() {
        return List.of("""
                create table if not exists spellslot (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT not null,
                    spell_id TEXT not null,
                    slot int not null
                );
                """,
                "create index user_slot_idx on spellslot (username, slot);");
    }

}
