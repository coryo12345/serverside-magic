package servermagic.spells;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.WindCharge;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;

public class WindChargeSpell extends BaseSpell {

    public WindChargeSpell(ServerLevel world, ServerPlayer player, Database db) {
        super(world, player, db);
    }

    @Override
    protected void spellImplementation() {
        Vec3 lookDirection = player.getLookAngle();
        Vec3 startPos = player.getEyePosition();

        WindCharge windCharge = new WindCharge(player, world, startPos.x, startPos.y, startPos.z);
        windCharge.shoot(lookDirection.x, lookDirection.y, lookDirection.z, 1.5F, 1.0F);
        world.addFreshEntity(windCharge);

        // Play sound
        world.playSound(null, player.getX(), player.getY(), player.getZ(), 
            SoundEvents.WIND_CHARGE_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);

        // Add particles for effect
        for (int i = 0; i < 5; i++) {
            world.sendParticles(
                ParticleTypes.SMALL_GUST,
                startPos.x + (world.random.nextDouble() - 0.5) * 0.5,
                startPos.y + (world.random.nextDouble() - 0.5) * 0.5,
                startPos.z + (world.random.nextDouble() - 0.5) * 0.5,
                1, 0.0, 0.0, 0.0, 0.05
            );
        }
        
        world.sendParticles(
            ParticleTypes.CLOUD,
            startPos.x, startPos.y, startPos.z,
            10, 0.2, 0.2, 0.2, 0.1
        );
    }

    @Override
    public String displayName() {
        return "Wind Charge";
    }

    @Override
    public String description() {
        return "Shoot a powerful gust of wind";
    }
}