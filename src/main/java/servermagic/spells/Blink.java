package servermagic.spells;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.SkillGranter;
import servermagic.web.skill.Skills;

public class Blink extends BaseSpell {

    private static final double MAX_BLINK_DISTANCE = 30.0;

    // ── Clutch-fall unlock tracking ──────────────────────────────────────────
    // Maps player UUID → game-time tick when the pearl was thrown
    private static final Map<UUID, Long> pendingClutch = new ConcurrentHashMap<>();
    private static final long CLUTCH_TIMEOUT_TICKS = 200; // 10 seconds
    private static final float MIN_FALL_DISTANCE = 20.0f;

    /**
     * Call this when a player throws an ender pearl. If they were in free-fall
     * deep enough, we start tracking them for the Blink unlock.
     */
    public static void onEnderPearlThrown(ServerPlayer player) {
        if (player.fallDistance >= MIN_FALL_DISTANCE) {
            pendingClutch.put(player.getUUID(), player.level().getGameTime());
        }
    }

    /**
     * Called each world tick. Grants BLINK to any tracked player who survived
     * their fall (fallDistance resets to 0 the tick after landing).
     */
    public static void tickClutchCheck(ServerLevel world) {
        Optional<Database> db = Database.GetDB();
        if (db.isEmpty()) return;

        long now = world.getGameTime();
        pendingClutch.entrySet().removeIf(entry -> {
            if (now - entry.getValue() > CLUTCH_TIMEOUT_TICKS) return true;

            ServerPlayer player = world.getServer().getPlayerList().getPlayer(entry.getKey());
            if (player == null || !player.isAlive()) return true;

            // fallDistance resets to 0.0 once the player lands on solid ground
            if (player.fallDistance == 0.0f && player.level() == world) {
                SkillGranter.grantSkillForPlayer(db.get(), player, Skills.BLINK);
                return true;
            }
            return false;
        });
    }

    // ── Spell implementation ─────────────────────────────────────────────────

    public Blink(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookDir = player.getLookAngle();

        Vec3 rayEnd = eyePos.add(lookDir.scale(MAX_BLINK_DISTANCE));
        BlockHitResult hit = world.clip(new ClipContext(
                eyePos, rayEnd,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player));

        Vec3 destEye;
        if (hit.getType() == HitResult.Type.BLOCK) {
            destEye = hit.getLocation().subtract(lookDir.normalize().scale(0.6));
        } else {
            destEye = rayEnd;
        }

        world.sendParticles(ParticleTypes.PORTAL,
                eyePos.x, eyePos.y, eyePos.z, 20, 0.3, 0.3, 0.3, 0.1);
        world.playSound(null, eyePos.x, eyePos.y, eyePos.z,
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.8F, 1.2F);

        Vec3 offset = destEye.subtract(eyePos);
        player.teleportTo(
                player.getX() + offset.x,
                player.getY() + offset.y,
                player.getZ() + offset.z);

        Vec3 newEye = player.getEyePosition();
        world.sendParticles(ParticleTypes.PORTAL,
                newEye.x, newEye.y, newEye.z, 20, 0.3, 0.3, 0.3, 0.1);
        world.playSound(null, newEye.x, newEye.y, newEye.z,
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.6F, 0.8F);
    }

    @Override
    public int getFlatXpCost() {
        return 5;
    }

    @Override
    public double getLevelPercentCost() {
        return 0.25;
    }

    @Override
    public String displayName() {
        return "Blink";
    }

    @Override
    public String description() {
        return "Instantly teleport up to 30 blocks in the direction you are looking";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.BLINK);
    }
}
