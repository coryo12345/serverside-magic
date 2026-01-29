package servermagic.data.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.EntityHitResult;
import servermagic.spells.FireBolt;
import servermagic.spells.LightningOnTarget;

public class SpellbookItem extends CustomItem {
    public static final String ID = "base_spellbook";

    @Override
    public String getItemId() {
        return ID;
    }

    @Override
    public ItemStack getDefaultItemStack() {
        ItemStack is = this.getBaseItemStack(Items.BOOK);
        is.set(DataComponents.ITEM_NAME, Component.literal("Spellbook"));
        return is;
    }

    @Override
    public InteractionResult onUse(ServerLevel world, ServerPlayer player, InteractionHand hand) {
        FireBolt spell = new FireBolt(world, player);
        InteractionResult ir = spell.castAsInteraction();
        if (ir == InteractionResult.SUCCESS) {
            player.getCooldowns().addCooldown(player.getItemInHand(hand), 20);
        }
        return ir;
    }

    @Override
    public InteractionResult onAttack(ServerLevel world, ServerPlayer player, InteractionHand hand, Entity entity,
            EntityHitResult hitResult) {
        LightningOnTarget spell = new LightningOnTarget(world, player);
        return spell.castAsInteraction();
    }

}
