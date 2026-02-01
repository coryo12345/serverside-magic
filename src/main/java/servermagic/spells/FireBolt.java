package servermagic.spells;

import java.util.List;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class FireBolt extends BaseSpell {

    public FireBolt(ServerLevel world, ServerPlayer player) {
        super(world, player);
    }

    @Override
    protected void spellImplementation() {
        Vec3 lookDirection = player.getLookAngle();
        Vec3 startPos = player.getEyePosition();
        double maxDistance = 5;

        // Spawn particles
        int i = 3; // start a bit away from the camera
        Vec3 nextParticlePos = new Vec3(startPos.x, startPos.y, startPos.z);
        while (Math.abs(startPos.distanceTo(nextParticlePos)) < maxDistance) {
            // offset each particle just a little bit more so they aren't too far away
            double offsetFactor = i++ * 0.2;
            nextParticlePos = startPos.add(lookDirection.multiply(offsetFactor, offsetFactor, offsetFactor));
            // spawn 1 particle, allow it to spread a little so it's not a perfect line
            world.sendParticles(ParticleTypes.FLAME, nextParticlePos.x, nextParticlePos.y, nextParticlePos.z, 1, 0.1,
                    0.1, 0.1, 0);
        }

        // Attack mobs
        List<LivingEntity> entities = SpellUtils.getAllLivingEntitiesInLineOfSight(player, maxDistance);
        DamageSource ds = player.damageSources().onFire();
        for (LivingEntity entity : entities) {
            if (entity.isAttackable()) {
                entity.hurtServer(world, ds, 5);
                entity.setRemainingFireTicks(20 * 3); // 3 seconds
            }
        }
    }

    @Override
    public String displayName() {
        return "Firebolt";
    }

    @Override
    public String description() {
        return "Short-range burst of flame";
    }

}
