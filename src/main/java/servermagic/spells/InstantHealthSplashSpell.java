package servermagic.spells;

import java.util.List;
import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import servermagic.db.Database;
import servermagic.spells.utils.PotionSpellUtils;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class InstantHealthSplashSpell extends BaseSpell {

    public InstantHealthSplashSpell(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        List<MobEffectInstance> effects = List.of(
                new MobEffectInstance(MobEffects.INSTANT_HEALTH, 0, 0, false, true));
        PotionSpellUtils.ThrowSplashPotion(world, player, effects);
    }

    @Override

    public int getFlatXpCost() {
        return 5;
    }

    public double getLevelPercentCost() {
        return 0.15;
    }

    public String displayName() {
        return "Heal Others";
    }

    @Override
    public String description() {
        return "Throw a splash healing mist";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.INSTANT_HEALTH_SPELL);
    }
}
