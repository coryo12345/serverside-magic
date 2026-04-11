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

public class WaterBreathingSpell extends BaseSpell {

    public WaterBreathingSpell(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 600, 0));
    }

    @Override

    public int getFlatXpCost() {
        return 2;
    }

    public double getLevelPercentCost() {
        return 0.05;
    }

    public String displayName() {
        return "Water Breathing";
    }

    @Override
    public String description() {
        return "Breathe underwater";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.WATER_BREATHING_SPELL);
    }
}
