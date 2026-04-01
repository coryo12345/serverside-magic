package servermagic.spells;

import java.util.Optional;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class EarthenSpike extends BaseSpell {

    private static final int FANG_COUNT = 8;
    private static final double SPACING = 1.25;

    public EarthenSpike(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Vec3 look = player.getLookAngle();

        // Flatten to horizontal and normalize
        double dx = look.x;
        double dz = look.z;
        double horizLen = Math.sqrt(dx * dx + dz * dz);
        if (horizLen > 1e-6) {
            dx /= horizLen;
            dz /= horizLen;
        } else {
            // Player is looking straight up/down — default to south
            dx = 0;
            dz = 1;
        }

        // EvokerFang yaw: atan2(dz, dx) in degrees (world-space angle from +X axis,
        // matching how vanilla Evoker spawns its fangs)
        float fangYaw = (float) (Math.atan2(dz, dx) * (180.0 / Math.PI));

        Vec3 origin = player.position();

        for (int i = 0; i < FANG_COUNT; i++) {
            double fangX = origin.x + dx * SPACING * (i + 1);
            double fangZ = origin.z + dz * SPACING * (i + 1);
            // Place each fang on the ground surface
            double fangY = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) fangX, (int) fangZ);

            // Staggered warmup so fangs erupt in sequence (2 ticks apart)
            EvokerFangs fang = new EvokerFangs(world, fangX, fangY, fangZ, fangYaw, i * 2, player);
            world.addFreshEntity(fang);

            // Earthy burst particles at each spike position
            world.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, Blocks.DIRT.defaultBlockState()),
                    fangX, fangY + 0.1, fangZ,
                    14, 0.25, 0.3, 0.25, 0.22);
            world.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, Blocks.STONE.defaultBlockState()),
                    fangX, fangY + 0.1, fangZ,
                    8, 0.2, 0.25, 0.2, 0.18);
            world.sendParticles(ParticleTypes.SMOKE,
                    fangX, fangY + 0.2, fangZ,
                    5, 0.15, 0.1, 0.15, 0.04);
        }

        // Rumble sound at player + heavy cast sound
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 0.6F);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 1.0F, 0.5F);
    }

    @Override
    public int cost() {
        return 3;
    }

    @Override
    public String displayName() {
        return "Earthen Spike";
    }

    @Override
    public String description() {
        return "Summon a line of earth spikes that erupt from the ground in the direction you are facing";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.EARTHEN_SPIKE);
    }
}
