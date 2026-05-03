package servermagic.data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.CraftingRecipe.CraftingBookInfo;
import net.minecraft.world.item.crafting.Recipe.CommonInfo;
import servermagic.data.items.LootboxItem;
import servermagic.data.items.SpellbookItem;

public class ModRecipes extends FabricRecipeProvider {
	public ModRecipes(FabricPackOutput output,
			CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
		return new RecipeProvider(registryLookup, exporter) {
			@Override
			public void buildRecipes() {
				// Spellbook: shaped recipe with custom ItemStack output.
				// ShapedRecipeBuilder.shaped() only accepts ItemLike, so we construct
				// ShapedRecipe directly.
				SpellbookItem si = new SpellbookItem();
				ResourceKey<Recipe<?>> spellbookKey = ResourceKey.create(Registries.RECIPE,
						Identifier.fromNamespaceAndPath("servermagic", "book"));

				Map<Character, Ingredient> ingredientMap = new LinkedHashMap<>();
				ingredientMap.put('F', Ingredient.of(Items.FEATHER));
				ingredientMap.put('L', Ingredient.of(Items.LAPIS_LAZULI));
				ingredientMap.put('B', Ingredient.of(Items.ENCHANTED_BOOK));
				ingredientMap.put('A', Ingredient.of(Items.AMETHYST_SHARD));

				ShapedRecipePattern spellbookPattern = ShapedRecipePattern.of(ingredientMap, " F ", "LBL", " A ");
				ShapedRecipe spellbookRecipe = new ShapedRecipe(
						new CommonInfo(false),
						new CraftingBookInfo(CraftingBookCategory.EQUIPMENT, ""),
						spellbookPattern,
						si.getDefaultItemStackTemplate());

				AdvancementHolder spellbookAdvancement = Advancement.Builder.advancement()
						.parent(Identifier.withDefaultNamespace("recipes/root"))
						.addCriterion("has_enchanted_book",
								InventoryChangeTrigger.TriggerInstance.hasItems(Items.ENCHANTED_BOOK))
						.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(spellbookKey))
						.requirements(AdvancementRequirements.anyOf(List.of("has_the_recipe", "has_enchanted_book")))
						.rewards(AdvancementRewards.Builder.recipe(spellbookKey))
						.build(Identifier.fromNamespaceAndPath("servermagic", "recipes/tools/book"));

				this.output.accept(spellbookKey, spellbookRecipe, spellbookAdvancement);

				// Lootbox: shaped recipe with custom ItemStack output.
				LootboxItem li = new LootboxItem();
				ResourceKey<Recipe<?>> lootboxKey = ResourceKey.create(Registries.RECIPE,
						Identifier.fromNamespaceAndPath("servermagic", "base_lootbox"));

				Map<Character, Ingredient> lootboxIngredientMap = new LinkedHashMap<>();
				lootboxIngredientMap.put('F', Ingredient.of(Items.CHORUS_FRUIT));
				lootboxIngredientMap.put('D', Ingredient.of(Items.DIAMOND));
				lootboxIngredientMap.put('C', Ingredient.of(Items.CHEST));

				ShapedRecipePattern lootboxPattern = ShapedRecipePattern.of(lootboxIngredientMap, " F ", "DCD", " F ");
				ShapedRecipe lootboxRecipe = new ShapedRecipe(
						new CommonInfo(false),
						new CraftingBookInfo(CraftingBookCategory.MISC, ""),
						lootboxPattern,
						li.getDefaultItemStackTemplate());

				AdvancementHolder lootboxAdvancement = Advancement.Builder.advancement()
						.parent(Identifier.withDefaultNamespace("recipes/root"))
						.addCriterion("has_diamond",
								InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
						.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(lootboxKey))
						.requirements(AdvancementRequirements.anyOf(List.of("has_the_recipe", "has_diamond")))
						.rewards(AdvancementRewards.Builder.recipe(lootboxKey))
						.build(Identifier.fromNamespaceAndPath("servermagic", "recipes/tools/base_lootbox"));

				this.output.accept(lootboxKey, lootboxRecipe, lootboxAdvancement);

				// Build the custom Mana Potion output template (used by both recipes)
				ItemStackTemplate manaPotionTemplate = new ItemStackTemplate(Items.EXPERIENCE_BOTTLE,
						DataComponentPatch.builder()
								.set(DataComponents.CUSTOM_NAME, Component.literal("Mana Potion")
										.withStyle(s -> s.withItalic(false).withColor(ChatFormatting.AQUA)))
								.set(DataComponents.LORE, new ItemLore(List.of(
										Component.literal("A burst of arcane energy.")
												.withStyle(ChatFormatting.GRAY).withStyle(s -> s.withItalic(false)),
										Component.literal("Best used below level 10.")
												.withStyle(ChatFormatting.DARK_GRAY).withStyle(s -> s.withItalic(false)))))
								.set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("servermagic", "mana_potion"))
								.build())
						.withCount(2);

				// Crystalline Mana: Amethyst Shard + Lapis + 2 Glass Bottles = 2 Mana Potions
				this.shapeless(RecipeCategory.BREWING, manaPotionTemplate)
						.requires(Items.AMETHYST_SHARD)
						.requires(Items.LAPIS_LAZULI)
						.requires(Items.GLASS_BOTTLE, 2)
						.unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
						.save(this.output, "crystalline_mana");

				// Gilded Glow: Gold Ingot + Glowstone Dust + 2 Glass Bottles = 2 Mana Potions
				this.shapeless(RecipeCategory.BREWING, manaPotionTemplate)
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
