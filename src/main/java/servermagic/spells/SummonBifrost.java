package servermagic.spells;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.db.tables.PlayerSpellConfig;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class SummonBifrost extends BaseSpell {

    private static final int TELEPORT_DELAY = 60;
    private static final int MIN_SPACE_ABOVE = 16;
    private static final double AURA_RADIUS = 2.0;
    private static final float PILLAR_HEIGHT = 20.0f;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Random RNG = new Random();

    // Pastel rainbow colors — each hue blended ~50% toward white
    private static final int[] RAINBOW_COLORS = {
        0xFF8080, // pastel red
        0xFFC080, // pastel orange
        0xFFFF80, // pastel yellow
        0x80FF80, // pastel green
        0x8080FF, // pastel blue
        0xA580C0, // pastel indigo
        0xC580FF, // pastel violet
    };

    private static final List<ActiveBifrost> active = new ArrayList<>();

    public SummonBifrost(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        String username = player.getPlainTextName();

        Optional<PlayerSpellConfig> cfg = PlayerSpellConfig.GetConfigForPlayer(db, username, "SetBifrostPoint");
        if (cfg.isEmpty()) {
            player.sendSystemMessage(Component.literal("No Bifrost point set. Cast 'Set Bifrost Point' first."));
            return;
        }

        SetBifrostPoint.BifrostData data;
        try {
            data = MAPPER.readValue(cfg.get().config, SetBifrostPoint.BifrostData.class);
        } catch (Exception e) {
            player.sendSystemMessage(Component.literal("Bifrost point data is corrupted."));
            return;
        }

        ServerLevel targetWorld = world.getServer().getLevel(
                ResourceKey.create(Registries.DIMENSION, Identifier.parse(data.dimension)));
        if (targetWorld == null) {
            player.sendSystemMessage(Component.literal("Bifrost destination is unreachable."));
            return;
        }

        if (!hasSpaceAbove(world, player)) {
            player.sendSystemMessage(Component.literal("The Bifrost cannot descend here. Clear the sky above you!"));
            return;
        }

        Vec3 castPos = player.position();
        active.add(new ActiveBifrost(
                player.getUUID(), world, castPos,
                targetWorld, data.x, data.y, data.z, data.yaw, data.pitch));

        world.playSound(null, castPos.x, castPos.y, castPos.z,
                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.2F, 0.8F);
        world.playSound(null, castPos.x, castPos.y, castPos.z,
                SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 0.8F, 1.2F);
    }

    private static boolean hasSpaceAbove(ServerLevel world, ServerPlayer player) {
        BlockPos base = player.blockPosition().above(2);
        for (int i = 0; i < MIN_SPACE_ABOVE; i++) {
            var state = world.getBlockState(base.above(i));
            if (!state.isAir() && !state.is(Blocks.BEDROCK)) {
                return false;
            }
        }
        return true;
    }

    public static void tick(MinecraftServer server) {
        Iterator<ActiveBifrost> iter = active.iterator();
        while (iter.hasNext()) {
            ActiveBifrost entry = iter.next();
            entry.ticksAlive++;

            if (entry.ticksAlive > TELEPORT_DELAY + 5) {
                iter.remove();
                continue;
            }

            ServerPlayer player = server.getPlayerList().getPlayer(entry.playerId);
            if (player == null) {
                iter.remove();
                continue;
            }

            if (entry.ticksAlive <= TELEPORT_DELAY) {
                spawnPillarFrame(entry);
            }

            if (entry.ticksAlive == TELEPORT_DELAY) {
                performTeleport(player, entry);
                iter.remove();
            }
        }
    }

    private static void spawnPillarFrame(ActiveBifrost entry) {
        double cx = entry.castPos.x;
        double cy = entry.castPos.y;
        double cz = entry.castPos.z;
        double rotOffset = entry.ticksAlive * 0.18;

        for (float h = 0.0f; h <= PILLAR_HEIGHT; h += 0.5f) {
            double radius = 1.3 * Math.exp(-h * 0.12);
            if (radius < 0.05) break;

            int numPts = Math.max(2, 6 - (int)(h / 3.5f));

            for (int i = 0; i < numPts; i++) {
                double angle = (2 * Math.PI / numPts) * i + rotOffset + h * 0.3;
                double px = cx + Math.cos(angle) * radius + (RNG.nextDouble() - 0.5) * 0.08;
                double py = cy + h;
                double pz = cz + Math.sin(angle) * radius + (RNG.nextDouble() - 0.5) * 0.08;

                int roll = RNG.nextInt(10);
                if (roll < 3) {
                    // 30% white END_ROD anchor sparkles
                    entry.castLevel.sendParticles(ParticleTypes.END_ROD, px, py, pz, 1, 0.0, 0.0, 0.0, 0.0);
                } else if (roll < 4) {
                    // 10% soft white CLOUD haze
                    entry.castLevel.sendParticles(ParticleTypes.CLOUD, px, py, pz, 1, 0.0, 0.0, 0.0, 0.0);
                } else {
                    // 60% rainbow dust — random color per particle so no single color dominates
                    int color = RAINBOW_COLORS[RNG.nextInt(RAINBOW_COLORS.length)];
                    entry.castLevel.sendParticles(
                            new DustParticleOptions(color, 1.0f),
                            px, py, pz, 1, 0.0, 0.0, 0.0, 0.0);
                }
            }
        }

        // Random rainbow sparkles scattered throughout the pillar column
        for (int s = 0; s < 10; s++) {
            double sAngle = RNG.nextDouble() * 2 * Math.PI;
            double sRadius = RNG.nextDouble() * 0.8;
            double sH = RNG.nextDouble() * PILLAR_HEIGHT;
            int color = RAINBOW_COLORS[RNG.nextInt(RAINBOW_COLORS.length)];
            entry.castLevel.sendParticles(
                    new DustParticleOptions(color, 0.9f),
                    cx + Math.cos(sAngle) * sRadius,
                    cy + sH,
                    cz + Math.sin(sAngle) * sRadius,
                    1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    private static void performTeleport(ServerPlayer player, ActiveBifrost entry) {
        Vec3 castPos = entry.castPos;

        // Gather nearby living entities before the player teleports away
        AABB area = AABB.ofSize(castPos, AURA_RADIUS * 2, AURA_RADIUS * 2, AURA_RADIUS * 2);
        List<LivingEntity> nearby = entry.castLevel.getEntitiesOfClass(LivingEntity.class, area,
                e -> !e.getUUID().equals(entry.playerId) && e.distanceTo(player) <= AURA_RADIUS);

        player.teleportTo(entry.targetWorld,
                entry.targetX, entry.targetY, entry.targetZ,
                Set.of(), entry.targetYaw, entry.targetPitch, false);

        for (LivingEntity entity : nearby) {
            entity.teleportTo(entry.targetWorld,
                    entry.targetX, entry.targetY, entry.targetZ,
                    Set.of(), entity.getYRot(), entity.getXRot(), false);
        }

        // Arrival effects at destination
        entry.targetWorld.playSound(null, entry.targetX, entry.targetY, entry.targetZ,
                SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.2F, 0.9F);
        entry.targetWorld.playSound(null, entry.targetX, entry.targetY, entry.targetZ,
                SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 0.8F, 1.3F);

        entry.targetWorld.sendParticles(ParticleTypes.END_ROD,
                entry.targetX, entry.targetY + 1.0, entry.targetZ, 40, 0.5, 0.8, 0.5, 0.06);
        for (int color : RAINBOW_COLORS) {
            entry.targetWorld.sendParticles(
                    new DustParticleOptions(color, 1.0f),
                    entry.targetX, entry.targetY + 1.0, entry.targetZ,
                    3, 0.6, 0.6, 0.6, 0.04);
        }
    }

    @Override
    public int getFlatXpCost() {
        return 20;
    }

    @Override
    public double getLevelPercentCost() {
        return 0.40;
    }

    @Override
    public String displayName() {
        return "Summon Bifrost";
    }

    @Override
    public String description() {
        return "Open the rainbow bridge and return to your anchored Bifrost point, carrying nearby entities with you";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.BIFROST);
    }

    private static class ActiveBifrost {
        final UUID playerId;
        final ServerLevel castLevel;
        final Vec3 castPos;
        final ServerLevel targetWorld;
        final double targetX, targetY, targetZ;
        final float targetYaw, targetPitch;
        int ticksAlive = 0;

        ActiveBifrost(UUID playerId, ServerLevel castLevel, Vec3 castPos,
                      ServerLevel targetWorld, double targetX, double targetY, double targetZ,
                      float targetYaw, float targetPitch) {
            this.playerId = playerId;
            this.castLevel = castLevel;
            this.castPos = castPos;
            this.targetWorld = targetWorld;
            this.targetX = targetX;
            this.targetY = targetY;
            this.targetZ = targetZ;
            this.targetYaw = targetYaw;
            this.targetPitch = targetPitch;
        }
    }
}
