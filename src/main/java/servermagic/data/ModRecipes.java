package servermagic.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import servermagic.data.items.SpellbookItem;

public class ModRecipes extends FabricRecipeProvider {
	public ModRecipes(FabricDataOutput output,
			CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
		return new RecipeProvider(registryLookup, exporter) {
			@Override
			public void buildRecipes() {
				// Is this needed?
				HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

				SpellbookItem si = new SpellbookItem();
				this.shapeless(RecipeCategory.TOOLS, si.getDefaultItemStack()).requires(Items.BOOK)
						.unlockedBy(getHasName(Items.BOOK), has(Items.BOOK)).save(this.output);

				// Build the custom Mana Potion output stack (used by both recipes)
				ItemStack manaPotion = new ItemStack(Items.EXPERIENCE_BOTTLE, 2);
				manaPotion.set(DataComponents.CUSTOM_NAME,
						Component.literal("Mana Potion")
								.withStyle(s -> s.withItalic(false).withColor(ChatFormatting.AQUA)));
				manaPotion.set(DataComponents.LORE, new ItemLore(List.of(
						Component.literal("A burst of arcane energy.")
								.withStyle(ChatFormatting.GRAY).withStyle(s -> s.withItalic(false)),
						Component.literal("Best used below level 10.")
								.withStyle(ChatFormatting.DARK_GRAY).withStyle(s -> s.withItalic(false))
				)));

				// Crystalline Mana: Amethyst Shard + Lapis + 2 Glass Bottles = 2 Mana Potions
				this.shapeless(RecipeCategory.BREWING, manaPotion.copy())
						.requires(Items.AMETHYST_SHARD)
						.requires(Items.LAPIS_LAZULI)
						.requires(Items.GLASS_BOTTLE, 2)
						.unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
						.save(this.output, "crystalline_mana");

				// Gilded Glow: Gold Ingot + Glowstone Dust + 2 Glass Bottles = 2 Mana Potions
				this.shapeless(RecipeCategory.BREWING, manaPotion.copy())
						.requires(Items.GOLD_INGOT)
						.requires(Items.GLOWSTONE_DUST)
						.requires(Items.GLASS_BOTTLE, 2)
						.unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
						.save(this.output, "gilded_glow");
			}
		};
	}

	@Override
	public String getName() {
		return "ModRecipes";
	}
}
