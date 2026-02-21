package servermagic.mixin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import servermagic.spells.utils.SummonedArmor;
import servermagic.spells.utils.SummonedArmor.ArmorDescriptor;

@Mixin(Player.class)
public class PlayerOnDeathMixin {

	/**
	 * For summoned (bound) armor, we need to revert armor back to it's original
	 * form after the player dies. We always put curse of binding on summed armor,
	 * so we need to make sure to revert it since the player COULD get around
	 * the curse by purposefully dieing
	 */
	@Inject(method = "dropEquipment(Lnet/minecraft/server/level/ServerLevel;)V", at = @At("HEAD"))
	protected void onDropEquipment(ServerLevel serverLevel, CallbackInfo ci) {
		Player self = (Player) (Object) this;

		if (!(self instanceof ServerPlayer player)) {
			return;
		}
		if (player.level().isClientSide()) {
			return;
		}

		// look at the players inventory,
		// if any items are custom items,
		// restore them to originals
		Iterator<ItemStack> invIterator = player.getInventory().iterator();
		List<ItemStack> itemsToRemove = new ArrayList<>();
		List<ItemStack> itemsToAdd = new ArrayList<>();
		while (invIterator.hasNext()) {
			ItemStack item = invIterator.next();
			if (item == null) {
				continue;
			}
			if (item.isEmpty()) {
				continue;
			}
			ArmorDescriptor ad = SummonedArmor.GetInfo(item);
			if (ad.IsTempArmor()) {
				itemsToRemove.add(item);
				ItemStack original = SummonedArmor.DecodeOriginalFromSummonedItem(player, item);
				if (original != null) {
					itemsToAdd.add(original);
				}
			}
		}

		for (ItemStack i : itemsToRemove) {
			player.getInventory().removeItem(i);
		}
		for (ItemStack i : itemsToAdd) {
			player.getInventory().add(i);
		}
	}
}