package servermagic.spells;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class JumpBoostSpell extends BaseSpell {

    public JumpBoostSpell(ServerLevel world, ServerPlayer player, Database db) {
        super(world, player, db);
    }

    @Override
    protected void spellImplementation() {
        player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 600, 1)); // Jump II usually better for a spell
    }

    @Override
    public String displayName() {
        return "Jump Boost";
    }

    @Override
    public String description() {
        return "Leap higher into the air";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.JUMP_BOOST_SPELL);
    }
}
