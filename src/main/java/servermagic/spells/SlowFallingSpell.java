package servermagic.spells;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class SlowFallingSpell extends BaseSpell {

    public SlowFallingSpell(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0));
    }

    @Override
    public String displayName() {
        return "Slow Falling";
    }

    @Override
    public String description() {
        return "Fall slowly and safely";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.SLOW_FALLING_SPELL);
    }
}
