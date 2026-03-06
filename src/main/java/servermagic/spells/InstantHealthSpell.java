package servermagic.spells;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class InstantHealthSpell extends BaseSpell {

    public InstantHealthSpell(ServerLevel world, ServerPlayer player, Database db) {
        super(world, player, db);
    }

    @Override
    protected void spellImplementation() {
        player.heal(6);
    }

    @Override
    public String displayName() {
        return "Heal Self";
    }

    @Override
    public String description() {
        return "Heal a small amount of hearts";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.INSTANT_HEALTH_SPELL);
    }
}
