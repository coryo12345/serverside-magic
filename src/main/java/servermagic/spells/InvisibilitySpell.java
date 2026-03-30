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

public class InvisibilitySpell extends BaseSpell {

    public InvisibilitySpell(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 600, 0));
    }

    @Override
    public String displayName() {
        return "Invisibility";
    }

    @Override
    public String description() {
        return "Vanish from sight";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.INVISIBILITY_SPELL);
    }
}
