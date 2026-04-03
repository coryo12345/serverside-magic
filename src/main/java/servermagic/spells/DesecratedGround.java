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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class DesecratedGround extends BaseSpell {

    private static final double HALF_SIZE = 2.5;
    private static final int DURATION_TICKS = 120;
    private static final int WITHER_INTERVAL = 20;
    private static final int WITHER_DURATION = 40;

    private static final List<ActiveDesecration> active = new ArrayList<>();

    public DesecratedGround(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Vec3 center = player.position();

        active.add(new ActiveDesecration(player.getUUID(), world, center));

        // Initial burst — spread flat across the 5x5 area
        world.sendParticles(ParticleTypes.SQUID_INK,
                center.x, center.y + 0.1, center.z, 30, 2.5, 0.3, 2.5, 0.04);
        world.sendParticles(ParticleTypes.LARGE_SMOKE,
                center.x, center.y + 0.1, center.z, 20, 2.0, 0.2, 2.0, 0.03);
        world.sendParticles(ParticleTypes.SCULK_SOUL,
                center.x, center.y + 0.5, center.z, 8, 0.4, 0.4, 0.4, 0.06);

        world.playSound(null, center.x, center.y, center.z,
                SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.PLAYERS, 1.0F, 0.7F);
        world.playSound(null, center.x, center.y, center.z,
                SoundEvents.WITHER_AMBIENT, SoundSource.PLAYERS, 0.8F, 0.5F);
    }

    public static void tick(MinecraftServer server) {
        Iterator<ActiveDesecration> iter = active.iterator();
        while (iter.hasNext()) {
            ActiveDesecration entry = iter.next();
            entry.ticksAlive++;

            if (entry.ticksAlive > DURATION_TICKS) {
                entry.level.playSound(null, entry.center.x, entry.center.y, entry.center.z,
                        SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.6F, 0.8F);
                iter.remove();
                continue;
            }

            tickDesecration(entry);
        }
    }

    private static void tickDesecration(ActiveDesecration entry) {
        Vec3 c = entry.center;
        double cy = c.y;

        // Perimeter — draw 4 edges of the 5x5 square at ground level
        for (double t = -HALF_SIZE; t <= HALF_SIZE; t += 0.5) {
            // North edge (z = -2.5)
            entry.level.sendParticles(ParticleTypes.SQUID_INK, c.x + t, cy + 0.1, c.z - HALF_SIZE, 1, 0, 0, 0, 0);
            // South edge (z = +2.5)
            entry.level.sendParticles(ParticleTypes.SQUID_INK, c.x + t, cy + 0.1, c.z + HALF_SIZE, 1, 0, 0, 0, 0);
            // West edge (x = -2.5)
            entry.level.sendParticles(ParticleTypes.SQUID_INK, c.x - HALF_SIZE, cy + 0.1, c.z + t, 1, 0, 0, 0, 0);
            // East edge (x = +2.5)
            entry.level.sendParticles(ParticleTypes.SQUID_INK, c.x + HALF_SIZE, cy + 0.1, c.z + t, 1, 0, 0, 0, 0);
        }

        // Even ticks: ash along perimeter for gray/brown variation
        if (entry.ticksAlive % 2 == 0) {
            for (double t = -HALF_SIZE; t <= HALF_SIZE; t += 0.5) {
                entry.level.sendParticles(ParticleTypes.ASH, c.x + t, cy + 0.1, c.z - HALF_SIZE, 1, 0, 0, 0, 0);
                entry.level.sendParticles(ParticleTypes.ASH, c.x + t, cy + 0.1, c.z + HALF_SIZE, 1, 0, 0, 0, 0);
                entry.level.sendParticles(ParticleTypes.ASH, c.x - HALF_SIZE, cy + 0.1, c.z + t, 1, 0, 0, 0, 0);
                entry.level.sendParticles(ParticleTypes.ASH, c.x + HALF_SIZE, cy + 0.1, c.z + t, 1, 0, 0, 0, 0);
            }
        }

        // Fill — dark particles rising from the ground inside the area
        var rng = entry.level.getRandom();
        for (int i = 0; i < 5; i++) {
            double rx = c.x + (rng.nextDouble() * 5.0 - HALF_SIZE);
            double rz = c.z + (rng.nextDouble() * 5.0 - HALF_SIZE);
            entry.level.sendParticles(ParticleTypes.ASH, rx, cy + 0.1, rz, 1, 0.05, 0.3, 0.05, 0.02);
        }

        if (entry.ticksAlive % 3 == 0) {
            for (int i = 0; i < 2; i++) {
                double rx = c.x + (rng.nextDouble() * 5.0 - HALF_SIZE);
                double rz = c.z + (rng.nextDouble() * 5.0 - HALF_SIZE);
                entry.level.sendParticles(ParticleTypes.LARGE_SMOKE, rx, cy + 0.1, rz, 1, 0.05, 0.2, 0.05, 0.01);
            }
        }

        // Red accents
        if (entry.ticksAlive % 5 == 0) {
            double rx = c.x + (rng.nextDouble() * 5.0 - HALF_SIZE);
            double rz = c.z + (rng.nextDouble() * 5.0 - HALF_SIZE);
            entry.level.sendParticles(ParticleTypes.CRIMSON_SPORE, rx, cy + 0.1, rz, 2, 0.2, 0.3, 0.2, 0.02);
        }

        // Apply Wither I to mobs standing in the zone
        if (entry.ticksAlive % WITHER_INTERVAL == 0) {
            AABB zone = AABB.ofSize(c, 5, 3, 5);
            List<LivingEntity> targets = entry.level.getEntitiesOfClass(LivingEntity.class, zone,
                    e -> e.isAlive() && !e.getUUID().equals(entry.casterId));
            for (LivingEntity target : targets) {
                target.addEffect(new MobEffectInstance(MobEffects.WITHER, WITHER_DURATION, 0));
            }
        }

        // Ambient sound every 40 ticks
        if (entry.ticksAlive % 40 == 0) {
            entry.level.playSound(null, c.x, c.y, c.z,
                    SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, SoundSource.AMBIENT, 0.5F, 1.0F);
        }
    }

    @Override
    public int cost() {
        return 6;
    }

    @Override
    public String displayName() {
        return "Desecrated Ground";
    }

    @Override
    public String description() {
        return "Corrupt the earth beneath your feet, withering any mob that stands within the cursed zone";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.DESECRATED_GROUND);
    }

    // -------------------------------------------------------------------------

    private static class ActiveDesecration {
        final UUID casterId;
        final ServerLevel level;
        final Vec3 center;
        int ticksAlive = 0;

        ActiveDesecration(UUID casterId, ServerLevel level, Vec3 center) {
            this.casterId = casterId;
            this.level = level;
            this.center = center;
        }
    }
}
