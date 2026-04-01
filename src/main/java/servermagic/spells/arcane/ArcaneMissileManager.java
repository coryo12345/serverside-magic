package servermagic.spells.arcane;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import servermagic.entitybinding.EntityBindingManager;

public class ArcaneMissileManager {

    private static final List<MissileData> active = new ArrayList<>();

    /**
     * Register a newly launched missile for per-tick tracking.
     * Call immediately after addFreshEntity for both snowball and display.
     */
    public static void register(UUID snowballId, UUID displayId, UUID targetId,
            UUID casterId, ServerLevel level) {
        active.add(new MissileData(snowballId, displayId, targetId, casterId, level));
    }

    /**
     * Call once per server tick via ServerTickEvents.END_SERVER_TICK.
     */
    public static void tick(MinecraftServer server) {
        Iterator<MissileData> iter = active.iterator();
        while (iter.hasNext()) {
            if (tickMissile(iter.next())) {
                iter.remove();
            }
        }
    }

    /**
     * Returns true when the missile should be removed from the active list.
     */
    private static boolean tickMissile(MissileData missile) {
        missile.ticksAlive++;

        Entity snowball = missile.level.getEntity(missile.snowballId);
        Entity display = missile.level.getEntity(missile.displayId);

        // Timed out (~4 seconds) or snowball left the world
        if (missile.ticksAlive > 80 || snowball == null || !snowball.isAlive()) {
            cleanup(missile.level, display, missile.displayId);
            return true;
        }

        // Hit detection — check for any living entity overlapping the snowball
        AABB hitBox = snowball.getBoundingBox().inflate(0.4);
        List<LivingEntity> hits = missile.level.getEntitiesOfClass(
                LivingEntity.class,
                hitBox,
                e -> e.isAlive() && !e.getUUID().equals(missile.casterId));

        if (!hits.isEmpty()) {
            LivingEntity target = hits.get(0);
            target.hurtServer(missile.level, target.damageSources().magic(), 4.0f);

            // Impact particles
            missile.level.sendParticles(ParticleTypes.ENCHANT,
                    target.getX(), target.getY() + target.getBbHeight() / 2.0, target.getZ(),
                    20, 0.3, 0.4, 0.3, 0.2);
            missile.level.sendParticles(ParticleTypes.END_ROD,
                    target.getX(), target.getY() + target.getBbHeight() / 2.0, target.getZ(),
                    8, 0.2, 0.3, 0.2, 0.05);
            missile.level.playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.9F, 1.3F);

            snowball.discard();
            cleanup(missile.level, display, missile.displayId);
            return true;
        }

        // Homing — steer toward target if it's still alive
        if (missile.targetId != null) {
            Entity targetEntity = missile.level.getEntity(missile.targetId);
            if (targetEntity instanceof LivingEntity living && living.isAlive()) {
                Vec3 currentVel = snowball.getDeltaMovement();
                Vec3 toTarget = living.getBoundingBox().getCenter().subtract(snowball.position());
                double speed = Math.max(currentVel.length(), 0.3);
                Vec3 desired = toTarget.normalize().scale(speed);
                Vec3 newVel = currentVel.lerp(desired, 0.18);
                snowball.setDeltaMovement(newVel);
            }
        }

        // Particle trail
        Vec3 pos = snowball.position();
        missile.level.sendParticles(ParticleTypes.ENCHANT,
                pos.x, pos.y, pos.z, 2, 0.05, 0.05, 0.05, 0.05);
        missile.level.sendParticles(ParticleTypes.END_ROD,
                pos.x, pos.y, pos.z, 1, 0.02, 0.02, 0.02, 0.0);

        return false;
    }

    private static void cleanup(ServerLevel level, Entity display, UUID displayId) {
        if (display != null && display.isAlive()) {
            display.discard();
        }
        EntityBindingManager.getOrCreate(level).removeBinding(displayId);
    }

    // -------------------------------------------------------------------------

    private static class MissileData {
        final UUID snowballId;
        final UUID displayId;
        final UUID targetId; // nullable — null if no target found at cast time
        final UUID casterId;
        final ServerLevel level;
        int ticksAlive = 0;

        MissileData(UUID snowballId, UUID displayId, UUID targetId, UUID casterId, ServerLevel level) {
            this.snowballId = snowballId;
            this.displayId = displayId;
            this.targetId = targetId;
            this.casterId = casterId;
            this.level = level;
        }
    }
}
