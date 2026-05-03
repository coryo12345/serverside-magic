package servermagic.data.items;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.CraftingRecipe.CraftingBookInfo;
import net.minecraft.world.item.crafting.Recipe.CommonInfo;
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
    public ItemStackTemplate getDefaultItemStackTemplate() {
        DataComponentPatch patch = DataComponentPatch.builder()
                .set(DataComponents.CUSTOM_DATA, getCustomDataComponent())
                .set(DataComponents.ITEM_NAME, Component.literal("Spellbook"))
                .set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("servermagic", "spellbook"))
                .build();
        return new ItemStackTemplate(Items.BOOK, patch);
    }

    @Override
    public void buildRecipe(RecipeOutput output, HolderLookup.Provider registryLookup) {
        ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE,
                Identifier.fromNamespaceAndPath("servermagic", "book"));

        Map<Character, Ingredient> ingredients = new LinkedHashMap<>();
        ingredients.put('F', Ingredient.of(Items.FEATHER));
        ingredients.put('L', Ingredient.of(Items.LAPIS_LAZULI));
        ingredients.put('B', Ingredient.of(Items.ENCHANTED_BOOK));
        ingredients.put('A', Ingredient.of(Items.AMETHYST_SHARD));

        ShapedRecipePattern pattern = ShapedRecipePattern.of(ingredients, " F ", "LBL", " A ");
        ShapedRecipe recipe = new ShapedRecipe(
                new CommonInfo(false),
                new CraftingBookInfo(CraftingBookCategory.EQUIPMENT, ""),
                pattern,
                getDefaultItemStackTemplate());

        AdvancementHolder advancement = Advancement.Builder.advancement()
                .parent(Identifier.withDefaultNamespace("recipes/root"))
                .addCriterion("has_enchanted_book",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.ENCHANTED_BOOK))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
                .requirements(AdvancementRequirements.anyOf(List.of("has_the_recipe", "has_enchanted_book")))
                .rewards(AdvancementRewards.Builder.recipe(key))
                .build(Identifier.fromNamespaceAndPath("servermagic", "recipes/tools/book"));

        output.accept(key, recipe, advancement);
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
