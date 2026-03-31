package servermagic.spells.freeze;

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
import servermagic.entitybinding.EntityBindingManager;

public class FreezeProjectileManager {

    private static final List<FreezeProjectile> active = new ArrayList<>();

    /**
     * Register a new in-flight freeze projectile for per-tick tracking.
     * Call immediately after addFreshEntity for both snowball and ice block.
     */
    public static void register(UUID snowballId, UUID iceBlockId, UUID casterId, ServerLevel level) {
        active.add(new FreezeProjectile(snowballId, iceBlockId, casterId, level));
    }

    /**
     * Call once per server tick via ServerTickEvents.END_SERVER_TICK.
     */
    public static void tick(MinecraftServer server) {
        Iterator<FreezeProjectile> iter = active.iterator();
        while (iter.hasNext()) {
            FreezeProjectile proj = iter.next();
            if (tickProjectile(proj)) {
                iter.remove();
            }
        }
    }

    /**
     * Returns true when the projectile should be removed from the active list.
     */
    private static boolean tickProjectile(FreezeProjectile proj) {
        proj.ticksAlive++;

        Entity snowball = proj.level.getEntity(proj.snowballId);
        Entity iceBlock = proj.level.getEntity(proj.iceBlockId);

        // Snowball hit something or left the world, or timed out (~5 seconds)
        if (snowball == null || !snowball.isAlive() || proj.ticksAlive > 100) {
            cleanup(proj.level, iceBlock, proj.iceBlockId);
            return true;
        }

        // Check for a living entity overlapping the snowball's hit sphere
        AABB hitBox = snowball.getBoundingBox().inflate(0.4);
        List<LivingEntity> hits = proj.level.getEntitiesOfClass(
                LivingEntity.class,
                hitBox,
                e -> e.isAlive() && !e.getUUID().equals(proj.casterId));

        if (hits.isEmpty()) {
            return false;
        }

        LivingEntity target = hits.get(0);

        // Freeze for ~5 seconds: ticksFrozen decrements by 2/tick outside powdered snow,
        // so 300 / 2 = 150 ticks until thaw. Full-freeze visuals require >= 140 ticks.
        target.setTicksFrozen(300);

        // 1 heart of magic damage
        target.hurtServer(proj.level, target.damageSources().magic(), 2.0f);

        // Visual + audio feedback at target
        proj.level.sendParticles(
                ParticleTypes.SNOWFLAKE,
                target.getX(), target.getY() + target.getBbHeight() / 2.0, target.getZ(),
                30, 0.3, 0.4, 0.3, 0.05);
        proj.level.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 0.8F, 1.5F);

        snowball.discard();
        cleanup(proj.level, iceBlock, proj.iceBlockId);
        return true;
    }

    private static void cleanup(ServerLevel level, Entity iceBlock, UUID iceBlockId) {
        if (iceBlock != null && iceBlock.isAlive()) {
            iceBlock.discard();
        }
        EntityBindingManager.getOrCreate(level).removeBinding(iceBlockId);
    }

    // -------------------------------------------------------------------------

    private static class FreezeProjectile {
        final UUID snowballId;
        final UUID iceBlockId;
        final UUID casterId;
        final ServerLevel level;
        int ticksAlive = 0;

        FreezeProjectile(UUID snowballId, UUID iceBlockId, UUID casterId, ServerLevel level) {
            this.snowballId = snowballId;
            this.iceBlockId = iceBlockId;
            this.casterId = casterId;
            this.level = level;
        }
    }
}
