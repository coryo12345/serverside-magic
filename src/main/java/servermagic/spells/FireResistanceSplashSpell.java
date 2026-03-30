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

public class FireResistanceSplashSpell extends BaseSpell {

    public FireResistanceSplashSpell(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        List<MobEffectInstance> effects = List.of(
                new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0));
        PotionSpellUtils.ThrowSplashPotion(world, player, effects);
    }

    @Override
    public String displayName() {
        return "Fire Resistance Others";
    }

    @Override
    public String description() {
        return "Throw a splash potion of fire resistance";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.FIRE_RESISTANCE_SPLASH_SPELL);
    }
}
