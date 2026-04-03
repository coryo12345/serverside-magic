package servermagic.spells;

import java.util.Optional;
import java.util.Set;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.spells.utils.SpellUtils;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class ShadowStep extends BaseSpell {

    private static final double MAX_RANGE = 20.0;
    private static final int STRENGTH_DURATION = 40; // 2 seconds in ticks
    private static final int STRENGTH_AMPLIFIER = 1; // Strength II (0 = I, 1 = II)

    public ShadowStep(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Optional<LivingEntity> targetOpt = SpellUtils.getFirstEntityInLineOfSight(player, MAX_RANGE);
        if (targetOpt.isEmpty()) {
            return;
        }
        LivingEntity target = targetOpt.get();

        Vec3 targetPos = target.position();
        Vec3 playerPos = player.position();

        // Direction from player toward target; behind = same direction past the target
        Vec3 flat = new Vec3(targetPos.x - playerPos.x, 0, targetPos.z - playerPos.z).normalize();

        // Try to land directly behind the mob; step closer if that spot is blocked
        double backDist = target.getBbWidth() * 0.5 + 1.5;
        Vec3 destination = null;
        for (double d = backDist; d >= 0.4; d -= 0.5) {
            Vec3 candidate = new Vec3(
                    targetPos.x + flat.x * d,
                    targetPos.y,
                    targetPos.z + flat.z * d);
            if (world.noCollision(player, player.getBoundingBox().move(candidate.subtract(playerPos)))) {
                destination = candidate;
                break;
            }
        }

        if (destination == null) {
            return;
        }

        // Departure burst — dark shadow at player's current position
        world.sendParticles(ParticleTypes.REVERSE_PORTAL,
                playerPos.x, playerPos.y + 1.0, playerPos.z, 30, 0.4, 0.7, 0.4, 0.12);
        world.sendParticles(ParticleTypes.LARGE_SMOKE,
                playerPos.x, playerPos.y + 1.0, playerPos.z, 10, 0.3, 0.5, 0.3, 0.04);
        world.sendParticles(ParticleTypes.SCULK_SOUL,
                playerPos.x, playerPos.y + 1.0, playerPos.z, 8, 0.3, 0.4, 0.3, 0.06);

        world.playSound(null, playerPos.x, playerPos.y, playerPos.z,
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.7F, 1.8F);

        // Compute facing angle so the player looks at the target on arrival
        Vec3 toLook = targetPos.add(0, target.getBbHeight() * 0.5, 0).subtract(destination);
        float yaw = (float) Math.toDegrees(Math.atan2(-toLook.x, toLook.z));
        double horizLen = Math.sqrt(toLook.x * toLook.x + toLook.z * toLook.z);
        float pitch = (float) -Math.toDegrees(Math.atan2(toLook.y, horizLen));

        player.teleportTo(world, destination.x, destination.y, destination.z, Set.of(), yaw, pitch, false);

        // Arrival burst — flash of shadow energy at the destination
        world.sendParticles(ParticleTypes.PORTAL,
                destination.x, destination.y + 1.0, destination.z, 35, 0.4, 0.7, 0.4, 0.14);
        world.sendParticles(ParticleTypes.SCULK_SOUL,
                destination.x, destination.y + 1.0, destination.z, 12, 0.35, 0.5, 0.35, 0.07);
        world.sendParticles(ParticleTypes.LARGE_SMOKE,
                destination.x, destination.y + 1.0, destination.z, 8, 0.3, 0.5, 0.3, 0.04);
        world.sendParticles(ParticleTypes.CRIT,
                destination.x, destination.y + 1.0, destination.z, 20, 0.45, 0.5, 0.45, 0.12);

        world.playSound(null, destination.x, destination.y, destination.z,
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.3F);
        world.playSound(null, destination.x, destination.y, destination.z,
                SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.9F, 0.8F);

        // Strength II for 2 seconds
        player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, STRENGTH_DURATION, STRENGTH_AMPLIFIER));
    }

    @Override
    public int cost() {
        return 4;
    }

    @Override
    public String displayName() {
        return "Shadow Step";
    }

    @Override
    public String description() {
        return "Teleport behind a target mob and gain Strength II for 2 seconds";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.SHADOW_STEP);
    }
}
