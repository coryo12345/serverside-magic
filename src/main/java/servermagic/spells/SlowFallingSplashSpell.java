package servermagic.spells;

import java.util.List;
import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import servermagic.db.Database;
import servermagic.spells.utils.PotionSpellUtils;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class SlowFallingSplashSpell extends BaseSpell {

    public SlowFallingSplashSpell(ServerLevel world, ServerPlayer player, Database db) {
        super(world, player, db);
    }

    @Override
    protected void spellImplementation() {
        List<MobEffectInstance> effects = List.of(
                new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0));
        PotionSpellUtils.ThrowSplashPotion(world, player, effects);
    }

    @Override
    public String displayName() {
        return "Slow Falling Others";
    }

    @Override
    public String description() {
        return "Throw a splash potion of slow falling";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.SLOW_FALLING_SPLASH_SPELL);
    }
}
