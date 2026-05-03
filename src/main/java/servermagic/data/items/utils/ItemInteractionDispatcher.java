package servermagic.data.items.utils;

import java.util.Map;
import java.util.Optional;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import servermagic.ServerMagic;
import servermagic.data.items.CustomItem;
import servermagic.data.items.LootboxItem;
import servermagic.data.items.SpellbookItem;

public class ItemInteractionDispatcher {
    private ServerLevel world;
    private ServerPlayer player;
    private InteractionHand hand;

    private final static Map<String, Class<? extends CustomItem>> customItemMap = Map.of(
            SpellbookItem.ID, SpellbookItem.class,
            LootboxItem.ID, LootboxItem.class);

    public ItemInteractionDispatcher(Level world, Player player, InteractionHand hand) {
        MinecraftServer server = world.getServer();
        this.world = server.getLevel(world.dimension());
        this.player = server.getPlayerList().getPlayer(player.getUUID());
        this.hand = hand;
    }

    public boolean isCustomItem() {
        ItemStack item = this.hand == InteractionHand.MAIN_HAND ? this.player.getMainHandItem()
                : this.player.getOffhandItem();
        if (item.isEmpty()) {
            return false;
        }
        CustomData data = item.get(DataComponents.CUSTOM_DATA);
        if (data == null) {
            return false;
        }
        CompoundTag tag = data.copyTag();
        if (tag.contains(CustomItem.CUSTOM_DATA_ITEM_ID_KEY)
                && customItemMap.containsKey(tag.getStringOr(CustomItem.CUSTOM_DATA_ITEM_ID_KEY, ""))) {
            return true;
        }
        return false;
    }

    public Optional<CustomItem> getHeldCustomItem() {
        ItemStack item = this.hand == InteractionHand.MAIN_HAND ? this.player.getMainHandItem()
                : this.player.getOffhandItem();
        if (item.isEmpty()) {
            return Optional.empty();
        }
        CustomData data = item.get(DataComponents.CUSTOM_DATA);
        if (data == null) {
            return Optional.empty();
        }
        CompoundTag tag = data.copyTag();
        if (tag.contains(CustomItem.CUSTOM_DATA_ITEM_ID_KEY)
                && customItemMap.containsKey(tag.getStringOr(CustomItem.CUSTOM_DATA_ITEM_ID_KEY, ""))) {
            var clazz = customItemMap.get(tag.getStringOr(CustomItem.CUSTOM_DATA_ITEM_ID_KEY, ""));
            try {
                CustomItem ci = clazz.getDeclaredConstructor().newInstance();
                return Optional.of(ci);
            } catch (Exception e) {
                ServerMagic.LOGGER.error("Failed to initialize custom item class!", e);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public InteractionResult dispatchAttack(Entity entity, EntityHitResult hitResult) {
        var item = this.getHeldCustomItem();
        if (item.isEmpty())
            return InteractionResult.PASS;
        return item.get().onAttack(world, player, hand, entity, hitResult);
    }

    public InteractionResult dispatchUse() {
        var item = this.getHeldCustomItem();
        if (item.isEmpty())
            return InteractionResult.PASS;
        return item.get().onUse(world, player, hand);
    }
}
