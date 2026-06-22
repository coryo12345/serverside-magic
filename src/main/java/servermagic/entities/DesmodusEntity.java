package servermagic.entities;

import de.tomalbrc.bil.api.AnimatedEntity;
import de.tomalbrc.bil.api.AnimatedEntityHolder;
import de.tomalbrc.bil.core.holder.entity.living.LivingEntityHolder;
import de.tomalbrc.bil.core.model.Model;
import de.tomalbrc.bil.file.loader.BbModelLoader;
import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DesmodusEntity extends PathfinderMob implements AnimatedEntity {

    private static final float SPEED = 0.4f;
    private static final Model MODEL = BbModelLoader.load(Identifier.parse("servermagic:desmodus"));

    private final LivingEntityHolder<DesmodusEntity> holder;
    private boolean wasRiding = false;

    public DesmodusEntity(EntityType<? extends DesmodusEntity> type, Level level) {
        super(type, level);
        this.holder = new LivingEntityHolder<>(this, MODEL);
        EntityAttachment.ofTicking(this.holder, this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0);
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scale) {
        return new Vec3(0, 0.6, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isVehicle() && this.getFirstPassenger() instanceof Player rider
                && rider.isShiftKeyDown()) {
            rider.fallDistance = 0;
            rider.stopRiding();
        }
        boolean isRiding = this.isVehicle();
        if (isRiding != wasRiding) {
            holder.getAnimator().playAnimation(isRiding ? "fly" : "idle_ground");
            wasRiding = isRiding;
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isVehicle() && this.getFirstPassenger() instanceof ServerPlayer rider) {
            this.setNoGravity(true);

            float forward = rider.zza;  // W = positive, S = negative
            float strafe = rider.xxa;   // A (left) = positive, D (right) = negative

            if (Math.abs(forward) > 0.01f || Math.abs(strafe) > 0.01f) {
                float yawRad = rider.getYRot() * Mth.DEG_TO_RAD;
                float pitchRad = rider.getXRot() * Mth.DEG_TO_RAD;

                // Forward follows look direction including pitch (up/down)
                double fx = -Mth.sin(yawRad) * Mth.cos(pitchRad) * forward;
                double fy = -Mth.sin(pitchRad) * forward;
                double fz = Mth.cos(yawRad) * Mth.cos(pitchRad) * forward;

                // Strafe is horizontal only, rotated by yaw
                double sx = Mth.cos(yawRad) * strafe;
                double sz = Mth.sin(yawRad) * strafe;

                Vec3 direction = new Vec3(fx + sx, fy, fz + sz);
                if (direction.lengthSqr() > 1e-6) {
                    direction = direction.normalize().scale(SPEED);
                    this.setDeltaMovement(direction);
                    this.move(MoverType.SELF, direction);
                }
            } else {
                this.setDeltaMovement(Vec3.ZERO);
            }

            this.setYRot(rider.getYRot());
            this.yBodyRot = rider.getYRot();
            this.yHeadRot = rider.getYRot();
        } else {
            this.setNoGravity(false);
            super.travel(travelVector);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND && !this.isVehicle()
                && player instanceof ServerPlayer serverPlayer) {
            MinecraftServer srv = this.level().getServer();
            if (srv != null) srv.execute(() -> serverPlayer.startRiding(this));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        this.discard();
        return true;
    }

    @Override
    protected void registerGoals() { }

    @Override
    public AnimatedEntityHolder getHolder() {
        return holder;
    }
}
