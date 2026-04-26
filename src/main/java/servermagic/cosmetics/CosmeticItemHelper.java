package servermagic.cosmetics;

import javax.annotation.Nullable;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import servermagic.ServerMagic;

public class CosmeticItemHelper {

    public static void setModel(ItemStack item, @Nullable String model) {
        if (item.isEmpty()) return;
        if (model == null) {
            item.remove(DataComponents.ITEM_MODEL);
        } else {
            try {
                item.set(DataComponents.ITEM_MODEL, Identifier.parse(model));
            } catch (Exception e) {
                ServerMagic.LOGGER.error("Failed to parse cosmetic model identifier: " + model, e);
            }
        }
    }
}
