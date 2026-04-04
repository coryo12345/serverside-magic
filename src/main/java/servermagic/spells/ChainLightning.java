package servermagic.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.spells.utils.SpellUtils;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class ChainLightning extends BaseSpell {

    public ChainLightning(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        List<LivingEntity> entities = SpellUtils.getAllLivingEntitiesInCone(player, 10, 30);

        // Cast sound at player origin
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.3F);

        // we need to build the path between each entity, as line segments
        List<Vec3> points = new ArrayList<>();
        points.add(player.getEyePosition(0));
        for (LivingEntity entity : entities) {
            points.add(entity.getEyePosition(0));
        }

        // spawn particles on the lines
        for (int i = 0; i < points.size() - 1; i++) {
            Vec3 startingPoint = points.get(i);
            Vec3 endingPoint = points.get(i + 1);
            Vec3 segment = startingPoint.vectorTo(endingPoint);
            double distance = startingPoint.distanceTo(endingPoint);
            int pointsBetweenCount = (int) Math.floor(distance / 0.25);
            Vec3 interval = new Vec3(segment.x / pointsBetweenCount, segment.y / pointsBetweenCount,
                    segment.z / pointsBetweenCount);
            for (int j = 1; j <= pointsBetweenCount; j++) {
                Vec3 addBy = interval.multiply(j, j, j);
                Vec3 particlePos = startingPoint.add(addBy);
                world.sendParticles(ParticleTypes.END_ROD, particlePos.x, particlePos.y, particlePos.z, 1, 0.1,
                        0.1, 0.1, 0);
            }
        }

        // Now do the damage and add impact effects at each target
        DamageSource ds = player.damageSources().magic();
        for (LivingEntity entity : entities) {
            if (entity.isAttackable()) {
                entity.hurtServer(world, ds, 7);
            }
            Vec3 ep = entity.getEyePosition();
            world.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    ep.x, ep.y, ep.z, 14, 0.3, 0.4, 0.3, 0.25);
            world.sendParticles(ParticleTypes.CRIT,
                    ep.x, ep.y, ep.z, 8, 0.3, 0.3, 0.3, 0.15);
            world.playSound(null, ep.x, ep.y, ep.z,
                    SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS, 0.5F, 1.8F);
        }
    }

    @Override
    public String displayName() {
        return "Chain Lightning";
    }

    @Override
    public String description() {
        return "Strike the targets in front of you with arc lightning";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.CHAIN_LIGHTNING);
    }
}
