package servermagic.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import servermagic.TaskScheduler;
import servermagic.data.items.CustomItem;
import servermagic.data.items.utils.ISpellFocus;
import servermagic.data.items.utils.ItemInteractionDispatcher;
import servermagic.spells.utils.ClickType;

@Mixin(LivingEntity.class)
public class PlayerSwingMixin {

	/**
	 * We need to track when the user left clicks with a spell focus item.
	 * We make a big assumption here: spell focus items dont have a swing animation
	 * on right click.
	 * 
	 * Since technically things like placing blocks also trigger a swing, this can
	 * trigger this function too,
	 * and it's not very easy to tell the difference between placing and attacking.
	 */
	@Inject(method = "swing(Lnet/minecraft/world/InteractionHand;)V", at = @At("HEAD"))
	private void onSwing(InteractionHand hand, CallbackInfo ci) {
		LivingEntity entity = (LivingEntity) (Object) this;

		// Only care about players
		if (!(entity instanceof ServerPlayer player)) {
			return;
		}

		Level level = player.level();
		if (!(level instanceof ServerLevel world)) {
			return;
		}

		if (player.level().isClientSide()) {
			return;
		}

		if (player.isUsingItem()) {
			return;
		}

		// we need to delay this because the swing can happen before the punch attack is
		// registered
		// If that happens, the players attack strength wont have dropped so we have no
		// clue
		// if this was an interaction, block placement, dropping something, etc..
		TaskScheduler.scheduleTask(() -> {
			if (player.getAttackStrengthScale(0.0F) >= 0.5F) {
				return;
			}

			ItemInteractionDispatcher dispatcher = new ItemInteractionDispatcher(world, player, hand);
			Optional<CustomItem> item = dispatcher.getHeldCustomItem();
			if (item.isEmpty()) {
				return;
			}

			// known issue: interacting with blocks, dropping items, anything triggers a
			// swing can incorrectly trigger this
			// idk the solution though so this works for now
			if (item.get() instanceof ISpellFocus spellFocus) {
				spellFocus.cast(world, player, InteractionHand.MAIN_HAND, ClickType.LEFT_CLICK);
			}
		}, 1);
	}
}