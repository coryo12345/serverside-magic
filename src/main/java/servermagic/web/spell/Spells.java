package servermagic.web.spell;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.reflections.Reflections;

import servermagic.ServerMagic;
import servermagic.spells.BaseSpell;

public class Spells {
    private static Spells singleton;

    public static Spells Get() {
        if (singleton == null) {
            singleton = new Spells();
            singleton.init();
        }
        return singleton;
    }

    // ==========================================
    private Map<String, UISpellDefinition> spellMap;

    private Spells() {
        this.spellMap = new HashMap<>();
    }

    private void init() {
        Reflections reflections = new Reflections("servermagic.spells");
        Set<Class<? extends BaseSpell>> allSpells = reflections.getSubTypesOf(BaseSpell.class);

        for (var spellClass : allSpells) {
            try {
                UISpellDefinition def = UISpellDefinition.FromSpell(spellClass);
                this.spellMap.put(def.id, def);
            } catch (Exception e) {
                ServerMagic.LOGGER.error("UNABLE TO INSTANTIATE SPELL: " + spellClass.getSimpleName());
            }
        }
    }

    public Optional<UISpellDefinition> getSpell(String id) {
        var s = this.spellMap.get(id);
        if (s == null) {
            return Optional.empty();
        }
        return Optional.of(s);
    }

    public Map<String, UISpellDefinition> all() {
        return new HashMap<>(this.spellMap);
    }
}
