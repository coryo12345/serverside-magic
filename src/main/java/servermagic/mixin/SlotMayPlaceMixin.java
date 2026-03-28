package servermagic.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import servermagic.spells.utils.BoundItems;

@Mixin(Slot.class)
public abstract class SlotMayPlaceMixin {

	@Shadow
	@Final
	public Container container;

	@Inject(method = "mayPlace", at = @At("HEAD"), cancellable = true)
	public void mayPlace(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.isEmpty()) {
			return;
		}
		if (BoundItems.IsItemBound(stack)) {
			// Only allow if the container is the player's inventory
			if (!(this.container instanceof Inventory)) {
				cir.setReturnValue(false);
			}
		}
	}
}
