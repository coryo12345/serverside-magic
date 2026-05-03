package servermagic.data.items;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import servermagic.cosmetics.Cosmetic;
import servermagic.cosmetics.CosmeticSlot;
import servermagic.cosmetics.Cosmetics;
import servermagic.db.Database;
import servermagic.db.tables.CosmeticUnlock;
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
        java.util.Optional<Database> dbOpt = Database.GetDB();
        if (dbOpt.isEmpty()) return InteractionResult.PASS;
        Database db = dbOpt.get();

        String username = player.getName().getString();

        List<Cosmetic> lockedSpellbooks = Cosmetics.GetAllForSlot(CosmeticSlot.SPELLBOOK).stream()
                .filter(c -> !CosmeticUnlock.IsUnlocked(db, username, c.getId(), c.getSlot().getId()))
                .toList();

        Map<String, List<Cosmetic>> armorBySet = Cosmetics.GetAll().stream()
                .filter(c -> c.getSlot() != CosmeticSlot.SPELLBOOK)
                .collect(Collectors.groupingBy(Cosmetic::getItemModel));

        List<List<Cosmetic>> lockedArmorSets = armorBySet.values().stream()
                .filter(pieces -> pieces.stream()
                        .anyMatch(c -> !CosmeticUnlock.IsUnlocked(db, username, c.getId(), c.getSlot().getId())))
                .toList();

        if (lockedSpellbooks.isEmpty() && lockedArmorSets.isEmpty()) {
            player.sendSystemMessage(Component.literal("You have already unlocked all cosmetics!")
                    .withStyle(ChatFormatting.GOLD).withStyle(s -> s.withItalic(false)), false);
            return InteractionResult.SUCCESS;
        }

        boolean pickSpellbook;
        if (lockedSpellbooks.isEmpty()) pickSpellbook = false;
        else if (lockedArmorSets.isEmpty()) pickSpellbook = true;
        else pickSpellbook = world.getRandom().nextBoolean();

        if (pickSpellbook) {
            Cosmetic cosmetic = lockedSpellbooks.get(world.getRandom().nextInt(lockedSpellbooks.size()));
            CosmeticUnlock.Insert(db, username, cosmetic.getId(), cosmetic.getSlot().getId());
            player.sendSystemMessage(Component.literal("You unlocked: " + cosmetic.getDisplayName() + "!")
                    .withStyle(ChatFormatting.AQUA).withStyle(s -> s.withItalic(false)), false);
        } else {
            List<Cosmetic> setToUnlock = lockedArmorSets.get(world.getRandom().nextInt(lockedArmorSets.size()));
            for (Cosmetic piece : setToUnlock) {
                if (!CosmeticUnlock.IsUnlocked(db, username, piece.getId(), piece.getSlot().getId())) {
                    CosmeticUnlock.Insert(db, username, piece.getId(), piece.getSlot().getId());
                }
            }
            String setName = deriveSetName(setToUnlock.get(0).getDisplayName());
            player.sendSystemMessage(Component.literal("You unlocked: " + setName + " armor set!")
                    .withStyle(ChatFormatting.AQUA).withStyle(s -> s.withItalic(false)), false);
        }

        player.getItemInHand(hand).shrink(1);
        return InteractionResult.SUCCESS;
    }

    private String deriveSetName(String pieceName) {
        int lastSpace = pieceName.lastIndexOf(' ');
        return lastSpace > 0 ? pieceName.substring(0, lastSpace) : pieceName;
    }

    @Override
    public InteractionResult onAttack(ServerLevel world, ServerPlayer player, InteractionHand hand, Entity entity,
            EntityHitResult hitResult) {
        return super.onAttack(world, player, hand, entity, hitResult);
    }

}
