package servermagic.spells;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import servermagic.db.Database;
import servermagic.spells.utils.SpellUtils;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class LightningOnTarget extends BaseSpell {

    public LightningOnTarget(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Optional<LivingEntity> target = SpellUtils.getFirstEntityInLineOfSight(player, 10);
        if (target.isEmpty())
            return;
        LightningBolt lb = EntityType.LIGHTNING_BOLT.create(world, EntitySpawnReason.COMMAND);
        lb.moveOrInterpolateTo(target.get().position());
        world.addFreshEntity(lb);
    }

    @Override
    public String displayName() {
        return "Lightning Strike";
    }

    @Override
    public String description() {
        return "Strike your target with lightning";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.LIGHTNING_STRIKE);
    }
}
