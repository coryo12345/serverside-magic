package servermagic.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import servermagic.spells.utils.BoundItems;

@Mixin(LivingEntity.class)
public class PlayerDropMixin {

	@Inject(method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At("HEAD"), cancellable = true)
	private void onDrop(ItemStack stack, boolean b1, boolean b2, CallbackInfoReturnable<ItemEntity> ci) {
		LivingEntity entity = (LivingEntity) (Object) this;

		if (entity instanceof ServerPlayer player) {
			if (BoundItems.IsItemBound(stack)) {
				ItemStack original = BoundItems.DecodeOriginalFromBoundItem(player, stack);
				if (original != null && !original.isEmpty()) {
					if (player.getMainHandItem().isEmpty()) {
						player.setItemInHand(InteractionHand.MAIN_HAND, original);
					} else {
						player.getInventory().add(original);
					}
				}
				ci.setReturnValue(null);
			}
		}
	}
}