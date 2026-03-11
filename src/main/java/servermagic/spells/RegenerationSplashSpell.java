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

public class RegenerationSplashSpell extends BaseSpell {

    public RegenerationSplashSpell(ServerLevel world, ServerPlayer player, Database db) {
        super(world, player, db);
    }

    @Override
    protected void spellImplementation() {
        // Regeneration II (Amplifier 1)
        List<MobEffectInstance> effects = List.of(
                new MobEffectInstance(MobEffects.REGENERATION, 600, 1));
        PotionSpellUtils.ThrowSplashPotion(world, player, effects);
    }

    @Override
    public String displayName() {
        return "Regeneration Others";
    }

    @Override
    public String description() {
        return "Throw a splash potion of regeneration";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.REGENERATION_SPLASH_SPELL);
    }
}
