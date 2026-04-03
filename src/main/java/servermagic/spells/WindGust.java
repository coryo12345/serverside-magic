package servermagic.spells;

import java.util.List;
import java.util.Optional;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.spells.utils.SpellUtils;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class WindGust extends BaseSpell {

    private static final double CONE_RANGE = 15.0;
    private static final double CONE_HALF_ANGLE = 35.0;
    // knockback() pushes opposite to the (dx,dz) you pass — we negate look direction below
    private static final double KNOCKBACK_STRENGTH = 2.0;
    private static final double KNOCKBACK_LIFT = 0.45;

    public WindGust(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Vec3 look = player.getLookAngle();
        Vec3 origin = player.getEyePosition();

        // Compute knockback direction: horizontal component + upward bias
        // Handle edge case of looking straight up/down
        Vec3 flat = new Vec3(look.x, 0, look.z);
        Vec3 knockDir;
        if (flat.length() > 0.1) {
            Vec3 h = flat.normalize();
            knockDir = new Vec3(h.x, KNOCKBACK_LIFT, h.z).normalize();
        } else {
            // Looking nearly straight up/down — use player's horizontal facing
            knockDir = new Vec3(player.getForward().x, KNOCKBACK_LIFT, player.getForward().z).normalize();
        }

        List<LivingEntity> targets = SpellUtils.getAllLivingEntitiesInCone(player, CONE_RANGE, CONE_HALF_ANGLE);
        for (LivingEntity target : targets) {
            // knockback() pushes away from (dx, dz), so negate the look direction to push forward
            target.knockback(KNOCKBACK_STRENGTH, -knockDir.x, -knockDir.z);
            // Add upward impulse on top of what knockback gives
            target.setDeltaMovement(target.getDeltaMovement().add(0, KNOCKBACK_LIFT, 0));

            // Burst of gust particles at each hit entity
            Vec3 tc = target.getBoundingBox().getCenter();
            world.sendParticles(ParticleTypes.CLOUD, tc.x, tc.y, tc.z, 8, 0.4, 0.3, 0.4, 0.14);
            world.sendParticles(ParticleTypes.POOF, tc.x, tc.y, tc.z, 5, 0.3, 0.2, 0.3, 0.18);
        }

        // Streaming cone of wind particles forward from the caster
        for (int i = 1; i <= 8; i++) {
            double dist = i * 1.75;
            Vec3 pt = origin.add(look.scale(dist));
            int count = Math.max(1, 5 - i / 2);
            double spread = 0.3 + i * 0.08;
            world.sendParticles(ParticleTypes.CLOUD, pt.x, pt.y, pt.z, count, spread, spread * 0.6, spread, 0.04);
            world.sendParticles(ParticleTypes.SMALL_GUST, pt.x, pt.y, pt.z, count, spread * 0.8, spread * 0.5, spread * 0.8, 0.0);
        }

        // Origin blast burst
        world.sendParticles(ParticleTypes.CLOUD, origin.x, origin.y, origin.z, 25, 0.7, 0.5, 0.7, 0.18);
        world.sendParticles(ParticleTypes.POOF, origin.x, origin.y, origin.z, 18, 0.5, 0.35, 0.5, 0.22);
        world.sendParticles(ParticleTypes.SMALL_GUST, origin.x, origin.y, origin.z, 12, 0.6, 0.4, 0.6, 0.0);

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WIND_CHARGE_BURST, SoundSource.PLAYERS, 1.6F, 0.65F);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WIND_CHARGE_THROW, SoundSource.PLAYERS, 0.9F, 0.85F);
    }

    @Override
    public int cost() {
        return 3;
    }

    @Override
    public String displayName() {
        return "Wind Gust";
    }

    @Override
    public String description() {
        return "Blast a powerful cone of wind that flings all nearby enemies away";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.WIND_GUST);
    }
}
