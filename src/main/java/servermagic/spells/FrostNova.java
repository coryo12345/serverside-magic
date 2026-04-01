package servermagic.spells;

import java.util.List;
import java.util.Optional;

import net.minecraft.core.particles.ParticleTypes;
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

public class FrostNova extends BaseSpell {

    private static final double RADIUS = 6.0;

    public FrostNova(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Vec3 center = player.position().add(0, 1, 0);

        // Hit all nearby living entities within radius (excluding caster)
        AABB searchArea = AABB.ofSize(player.position(), RADIUS * 2, RADIUS * 2, RADIUS * 2);
        List<LivingEntity> targets = world.getEntitiesOfClass(LivingEntity.class, searchArea,
                e -> e.isAlive()
                        && !e.getUUID().equals(player.getUUID())
                        && e.isAttackable()
                        && e.distanceTo(player) <= RADIUS);

        for (LivingEntity target : targets) {
            // Knockback away from player
            target.knockback(2.5, player.getX() - target.getX(), player.getZ() - target.getZ());

            // Slowness V for 2 seconds (40 ticks)
            target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 40, 4));

            // Per-target frost burst
            Vec3 tp = target.position();
            world.sendParticles(ParticleTypes.SNOWFLAKE,
                    tp.x, tp.y + target.getBbHeight() / 2.0, tp.z,
                    20, 0.4, 0.5, 0.4, 0.08);
            world.sendParticles(ParticleTypes.SPLASH,
                    tp.x, tp.y + 0.1, tp.z,
                    15, 0.4, 0.1, 0.4, 0.15);
        }

        // Nova ring — two expanding rings of particles at different heights
        spawnRing(center.x, center.y - 0.8, center.z, RADIUS, 32, 0.15);
        spawnRing(center.x, center.y,        center.z, RADIUS, 32, 0.15);
        spawnRing(center.x, center.y + 1.2,  center.z, RADIUS, 24, 0.15);

        // Dense central burst upward
        world.sendParticles(ParticleTypes.SNOWFLAKE,
                center.x, center.y, center.z,
                80, 0.5, 0.8, 0.5, 0.18);
        world.sendParticles(ParticleTypes.CLOUD,
                center.x, center.y, center.z,
                30, 0.6, 0.3, 0.6, 0.12);
        world.sendParticles(ParticleTypes.SPLASH,
                center.x, center.y + 0.1, center.z,
                40, 1.5, 0.1, 1.5, 0.2);

        // Sounds: sharp icy crack + glass shatter
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.2F, 0.7F);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WIND_CHARGE_BURST, SoundSource.PLAYERS, 0.8F, 1.5F);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.POWDER_SNOW_FALL, SoundSource.PLAYERS, 1.0F, 0.5F);
    }

    /** Spawns a ring of snowflake particles at fixed (x, y, z) with the given radius. */
    private void spawnRing(double x, double y, double z, double radius, int points, double spread) {
        for (int i = 0; i < points; i++) {
            double angle = (2 * Math.PI * i) / points;
            double rx = Math.cos(angle) * radius;
            double rz = Math.sin(angle) * radius;
            world.sendParticles(ParticleTypes.SNOWFLAKE,
                    x + rx, y, z + rz,
                    2, spread, spread * 0.5, spread, 0.04);
        }
    }

    @Override
    public int cost() {
        return 3;
    }

    @Override
    public String displayName() {
        return "Frost Nova";
    }

    @Override
    public String description() {
        return "Unleash a freezing shockwave that blasts nearby enemies back and slows them to a crawl";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.FROST_NOVA);
    }
}
