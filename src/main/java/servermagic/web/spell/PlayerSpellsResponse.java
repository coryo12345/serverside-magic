package servermagic.web.spell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import servermagic.db.tables.SpellSlot;

public class PlayerSpellsResponse {
    public Map<Integer, UISpellDefinition> spellSlotMap;
    public Map<String, UISpellDefinition> availableSpells;

    public PlayerSpellsResponse() {
        this.spellSlotMap = new HashMap<>();
        this.availableSpells = new HashMap<>();
    }

    public PlayerSpellsResponse(Map<Integer, UISpellDefinition> spellMap,
            Map<String, UISpellDefinition> availableSpells) {
        this.spellSlotMap = spellMap;
        this.availableSpells = availableSpells;
    }

    public void setSpellSlots(List<SpellSlot> spellSlots) {
        Map<Integer, UISpellDefinition> spellSlotMap = new HashMap<>();
        for (SpellSlot ss : spellSlots) {
            Optional<UISpellDefinition> def = Spells.Get().getSpell(ss.spell_id);
            if (def.isPresent()) {
                spellSlotMap.put(ss.slot, def.get());
            }
        }

        this.spellSlotMap = spellSlotMap;
    }

    public void setAvailableSpells(Map<String, UISpellDefinition> allSpells) {
        this.availableSpells = allSpells;
    }
}
