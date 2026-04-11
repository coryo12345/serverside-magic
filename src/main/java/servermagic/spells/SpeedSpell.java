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

public class SpeedSpell extends BaseSpell {

    public SpeedSpell(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        // 30 seconds = 30 * 20 ticks = 600 ticks
        // Amplifier 0 is Speed I, Amplifier 1 is Speed II, etc.
        player.addEffect(new MobEffectInstance(MobEffects.SPEED, 600, 0));
    }

    @Override

    public int getFlatXpCost() {
        return 2;
    }

    public double getLevelPercentCost() {
        return 0.05;
    }

    public String displayName() {
        return "Speed";
    }

    @Override
    public String description() {
        return "Gives you speed for 30 seconds";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.SPEED_SPELL);
    }
}
