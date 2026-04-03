package servermagic.spells;

import java.util.List;
import java.util.Optional;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.spells.utils.SpellUtils;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class Reap extends BaseSpell {

    private static final double RANGE = 8.0;
    private static final double ARC_HALF_ANGLE = 65.0; // 130 degree total arc
    private static final double ARC_HALF_ANGLE_RAD = Math.toRadians(ARC_HALF_ANGLE);
    private static final float MINOR_DAMAGE = 2.0f; // 1 hearts
    private static final double EXECUTE_THRESHOLD = 0.20; // 20% health

    public Reap(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        List<LivingEntity> targets = SpellUtils.getAllLivingEntitiesInCone(player, RANGE, ARC_HALF_ANGLE);

        Vec3 look = player.getLookAngle();
        Vec3 flatLook = new Vec3(look.x, 0, look.z);
        if (flatLook.length() > 0.01) {
            flatLook = flatLook.normalize();
        } else {
            flatLook = player.getForward();
        }

        spawnArcParticles(flatLook);

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.2F, 0.6F);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 0.4F, 1.8F);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.PLAYERS, 0.5F, 0.7F);

        for (LivingEntity target : targets) {
            Vec3 tc = target.getBoundingBox().getCenter();
            if (target.getHealth() / target.getMaxHealth() <= EXECUTE_THRESHOLD) {
                target.hurtServer(world, target.damageSources().magic(), target.getMaxHealth() * 2);
                world.sendParticles(ParticleTypes.SOUL,
                        tc.x, tc.y, tc.z, 15, 0.4, 0.5, 0.4, 0.1);
                world.sendParticles(ParticleTypes.REVERSE_PORTAL,
                        tc.x, tc.y, tc.z, 20, 0.3, 0.4, 0.3, 0.12);
                world.sendParticles(ParticleTypes.ENCHANTED_HIT,
                        tc.x, tc.y, tc.z, 25, 0.4, 0.4, 0.4, 0.15);
            } else {
                target.hurtServer(world, target.damageSources().magic(), MINOR_DAMAGE);
                world.sendParticles(ParticleTypes.CRIT,
                        tc.x, tc.y, tc.z, 10, 0.3, 0.3, 0.3, 0.1);
                world.sendParticles(ParticleTypes.ENCHANTED_HIT,
                        tc.x, tc.y, tc.z, 8, 0.3, 0.3, 0.3, 0.08);
            }
        }
    }

    private void spawnArcParticles(Vec3 flatLook) {
        Vec3 origin = player.getEyePosition().subtract(0, 0.5, 0);
        double baseAngle = Math.atan2(flatLook.z, flatLook.x);

        // Sweep SWEEP_ATTACK particles across the full arc at multiple radii
        int angularSteps = 20;
        for (int i = 0; i <= angularSteps; i++) {
            double t = (double) i / angularSteps;
            double angle = baseAngle - ARC_HALF_ANGLE_RAD + t * (ARC_HALF_ANGLE_RAD * 2);
            double cosA = Math.cos(angle);
            double sinA = Math.sin(angle);
            for (double r = 1.5; r <= RANGE; r += 1.5) {
                world.sendParticles(ParticleTypes.SWEEP_ATTACK,
                        origin.x + cosA * r, origin.y, origin.z + sinA * r,
                        1, 0.0, 0.0, 0.0, 0.0);
            }
        }

        // Outer edge arc: ENCHANTED_HIT + CRIT fringe
        int edgeSteps = 12;
        for (int i = 0; i <= edgeSteps; i++) {
            double t = (double) i / edgeSteps;
            double angle = baseAngle - ARC_HALF_ANGLE_RAD + t * (ARC_HALF_ANGLE_RAD * 2);
            double px = origin.x + Math.cos(angle) * RANGE;
            double pz = origin.z + Math.sin(angle) * RANGE;
            world.sendParticles(ParticleTypes.ENCHANTED_HIT,
                    px, origin.y, pz, 3, 0.2, 0.3, 0.2, 0.1);
            world.sendParticles(ParticleTypes.CRIT,
                    px, origin.y, pz, 2, 0.15, 0.2, 0.15, 0.08);
        }

        // Center scatter: soul particles for the dark reaping feel
        world.sendParticles(ParticleTypes.SOUL,
                origin.x + flatLook.x * 3, origin.y, origin.z + flatLook.z * 3,
                12, 2.0, 0.5, 2.0, 0.05);
        world.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                origin.x + flatLook.x * 2, origin.y, origin.z + flatLook.z * 2,
                8, 1.5, 0.3, 1.5, 0.06);
    }

    @Override
    public int cost() {
        return 5;
    }

    @Override
    public String displayName() {
        return "Reap";
    }

    @Override
    public String description() {
        return "A sweeping arc that executes mobs below 20% health and damages others";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.REAP);
    }
}
