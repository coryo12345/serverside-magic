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

public class RegenerationSpell extends BaseSpell {

    public RegenerationSpell(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        // Regeneration II (Amplifier 1) is nicer for short duration
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 1));
    }

    @Override

    public int getFlatXpCost() {
        return 5;
    }

    public double getLevelPercentCost() {
        return 0.15;
    }

    public String displayName() {
        return "Regeneration";
    }

    @Override
    public String description() {
        return "Regenerate health over time";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.REGENERATION_SPELL);
    }
}
