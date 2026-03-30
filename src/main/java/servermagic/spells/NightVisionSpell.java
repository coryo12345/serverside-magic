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

public class NightVisionSpell extends BaseSpell {

    public NightVisionSpell(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0));
    }

    @Override
    public String displayName() {
        return "Night Vision";
    }

    @Override
    public String description() {
        return "See clearly in the dark";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.NIGHT_VISION_SPELL);
    }
}
