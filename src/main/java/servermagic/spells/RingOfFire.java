package servermagic.spells;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class RingOfFire extends BaseSpell {

    private static final double RADIUS = 5.0;
    private static final int DURATION = 100; // 5 seconds

    private static final List<ActiveRing> active = new ArrayList<>();

    public RingOfFire(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Vec3 center = player.position();
        active.add(new ActiveRing(center, world, player.getUUID()));

        // Initial ignition burst — dense ring of flame shoots upward
        int burstPoints = 36;
        for (int i = 0; i < burstPoints; i++) {
            double a = (2 * Math.PI * i) / burstPoints;
            double rx = Math.cos(a) * RADIUS;
            double rz = Math.sin(a) * RADIUS;
            world.sendParticles(ParticleTypes.FLAME,
                    center.x + rx, center.y + 0.1, center.z + rz,
                    6, 0.15, 0.4, 0.15, 0.08);
            world.sendParticles(ParticleTypes.LARGE_SMOKE,
                    center.x + rx, center.y + 0.1, center.z + rz,
                    2, 0.1, 0.2, 0.1, 0.02);
        }
        // Central flash
        world.sendParticles(ParticleTypes.LAVA,
                center.x, center.y + 0.5, center.z, 20, 2.0, 0.3, 2.0, 0.0);
        world.sendParticles(ParticleTypes.FLAME,
                center.x, center.y + 0.5, center.z, 30, 2.0, 0.3, 2.0, 0.12);

        world.playSound(null, center.x, center.y, center.z,
                SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 0.6F);
        world.playSound(null, center.x, center.y, center.z,
                SoundEvents.CAMPFIRE_CRACKLE, SoundSource.PLAYERS, 1.2F, 0.8F);
    }

    public static void tick(MinecraftServer server) {
        Iterator<ActiveRing> iter = active.iterator();
        while (iter.hasNext()) {
            ActiveRing ring = iter.next();
            ring.ticksAlive++;
            if (ring.ticksAlive > DURATION) {
                // Extinguish puff
                Vec3 c = ring.center;
                ring.level.sendParticles(ParticleTypes.LARGE_SMOKE,
                        c.x, c.y + 0.5, c.z, 25, 2.5, 0.4, 2.5, 0.03);
                ring.level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        c.x, c.y + 0.3, c.z, 15, 2.0, 0.3, 2.0, 0.02);
                ring.level.playSound(null, c.x, c.y, c.z,
                        SoundEvents.FIRE_EXTINGUISH, SoundSource.AMBIENT, 1.0F, 0.9F);
                iter.remove();
                continue;
            }
            tickRing(ring);
        }
    }

    private static void tickRing(ActiveRing ring) {
        Vec3 c = ring.center;
        ring.angle += 8.0;

        // --- Flame ring perimeter ---
        // Use two offset passes so the ring looks dense and flickering
        int ringPoints = 32;
        for (int i = 0; i < ringPoints; i++) {
            double a = Math.toRadians((360.0 * i / ringPoints) + ring.angle);
            double rx = Math.cos(a) * RADIUS;
            double rz = Math.sin(a) * RADIUS;

            // Main flames: random height gives natural fire look
            ring.level.sendParticles(ParticleTypes.FLAME,
                    c.x + rx, c.y + 0.05, c.z + rz,
                    1, 0.08, 0.25, 0.08, 0.01);

            // Small flames at half-offset angle for density
            double a2 = Math.toRadians((360.0 * i / ringPoints) + ring.angle + 180.0 / ringPoints);
            double rx2 = Math.cos(a2) * RADIUS;
            double rz2 = Math.sin(a2) * RADIUS;
            ring.level.sendParticles(ParticleTypes.SMALL_FLAME,
                    c.x + rx2, c.y + 0.05, c.z + rz2,
                    1, 0.06, 0.18, 0.06, 0.005);
        }

        // --- Smoke drifting up from the ring (every 2 ticks) ---
        if (ring.ticksAlive % 2 == 0) {
            int smokePoints = 12;
            for (int i = 0; i < smokePoints; i++) {
                double a = Math.toRadians((360.0 * i / smokePoints) - ring.angle * 0.5);
                double rx = Math.cos(a) * RADIUS;
                double rz = Math.sin(a) * RADIUS;
                ring.level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        c.x + rx, c.y + 0.3, c.z + rz,
                        1, 0.1, 0.1, 0.1, 0.01);
            }
        }

        // --- Occasional lava pops around the ring (every 5 ticks) ---
        if (ring.ticksAlive % 5 == 0) {
            double a = Math.toRadians(ring.angle * 3.7); // pseudo-random position
            ring.level.sendParticles(ParticleTypes.LAVA,
                    c.x + Math.cos(a) * RADIUS, c.y + 0.1, c.z + Math.sin(a) * RADIUS,
                    1, 0.0, 0.0, 0.0, 0.0);
        }

        // --- Soul fire flicker at the ground center (every 3 ticks) ---
        if (ring.ticksAlive % 3 == 0) {
            ring.level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    c.x, c.y + 0.1, c.z, 2, 0.3, 0.1, 0.3, 0.02);
        }

        // --- Ignite any mobs inside the ring ---
        AABB searchArea = AABB.ofSize(c, RADIUS * 2, RADIUS * 2, RADIUS * 2);
        List<LivingEntity> allInBox = ring.level.getEntitiesOfClass(LivingEntity.class, searchArea,
                e -> e.isAlive()
                        && !e.getUUID().equals(ring.casterId)
                        && e.isAttackable());
        for (LivingEntity target : allInBox) {
            double dx = target.getX() - c.x;
            double dz = target.getZ() - c.z;
            if (Math.sqrt(dx * dx + dz * dz) > RADIUS) continue;

            // Re-ignite every tick so they stay on fire while inside
            target.igniteForTicks(60); // 3 seconds, refreshed each tick

            // Fire burst on the burning entity (every 3 ticks to avoid spam)
            if (ring.ticksAlive % 3 == 0) {
                Vec3 tp = target.position();
                ring.level.sendParticles(ParticleTypes.FLAME,
                        tp.x, tp.y + target.getBbHeight() * 0.5, tp.z,
                        5, 0.3, 0.4, 0.3, 0.06);
            }
        }

        // --- Crackling ambient sound (every 30 ticks) ---
        if (ring.ticksAlive % 30 == 0) {
            ring.level.playSound(null, c.x, c.y, c.z,
                    SoundEvents.CAMPFIRE_CRACKLE, SoundSource.AMBIENT, 0.6F, 0.9F);
        }
    }

    @Override
    public int cost() {
        return 3;
    }

    @Override
    public String displayName() {
        return "Ring of Fire";
    }

    @Override
    public String description() {
        return "Ignite a ring of fire around you that sets ablaze any enemy who enters it";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.RING_OF_FIRE);
    }

    private static class ActiveRing {
        final Vec3 center;
        final ServerLevel level;
        final UUID casterId;
        int ticksAlive = 0;
        double angle = 0.0;

        ActiveRing(Vec3 center, ServerLevel level, UUID casterId) {
            this.center = center;
            this.level = level;
            this.casterId = casterId;
        }
    }
}
