package servermagic.spells;

import java.util.Optional;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class FireballSpell extends BaseSpell {

    public FireballSpell(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Vec3 lookDirection = player.getLookAngle();
        // Start about 1 block in front of the player's eyes
        Vec3 startPos = player.getEyePosition().add(lookDirection.scale(1.0));

        // In 1.21.x Mojang mappings, LargeFireball constructor takes (Level,
        // LivingEntity, Vec3, int)
        // where Vec3 is the power/acceleration vector and int is the explosion power.
        LargeFireball fireball = new LargeFireball(world, player, lookDirection, 1);

        // Explicitly set position to be in front of the player
        fireball.setPos(startPos.x, startPos.y, startPos.z);

        world.addFreshEntity(fireball);

        // Play ghast shoot sound
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GHAST_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);

        // Spawn some particles at the launch point
        world.sendParticles(ParticleTypes.FLAME, startPos.x, startPos.y, startPos.z,
                15, 0.2, 0.2, 0.2, 0.1);
        world.sendParticles(ParticleTypes.LARGE_SMOKE, startPos.x, startPos.y, startPos.z,
                5, 0.1, 0.1, 0.1, 0.05);
    }

    @Override

    public int getFlatXpCost() {
        return 20;
    }

    public double getLevelPercentCost() {
        return 0.5;
    }

    public String displayName() {
        return "Fireball";
    }

    @Override
    public String description() {
        return "Shoots an explosive ghast fireball";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.FIREBALL);
    }
}
