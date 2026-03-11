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

public class WaterBreathingSplashSpell extends BaseSpell {

    public WaterBreathingSplashSpell(ServerLevel world, ServerPlayer player, Database db) {
        super(world, player, db);
    }

    @Override
    protected void spellImplementation() {
        List<MobEffectInstance> effects = List.of(
                new MobEffectInstance(MobEffects.WATER_BREATHING, 600, 0));
        PotionSpellUtils.ThrowSplashPotion(world, player, effects);
    }

    @Override
    public String displayName() {
        return "Water Breathing Others";
    }

    @Override
    public String description() {
        return "Throw a splash potion of water breathing";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.WATER_BREATHING_SPLASH_SPELL);
    }
}
