package servermagic.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import servermagic.db.Database;
import servermagic.web.skill.SkillGranter;

@Mixin(SmithingMenu.class)
public class SmithingMenuMixin {

    @Inject(method = "onTake", at = @At("HEAD"))
    private void onSmithingTake(Player player, ItemStack stack, CallbackInfo ci) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        Optional<Database> db = Database.GetDB();
        if (db.isEmpty()) {
            return;
        }

        SkillGranter granter = new SkillGranter((ServerLevel) serverPlayer.level(), serverPlayer, db.get());
        granter.grantFromSmithing(stack);
    }
}
