package servermagic.spells;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mojang.math.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class MeteorShower extends BaseSpell {

    private static final double FALL_SPEED = 0.8;
    private static final int SPAWN_HEIGHT = 28;
    private static final double SCATTER_RADIUS = 4.0;
    private static final float IMPACT_DAMAGE = 8.0f;

    private static final List<ActiveMeteor> active = new ArrayList<>();

    public MeteorShower(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        // Determine target center from player look
        Vec3 target;
        HitResult hit = player.pick(25.0, 0f, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos bp = ((BlockHitResult) hit).getBlockPos();
            target = new Vec3(bp.getX() + 0.5, bp.getY(), bp.getZ() + 0.5);
        } else {
            // Looking at sky or too far — scatter around player
            target = player.position();
        }

        int count = 4 + world.getRandom().nextInt(3); // 4, 5, or 6 meteors
        for (int i = 0; i < count; i++) {
            double ox = world.getRandom().nextDouble() * SCATTER_RADIUS * 2 - SCATTER_RADIUS;
            double oz = world.getRandom().nextDouble() * SCATTER_RADIUS * 2 - SCATTER_RADIUS;
            double x = target.x + ox;
            double z = target.z + oz;
            int groundY = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) x, (int) z);
            double spawnY = groundY + SPAWN_HEIGHT;

            Display.BlockDisplay meteor = new Display.BlockDisplay(EntityType.BLOCK_DISPLAY, world);
            meteor.setPos(x, spawnY, z);
            meteor.setBlockState(Blocks.MAGMA_BLOCK.defaultBlockState());
            // 2× scale, centered on entity position
            meteor.setTransformation(new Transformation(
                    new Vector3f(-1f, -1f, -1f),
                    new Quaternionf(),
                    new Vector3f(2f, 2f, 2f),
                    new Quaternionf()));
            world.addFreshEntity(meteor);

            active.add(new ActiveMeteor(meteor.getUUID(), player.getUUID(), world,
                    new Vec3(x, spawnY, z), groundY));
        }

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 0.5F);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.7F, 0.6F);
    }

    public static void tick(MinecraftServer server) {
        Iterator<ActiveMeteor> iter = active.iterator();
        while (iter.hasNext()) {
            if (tickMeteor(iter.next())) {
                iter.remove();
            }
        }
    }

    private static boolean tickMeteor(ActiveMeteor meteor) {
        meteor.ticksAlive++;

        Entity display = meteor.level.getEntity(meteor.displayId);

        if (meteor.ticksAlive > 200 || display == null || !display.isAlive()) {
            if (display != null && display.isAlive()) {
                display.discard();
            }
            return true;
        }

        // Move down and sync entity position
        meteor.pos = new Vec3(meteor.pos.x, meteor.pos.y - FALL_SPEED, meteor.pos.z);
        display.setPos(meteor.pos.x, meteor.pos.y, meteor.pos.z);

        // Fire trail
        Vec3 p = meteor.pos;
        meteor.level.sendParticles(ParticleTypes.FLAME,
                p.x, p.y + 1.0, p.z, 3, 0.3, 0.3, 0.3, 0.05);
        meteor.level.sendParticles(ParticleTypes.LARGE_SMOKE,
                p.x, p.y + 1.0, p.z, 2, 0.2, 0.2, 0.2, 0.02);
        if (meteor.ticksAlive % 2 == 0) {
            meteor.level.sendParticles(ParticleTypes.LAVA,
                    p.x, p.y + 1.0, p.z, 1, 0.4, 0.4, 0.4, 0.0);
        }
        if (meteor.ticksAlive % 3 == 0) {
            meteor.level.sendParticles(ParticleTypes.SMALL_FLAME,
                    p.x, p.y + 1.0, p.z, 2, 0.3, 0.3, 0.3, 0.02);
        }

        // Check for ground impact
        if (meteor.pos.y <= meteor.groundY + 1.0) {
            onImpact(meteor);
            display.discard();
            return true;
        }

        return false;
    }

    private static void onImpact(ActiveMeteor meteor) {
        Vec3 pos = meteor.pos;

        // Impact particle burst
        meteor.level.sendParticles(ParticleTypes.LAVA,
                pos.x, pos.y, pos.z, 25, 1.0, 0.5, 1.0, 0.3);
        meteor.level.sendParticles(ParticleTypes.FLAME,
                pos.x, pos.y, pos.z, 40, 1.5, 0.3, 1.5, 0.2);
        meteor.level.sendParticles(ParticleTypes.LARGE_SMOKE,
                pos.x, pos.y, pos.z, 15, 0.8, 0.5, 0.8, 0.05);
        meteor.level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                pos.x, pos.y + 1.0, pos.z, 8, 0.5, 0.2, 0.5, 0.04);

        meteor.level.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.5F, 0.7F);
        meteor.level.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 0.8F, 0.8F);

        // Damage and ignite mobs in blast radius
        AABB blastArea = AABB.ofSize(pos, 7.0, 4.0, 7.0);
        List<LivingEntity> targets = meteor.level.getEntitiesOfClass(LivingEntity.class, blastArea,
                e -> e.isAlive() && !e.getUUID().equals(meteor.casterId));
        for (LivingEntity target : targets) {
            target.hurtServer(meteor.level, target.damageSources().magic(), IMPACT_DAMAGE);
            target.igniteForTicks(100);
        }
    }

    @Override
    public int cost() {
        return 7;
    }

    @Override
    public String displayName() {
        return "Meteor Shower";
    }

    @Override
    public String description() {
        return "Call down a shower of blazing meteors upon a target location";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.METEOR_SHOWER);
    }

    // -------------------------------------------------------------------------

    private static class ActiveMeteor {
        final UUID displayId;
        final UUID casterId;
        final ServerLevel level;
        Vec3 pos;
        final int groundY;
        int ticksAlive = 0;

        ActiveMeteor(UUID displayId, UUID casterId, ServerLevel level, Vec3 pos, int groundY) {
            this.displayId = displayId;
            this.casterId = casterId;
            this.level = level;
            this.pos = pos;
            this.groundY = groundY;
        }
    }
}
