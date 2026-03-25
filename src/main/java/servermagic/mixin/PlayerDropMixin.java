package servermagic.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import servermagic.spells.utils.BoundItems;

@Mixin(LivingEntity.class)
public class PlayerDropMixin {

	@Inject(method = "drop(Lnet/minecraft/world/itemItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity", at = @At("HEAD"), cancellable = true)
	private void onSwing(ItemStack stack, boolean b1, boolean b2, CallbackInfoReturnable<ItemEntity> ci) {
		// LivingEntity entity = (LivingEntity) (Object) this;
		if (BoundItems.IsItemBound(stack)) {
			ci.setReturnValue(null);
		}
	}
}