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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.SkillGranter;
import servermagic.web.skill.Skills;

public class Blink extends BaseSpell {

    // Reduced velocity vs. a normal ender pearl throw (~1.5F), giving roughly half the range
    private static final float PEARL_VELOCITY = 0.7F;

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

        // Pre-cast visual burst at player eye
        world.sendParticles(ParticleTypes.PORTAL,
                eyePos.x, eyePos.y, eyePos.z, 15, 0.2, 0.2, 0.2, 0.08);
        world.sendParticles(ParticleTypes.ENCHANT,
                eyePos.x, eyePos.y, eyePos.z, 8, 0.25, 0.25, 0.25, 0.06);

        world.playSound(null, eyePos.x, eyePos.y, eyePos.z,
                SoundEvents.ENDER_EYE_LAUNCH, SoundSource.PLAYERS, 0.8F, 1.2F);

        ThrownEnderpearl pearl = new ThrownEnderpearl(EntityType.ENDER_PEARL, world);
        pearl.setOwner(player);
        pearl.setPos(eyePos.x, eyePos.y, eyePos.z);
        pearl.shoot(lookDir.x, lookDir.y, lookDir.z, PEARL_VELOCITY, 0.0F);
        world.addFreshEntity(pearl);
    }

    @Override
    public int getFlatXpCost() {
        return 10;
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
        return "Conjure an ender pearl and teleport a short distance";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.BLINK);
    }
}
