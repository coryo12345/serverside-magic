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

public class GravityWell extends BaseSpell {

    private static final double RADIUS = 8.0;
    private static final int DURATION = 100; // 5 seconds

    private static final List<ActiveWell> active = new ArrayList<>();

    public GravityWell(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Vec3 center = player.position();

        active.add(new ActiveWell(center, world, player.getUUID()));

        // Initial collapse burst
        world.sendParticles(ParticleTypes.REVERSE_PORTAL,
                center.x, center.y + 0.5, center.z, 80, 2.5, 0.5, 2.5, 0.2);
        world.sendParticles(ParticleTypes.PORTAL,
                center.x, center.y + 0.5, center.z, 50, 2.0, 0.4, 2.0, 0.15);
        world.sendParticles(ParticleTypes.SCULK_SOUL,
                center.x, center.y + 1.0, center.z, 12, 0.6, 0.6, 0.6, 0.08);
        world.sendParticles(ParticleTypes.END_ROD,
                center.x, center.y + 0.5, center.z, 20, 1.0, 0.5, 1.0, 0.12);

        world.playSound(null, center.x, center.y, center.z,
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0F, 0.5F);
        world.playSound(null, center.x, center.y, center.z,
                SoundEvents.ENDER_EYE_LAUNCH, SoundSource.PLAYERS, 1.0F, 0.3F);
    }

    public static void tick(MinecraftServer server) {
        Iterator<ActiveWell> iter = active.iterator();
        while (iter.hasNext()) {
            ActiveWell well = iter.next();
            well.ticksAlive++;
            if (well.ticksAlive > DURATION) {
                // Collapse pop
                well.level.sendParticles(ParticleTypes.REVERSE_PORTAL,
                        well.center.x, well.center.y + 0.5, well.center.z, 30, 0.5, 0.5, 0.5, 0.25);
                well.level.playSound(null, well.center.x, well.center.y, well.center.z,
                        SoundEvents.BEACON_DEACTIVATE, SoundSource.AMBIENT, 0.8F, 0.6F);
                iter.remove();
                continue;
            }
            tickWell(well);
        }
    }

    private static void tickWell(ActiveWell well) {
        Vec3 c = well.center;
        well.angle += 12.0;

        // --- Outer spinning ring (REVERSE_PORTAL pulls inward visually) ---
        int outerPoints = 28;
        for (int i = 0; i < outerPoints; i++) {
            double a = Math.toRadians(well.angle + (360.0 * i / outerPoints));
            double rx = Math.cos(a) * RADIUS;
            double rz = Math.sin(a) * RADIUS;
            well.level.sendParticles(ParticleTypes.REVERSE_PORTAL,
                    c.x + rx, c.y + 0.05, c.z + rz,
                    1, 0.08, 0.08, 0.08, 0.02);
        }

        // --- Mid ring rotating opposite direction ---
        int midPoints = 18;
        double midRadius = RADIUS * 0.55;
        for (int i = 0; i < midPoints; i++) {
            double a = Math.toRadians(-well.angle * 1.4 + (360.0 * i / midPoints));
            double rx = Math.cos(a) * midRadius;
            double rz = Math.sin(a) * midRadius;
            well.level.sendParticles(ParticleTypes.PORTAL,
                    c.x + rx, c.y + 0.15, c.z + rz,
                    1, 0.12, 0.15, 0.12, 0.03);
        }

        // --- Pulsing center vortex (every other tick) ---
        if (well.ticksAlive % 2 == 0) {
            well.level.sendParticles(ParticleTypes.SCULK_SOUL,
                    c.x, c.y + 0.2, c.z, 3, 0.3, 0.4, 0.3, 0.04);
            well.level.sendParticles(ParticleTypes.END_ROD,
                    c.x, c.y + 0.3, c.z, 2, 0.25, 0.15, 0.25, 0.06);
        }

        // --- Pull nearby entities toward center ---
        AABB searchArea = AABB.ofSize(c, RADIUS * 2, RADIUS * 2, RADIUS * 2);
        List<LivingEntity> targets = well.level.getEntitiesOfClass(LivingEntity.class, searchArea,
                e -> e.isAlive()
                        && !e.getUUID().equals(well.casterId)
                        && e.isAttackable());

        for (LivingEntity target : targets) {
            double dx = target.getX() - c.x;
            double dz = target.getZ() - c.z;
            double dist = Math.sqrt(dx * dx + dz * dz);
            if (dist > RADIUS || dist < 0.5) continue;

            // knockback(strength, dx, dz) pushes entity away from (dx, dz).
            // Passing (target - center) pushes away from the "outward" direction = toward center.
            double pullStrength = 0.06 + 0.08 * (1.0 - dist / RADIUS);
            target.knockback(pullStrength, dx, dz);

            // Particle trail streaming off the entity toward center
            if (well.ticksAlive % 2 == 0) {
                Vec3 tp = target.position();
                well.level.sendParticles(ParticleTypes.REVERSE_PORTAL,
                        tp.x, tp.y + target.getBbHeight() * 0.5, tp.z,
                        4, 0.2, 0.3, 0.2, 0.05);
            }
        }

        // --- Ambient sound pulse every 25 ticks ---
        if (well.ticksAlive % 25 == 0) {
            well.level.playSound(null, c.x, c.y, c.z,
                    SoundEvents.PORTAL_AMBIENT, SoundSource.AMBIENT, 0.4F, 0.5F);
        }
    }

    @Override

    public int getFlatXpCost() {
        return 20;
    }

    public double getLevelPercentCost() {
        return 0.5;
    }
    @Override
    public String displayName() {
        return "Gravity Well";
    }

    @Override
    public String description() {
        return "Open a vortex that drags all nearby enemies toward its center for 5 seconds";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.GRAVITY_WELL);
    }

    private static class ActiveWell {
        final Vec3 center;
        final ServerLevel level;
        final UUID casterId;
        int ticksAlive = 0;
        double angle = 0.0;

        ActiveWell(Vec3 center, ServerLevel level, UUID casterId) {
            this.center = center;
            this.level = level;
            this.casterId = casterId;
        }
    }
}
