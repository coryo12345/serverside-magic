package servermagic.data;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

				this.shapeless(RecipeCategory.MISC, Items.LEATHER)
						.requires(Items.ROTTEN_FLESH)
						.unlockedBy(getHasName(Items.ROTTEN_FLESH), has(Items.ROTTEN_FLESH))
						.save(this.output);

				SpellbookItem si = new SpellbookItem();
				this.shapeless(RecipeCategory.TOOLS, si.getDefaultItemStack()).requires(Items.BOOK)
						.unlockedBy(getHasName(Items.BOOK), has(Items.BOOK)).save(this.output);

				// Crystalline Mana: Amethyst Shard + Lapis + 2 Glass Bottles = 2 Bottles o' Enchanting
				this.shapeless(RecipeCategory.BREWING, new ItemStack(Items.EXPERIENCE_BOTTLE, 2))
						.requires(Items.AMETHYST_SHARD)
						.requires(Items.LAPIS_LAZULI)
						.requires(Items.GLASS_BOTTLE, 2)
						.unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
						.save(this.output, "crystalline_mana");

				// Gilded Glow: Gold Ingot + Glowstone Dust + 2 Glass Bottles = 2 Bottles o' Enchanting
				this.shapeless(RecipeCategory.BREWING, new ItemStack(Items.EXPERIENCE_BOTTLE, 2))
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
