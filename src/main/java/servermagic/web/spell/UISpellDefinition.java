package servermagic.web.spell;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import servermagic.ServerMagic;
import servermagic.spells.BaseSpell;

public class UISpellDefinition {
    public String id;
    public String displayName;
    public String description;
    public int cost;

    private Class<? extends BaseSpell> clazz;

    public UISpellDefinition() {
    }

    public void setClass(Class<? extends BaseSpell> c) {
        this.clazz = c;
    }

    public Class<? extends BaseSpell> getClazz() {
        return this.clazz;
    }

    public static UISpellDefinition FromSpell(Class<? extends BaseSpell> clazz) {
        try {
            BaseSpell spell = clazz.getDeclaredConstructor(ServerLevel.class, ServerPlayer.class).newInstance(null,
                    null);

            UISpellDefinition def = new UISpellDefinition();
            def.id = spell.id();
            def.displayName = spell.displayName();
            def.description = spell.description();
            def.cost = spell.cost();
            def.clazz = clazz;
            return def;
        } catch (Exception e) {
            ServerMagic.LOGGER.error("FAILED TO CONVERT SPELL TO DEFINITION: " + e.getMessage());
            return null;
        }
    }
}
