package servermagic.spells;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.spells.utils.SpellUtils;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class Sunbeam extends BaseSpell {

    private static final double PILLAR_RADIUS = 2.0;
    private static final double PILLAR_HEIGHT = 20.0;
    private static final float SMITE_DAMAGE = 20.0f; // 10 hearts
    private static final int BEAM_DURATION = 40; // 2 seconds
    private static final double TARGET_RANGE = 25.0;

    private static final List<ActiveBeam> active = new ArrayList<>();

    public Sunbeam(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Optional<LivingEntity> targetOpt = SpellUtils.getFirstEntityInLineOfSight(player, TARGET_RANGE);
        Vec3 center = targetOpt.isPresent() ? targetOpt.get().position() : player.position();

        // Damage all undead in the pillar immediately
        AABB pillarBox = new AABB(
                center.x - PILLAR_RADIUS, center.y - 0.5, center.z - PILLAR_RADIUS,
                center.x + PILLAR_RADIUS, center.y + PILLAR_HEIGHT, center.z + PILLAR_RADIUS);
        List<LivingEntity> targets = world.getEntitiesOfClass(LivingEntity.class, pillarBox,
                e -> e.isAlive() && e.getType().is(EntityTypeTags.SENSITIVE_TO_SMITE)
                        && !e.getUUID().equals(player.getUUID()));
        for (LivingEntity target : targets) {
            target.hurtServer(world, target.damageSources().magic(), SMITE_DAMAGE);
            Vec3 tp = target.getBoundingBox().getCenter();
            world.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    tp.x, tp.y, tp.z, 25, 0.5, 0.4, 0.5, 0.25);
        }

        // Initial impact burst at base
        world.sendParticles(ParticleTypes.CRIT,
                center.x, center.y + 1.0, center.z, 35, 0.8, 0.3, 0.8, 0.2);
        world.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                center.x, center.y + 1.0, center.z, 60, 1.2, 0.5, 1.2, 0.35);
        world.sendParticles(ParticleTypes.END_ROD,
                center.x, center.y + 10.0, center.z, 40, PILLAR_RADIUS * 0.5, 8.0, PILLAR_RADIUS * 0.5, 0.02);

        world.playSound(null, center.x, center.y, center.z,
                SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS, 1.4F, 1.3F);
        world.playSound(null, center.x, center.y, center.z,
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 0.8F, 1.5F);
        world.playSound(null, center.x, center.y, center.z,
                SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.PLAYERS, 0.6F, 1.2F);

        active.add(new ActiveBeam(center, world));
    }

    public static void tick(MinecraftServer server) {
        Iterator<ActiveBeam> iter = active.iterator();
        while (iter.hasNext()) {
            ActiveBeam beam = iter.next();
            beam.ticksAlive++;

            if (beam.ticksAlive > BEAM_DURATION) {
                // Fade-out burst
                beam.level.sendParticles(ParticleTypes.GLOW,
                        beam.center.x, beam.center.y + PILLAR_HEIGHT * 0.5, beam.center.z,
                        20, PILLAR_RADIUS * 0.6, PILLAR_HEIGHT * 0.3, PILLAR_RADIUS * 0.6, 0.02);
                iter.remove();
                continue;
            }

            // Vertical column: END_ROD particles sweeping up the pillar
            for (double y = 0.5; y <= PILLAR_HEIGHT; y += 1.5) {
                double jx = (beam.level.getRandom().nextDouble() - 0.5) * PILLAR_RADIUS * 0.8;
                double jz = (beam.level.getRandom().nextDouble() - 0.5) * PILLAR_RADIUS * 0.8;
                beam.level.sendParticles(ParticleTypes.END_ROD,
                        beam.center.x + jx, beam.center.y + y, beam.center.z + jz,
                        1, 0.05, 0.0, 0.05, 0.0);
            }

            // WAX_ON (golden) glow at the base
            if (beam.ticksAlive % 4 == 0) {
                beam.level.sendParticles(ParticleTypes.WAX_ON,
                        beam.center.x, beam.center.y + 0.5, beam.center.z,
                        8, PILLAR_RADIUS * 0.7, 0.3, PILLAR_RADIUS * 0.7, 0.1);
            }

            // Scattered GLOW particles throughout the pillar
            if (beam.ticksAlive % 3 == 0) {
                double gy = beam.level.getRandom().nextDouble() * PILLAR_HEIGHT;
                beam.level.sendParticles(ParticleTypes.GLOW,
                        beam.center.x, beam.center.y + gy, beam.center.z,
                        2, PILLAR_RADIUS * 0.35, 0.1, PILLAR_RADIUS * 0.35, 0.0);
            }

            // Electric smite sparks near the base every few ticks
            if (beam.ticksAlive % 6 == 0) {
                beam.level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                        beam.center.x, beam.center.y + 1.5, beam.center.z,
                        10, PILLAR_RADIUS * 0.5, 0.5, PILLAR_RADIUS * 0.5, 0.15);
            }

            // Beacon ambient sound midway through
            if (beam.ticksAlive == BEAM_DURATION / 2) {
                beam.level.playSound(null, beam.center.x, beam.center.y, beam.center.z,
                        SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 0.5F, 1.6F);
            }
        }
    }

    @Override
    public int cost() {
        return 6;
    }

    @Override
    public String displayName() {
        return "Sunbeam";
    }

    @Override
    public String description() {
        return "Summon a pillar of holy light that devastates undead mobs within";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.SUNBEAM);
    }

    // -------------------------------------------------------------------------

    private static class ActiveBeam {
        final Vec3 center;
        final ServerLevel level;
        int ticksAlive = 0;

        ActiveBeam(Vec3 center, ServerLevel level) {
            this.center = center;
            this.level = level;
        }
    }
}
