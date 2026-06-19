package servermagic.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import servermagic.db.Database;
import servermagic.spells.FloatingShield;
import servermagic.web.skill.SkillGranter;
import servermagic.web.skill.Skills;

@Mixin(LivingEntity.class)
public class LivingEntityHurtMixin {

    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void onHurtServerHead(ServerLevel level, DamageSource source, float amount,
            CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self instanceof ServerPlayer player)) return;
        if (FloatingShield.tryAbsorbHit(player)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "hurtServer", at = @At("RETURN"))
    private void onHurtServer(ServerLevel level, DamageSource source, float amount,
            CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self instanceof ServerPlayer player)) return;

        if (cir.getReturnValue()) {
            // ARROW_VOLLEY unlock: hit yourself with your own arrow
            if (source.getDirectEntity() instanceof AbstractArrow arrow
                    && arrow.getOwner() != null
                    && arrow.getOwner().getUUID().equals(player.getUUID())) {
                Optional<Database> db = Database.GetDB();
                if (db.isPresent()) {
                    SkillGranter.grantSkillForPlayer(db.get(), player, Skills.ARROW_VOLLEY);
                }
            }
        }

        // FLOATING_SHIELD unlock: block a charged creeper explosion and survive
        if (!cir.getReturnValue() && player.isAlive()) {
            if (source.getDirectEntity() instanceof Creeper creeper && creeper.isPowered()) {
                Optional<Database> db = Database.GetDB();
                if (db.isPresent()) {
                    SkillGranter.grantSkillForPlayer(db.get(), player, Skills.FLOATING_SHIELD);
                }
            }
        }
    }
}
