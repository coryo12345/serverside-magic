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
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.SkillGranter;
import servermagic.web.skill.Skills;

public class Backstep extends BaseSpell {

    // ── Unlock tracking ──────────────────────────────────────────────────────
    private static final Map<UUID, Double> backstepProgress = new ConcurrentHashMap<>();
    private static final Map<UUID, Vec3> lastPosition = new ConcurrentHashMap<>();
    private static final double REQUIRED_DISTANCE = 20.0;

    public static void tickBackstepCheck(ServerLevel world) {
        Optional<Database> db = Database.GetDB();
        if (db.isEmpty()) return;

        for (ServerPlayer player : world.players()) {
            UUID id = player.getUUID();
            Vec3 current = player.position();
            Vec3 prev = lastPosition.get(id);
            lastPosition.put(id, current);

            if (prev == null) continue;

            if (player.isCrouching() || !player.onGround()) {
                backstepProgress.put(id, 0.0);
                continue;
            }

            Vec3 moveDelta = new Vec3(current.x - prev.x, 0, current.z - prev.z);
            double moveMag = moveDelta.length();

            // Filter out negligible movement (standing still) from accumulation and reset
            if (moveMag < 0.01) {
                continue;
            }

            Vec3 look = player.getLookAngle();
            Vec3 lookHoriz = new Vec3(look.x, 0, look.z);
            if (lookHoriz.length() < 0.01) continue;
            lookHoriz = lookHoriz.normalize();

            // Negative dot product means moving opposite to look direction (backwards)
            double dot = moveDelta.normalize().dot(lookHoriz);
            if (dot < -0.2) {
                double progress = backstepProgress.getOrDefault(id, 0.0) + moveMag;
                if (progress >= REQUIRED_DISTANCE) {
                    SkillGranter.grantSkillForPlayer(db.get(), player, Skills.BACKSTEP);
                    backstepProgress.put(id, 0.0);
                } else {
                    backstepProgress.put(id, progress);
                }
            } else {
                backstepProgress.put(id, 0.0);
            }
        }
    }

    // ── Spell implementation ─────────────────────────────────────────────────

    public Backstep(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Vec3 look = player.getLookAngle();
        Vec3 backwards = new Vec3(-look.x, 0, -look.z);

        // Fall back to player facing if looking nearly straight up/down
        if (backwards.length() < 0.1) {
            backwards = new Vec3(-player.getForward().x, 0, -player.getForward().z);
        }
        backwards = backwards.normalize();

        Vec3 launchVel = backwards.scale(1.8).add(0, 0.6, 0);
        player.setDeltaMovement(launchVel);
        player.hurtMarked = true;

        Vec3 pos = player.position();

        // Burst particles behind and around the player
        world.sendParticles(ParticleTypes.CLOUD,
                pos.x, pos.y + 1.0, pos.z, 20, 0.5, 0.4, 0.5, 0.12);
        world.sendParticles(ParticleTypes.POOF,
                pos.x, pos.y + 0.5, pos.z, 15, 0.4, 0.3, 0.4, 0.18);
        world.sendParticles(ParticleTypes.SMALL_GUST,
                pos.x, pos.y + 0.8, pos.z, 10, 0.5, 0.4, 0.5, 0.0);

        // Directional trail of particles in the launch direction
        for (int i = 1; i <= 5; i++) {
            Vec3 pt = pos.add(backwards.scale(i * 0.8)).add(0, 0.6, 0);
            world.sendParticles(ParticleTypes.CLOUD, pt.x, pt.y, pt.z, 3, 0.2, 0.15, 0.2, 0.06);
        }

        world.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.WIND_CHARGE_BURST, SoundSource.PLAYERS, 0.9F, 1.1F);
        world.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.WIND_CHARGE_THROW, SoundSource.PLAYERS, 0.7F, 0.9F);
    }

    @Override
    public int getFlatXpCost() {
        return 3;
    }

    @Override
    public double getLevelPercentCost() {
        return 0.0;
    }

    @Override
    public String displayName() {
        return "Backstep";
    }

    @Override
    public String description() {
        return "Burst backwards with a gust of wind, launching yourself 10-15 blocks";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.BACKSTEP);
    }
}
