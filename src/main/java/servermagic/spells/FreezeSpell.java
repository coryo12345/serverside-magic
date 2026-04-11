package servermagic.spells;

import java.util.Optional;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.entitybinding.EntityBindingUtil;
import servermagic.spells.freeze.FreezeProjectileManager;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class FreezeSpell extends BaseSpell {

    public FreezeSpell(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Vec3 lookDir = player.getLookAngle();
        Vec3 startPos = player.getEyePosition().add(lookDir.scale(1.0));

        // Invisible snowball — physics driver for the projectile trajectory
        Snowball snowball = new Snowball(world, player, new ItemStack(Items.SNOWBALL));
        snowball.setPos(startPos.x, startPos.y, startPos.z);
        snowball.shoot(lookDir.x, lookDir.y, lookDir.z, 1.5F, 0.0F);
        world.addFreshEntity(snowball);

        // Visible ice block — follows the snowball via EntityBindingUtil each tick.
        // BlockDisplay is purely visual with no physics, so it won't fight the binding.
        Display.BlockDisplay iceBlock = new Display.BlockDisplay(EntityType.BLOCK_DISPLAY, world);
        iceBlock.setPos(startPos.x, startPos.y, startPos.z);
        iceBlock.setBlockState(Blocks.ICE.defaultBlockState());
        world.addFreshEntity(iceBlock);

        // Bind: snowball (driver, made invisible) → iceBlock (follower, tracks position)
        EntityBindingUtil.bind(world, snowball, iceBlock);

        // Register for hit detection + cleanup
        FreezeProjectileManager.register(
                snowball.getUUID(), iceBlock.getUUID(), player.getUUID(), world);

        // Cast effects
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
        world.sendParticles(ParticleTypes.SNOWFLAKE,
                startPos.x, startPos.y, startPos.z, 10, 0.2, 0.2, 0.2, 0.05);
    }

    @Override

    public int getFlatXpCost() {
        return 8;
    }

    public double getLevelPercentCost() {
        return 0.2;
    }
    @Override
    public String displayName() {
        return "Freeze";
    }

    @Override
    public String description() {
        return "Shoot a block of ice that freezes and damages the target";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.FREEZE);
    }
}
