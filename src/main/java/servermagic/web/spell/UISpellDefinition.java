package servermagic.web.spell;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import servermagic.ServerMagic;
import servermagic.db.Database;
import servermagic.spells.BaseSpell;
import servermagic.web.skill.Skill;

public class UISpellDefinition {
    public String id;
    public String displayName;
    public String description;
    public int flatXpCost;
    public double levelPercentCost;
    public String requiredSkillId;

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
            BaseSpell spell = clazz.getDeclaredConstructor(ServerLevel.class, ServerPlayer.class, Database.class, InteractionHand.class)
                    .newInstance(null, null, null, null);

            UISpellDefinition def = new UISpellDefinition();
            def.id = spell.id();
            def.displayName = spell.displayName();
            def.description = spell.description();
            def.flatXpCost = spell.getFlatXpCost();
            def.levelPercentCost = spell.getLevelPercentCost();
            def.clazz = clazz;
            Optional<Skill> s = spell.getRequiredSkill();
            if (s.isPresent()) {
                def.requiredSkillId = s.get().id();
            }
            return def;
        } catch (Exception e) {
            ServerMagic.LOGGER.error("FAILED TO CONVERT SPELL TO DEFINITION: " + e.getMessage());
            return null;
        }
    }
}
