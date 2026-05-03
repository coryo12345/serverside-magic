package servermagic.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import servermagic.data.items.CustomItem;
import servermagic.data.items.LootboxItem;
import servermagic.data.items.ManaPotionItem;
import servermagic.data.items.SpellbookItem;

public class ModRecipes extends FabricRecipeProvider {
	private static final List<CustomItem> ITEMS = List.of(
			new SpellbookItem(),
			new LootboxItem(),
			new ManaPotionItem());

	public ModRecipes(FabricPackOutput output,
			CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
		return new RecipeProvider(registryLookup, exporter) {
			@Override
			public void buildRecipes() {
				for (CustomItem item : ITEMS) {
					item.buildRecipe(this.output, registryLookup);
				}
			}
		};
	}

	@Override
	public String getName() {
		return "ModRecipes";
	}
}
