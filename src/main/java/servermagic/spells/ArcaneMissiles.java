package servermagic.spells;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.mojang.math.Transformation;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.entitybinding.EntityBindingUtil;
import servermagic.spells.arcane.ArcaneMissileManager;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class ArcaneMissiles extends BaseSpell {

    public ArcaneMissiles(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        // Find the nearest living entity to home in on
        AABB searchArea = AABB.ofSize(player.position(), 40, 40, 40);
        List<LivingEntity> nearby = world.getEntitiesOfClass(LivingEntity.class, searchArea,
                e -> e.isAlive() && !e.getUUID().equals(player.getUUID()) && e.isAttackable());
        LivingEntity target = nearby.stream()
                .min(Comparator.comparingDouble(e -> e.distanceToSqr(player)))
                .orElse(null);
        UUID targetId = target != null ? target.getUUID() : null;

        int missileCount = 3 + world.random.nextInt(3); // 3, 4, or 5

        // Cast sound + burst particles at eye position
        Vec3 eyePos = player.getEyePosition();
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENDER_EYE_LAUNCH, SoundSource.PLAYERS, 1.0F, 1.1F);
        world.sendParticles(ParticleTypes.ENCHANT,
                eyePos.x, eyePos.y, eyePos.z, 15, 0.3, 0.3, 0.3, 0.15);

        Vec3 look = player.getLookAngle();
        double baseYaw = Math.atan2(-look.x, look.z);
        double basePitch = Math.asin(Math.max(-1.0, Math.min(1.0, -look.y)));

        for (int i = 0; i < missileCount; i++) {
            // Random spread ±20° in yaw and pitch
            double yawOffset = Math.toRadians(world.random.nextDouble() * 40 - 20);
            double pitchOffset = Math.toRadians(world.random.nextDouble() * 40 - 20);
            double newYaw = baseYaw + yawOffset;
            double newPitch = basePitch + pitchOffset;
            double cosPitch = Math.cos(newPitch);
            Vec3 spreadDir = new Vec3(
                    -Math.sin(newYaw) * cosPitch,
                    -Math.sin(newPitch),
                    Math.cos(newYaw) * cosPitch);

            Vec3 startPos = eyePos.add(spreadDir.scale(0.8));

            // Invisible snowball — physics driver
            Snowball snowball = new Snowball(world, player, new ItemStack(Items.SNOWBALL));
            snowball.setPos(startPos.x, startPos.y, startPos.z);
            snowball.shoot(spreadDir.x, spreadDir.y, spreadDir.z, 1.2F, 0.0F);
            world.addFreshEntity(snowball);

            // Amethyst shard ItemDisplay — visual
            Display.ItemDisplay display = new Display.ItemDisplay(EntityType.ITEM_DISPLAY, world);
            display.setPos(startPos.x, startPos.y, startPos.z);
            display.setItemStack(new ItemStack(Items.AMETHYST_SHARD));
            float scale = 0.35f;
            display.setTransformation(new Transformation(
                    new Vector3f(-scale / 2f, -scale / 2f, -scale / 2f),
                    new Quaternionf(),
                    new Vector3f(scale, scale, scale),
                    new Quaternionf()));
            world.addFreshEntity(display);

            // Bind display (follower) to snowball (driver, made invisible)
            EntityBindingUtil.bind(world, snowball, display);

            // Register for per-tick homing, hit detection, and cleanup
            ArcaneMissileManager.register(
                    snowball.getUUID(), display.getUUID(), targetId, player.getUUID(), world);
        }
    }

    @Override
    public int cost() {
        return 4;
    }

    @Override
    public String displayName() {
        return "Arcane Missiles";
    }

    @Override
    public String description() {
        return "Launch a burst of homing arcane projectiles at the nearest enemy";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.ARCANE_MISSILES);
    }
}
