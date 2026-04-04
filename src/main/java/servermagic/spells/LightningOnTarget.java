package servermagic.spells;

import java.util.Optional;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
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

        // Cast sounds at player
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 0.7F);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 0.8F, 1.2F);

        // Charge-up particles at target to telegraph the incoming strike
        Vec3 tp = target.get().position();
        world.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                tp.x, tp.y + 1.0, tp.z, 25, 0.4, 0.8, 0.4, 0.3);
        world.sendParticles(ParticleTypes.END_ROD,
                tp.x, tp.y + 1.0, tp.z, 10, 0.3, 0.6, 0.3, 0.1);

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
