package servermagic.spells;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.spells.utils.SpellUtils;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class IronMaiden extends BaseSpell {

    private static final int CAGE_DURATION = 40; // 2 seconds
    private static final float PIERCE_DAMAGE = 11.0f; // 5.5 hearts

    private static final List<ActiveCage> active = new ArrayList<>();

    public IronMaiden(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Optional<LivingEntity> targetOpt = SpellUtils.getFirstEntityInLineOfSight(player, 20.0);
        if (targetOpt.isEmpty()) {
            return;
        }
        LivingEntity target = targetOpt.get();

        // Don't cage an already-caged target
        if (active.stream().anyMatch(c -> c.targetId.equals(target.getUUID()))) {
            return;
        }

        Vec3 center = target.getBoundingBox().getCenter();
        List<BlockPos> placed = buildCage(target);
        if (placed.isEmpty()) {
            return;
        }

        // Iron bar particles erupting inward from all sides to form the cage
        world.sendParticles(
                new BlockParticleOption(ParticleTypes.BLOCK, Blocks.IRON_BARS.defaultBlockState()),
                center.x, center.y, center.z, 35, 1.2, 1.0, 1.2, 0.25);
        world.sendParticles(ParticleTypes.CRIT,
                center.x, center.y, center.z, 15, 0.8, 0.6, 0.8, 0.15);
        world.sendParticles(ParticleTypes.SMOKE,
                center.x, center.y, center.z, 10, 0.6, 0.5, 0.6, 0.04);

        world.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.IRON_GOLEM_STEP, SoundSource.PLAYERS, 1.4F, 0.6F);
        world.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 0.7F, 1.4F);

        active.add(new ActiveCage(target.getUUID(), world, placed, center));
    }

    /**
     * Builds a hollow cage of iron bars around the entity's bounding box.
     * Only places bars where the existing block is air.
     * Returns the list of block positions where bars were actually placed.
     */
    private List<BlockPos> buildCage(LivingEntity target) {
        AABB box = target.getBoundingBox();
        // Expand 1 block outward in all directions; bottom 1 below feet, top 1 above head
        int minX = (int) Math.floor(box.minX) - 1;
        int maxX = (int) Math.floor(box.maxX) + 1;
        int minY = (int) Math.floor(box.minY) - 1;
        int maxY = (int) Math.ceil(box.maxY) + 1;
        int minZ = (int) Math.floor(box.minZ) - 1;
        int maxZ = (int) Math.floor(box.maxZ) + 1;

        List<BlockPos> placed = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    // Only the hollow surface — skip fully interior positions
                    if (x != minX && x != maxX && y != minY && y != maxY && z != minZ && z != maxZ) {
                        continue;
                    }
                    BlockPos pos = new BlockPos(x, y, z);
                    if (world.getBlockState(pos).isAir()) {
                        world.setBlock(pos, Blocks.IRON_BARS.defaultBlockState(), 3);
                        placed.add(pos);
                    }
                }
            }
        }
        return placed;
    }

    public static void tick(MinecraftServer server) {
        Iterator<ActiveCage> iter = active.iterator();
        while (iter.hasNext()) {
            ActiveCage cage = iter.next();
            cage.ticksAlive++;

            // Ambient energy particles while the cage charges
            if (cage.ticksAlive % 5 == 0) {
                cage.level.sendParticles(ParticleTypes.CRIT,
                        cage.center.x, cage.center.y, cage.center.z,
                        4, 0.6, 0.5, 0.6, 0.08);
            }
            if (cage.ticksAlive % 8 == 0) {
                cage.level.sendParticles(ParticleTypes.SMOKE,
                        cage.center.x, cage.center.y, cage.center.z,
                        2, 0.4, 0.4, 0.4, 0.02);
            }

            if (cage.ticksAlive >= CAGE_DURATION) {
                releaseCage(cage);
                iter.remove();
            }
        }
    }

    private static void releaseCage(ActiveCage cage) {
        // Remove all placed bars
        for (BlockPos pos : cage.placedBlocks) {
            if (cage.level.getBlockState(pos).is(Blocks.IRON_BARS)) {
                cage.level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }

        // Burst particles — bars imploding inward and crit flash
        cage.level.sendParticles(
                new BlockParticleOption(ParticleTypes.BLOCK, Blocks.IRON_BARS.defaultBlockState()),
                cage.center.x, cage.center.y, cage.center.z,
                60, 0.9, 0.7, 0.9, 0.55);
        cage.level.sendParticles(ParticleTypes.CRIT,
                cage.center.x, cage.center.y, cage.center.z,
                50, 0.5, 0.5, 0.5, 0.5);
        cage.level.sendParticles(ParticleTypes.LARGE_SMOKE,
                cage.center.x, cage.center.y, cage.center.z,
                20, 0.6, 0.5, 0.6, 0.08);
        cage.level.sendParticles(ParticleTypes.SWEEP_ATTACK,
                cage.center.x, cage.center.y, cage.center.z,
                6, 0.5, 0.3, 0.5, 0.12);

        cage.level.playSound(null, cage.center.x, cage.center.y, cage.center.z,
                SoundEvents.IRON_GOLEM_ATTACK, SoundSource.PLAYERS, 1.5F, 0.7F);
        cage.level.playSound(null, cage.center.x, cage.center.y, cage.center.z,
                SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0F, 0.9F);

        // Apply piercing damage
        Entity entity = cage.level.getEntity(cage.targetId);
        if (entity instanceof LivingEntity target && target.isAlive()) {
            target.hurtServer(cage.level, target.damageSources().magic(), PIERCE_DAMAGE);
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
        return "Iron Maiden";
    }

    @Override
    public String description() {
        return "Encase a target in iron bars for 2 seconds, then crush them with a burst of piercing damage";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.IRON_MAIDEN);
    }

    // -------------------------------------------------------------------------

    private static class ActiveCage {
        final UUID targetId;
        final ServerLevel level;
        final List<BlockPos> placedBlocks;
        final Vec3 center;
        int ticksAlive = 0;

        ActiveCage(UUID targetId, ServerLevel level, List<BlockPos> placedBlocks, Vec3 center) {
            this.targetId = targetId;
            this.level = level;
            this.placedBlocks = placedBlocks;
            this.center = center;
        }
    }
}
