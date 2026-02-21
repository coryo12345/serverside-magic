package servermagic.mixin;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import servermagic.spells.utils.SummonedArmor;
import servermagic.spells.utils.SummonedArmor.ArmorDescriptor;

@Mixin(ItemStack.class)
public class ItemOnBreakMixin {

	/**
	 * For Summoned/bound armors. We need to detect if the armor is breaking
	 * so we can revert it to the original before it is destroyed.
	 * It would suck to summon some temporary armor with low durability,
	 * just to have it get broken and your original armor disappear along with it
	 * 
	 * This code runs immediately after we set the new damage value,
	 * so we can detect if the item is broken and handle this case BEFORE
	 * the item is actually destroyed
	 */
	@Inject(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V", shift = At.Shift.AFTER))
	protected void afterSetDamageValue(int i, @Nullable ServerPlayer serverPlayer, Consumer<Item> consumer,
			CallbackInfo ci) {
		ItemStack stack = (ItemStack) (Object) this;

		if (serverPlayer == null) {
			return;
		}

		if (!stack.isBroken()) {
			return;
		}

		ArmorDescriptor ad = SummonedArmor.GetInfo(stack);
		if (ad.IsTempArmor()) {
			ItemStack original = SummonedArmor.DecodeOriginalFromSummonedItem(serverPlayer, stack);
			if (original == null) {
				original = ItemStack.EMPTY;
			}

			serverPlayer.getInventory().add(original);
		}

	}
}