package servermagic.data.items;

import java.util.Optional;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.phys.EntityHitResult;

public abstract class CustomItem {
    public static final String CUSTOM_DATA_KEY = "magiccustomdata";
    public static final String CUSTOM_DATA_ITEM_ID_KEY = "magicitem";
    public static final String ID = "";

    public CustomData getCustomDataComponent() {
        CompoundTag componentData = new CompoundTag();
        Optional<CompoundTag> customData = getCustomData();
        if (customData.isPresent()) {
            componentData.put(CUSTOM_DATA_KEY, customData.get());
        }
        componentData.putString(CUSTOM_DATA_ITEM_ID_KEY, getItemId());
        return CustomData.of(componentData);
    }

    public abstract String getItemId();

    public abstract ItemStack getDefaultItemStack();

    // The below functions are OPTIONAL to implement ------------------------------

    protected ItemStack getBaseItemStack(Item baseItem) {
        ItemStack is = new ItemStack(baseItem);
        is.set(DataComponents.CUSTOM_DATA, this.getCustomDataComponent());
        return is;
    }

    /**
     * A map of any data to store on the item
     */
    public Optional<CompoundTag> getCustomData() {
        return Optional.empty();
    }

    public InteractionResult onUse(ServerLevel world, ServerPlayer player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    public InteractionResult onAttack(ServerLevel world, ServerPlayer player, InteractionHand hand, Entity entity,
            EntityHitResult hitResult) {
        return InteractionResult.PASS;
    }

}
