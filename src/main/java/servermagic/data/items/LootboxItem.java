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
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.CraftingRecipe.CraftingBookInfo;
import net.minecraft.world.item.crafting.Recipe.CommonInfo;
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
                .set(DataComponents.LORE, new ItemLore(List.of(
                        Component.literal("Use to unlock a new vanity option")
                                .withStyle(ChatFormatting.GRAY).withStyle(s -> s.withItalic(false)))))
                // TODO set a unique texture
                .set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("minecraft", "diamond"))
                .build();
        return new ItemStackTemplate(Items.TRIAL_KEY, patch);
    }

    @Override
    public void buildRecipe(RecipeOutput output, HolderLookup.Provider registryLookup) {
        ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE,
                Identifier.fromNamespaceAndPath("servermagic", "base_lootbox"));

        Map<Character, Ingredient> ingredients = new LinkedHashMap<>();
        ingredients.put('F', Ingredient.of(Items.CHORUS_FRUIT));
        ingredients.put('D', Ingredient.of(Items.DIAMOND));
        ingredients.put('C', Ingredient.of(Items.CHEST));

        ShapedRecipePattern pattern = ShapedRecipePattern.of(ingredients, " F ", "DCD", " F ");
        ShapedRecipe recipe = new ShapedRecipe(
                new CommonInfo(false),
                new CraftingBookInfo(CraftingBookCategory.MISC, ""),
                pattern,
                getDefaultItemStackTemplate());

        AdvancementHolder advancement = Advancement.Builder.advancement()
                .parent(Identifier.withDefaultNamespace("recipes/root"))
                .addCriterion("has_diamond",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
                .requirements(AdvancementRequirements.anyOf(List.of("has_the_recipe", "has_diamond")))
                .rewards(AdvancementRewards.Builder.recipe(key))
                .build(Identifier.fromNamespaceAndPath("servermagic", "recipes/tools/base_lootbox"));

        output.accept(key, recipe, advancement);
    }

    @Override
    public InteractionResult onUse(ServerLevel world, ServerPlayer player, InteractionHand hand) {
        // TODO this will need to add a random cosmetic unlock for the player they have not yet unlocked.
        // When we pick a cosmetic to unlock, first do a 50/50 pick to either unlock a spellbook cosmetic, or an armor set.
        // (if the player has all of the spellbook cosmetics or all the armor cosmetics then skip that type and guarantee the other)

        // If we are giving a spellbook skin, give a random cosmetic from Cosmetics.java for the spellbook slot.
        // If we are giving an armor set, then we need to pick a random SET. You can detmine which items are in the same set based on the itemModel for the cosmetic.
        // If two pieces for an armor slot have the same item model, then they are in the same set. When we unlock a set, all cosmetics with that itemModel get unlocked.
        // So, I could unlock Lunar Staff spellbook model, OR i could unlock the "minecraft:chainmail/ar_useful" armor set (4 different pieces for this set.)

        // Once we set the unlock, create a message to send to the player in the chat to inform them what they unlocked.
        // Then delete the lootbox item from their inventory they used to trigger this. (if multipel in a stack, just remove one)

        // If the player has ALL cosmetics unlocked, do NOT consume the item, just give them a message in chat telling them they have nothing else to unlock.
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult onAttack(ServerLevel world, ServerPlayer player, InteractionHand hand, Entity entity,
            EntityHitResult hitResult) {
        return super.onAttack(world, player, hand, entity, hitResult);
    }

}
