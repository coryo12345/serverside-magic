package servermagic.web.spell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import servermagic.db.tables.SpellSlot;

public class PlayerSpellsResponse {
    public Map<Integer, UISpellDefinition> spellSlotMap;
    public Map<String, UISpellDefinition> availableSpells;

    public PlayerSpellsResponse(Map<Integer, UISpellDefinition> spellMap,
            Map<String, UISpellDefinition> availableSpells) {
        this.spellSlotMap = spellMap;
        this.availableSpells = availableSpells;
    }

    public static PlayerSpellsResponse FromSpellSlots(List<SpellSlot> spellSlots) {
        Map<Integer, UISpellDefinition> spellSlotMap = new HashMap<>();
        for (SpellSlot ss : spellSlots) {
            Optional<UISpellDefinition> def = Spells.Get().getSpell(ss.spell_id);
            if (def.isPresent()) {
                spellSlotMap.put(ss.slot, def.get());
            }
        }
        return new PlayerSpellsResponse(spellSlotMap, Spells.Get().all());
    }
}
