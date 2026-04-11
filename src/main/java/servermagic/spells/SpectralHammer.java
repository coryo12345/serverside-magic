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
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class SpectralHammer extends BaseSpell {

    private static final int SWING_TICKS = 18;
    private static final float IMPACT_DAMAGE = 8.0f;

    private static final List<ActiveHammer> active = new ArrayList<>();

    public SpectralHammer(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        HitResult hit = player.pick(20.0, 0f, false);
        if (hit.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockHitResult blockHit = (BlockHitResult) hit;
        BlockPos targetBlock = blockHit.getBlockPos();
        Direction face = blockHit.getDirection();

        // Center of the targeted block face surface
        Vec3 faceCenter = Vec3.atCenterOf(targetBlock).add(
                face.getStepX() * 0.5,
                face.getStepY() * 0.5,
                face.getStepZ() * 0.5);

        // Hammer starts in front of and above the face, then swings in
        Vec3 startPos;
        if (face.getAxis() == Direction.Axis.Y) {
            startPos = faceCenter.add(0, 4.5, 0);
        } else {
            startPos = faceCenter.add(
                    face.getStepX() * 2.5,
                    3.0,
                    face.getStepZ() * 2.5);
        }

        // Spawn hammer head (iron block, wide and short)
        Display.BlockDisplay head = new Display.BlockDisplay(EntityType.BLOCK_DISPLAY, world);
        head.setPos(startPos.x, startPos.y, startPos.z);
        head.setBlockState(Blocks.IRON_BLOCK.defaultBlockState());
        head.setTransformation(new Transformation(
                new Vector3f(-0.45f, -0.25f, -0.45f),
                new Quaternionf(),
                new Vector3f(0.9f, 0.5f, 0.9f),
                new Quaternionf()));
        world.addFreshEntity(head);

        // Spawn hammer handle (polished blackstone, thin vertical rod trailing behind)
        Vec3 trailDir = computeTrailDir(startPos, faceCenter);
        Vec3 handleStartPos = startPos.add(trailDir.scale(0.65));

        Display.BlockDisplay handle = new Display.BlockDisplay(EntityType.BLOCK_DISPLAY, world);
        handle.setPos(handleStartPos.x, handleStartPos.y, handleStartPos.z);
        handle.setBlockState(Blocks.POLISHED_BLACKSTONE.defaultBlockState());
        handle.setTransformation(new Transformation(
                new Vector3f(-0.1f, -0.6f, -0.1f),
                new Quaternionf(),
                new Vector3f(0.2f, 1.2f, 0.2f),
                new Quaternionf()));
        world.addFreshEntity(handle);

        // Summon effects
        world.sendParticles(ParticleTypes.ENCHANTED_HIT,
                startPos.x, startPos.y, startPos.z, 20, 0.5, 0.5, 0.5, 0.15);
        world.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                startPos.x, startPos.y, startPos.z, 8, 0.4, 0.4, 0.4, 0.04);

        world.playSound(null, startPos.x, startPos.y, startPos.z,
                SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 0.8F);
        world.playSound(null, startPos.x, startPos.y, startPos.z,
                SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 0.7F, 0.5F);

        active.add(new ActiveHammer(
                head.getUUID(),
                handle.getUUID(),
                player.getUUID(),
                world,
                startPos,
                faceCenter,
                targetBlock,
                face));
    }

    public static void tick(MinecraftServer server) {
        Iterator<ActiveHammer> iter = active.iterator();
        while (iter.hasNext()) {
            ActiveHammer hammer = iter.next();
            hammer.ticksAlive++;

            if (hammer.ticksAlive > SWING_TICKS + 8) {
                discardHammer(hammer);
                iter.remove();
                continue;
            }

            if (hammer.ticksAlive <= SWING_TICKS) {
                // Quadratic ease-in so the hammer accelerates into the target
                float t = (float) hammer.ticksAlive / SWING_TICKS;
                float eased = t * t;

                Vec3 headPos = lerp(hammer.startPos, hammer.targetPos, eased);
                Vec3 trailDir = computeTrailDir(hammer.startPos, hammer.targetPos);
                Vec3 handlePos = headPos.add(trailDir.scale(0.65));

                moveHammer(hammer, headPos, handlePos);

                // Swing trail particles
                hammer.level.sendParticles(ParticleTypes.ENCHANTED_HIT,
                        headPos.x, headPos.y, headPos.z, 3, 0.2, 0.2, 0.2, 0.1);
                if (hammer.ticksAlive % 3 == 0) {
                    hammer.level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                            headPos.x, headPos.y, headPos.z, 1, 0.15, 0.15, 0.15, 0.02);
                }

                // Whoosh halfway through swing
                if (hammer.ticksAlive == SWING_TICKS / 2) {
                    hammer.level.playSound(null, headPos.x, headPos.y, headPos.z,
                            SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.2F, 0.7F);
                }

                if (hammer.ticksAlive == SWING_TICKS) {
                    onImpact(hammer);
                }
            }
        }
    }

    private static void moveHammer(ActiveHammer hammer, Vec3 headPos, Vec3 handlePos) {
        Entity head = hammer.level.getEntity(hammer.headId);
        Entity handle = hammer.level.getEntity(hammer.handleId);
        if (head != null) head.setPos(headPos.x, headPos.y, headPos.z);
        if (handle != null) handle.setPos(handlePos.x, handlePos.y, handlePos.z);
    }

    private static void onImpact(ActiveHammer hammer) {
        Vec3 pos = hammer.targetPos;

        // Impact particle burst
        hammer.level.sendParticles(ParticleTypes.ENCHANTED_HIT,
                pos.x, pos.y, pos.z, 60, 1.0, 0.8, 1.0, 0.4);
        hammer.level.sendParticles(ParticleTypes.CRIT,
                pos.x, pos.y, pos.z, 40, 0.8, 0.6, 0.8, 0.3);
        hammer.level.sendParticles(ParticleTypes.LARGE_SMOKE,
                pos.x, pos.y, pos.z, 12, 0.5, 0.3, 0.5, 0.06);
        hammer.level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                pos.x, pos.y, pos.z, 15, 0.6, 0.4, 0.6, 0.1);

        hammer.level.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.IRON_GOLEM_ATTACK, SoundSource.PLAYERS, 1.5F, 0.5F);
        hammer.level.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.2F, 0.7F);
        hammer.level.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, SoundSource.PLAYERS, 1.0F, 0.6F);

        // Break 3x3x1 face of blocks
        Entity breaker = hammer.level.getEntity(hammer.casterId);
        for (BlockPos bp : get3x3x1Blocks(hammer.targetBlock, hammer.face)) {
            BlockState state = hammer.level.getBlockState(bp);
            if (!state.isAir()
                    && state.getFluidState().isEmpty()
                    && state.getDestroySpeed(hammer.level, bp) >= 0) {
                hammer.level.sendParticles(
                        new BlockParticleOption(ParticleTypes.BLOCK, state),
                        bp.getX() + 0.5, bp.getY() + 0.5, bp.getZ() + 0.5,
                        6, 0.3, 0.3, 0.3, 0.12);
                hammer.level.destroyBlock(bp, true, breaker);
            }
        }

        // Damage nearby mobs
        AABB hitBox = AABB.ofSize(pos, 4.5, 4.5, 4.5);
        hammer.level.getEntitiesOfClass(LivingEntity.class, hitBox,
                e -> e.isAlive() && !e.getUUID().equals(hammer.casterId))
                .forEach(e -> e.hurtServer(hammer.level, e.damageSources().magic(), IMPACT_DAMAGE));
    }

    /**
     * Returns the 9 block positions forming a 3×3 slice of the face at center.
     * The slice is 1 block deep along the face's normal axis, 3 in each
     * perpendicular direction.
     */
    private static List<BlockPos> get3x3x1Blocks(BlockPos center, Direction face) {
        List<BlockPos> blocks = new ArrayList<>();
        Direction.Axis depth = face.getAxis();
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                BlockPos pos = switch (depth) {
                    case X -> center.offset(0, b, a); // 3 wide (Z), 3 high (Y)
                    case Z -> center.offset(a, b, 0); // 3 wide (X), 3 high (Y)
                    case Y -> center.offset(a, 0, b); // 3 wide (X), 3 deep (Z)
                };
                blocks.add(pos);
            }
        }
        return blocks;
    }

    private static void discardHammer(ActiveHammer hammer) {
        Entity head = hammer.level.getEntity(hammer.headId);
        Entity handle = hammer.level.getEntity(hammer.handleId);
        if (head != null) head.discard();
        if (handle != null) handle.discard();
    }

    private static Vec3 computeTrailDir(Vec3 from, Vec3 to) {
        Vec3 diff = from.subtract(to);
        double len = diff.length();
        return len > 1e-6 ? diff.scale(1.0 / len) : new Vec3(0, 1, 0);
    }

    private static Vec3 lerp(Vec3 from, Vec3 to, float t) {
        return new Vec3(
                from.x + (to.x - from.x) * t,
                from.y + (to.y - from.y) * t,
                from.z + (to.z - from.z) * t);
    }

    @Override

    public int getFlatXpCost() {
        return 1;
    }

    public double getLevelPercentCost() {
        return 0.05;
    }
    @Override
    public String displayName() {
        return "Spectral Hammer";
    }

    @Override
    public String description() {
        return "Summon a spectral hammer that smashes your targeted block face, destroying a 3×3 area";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.SPECTRAL_HAMMER);
    }

    // -------------------------------------------------------------------------

    private static class ActiveHammer {
        final UUID headId;
        final UUID handleId;
        final UUID casterId;
        final ServerLevel level;
        final Vec3 startPos;
        final Vec3 targetPos;
        final BlockPos targetBlock;
        final Direction face;
        int ticksAlive = 0;

        ActiveHammer(UUID headId, UUID handleId, UUID casterId, ServerLevel level,
                Vec3 startPos, Vec3 targetPos, BlockPos targetBlock, Direction face) {
            this.headId = headId;
            this.handleId = handleId;
            this.casterId = casterId;
            this.level = level;
            this.startPos = startPos;
            this.targetPos = targetPos;
            this.targetBlock = targetBlock;
            this.face = face;
        }
    }
}
