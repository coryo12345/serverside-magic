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
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

        // Hammer starts 1.5 blocks directly in front of the face, then swings in
        Vec3 startPos = faceCenter.add(
                face.getStepX() * 1.5,
                face.getStepY() * 1.5,
                face.getStepZ() * 1.5);

        // Spawn hammer as a single ItemDisplay using the custom spectral_hammer model
        ItemStack hammerItem = new ItemStack(Items.STICK);
        hammerItem.set(DataComponents.ITEM_MODEL,
                Identifier.fromNamespaceAndPath("servermagic", "spectral_hammer"));
        Display.ItemDisplay display = new Display.ItemDisplay(EntityType.ITEM_DISPLAY, world);
        display.setPos(startPos.x, startPos.y, startPos.z);
        display.setItemStack(hammerItem);
        display.setTransformation(computeSwingTransform(face, (float) (Math.PI / 2)));
        world.addFreshEntity(display);

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
                display.getUUID(),
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
                // Swing from 90° tilted-back to 0° at impact
                float swingAngle = (float) (Math.PI / 2) * (1.0f - eased);

                moveHammer(hammer, headPos, swingAngle);

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

    private static void moveHammer(ActiveHammer hammer, Vec3 pos, float swingAngle) {
        Entity entity = hammer.level.getEntity(hammer.displayId);
        if (entity instanceof Display.ItemDisplay display) {
            display.setPos(pos.x, pos.y, pos.z);
            display.setTransformation(computeSwingTransform(hammer.face, swingAngle));
        }
    }

    private static Transformation computeSwingTransform(Direction face, float swingAngle) {
        // Swing rotation in world space
        Vector3f swingAxis = getSwingAxis(face);
        Quaternionf swingRot = new Quaternionf().rotationAxis(swingAngle, swingAxis);
        // Base orientation: rotate 90° around the swing axis to orient the model
        Quaternionf baseRot = new Quaternionf().rotationAxis((float) (Math.PI / 2), new Vector3f(0, 1, 0));
        return new Transformation(new Vector3f(0, 0, 0), swingRot, new Vector3f(1, 1, 1), baseRot);
    }

    private static Vector3f getSwingAxis(Direction face) {
        if (face.getAxis() == Direction.Axis.Y) {
            return new Vector3f(1, 0, 0);
        }
        // Negated cross product so rotation swings the head forward into the face
        float nx = face.getStepX();
        float nz = face.getStepZ();
        return new Vector3f(nz, 0, -nx);
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
        Entity display = hammer.level.getEntity(hammer.displayId);
        if (display != null) display.discard();
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
        final UUID displayId;
        final UUID casterId;
        final ServerLevel level;
        final Vec3 startPos;
        final Vec3 targetPos;
        final BlockPos targetBlock;
        final Direction face;
        int ticksAlive = 0;

        ActiveHammer(UUID displayId, UUID casterId, ServerLevel level,
                Vec3 startPos, Vec3 targetPos, BlockPos targetBlock, Direction face) {
            this.displayId = displayId;
            this.casterId = casterId;
            this.level = level;
            this.startPos = startPos;
            this.targetPos = targetPos;
            this.targetBlock = targetBlock;
            this.face = face;
        }
    }
}
