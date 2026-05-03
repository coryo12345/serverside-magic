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
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.EntityHitResult;

public class LootboxItem extends CustomItem {
    public static final String ID = "base_lootbox";

    @Override
    public String getItemId() {
        return ID;
    }

    @Override
    public ItemStackTemplate getDefaultItemStackTemplate() {
        DataComponentPatch patch = DataComponentPatch.builder()
                .set(DataComponents.CUSTOM_DATA, getCustomDataComponent())
                .set(DataComponents.ITEM_NAME, Component.literal("Vanity Box"))
                // TODO set description: "Use to unlock a new vanity option"
                // TODO set a unique texture
                .set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("minecraft", "diamond"))
                .build();
        return new ItemStackTemplate(Items.TRIAL_KEY, patch);
    }

    @Override
    public InteractionResult onUse(ServerLevel world, ServerPlayer player, InteractionHand hand) {
        // TODO
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult onAttack(ServerLevel world, ServerPlayer player, InteractionHand hand, Entity entity,
            EntityHitResult hitResult) {
        return super.onAttack(world, player, hand, entity, hitResult);
    }

}
