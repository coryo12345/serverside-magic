package servermagic.data.items;

import java.util.List;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.CraftingRecipe.CraftingBookInfo;
import net.minecraft.world.item.crafting.Recipe.CommonInfo;

public class ManaPotionItem extends CustomItem {
    public static final String ID = "mana_potion";

    @Override
    public String getItemId() {
        return ID;
    }

    @Override
    public ItemStackTemplate getDefaultItemStackTemplate() {
        return new ItemStackTemplate(Items.EXPERIENCE_BOTTLE,
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
    }

    @Override
    public void buildRecipe(RecipeOutput output, HolderLookup.Provider registryLookup) {
        ItemStackTemplate template = getDefaultItemStackTemplate();

        // Crystalline Mana: Amethyst Shard + Lapis + 2 Glass Bottles = 2 Mana Potions
        ResourceKey<Recipe<?>> crystallineKey = ResourceKey.create(Registries.RECIPE,
                Identifier.fromNamespaceAndPath("servermagic", "crystalline_mana"));
        ShapelessRecipe crystallineRecipe = new ShapelessRecipe(
                new CommonInfo(false),
                new CraftingBookInfo(CraftingBookCategory.MISC, ""),
                template,
                List.of(Ingredient.of(Items.AMETHYST_SHARD), Ingredient.of(Items.LAPIS_LAZULI),
                        Ingredient.of(Items.GLASS_BOTTLE), Ingredient.of(Items.GLASS_BOTTLE)));
        AdvancementHolder crystallineAdvancement = Advancement.Builder.advancement()
                .parent(Identifier.withDefaultNamespace("recipes/root"))
                .addCriterion("has_amethyst_shard",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.AMETHYST_SHARD))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(crystallineKey))
                .requirements(AdvancementRequirements.anyOf(List.of("has_the_recipe", "has_amethyst_shard")))
                .rewards(AdvancementRewards.Builder.recipe(crystallineKey))
                .build(Identifier.fromNamespaceAndPath("servermagic", "recipes/brewing/crystalline_mana"));
        output.accept(crystallineKey, crystallineRecipe, crystallineAdvancement);

        // Gilded Glow: Gold Ingot + Glowstone Dust + 2 Glass Bottles = 2 Mana Potions
        ResourceKey<Recipe<?>> gildedKey = ResourceKey.create(Registries.RECIPE,
                Identifier.fromNamespaceAndPath("servermagic", "gilded_glow"));
        ShapelessRecipe gildedRecipe = new ShapelessRecipe(
                new CommonInfo(false),
                new CraftingBookInfo(CraftingBookCategory.MISC, ""),
                template,
                List.of(Ingredient.of(Items.GOLD_INGOT), Ingredient.of(Items.GLOWSTONE_DUST),
                        Ingredient.of(Items.GLASS_BOTTLE), Ingredient.of(Items.GLASS_BOTTLE)));
        AdvancementHolder gildedAdvancement = Advancement.Builder.advancement()
                .parent(Identifier.withDefaultNamespace("recipes/root"))
                .addCriterion("has_gold_ingot",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(gildedKey))
                .requirements(AdvancementRequirements.anyOf(List.of("has_the_recipe", "has_gold_ingot")))
                .rewards(AdvancementRewards.Builder.recipe(gildedKey))
                .build(Identifier.fromNamespaceAndPath("servermagic", "recipes/brewing/gilded_glow"));
        output.accept(gildedKey, gildedRecipe, gildedAdvancement);
    }
}
