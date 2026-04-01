package servermagic.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import servermagic.spells.FlyingCarpet;
import servermagic.spells.SummonMount;

@Mixin(LivingEntity.class)
public class PlayerDismountMixin {

	/**
	 * To support spells with vehicles
	 * 
	 * When the player gets off the vehicle, handle the cases of logic to run
	 */
	@Inject(method = "dismountVehicle(Lnet/minecraft/world/entity/Entity;)V", at = @At("TAIL"))
	private void onDismount(Entity entity, CallbackInfo ci) {
		LivingEntity self = (LivingEntity) (Object) this;

		if (!(self instanceof ServerPlayer player)) {
			return;
		}
		if (player.level().isClientSide()) {
			return;
		}

		if (SummonMount.isCustomHorse(entity)) {
			entity.discard();
		}

		if (FlyingCarpet.isFlyingCarpetGhast(entity)) {
			entity.discard();
		}

	}
}