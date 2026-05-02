package servermagic.data.items;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.EntityHitResult;
import servermagic.data.items.utils.ISpellFocus;
import servermagic.spells.utils.ClickType;

public class SpellbookItem extends CustomItem implements ISpellFocus {
    public static final String ID = "base_spellbook";

    @Override
    public String getItemId() {
        return ID;
    }

    @Override
    public ItemStack getDefaultItemStack() {
        ItemStack is = this.getBaseItemStack(Items.BOOK);
        is.set(DataComponents.ITEM_NAME, Component.literal("Spellbook"));
        is.set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("servermagic", "spellbook"));
        return is;
    }

    @Override
    public ItemStackTemplate getDefaultItemStackTemplate() {
        DataComponentPatch patch = DataComponentPatch.builder()
                .set(DataComponents.CUSTOM_DATA, getCustomDataComponent())
                .set(DataComponents.ITEM_NAME, Component.literal("Spellbook"))
                .set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("servermagic", "spellbook"))
                .build();
        return new ItemStackTemplate(Items.BOOK, patch);
    }

    @Override
    public InteractionResult onUse(ServerLevel world, ServerPlayer player, InteractionHand hand) {
        return this.cast(world, player, hand, ClickType.RIGHT_CLICK);
    }

    @Override
    public InteractionResult onAttack(ServerLevel world, ServerPlayer player, InteractionHand hand, Entity entity,
            EntityHitResult hitResult) {
        return super.onAttack(world, player, hand, entity, hitResult);
    }

}
