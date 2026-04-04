package servermagic.web.spell;

import java.util.List;

public class SpellConfigField {
    public String key;
    public String type;
    public Object defaultValue;
    public Integer min;
    public Integer max;
    public List<String> options;

    public static SpellConfigField select(String key, List<String> options, String defaultValue) {
        SpellConfigField f = new SpellConfigField();
        f.key = key;
        f.type = "select";
        f.options = options;
        f.defaultValue = defaultValue;
        return f;
    }

    public static SpellConfigField text(String key, String defaultValue) {
        SpellConfigField f = new SpellConfigField();
        f.key = key;
        f.type = "text";
        f.defaultValue = defaultValue;
        return f;
    }

    public static SpellConfigField number(String key, Integer defaultValue) {
        SpellConfigField f = new SpellConfigField();
        f.key = key;
        f.type = "number";
        f.defaultValue = defaultValue;
        return f;
    }

    public static SpellConfigField number(String key, Integer defaultValue, Integer min, Integer max) {
        SpellConfigField f = number(key, defaultValue);
        f.min = min;
        f.max = max;
        return f;
    }

    public static SpellConfigField bool(String key, Boolean defaultValue) {
        SpellConfigField f = new SpellConfigField();
        f.key = key;
        f.type = "boolean";
        f.defaultValue = defaultValue;
        return f;
    }
}
