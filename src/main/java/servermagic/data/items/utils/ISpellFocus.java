package servermagic.data.items.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import servermagic.spells.utils.ClickType;
import servermagic.spells.utils.PlayerSpellFocusCaster;

public interface ISpellFocus {
    public ItemStack getDefaultItemStack();

    default int spellCastCooldown() {
        return 20;
    }

    default InteractionResult cast(ServerLevel world, ServerPlayer player, InteractionHand hand, ClickType clickType) {
        try {
            ItemStack held = player.getItemInHand(hand);
            if (player.getCooldowns().isOnCooldown(held)) {
                return InteractionResult.PASS;
            }

            PlayerSpellFocusCaster caster = PlayerSpellFocusCaster.Get();
            InteractionResult ir = caster.handleClick(world, player, clickType);
            if (ir == InteractionResult.SUCCESS) {
                player.getCooldowns().addCooldown(this.getDefaultItemStack(), this.spellCastCooldown());
            }
            return ir;
        } catch (Exception e) {
            return InteractionResult.FAIL;
        }
    }
}
