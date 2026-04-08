package servermagic.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import servermagic.db.Database;
import servermagic.web.skill.SkillGranter;

@Mixin(ItemCombinerMenu.class)
public abstract class ItemCombinerMenuMixin {

    @Shadow
    public abstract int getResultSlot();

    @Unique
    private boolean servermagic$pendingSmithShiftClick = false;

    @Inject(method = "quickMoveStack", at = @At("HEAD"))
    private void onQuickMoveHead(Player player, int slotIndex, CallbackInfoReturnable<ItemStack> cir) {
        // Only care about shift-clicking the result slot on a SmithingMenu
        servermagic$pendingSmithShiftClick = ((Object) this instanceof SmithingMenu) && slotIndex == this.getResultSlot();
    }

    @Inject(method = "quickMoveStack", at = @At("RETURN"))
    private void onQuickMoveReturn(Player player, int slotIndex, CallbackInfoReturnable<ItemStack> cir) {
        if (!servermagic$pendingSmithShiftClick) {
            return;
        }
        servermagic$pendingSmithShiftClick = false;

        // The return value is a copy of the item that was moved; empty means nothing was taken
        ItemStack moved = cir.getReturnValue();
        if (moved.isEmpty()) {
            return;
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        Optional<Database> db = Database.GetDB();
        if (db.isEmpty()) {
            return;
        }

        SkillGranter granter = new SkillGranter((ServerLevel) serverPlayer.level(), serverPlayer, db.get());
        granter.grantFromSmithing(moved);
    }
}
