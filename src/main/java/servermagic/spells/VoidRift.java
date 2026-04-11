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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class VoidRift extends BaseSpell {

    private static final List<ActiveSkull> active = new ArrayList<>();

    public VoidRift(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Vec3 look = player.getLookAngle();
        Vec3 startPos = player.getEyePosition().add(look.scale(1.2));

        // Slow-moving skull: small power vector = low terminal velocity; also set
        // initial delta movement so it doesn't start stationary.
        WitherSkull skull = new WitherSkull(world, player, look.scale(0.12));
        skull.setPos(startPos.x, startPos.y, startPos.z);
        skull.setDeltaMovement(look.scale(0.28));
        world.addFreshEntity(skull);

        active.add(new ActiveSkull(skull.getUUID(), player.getUUID(), world, startPos));

        // Cast sound + dark particle burst
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1.0F, 0.6F);
        world.sendParticles(ParticleTypes.SQUID_INK,
                startPos.x, startPos.y, startPos.z, 20, 0.25, 0.25, 0.25, 0.08);
        world.sendParticles(ParticleTypes.LARGE_SMOKE,
                startPos.x, startPos.y, startPos.z, 12, 0.2, 0.2, 0.2, 0.04);
        world.sendParticles(ParticleTypes.SCULK_SOUL,
                startPos.x, startPos.y, startPos.z, 6, 0.3, 0.3, 0.3, 0.05);
    }

    /**
     * Call once per server tick via ServerTickEvents.END_SERVER_TICK.
     * Handles trail particles, hit detection, and impact effects.
     */
    public static void tick(MinecraftServer server) {
        Iterator<ActiveSkull> iter = active.iterator();
        while (iter.hasNext()) {
            ActiveSkull entry = iter.next();
            if (tickSkull(entry)) {
                iter.remove();
            }
        }
    }

    private static boolean tickSkull(ActiveSkull entry) {
        entry.ticksAlive++;
        Entity skull = entry.level.getEntity(entry.skullId);

        if (skull == null || !skull.isAlive() || entry.ticksAlive > 200) {
            // Skull hit something or timed out — apply impact effects at last known position
            if (skull == null || !skull.isAlive()) {
                onImpact(entry);
            }
            return true;
        }

        // Update last known position for when we need impact location
        entry.lastPos = skull.position();

        // Dark particle trail every tick
        Vec3 pos = skull.position();
        entry.level.sendParticles(ParticleTypes.SQUID_INK,
                pos.x, pos.y, pos.z, 3, 0.1, 0.1, 0.1, 0.02);
        entry.level.sendParticles(ParticleTypes.LARGE_SMOKE,
                pos.x, pos.y, pos.z, 2, 0.08, 0.08, 0.08, 0.0);

        // Soul particles every other tick for a more ethereal effect
        if (entry.ticksAlive % 2 == 0) {
            entry.level.sendParticles(ParticleTypes.SOUL,
                    pos.x, pos.y, pos.z, 1, 0.12, 0.12, 0.12, 0.01);
        }

        return false;
    }

    private static void onImpact(ActiveSkull entry) {
        Vec3 pos = entry.lastPos;

        // Massive dark particle explosion at impact
        entry.level.sendParticles(ParticleTypes.SQUID_INK,
                pos.x, pos.y, pos.z, 60, 0.5, 0.5, 0.5, 0.15);
        entry.level.sendParticles(ParticleTypes.LARGE_SMOKE,
                pos.x, pos.y, pos.z, 30, 0.6, 0.6, 0.6, 0.08);
        entry.level.sendParticles(ParticleTypes.SOUL,
                pos.x, pos.y, pos.z, 20, 0.4, 0.6, 0.4, 0.12);
        entry.level.sendParticles(ParticleTypes.SCULK_SOUL,
                pos.x, pos.y, pos.z, 15, 0.5, 0.5, 0.5, 0.1);
        entry.level.sendParticles(ParticleTypes.ASH,
                pos.x, pos.y, pos.z, 40, 0.8, 0.5, 0.8, 0.06);

        entry.level.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.WITHER_DEATH, SoundSource.HOSTILE, 0.6F, 1.5F);

        // Apply Blindness (3 s) and Wither II (3 s) to nearby entities
        AABB blastArea = AABB.ofSize(pos, 6, 6, 6);
        List<LivingEntity> targets = entry.level.getEntitiesOfClass(LivingEntity.class, blastArea,
                e -> e.isAlive() && !e.getUUID().equals(entry.casterId));

        for (LivingEntity target : targets) {
            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0));
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 1));
        }
    }

    @Override

    public int getFlatXpCost() {
        return 75;
    }

    public double getLevelPercentCost() {
        return 1.0;
    }
    @Override
    public String displayName() {
        return "Void Rift";
    }

    @Override
    public String description() {
        return "Fire a slow-moving void skull that explodes into darkness, blinding and withering nearby enemies";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.VOID_RIFT);
    }

    // -------------------------------------------------------------------------

    private static class ActiveSkull {
        final UUID skullId;
        final UUID casterId;
        final ServerLevel level;
        Vec3 lastPos;
        int ticksAlive = 0;

        ActiveSkull(UUID skullId, UUID casterId, ServerLevel level, Vec3 startPos) {
            this.skullId = skullId;
            this.casterId = casterId;
            this.level = level;
            this.lastPos = startPos;
        }
    }
}
