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

public class StrengthSpell extends BaseSpell {

    public StrengthSpell(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 600, 0));
    }

    @Override

    public int getFlatXpCost() {
        return 5;
    }

    public double getLevelPercentCost() {
        return 0.15;
    }

    public String displayName() {
        return "Strength";
    }

    @Override
    public String description() {
        return "Increase melee damage";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.STRENGTH_SPELL);
    }
}
