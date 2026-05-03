package servermagic.cosmetics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import servermagic.ServerMagic;

public class Cosmetics {
    public static final Cosmetic LUNAR_STAFF = new Cosmetic(
            "lunar_staff", "Lunar Staff", CosmeticSlot.SPELLBOOK, "servermagic:lunar_staff");
    public static final Cosmetic EARTH_STAFF = new Cosmetic(
            "earth_staff", "Earth Staff", CosmeticSlot.SPELLBOOK, "servermagic:earth_staff");
    public static final Cosmetic STAR_WAND = new Cosmetic(
            "star_wand", "Star Wand", CosmeticSlot.SPELLBOOK, "servermagic:star_wand");
    public static final Cosmetic FLICKER_STAFF = new Cosmetic(
            "flicker_staff", "Flicker Staff", CosmeticSlot.SPELLBOOK, "servermagic:flicker_staff");
    public static final Cosmetic ICE_STAFF = new Cosmetic(
            "ice_staff", "Ice Staff", CosmeticSlot.SPELLBOOK, "servermagic:ice_staff");
    public static final Cosmetic POTTER_WAND = new Cosmetic(
            "potter_wand", "Magic Wand", CosmeticSlot.SPELLBOOK, "servermagic:potter_wand");
    public static final Cosmetic ELDER_WAND = new Cosmetic(
            "elder_wand", "Elder Wand", CosmeticSlot.SPELLBOOK, "servermagic:elder_wand");

    // below are generated cosmetic items, do not edit manually <generated>

    public static final Cosmetic CHAINMAIL_AR_USEFUL_HELMET = new Cosmetic(
            "chainmail/ar_useful_helmet", "Chainmail Ar Useful Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/ar_useful");
    public static final Cosmetic CHAINMAIL_AR_USEFUL_CHESTPLATE = new Cosmetic(
            "chainmail/ar_useful_chestplate", "Chainmail Ar Useful Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/ar_useful");
    public static final Cosmetic CHAINMAIL_AR_USEFUL_LEGGINGS = new Cosmetic(
            "chainmail/ar_useful_leggings", "Chainmail Ar Useful Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/ar_useful");
    public static final Cosmetic CHAINMAIL_AR_USEFUL_BOOTS = new Cosmetic(
            "chainmail/ar_useful_boots", "Chainmail Ar Useful Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/ar_useful");

    public static final Cosmetic CHAINMAIL_AR_HELMET = new Cosmetic(
            "chainmail/ar_helmet", "Chainmail Ar Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/ar");
    public static final Cosmetic CHAINMAIL_AR_CHESTPLATE = new Cosmetic(
            "chainmail/ar_chestplate", "Chainmail Ar Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/ar");
    public static final Cosmetic CHAINMAIL_AR_LEGGINGS = new Cosmetic(
            "chainmail/ar_leggings", "Chainmail Ar Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/ar");
    public static final Cosmetic CHAINMAIL_AR_BOOTS = new Cosmetic(
            "chainmail/ar_boots", "Chainmail Ar Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/ar");

    public static final Cosmetic CHAINMAIL_CHAMPION_HELMET = new Cosmetic(
            "chainmail/champion_helmet", "Chainmail Champion Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/champion");
    public static final Cosmetic CHAINMAIL_CHAMPION_CHESTPLATE = new Cosmetic(
            "chainmail/champion_chestplate", "Chainmail Champion Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/champion");
    public static final Cosmetic CHAINMAIL_CHAMPION_LEGGINGS = new Cosmetic(
            "chainmail/champion_leggings", "Chainmail Champion Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/champion");
    public static final Cosmetic CHAINMAIL_CHAMPION_BOOTS = new Cosmetic(
            "chainmail/champion_boots", "Chainmail Champion Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/champion");

    public static final Cosmetic CHAINMAIL_COMBAT_HELMET = new Cosmetic(
            "chainmail/combat_helmet", "Chainmail Combat Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/combat");
    public static final Cosmetic CHAINMAIL_COMBAT_CHESTPLATE = new Cosmetic(
            "chainmail/combat_chestplate", "Chainmail Combat Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/combat");
    public static final Cosmetic CHAINMAIL_COMBAT_LEGGINGS = new Cosmetic(
            "chainmail/combat_leggings", "Chainmail Combat Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/combat");
    public static final Cosmetic CHAINMAIL_COMBAT_BOOTS = new Cosmetic(
            "chainmail/combat_boots", "Chainmail Combat Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/combat");

    public static final Cosmetic CHAINMAIL_COMMAND_BLOCK_HELMET = new Cosmetic(
            "chainmail/command_block_helmet", "Chainmail Command Block Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/command_block");
    public static final Cosmetic CHAINMAIL_COMMAND_BLOCK_CHESTPLATE = new Cosmetic(
            "chainmail/command_block_chestplate", "Chainmail Command Block Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/command_block");
    public static final Cosmetic CHAINMAIL_COMMAND_BLOCK_LEGGINGS = new Cosmetic(
            "chainmail/command_block_leggings", "Chainmail Command Block Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/command_block");
    public static final Cosmetic CHAINMAIL_COMMAND_BLOCK_BOOTS = new Cosmetic(
            "chainmail/command_block_boots", "Chainmail Command Block Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/command_block");

    public static final Cosmetic CHAINMAIL_CREED_HELMET = new Cosmetic(
            "chainmail/creed_helmet", "Chainmail Creed Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/creed");
    public static final Cosmetic CHAINMAIL_CREED_CHESTPLATE = new Cosmetic(
            "chainmail/creed_chestplate", "Chainmail Creed Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/creed");
    public static final Cosmetic CHAINMAIL_CREED_LEGGINGS = new Cosmetic(
            "chainmail/creed_leggings", "Chainmail Creed Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/creed");
    public static final Cosmetic CHAINMAIL_CREED_BOOTS = new Cosmetic(
            "chainmail/creed_boots", "Chainmail Creed Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/creed");

    public static final Cosmetic CHAINMAIL_DARK_HELMET = new Cosmetic(
            "chainmail/dark_helmet", "Chainmail Dark Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/dark");
    public static final Cosmetic CHAINMAIL_DARK_CHESTPLATE = new Cosmetic(
            "chainmail/dark_chestplate", "Chainmail Dark Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/dark");
    public static final Cosmetic CHAINMAIL_DARK_LEGGINGS = new Cosmetic(
            "chainmail/dark_leggings", "Chainmail Dark Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/dark");
    public static final Cosmetic CHAINMAIL_DARK_BOOTS = new Cosmetic(
            "chainmail/dark_boots", "Chainmail Dark Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/dark");

    public static final Cosmetic CHAINMAIL_DRAGON_HELMET = new Cosmetic(
            "chainmail/dragon_helmet", "Chainmail Dragon Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/dragon");
    public static final Cosmetic CHAINMAIL_DRAGON_CHESTPLATE = new Cosmetic(
            "chainmail/dragon_chestplate", "Chainmail Dragon Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/dragon");
    public static final Cosmetic CHAINMAIL_DRAGON_LEGGINGS = new Cosmetic(
            "chainmail/dragon_leggings", "Chainmail Dragon Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/dragon");
    public static final Cosmetic CHAINMAIL_DRAGON_BOOTS = new Cosmetic(
            "chainmail/dragon_boots", "Chainmail Dragon Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/dragon");

    public static final Cosmetic CHAINMAIL_DROWNED_KING_HELMET = new Cosmetic(
            "chainmail/drowned_king_helmet", "Chainmail Drowned King Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/drowned_king");
    public static final Cosmetic CHAINMAIL_DROWNED_KING_CHESTPLATE = new Cosmetic(
            "chainmail/drowned_king_chestplate", "Chainmail Drowned King Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/drowned_king");
    public static final Cosmetic CHAINMAIL_DROWNED_KING_LEGGINGS = new Cosmetic(
            "chainmail/drowned_king_leggings", "Chainmail Drowned King Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/drowned_king");
    public static final Cosmetic CHAINMAIL_DROWNED_KING_BOOTS = new Cosmetic(
            "chainmail/drowned_king_boots", "Chainmail Drowned King Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/drowned_king");

    public static final Cosmetic CHAINMAIL_EMERALD_HELMET = new Cosmetic(
            "chainmail/emerald_helmet", "Chainmail Emerald Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/emerald");
    public static final Cosmetic CHAINMAIL_EMERALD_CHESTPLATE = new Cosmetic(
            "chainmail/emerald_chestplate", "Chainmail Emerald Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/emerald");
    public static final Cosmetic CHAINMAIL_EMERALD_LEGGINGS = new Cosmetic(
            "chainmail/emerald_leggings", "Chainmail Emerald Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/emerald");
    public static final Cosmetic CHAINMAIL_EMERALD_BOOTS = new Cosmetic(
            "chainmail/emerald_boots", "Chainmail Emerald Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/emerald");

    public static final Cosmetic CHAINMAIL_ENDER_HELMET = new Cosmetic(
            "chainmail/ender_helmet", "Chainmail Ender Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/ender");
    public static final Cosmetic CHAINMAIL_ENDER_CHESTPLATE = new Cosmetic(
            "chainmail/ender_chestplate", "Chainmail Ender Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/ender");
    public static final Cosmetic CHAINMAIL_ENDER_LEGGINGS = new Cosmetic(
            "chainmail/ender_leggings", "Chainmail Ender Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/ender");
    public static final Cosmetic CHAINMAIL_ENDER_BOOTS = new Cosmetic(
            "chainmail/ender_boots", "Chainmail Ender Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/ender");

    public static final Cosmetic CHAINMAIL_EXPOSED_COMBAT_HELMET = new Cosmetic(
            "chainmail/exposed_combat_helmet", "Exposed Combat Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/exposed_combat");
    public static final Cosmetic CHAINMAIL_EXPOSED_COMBAT_CHESTPLATE = new Cosmetic(
            "chainmail/exposed_combat_chestplate", "Exposed Combat Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/exposed_combat");
    public static final Cosmetic CHAINMAIL_EXPOSED_COMBAT_LEGGINGS = new Cosmetic(
            "chainmail/exposed_combat_leggings", "Exposed Combat Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/exposed_combat");
    public static final Cosmetic CHAINMAIL_EXPOSED_COMBAT_BOOTS = new Cosmetic(
            "chainmail/exposed_combat_boots", "Exposed Combat Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/exposed_combat");

    public static final Cosmetic CHAINMAIL_EXPOSED_TRIAL_HELMET = new Cosmetic(
            "chainmail/exposed_trial_helmet", "Exposed Trial Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/exposed_trial");
    public static final Cosmetic CHAINMAIL_EXPOSED_TRIAL_CHESTPLATE = new Cosmetic(
            "chainmail/exposed_trial_chestplate", "Exposed Trial Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/exposed_trial");
    public static final Cosmetic CHAINMAIL_EXPOSED_TRIAL_LEGGINGS = new Cosmetic(
            "chainmail/exposed_trial_leggings", "Exposed Trial Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/exposed_trial");
    public static final Cosmetic CHAINMAIL_EXPOSED_TRIAL_BOOTS = new Cosmetic(
            "chainmail/exposed_trial_boots", "Exposed Trial Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/exposed_trial");

    public static final Cosmetic CHAINMAIL_FOX_HELMET = new Cosmetic(
            "chainmail/fox_helmet", "Chainmail Fox Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/fox");
    public static final Cosmetic CHAINMAIL_FOX_CHESTPLATE = new Cosmetic(
            "chainmail/fox_chestplate", "Chainmail Fox Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/fox");
    public static final Cosmetic CHAINMAIL_FOX_LEGGINGS = new Cosmetic(
            "chainmail/fox_leggings", "Chainmail Fox Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/fox");
    public static final Cosmetic CHAINMAIL_FOX_BOOTS = new Cosmetic(
            "chainmail/fox_boots", "Chainmail Fox Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/fox");

    public static final Cosmetic CHAINMAIL_FULL_METAL_HELMET = new Cosmetic(
            "chainmail/full_metal_helmet", "Chainmail Full Metal Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/full_metal");
    public static final Cosmetic CHAINMAIL_FULL_METAL_CHESTPLATE = new Cosmetic(
            "chainmail/full_metal_chestplate", "Chainmail Full Metal Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/full_metal");
    public static final Cosmetic CHAINMAIL_FULL_METAL_LEGGINGS = new Cosmetic(
            "chainmail/full_metal_leggings", "Chainmail Full Metal Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/full_metal");
    public static final Cosmetic CHAINMAIL_FULL_METAL_BOOTS = new Cosmetic(
            "chainmail/full_metal_boots", "Chainmail Full Metal Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/full_metal");

    public static final Cosmetic CHAINMAIL_GHOSTLY_HELMET = new Cosmetic(
            "chainmail/ghostly_helmet", "Chainmail Ghostly Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/ghostly");
    public static final Cosmetic CHAINMAIL_GHOSTLY_CHESTPLATE = new Cosmetic(
            "chainmail/ghostly_chestplate", "Chainmail Ghostly Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/ghostly");
    public static final Cosmetic CHAINMAIL_GHOSTLY_LEGGINGS = new Cosmetic(
            "chainmail/ghostly_leggings", "Chainmail Ghostly Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/ghostly");
    public static final Cosmetic CHAINMAIL_GHOSTLY_BOOTS = new Cosmetic(
            "chainmail/ghostly_boots", "Chainmail Ghostly Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/ghostly");

    public static final Cosmetic CHAINMAIL_GRIM_HELMET = new Cosmetic(
            "chainmail/grim_helmet", "Chainmail Grim Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/grim");
    public static final Cosmetic CHAINMAIL_GRIM_CHESTPLATE = new Cosmetic(
            "chainmail/grim_chestplate", "Chainmail Grim Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/grim");
    public static final Cosmetic CHAINMAIL_GRIM_LEGGINGS = new Cosmetic(
            "chainmail/grim_leggings", "Chainmail Grim Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/grim");
    public static final Cosmetic CHAINMAIL_GRIM_BOOTS = new Cosmetic(
            "chainmail/grim_boots", "Chainmail Grim Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/grim");

    public static final Cosmetic CHAINMAIL_GUARD_HELMET = new Cosmetic(
            "chainmail/guard_helmet", "Chainmail Guard Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/guard");
    public static final Cosmetic CHAINMAIL_GUARD_CHESTPLATE = new Cosmetic(
            "chainmail/guard_chestplate", "Chainmail Guard Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/guard");
    public static final Cosmetic CHAINMAIL_GUARD_LEGGINGS = new Cosmetic(
            "chainmail/guard_leggings", "Chainmail Guard Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/guard");
    public static final Cosmetic CHAINMAIL_GUARD_BOOTS = new Cosmetic(
            "chainmail/guard_boots", "Chainmail Guard Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/guard");

    public static final Cosmetic CHAINMAIL_HIGHLAND_HELMET = new Cosmetic(
            "chainmail/highland_helmet", "Chainmail Highland Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/highland");
    public static final Cosmetic CHAINMAIL_HIGHLAND_CHESTPLATE = new Cosmetic(
            "chainmail/highland_chestplate", "Chainmail Highland Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/highland");
    public static final Cosmetic CHAINMAIL_HIGHLAND_LEGGINGS = new Cosmetic(
            "chainmail/highland_leggings", "Chainmail Highland Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/highland");
    public static final Cosmetic CHAINMAIL_HIGHLAND_BOOTS = new Cosmetic(
            "chainmail/highland_boots", "Chainmail Highland Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/highland");

    public static final Cosmetic CHAINMAIL_LAVA_INFUSED_HELMET = new Cosmetic(
            "chainmail/lava_infused_helmet", "Chainmail Lava Infused Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/lava_infused");
    public static final Cosmetic CHAINMAIL_LAVA_INFUSED_CHESTPLATE = new Cosmetic(
            "chainmail/lava_infused_chestplate", "Chainmail Lava Infused Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/lava_infused");
    public static final Cosmetic CHAINMAIL_LAVA_INFUSED_LEGGINGS = new Cosmetic(
            "chainmail/lava_infused_leggings", "Chainmail Lava Infused Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/lava_infused");
    public static final Cosmetic CHAINMAIL_LAVA_INFUSED_BOOTS = new Cosmetic(
            "chainmail/lava_infused_boots", "Chainmail Lava Infused Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/lava_infused");

    public static final Cosmetic CHAINMAIL_MYSTERY_HELMET = new Cosmetic(
            "chainmail/mystery_helmet", "Chainmail Mystery Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/mystery");
    public static final Cosmetic CHAINMAIL_MYSTERY_CHESTPLATE = new Cosmetic(
            "chainmail/mystery_chestplate", "Chainmail Mystery Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/mystery");
    public static final Cosmetic CHAINMAIL_MYSTERY_LEGGINGS = new Cosmetic(
            "chainmail/mystery_leggings", "Chainmail Mystery Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/mystery");
    public static final Cosmetic CHAINMAIL_MYSTERY_BOOTS = new Cosmetic(
            "chainmail/mystery_boots", "Chainmail Mystery Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/mystery");

    public static final Cosmetic CHAINMAIL_NAMELESS_HELMET = new Cosmetic(
            "chainmail/nameless_helmet", "Chainmail Nameless Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/nameless");
    public static final Cosmetic CHAINMAIL_NAMELESS_CHESTPLATE = new Cosmetic(
            "chainmail/nameless_chestplate", "Chainmail Nameless Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/nameless");
    public static final Cosmetic CHAINMAIL_NAMELESS_LEGGINGS = new Cosmetic(
            "chainmail/nameless_leggings", "Chainmail Nameless Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/nameless");
    public static final Cosmetic CHAINMAIL_NAMELESS_BOOTS = new Cosmetic(
            "chainmail/nameless_boots", "Chainmail Nameless Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/nameless");

    public static final Cosmetic CHAINMAIL_NECROMANCER_HELMET = new Cosmetic(
            "chainmail/necromancer_helmet", "Chainmail Necromancer Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/necromancer");
    public static final Cosmetic CHAINMAIL_NECROMANCER_CHESTPLATE = new Cosmetic(
            "chainmail/necromancer_chestplate", "Chainmail Necromancer Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/necromancer");
    public static final Cosmetic CHAINMAIL_NECROMANCER_LEGGINGS = new Cosmetic(
            "chainmail/necromancer_leggings", "Chainmail Necromancer Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/necromancer");
    public static final Cosmetic CHAINMAIL_NECROMANCER_BOOTS = new Cosmetic(
            "chainmail/necromancer_boots", "Chainmail Necromancer Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/necromancer");

    public static final Cosmetic CHAINMAIL_NETHERWALKER_HELMET = new Cosmetic(
            "chainmail/netherwalker_helmet", "Chainmail Netherwalker Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/netherwalker");
    public static final Cosmetic CHAINMAIL_NETHERWALKER_CHESTPLATE = new Cosmetic(
            "chainmail/netherwalker_chestplate", "Chainmail Netherwalker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/netherwalker");
    public static final Cosmetic CHAINMAIL_NETHERWALKER_LEGGINGS = new Cosmetic(
            "chainmail/netherwalker_leggings", "Chainmail Netherwalker Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/netherwalker");
    public static final Cosmetic CHAINMAIL_NETHERWALKER_BOOTS = new Cosmetic(
            "chainmail/netherwalker_boots", "Chainmail Netherwalker Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/netherwalker");

    public static final Cosmetic CHAINMAIL_OCELOT_HELMET = new Cosmetic(
            "chainmail/ocelot_helmet", "Chainmail Ocelot Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/ocelot");
    public static final Cosmetic CHAINMAIL_OCELOT_CHESTPLATE = new Cosmetic(
            "chainmail/ocelot_chestplate", "Chainmail Ocelot Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/ocelot");
    public static final Cosmetic CHAINMAIL_OCELOT_LEGGINGS = new Cosmetic(
            "chainmail/ocelot_leggings", "Chainmail Ocelot Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/ocelot");
    public static final Cosmetic CHAINMAIL_OCELOT_BOOTS = new Cosmetic(
            "chainmail/ocelot_boots", "Chainmail Ocelot Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/ocelot");

    public static final Cosmetic CHAINMAIL_OPULENT_HELMET = new Cosmetic(
            "chainmail/opulent_helmet", "Chainmail Opulent Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/opulent");
    public static final Cosmetic CHAINMAIL_OPULENT_CHESTPLATE = new Cosmetic(
            "chainmail/opulent_chestplate", "Chainmail Opulent Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/opulent");
    public static final Cosmetic CHAINMAIL_OPULENT_LEGGINGS = new Cosmetic(
            "chainmail/opulent_leggings", "Chainmail Opulent Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/opulent");
    public static final Cosmetic CHAINMAIL_OPULENT_BOOTS = new Cosmetic(
            "chainmail/opulent_boots", "Chainmail Opulent Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/opulent");

    public static final Cosmetic CHAINMAIL_ORE_HELMET = new Cosmetic(
            "chainmail/ore_helmet", "Chainmail Ore Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/ore");
    public static final Cosmetic CHAINMAIL_ORE_CHESTPLATE = new Cosmetic(
            "chainmail/ore_chestplate", "Chainmail Ore Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/ore");
    public static final Cosmetic CHAINMAIL_ORE_LEGGINGS = new Cosmetic(
            "chainmail/ore_leggings", "Chainmail Ore Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/ore");
    public static final Cosmetic CHAINMAIL_ORE_BOOTS = new Cosmetic(
            "chainmail/ore_boots", "Chainmail Ore Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/ore");

    public static final Cosmetic CHAINMAIL_OXIDIZED_COMBAT_HELMET = new Cosmetic(
            "chainmail/oxidized_combat_helmet", "Oxidized Combat Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/oxidized_combat");
    public static final Cosmetic CHAINMAIL_OXIDIZED_COMBAT_CHESTPLATE = new Cosmetic(
            "chainmail/oxidized_combat_chestplate", "Oxidized Combat Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/oxidized_combat");
    public static final Cosmetic CHAINMAIL_OXIDIZED_COMBAT_LEGGINGS = new Cosmetic(
            "chainmail/oxidized_combat_leggings", "Oxidized Combat Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/oxidized_combat");
    public static final Cosmetic CHAINMAIL_OXIDIZED_COMBAT_BOOTS = new Cosmetic(
            "chainmail/oxidized_combat_boots", "Oxidized Combat Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/oxidized_combat");

    public static final Cosmetic CHAINMAIL_OXIDIZED_TRIAL_HELMET = new Cosmetic(
            "chainmail/oxidized_trial_helmet", "Oxidized Trial Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/oxidized_trial");
    public static final Cosmetic CHAINMAIL_OXIDIZED_TRIAL_CHESTPLATE = new Cosmetic(
            "chainmail/oxidized_trial_chestplate", "Oxidized Trial Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/oxidized_trial");
    public static final Cosmetic CHAINMAIL_OXIDIZED_TRIAL_LEGGINGS = new Cosmetic(
            "chainmail/oxidized_trial_leggings", "Oxidized Trial Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/oxidized_trial");
    public static final Cosmetic CHAINMAIL_OXIDIZED_TRIAL_BOOTS = new Cosmetic(
            "chainmail/oxidized_trial_boots", "Oxidized Trial Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/oxidized_trial");

    public static final Cosmetic CHAINMAIL_PAMA_HELMET = new Cosmetic(
            "chainmail/pama_helmet", "Chainmail Pama Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/pama");
    public static final Cosmetic CHAINMAIL_PAMA_CHESTPLATE = new Cosmetic(
            "chainmail/pama_chestplate", "Chainmail Pama Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/pama");
    public static final Cosmetic CHAINMAIL_PAMA_LEGGINGS = new Cosmetic(
            "chainmail/pama_leggings", "Chainmail Pama Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/pama");
    public static final Cosmetic CHAINMAIL_PAMA_BOOTS = new Cosmetic(
            "chainmail/pama_boots", "Chainmail Pama Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/pama");

    public static final Cosmetic CHAINMAIL_PHANTOM_HELMET = new Cosmetic(
            "chainmail/phantom_helmet", "Chainmail Phantom Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/phantom");
    public static final Cosmetic CHAINMAIL_PHANTOM_CHESTPLATE = new Cosmetic(
            "chainmail/phantom_chestplate", "Chainmail Phantom Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/phantom");
    public static final Cosmetic CHAINMAIL_PHANTOM_LEGGINGS = new Cosmetic(
            "chainmail/phantom_leggings", "Chainmail Phantom Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/phantom");
    public static final Cosmetic CHAINMAIL_PHANTOM_BOOTS = new Cosmetic(
            "chainmail/phantom_boots", "Chainmail Phantom Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/phantom");

    public static final Cosmetic CHAINMAIL_PIGLIN_HELMET = new Cosmetic(
            "chainmail/piglin_helmet", "Chainmail Piglin Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/piglin");
    public static final Cosmetic CHAINMAIL_PIGLIN_CHESTPLATE = new Cosmetic(
            "chainmail/piglin_chestplate", "Chainmail Piglin Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/piglin");
    public static final Cosmetic CHAINMAIL_PIGLIN_LEGGINGS = new Cosmetic(
            "chainmail/piglin_leggings", "Chainmail Piglin Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/piglin");
    public static final Cosmetic CHAINMAIL_PIGLIN_BOOTS = new Cosmetic(
            "chainmail/piglin_boots", "Chainmail Piglin Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/piglin");

    public static final Cosmetic CHAINMAIL_PLATE_HELMET = new Cosmetic(
            "chainmail/plate_helmet", "Chainmail Plate Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/plate");
    public static final Cosmetic CHAINMAIL_PLATE_CHESTPLATE = new Cosmetic(
            "chainmail/plate_chestplate", "Chainmail Plate Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/plate");
    public static final Cosmetic CHAINMAIL_PLATE_LEGGINGS = new Cosmetic(
            "chainmail/plate_leggings", "Chainmail Plate Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/plate");
    public static final Cosmetic CHAINMAIL_PLATE_BOOTS = new Cosmetic(
            "chainmail/plate_boots", "Chainmail Plate Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/plate");

    public static final Cosmetic CHAINMAIL_PROUD_HELMET = new Cosmetic(
            "chainmail/proud_helmet", "Chainmail Proud Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/proud");
    public static final Cosmetic CHAINMAIL_PROUD_CHESTPLATE = new Cosmetic(
            "chainmail/proud_chestplate", "Chainmail Proud Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/proud");
    public static final Cosmetic CHAINMAIL_PROUD_LEGGINGS = new Cosmetic(
            "chainmail/proud_leggings", "Chainmail Proud Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/proud");
    public static final Cosmetic CHAINMAIL_PROUD_BOOTS = new Cosmetic(
            "chainmail/proud_boots", "Chainmail Proud Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/proud");

    public static final Cosmetic CHAINMAIL_RENEGADE_HELMET = new Cosmetic(
            "chainmail/renegade_helmet", "Chainmail Renegade Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/renegade");
    public static final Cosmetic CHAINMAIL_RENEGADE_CHESTPLATE = new Cosmetic(
            "chainmail/renegade_chestplate", "Chainmail Renegade Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/renegade");
    public static final Cosmetic CHAINMAIL_RENEGADE_LEGGINGS = new Cosmetic(
            "chainmail/renegade_leggings", "Chainmail Renegade Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/renegade");
    public static final Cosmetic CHAINMAIL_RENEGADE_BOOTS = new Cosmetic(
            "chainmail/renegade_boots", "Chainmail Renegade Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/renegade");

    public static final Cosmetic CHAINMAIL_ROYAL_HELMET = new Cosmetic(
            "chainmail/royal_helmet", "Chainmail Royal Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/royal");
    public static final Cosmetic CHAINMAIL_ROYAL_CHESTPLATE = new Cosmetic(
            "chainmail/royal_chestplate", "Chainmail Royal Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/royal");
    public static final Cosmetic CHAINMAIL_ROYAL_LEGGINGS = new Cosmetic(
            "chainmail/royal_leggings", "Chainmail Royal Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/royal");
    public static final Cosmetic CHAINMAIL_ROYAL_BOOTS = new Cosmetic(
            "chainmail/royal_boots", "Chainmail Royal Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/royal");

    public static final Cosmetic CHAINMAIL_RUBY_HELMET = new Cosmetic(
            "chainmail/ruby_helmet", "Chainmail Ruby Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/ruby");
    public static final Cosmetic CHAINMAIL_RUBY_CHESTPLATE = new Cosmetic(
            "chainmail/ruby_chestplate", "Chainmail Ruby Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/ruby");
    public static final Cosmetic CHAINMAIL_RUBY_LEGGINGS = new Cosmetic(
            "chainmail/ruby_leggings", "Chainmail Ruby Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/ruby");
    public static final Cosmetic CHAINMAIL_RUBY_BOOTS = new Cosmetic(
            "chainmail/ruby_boots", "Chainmail Ruby Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/ruby");

    public static final Cosmetic CHAINMAIL_SHOGUN_HELMET = new Cosmetic(
            "chainmail/shogun_helmet", "Chainmail Shogun Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/shogun");
    public static final Cosmetic CHAINMAIL_SHOGUN_CHESTPLATE = new Cosmetic(
            "chainmail/shogun_chestplate", "Chainmail Shogun Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/shogun");
    public static final Cosmetic CHAINMAIL_SHOGUN_LEGGINGS = new Cosmetic(
            "chainmail/shogun_leggings", "Chainmail Shogun Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/shogun");
    public static final Cosmetic CHAINMAIL_SHOGUN_BOOTS = new Cosmetic(
            "chainmail/shogun_boots", "Chainmail Shogun Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/shogun");

    public static final Cosmetic CHAINMAIL_SPELUNKER_HELMET = new Cosmetic(
            "chainmail/spelunker_helmet", "Chainmail Spelunker Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/spelunker");
    public static final Cosmetic CHAINMAIL_SPELUNKER_CHESTPLATE = new Cosmetic(
            "chainmail/spelunker_chestplate", "Chainmail Spelunker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/spelunker");
    public static final Cosmetic CHAINMAIL_SPELUNKER_LEGGINGS = new Cosmetic(
            "chainmail/spelunker_leggings", "Chainmail Spelunker Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/spelunker");
    public static final Cosmetic CHAINMAIL_SPELUNKER_BOOTS = new Cosmetic(
            "chainmail/spelunker_boots", "Chainmail Spelunker Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/spelunker");

    public static final Cosmetic CHAINMAIL_SPLEEF_HELMET = new Cosmetic(
            "chainmail/spleef_helmet", "Chainmail Spleef Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/spleef");
    public static final Cosmetic CHAINMAIL_SPLEEF_CHESTPLATE = new Cosmetic(
            "chainmail/spleef_chestplate", "Chainmail Spleef Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/spleef");
    public static final Cosmetic CHAINMAIL_SPLEEF_LEGGINGS = new Cosmetic(
            "chainmail/spleef_leggings", "Chainmail Spleef Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/spleef");
    public static final Cosmetic CHAINMAIL_SPLEEF_BOOTS = new Cosmetic(
            "chainmail/spleef_boots", "Chainmail Spleef Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/spleef");

    public static final Cosmetic CHAINMAIL_STALWART_HELMET = new Cosmetic(
            "chainmail/stalwart_helmet", "Chainmail Stalwart Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/stalwart");
    public static final Cosmetic CHAINMAIL_STALWART_CHESTPLATE = new Cosmetic(
            "chainmail/stalwart_chestplate", "Chainmail Stalwart Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/stalwart");
    public static final Cosmetic CHAINMAIL_STALWART_LEGGINGS = new Cosmetic(
            "chainmail/stalwart_leggings", "Chainmail Stalwart Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/stalwart");
    public static final Cosmetic CHAINMAIL_STALWART_BOOTS = new Cosmetic(
            "chainmail/stalwart_boots", "Chainmail Stalwart Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/stalwart");

    public static final Cosmetic CHAINMAIL_THIEF_HELMET = new Cosmetic(
            "chainmail/thief_helmet", "Chainmail Thief Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/thief");
    public static final Cosmetic CHAINMAIL_THIEF_CHESTPLATE = new Cosmetic(
            "chainmail/thief_chestplate", "Chainmail Thief Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/thief");
    public static final Cosmetic CHAINMAIL_THIEF_LEGGINGS = new Cosmetic(
            "chainmail/thief_leggings", "Chainmail Thief Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/thief");
    public static final Cosmetic CHAINMAIL_THIEF_BOOTS = new Cosmetic(
            "chainmail/thief_boots", "Chainmail Thief Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/thief");

    public static final Cosmetic CHAINMAIL_TRIAL_HELMET = new Cosmetic(
            "chainmail/trial_helmet", "Chainmail Trial Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/trial");
    public static final Cosmetic CHAINMAIL_TRIAL_CHESTPLATE = new Cosmetic(
            "chainmail/trial_chestplate", "Chainmail Trial Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/trial");
    public static final Cosmetic CHAINMAIL_TRIAL_LEGGINGS = new Cosmetic(
            "chainmail/trial_leggings", "Chainmail Trial Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/trial");
    public static final Cosmetic CHAINMAIL_TRIAL_BOOTS = new Cosmetic(
            "chainmail/trial_boots", "Chainmail Trial Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/trial");

    public static final Cosmetic CHAINMAIL_VEMI_HELMET = new Cosmetic(
            "chainmail/vemi_helmet", "Chainmail Vemi Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/vemi");
    public static final Cosmetic CHAINMAIL_VEMI_CHESTPLATE = new Cosmetic(
            "chainmail/vemi_chestplate", "Chainmail Vemi Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/vemi");
    public static final Cosmetic CHAINMAIL_VEMI_LEGGINGS = new Cosmetic(
            "chainmail/vemi_leggings", "Chainmail Vemi Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/vemi");
    public static final Cosmetic CHAINMAIL_VEMI_BOOTS = new Cosmetic(
            "chainmail/vemi_boots", "Chainmail Vemi Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/vemi");

    public static final Cosmetic CHAINMAIL_WEATHERED_COMBAT_HELMET = new Cosmetic(
            "chainmail/weathered_combat_helmet", "Weathered Combat Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/weathered_combat");
    public static final Cosmetic CHAINMAIL_WEATHERED_COMBAT_CHESTPLATE = new Cosmetic(
            "chainmail/weathered_combat_chestplate", "Weathered Combat Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/weathered_combat");
    public static final Cosmetic CHAINMAIL_WEATHERED_COMBAT_LEGGINGS = new Cosmetic(
            "chainmail/weathered_combat_leggings", "Weathered Combat Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/weathered_combat");
    public static final Cosmetic CHAINMAIL_WEATHERED_COMBAT_BOOTS = new Cosmetic(
            "chainmail/weathered_combat_boots", "Weathered Combat Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/weathered_combat");

    public static final Cosmetic CHAINMAIL_WEATHERED_TRIAL_HELMET = new Cosmetic(
            "chainmail/weathered_trial_helmet", "Weathered Trial Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/weathered_trial");
    public static final Cosmetic CHAINMAIL_WEATHERED_TRIAL_CHESTPLATE = new Cosmetic(
            "chainmail/weathered_trial_chestplate", "Weathered Trial Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/weathered_trial");
    public static final Cosmetic CHAINMAIL_WEATHERED_TRIAL_LEGGINGS = new Cosmetic(
            "chainmail/weathered_trial_leggings", "Weathered Trial Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/weathered_trial");
    public static final Cosmetic CHAINMAIL_WEATHERED_TRIAL_BOOTS = new Cosmetic(
            "chainmail/weathered_trial_boots", "Weathered Trial Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/weathered_trial");

    public static final Cosmetic CHAINMAIL_WOLF_HELMET = new Cosmetic(
            "chainmail/wolf_helmet", "Chainmail Wolf Helmet", CosmeticSlot.HELMET, "minecraft:chainmail/wolf");
    public static final Cosmetic CHAINMAIL_WOLF_CHESTPLATE = new Cosmetic(
            "chainmail/wolf_chestplate", "Chainmail Wolf Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:chainmail/wolf");
    public static final Cosmetic CHAINMAIL_WOLF_LEGGINGS = new Cosmetic(
            "chainmail/wolf_leggings", "Chainmail Wolf Leggings", CosmeticSlot.LEGGINGS, "minecraft:chainmail/wolf");
    public static final Cosmetic CHAINMAIL_WOLF_BOOTS = new Cosmetic(
            "chainmail/wolf_boots", "Chainmail Wolf Boots", CosmeticSlot.BOOTS, "minecraft:chainmail/wolf");

    public static final Cosmetic IRON_AR_USEFUL_HELMET = new Cosmetic(
            "iron/ar_useful_helmet", "Iron Ar Useful Helmet", CosmeticSlot.HELMET, "minecraft:iron/ar_useful");
    public static final Cosmetic IRON_AR_USEFUL_CHESTPLATE = new Cosmetic(
            "iron/ar_useful_chestplate", "Iron Ar Useful Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/ar_useful");
    public static final Cosmetic IRON_AR_USEFUL_LEGGINGS = new Cosmetic(
            "iron/ar_useful_leggings", "Iron Ar Useful Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/ar_useful");
    public static final Cosmetic IRON_AR_USEFUL_BOOTS = new Cosmetic(
            "iron/ar_useful_boots", "Iron Ar Useful Boots", CosmeticSlot.BOOTS, "minecraft:iron/ar_useful");

    public static final Cosmetic IRON_AR_HELMET = new Cosmetic(
            "iron/ar_helmet", "Iron Ar Helmet", CosmeticSlot.HELMET, "minecraft:iron/ar");
    public static final Cosmetic IRON_AR_CHESTPLATE = new Cosmetic(
            "iron/ar_chestplate", "Iron Ar Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/ar");
    public static final Cosmetic IRON_AR_LEGGINGS = new Cosmetic(
            "iron/ar_leggings", "Iron Ar Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/ar");
    public static final Cosmetic IRON_AR_BOOTS = new Cosmetic(
            "iron/ar_boots", "Iron Ar Boots", CosmeticSlot.BOOTS, "minecraft:iron/ar");

    public static final Cosmetic IRON_CHAMPION_HELMET = new Cosmetic(
            "iron/champion_helmet", "Iron Champion Helmet", CosmeticSlot.HELMET, "minecraft:iron/champion");
    public static final Cosmetic IRON_CHAMPION_CHESTPLATE = new Cosmetic(
            "iron/champion_chestplate", "Iron Champion Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/champion");
    public static final Cosmetic IRON_CHAMPION_LEGGINGS = new Cosmetic(
            "iron/champion_leggings", "Iron Champion Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/champion");
    public static final Cosmetic IRON_CHAMPION_BOOTS = new Cosmetic(
            "iron/champion_boots", "Iron Champion Boots", CosmeticSlot.BOOTS, "minecraft:iron/champion");

    public static final Cosmetic IRON_COMBAT_HELMET = new Cosmetic(
            "iron/combat_helmet", "Iron Combat Helmet", CosmeticSlot.HELMET, "minecraft:iron/combat");
    public static final Cosmetic IRON_COMBAT_CHESTPLATE = new Cosmetic(
            "iron/combat_chestplate", "Iron Combat Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/combat");
    public static final Cosmetic IRON_COMBAT_LEGGINGS = new Cosmetic(
            "iron/combat_leggings", "Iron Combat Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/combat");
    public static final Cosmetic IRON_COMBAT_BOOTS = new Cosmetic(
            "iron/combat_boots", "Iron Combat Boots", CosmeticSlot.BOOTS, "minecraft:iron/combat");

    public static final Cosmetic IRON_COMMAND_BLOCK_HELMET = new Cosmetic(
            "iron/command_block_helmet", "Iron Command Block Helmet", CosmeticSlot.HELMET, "minecraft:iron/command_block");
    public static final Cosmetic IRON_COMMAND_BLOCK_CHESTPLATE = new Cosmetic(
            "iron/command_block_chestplate", "Iron Command Block Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/command_block");
    public static final Cosmetic IRON_COMMAND_BLOCK_LEGGINGS = new Cosmetic(
            "iron/command_block_leggings", "Iron Command Block Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/command_block");
    public static final Cosmetic IRON_COMMAND_BLOCK_BOOTS = new Cosmetic(
            "iron/command_block_boots", "Iron Command Block Boots", CosmeticSlot.BOOTS, "minecraft:iron/command_block");

    public static final Cosmetic IRON_CREED_HELMET = new Cosmetic(
            "iron/creed_helmet", "Iron Creed Helmet", CosmeticSlot.HELMET, "minecraft:iron/creed");
    public static final Cosmetic IRON_CREED_CHESTPLATE = new Cosmetic(
            "iron/creed_chestplate", "Iron Creed Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/creed");
    public static final Cosmetic IRON_CREED_LEGGINGS = new Cosmetic(
            "iron/creed_leggings", "Iron Creed Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/creed");
    public static final Cosmetic IRON_CREED_BOOTS = new Cosmetic(
            "iron/creed_boots", "Iron Creed Boots", CosmeticSlot.BOOTS, "minecraft:iron/creed");

    public static final Cosmetic IRON_DARK_HELMET = new Cosmetic(
            "iron/dark_helmet", "Iron Dark Helmet", CosmeticSlot.HELMET, "minecraft:iron/dark");
    public static final Cosmetic IRON_DARK_CHESTPLATE = new Cosmetic(
            "iron/dark_chestplate", "Iron Dark Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/dark");
    public static final Cosmetic IRON_DARK_LEGGINGS = new Cosmetic(
            "iron/dark_leggings", "Iron Dark Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/dark");
    public static final Cosmetic IRON_DARK_BOOTS = new Cosmetic(
            "iron/dark_boots", "Iron Dark Boots", CosmeticSlot.BOOTS, "minecraft:iron/dark");

    public static final Cosmetic IRON_DRAGON_HELMET = new Cosmetic(
            "iron/dragon_helmet", "Iron Dragon Helmet", CosmeticSlot.HELMET, "minecraft:iron/dragon");
    public static final Cosmetic IRON_DRAGON_CHESTPLATE = new Cosmetic(
            "iron/dragon_chestplate", "Iron Dragon Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/dragon");
    public static final Cosmetic IRON_DRAGON_LEGGINGS = new Cosmetic(
            "iron/dragon_leggings", "Iron Dragon Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/dragon");
    public static final Cosmetic IRON_DRAGON_BOOTS = new Cosmetic(
            "iron/dragon_boots", "Iron Dragon Boots", CosmeticSlot.BOOTS, "minecraft:iron/dragon");

    public static final Cosmetic IRON_EMERALD_HELMET = new Cosmetic(
            "iron/emerald_helmet", "Iron Emerald Helmet", CosmeticSlot.HELMET, "minecraft:iron/emerald");
    public static final Cosmetic IRON_EMERALD_CHESTPLATE = new Cosmetic(
            "iron/emerald_chestplate", "Iron Emerald Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/emerald");
    public static final Cosmetic IRON_EMERALD_LEGGINGS = new Cosmetic(
            "iron/emerald_leggings", "Iron Emerald Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/emerald");
    public static final Cosmetic IRON_EMERALD_BOOTS = new Cosmetic(
            "iron/emerald_boots", "Iron Emerald Boots", CosmeticSlot.BOOTS, "minecraft:iron/emerald");

    public static final Cosmetic IRON_ENDER_HELMET = new Cosmetic(
            "iron/ender_helmet", "Iron Ender Helmet", CosmeticSlot.HELMET, "minecraft:iron/ender");
    public static final Cosmetic IRON_ENDER_CHESTPLATE = new Cosmetic(
            "iron/ender_chestplate", "Iron Ender Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/ender");
    public static final Cosmetic IRON_ENDER_LEGGINGS = new Cosmetic(
            "iron/ender_leggings", "Iron Ender Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/ender");
    public static final Cosmetic IRON_ENDER_BOOTS = new Cosmetic(
            "iron/ender_boots", "Iron Ender Boots", CosmeticSlot.BOOTS, "minecraft:iron/ender");

    public static final Cosmetic IRON_FOX_HELMET = new Cosmetic(
            "iron/fox_helmet", "Iron Fox Helmet", CosmeticSlot.HELMET, "minecraft:iron/fox");
    public static final Cosmetic IRON_FOX_CHESTPLATE = new Cosmetic(
            "iron/fox_chestplate", "Iron Fox Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/fox");
    public static final Cosmetic IRON_FOX_LEGGINGS = new Cosmetic(
            "iron/fox_leggings", "Iron Fox Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/fox");
    public static final Cosmetic IRON_FOX_BOOTS = new Cosmetic(
            "iron/fox_boots", "Iron Fox Boots", CosmeticSlot.BOOTS, "minecraft:iron/fox");

    public static final Cosmetic IRON_FULL_METAL_HELMET = new Cosmetic(
            "iron/full_metal_helmet", "Iron Full Metal Helmet", CosmeticSlot.HELMET, "minecraft:iron/full_metal");
    public static final Cosmetic IRON_FULL_METAL_CHESTPLATE = new Cosmetic(
            "iron/full_metal_chestplate", "Iron Full Metal Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/full_metal");
    public static final Cosmetic IRON_FULL_METAL_LEGGINGS = new Cosmetic(
            "iron/full_metal_leggings", "Iron Full Metal Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/full_metal");
    public static final Cosmetic IRON_FULL_METAL_BOOTS = new Cosmetic(
            "iron/full_metal_boots", "Iron Full Metal Boots", CosmeticSlot.BOOTS, "minecraft:iron/full_metal");

    public static final Cosmetic IRON_GHOSTLY_HELMET = new Cosmetic(
            "iron/ghostly_helmet", "Iron Ghostly Helmet", CosmeticSlot.HELMET, "minecraft:iron/ghostly");
    public static final Cosmetic IRON_GHOSTLY_CHESTPLATE = new Cosmetic(
            "iron/ghostly_chestplate", "Iron Ghostly Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/ghostly");
    public static final Cosmetic IRON_GHOSTLY_LEGGINGS = new Cosmetic(
            "iron/ghostly_leggings", "Iron Ghostly Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/ghostly");
    public static final Cosmetic IRON_GHOSTLY_BOOTS = new Cosmetic(
            "iron/ghostly_boots", "Iron Ghostly Boots", CosmeticSlot.BOOTS, "minecraft:iron/ghostly");

    public static final Cosmetic IRON_GRIM_HELMET = new Cosmetic(
            "iron/grim_helmet", "Iron Grim Helmet", CosmeticSlot.HELMET, "minecraft:iron/grim");
    public static final Cosmetic IRON_GRIM_CHESTPLATE = new Cosmetic(
            "iron/grim_chestplate", "Iron Grim Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/grim");
    public static final Cosmetic IRON_GRIM_LEGGINGS = new Cosmetic(
            "iron/grim_leggings", "Iron Grim Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/grim");
    public static final Cosmetic IRON_GRIM_BOOTS = new Cosmetic(
            "iron/grim_boots", "Iron Grim Boots", CosmeticSlot.BOOTS, "minecraft:iron/grim");

    public static final Cosmetic IRON_GUARD_HELMET = new Cosmetic(
            "iron/guard_helmet", "Iron Guard Helmet", CosmeticSlot.HELMET, "minecraft:iron/guard");
    public static final Cosmetic IRON_GUARD_CHESTPLATE = new Cosmetic(
            "iron/guard_chestplate", "Iron Guard Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/guard");
    public static final Cosmetic IRON_GUARD_LEGGINGS = new Cosmetic(
            "iron/guard_leggings", "Iron Guard Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/guard");
    public static final Cosmetic IRON_GUARD_BOOTS = new Cosmetic(
            "iron/guard_boots", "Iron Guard Boots", CosmeticSlot.BOOTS, "minecraft:iron/guard");

    public static final Cosmetic IRON_HIGHLAND_HELMET = new Cosmetic(
            "iron/highland_helmet", "Iron Highland Helmet", CosmeticSlot.HELMET, "minecraft:iron/highland");
    public static final Cosmetic IRON_HIGHLAND_CHESTPLATE = new Cosmetic(
            "iron/highland_chestplate", "Iron Highland Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/highland");
    public static final Cosmetic IRON_HIGHLAND_LEGGINGS = new Cosmetic(
            "iron/highland_leggings", "Iron Highland Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/highland");
    public static final Cosmetic IRON_HIGHLAND_BOOTS = new Cosmetic(
            "iron/highland_boots", "Iron Highland Boots", CosmeticSlot.BOOTS, "minecraft:iron/highland");

    public static final Cosmetic IRON_LAVA_INFUSED_HELMET = new Cosmetic(
            "iron/lava_infused_helmet", "Iron Lava Infused Helmet", CosmeticSlot.HELMET, "minecraft:iron/lava_infused");
    public static final Cosmetic IRON_LAVA_INFUSED_CHESTPLATE = new Cosmetic(
            "iron/lava_infused_chestplate", "Iron Lava Infused Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/lava_infused");
    public static final Cosmetic IRON_LAVA_INFUSED_LEGGINGS = new Cosmetic(
            "iron/lava_infused_leggings", "Iron Lava Infused Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/lava_infused");
    public static final Cosmetic IRON_LAVA_INFUSED_BOOTS = new Cosmetic(
            "iron/lava_infused_boots", "Iron Lava Infused Boots", CosmeticSlot.BOOTS, "minecraft:iron/lava_infused");

    public static final Cosmetic IRON_MYSTERY_HELMET = new Cosmetic(
            "iron/mystery_helmet", "Iron Mystery Helmet", CosmeticSlot.HELMET, "minecraft:iron/mystery");
    public static final Cosmetic IRON_MYSTERY_CHESTPLATE = new Cosmetic(
            "iron/mystery_chestplate", "Iron Mystery Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/mystery");
    public static final Cosmetic IRON_MYSTERY_LEGGINGS = new Cosmetic(
            "iron/mystery_leggings", "Iron Mystery Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/mystery");
    public static final Cosmetic IRON_MYSTERY_BOOTS = new Cosmetic(
            "iron/mystery_boots", "Iron Mystery Boots", CosmeticSlot.BOOTS, "minecraft:iron/mystery");

    public static final Cosmetic IRON_NETHERWALKER_HELMET = new Cosmetic(
            "iron/netherwalker_helmet", "Iron Netherwalker Helmet", CosmeticSlot.HELMET, "minecraft:iron/netherwalker");
    public static final Cosmetic IRON_NETHERWALKER_CHESTPLATE = new Cosmetic(
            "iron/netherwalker_chestplate", "Iron Netherwalker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/netherwalker");
    public static final Cosmetic IRON_NETHERWALKER_LEGGINGS = new Cosmetic(
            "iron/netherwalker_leggings", "Iron Netherwalker Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/netherwalker");
    public static final Cosmetic IRON_NETHERWALKER_BOOTS = new Cosmetic(
            "iron/netherwalker_boots", "Iron Netherwalker Boots", CosmeticSlot.BOOTS, "minecraft:iron/netherwalker");

    public static final Cosmetic IRON_OCELOT_HELMET = new Cosmetic(
            "iron/ocelot_helmet", "Iron Ocelot Helmet", CosmeticSlot.HELMET, "minecraft:iron/ocelot");
    public static final Cosmetic IRON_OCELOT_CHESTPLATE = new Cosmetic(
            "iron/ocelot_chestplate", "Iron Ocelot Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/ocelot");
    public static final Cosmetic IRON_OCELOT_LEGGINGS = new Cosmetic(
            "iron/ocelot_leggings", "Iron Ocelot Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/ocelot");
    public static final Cosmetic IRON_OCELOT_BOOTS = new Cosmetic(
            "iron/ocelot_boots", "Iron Ocelot Boots", CosmeticSlot.BOOTS, "minecraft:iron/ocelot");

    public static final Cosmetic IRON_OPULENT_HELMET = new Cosmetic(
            "iron/opulent_helmet", "Iron Opulent Helmet", CosmeticSlot.HELMET, "minecraft:iron/opulent");
    public static final Cosmetic IRON_OPULENT_CHESTPLATE = new Cosmetic(
            "iron/opulent_chestplate", "Iron Opulent Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/opulent");
    public static final Cosmetic IRON_OPULENT_LEGGINGS = new Cosmetic(
            "iron/opulent_leggings", "Iron Opulent Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/opulent");
    public static final Cosmetic IRON_OPULENT_BOOTS = new Cosmetic(
            "iron/opulent_boots", "Iron Opulent Boots", CosmeticSlot.BOOTS, "minecraft:iron/opulent");

    public static final Cosmetic IRON_ORE_HELMET = new Cosmetic(
            "iron/ore_helmet", "Iron Ore Helmet", CosmeticSlot.HELMET, "minecraft:iron/ore");
    public static final Cosmetic IRON_ORE_CHESTPLATE = new Cosmetic(
            "iron/ore_chestplate", "Iron Ore Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/ore");
    public static final Cosmetic IRON_ORE_LEGGINGS = new Cosmetic(
            "iron/ore_leggings", "Iron Ore Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/ore");
    public static final Cosmetic IRON_ORE_BOOTS = new Cosmetic(
            "iron/ore_boots", "Iron Ore Boots", CosmeticSlot.BOOTS, "minecraft:iron/ore");

    public static final Cosmetic IRON_PAMA_HELMET = new Cosmetic(
            "iron/pama_helmet", "Iron Pama Helmet", CosmeticSlot.HELMET, "minecraft:iron/pama");
    public static final Cosmetic IRON_PAMA_CHESTPLATE = new Cosmetic(
            "iron/pama_chestplate", "Iron Pama Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/pama");
    public static final Cosmetic IRON_PAMA_LEGGINGS = new Cosmetic(
            "iron/pama_leggings", "Iron Pama Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/pama");
    public static final Cosmetic IRON_PAMA_BOOTS = new Cosmetic(
            "iron/pama_boots", "Iron Pama Boots", CosmeticSlot.BOOTS, "minecraft:iron/pama");

    public static final Cosmetic IRON_PHANTOM_HELMET = new Cosmetic(
            "iron/phantom_helmet", "Iron Phantom Helmet", CosmeticSlot.HELMET, "minecraft:iron/phantom");
    public static final Cosmetic IRON_PHANTOM_CHESTPLATE = new Cosmetic(
            "iron/phantom_chestplate", "Iron Phantom Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/phantom");
    public static final Cosmetic IRON_PHANTOM_LEGGINGS = new Cosmetic(
            "iron/phantom_leggings", "Iron Phantom Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/phantom");
    public static final Cosmetic IRON_PHANTOM_BOOTS = new Cosmetic(
            "iron/phantom_boots", "Iron Phantom Boots", CosmeticSlot.BOOTS, "minecraft:iron/phantom");

    public static final Cosmetic IRON_PIGLIN_HELMET = new Cosmetic(
            "iron/piglin_helmet", "Iron Piglin Helmet", CosmeticSlot.HELMET, "minecraft:iron/piglin");
    public static final Cosmetic IRON_PIGLIN_CHESTPLATE = new Cosmetic(
            "iron/piglin_chestplate", "Iron Piglin Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/piglin");
    public static final Cosmetic IRON_PIGLIN_LEGGINGS = new Cosmetic(
            "iron/piglin_leggings", "Iron Piglin Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/piglin");
    public static final Cosmetic IRON_PIGLIN_BOOTS = new Cosmetic(
            "iron/piglin_boots", "Iron Piglin Boots", CosmeticSlot.BOOTS, "minecraft:iron/piglin");

    public static final Cosmetic IRON_PLATE_HELMET = new Cosmetic(
            "iron/plate_helmet", "Iron Plate Helmet", CosmeticSlot.HELMET, "minecraft:iron/plate");
    public static final Cosmetic IRON_PLATE_CHESTPLATE = new Cosmetic(
            "iron/plate_chestplate", "Iron Plate Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/plate");
    public static final Cosmetic IRON_PLATE_LEGGINGS = new Cosmetic(
            "iron/plate_leggings", "Iron Plate Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/plate");
    public static final Cosmetic IRON_PLATE_BOOTS = new Cosmetic(
            "iron/plate_boots", "Iron Plate Boots", CosmeticSlot.BOOTS, "minecraft:iron/plate");

    public static final Cosmetic IRON_PROUD_HELMET = new Cosmetic(
            "iron/proud_helmet", "Iron Proud Helmet", CosmeticSlot.HELMET, "minecraft:iron/proud");
    public static final Cosmetic IRON_PROUD_CHESTPLATE = new Cosmetic(
            "iron/proud_chestplate", "Iron Proud Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/proud");
    public static final Cosmetic IRON_PROUD_LEGGINGS = new Cosmetic(
            "iron/proud_leggings", "Iron Proud Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/proud");
    public static final Cosmetic IRON_PROUD_BOOTS = new Cosmetic(
            "iron/proud_boots", "Iron Proud Boots", CosmeticSlot.BOOTS, "minecraft:iron/proud");

    public static final Cosmetic IRON_RENEGADE_HELMET = new Cosmetic(
            "iron/renegade_helmet", "Iron Renegade Helmet", CosmeticSlot.HELMET, "minecraft:iron/renegade");
    public static final Cosmetic IRON_RENEGADE_CHESTPLATE = new Cosmetic(
            "iron/renegade_chestplate", "Iron Renegade Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/renegade");
    public static final Cosmetic IRON_RENEGADE_LEGGINGS = new Cosmetic(
            "iron/renegade_leggings", "Iron Renegade Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/renegade");
    public static final Cosmetic IRON_RENEGADE_BOOTS = new Cosmetic(
            "iron/renegade_boots", "Iron Renegade Boots", CosmeticSlot.BOOTS, "minecraft:iron/renegade");

    public static final Cosmetic IRON_ROYAL_HELMET = new Cosmetic(
            "iron/royal_helmet", "Iron Royal Helmet", CosmeticSlot.HELMET, "minecraft:iron/royal");
    public static final Cosmetic IRON_ROYAL_CHESTPLATE = new Cosmetic(
            "iron/royal_chestplate", "Iron Royal Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/royal");
    public static final Cosmetic IRON_ROYAL_LEGGINGS = new Cosmetic(
            "iron/royal_leggings", "Iron Royal Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/royal");
    public static final Cosmetic IRON_ROYAL_BOOTS = new Cosmetic(
            "iron/royal_boots", "Iron Royal Boots", CosmeticSlot.BOOTS, "minecraft:iron/royal");

    public static final Cosmetic IRON_RUBY_HELMET = new Cosmetic(
            "iron/ruby_helmet", "Iron Ruby Helmet", CosmeticSlot.HELMET, "minecraft:iron/ruby");
    public static final Cosmetic IRON_RUBY_CHESTPLATE = new Cosmetic(
            "iron/ruby_chestplate", "Iron Ruby Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/ruby");
    public static final Cosmetic IRON_RUBY_LEGGINGS = new Cosmetic(
            "iron/ruby_leggings", "Iron Ruby Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/ruby");
    public static final Cosmetic IRON_RUBY_BOOTS = new Cosmetic(
            "iron/ruby_boots", "Iron Ruby Boots", CosmeticSlot.BOOTS, "minecraft:iron/ruby");

    public static final Cosmetic IRON_SHOGUN_HELMET = new Cosmetic(
            "iron/shogun_helmet", "Iron Shogun Helmet", CosmeticSlot.HELMET, "minecraft:iron/shogun");
    public static final Cosmetic IRON_SHOGUN_CHESTPLATE = new Cosmetic(
            "iron/shogun_chestplate", "Iron Shogun Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/shogun");
    public static final Cosmetic IRON_SHOGUN_LEGGINGS = new Cosmetic(
            "iron/shogun_leggings", "Iron Shogun Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/shogun");
    public static final Cosmetic IRON_SHOGUN_BOOTS = new Cosmetic(
            "iron/shogun_boots", "Iron Shogun Boots", CosmeticSlot.BOOTS, "minecraft:iron/shogun");

    public static final Cosmetic IRON_SPELUNKER_HELMET = new Cosmetic(
            "iron/spelunker_helmet", "Iron Spelunker Helmet", CosmeticSlot.HELMET, "minecraft:iron/spelunker");
    public static final Cosmetic IRON_SPELUNKER_CHESTPLATE = new Cosmetic(
            "iron/spelunker_chestplate", "Iron Spelunker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/spelunker");
    public static final Cosmetic IRON_SPELUNKER_LEGGINGS = new Cosmetic(
            "iron/spelunker_leggings", "Iron Spelunker Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/spelunker");
    public static final Cosmetic IRON_SPELUNKER_BOOTS = new Cosmetic(
            "iron/spelunker_boots", "Iron Spelunker Boots", CosmeticSlot.BOOTS, "minecraft:iron/spelunker");

    public static final Cosmetic IRON_SPLEEF_HELMET = new Cosmetic(
            "iron/spleef_helmet", "Iron Spleef Helmet", CosmeticSlot.HELMET, "minecraft:iron/spleef");
    public static final Cosmetic IRON_SPLEEF_CHESTPLATE = new Cosmetic(
            "iron/spleef_chestplate", "Iron Spleef Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/spleef");
    public static final Cosmetic IRON_SPLEEF_LEGGINGS = new Cosmetic(
            "iron/spleef_leggings", "Iron Spleef Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/spleef");
    public static final Cosmetic IRON_SPLEEF_BOOTS = new Cosmetic(
            "iron/spleef_boots", "Iron Spleef Boots", CosmeticSlot.BOOTS, "minecraft:iron/spleef");

    public static final Cosmetic IRON_STALWART_HELMET = new Cosmetic(
            "iron/stalwart_helmet", "Iron Stalwart Helmet", CosmeticSlot.HELMET, "minecraft:iron/stalwart");
    public static final Cosmetic IRON_STALWART_CHESTPLATE = new Cosmetic(
            "iron/stalwart_chestplate", "Iron Stalwart Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/stalwart");
    public static final Cosmetic IRON_STALWART_LEGGINGS = new Cosmetic(
            "iron/stalwart_leggings", "Iron Stalwart Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/stalwart");
    public static final Cosmetic IRON_STALWART_BOOTS = new Cosmetic(
            "iron/stalwart_boots", "Iron Stalwart Boots", CosmeticSlot.BOOTS, "minecraft:iron/stalwart");

    public static final Cosmetic IRON_THIEF_HELMET = new Cosmetic(
            "iron/thief_helmet", "Iron Thief Helmet", CosmeticSlot.HELMET, "minecraft:iron/thief");
    public static final Cosmetic IRON_THIEF_CHESTPLATE = new Cosmetic(
            "iron/thief_chestplate", "Iron Thief Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/thief");
    public static final Cosmetic IRON_THIEF_LEGGINGS = new Cosmetic(
            "iron/thief_leggings", "Iron Thief Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/thief");
    public static final Cosmetic IRON_THIEF_BOOTS = new Cosmetic(
            "iron/thief_boots", "Iron Thief Boots", CosmeticSlot.BOOTS, "minecraft:iron/thief");

    public static final Cosmetic IRON_TRIAL_HELMET = new Cosmetic(
            "iron/trial_helmet", "Iron Trial Helmet", CosmeticSlot.HELMET, "minecraft:iron/trial");
    public static final Cosmetic IRON_TRIAL_CHESTPLATE = new Cosmetic(
            "iron/trial_chestplate", "Iron Trial Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/trial");
    public static final Cosmetic IRON_TRIAL_LEGGINGS = new Cosmetic(
            "iron/trial_leggings", "Iron Trial Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/trial");
    public static final Cosmetic IRON_TRIAL_BOOTS = new Cosmetic(
            "iron/trial_boots", "Iron Trial Boots", CosmeticSlot.BOOTS, "minecraft:iron/trial");

    public static final Cosmetic IRON_VEMI_HELMET = new Cosmetic(
            "iron/vemi_helmet", "Iron Vemi Helmet", CosmeticSlot.HELMET, "minecraft:iron/vemi");
    public static final Cosmetic IRON_VEMI_CHESTPLATE = new Cosmetic(
            "iron/vemi_chestplate", "Iron Vemi Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/vemi");
    public static final Cosmetic IRON_VEMI_LEGGINGS = new Cosmetic(
            "iron/vemi_leggings", "Iron Vemi Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/vemi");
    public static final Cosmetic IRON_VEMI_BOOTS = new Cosmetic(
            "iron/vemi_boots", "Iron Vemi Boots", CosmeticSlot.BOOTS, "minecraft:iron/vemi");

    public static final Cosmetic IRON_WOLF_HELMET = new Cosmetic(
            "iron/wolf_helmet", "Iron Wolf Helmet", CosmeticSlot.HELMET, "minecraft:iron/wolf");
    public static final Cosmetic IRON_WOLF_CHESTPLATE = new Cosmetic(
            "iron/wolf_chestplate", "Iron Wolf Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:iron/wolf");
    public static final Cosmetic IRON_WOLF_LEGGINGS = new Cosmetic(
            "iron/wolf_leggings", "Iron Wolf Leggings", CosmeticSlot.LEGGINGS, "minecraft:iron/wolf");
    public static final Cosmetic IRON_WOLF_BOOTS = new Cosmetic(
            "iron/wolf_boots", "Iron Wolf Boots", CosmeticSlot.BOOTS, "minecraft:iron/wolf");

    public static final Cosmetic GOLD_AR_USEFUL_HELMET = new Cosmetic(
            "gold/ar_useful_helmet", "Gold Ar Useful Helmet", CosmeticSlot.HELMET, "minecraft:gold/ar_useful");
    public static final Cosmetic GOLD_AR_USEFUL_CHESTPLATE = new Cosmetic(
            "gold/ar_useful_chestplate", "Gold Ar Useful Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/ar_useful");
    public static final Cosmetic GOLD_AR_USEFUL_LEGGINGS = new Cosmetic(
            "gold/ar_useful_leggings", "Gold Ar Useful Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/ar_useful");
    public static final Cosmetic GOLD_AR_USEFUL_BOOTS = new Cosmetic(
            "gold/ar_useful_boots", "Gold Ar Useful Boots", CosmeticSlot.BOOTS, "minecraft:gold/ar_useful");

    public static final Cosmetic GOLD_AR_HELMET = new Cosmetic(
            "gold/ar_helmet", "Gold Ar Helmet", CosmeticSlot.HELMET, "minecraft:gold/ar");
    public static final Cosmetic GOLD_AR_CHESTPLATE = new Cosmetic(
            "gold/ar_chestplate", "Gold Ar Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/ar");
    public static final Cosmetic GOLD_AR_LEGGINGS = new Cosmetic(
            "gold/ar_leggings", "Gold Ar Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/ar");
    public static final Cosmetic GOLD_AR_BOOTS = new Cosmetic(
            "gold/ar_boots", "Gold Ar Boots", CosmeticSlot.BOOTS, "minecraft:gold/ar");

    public static final Cosmetic GOLD_CHAMPION_HELMET = new Cosmetic(
            "gold/champion_helmet", "Gold Champion Helmet", CosmeticSlot.HELMET, "minecraft:gold/champion");
    public static final Cosmetic GOLD_CHAMPION_CHESTPLATE = new Cosmetic(
            "gold/champion_chestplate", "Gold Champion Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/champion");
    public static final Cosmetic GOLD_CHAMPION_LEGGINGS = new Cosmetic(
            "gold/champion_leggings", "Gold Champion Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/champion");
    public static final Cosmetic GOLD_CHAMPION_BOOTS = new Cosmetic(
            "gold/champion_boots", "Gold Champion Boots", CosmeticSlot.BOOTS, "minecraft:gold/champion");

    public static final Cosmetic GOLD_COMBAT_HELMET = new Cosmetic(
            "gold/combat_helmet", "Gold Combat Helmet", CosmeticSlot.HELMET, "minecraft:gold/combat");
    public static final Cosmetic GOLD_COMBAT_CHESTPLATE = new Cosmetic(
            "gold/combat_chestplate", "Gold Combat Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/combat");
    public static final Cosmetic GOLD_COMBAT_LEGGINGS = new Cosmetic(
            "gold/combat_leggings", "Gold Combat Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/combat");
    public static final Cosmetic GOLD_COMBAT_BOOTS = new Cosmetic(
            "gold/combat_boots", "Gold Combat Boots", CosmeticSlot.BOOTS, "minecraft:gold/combat");

    public static final Cosmetic GOLD_COMMAND_BLOCK_HELMET = new Cosmetic(
            "gold/command_block_helmet", "Gold Command Block Helmet", CosmeticSlot.HELMET, "minecraft:gold/command_block");
    public static final Cosmetic GOLD_COMMAND_BLOCK_CHESTPLATE = new Cosmetic(
            "gold/command_block_chestplate", "Gold Command Block Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/command_block");
    public static final Cosmetic GOLD_COMMAND_BLOCK_LEGGINGS = new Cosmetic(
            "gold/command_block_leggings", "Gold Command Block Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/command_block");
    public static final Cosmetic GOLD_COMMAND_BLOCK_BOOTS = new Cosmetic(
            "gold/command_block_boots", "Gold Command Block Boots", CosmeticSlot.BOOTS, "minecraft:gold/command_block");

    public static final Cosmetic GOLD_CREED_HELMET = new Cosmetic(
            "gold/creed_helmet", "Gold Creed Helmet", CosmeticSlot.HELMET, "minecraft:gold/creed");
    public static final Cosmetic GOLD_CREED_CHESTPLATE = new Cosmetic(
            "gold/creed_chestplate", "Gold Creed Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/creed");
    public static final Cosmetic GOLD_CREED_LEGGINGS = new Cosmetic(
            "gold/creed_leggings", "Gold Creed Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/creed");
    public static final Cosmetic GOLD_CREED_BOOTS = new Cosmetic(
            "gold/creed_boots", "Gold Creed Boots", CosmeticSlot.BOOTS, "minecraft:gold/creed");

    public static final Cosmetic GOLD_DARK_HELMET = new Cosmetic(
            "gold/dark_helmet", "Gold Dark Helmet", CosmeticSlot.HELMET, "minecraft:gold/dark");
    public static final Cosmetic GOLD_DARK_CHESTPLATE = new Cosmetic(
            "gold/dark_chestplate", "Gold Dark Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/dark");
    public static final Cosmetic GOLD_DARK_LEGGINGS = new Cosmetic(
            "gold/dark_leggings", "Gold Dark Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/dark");
    public static final Cosmetic GOLD_DARK_BOOTS = new Cosmetic(
            "gold/dark_boots", "Gold Dark Boots", CosmeticSlot.BOOTS, "minecraft:gold/dark");

    public static final Cosmetic GOLD_DRAGON_HELMET = new Cosmetic(
            "gold/dragon_helmet", "Gold Dragon Helmet", CosmeticSlot.HELMET, "minecraft:gold/dragon");
    public static final Cosmetic GOLD_DRAGON_CHESTPLATE = new Cosmetic(
            "gold/dragon_chestplate", "Gold Dragon Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/dragon");
    public static final Cosmetic GOLD_DRAGON_LEGGINGS = new Cosmetic(
            "gold/dragon_leggings", "Gold Dragon Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/dragon");
    public static final Cosmetic GOLD_DRAGON_BOOTS = new Cosmetic(
            "gold/dragon_boots", "Gold Dragon Boots", CosmeticSlot.BOOTS, "minecraft:gold/dragon");

    public static final Cosmetic GOLD_EMERALD_HELMET = new Cosmetic(
            "gold/emerald_helmet", "Gold Emerald Helmet", CosmeticSlot.HELMET, "minecraft:gold/emerald");
    public static final Cosmetic GOLD_EMERALD_CHESTPLATE = new Cosmetic(
            "gold/emerald_chestplate", "Gold Emerald Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/emerald");
    public static final Cosmetic GOLD_EMERALD_LEGGINGS = new Cosmetic(
            "gold/emerald_leggings", "Gold Emerald Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/emerald");
    public static final Cosmetic GOLD_EMERALD_BOOTS = new Cosmetic(
            "gold/emerald_boots", "Gold Emerald Boots", CosmeticSlot.BOOTS, "minecraft:gold/emerald");

    public static final Cosmetic GOLD_ENDER_HELMET = new Cosmetic(
            "gold/ender_helmet", "Gold Ender Helmet", CosmeticSlot.HELMET, "minecraft:gold/ender");
    public static final Cosmetic GOLD_ENDER_CHESTPLATE = new Cosmetic(
            "gold/ender_chestplate", "Gold Ender Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/ender");
    public static final Cosmetic GOLD_ENDER_LEGGINGS = new Cosmetic(
            "gold/ender_leggings", "Gold Ender Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/ender");
    public static final Cosmetic GOLD_ENDER_BOOTS = new Cosmetic(
            "gold/ender_boots", "Gold Ender Boots", CosmeticSlot.BOOTS, "minecraft:gold/ender");

    public static final Cosmetic GOLD_FOX_HELMET = new Cosmetic(
            "gold/fox_helmet", "Gold Fox Helmet", CosmeticSlot.HELMET, "minecraft:gold/fox");
    public static final Cosmetic GOLD_FOX_CHESTPLATE = new Cosmetic(
            "gold/fox_chestplate", "Gold Fox Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/fox");
    public static final Cosmetic GOLD_FOX_LEGGINGS = new Cosmetic(
            "gold/fox_leggings", "Gold Fox Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/fox");
    public static final Cosmetic GOLD_FOX_BOOTS = new Cosmetic(
            "gold/fox_boots", "Gold Fox Boots", CosmeticSlot.BOOTS, "minecraft:gold/fox");

    public static final Cosmetic GOLD_FULL_METAL_HELMET = new Cosmetic(
            "gold/full_metal_helmet", "Gold Full Metal Helmet", CosmeticSlot.HELMET, "minecraft:gold/full_metal");
    public static final Cosmetic GOLD_FULL_METAL_CHESTPLATE = new Cosmetic(
            "gold/full_metal_chestplate", "Gold Full Metal Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/full_metal");
    public static final Cosmetic GOLD_FULL_METAL_LEGGINGS = new Cosmetic(
            "gold/full_metal_leggings", "Gold Full Metal Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/full_metal");
    public static final Cosmetic GOLD_FULL_METAL_BOOTS = new Cosmetic(
            "gold/full_metal_boots", "Gold Full Metal Boots", CosmeticSlot.BOOTS, "minecraft:gold/full_metal");

    public static final Cosmetic GOLD_GHOSTLY_HELMET = new Cosmetic(
            "gold/ghostly_helmet", "Gold Ghostly Helmet", CosmeticSlot.HELMET, "minecraft:gold/ghostly");
    public static final Cosmetic GOLD_GHOSTLY_CHESTPLATE = new Cosmetic(
            "gold/ghostly_chestplate", "Gold Ghostly Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/ghostly");
    public static final Cosmetic GOLD_GHOSTLY_LEGGINGS = new Cosmetic(
            "gold/ghostly_leggings", "Gold Ghostly Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/ghostly");
    public static final Cosmetic GOLD_GHOSTLY_BOOTS = new Cosmetic(
            "gold/ghostly_boots", "Gold Ghostly Boots", CosmeticSlot.BOOTS, "minecraft:gold/ghostly");

    public static final Cosmetic GOLD_GRIM_HELMET = new Cosmetic(
            "gold/grim_helmet", "Gold Grim Helmet", CosmeticSlot.HELMET, "minecraft:gold/grim");
    public static final Cosmetic GOLD_GRIM_CHESTPLATE = new Cosmetic(
            "gold/grim_chestplate", "Gold Grim Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/grim");
    public static final Cosmetic GOLD_GRIM_LEGGINGS = new Cosmetic(
            "gold/grim_leggings", "Gold Grim Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/grim");
    public static final Cosmetic GOLD_GRIM_BOOTS = new Cosmetic(
            "gold/grim_boots", "Gold Grim Boots", CosmeticSlot.BOOTS, "minecraft:gold/grim");

    public static final Cosmetic GOLD_GUARD_HELMET = new Cosmetic(
            "gold/guard_helmet", "Gold Guard Helmet", CosmeticSlot.HELMET, "minecraft:gold/guard");
    public static final Cosmetic GOLD_GUARD_CHESTPLATE = new Cosmetic(
            "gold/guard_chestplate", "Gold Guard Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/guard");
    public static final Cosmetic GOLD_GUARD_LEGGINGS = new Cosmetic(
            "gold/guard_leggings", "Gold Guard Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/guard");
    public static final Cosmetic GOLD_GUARD_BOOTS = new Cosmetic(
            "gold/guard_boots", "Gold Guard Boots", CosmeticSlot.BOOTS, "minecraft:gold/guard");

    public static final Cosmetic GOLD_HIGHLAND_HELMET = new Cosmetic(
            "gold/highland_helmet", "Gold Highland Helmet", CosmeticSlot.HELMET, "minecraft:gold/highland");
    public static final Cosmetic GOLD_HIGHLAND_CHESTPLATE = new Cosmetic(
            "gold/highland_chestplate", "Gold Highland Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/highland");
    public static final Cosmetic GOLD_HIGHLAND_LEGGINGS = new Cosmetic(
            "gold/highland_leggings", "Gold Highland Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/highland");
    public static final Cosmetic GOLD_HIGHLAND_BOOTS = new Cosmetic(
            "gold/highland_boots", "Gold Highland Boots", CosmeticSlot.BOOTS, "minecraft:gold/highland");

    public static final Cosmetic GOLD_LAVA_INFUSED_HELMET = new Cosmetic(
            "gold/lava_infused_helmet", "Gold Lava Infused Helmet", CosmeticSlot.HELMET, "minecraft:gold/lava_infused");
    public static final Cosmetic GOLD_LAVA_INFUSED_CHESTPLATE = new Cosmetic(
            "gold/lava_infused_chestplate", "Gold Lava Infused Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/lava_infused");
    public static final Cosmetic GOLD_LAVA_INFUSED_LEGGINGS = new Cosmetic(
            "gold/lava_infused_leggings", "Gold Lava Infused Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/lava_infused");
    public static final Cosmetic GOLD_LAVA_INFUSED_BOOTS = new Cosmetic(
            "gold/lava_infused_boots", "Gold Lava Infused Boots", CosmeticSlot.BOOTS, "minecraft:gold/lava_infused");

    public static final Cosmetic GOLD_MYSTERY_HELMET = new Cosmetic(
            "gold/mystery_helmet", "Gold Mystery Helmet", CosmeticSlot.HELMET, "minecraft:gold/mystery");
    public static final Cosmetic GOLD_MYSTERY_CHESTPLATE = new Cosmetic(
            "gold/mystery_chestplate", "Gold Mystery Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/mystery");
    public static final Cosmetic GOLD_MYSTERY_LEGGINGS = new Cosmetic(
            "gold/mystery_leggings", "Gold Mystery Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/mystery");
    public static final Cosmetic GOLD_MYSTERY_BOOTS = new Cosmetic(
            "gold/mystery_boots", "Gold Mystery Boots", CosmeticSlot.BOOTS, "minecraft:gold/mystery");

    public static final Cosmetic GOLD_NETHERWALKER_HELMET = new Cosmetic(
            "gold/netherwalker_helmet", "Gold Netherwalker Helmet", CosmeticSlot.HELMET, "minecraft:gold/netherwalker");
    public static final Cosmetic GOLD_NETHERWALKER_CHESTPLATE = new Cosmetic(
            "gold/netherwalker_chestplate", "Gold Netherwalker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/netherwalker");
    public static final Cosmetic GOLD_NETHERWALKER_LEGGINGS = new Cosmetic(
            "gold/netherwalker_leggings", "Gold Netherwalker Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/netherwalker");
    public static final Cosmetic GOLD_NETHERWALKER_BOOTS = new Cosmetic(
            "gold/netherwalker_boots", "Gold Netherwalker Boots", CosmeticSlot.BOOTS, "minecraft:gold/netherwalker");

    public static final Cosmetic GOLD_OCELOT_HELMET = new Cosmetic(
            "gold/ocelot_helmet", "Gold Ocelot Helmet", CosmeticSlot.HELMET, "minecraft:gold/ocelot");
    public static final Cosmetic GOLD_OCELOT_CHESTPLATE = new Cosmetic(
            "gold/ocelot_chestplate", "Gold Ocelot Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/ocelot");
    public static final Cosmetic GOLD_OCELOT_LEGGINGS = new Cosmetic(
            "gold/ocelot_leggings", "Gold Ocelot Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/ocelot");
    public static final Cosmetic GOLD_OCELOT_BOOTS = new Cosmetic(
            "gold/ocelot_boots", "Gold Ocelot Boots", CosmeticSlot.BOOTS, "minecraft:gold/ocelot");

    public static final Cosmetic GOLD_OPULENT_HELMET = new Cosmetic(
            "gold/opulent_helmet", "Gold Opulent Helmet", CosmeticSlot.HELMET, "minecraft:gold/opulent");
    public static final Cosmetic GOLD_OPULENT_CHESTPLATE = new Cosmetic(
            "gold/opulent_chestplate", "Gold Opulent Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/opulent");
    public static final Cosmetic GOLD_OPULENT_LEGGINGS = new Cosmetic(
            "gold/opulent_leggings", "Gold Opulent Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/opulent");
    public static final Cosmetic GOLD_OPULENT_BOOTS = new Cosmetic(
            "gold/opulent_boots", "Gold Opulent Boots", CosmeticSlot.BOOTS, "minecraft:gold/opulent");

    public static final Cosmetic GOLD_ORE_HELMET = new Cosmetic(
            "gold/ore_helmet", "Gold Ore Helmet", CosmeticSlot.HELMET, "minecraft:gold/ore");
    public static final Cosmetic GOLD_ORE_CHESTPLATE = new Cosmetic(
            "gold/ore_chestplate", "Gold Ore Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/ore");
    public static final Cosmetic GOLD_ORE_LEGGINGS = new Cosmetic(
            "gold/ore_leggings", "Gold Ore Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/ore");
    public static final Cosmetic GOLD_ORE_BOOTS = new Cosmetic(
            "gold/ore_boots", "Gold Ore Boots", CosmeticSlot.BOOTS, "minecraft:gold/ore");

    public static final Cosmetic GOLD_PAMA_HELMET = new Cosmetic(
            "gold/pama_helmet", "Gold Pama Helmet", CosmeticSlot.HELMET, "minecraft:gold/pama");
    public static final Cosmetic GOLD_PAMA_CHESTPLATE = new Cosmetic(
            "gold/pama_chestplate", "Gold Pama Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/pama");
    public static final Cosmetic GOLD_PAMA_LEGGINGS = new Cosmetic(
            "gold/pama_leggings", "Gold Pama Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/pama");
    public static final Cosmetic GOLD_PAMA_BOOTS = new Cosmetic(
            "gold/pama_boots", "Gold Pama Boots", CosmeticSlot.BOOTS, "minecraft:gold/pama");

    public static final Cosmetic GOLD_PHANTOM_HELMET = new Cosmetic(
            "gold/phantom_helmet", "Gold Phantom Helmet", CosmeticSlot.HELMET, "minecraft:gold/phantom");
    public static final Cosmetic GOLD_PHANTOM_CHESTPLATE = new Cosmetic(
            "gold/phantom_chestplate", "Gold Phantom Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/phantom");
    public static final Cosmetic GOLD_PHANTOM_LEGGINGS = new Cosmetic(
            "gold/phantom_leggings", "Gold Phantom Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/phantom");
    public static final Cosmetic GOLD_PHANTOM_BOOTS = new Cosmetic(
            "gold/phantom_boots", "Gold Phantom Boots", CosmeticSlot.BOOTS, "minecraft:gold/phantom");

    public static final Cosmetic GOLD_PIGLIN_HELMET = new Cosmetic(
            "gold/piglin_helmet", "Gold Piglin Helmet", CosmeticSlot.HELMET, "minecraft:gold/piglin");
    public static final Cosmetic GOLD_PIGLIN_CHESTPLATE = new Cosmetic(
            "gold/piglin_chestplate", "Gold Piglin Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/piglin");
    public static final Cosmetic GOLD_PIGLIN_LEGGINGS = new Cosmetic(
            "gold/piglin_leggings", "Gold Piglin Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/piglin");
    public static final Cosmetic GOLD_PIGLIN_BOOTS = new Cosmetic(
            "gold/piglin_boots", "Gold Piglin Boots", CosmeticSlot.BOOTS, "minecraft:gold/piglin");

    public static final Cosmetic GOLD_PLATE_HELMET = new Cosmetic(
            "gold/plate_helmet", "Gold Plate Helmet", CosmeticSlot.HELMET, "minecraft:gold/plate");
    public static final Cosmetic GOLD_PLATE_CHESTPLATE = new Cosmetic(
            "gold/plate_chestplate", "Gold Plate Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/plate");
    public static final Cosmetic GOLD_PLATE_LEGGINGS = new Cosmetic(
            "gold/plate_leggings", "Gold Plate Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/plate");
    public static final Cosmetic GOLD_PLATE_BOOTS = new Cosmetic(
            "gold/plate_boots", "Gold Plate Boots", CosmeticSlot.BOOTS, "minecraft:gold/plate");

    public static final Cosmetic GOLD_PROUD_HELMET = new Cosmetic(
            "gold/proud_helmet", "Gold Proud Helmet", CosmeticSlot.HELMET, "minecraft:gold/proud");
    public static final Cosmetic GOLD_PROUD_CHESTPLATE = new Cosmetic(
            "gold/proud_chestplate", "Gold Proud Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/proud");
    public static final Cosmetic GOLD_PROUD_LEGGINGS = new Cosmetic(
            "gold/proud_leggings", "Gold Proud Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/proud");
    public static final Cosmetic GOLD_PROUD_BOOTS = new Cosmetic(
            "gold/proud_boots", "Gold Proud Boots", CosmeticSlot.BOOTS, "minecraft:gold/proud");

    public static final Cosmetic GOLD_RENEGADE_HELMET = new Cosmetic(
            "gold/renegade_helmet", "Gold Renegade Helmet", CosmeticSlot.HELMET, "minecraft:gold/renegade");
    public static final Cosmetic GOLD_RENEGADE_CHESTPLATE = new Cosmetic(
            "gold/renegade_chestplate", "Gold Renegade Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/renegade");
    public static final Cosmetic GOLD_RENEGADE_LEGGINGS = new Cosmetic(
            "gold/renegade_leggings", "Gold Renegade Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/renegade");
    public static final Cosmetic GOLD_RENEGADE_BOOTS = new Cosmetic(
            "gold/renegade_boots", "Gold Renegade Boots", CosmeticSlot.BOOTS, "minecraft:gold/renegade");

    public static final Cosmetic GOLD_ROYAL_HELMET = new Cosmetic(
            "gold/royal_helmet", "Gold Royal Helmet", CosmeticSlot.HELMET, "minecraft:gold/royal");
    public static final Cosmetic GOLD_ROYAL_CHESTPLATE = new Cosmetic(
            "gold/royal_chestplate", "Gold Royal Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/royal");
    public static final Cosmetic GOLD_ROYAL_LEGGINGS = new Cosmetic(
            "gold/royal_leggings", "Gold Royal Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/royal");
    public static final Cosmetic GOLD_ROYAL_BOOTS = new Cosmetic(
            "gold/royal_boots", "Gold Royal Boots", CosmeticSlot.BOOTS, "minecraft:gold/royal");

    public static final Cosmetic GOLD_RUBY_HELMET = new Cosmetic(
            "gold/ruby_helmet", "Gold Ruby Helmet", CosmeticSlot.HELMET, "minecraft:gold/ruby");
    public static final Cosmetic GOLD_RUBY_CHESTPLATE = new Cosmetic(
            "gold/ruby_chestplate", "Gold Ruby Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/ruby");
    public static final Cosmetic GOLD_RUBY_LEGGINGS = new Cosmetic(
            "gold/ruby_leggings", "Gold Ruby Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/ruby");
    public static final Cosmetic GOLD_RUBY_BOOTS = new Cosmetic(
            "gold/ruby_boots", "Gold Ruby Boots", CosmeticSlot.BOOTS, "minecraft:gold/ruby");

    public static final Cosmetic GOLD_SHOGUN_HELMET = new Cosmetic(
            "gold/shogun_helmet", "Gold Shogun Helmet", CosmeticSlot.HELMET, "minecraft:gold/shogun");
    public static final Cosmetic GOLD_SHOGUN_CHESTPLATE = new Cosmetic(
            "gold/shogun_chestplate", "Gold Shogun Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/shogun");
    public static final Cosmetic GOLD_SHOGUN_LEGGINGS = new Cosmetic(
            "gold/shogun_leggings", "Gold Shogun Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/shogun");
    public static final Cosmetic GOLD_SHOGUN_BOOTS = new Cosmetic(
            "gold/shogun_boots", "Gold Shogun Boots", CosmeticSlot.BOOTS, "minecraft:gold/shogun");

    public static final Cosmetic GOLD_SPELUNKER_HELMET = new Cosmetic(
            "gold/spelunker_helmet", "Gold Spelunker Helmet", CosmeticSlot.HELMET, "minecraft:gold/spelunker");
    public static final Cosmetic GOLD_SPELUNKER_CHESTPLATE = new Cosmetic(
            "gold/spelunker_chestplate", "Gold Spelunker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/spelunker");
    public static final Cosmetic GOLD_SPELUNKER_LEGGINGS = new Cosmetic(
            "gold/spelunker_leggings", "Gold Spelunker Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/spelunker");
    public static final Cosmetic GOLD_SPELUNKER_BOOTS = new Cosmetic(
            "gold/spelunker_boots", "Gold Spelunker Boots", CosmeticSlot.BOOTS, "minecraft:gold/spelunker");

    public static final Cosmetic GOLD_SPLEEF_HELMET = new Cosmetic(
            "gold/spleef_helmet", "Gold Spleef Helmet", CosmeticSlot.HELMET, "minecraft:gold/spleef");
    public static final Cosmetic GOLD_SPLEEF_CHESTPLATE = new Cosmetic(
            "gold/spleef_chestplate", "Gold Spleef Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/spleef");
    public static final Cosmetic GOLD_SPLEEF_LEGGINGS = new Cosmetic(
            "gold/spleef_leggings", "Gold Spleef Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/spleef");
    public static final Cosmetic GOLD_SPLEEF_BOOTS = new Cosmetic(
            "gold/spleef_boots", "Gold Spleef Boots", CosmeticSlot.BOOTS, "minecraft:gold/spleef");

    public static final Cosmetic GOLD_STALWART_HELMET = new Cosmetic(
            "gold/stalwart_helmet", "Gold Stalwart Helmet", CosmeticSlot.HELMET, "minecraft:gold/stalwart");
    public static final Cosmetic GOLD_STALWART_CHESTPLATE = new Cosmetic(
            "gold/stalwart_chestplate", "Gold Stalwart Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/stalwart");
    public static final Cosmetic GOLD_STALWART_LEGGINGS = new Cosmetic(
            "gold/stalwart_leggings", "Gold Stalwart Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/stalwart");
    public static final Cosmetic GOLD_STALWART_BOOTS = new Cosmetic(
            "gold/stalwart_boots", "Gold Stalwart Boots", CosmeticSlot.BOOTS, "minecraft:gold/stalwart");

    public static final Cosmetic GOLD_THIEF_HELMET = new Cosmetic(
            "gold/thief_helmet", "Gold Thief Helmet", CosmeticSlot.HELMET, "minecraft:gold/thief");
    public static final Cosmetic GOLD_THIEF_CHESTPLATE = new Cosmetic(
            "gold/thief_chestplate", "Gold Thief Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/thief");
    public static final Cosmetic GOLD_THIEF_LEGGINGS = new Cosmetic(
            "gold/thief_leggings", "Gold Thief Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/thief");
    public static final Cosmetic GOLD_THIEF_BOOTS = new Cosmetic(
            "gold/thief_boots", "Gold Thief Boots", CosmeticSlot.BOOTS, "minecraft:gold/thief");

    public static final Cosmetic GOLD_TRIAL_HELMET = new Cosmetic(
            "gold/trial_helmet", "Gold Trial Helmet", CosmeticSlot.HELMET, "minecraft:gold/trial");
    public static final Cosmetic GOLD_TRIAL_CHESTPLATE = new Cosmetic(
            "gold/trial_chestplate", "Gold Trial Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/trial");
    public static final Cosmetic GOLD_TRIAL_LEGGINGS = new Cosmetic(
            "gold/trial_leggings", "Gold Trial Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/trial");
    public static final Cosmetic GOLD_TRIAL_BOOTS = new Cosmetic(
            "gold/trial_boots", "Gold Trial Boots", CosmeticSlot.BOOTS, "minecraft:gold/trial");

    public static final Cosmetic GOLD_VEMI_HELMET = new Cosmetic(
            "gold/vemi_helmet", "Gold Vemi Helmet", CosmeticSlot.HELMET, "minecraft:gold/vemi");
    public static final Cosmetic GOLD_VEMI_CHESTPLATE = new Cosmetic(
            "gold/vemi_chestplate", "Gold Vemi Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/vemi");
    public static final Cosmetic GOLD_VEMI_LEGGINGS = new Cosmetic(
            "gold/vemi_leggings", "Gold Vemi Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/vemi");
    public static final Cosmetic GOLD_VEMI_BOOTS = new Cosmetic(
            "gold/vemi_boots", "Gold Vemi Boots", CosmeticSlot.BOOTS, "minecraft:gold/vemi");

    public static final Cosmetic GOLD_WOLF_HELMET = new Cosmetic(
            "gold/wolf_helmet", "Gold Wolf Helmet", CosmeticSlot.HELMET, "minecraft:gold/wolf");
    public static final Cosmetic GOLD_WOLF_CHESTPLATE = new Cosmetic(
            "gold/wolf_chestplate", "Gold Wolf Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:gold/wolf");
    public static final Cosmetic GOLD_WOLF_LEGGINGS = new Cosmetic(
            "gold/wolf_leggings", "Gold Wolf Leggings", CosmeticSlot.LEGGINGS, "minecraft:gold/wolf");
    public static final Cosmetic GOLD_WOLF_BOOTS = new Cosmetic(
            "gold/wolf_boots", "Gold Wolf Boots", CosmeticSlot.BOOTS, "minecraft:gold/wolf");

    public static final Cosmetic DIAMOND_AR_USEFUL_HELMET = new Cosmetic(
            "diamond/ar_useful_helmet", "Diamond Ar Useful Helmet", CosmeticSlot.HELMET, "minecraft:diamond/ar_useful");
    public static final Cosmetic DIAMOND_AR_USEFUL_CHESTPLATE = new Cosmetic(
            "diamond/ar_useful_chestplate", "Diamond Ar Useful Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/ar_useful");
    public static final Cosmetic DIAMOND_AR_USEFUL_LEGGINGS = new Cosmetic(
            "diamond/ar_useful_leggings", "Diamond Ar Useful Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/ar_useful");
    public static final Cosmetic DIAMOND_AR_USEFUL_BOOTS = new Cosmetic(
            "diamond/ar_useful_boots", "Diamond Ar Useful Boots", CosmeticSlot.BOOTS, "minecraft:diamond/ar_useful");

    public static final Cosmetic DIAMOND_AR_HELMET = new Cosmetic(
            "diamond/ar_helmet", "Diamond Ar Helmet", CosmeticSlot.HELMET, "minecraft:diamond/ar");
    public static final Cosmetic DIAMOND_AR_CHESTPLATE = new Cosmetic(
            "diamond/ar_chestplate", "Diamond Ar Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/ar");
    public static final Cosmetic DIAMOND_AR_LEGGINGS = new Cosmetic(
            "diamond/ar_leggings", "Diamond Ar Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/ar");
    public static final Cosmetic DIAMOND_AR_BOOTS = new Cosmetic(
            "diamond/ar_boots", "Diamond Ar Boots", CosmeticSlot.BOOTS, "minecraft:diamond/ar");

    public static final Cosmetic DIAMOND_CHAMPION_HELMET = new Cosmetic(
            "diamond/champion_helmet", "Diamond Champion Helmet", CosmeticSlot.HELMET, "minecraft:diamond/champion");
    public static final Cosmetic DIAMOND_CHAMPION_CHESTPLATE = new Cosmetic(
            "diamond/champion_chestplate", "Diamond Champion Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/champion");
    public static final Cosmetic DIAMOND_CHAMPION_LEGGINGS = new Cosmetic(
            "diamond/champion_leggings", "Diamond Champion Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/champion");
    public static final Cosmetic DIAMOND_CHAMPION_BOOTS = new Cosmetic(
            "diamond/champion_boots", "Diamond Champion Boots", CosmeticSlot.BOOTS, "minecraft:diamond/champion");

    public static final Cosmetic DIAMOND_COMBAT_HELMET = new Cosmetic(
            "diamond/combat_helmet", "Diamond Combat Helmet", CosmeticSlot.HELMET, "minecraft:diamond/combat");
    public static final Cosmetic DIAMOND_COMBAT_CHESTPLATE = new Cosmetic(
            "diamond/combat_chestplate", "Diamond Combat Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/combat");
    public static final Cosmetic DIAMOND_COMBAT_LEGGINGS = new Cosmetic(
            "diamond/combat_leggings", "Diamond Combat Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/combat");
    public static final Cosmetic DIAMOND_COMBAT_BOOTS = new Cosmetic(
            "diamond/combat_boots", "Diamond Combat Boots", CosmeticSlot.BOOTS, "minecraft:diamond/combat");

    public static final Cosmetic DIAMOND_COMMAND_BLOCK_HELMET = new Cosmetic(
            "diamond/command_block_helmet", "Diamond Command Block Helmet", CosmeticSlot.HELMET, "minecraft:diamond/command_block");
    public static final Cosmetic DIAMOND_COMMAND_BLOCK_CHESTPLATE = new Cosmetic(
            "diamond/command_block_chestplate", "Diamond Command Block Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/command_block");
    public static final Cosmetic DIAMOND_COMMAND_BLOCK_LEGGINGS = new Cosmetic(
            "diamond/command_block_leggings", "Diamond Command Block Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/command_block");
    public static final Cosmetic DIAMOND_COMMAND_BLOCK_BOOTS = new Cosmetic(
            "diamond/command_block_boots", "Diamond Command Block Boots", CosmeticSlot.BOOTS, "minecraft:diamond/command_block");

    public static final Cosmetic DIAMOND_CREED_HELMET = new Cosmetic(
            "diamond/creed_helmet", "Diamond Creed Helmet", CosmeticSlot.HELMET, "minecraft:diamond/creed");
    public static final Cosmetic DIAMOND_CREED_CHESTPLATE = new Cosmetic(
            "diamond/creed_chestplate", "Diamond Creed Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/creed");
    public static final Cosmetic DIAMOND_CREED_LEGGINGS = new Cosmetic(
            "diamond/creed_leggings", "Diamond Creed Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/creed");
    public static final Cosmetic DIAMOND_CREED_BOOTS = new Cosmetic(
            "diamond/creed_boots", "Diamond Creed Boots", CosmeticSlot.BOOTS, "minecraft:diamond/creed");

    public static final Cosmetic DIAMOND_DARK_HELMET = new Cosmetic(
            "diamond/dark_helmet", "Diamond Dark Helmet", CosmeticSlot.HELMET, "minecraft:diamond/dark");
    public static final Cosmetic DIAMOND_DARK_CHESTPLATE = new Cosmetic(
            "diamond/dark_chestplate", "Diamond Dark Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/dark");
    public static final Cosmetic DIAMOND_DARK_LEGGINGS = new Cosmetic(
            "diamond/dark_leggings", "Diamond Dark Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/dark");
    public static final Cosmetic DIAMOND_DARK_BOOTS = new Cosmetic(
            "diamond/dark_boots", "Diamond Dark Boots", CosmeticSlot.BOOTS, "minecraft:diamond/dark");

    public static final Cosmetic DIAMOND_DRAGON_HELMET = new Cosmetic(
            "diamond/dragon_helmet", "Diamond Dragon Helmet", CosmeticSlot.HELMET, "minecraft:diamond/dragon");
    public static final Cosmetic DIAMOND_DRAGON_CHESTPLATE = new Cosmetic(
            "diamond/dragon_chestplate", "Diamond Dragon Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/dragon");
    public static final Cosmetic DIAMOND_DRAGON_LEGGINGS = new Cosmetic(
            "diamond/dragon_leggings", "Diamond Dragon Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/dragon");
    public static final Cosmetic DIAMOND_DRAGON_BOOTS = new Cosmetic(
            "diamond/dragon_boots", "Diamond Dragon Boots", CosmeticSlot.BOOTS, "minecraft:diamond/dragon");

    public static final Cosmetic DIAMOND_EMERALD_HELMET = new Cosmetic(
            "diamond/emerald_helmet", "Diamond Emerald Helmet", CosmeticSlot.HELMET, "minecraft:diamond/emerald");
    public static final Cosmetic DIAMOND_EMERALD_CHESTPLATE = new Cosmetic(
            "diamond/emerald_chestplate", "Diamond Emerald Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/emerald");
    public static final Cosmetic DIAMOND_EMERALD_LEGGINGS = new Cosmetic(
            "diamond/emerald_leggings", "Diamond Emerald Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/emerald");
    public static final Cosmetic DIAMOND_EMERALD_BOOTS = new Cosmetic(
            "diamond/emerald_boots", "Diamond Emerald Boots", CosmeticSlot.BOOTS, "minecraft:diamond/emerald");

    public static final Cosmetic DIAMOND_ENDER_HELMET = new Cosmetic(
            "diamond/ender_helmet", "Diamond Ender Helmet", CosmeticSlot.HELMET, "minecraft:diamond/ender");
    public static final Cosmetic DIAMOND_ENDER_CHESTPLATE = new Cosmetic(
            "diamond/ender_chestplate", "Diamond Ender Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/ender");
    public static final Cosmetic DIAMOND_ENDER_LEGGINGS = new Cosmetic(
            "diamond/ender_leggings", "Diamond Ender Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/ender");
    public static final Cosmetic DIAMOND_ENDER_BOOTS = new Cosmetic(
            "diamond/ender_boots", "Diamond Ender Boots", CosmeticSlot.BOOTS, "minecraft:diamond/ender");

    public static final Cosmetic DIAMOND_FOX_HELMET = new Cosmetic(
            "diamond/fox_helmet", "Diamond Fox Helmet", CosmeticSlot.HELMET, "minecraft:diamond/fox");
    public static final Cosmetic DIAMOND_FOX_CHESTPLATE = new Cosmetic(
            "diamond/fox_chestplate", "Diamond Fox Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/fox");
    public static final Cosmetic DIAMOND_FOX_LEGGINGS = new Cosmetic(
            "diamond/fox_leggings", "Diamond Fox Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/fox");
    public static final Cosmetic DIAMOND_FOX_BOOTS = new Cosmetic(
            "diamond/fox_boots", "Diamond Fox Boots", CosmeticSlot.BOOTS, "minecraft:diamond/fox");

    public static final Cosmetic DIAMOND_FULL_METAL_HELMET = new Cosmetic(
            "diamond/full_metal_helmet", "Diamond Full Metal Helmet", CosmeticSlot.HELMET, "minecraft:diamond/full_metal");
    public static final Cosmetic DIAMOND_FULL_METAL_CHESTPLATE = new Cosmetic(
            "diamond/full_metal_chestplate", "Diamond Full Metal Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/full_metal");
    public static final Cosmetic DIAMOND_FULL_METAL_LEGGINGS = new Cosmetic(
            "diamond/full_metal_leggings", "Diamond Full Metal Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/full_metal");
    public static final Cosmetic DIAMOND_FULL_METAL_BOOTS = new Cosmetic(
            "diamond/full_metal_boots", "Diamond Full Metal Boots", CosmeticSlot.BOOTS, "minecraft:diamond/full_metal");

    public static final Cosmetic DIAMOND_GHOSTLY_HELMET = new Cosmetic(
            "diamond/ghostly_helmet", "Diamond Ghostly Helmet", CosmeticSlot.HELMET, "minecraft:diamond/ghostly");
    public static final Cosmetic DIAMOND_GHOSTLY_CHESTPLATE = new Cosmetic(
            "diamond/ghostly_chestplate", "Diamond Ghostly Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/ghostly");
    public static final Cosmetic DIAMOND_GHOSTLY_LEGGINGS = new Cosmetic(
            "diamond/ghostly_leggings", "Diamond Ghostly Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/ghostly");
    public static final Cosmetic DIAMOND_GHOSTLY_BOOTS = new Cosmetic(
            "diamond/ghostly_boots", "Diamond Ghostly Boots", CosmeticSlot.BOOTS, "minecraft:diamond/ghostly");

    public static final Cosmetic DIAMOND_GRIM_HELMET = new Cosmetic(
            "diamond/grim_helmet", "Diamond Grim Helmet", CosmeticSlot.HELMET, "minecraft:diamond/grim");
    public static final Cosmetic DIAMOND_GRIM_CHESTPLATE = new Cosmetic(
            "diamond/grim_chestplate", "Diamond Grim Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/grim");
    public static final Cosmetic DIAMOND_GRIM_LEGGINGS = new Cosmetic(
            "diamond/grim_leggings", "Diamond Grim Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/grim");
    public static final Cosmetic DIAMOND_GRIM_BOOTS = new Cosmetic(
            "diamond/grim_boots", "Diamond Grim Boots", CosmeticSlot.BOOTS, "minecraft:diamond/grim");

    public static final Cosmetic DIAMOND_GUARD_HELMET = new Cosmetic(
            "diamond/guard_helmet", "Diamond Guard Helmet", CosmeticSlot.HELMET, "minecraft:diamond/guard");
    public static final Cosmetic DIAMOND_GUARD_CHESTPLATE = new Cosmetic(
            "diamond/guard_chestplate", "Diamond Guard Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/guard");
    public static final Cosmetic DIAMOND_GUARD_LEGGINGS = new Cosmetic(
            "diamond/guard_leggings", "Diamond Guard Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/guard");
    public static final Cosmetic DIAMOND_GUARD_BOOTS = new Cosmetic(
            "diamond/guard_boots", "Diamond Guard Boots", CosmeticSlot.BOOTS, "minecraft:diamond/guard");

    public static final Cosmetic DIAMOND_HIGHLAND_HELMET = new Cosmetic(
            "diamond/highland_helmet", "Diamond Highland Helmet", CosmeticSlot.HELMET, "minecraft:diamond/highland");
    public static final Cosmetic DIAMOND_HIGHLAND_CHESTPLATE = new Cosmetic(
            "diamond/highland_chestplate", "Diamond Highland Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/highland");
    public static final Cosmetic DIAMOND_HIGHLAND_LEGGINGS = new Cosmetic(
            "diamond/highland_leggings", "Diamond Highland Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/highland");
    public static final Cosmetic DIAMOND_HIGHLAND_BOOTS = new Cosmetic(
            "diamond/highland_boots", "Diamond Highland Boots", CosmeticSlot.BOOTS, "minecraft:diamond/highland");

    public static final Cosmetic DIAMOND_LAVA_INFUSED_HELMET = new Cosmetic(
            "diamond/lava_infused_helmet", "Diamond Lava Infused Helmet", CosmeticSlot.HELMET, "minecraft:diamond/lava_infused");
    public static final Cosmetic DIAMOND_LAVA_INFUSED_CHESTPLATE = new Cosmetic(
            "diamond/lava_infused_chestplate", "Diamond Lava Infused Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/lava_infused");
    public static final Cosmetic DIAMOND_LAVA_INFUSED_LEGGINGS = new Cosmetic(
            "diamond/lava_infused_leggings", "Diamond Lava Infused Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/lava_infused");
    public static final Cosmetic DIAMOND_LAVA_INFUSED_BOOTS = new Cosmetic(
            "diamond/lava_infused_boots", "Diamond Lava Infused Boots", CosmeticSlot.BOOTS, "minecraft:diamond/lava_infused");

    public static final Cosmetic DIAMOND_MYSTERY_HELMET = new Cosmetic(
            "diamond/mystery_helmet", "Diamond Mystery Helmet", CosmeticSlot.HELMET, "minecraft:diamond/mystery");
    public static final Cosmetic DIAMOND_MYSTERY_CHESTPLATE = new Cosmetic(
            "diamond/mystery_chestplate", "Diamond Mystery Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/mystery");
    public static final Cosmetic DIAMOND_MYSTERY_LEGGINGS = new Cosmetic(
            "diamond/mystery_leggings", "Diamond Mystery Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/mystery");
    public static final Cosmetic DIAMOND_MYSTERY_BOOTS = new Cosmetic(
            "diamond/mystery_boots", "Diamond Mystery Boots", CosmeticSlot.BOOTS, "minecraft:diamond/mystery");

    public static final Cosmetic DIAMOND_NETHERWALKER_HELMET = new Cosmetic(
            "diamond/netherwalker_helmet", "Diamond Netherwalker Helmet", CosmeticSlot.HELMET, "minecraft:diamond/netherwalker");
    public static final Cosmetic DIAMOND_NETHERWALKER_CHESTPLATE = new Cosmetic(
            "diamond/netherwalker_chestplate", "Diamond Netherwalker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/netherwalker");
    public static final Cosmetic DIAMOND_NETHERWALKER_LEGGINGS = new Cosmetic(
            "diamond/netherwalker_leggings", "Diamond Netherwalker Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/netherwalker");
    public static final Cosmetic DIAMOND_NETHERWALKER_BOOTS = new Cosmetic(
            "diamond/netherwalker_boots", "Diamond Netherwalker Boots", CosmeticSlot.BOOTS, "minecraft:diamond/netherwalker");

    public static final Cosmetic DIAMOND_OCELOT_HELMET = new Cosmetic(
            "diamond/ocelot_helmet", "Diamond Ocelot Helmet", CosmeticSlot.HELMET, "minecraft:diamond/ocelot");
    public static final Cosmetic DIAMOND_OCELOT_CHESTPLATE = new Cosmetic(
            "diamond/ocelot_chestplate", "Diamond Ocelot Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/ocelot");
    public static final Cosmetic DIAMOND_OCELOT_LEGGINGS = new Cosmetic(
            "diamond/ocelot_leggings", "Diamond Ocelot Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/ocelot");
    public static final Cosmetic DIAMOND_OCELOT_BOOTS = new Cosmetic(
            "diamond/ocelot_boots", "Diamond Ocelot Boots", CosmeticSlot.BOOTS, "minecraft:diamond/ocelot");

    public static final Cosmetic DIAMOND_OPULENT_HELMET = new Cosmetic(
            "diamond/opulent_helmet", "Diamond Opulent Helmet", CosmeticSlot.HELMET, "minecraft:diamond/opulent");
    public static final Cosmetic DIAMOND_OPULENT_CHESTPLATE = new Cosmetic(
            "diamond/opulent_chestplate", "Diamond Opulent Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/opulent");
    public static final Cosmetic DIAMOND_OPULENT_LEGGINGS = new Cosmetic(
            "diamond/opulent_leggings", "Diamond Opulent Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/opulent");
    public static final Cosmetic DIAMOND_OPULENT_BOOTS = new Cosmetic(
            "diamond/opulent_boots", "Diamond Opulent Boots", CosmeticSlot.BOOTS, "minecraft:diamond/opulent");

    public static final Cosmetic DIAMOND_ORE_HELMET = new Cosmetic(
            "diamond/ore_helmet", "Diamond Ore Helmet", CosmeticSlot.HELMET, "minecraft:diamond/ore");
    public static final Cosmetic DIAMOND_ORE_CHESTPLATE = new Cosmetic(
            "diamond/ore_chestplate", "Diamond Ore Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/ore");
    public static final Cosmetic DIAMOND_ORE_LEGGINGS = new Cosmetic(
            "diamond/ore_leggings", "Diamond Ore Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/ore");
    public static final Cosmetic DIAMOND_ORE_BOOTS = new Cosmetic(
            "diamond/ore_boots", "Diamond Ore Boots", CosmeticSlot.BOOTS, "minecraft:diamond/ore");

    public static final Cosmetic DIAMOND_PAMA_HELMET = new Cosmetic(
            "diamond/pama_helmet", "Diamond Pama Helmet", CosmeticSlot.HELMET, "minecraft:diamond/pama");
    public static final Cosmetic DIAMOND_PAMA_CHESTPLATE = new Cosmetic(
            "diamond/pama_chestplate", "Diamond Pama Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/pama");
    public static final Cosmetic DIAMOND_PAMA_LEGGINGS = new Cosmetic(
            "diamond/pama_leggings", "Diamond Pama Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/pama");
    public static final Cosmetic DIAMOND_PAMA_BOOTS = new Cosmetic(
            "diamond/pama_boots", "Diamond Pama Boots", CosmeticSlot.BOOTS, "minecraft:diamond/pama");

    public static final Cosmetic DIAMOND_PHANTOM_HELMET = new Cosmetic(
            "diamond/phantom_helmet", "Diamond Phantom Helmet", CosmeticSlot.HELMET, "minecraft:diamond/phantom");
    public static final Cosmetic DIAMOND_PHANTOM_CHESTPLATE = new Cosmetic(
            "diamond/phantom_chestplate", "Diamond Phantom Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/phantom");
    public static final Cosmetic DIAMOND_PHANTOM_LEGGINGS = new Cosmetic(
            "diamond/phantom_leggings", "Diamond Phantom Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/phantom");
    public static final Cosmetic DIAMOND_PHANTOM_BOOTS = new Cosmetic(
            "diamond/phantom_boots", "Diamond Phantom Boots", CosmeticSlot.BOOTS, "minecraft:diamond/phantom");

    public static final Cosmetic DIAMOND_PIGLIN_HELMET = new Cosmetic(
            "diamond/piglin_helmet", "Diamond Piglin Helmet", CosmeticSlot.HELMET, "minecraft:diamond/piglin");
    public static final Cosmetic DIAMOND_PIGLIN_CHESTPLATE = new Cosmetic(
            "diamond/piglin_chestplate", "Diamond Piglin Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/piglin");
    public static final Cosmetic DIAMOND_PIGLIN_LEGGINGS = new Cosmetic(
            "diamond/piglin_leggings", "Diamond Piglin Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/piglin");
    public static final Cosmetic DIAMOND_PIGLIN_BOOTS = new Cosmetic(
            "diamond/piglin_boots", "Diamond Piglin Boots", CosmeticSlot.BOOTS, "minecraft:diamond/piglin");

    public static final Cosmetic DIAMOND_PLATE_HELMET = new Cosmetic(
            "diamond/plate_helmet", "Diamond Plate Helmet", CosmeticSlot.HELMET, "minecraft:diamond/plate");
    public static final Cosmetic DIAMOND_PLATE_CHESTPLATE = new Cosmetic(
            "diamond/plate_chestplate", "Diamond Plate Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/plate");
    public static final Cosmetic DIAMOND_PLATE_LEGGINGS = new Cosmetic(
            "diamond/plate_leggings", "Diamond Plate Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/plate");
    public static final Cosmetic DIAMOND_PLATE_BOOTS = new Cosmetic(
            "diamond/plate_boots", "Diamond Plate Boots", CosmeticSlot.BOOTS, "minecraft:diamond/plate");

    public static final Cosmetic DIAMOND_PROUD_HELMET = new Cosmetic(
            "diamond/proud_helmet", "Diamond Proud Helmet", CosmeticSlot.HELMET, "minecraft:diamond/proud");
    public static final Cosmetic DIAMOND_PROUD_CHESTPLATE = new Cosmetic(
            "diamond/proud_chestplate", "Diamond Proud Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/proud");
    public static final Cosmetic DIAMOND_PROUD_LEGGINGS = new Cosmetic(
            "diamond/proud_leggings", "Diamond Proud Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/proud");
    public static final Cosmetic DIAMOND_PROUD_BOOTS = new Cosmetic(
            "diamond/proud_boots", "Diamond Proud Boots", CosmeticSlot.BOOTS, "minecraft:diamond/proud");

    public static final Cosmetic DIAMOND_RENEGADE_HELMET = new Cosmetic(
            "diamond/renegade_helmet", "Diamond Renegade Helmet", CosmeticSlot.HELMET, "minecraft:diamond/renegade");
    public static final Cosmetic DIAMOND_RENEGADE_CHESTPLATE = new Cosmetic(
            "diamond/renegade_chestplate", "Diamond Renegade Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/renegade");
    public static final Cosmetic DIAMOND_RENEGADE_LEGGINGS = new Cosmetic(
            "diamond/renegade_leggings", "Diamond Renegade Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/renegade");
    public static final Cosmetic DIAMOND_RENEGADE_BOOTS = new Cosmetic(
            "diamond/renegade_boots", "Diamond Renegade Boots", CosmeticSlot.BOOTS, "minecraft:diamond/renegade");

    public static final Cosmetic DIAMOND_ROYAL_HELMET = new Cosmetic(
            "diamond/royal_helmet", "Diamond Royal Helmet", CosmeticSlot.HELMET, "minecraft:diamond/royal");
    public static final Cosmetic DIAMOND_ROYAL_CHESTPLATE = new Cosmetic(
            "diamond/royal_chestplate", "Diamond Royal Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/royal");
    public static final Cosmetic DIAMOND_ROYAL_LEGGINGS = new Cosmetic(
            "diamond/royal_leggings", "Diamond Royal Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/royal");
    public static final Cosmetic DIAMOND_ROYAL_BOOTS = new Cosmetic(
            "diamond/royal_boots", "Diamond Royal Boots", CosmeticSlot.BOOTS, "minecraft:diamond/royal");

    public static final Cosmetic DIAMOND_RUBY_HELMET = new Cosmetic(
            "diamond/ruby_helmet", "Diamond Ruby Helmet", CosmeticSlot.HELMET, "minecraft:diamond/ruby");
    public static final Cosmetic DIAMOND_RUBY_CHESTPLATE = new Cosmetic(
            "diamond/ruby_chestplate", "Diamond Ruby Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/ruby");
    public static final Cosmetic DIAMOND_RUBY_LEGGINGS = new Cosmetic(
            "diamond/ruby_leggings", "Diamond Ruby Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/ruby");
    public static final Cosmetic DIAMOND_RUBY_BOOTS = new Cosmetic(
            "diamond/ruby_boots", "Diamond Ruby Boots", CosmeticSlot.BOOTS, "minecraft:diamond/ruby");

    public static final Cosmetic DIAMOND_SHOGUN_HELMET = new Cosmetic(
            "diamond/shogun_helmet", "Diamond Shogun Helmet", CosmeticSlot.HELMET, "minecraft:diamond/shogun");
    public static final Cosmetic DIAMOND_SHOGUN_CHESTPLATE = new Cosmetic(
            "diamond/shogun_chestplate", "Diamond Shogun Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/shogun");
    public static final Cosmetic DIAMOND_SHOGUN_LEGGINGS = new Cosmetic(
            "diamond/shogun_leggings", "Diamond Shogun Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/shogun");
    public static final Cosmetic DIAMOND_SHOGUN_BOOTS = new Cosmetic(
            "diamond/shogun_boots", "Diamond Shogun Boots", CosmeticSlot.BOOTS, "minecraft:diamond/shogun");

    public static final Cosmetic DIAMOND_SPELUNKER_HELMET = new Cosmetic(
            "diamond/spelunker_helmet", "Diamond Spelunker Helmet", CosmeticSlot.HELMET, "minecraft:diamond/spelunker");
    public static final Cosmetic DIAMOND_SPELUNKER_CHESTPLATE = new Cosmetic(
            "diamond/spelunker_chestplate", "Diamond Spelunker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/spelunker");
    public static final Cosmetic DIAMOND_SPELUNKER_LEGGINGS = new Cosmetic(
            "diamond/spelunker_leggings", "Diamond Spelunker Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/spelunker");
    public static final Cosmetic DIAMOND_SPELUNKER_BOOTS = new Cosmetic(
            "diamond/spelunker_boots", "Diamond Spelunker Boots", CosmeticSlot.BOOTS, "minecraft:diamond/spelunker");

    public static final Cosmetic DIAMOND_SPLEEF_HELMET = new Cosmetic(
            "diamond/spleef_helmet", "Diamond Spleef Helmet", CosmeticSlot.HELMET, "minecraft:diamond/spleef");
    public static final Cosmetic DIAMOND_SPLEEF_CHESTPLATE = new Cosmetic(
            "diamond/spleef_chestplate", "Diamond Spleef Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/spleef");
    public static final Cosmetic DIAMOND_SPLEEF_LEGGINGS = new Cosmetic(
            "diamond/spleef_leggings", "Diamond Spleef Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/spleef");
    public static final Cosmetic DIAMOND_SPLEEF_BOOTS = new Cosmetic(
            "diamond/spleef_boots", "Diamond Spleef Boots", CosmeticSlot.BOOTS, "minecraft:diamond/spleef");

    public static final Cosmetic DIAMOND_STALWART_HELMET = new Cosmetic(
            "diamond/stalwart_helmet", "Diamond Stalwart Helmet", CosmeticSlot.HELMET, "minecraft:diamond/stalwart");
    public static final Cosmetic DIAMOND_STALWART_CHESTPLATE = new Cosmetic(
            "diamond/stalwart_chestplate", "Diamond Stalwart Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/stalwart");
    public static final Cosmetic DIAMOND_STALWART_LEGGINGS = new Cosmetic(
            "diamond/stalwart_leggings", "Diamond Stalwart Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/stalwart");
    public static final Cosmetic DIAMOND_STALWART_BOOTS = new Cosmetic(
            "diamond/stalwart_boots", "Diamond Stalwart Boots", CosmeticSlot.BOOTS, "minecraft:diamond/stalwart");

    public static final Cosmetic DIAMOND_THIEF_HELMET = new Cosmetic(
            "diamond/thief_helmet", "Diamond Thief Helmet", CosmeticSlot.HELMET, "minecraft:diamond/thief");
    public static final Cosmetic DIAMOND_THIEF_CHESTPLATE = new Cosmetic(
            "diamond/thief_chestplate", "Diamond Thief Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/thief");
    public static final Cosmetic DIAMOND_THIEF_LEGGINGS = new Cosmetic(
            "diamond/thief_leggings", "Diamond Thief Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/thief");
    public static final Cosmetic DIAMOND_THIEF_BOOTS = new Cosmetic(
            "diamond/thief_boots", "Diamond Thief Boots", CosmeticSlot.BOOTS, "minecraft:diamond/thief");

    public static final Cosmetic DIAMOND_TRIAL_HELMET = new Cosmetic(
            "diamond/trial_helmet", "Diamond Trial Helmet", CosmeticSlot.HELMET, "minecraft:diamond/trial");
    public static final Cosmetic DIAMOND_TRIAL_CHESTPLATE = new Cosmetic(
            "diamond/trial_chestplate", "Diamond Trial Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/trial");
    public static final Cosmetic DIAMOND_TRIAL_LEGGINGS = new Cosmetic(
            "diamond/trial_leggings", "Diamond Trial Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/trial");
    public static final Cosmetic DIAMOND_TRIAL_BOOTS = new Cosmetic(
            "diamond/trial_boots", "Diamond Trial Boots", CosmeticSlot.BOOTS, "minecraft:diamond/trial");

    public static final Cosmetic DIAMOND_VEMI_HELMET = new Cosmetic(
            "diamond/vemi_helmet", "Diamond Vemi Helmet", CosmeticSlot.HELMET, "minecraft:diamond/vemi");
    public static final Cosmetic DIAMOND_VEMI_CHESTPLATE = new Cosmetic(
            "diamond/vemi_chestplate", "Diamond Vemi Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/vemi");
    public static final Cosmetic DIAMOND_VEMI_LEGGINGS = new Cosmetic(
            "diamond/vemi_leggings", "Diamond Vemi Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/vemi");
    public static final Cosmetic DIAMOND_VEMI_BOOTS = new Cosmetic(
            "diamond/vemi_boots", "Diamond Vemi Boots", CosmeticSlot.BOOTS, "minecraft:diamond/vemi");

    public static final Cosmetic DIAMOND_WOLF_HELMET = new Cosmetic(
            "diamond/wolf_helmet", "Diamond Wolf Helmet", CosmeticSlot.HELMET, "minecraft:diamond/wolf");
    public static final Cosmetic DIAMOND_WOLF_CHESTPLATE = new Cosmetic(
            "diamond/wolf_chestplate", "Diamond Wolf Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:diamond/wolf");
    public static final Cosmetic DIAMOND_WOLF_LEGGINGS = new Cosmetic(
            "diamond/wolf_leggings", "Diamond Wolf Leggings", CosmeticSlot.LEGGINGS, "minecraft:diamond/wolf");
    public static final Cosmetic DIAMOND_WOLF_BOOTS = new Cosmetic(
            "diamond/wolf_boots", "Diamond Wolf Boots", CosmeticSlot.BOOTS, "minecraft:diamond/wolf");

    public static final Cosmetic NETHERITE_ADAMANTIUM_IMPERVIUM_HELMET = new Cosmetic(
            "netherite/adamantium_impervium_helmet", "Adamantium Impervium Helmet", CosmeticSlot.HELMET, "minecraft:netherite/adamantium_impervium");
    public static final Cosmetic NETHERITE_ADAMANTIUM_IMPERVIUM_CHESTPLATE = new Cosmetic(
            "netherite/adamantium_impervium_chestplate", "Adamantium Impervium Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/adamantium_impervium");
    public static final Cosmetic NETHERITE_ADAMANTIUM_IMPERVIUM_LEGGINGS = new Cosmetic(
            "netherite/adamantium_impervium_leggings", "Adamantium Impervium Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/adamantium_impervium");
    public static final Cosmetic NETHERITE_ADAMANTIUM_IMPERVIUM_BOOTS = new Cosmetic(
            "netherite/adamantium_impervium_boots", "Adamantium Impervium Boots", CosmeticSlot.BOOTS, "minecraft:netherite/adamantium_impervium");

    public static final Cosmetic NETHERITE_AR_USEFUL_HELMET = new Cosmetic(
            "netherite/ar_useful_helmet", "Netherite Ar Useful Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ar_useful");
    public static final Cosmetic NETHERITE_AR_USEFUL_CHESTPLATE = new Cosmetic(
            "netherite/ar_useful_chestplate", "Netherite Ar Useful Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ar_useful");
    public static final Cosmetic NETHERITE_AR_USEFUL_LEGGINGS = new Cosmetic(
            "netherite/ar_useful_leggings", "Netherite Ar Useful Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ar_useful");
    public static final Cosmetic NETHERITE_AR_USEFUL_BOOTS = new Cosmetic(
            "netherite/ar_useful_boots", "Netherite Ar Useful Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ar_useful");

    public static final Cosmetic NETHERITE_AR_HELMET = new Cosmetic(
            "netherite/ar_helmet", "Netherite Ar Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ar");
    public static final Cosmetic NETHERITE_AR_CHESTPLATE = new Cosmetic(
            "netherite/ar_chestplate", "Netherite Ar Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ar");
    public static final Cosmetic NETHERITE_AR_LEGGINGS = new Cosmetic(
            "netherite/ar_leggings", "Netherite Ar Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ar");
    public static final Cosmetic NETHERITE_AR_BOOTS = new Cosmetic(
            "netherite/ar_boots", "Netherite Ar Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ar");

    public static final Cosmetic NETHERITE_AXEBREAKER_HELMET = new Cosmetic(
            "netherite/axebreaker_helmet", "Axebreaker Helmet", CosmeticSlot.HELMET, "minecraft:netherite/axebreaker");
    public static final Cosmetic NETHERITE_AXEBREAKER_CHESTPLATE = new Cosmetic(
            "netherite/axebreaker_chestplate", "Axebreaker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/axebreaker");
    public static final Cosmetic NETHERITE_AXEBREAKER_LEGGINGS = new Cosmetic(
            "netherite/axebreaker_leggings", "Axebreaker Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/axebreaker");
    public static final Cosmetic NETHERITE_AXEBREAKER_BOOTS = new Cosmetic(
            "netherite/axebreaker_boots", "Axebreaker Boots", CosmeticSlot.BOOTS, "minecraft:netherite/axebreaker");

    public static final Cosmetic NETHERITE_BARD_HELMET = new Cosmetic(
            "netherite/bard_helmet", "Bard Helmet", CosmeticSlot.HELMET, "minecraft:netherite/bard");
    public static final Cosmetic NETHERITE_BARD_CHESTPLATE = new Cosmetic(
            "netherite/bard_chestplate", "Bard Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/bard");
    public static final Cosmetic NETHERITE_BARD_LEGGINGS = new Cosmetic(
            "netherite/bard_leggings", "Bard Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/bard");
    public static final Cosmetic NETHERITE_BARD_BOOTS = new Cosmetic(
            "netherite/bard_boots", "Bard Boots", CosmeticSlot.BOOTS, "minecraft:netherite/bard");

    public static final Cosmetic NETHERITE_BATTLE_HELMET = new Cosmetic(
            "netherite/battle_helmet", "Battle Helmet", CosmeticSlot.HELMET, "minecraft:netherite/battle");
    public static final Cosmetic NETHERITE_BATTLE_CHESTPLATE = new Cosmetic(
            "netherite/battle_chestplate", "Battle Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/battle");
    public static final Cosmetic NETHERITE_BATTLE_LEGGINGS = new Cosmetic(
            "netherite/battle_leggings", "Battle Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/battle");
    public static final Cosmetic NETHERITE_BATTLE_BOOTS = new Cosmetic(
            "netherite/battle_boots", "Battle Boots", CosmeticSlot.BOOTS, "minecraft:netherite/battle");

    public static final Cosmetic NETHERITE_BEEHIVE_HELMET = new Cosmetic(
            "netherite/beehive_helmet", "Beehive Helmet", CosmeticSlot.HELMET, "minecraft:netherite/beehive");
    public static final Cosmetic NETHERITE_BEEHIVE_CHESTPLATE = new Cosmetic(
            "netherite/beehive_chestplate", "Beehive Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/beehive");
    public static final Cosmetic NETHERITE_BEEHIVE_LEGGINGS = new Cosmetic(
            "netherite/beehive_leggings", "Beehive Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/beehive");
    public static final Cosmetic NETHERITE_BEEHIVE_BOOTS = new Cosmetic(
            "netherite/beehive_boots", "Beehive Boots", CosmeticSlot.BOOTS, "minecraft:netherite/beehive");

    public static final Cosmetic NETHERITE_BEENEST_HELMET = new Cosmetic(
            "netherite/beenest_helmet", "Beenest Helmet", CosmeticSlot.HELMET, "minecraft:netherite/beenest");
    public static final Cosmetic NETHERITE_BEENEST_CHESTPLATE = new Cosmetic(
            "netherite/beenest_chestplate", "Beenest Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/beenest");
    public static final Cosmetic NETHERITE_BEENEST_LEGGINGS = new Cosmetic(
            "netherite/beenest_leggings", "Beenest Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/beenest");
    public static final Cosmetic NETHERITE_BEENEST_BOOTS = new Cosmetic(
            "netherite/beenest_boots", "Beenest Boots", CosmeticSlot.BOOTS, "minecraft:netherite/beenest");

    public static final Cosmetic NETHERITE_BLACK_SPOT_HELMET = new Cosmetic(
            "netherite/black_spot_helmet", "Black Spot Helmet", CosmeticSlot.HELMET, "minecraft:netherite/black_spot");
    public static final Cosmetic NETHERITE_BLACK_SPOT_CHESTPLATE = new Cosmetic(
            "netherite/black_spot_chestplate", "Black Spot Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/black_spot");
    public static final Cosmetic NETHERITE_BLACK_SPOT_LEGGINGS = new Cosmetic(
            "netherite/black_spot_leggings", "Black Spot Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/black_spot");
    public static final Cosmetic NETHERITE_BLACK_SPOT_BOOTS = new Cosmetic(
            "netherite/black_spot_boots", "Black Spot Boots", CosmeticSlot.BOOTS, "minecraft:netherite/black_spot");

    public static final Cosmetic NETHERITE_BLASTBREAKER_HELMET = new Cosmetic(
            "netherite/blastbreaker_helmet", "Blastbreaker Helmet", CosmeticSlot.HELMET, "minecraft:netherite/blastbreaker");
    public static final Cosmetic NETHERITE_BLASTBREAKER_CHESTPLATE = new Cosmetic(
            "netherite/blastbreaker_chestplate", "Blastbreaker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/blastbreaker");
    public static final Cosmetic NETHERITE_BLASTBREAKER_LEGGINGS = new Cosmetic(
            "netherite/blastbreaker_leggings", "Blastbreaker Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/blastbreaker");
    public static final Cosmetic NETHERITE_BLASTBREAKER_BOOTS = new Cosmetic(
            "netherite/blastbreaker_boots", "Blastbreaker Boots", CosmeticSlot.BOOTS, "minecraft:netherite/blastbreaker");

    public static final Cosmetic NETHERITE_BOOM_TOWN_HELMET = new Cosmetic(
            "netherite/boom_town_helmet", "Boom Town Helmet", CosmeticSlot.HELMET, "minecraft:netherite/boom_town");
    public static final Cosmetic NETHERITE_BOOM_TOWN_CHESTPLATE = new Cosmetic(
            "netherite/boom_town_chestplate", "Boom Town Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/boom_town");
    public static final Cosmetic NETHERITE_BOOM_TOWN_LEGGINGS = new Cosmetic(
            "netherite/boom_town_leggings", "Boom Town Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/boom_town");
    public static final Cosmetic NETHERITE_BOOM_TOWN_BOOTS = new Cosmetic(
            "netherite/boom_town_boots", "Boom Town Boots", CosmeticSlot.BOOTS, "minecraft:netherite/boom_town");

    public static final Cosmetic NETHERITE_CAULDRON_HELMET = new Cosmetic(
            "netherite/cauldron_helmet", "Cauldron Helmet", CosmeticSlot.HELMET, "minecraft:netherite/cauldron");
    public static final Cosmetic NETHERITE_CAULDRON_CHESTPLATE = new Cosmetic(
            "netherite/cauldron_chestplate", "Cauldron Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/cauldron");
    public static final Cosmetic NETHERITE_CAULDRON_LEGGINGS = new Cosmetic(
            "netherite/cauldron_leggings", "Cauldron Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/cauldron");
    public static final Cosmetic NETHERITE_CAULDRON_BOOTS = new Cosmetic(
            "netherite/cauldron_boots", "Cauldron Boots", CosmeticSlot.BOOTS, "minecraft:netherite/cauldron");

    public static final Cosmetic NETHERITE_CAVE_CRAWLER_HELMET = new Cosmetic(
            "netherite/cave_crawler_helmet", "Cave Crawler Helmet", CosmeticSlot.HELMET, "minecraft:netherite/cave_crawler");
    public static final Cosmetic NETHERITE_CAVE_CRAWLER_CHESTPLATE = new Cosmetic(
            "netherite/cave_crawler_chestplate", "Cave Crawler Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/cave_crawler");
    public static final Cosmetic NETHERITE_CAVE_CRAWLER_LEGGINGS = new Cosmetic(
            "netherite/cave_crawler_leggings", "Cave Crawler Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/cave_crawler");
    public static final Cosmetic NETHERITE_CAVE_CRAWLER_BOOTS = new Cosmetic(
            "netherite/cave_crawler_boots", "Cave Crawler Boots", CosmeticSlot.BOOTS, "minecraft:netherite/cave_crawler");

    public static final Cosmetic NETHERITE_CHAMPION_HELMET = new Cosmetic(
            "netherite/champion_helmet", "Netherite Champion Helmet", CosmeticSlot.HELMET, "minecraft:netherite/champion");
    public static final Cosmetic NETHERITE_CHAMPION_CHESTPLATE = new Cosmetic(
            "netherite/champion_chestplate", "Netherite Champion Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/champion");
    public static final Cosmetic NETHERITE_CHAMPION_LEGGINGS = new Cosmetic(
            "netherite/champion_leggings", "Netherite Champion Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/champion");
    public static final Cosmetic NETHERITE_CHAMPION_BOOTS = new Cosmetic(
            "netherite/champion_boots", "Netherite Champion Boots", CosmeticSlot.BOOTS, "minecraft:netherite/champion");

    public static final Cosmetic NETHERITE_CLIMBING_GEAR_HELMET = new Cosmetic(
            "netherite/climbing_gear_helmet", "Climbing Gear Helmet", CosmeticSlot.HELMET, "minecraft:netherite/climbing_gear");
    public static final Cosmetic NETHERITE_CLIMBING_GEAR_CHESTPLATE = new Cosmetic(
            "netherite/climbing_gear_chestplate", "Climbing Gear Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/climbing_gear");
    public static final Cosmetic NETHERITE_CLIMBING_GEAR_LEGGINGS = new Cosmetic(
            "netherite/climbing_gear_leggings", "Climbing Gear Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/climbing_gear");
    public static final Cosmetic NETHERITE_CLIMBING_GEAR_BOOTS = new Cosmetic(
            "netherite/climbing_gear_boots", "Climbing Gear Boots", CosmeticSlot.BOOTS, "minecraft:netherite/climbing_gear");

    public static final Cosmetic NETHERITE_CLOAKED_SKULL_HELMET = new Cosmetic(
            "netherite/cloaked_skull_helmet", "Cloaked Skull Helmet", CosmeticSlot.HELMET, "minecraft:netherite/cloaked_skull");
    public static final Cosmetic NETHERITE_CLOAKED_SKULL_CHESTPLATE = new Cosmetic(
            "netherite/cloaked_skull_chestplate", "Cloaked Skull Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/cloaked_skull");
    public static final Cosmetic NETHERITE_CLOAKED_SKULL_LEGGINGS = new Cosmetic(
            "netherite/cloaked_skull_leggings", "Cloaked Skull Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/cloaked_skull");
    public static final Cosmetic NETHERITE_CLOAKED_SKULL_BOOTS = new Cosmetic(
            "netherite/cloaked_skull_boots", "Cloaked Skull Boots", CosmeticSlot.BOOTS, "minecraft:netherite/cloaked_skull");

    public static final Cosmetic NETHERITE_COMBAT_HELMET = new Cosmetic(
            "netherite/combat_helmet", "Netherite Combat Helmet", CosmeticSlot.HELMET, "minecraft:netherite/combat");
    public static final Cosmetic NETHERITE_COMBAT_CHESTPLATE = new Cosmetic(
            "netherite/combat_chestplate", "Netherite Combat Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/combat");
    public static final Cosmetic NETHERITE_COMBAT_LEGGINGS = new Cosmetic(
            "netherite/combat_leggings", "Netherite Combat Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/combat");
    public static final Cosmetic NETHERITE_COMBAT_BOOTS = new Cosmetic(
            "netherite/combat_boots", "Netherite Combat Boots", CosmeticSlot.BOOTS, "minecraft:netherite/combat");

    public static final Cosmetic NETHERITE_COMMAND_BLOCK_HELMET = new Cosmetic(
            "netherite/command_block_helmet", "Netherite Command Block Helmet", CosmeticSlot.HELMET, "minecraft:netherite/command_block");
    public static final Cosmetic NETHERITE_COMMAND_BLOCK_CHESTPLATE = new Cosmetic(
            "netherite/command_block_chestplate", "Netherite Command Block Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/command_block");
    public static final Cosmetic NETHERITE_COMMAND_BLOCK_LEGGINGS = new Cosmetic(
            "netherite/command_block_leggings", "Netherite Command Block Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/command_block");
    public static final Cosmetic NETHERITE_COMMAND_BLOCK_BOOTS = new Cosmetic(
            "netherite/command_block_boots", "Netherite Command Block Boots", CosmeticSlot.BOOTS, "minecraft:netherite/command_block");

    public static final Cosmetic NETHERITE_CREED_HELMET = new Cosmetic(
            "netherite/creed_helmet", "Netherite Creed Helmet", CosmeticSlot.HELMET, "minecraft:netherite/creed");
    public static final Cosmetic NETHERITE_CREED_CHESTPLATE = new Cosmetic(
            "netherite/creed_chestplate", "Netherite Creed Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/creed");
    public static final Cosmetic NETHERITE_CREED_LEGGINGS = new Cosmetic(
            "netherite/creed_leggings", "Netherite Creed Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/creed");
    public static final Cosmetic NETHERITE_CREED_BOOTS = new Cosmetic(
            "netherite/creed_boots", "Netherite Creed Boots", CosmeticSlot.BOOTS, "minecraft:netherite/creed");

    public static final Cosmetic NETHERITE_CRUCUBLE_HELMET = new Cosmetic(
            "netherite/crucuble_helmet", "Crucuble Helmet", CosmeticSlot.HELMET, "minecraft:netherite/crucuble");
    public static final Cosmetic NETHERITE_CRUCUBLE_CHESTPLATE = new Cosmetic(
            "netherite/crucuble_chestplate", "Crucuble Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/crucuble");
    public static final Cosmetic NETHERITE_CRUCUBLE_LEGGINGS = new Cosmetic(
            "netherite/crucuble_leggings", "Crucuble Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/crucuble");
    public static final Cosmetic NETHERITE_CRUCUBLE_BOOTS = new Cosmetic(
            "netherite/crucuble_boots", "Crucuble Boots", CosmeticSlot.BOOTS, "minecraft:netherite/crucuble");

    public static final Cosmetic NETHERITE_CYKLOPPS_HELMET = new Cosmetic(
            "netherite/cyklopps_helmet", "Cyklopps Helmet", CosmeticSlot.HELMET, "minecraft:netherite/cyklopps");
    public static final Cosmetic NETHERITE_CYKLOPPS_CHESTPLATE = new Cosmetic(
            "netherite/cyklopps_chestplate", "Cyklopps Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/cyklopps");
    public static final Cosmetic NETHERITE_CYKLOPPS_LEGGINGS = new Cosmetic(
            "netherite/cyklopps_leggings", "Cyklopps Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/cyklopps");
    public static final Cosmetic NETHERITE_CYKLOPPS_BOOTS = new Cosmetic(
            "netherite/cyklopps_boots", "Cyklopps Boots", CosmeticSlot.BOOTS, "minecraft:netherite/cyklopps");

    public static final Cosmetic NETHERITE_DARK_HELMET = new Cosmetic(
            "netherite/dark_helmet", "Netherite Dark Helmet", CosmeticSlot.HELMET, "minecraft:netherite/dark");
    public static final Cosmetic NETHERITE_DARK_CHESTPLATE = new Cosmetic(
            "netherite/dark_chestplate", "Netherite Dark Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/dark");
    public static final Cosmetic NETHERITE_DARK_LEGGINGS = new Cosmetic(
            "netherite/dark_leggings", "Netherite Dark Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/dark");
    public static final Cosmetic NETHERITE_DARK_BOOTS = new Cosmetic(
            "netherite/dark_boots", "Netherite Dark Boots", CosmeticSlot.BOOTS, "minecraft:netherite/dark");

    public static final Cosmetic NETHERITE_DRAGON_HELMET = new Cosmetic(
            "netherite/dragon_helmet", "Netherite Dragon Helmet", CosmeticSlot.HELMET, "minecraft:netherite/dragon");
    public static final Cosmetic NETHERITE_DRAGON_CHESTPLATE = new Cosmetic(
            "netherite/dragon_chestplate", "Netherite Dragon Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/dragon");
    public static final Cosmetic NETHERITE_DRAGON_LEGGINGS = new Cosmetic(
            "netherite/dragon_leggings", "Netherite Dragon Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/dragon");
    public static final Cosmetic NETHERITE_DRAGON_BOOTS = new Cosmetic(
            "netherite/dragon_boots", "Netherite Dragon Boots", CosmeticSlot.BOOTS, "minecraft:netherite/dragon");

    public static final Cosmetic NETHERITE_DRAGONMASTER_HELMET = new Cosmetic(
            "netherite/dragonmaster_helmet", "Dragonmaster Helmet", CosmeticSlot.HELMET, "minecraft:netherite/dragonmaster");
    public static final Cosmetic NETHERITE_DRAGONMASTER_CHESTPLATE = new Cosmetic(
            "netherite/dragonmaster_chestplate", "Dragonmaster Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/dragonmaster");
    public static final Cosmetic NETHERITE_DRAGONMASTER_LEGGINGS = new Cosmetic(
            "netherite/dragonmaster_leggings", "Dragonmaster Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/dragonmaster");
    public static final Cosmetic NETHERITE_DRAGONMASTER_BOOTS = new Cosmetic(
            "netherite/dragonmaster_boots", "Dragonmaster Boots", CosmeticSlot.BOOTS, "minecraft:netherite/dragonmaster");

    public static final Cosmetic NETHERITE_DRAGONSBANE_HELMET = new Cosmetic(
            "netherite/dragonsbane_helmet", "Dragonsbane Helmet", CosmeticSlot.HELMET, "minecraft:netherite/dragonsbane");
    public static final Cosmetic NETHERITE_DRAGONSBANE_CHESTPLATE = new Cosmetic(
            "netherite/dragonsbane_chestplate", "Dragonsbane Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/dragonsbane");
    public static final Cosmetic NETHERITE_DRAGONSBANE_LEGGINGS = new Cosmetic(
            "netherite/dragonsbane_leggings", "Dragonsbane Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/dragonsbane");
    public static final Cosmetic NETHERITE_DRAGONSBANE_BOOTS = new Cosmetic(
            "netherite/dragonsbane_boots", "Dragonsbane Boots", CosmeticSlot.BOOTS, "minecraft:netherite/dragonsbane");

    public static final Cosmetic NETHERITE_DRAGONSLAYER_HELMET = new Cosmetic(
            "netherite/dragonslayer_helmet", "Dragonslayer Helmet", CosmeticSlot.HELMET, "minecraft:netherite/dragonslayer");
    public static final Cosmetic NETHERITE_DRAGONSLAYER_CHESTPLATE = new Cosmetic(
            "netherite/dragonslayer_chestplate", "Dragonslayer Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/dragonslayer");
    public static final Cosmetic NETHERITE_DRAGONSLAYER_LEGGINGS = new Cosmetic(
            "netherite/dragonslayer_leggings", "Dragonslayer Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/dragonslayer");
    public static final Cosmetic NETHERITE_DRAGONSLAYER_BOOTS = new Cosmetic(
            "netherite/dragonslayer_boots", "Dragonslayer Boots", CosmeticSlot.BOOTS, "minecraft:netherite/dragonslayer");

    public static final Cosmetic NETHERITE_DROID_HELMET = new Cosmetic(
            "netherite/droid_helmet", "Droid Helmet", CosmeticSlot.HELMET, "minecraft:netherite/droid");
    public static final Cosmetic NETHERITE_DROID_CHESTPLATE = new Cosmetic(
            "netherite/droid_chestplate", "Droid Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/droid");
    public static final Cosmetic NETHERITE_DROID_LEGGINGS = new Cosmetic(
            "netherite/droid_leggings", "Droid Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/droid");
    public static final Cosmetic NETHERITE_DROID_BOOTS = new Cosmetic(
            "netherite/droid_boots", "Droid Boots", CosmeticSlot.BOOTS, "minecraft:netherite/droid");

    public static final Cosmetic NETHERITE_DROWNED_KING_HELMET = new Cosmetic(
            "netherite/drowned_king_helmet", "Netherite Drowned King Helmet", CosmeticSlot.HELMET, "minecraft:netherite/drowned_king");
    public static final Cosmetic NETHERITE_DROWNED_KING_CHESTPLATE = new Cosmetic(
            "netherite/drowned_king_chestplate", "Netherite Drowned King Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/drowned_king");
    public static final Cosmetic NETHERITE_DROWNED_KING_LEGGINGS = new Cosmetic(
            "netherite/drowned_king_leggings", "Netherite Drowned King Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/drowned_king");
    public static final Cosmetic NETHERITE_DROWNED_KING_BOOTS = new Cosmetic(
            "netherite/drowned_king_boots", "Netherite Drowned King Boots", CosmeticSlot.BOOTS, "minecraft:netherite/drowned_king");

    public static final Cosmetic NETHERITE_ELLEGAARD_HELMET = new Cosmetic(
            "netherite/ellegaard_helmet", "Ellegaard Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ellegaard");
    public static final Cosmetic NETHERITE_ELLEGAARD_CHESTPLATE = new Cosmetic(
            "netherite/ellegaard_chestplate", "Ellegaard Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ellegaard");
    public static final Cosmetic NETHERITE_ELLEGAARD_LEGGINGS = new Cosmetic(
            "netherite/ellegaard_leggings", "Ellegaard Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ellegaard");
    public static final Cosmetic NETHERITE_ELLEGAARD_BOOTS = new Cosmetic(
            "netherite/ellegaard_boots", "Ellegaard Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ellegaard");

    public static final Cosmetic NETHERITE_EMBER_HELMET = new Cosmetic(
            "netherite/ember_helmet", "Ember Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ember");
    public static final Cosmetic NETHERITE_EMBER_CHESTPLATE = new Cosmetic(
            "netherite/ember_chestplate", "Ember Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ember");
    public static final Cosmetic NETHERITE_EMBER_LEGGINGS = new Cosmetic(
            "netherite/ember_leggings", "Ember Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ember");
    public static final Cosmetic NETHERITE_EMBER_BOOTS = new Cosmetic(
            "netherite/ember_boots", "Ember Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ember");

    public static final Cosmetic NETHERITE_EMERALD_HELMET = new Cosmetic(
            "netherite/emerald_helmet", "Netherite Emerald Helmet", CosmeticSlot.HELMET, "minecraft:netherite/emerald");
    public static final Cosmetic NETHERITE_EMERALD_CHESTPLATE = new Cosmetic(
            "netherite/emerald_chestplate", "Netherite Emerald Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/emerald");
    public static final Cosmetic NETHERITE_EMERALD_LEGGINGS = new Cosmetic(
            "netherite/emerald_leggings", "Netherite Emerald Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/emerald");
    public static final Cosmetic NETHERITE_EMERALD_BOOTS = new Cosmetic(
            "netherite/emerald_boots", "Netherite Emerald Boots", CosmeticSlot.BOOTS, "minecraft:netherite/emerald");

    public static final Cosmetic NETHERITE_ENDER_DEFENDER_HELMET = new Cosmetic(
            "netherite/ender_defender_helmet", "Ender Defender Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ender_defender");
    public static final Cosmetic NETHERITE_ENDER_DEFENDER_CHESTPLATE = new Cosmetic(
            "netherite/ender_defender_chestplate", "Ender Defender Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ender_defender");
    public static final Cosmetic NETHERITE_ENDER_DEFENDER_LEGGINGS = new Cosmetic(
            "netherite/ender_defender_leggings", "Ender Defender Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ender_defender");
    public static final Cosmetic NETHERITE_ENDER_DEFENDER_BOOTS = new Cosmetic(
            "netherite/ender_defender_boots", "Ender Defender Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ender_defender");

    public static final Cosmetic NETHERITE_ENDER_DRAGON_HELMET = new Cosmetic(
            "netherite/ender_dragon_helmet", "Ender Dragon Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ender_dragon");
    public static final Cosmetic NETHERITE_ENDER_DRAGON_CHESTPLATE = new Cosmetic(
            "netherite/ender_dragon_chestplate", "Ender Dragon Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ender_dragon");
    public static final Cosmetic NETHERITE_ENDER_DRAGON_LEGGINGS = new Cosmetic(
            "netherite/ender_dragon_leggings", "Ender Dragon Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ender_dragon");
    public static final Cosmetic NETHERITE_ENDER_DRAGON_BOOTS = new Cosmetic(
            "netherite/ender_dragon_boots", "Ender Dragon Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ender_dragon");

    public static final Cosmetic NETHERITE_ENDER_HELMET = new Cosmetic(
            "netherite/ender_helmet", "Netherite Ender Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ender");
    public static final Cosmetic NETHERITE_ENDER_CHESTPLATE = new Cosmetic(
            "netherite/ender_chestplate", "Netherite Ender Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ender");
    public static final Cosmetic NETHERITE_ENDER_LEGGINGS = new Cosmetic(
            "netherite/ender_leggings", "Netherite Ender Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ender");
    public static final Cosmetic NETHERITE_ENDER_BOOTS = new Cosmetic(
            "netherite/ender_boots", "Netherite Ender Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ender");

    public static final Cosmetic NETHERITE_ENTERTAINER_HELMET = new Cosmetic(
            "netherite/entertainer_helmet", "Entertainer Helmet", CosmeticSlot.HELMET, "minecraft:netherite/entertainer");
    public static final Cosmetic NETHERITE_ENTERTAINER_CHESTPLATE = new Cosmetic(
            "netherite/entertainer_chestplate", "Entertainer Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/entertainer");
    public static final Cosmetic NETHERITE_ENTERTAINER_LEGGINGS = new Cosmetic(
            "netherite/entertainer_leggings", "Entertainer Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/entertainer");
    public static final Cosmetic NETHERITE_ENTERTAINER_BOOTS = new Cosmetic(
            "netherite/entertainer_boots", "Entertainer Boots", CosmeticSlot.BOOTS, "minecraft:netherite/entertainer");

    public static final Cosmetic NETHERITE_EVOCATION_HELMET = new Cosmetic(
            "netherite/evocation_helmet", "Evocation Helmet", CosmeticSlot.HELMET, "minecraft:netherite/evocation");
    public static final Cosmetic NETHERITE_EVOCATION_CHESTPLATE = new Cosmetic(
            "netherite/evocation_chestplate", "Evocation Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/evocation");
    public static final Cosmetic NETHERITE_EVOCATION_LEGGINGS = new Cosmetic(
            "netherite/evocation_leggings", "Evocation Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/evocation");
    public static final Cosmetic NETHERITE_EVOCATION_BOOTS = new Cosmetic(
            "netherite/evocation_boots", "Evocation Boots", CosmeticSlot.BOOTS, "minecraft:netherite/evocation");

    public static final Cosmetic NETHERITE_FIREFORGED_HELMET = new Cosmetic(
            "netherite/fireforged_helmet", "Fireforged Helmet", CosmeticSlot.HELMET, "minecraft:netherite/fireforged");
    public static final Cosmetic NETHERITE_FIREFORGED_CHESTPLATE = new Cosmetic(
            "netherite/fireforged_chestplate", "Fireforged Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/fireforged");
    public static final Cosmetic NETHERITE_FIREFORGED_LEGGINGS = new Cosmetic(
            "netherite/fireforged_leggings", "Fireforged Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/fireforged");
    public static final Cosmetic NETHERITE_FIREFORGED_BOOTS = new Cosmetic(
            "netherite/fireforged_boots", "Fireforged Boots", CosmeticSlot.BOOTS, "minecraft:netherite/fireforged");

    public static final Cosmetic NETHERITE_FOX_HELMET = new Cosmetic(
            "netherite/fox_helmet", "Netherite Fox Helmet", CosmeticSlot.HELMET, "minecraft:netherite/fox");
    public static final Cosmetic NETHERITE_FOX_CHESTPLATE = new Cosmetic(
            "netherite/fox_chestplate", "Netherite Fox Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/fox");
    public static final Cosmetic NETHERITE_FOX_LEGGINGS = new Cosmetic(
            "netherite/fox_leggings", "Netherite Fox Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/fox");
    public static final Cosmetic NETHERITE_FOX_BOOTS = new Cosmetic(
            "netherite/fox_boots", "Netherite Fox Boots", CosmeticSlot.BOOTS, "minecraft:netherite/fox");

    public static final Cosmetic NETHERITE_FRED_HELMET = new Cosmetic(
            "netherite/fred_helmet", "Fred Helmet", CosmeticSlot.HELMET, "minecraft:netherite/fred");
    public static final Cosmetic NETHERITE_FRED_CHESTPLATE = new Cosmetic(
            "netherite/fred_chestplate", "Fred Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/fred");
    public static final Cosmetic NETHERITE_FRED_LEGGINGS = new Cosmetic(
            "netherite/fred_leggings", "Fred Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/fred");
    public static final Cosmetic NETHERITE_FRED_BOOTS = new Cosmetic(
            "netherite/fred_boots", "Fred Boots", CosmeticSlot.BOOTS, "minecraft:netherite/fred");

    public static final Cosmetic NETHERITE_FROST_HELMET = new Cosmetic(
            "netherite/frost_helmet", "Frost Helmet", CosmeticSlot.HELMET, "minecraft:netherite/frost");
    public static final Cosmetic NETHERITE_FROST_CHESTPLATE = new Cosmetic(
            "netherite/frost_chestplate", "Frost Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/frost");
    public static final Cosmetic NETHERITE_FROST_LEGGINGS = new Cosmetic(
            "netherite/frost_leggings", "Frost Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/frost");
    public static final Cosmetic NETHERITE_FROST_BOOTS = new Cosmetic(
            "netherite/frost_boots", "Frost Boots", CosmeticSlot.BOOTS, "minecraft:netherite/frost");

    public static final Cosmetic NETHERITE_FULL_METAL_HELMET = new Cosmetic(
            "netherite/full_metal_helmet", "Netherite Full Metal Helmet", CosmeticSlot.HELMET, "minecraft:netherite/full_metal");
    public static final Cosmetic NETHERITE_FULL_METAL_CHESTPLATE = new Cosmetic(
            "netherite/full_metal_chestplate", "Netherite Full Metal Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/full_metal");
    public static final Cosmetic NETHERITE_FULL_METAL_LEGGINGS = new Cosmetic(
            "netherite/full_metal_leggings", "Netherite Full Metal Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/full_metal");
    public static final Cosmetic NETHERITE_FULL_METAL_BOOTS = new Cosmetic(
            "netherite/full_metal_boots", "Netherite Full Metal Boots", CosmeticSlot.BOOTS, "minecraft:netherite/full_metal");

    public static final Cosmetic NETHERITE_GABRIEL_HELMET = new Cosmetic(
            "netherite/gabriel_helmet", "Gabriel Helmet", CosmeticSlot.HELMET, "minecraft:netherite/gabriel");
    public static final Cosmetic NETHERITE_GABRIEL_CHESTPLATE = new Cosmetic(
            "netherite/gabriel_chestplate", "Gabriel Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/gabriel");
    public static final Cosmetic NETHERITE_GABRIEL_LEGGINGS = new Cosmetic(
            "netherite/gabriel_leggings", "Gabriel Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/gabriel");
    public static final Cosmetic NETHERITE_GABRIEL_BOOTS = new Cosmetic(
            "netherite/gabriel_boots", "Gabriel Boots", CosmeticSlot.BOOTS, "minecraft:netherite/gabriel");

    public static final Cosmetic NETHERITE_GHOSTLY_HELMET = new Cosmetic(
            "netherite/ghostly_helmet", "Netherite Ghostly Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ghostly");
    public static final Cosmetic NETHERITE_GHOSTLY_CHESTPLATE = new Cosmetic(
            "netherite/ghostly_chestplate", "Netherite Ghostly Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ghostly");
    public static final Cosmetic NETHERITE_GHOSTLY_LEGGINGS = new Cosmetic(
            "netherite/ghostly_leggings", "Netherite Ghostly Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ghostly");
    public static final Cosmetic NETHERITE_GHOSTLY_BOOTS = new Cosmetic(
            "netherite/ghostly_boots", "Netherite Ghostly Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ghostly");

    public static final Cosmetic NETHERITE_GLOW_SQUID_HELMET = new Cosmetic(
            "netherite/glow_squid_helmet", "Glow Squid Helmet", CosmeticSlot.HELMET, "minecraft:netherite/glow_squid");
    public static final Cosmetic NETHERITE_GLOW_SQUID_CHESTPLATE = new Cosmetic(
            "netherite/glow_squid_chestplate", "Glow Squid Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/glow_squid");
    public static final Cosmetic NETHERITE_GLOW_SQUID_LEGGINGS = new Cosmetic(
            "netherite/glow_squid_leggings", "Glow Squid Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/glow_squid");
    public static final Cosmetic NETHERITE_GLOW_SQUID_BOOTS = new Cosmetic(
            "netherite/glow_squid_boots", "Glow Squid Boots", CosmeticSlot.BOOTS, "minecraft:netherite/glow_squid");

    public static final Cosmetic NETHERITE_GOAT_HELMET = new Cosmetic(
            "netherite/goat_helmet", "Goat Helmet", CosmeticSlot.HELMET, "minecraft:netherite/goat");
    public static final Cosmetic NETHERITE_GOAT_CHESTPLATE = new Cosmetic(
            "netherite/goat_chestplate", "Goat Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/goat");
    public static final Cosmetic NETHERITE_GOAT_LEGGINGS = new Cosmetic(
            "netherite/goat_leggings", "Goat Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/goat");
    public static final Cosmetic NETHERITE_GOAT_BOOTS = new Cosmetic(
            "netherite/goat_boots", "Goat Boots", CosmeticSlot.BOOTS, "minecraft:netherite/goat");

    public static final Cosmetic NETHERITE_GOLDEN_APPLE_HELMET = new Cosmetic(
            "netherite/golden_apple_helmet", "Golden Apple Helmet", CosmeticSlot.HELMET, "minecraft:netherite/golden_apple");
    public static final Cosmetic NETHERITE_GOLDEN_APPLE_CHESTPLATE = new Cosmetic(
            "netherite/golden_apple_chestplate", "Golden Apple Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/golden_apple");
    public static final Cosmetic NETHERITE_GOLDEN_APPLE_LEGGINGS = new Cosmetic(
            "netherite/golden_apple_leggings", "Golden Apple Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/golden_apple");
    public static final Cosmetic NETHERITE_GOLDEN_APPLE_BOOTS = new Cosmetic(
            "netherite/golden_apple_boots", "Golden Apple Boots", CosmeticSlot.BOOTS, "minecraft:netherite/golden_apple");

    public static final Cosmetic NETHERITE_GOLDEN_GOLIATH_HELMET = new Cosmetic(
            "netherite/golden_goliath_helmet", "Golden Goliath Helmet", CosmeticSlot.HELMET, "minecraft:netherite/golden_goliath");
    public static final Cosmetic NETHERITE_GOLDEN_GOLIATH_CHESTPLATE = new Cosmetic(
            "netherite/golden_goliath_chestplate", "Golden Goliath Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/golden_goliath");
    public static final Cosmetic NETHERITE_GOLDEN_GOLIATH_LEGGINGS = new Cosmetic(
            "netherite/golden_goliath_leggings", "Golden Goliath Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/golden_goliath");
    public static final Cosmetic NETHERITE_GOLDEN_GOLIATH_BOOTS = new Cosmetic(
            "netherite/golden_goliath_boots", "Golden Goliath Boots", CosmeticSlot.BOOTS, "minecraft:netherite/golden_goliath");

    public static final Cosmetic NETHERITE_GRIM_HELMET = new Cosmetic(
            "netherite/grim_helmet", "Netherite Grim Helmet", CosmeticSlot.HELMET, "minecraft:netherite/grim");
    public static final Cosmetic NETHERITE_GRIM_CHESTPLATE = new Cosmetic(
            "netherite/grim_chestplate", "Netherite Grim Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/grim");
    public static final Cosmetic NETHERITE_GRIM_LEGGINGS = new Cosmetic(
            "netherite/grim_leggings", "Netherite Grim Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/grim");
    public static final Cosmetic NETHERITE_GRIM_BOOTS = new Cosmetic(
            "netherite/grim_boots", "Netherite Grim Boots", CosmeticSlot.BOOTS, "minecraft:netherite/grim");

    public static final Cosmetic NETHERITE_GUARD_HELMET = new Cosmetic(
            "netherite/guard_helmet", "Netherite Guard Helmet", CosmeticSlot.HELMET, "minecraft:netherite/guard");
    public static final Cosmetic NETHERITE_GUARD_CHESTPLATE = new Cosmetic(
            "netherite/guard_chestplate", "Netherite Guard Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/guard");
    public static final Cosmetic NETHERITE_GUARD_LEGGINGS = new Cosmetic(
            "netherite/guard_leggings", "Netherite Guard Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/guard");
    public static final Cosmetic NETHERITE_GUARD_BOOTS = new Cosmetic(
            "netherite/guard_boots", "Netherite Guard Boots", CosmeticSlot.BOOTS, "minecraft:netherite/guard");

    public static final Cosmetic NETHERITE_HEAVY_HELMET = new Cosmetic(
            "netherite/heavy_helmet", "Heavy Helmet", CosmeticSlot.HELMET, "minecraft:netherite/heavy");
    public static final Cosmetic NETHERITE_HEAVY_CHESTPLATE = new Cosmetic(
            "netherite/heavy_chestplate", "Heavy Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/heavy");
    public static final Cosmetic NETHERITE_HEAVY_LEGGINGS = new Cosmetic(
            "netherite/heavy_leggings", "Heavy Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/heavy");
    public static final Cosmetic NETHERITE_HEAVY_BOOTS = new Cosmetic(
            "netherite/heavy_boots", "Heavy Boots", CosmeticSlot.BOOTS, "minecraft:netherite/heavy");

    public static final Cosmetic NETHERITE_HERO_HELMET = new Cosmetic(
            "netherite/hero_helmet", "Hero Helmet", CosmeticSlot.HELMET, "minecraft:netherite/hero");
    public static final Cosmetic NETHERITE_HERO_CHESTPLATE = new Cosmetic(
            "netherite/hero_chestplate", "Hero Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/hero");
    public static final Cosmetic NETHERITE_HERO_LEGGINGS = new Cosmetic(
            "netherite/hero_leggings", "Hero Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/hero");
    public static final Cosmetic NETHERITE_HERO_BOOTS = new Cosmetic(
            "netherite/hero_boots", "Hero Boots", CosmeticSlot.BOOTS, "minecraft:netherite/hero");

    public static final Cosmetic NETHERITE_HIGHLAND_HELMET = new Cosmetic(
            "netherite/highland_helmet", "Netherite Highland Helmet", CosmeticSlot.HELMET, "minecraft:netherite/highland");
    public static final Cosmetic NETHERITE_HIGHLAND_CHESTPLATE = new Cosmetic(
            "netherite/highland_chestplate", "Netherite Highland Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/highland");
    public static final Cosmetic NETHERITE_HIGHLAND_LEGGINGS = new Cosmetic(
            "netherite/highland_leggings", "Netherite Highland Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/highland");
    public static final Cosmetic NETHERITE_HIGHLAND_BOOTS = new Cosmetic(
            "netherite/highland_boots", "Netherite Highland Boots", CosmeticSlot.BOOTS, "minecraft:netherite/highland");

    public static final Cosmetic NETHERITE_HUNGRIEST_HORROR_HELMET = new Cosmetic(
            "netherite/hungriest_horror_helmet", "Hungriest Horror Helmet", CosmeticSlot.HELMET, "minecraft:netherite/hungriest_horror");
    public static final Cosmetic NETHERITE_HUNGRIEST_HORROR_CHESTPLATE = new Cosmetic(
            "netherite/hungriest_horror_chestplate", "Hungriest Horror Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/hungriest_horror");
    public static final Cosmetic NETHERITE_HUNGRIEST_HORROR_LEGGINGS = new Cosmetic(
            "netherite/hungriest_horror_leggings", "Hungriest Horror Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/hungriest_horror");
    public static final Cosmetic NETHERITE_HUNGRIEST_HORROR_BOOTS = new Cosmetic(
            "netherite/hungriest_horror_boots", "Hungriest Horror Boots", CosmeticSlot.BOOTS, "minecraft:netherite/hungriest_horror");

    public static final Cosmetic NETHERITE_HUNGRY_HORROR_HELMET = new Cosmetic(
            "netherite/hungry_horror_helmet", "Hungry Horror Helmet", CosmeticSlot.HELMET, "minecraft:netherite/hungry_horror");
    public static final Cosmetic NETHERITE_HUNGRY_HORROR_CHESTPLATE = new Cosmetic(
            "netherite/hungry_horror_chestplate", "Hungry Horror Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/hungry_horror");
    public static final Cosmetic NETHERITE_HUNGRY_HORROR_LEGGINGS = new Cosmetic(
            "netherite/hungry_horror_leggings", "Hungry Horror Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/hungry_horror");
    public static final Cosmetic NETHERITE_HUNGRY_HORROR_BOOTS = new Cosmetic(
            "netherite/hungry_horror_boots", "Hungry Horror Boots", CosmeticSlot.BOOTS, "minecraft:netherite/hungry_horror");

    public static final Cosmetic NETHERITE_ILL_HELMET = new Cosmetic(
            "netherite/ill_helmet", "Ill Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ill");
    public static final Cosmetic NETHERITE_ILL_CHESTPLATE = new Cosmetic(
            "netherite/ill_chestplate", "Ill Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ill");
    public static final Cosmetic NETHERITE_ILL_LEGGINGS = new Cosmetic(
            "netherite/ill_leggings", "Ill Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ill");
    public static final Cosmetic NETHERITE_ILL_BOOTS = new Cosmetic(
            "netherite/ill_boots", "Ill Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ill");

    public static final Cosmetic NETHERITE_IVOR_HELMET = new Cosmetic(
            "netherite/ivor_helmet", "Ivor Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ivor");
    public static final Cosmetic NETHERITE_IVOR_CHESTPLATE = new Cosmetic(
            "netherite/ivor_chestplate", "Ivor Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ivor");
    public static final Cosmetic NETHERITE_IVOR_LEGGINGS = new Cosmetic(
            "netherite/ivor_leggings", "Ivor Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ivor");
    public static final Cosmetic NETHERITE_IVOR_BOOTS = new Cosmetic(
            "netherite/ivor_boots", "Ivor Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ivor");

    public static final Cosmetic NETHERITE_JACK_O_HELMET = new Cosmetic(
            "netherite/jack_o_helmet", "Jack O Helmet", CosmeticSlot.HELMET, "minecraft:netherite/jack_o");
    public static final Cosmetic NETHERITE_JACK_O_CHESTPLATE = new Cosmetic(
            "netherite/jack_o_chestplate", "Jack O Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/jack_o");
    public static final Cosmetic NETHERITE_JACK_O_LEGGINGS = new Cosmetic(
            "netherite/jack_o_leggings", "Jack O Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/jack_o");
    public static final Cosmetic NETHERITE_JACK_O_BOOTS = new Cosmetic(
            "netherite/jack_o_boots", "Jack O Boots", CosmeticSlot.BOOTS, "minecraft:netherite/jack_o");

    public static final Cosmetic NETHERITE_JOURNEY_HELMET = new Cosmetic(
            "netherite/journey_helmet", "Journey Helmet", CosmeticSlot.HELMET, "minecraft:netherite/journey");
    public static final Cosmetic NETHERITE_JOURNEY_CHESTPLATE = new Cosmetic(
            "netherite/journey_chestplate", "Journey Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/journey");
    public static final Cosmetic NETHERITE_JOURNEY_LEGGINGS = new Cosmetic(
            "netherite/journey_leggings", "Journey Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/journey");
    public static final Cosmetic NETHERITE_JOURNEY_BOOTS = new Cosmetic(
            "netherite/journey_boots", "Journey Boots", CosmeticSlot.BOOTS, "minecraft:netherite/journey");

    public static final Cosmetic NETHERITE_LAVA_INFUSED_HELMET = new Cosmetic(
            "netherite/lava_infused_helmet", "Netherite Lava Infused Helmet", CosmeticSlot.HELMET, "minecraft:netherite/lava_infused");
    public static final Cosmetic NETHERITE_LAVA_INFUSED_CHESTPLATE = new Cosmetic(
            "netherite/lava_infused_chestplate", "Netherite Lava Infused Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/lava_infused");
    public static final Cosmetic NETHERITE_LAVA_INFUSED_LEGGINGS = new Cosmetic(
            "netherite/lava_infused_leggings", "Netherite Lava Infused Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/lava_infused");
    public static final Cosmetic NETHERITE_LAVA_INFUSED_BOOTS = new Cosmetic(
            "netherite/lava_infused_boots", "Netherite Lava Infused Boots", CosmeticSlot.BOOTS, "minecraft:netherite/lava_infused");

    public static final Cosmetic NETHERITE_LAVA_WARRIOR_HELMET = new Cosmetic(
            "netherite/lava_warrior_helmet", "Lava Warrior Helmet", CosmeticSlot.HELMET, "minecraft:netherite/lava_warrior");
    public static final Cosmetic NETHERITE_LAVA_WARRIOR_CHESTPLATE = new Cosmetic(
            "netherite/lava_warrior_chestplate", "Lava Warrior Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/lava_warrior");
    public static final Cosmetic NETHERITE_LAVA_WARRIOR_LEGGINGS = new Cosmetic(
            "netherite/lava_warrior_leggings", "Lava Warrior Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/lava_warrior");
    public static final Cosmetic NETHERITE_LAVA_WARRIOR_BOOTS = new Cosmetic(
            "netherite/lava_warrior_boots", "Lava Warrior Boots", CosmeticSlot.BOOTS, "minecraft:netherite/lava_warrior");

    public static final Cosmetic NETHERITE_LIVING_VINES_HELMET = new Cosmetic(
            "netherite/living_vines_helmet", "Living Vines Helmet", CosmeticSlot.HELMET, "minecraft:netherite/living_vines");
    public static final Cosmetic NETHERITE_LIVING_VINES_CHESTPLATE = new Cosmetic(
            "netherite/living_vines_chestplate", "Living Vines Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/living_vines");
    public static final Cosmetic NETHERITE_LIVING_VINES_LEGGINGS = new Cosmetic(
            "netherite/living_vines_leggings", "Living Vines Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/living_vines");
    public static final Cosmetic NETHERITE_LIVING_VINES_BOOTS = new Cosmetic(
            "netherite/living_vines_boots", "Living Vines Boots", CosmeticSlot.BOOTS, "minecraft:netherite/living_vines");

    public static final Cosmetic NETHERITE_MAGNUS_HELMET = new Cosmetic(
            "netherite/magnus_helmet", "Magnus Helmet", CosmeticSlot.HELMET, "minecraft:netherite/magnus");
    public static final Cosmetic NETHERITE_MAGNUS_CHESTPLATE = new Cosmetic(
            "netherite/magnus_chestplate", "Magnus Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/magnus");
    public static final Cosmetic NETHERITE_MAGNUS_LEGGINGS = new Cosmetic(
            "netherite/magnus_leggings", "Magnus Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/magnus");
    public static final Cosmetic NETHERITE_MAGNUS_BOOTS = new Cosmetic(
            "netherite/magnus_boots", "Magnus Boots", CosmeticSlot.BOOTS, "minecraft:netherite/magnus");

    public static final Cosmetic NETHERITE_MCDONALDS_HELMET = new Cosmetic(
            "netherite/mcdonalds_helmet", "Mcdonalds Helmet", CosmeticSlot.HELMET, "minecraft:netherite/mcdonalds");
    public static final Cosmetic NETHERITE_MCDONALDS_CHESTPLATE = new Cosmetic(
            "netherite/mcdonalds_chestplate", "Mcdonalds Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/mcdonalds");
    public static final Cosmetic NETHERITE_MCDONALDS_LEGGINGS = new Cosmetic(
            "netherite/mcdonalds_leggings", "Mcdonalds Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/mcdonalds");
    public static final Cosmetic NETHERITE_MCDONALDS_BOOTS = new Cosmetic(
            "netherite/mcdonalds_boots", "Mcdonalds Boots", CosmeticSlot.BOOTS, "minecraft:netherite/mcdonalds");

    public static final Cosmetic NETHERITE_MEGA_FIRE_PROTECTION_HELMET = new Cosmetic(
            "netherite/mega_fire_protection_helmet", "Mega Fire Protection Helmet", CosmeticSlot.HELMET, "minecraft:netherite/mega_fire_protection");
    public static final Cosmetic NETHERITE_MEGA_FIRE_PROTECTION_CHESTPLATE = new Cosmetic(
            "netherite/mega_fire_protection_chestplate", "Mega Fire Protection Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/mega_fire_protection");
    public static final Cosmetic NETHERITE_MEGA_FIRE_PROTECTION_LEGGINGS = new Cosmetic(
            "netherite/mega_fire_protection_leggings", "Mega Fire Protection Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/mega_fire_protection");
    public static final Cosmetic NETHERITE_MEGA_FIRE_PROTECTION_BOOTS = new Cosmetic(
            "netherite/mega_fire_protection_boots", "Mega Fire Protection Boots", CosmeticSlot.BOOTS, "minecraft:netherite/mega_fire_protection");

    public static final Cosmetic NETHERITE_MEGA_X_FIRE_PROTECTION_HELMET = new Cosmetic(
            "netherite/mega_x_fire_protection_helmet", "Mega X Fire Protection Helmet", CosmeticSlot.HELMET, "minecraft:netherite/mega_x_fire_protection");
    public static final Cosmetic NETHERITE_MEGA_X_FIRE_PROTECTION_CHESTPLATE = new Cosmetic(
            "netherite/mega_x_fire_protection_chestplate", "Mega X Fire Protection Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/mega_x_fire_protection");
    public static final Cosmetic NETHERITE_MEGA_X_FIRE_PROTECTION_LEGGINGS = new Cosmetic(
            "netherite/mega_x_fire_protection_leggings", "Mega X Fire Protection Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/mega_x_fire_protection");
    public static final Cosmetic NETHERITE_MEGA_X_FIRE_PROTECTION_BOOTS = new Cosmetic(
            "netherite/mega_x_fire_protection_boots", "Mega X Fire Protection Boots", CosmeticSlot.BOOTS, "minecraft:netherite/mega_x_fire_protection");

    public static final Cosmetic NETHERITE_MEGA_X_PROTECTION_HELMET = new Cosmetic(
            "netherite/mega_x_protection_helmet", "Mega X Protection Helmet", CosmeticSlot.HELMET, "minecraft:netherite/mega_x_protection");
    public static final Cosmetic NETHERITE_MEGA_X_PROTECTION_CHESTPLATE = new Cosmetic(
            "netherite/mega_x_protection_chestplate", "Mega X Protection Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/mega_x_protection");
    public static final Cosmetic NETHERITE_MEGA_X_PROTECTION_LEGGINGS = new Cosmetic(
            "netherite/mega_x_protection_leggings", "Mega X Protection Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/mega_x_protection");
    public static final Cosmetic NETHERITE_MEGA_X_PROTECTION_BOOTS = new Cosmetic(
            "netherite/mega_x_protection_boots", "Mega X Protection Boots", CosmeticSlot.BOOTS, "minecraft:netherite/mega_x_protection");

    public static final Cosmetic NETHERITE_MEGA_X_HELMET = new Cosmetic(
            "netherite/mega_x_helmet", "Mega X Helmet", CosmeticSlot.HELMET, "minecraft:netherite/mega_x");
    public static final Cosmetic NETHERITE_MEGA_X_CHESTPLATE = new Cosmetic(
            "netherite/mega_x_chestplate", "Mega X Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/mega_x");
    public static final Cosmetic NETHERITE_MEGA_X_LEGGINGS = new Cosmetic(
            "netherite/mega_x_leggings", "Mega X Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/mega_x");
    public static final Cosmetic NETHERITE_MEGA_X_BOOTS = new Cosmetic(
            "netherite/mega_x_boots", "Mega X Boots", CosmeticSlot.BOOTS, "minecraft:netherite/mega_x");

    public static final Cosmetic NETHERITE_MEGA_HELMET = new Cosmetic(
            "netherite/mega_helmet", "Mega Helmet", CosmeticSlot.HELMET, "minecraft:netherite/mega");
    public static final Cosmetic NETHERITE_MEGA_CHESTPLATE = new Cosmetic(
            "netherite/mega_chestplate", "Mega Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/mega");
    public static final Cosmetic NETHERITE_MEGA_LEGGINGS = new Cosmetic(
            "netherite/mega_leggings", "Mega Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/mega");
    public static final Cosmetic NETHERITE_MEGA_BOOTS = new Cosmetic(
            "netherite/mega_boots", "Mega Boots", CosmeticSlot.BOOTS, "minecraft:netherite/mega");

    public static final Cosmetic NETHERITE_MYSTERY_HELMET = new Cosmetic(
            "netherite/mystery_helmet", "Netherite Mystery Helmet", CosmeticSlot.HELMET, "minecraft:netherite/mystery");
    public static final Cosmetic NETHERITE_MYSTERY_CHESTPLATE = new Cosmetic(
            "netherite/mystery_chestplate", "Netherite Mystery Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/mystery");
    public static final Cosmetic NETHERITE_MYSTERY_LEGGINGS = new Cosmetic(
            "netherite/mystery_leggings", "Netherite Mystery Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/mystery");
    public static final Cosmetic NETHERITE_MYSTERY_BOOTS = new Cosmetic(
            "netherite/mystery_boots", "Netherite Mystery Boots", CosmeticSlot.BOOTS, "minecraft:netherite/mystery");

    public static final Cosmetic NETHERITE_NAMELESS_HELMET = new Cosmetic(
            "netherite/nameless_helmet", "Netherite Nameless Helmet", CosmeticSlot.HELMET, "minecraft:netherite/nameless");
    public static final Cosmetic NETHERITE_NAMELESS_CHESTPLATE = new Cosmetic(
            "netherite/nameless_chestplate", "Netherite Nameless Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/nameless");
    public static final Cosmetic NETHERITE_NAMELESS_LEGGINGS = new Cosmetic(
            "netherite/nameless_leggings", "Netherite Nameless Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/nameless");
    public static final Cosmetic NETHERITE_NAMELESS_BOOTS = new Cosmetic(
            "netherite/nameless_boots", "Netherite Nameless Boots", CosmeticSlot.BOOTS, "minecraft:netherite/nameless");

    public static final Cosmetic NETHERITE_NECROMANCER_HELMET = new Cosmetic(
            "netherite/necromancer_helmet", "Netherite Necromancer Helmet", CosmeticSlot.HELMET, "minecraft:netherite/necromancer");
    public static final Cosmetic NETHERITE_NECROMANCER_CHESTPLATE = new Cosmetic(
            "netherite/necromancer_chestplate", "Netherite Necromancer Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/necromancer");
    public static final Cosmetic NETHERITE_NECROMANCER_LEGGINGS = new Cosmetic(
            "netherite/necromancer_leggings", "Netherite Necromancer Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/necromancer");
    public static final Cosmetic NETHERITE_NECROMANCER_BOOTS = new Cosmetic(
            "netherite/necromancer_boots", "Netherite Necromancer Boots", CosmeticSlot.BOOTS, "minecraft:netherite/necromancer");

    public static final Cosmetic NETHERITE_NETHERWALKER_HELMET = new Cosmetic(
            "netherite/netherwalker_helmet", "Netherite Netherwalker Helmet", CosmeticSlot.HELMET, "minecraft:netherite/netherwalker");
    public static final Cosmetic NETHERITE_NETHERWALKER_CHESTPLATE = new Cosmetic(
            "netherite/netherwalker_chestplate", "Netherite Netherwalker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/netherwalker");
    public static final Cosmetic NETHERITE_NETHERWALKER_LEGGINGS = new Cosmetic(
            "netherite/netherwalker_leggings", "Netherite Netherwalker Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/netherwalker");
    public static final Cosmetic NETHERITE_NETHERWALKER_BOOTS = new Cosmetic(
            "netherite/netherwalker_boots", "Netherite Netherwalker Boots", CosmeticSlot.BOOTS, "minecraft:netherite/netherwalker");

    public static final Cosmetic NETHERITE_NIMBLE_TURTLE_HELMET = new Cosmetic(
            "netherite/nimble_turtle_helmet", "Nimble Turtle Helmet", CosmeticSlot.HELMET, "minecraft:netherite/nimble_turtle");
    public static final Cosmetic NETHERITE_NIMBLE_TURTLE_CHESTPLATE = new Cosmetic(
            "netherite/nimble_turtle_chestplate", "Nimble Turtle Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/nimble_turtle");
    public static final Cosmetic NETHERITE_NIMBLE_TURTLE_LEGGINGS = new Cosmetic(
            "netherite/nimble_turtle_leggings", "Nimble Turtle Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/nimble_turtle");
    public static final Cosmetic NETHERITE_NIMBLE_TURTLE_BOOTS = new Cosmetic(
            "netherite/nimble_turtle_boots", "Nimble Turtle Boots", CosmeticSlot.BOOTS, "minecraft:netherite/nimble_turtle");

    public static final Cosmetic NETHERITE_NINJA_HELMET = new Cosmetic(
            "netherite/ninja_helmet", "Ninja Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ninja");
    public static final Cosmetic NETHERITE_NINJA_CHESTPLATE = new Cosmetic(
            "netherite/ninja_chestplate", "Ninja Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ninja");
    public static final Cosmetic NETHERITE_NINJA_LEGGINGS = new Cosmetic(
            "netherite/ninja_leggings", "Ninja Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ninja");
    public static final Cosmetic NETHERITE_NINJA_BOOTS = new Cosmetic(
            "netherite/ninja_boots", "Ninja Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ninja");

    public static final Cosmetic NETHERITE_OCELOT_HELMET = new Cosmetic(
            "netherite/ocelot_helmet", "Netherite Ocelot Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ocelot");
    public static final Cosmetic NETHERITE_OCELOT_CHESTPLATE = new Cosmetic(
            "netherite/ocelot_chestplate", "Netherite Ocelot Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ocelot");
    public static final Cosmetic NETHERITE_OCELOT_LEGGINGS = new Cosmetic(
            "netherite/ocelot_leggings", "Netherite Ocelot Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ocelot");
    public static final Cosmetic NETHERITE_OCELOT_BOOTS = new Cosmetic(
            "netherite/ocelot_boots", "Netherite Ocelot Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ocelot");

    public static final Cosmetic NETHERITE_OPULENT_HELMET = new Cosmetic(
            "netherite/opulent_helmet", "Netherite Opulent Helmet", CosmeticSlot.HELMET, "minecraft:netherite/opulent");
    public static final Cosmetic NETHERITE_OPULENT_CHESTPLATE = new Cosmetic(
            "netherite/opulent_chestplate", "Netherite Opulent Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/opulent");
    public static final Cosmetic NETHERITE_OPULENT_LEGGINGS = new Cosmetic(
            "netherite/opulent_leggings", "Netherite Opulent Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/opulent");
    public static final Cosmetic NETHERITE_OPULENT_BOOTS = new Cosmetic(
            "netherite/opulent_boots", "Netherite Opulent Boots", CosmeticSlot.BOOTS, "minecraft:netherite/opulent");

    public static final Cosmetic NETHERITE_ORE_HELMET = new Cosmetic(
            "netherite/ore_helmet", "Netherite Ore Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ore");
    public static final Cosmetic NETHERITE_ORE_CHESTPLATE = new Cosmetic(
            "netherite/ore_chestplate", "Netherite Ore Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ore");
    public static final Cosmetic NETHERITE_ORE_LEGGINGS = new Cosmetic(
            "netherite/ore_leggings", "Netherite Ore Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ore");
    public static final Cosmetic NETHERITE_ORE_BOOTS = new Cosmetic(
            "netherite/ore_boots", "Netherite Ore Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ore");

    public static final Cosmetic NETHERITE_PAMA_HELMET = new Cosmetic(
            "netherite/pama_helmet", "Netherite Pama Helmet", CosmeticSlot.HELMET, "minecraft:netherite/pama");
    public static final Cosmetic NETHERITE_PAMA_CHESTPLATE = new Cosmetic(
            "netherite/pama_chestplate", "Netherite Pama Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/pama");
    public static final Cosmetic NETHERITE_PAMA_LEGGINGS = new Cosmetic(
            "netherite/pama_leggings", "Netherite Pama Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/pama");
    public static final Cosmetic NETHERITE_PAMA_BOOTS = new Cosmetic(
            "netherite/pama_boots", "Netherite Pama Boots", CosmeticSlot.BOOTS, "minecraft:netherite/pama");

    public static final Cosmetic NETHERITE_PETRA_HELMET = new Cosmetic(
            "netherite/petra_helmet", "Petra Helmet", CosmeticSlot.HELMET, "minecraft:netherite/petra");
    public static final Cosmetic NETHERITE_PETRA_CHESTPLATE = new Cosmetic(
            "netherite/petra_chestplate", "Petra Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/petra");
    public static final Cosmetic NETHERITE_PETRA_LEGGINGS = new Cosmetic(
            "netherite/petra_leggings", "Petra Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/petra");
    public static final Cosmetic NETHERITE_PETRA_BOOTS = new Cosmetic(
            "netherite/petra_boots", "Petra Boots", CosmeticSlot.BOOTS, "minecraft:netherite/petra");

    public static final Cosmetic NETHERITE_PHANTOM_RANGER_HELMET = new Cosmetic(
            "netherite/phantom_ranger_helmet", "Phantom Ranger Helmet", CosmeticSlot.HELMET, "minecraft:netherite/phantom_ranger");
    public static final Cosmetic NETHERITE_PHANTOM_RANGER_CHESTPLATE = new Cosmetic(
            "netherite/phantom_ranger_chestplate", "Phantom Ranger Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/phantom_ranger");
    public static final Cosmetic NETHERITE_PHANTOM_RANGER_LEGGINGS = new Cosmetic(
            "netherite/phantom_ranger_leggings", "Phantom Ranger Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/phantom_ranger");
    public static final Cosmetic NETHERITE_PHANTOM_RANGER_BOOTS = new Cosmetic(
            "netherite/phantom_ranger_boots", "Phantom Ranger Boots", CosmeticSlot.BOOTS, "minecraft:netherite/phantom_ranger");

    public static final Cosmetic NETHERITE_PHANTOM_HELMET = new Cosmetic(
            "netherite/phantom_helmet", "Netherite Phantom Helmet", CosmeticSlot.HELMET, "minecraft:netherite/phantom");
    public static final Cosmetic NETHERITE_PHANTOM_CHESTPLATE = new Cosmetic(
            "netherite/phantom_chestplate", "Netherite Phantom Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/phantom");
    public static final Cosmetic NETHERITE_PHANTOM_LEGGINGS = new Cosmetic(
            "netherite/phantom_leggings", "Netherite Phantom Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/phantom");
    public static final Cosmetic NETHERITE_PHANTOM_BOOTS = new Cosmetic(
            "netherite/phantom_boots", "Netherite Phantom Boots", CosmeticSlot.BOOTS, "minecraft:netherite/phantom");

    public static final Cosmetic NETHERITE_PIGLIN_HELMET = new Cosmetic(
            "netherite/piglin_helmet", "Netherite Piglin Helmet", CosmeticSlot.HELMET, "minecraft:netherite/piglin");
    public static final Cosmetic NETHERITE_PIGLIN_CHESTPLATE = new Cosmetic(
            "netherite/piglin_chestplate", "Netherite Piglin Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/piglin");
    public static final Cosmetic NETHERITE_PIGLIN_LEGGINGS = new Cosmetic(
            "netherite/piglin_leggings", "Netherite Piglin Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/piglin");
    public static final Cosmetic NETHERITE_PIGLIN_BOOTS = new Cosmetic(
            "netherite/piglin_boots", "Netherite Piglin Boots", CosmeticSlot.BOOTS, "minecraft:netherite/piglin");

    public static final Cosmetic NETHERITE_PLATE_HELMET = new Cosmetic(
            "netherite/plate_helmet", "Netherite Plate Helmet", CosmeticSlot.HELMET, "minecraft:netherite/plate");
    public static final Cosmetic NETHERITE_PLATE_CHESTPLATE = new Cosmetic(
            "netherite/plate_chestplate", "Netherite Plate Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/plate");
    public static final Cosmetic NETHERITE_PLATE_LEGGINGS = new Cosmetic(
            "netherite/plate_leggings", "Netherite Plate Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/plate");
    public static final Cosmetic NETHERITE_PLATE_BOOTS = new Cosmetic(
            "netherite/plate_boots", "Netherite Plate Boots", CosmeticSlot.BOOTS, "minecraft:netherite/plate");

    public static final Cosmetic NETHERITE_POISONOUS_POTATO_HELMET = new Cosmetic(
            "netherite/poisonous_potato_helmet", "Poisonous Potato Helmet", CosmeticSlot.HELMET, "minecraft:netherite/poisonous_potato");
    public static final Cosmetic NETHERITE_POISONOUS_POTATO_CHESTPLATE = new Cosmetic(
            "netherite/poisonous_potato_chestplate", "Poisonous Potato Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/poisonous_potato");
    public static final Cosmetic NETHERITE_POISONOUS_POTATO_LEGGINGS = new Cosmetic(
            "netherite/poisonous_potato_leggings", "Poisonous Potato Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/poisonous_potato");
    public static final Cosmetic NETHERITE_POISONOUS_POTATO_BOOTS = new Cosmetic(
            "netherite/poisonous_potato_boots", "Poisonous Potato Boots", CosmeticSlot.BOOTS, "minecraft:netherite/poisonous_potato");

    public static final Cosmetic NETHERITE_PORTAL_BUSTER_HELMET = new Cosmetic(
            "netherite/portal_buster_helmet", "Portal Buster Helmet", CosmeticSlot.HELMET, "minecraft:netherite/portal_buster");
    public static final Cosmetic NETHERITE_PORTAL_BUSTER_CHESTPLATE = new Cosmetic(
            "netherite/portal_buster_chestplate", "Portal Buster Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/portal_buster");
    public static final Cosmetic NETHERITE_PORTAL_BUSTER_LEGGINGS = new Cosmetic(
            "netherite/portal_buster_leggings", "Portal Buster Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/portal_buster");
    public static final Cosmetic NETHERITE_PORTAL_BUSTER_BOOTS = new Cosmetic(
            "netherite/portal_buster_boots", "Portal Buster Boots", CosmeticSlot.BOOTS, "minecraft:netherite/portal_buster");

    public static final Cosmetic NETHERITE_PRISMARINE_HELMET = new Cosmetic(
            "netherite/prismarine_helmet", "Prismarine Helmet", CosmeticSlot.HELMET, "minecraft:netherite/prismarine");
    public static final Cosmetic NETHERITE_PRISMARINE_CHESTPLATE = new Cosmetic(
            "netherite/prismarine_chestplate", "Prismarine Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/prismarine");
    public static final Cosmetic NETHERITE_PRISMARINE_LEGGINGS = new Cosmetic(
            "netherite/prismarine_leggings", "Prismarine Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/prismarine");
    public static final Cosmetic NETHERITE_PRISMARINE_BOOTS = new Cosmetic(
            "netherite/prismarine_boots", "Prismarine Boots", CosmeticSlot.BOOTS, "minecraft:netherite/prismarine");

    public static final Cosmetic NETHERITE_PROUD_HELMET = new Cosmetic(
            "netherite/proud_helmet", "Netherite Proud Helmet", CosmeticSlot.HELMET, "minecraft:netherite/proud");
    public static final Cosmetic NETHERITE_PROUD_CHESTPLATE = new Cosmetic(
            "netherite/proud_chestplate", "Netherite Proud Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/proud");
    public static final Cosmetic NETHERITE_PROUD_LEGGINGS = new Cosmetic(
            "netherite/proud_leggings", "Netherite Proud Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/proud");
    public static final Cosmetic NETHERITE_PROUD_BOOTS = new Cosmetic(
            "netherite/proud_boots", "Netherite Proud Boots", CosmeticSlot.BOOTS, "minecraft:netherite/proud");

    public static final Cosmetic NETHERITE_RED_RASCAL_HELMET = new Cosmetic(
            "netherite/red_rascal_helmet", "Red Rascal Helmet", CosmeticSlot.HELMET, "minecraft:netherite/red_rascal");
    public static final Cosmetic NETHERITE_RED_RASCAL_CHESTPLATE = new Cosmetic(
            "netherite/red_rascal_chestplate", "Red Rascal Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/red_rascal");
    public static final Cosmetic NETHERITE_RED_RASCAL_LEGGINGS = new Cosmetic(
            "netherite/red_rascal_leggings", "Red Rascal Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/red_rascal");
    public static final Cosmetic NETHERITE_RED_RASCAL_BOOTS = new Cosmetic(
            "netherite/red_rascal_boots", "Red Rascal Boots", CosmeticSlot.BOOTS, "minecraft:netherite/red_rascal");

    public static final Cosmetic NETHERITE_REDSTONE_RIOT_HELMET = new Cosmetic(
            "netherite/redstone_riot_helmet", "Redstone Riot Helmet", CosmeticSlot.HELMET, "minecraft:netherite/redstone_riot");
    public static final Cosmetic NETHERITE_REDSTONE_RIOT_CHESTPLATE = new Cosmetic(
            "netherite/redstone_riot_chestplate", "Redstone Riot Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/redstone_riot");
    public static final Cosmetic NETHERITE_REDSTONE_RIOT_LEGGINGS = new Cosmetic(
            "netherite/redstone_riot_leggings", "Redstone Riot Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/redstone_riot");
    public static final Cosmetic NETHERITE_REDSTONE_RIOT_BOOTS = new Cosmetic(
            "netherite/redstone_riot_boots", "Redstone Riot Boots", CosmeticSlot.BOOTS, "minecraft:netherite/redstone_riot");

    public static final Cosmetic NETHERITE_REDSTONIA_HELMET = new Cosmetic(
            "netherite/redstonia_helmet", "Redstonia Helmet", CosmeticSlot.HELMET, "minecraft:netherite/redstonia");
    public static final Cosmetic NETHERITE_REDSTONIA_CHESTPLATE = new Cosmetic(
            "netherite/redstonia_chestplate", "Redstonia Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/redstonia");
    public static final Cosmetic NETHERITE_REDSTONIA_LEGGINGS = new Cosmetic(
            "netherite/redstonia_leggings", "Redstonia Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/redstonia");
    public static final Cosmetic NETHERITE_REDSTONIA_BOOTS = new Cosmetic(
            "netherite/redstonia_boots", "Redstonia Boots", CosmeticSlot.BOOTS, "minecraft:netherite/redstonia");

    public static final Cosmetic NETHERITE_RENEGADE_HELMET = new Cosmetic(
            "netherite/renegade_helmet", "Netherite Renegade Helmet", CosmeticSlot.HELMET, "minecraft:netherite/renegade");
    public static final Cosmetic NETHERITE_RENEGADE_CHESTPLATE = new Cosmetic(
            "netherite/renegade_chestplate", "Netherite Renegade Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/renegade");
    public static final Cosmetic NETHERITE_RENEGADE_LEGGINGS = new Cosmetic(
            "netherite/renegade_leggings", "Netherite Renegade Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/renegade");
    public static final Cosmetic NETHERITE_RENEGADE_BOOTS = new Cosmetic(
            "netherite/renegade_boots", "Netherite Renegade Boots", CosmeticSlot.BOOTS, "minecraft:netherite/renegade");

    public static final Cosmetic NETHERITE_RGB_RANGER_HELMET = new Cosmetic(
            "netherite/rgb_ranger_helmet", "Rgb Ranger Helmet", CosmeticSlot.HELMET, "minecraft:netherite/rgb_ranger");
    public static final Cosmetic NETHERITE_RGB_RANGER_CHESTPLATE = new Cosmetic(
            "netherite/rgb_ranger_chestplate", "Rgb Ranger Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/rgb_ranger");
    public static final Cosmetic NETHERITE_RGB_RANGER_LEGGINGS = new Cosmetic(
            "netherite/rgb_ranger_leggings", "Rgb Ranger Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/rgb_ranger");
    public static final Cosmetic NETHERITE_RGB_RANGER_BOOTS = new Cosmetic(
            "netherite/rgb_ranger_boots", "Rgb Ranger Boots", CosmeticSlot.BOOTS, "minecraft:netherite/rgb_ranger");

    public static final Cosmetic NETHERITE_ROBOT_HELMET = new Cosmetic(
            "netherite/robot_helmet", "Robot Helmet", CosmeticSlot.HELMET, "minecraft:netherite/robot");
    public static final Cosmetic NETHERITE_ROBOT_CHESTPLATE = new Cosmetic(
            "netherite/robot_chestplate", "Robot Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/robot");
    public static final Cosmetic NETHERITE_ROBOT_LEGGINGS = new Cosmetic(
            "netherite/robot_leggings", "Robot Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/robot");
    public static final Cosmetic NETHERITE_ROBOT_BOOTS = new Cosmetic(
            "netherite/robot_boots", "Robot Boots", CosmeticSlot.BOOTS, "minecraft:netherite/robot");

    public static final Cosmetic NETHERITE_ROMEO_HELMET = new Cosmetic(
            "netherite/romeo_helmet", "Romeo Helmet", CosmeticSlot.HELMET, "minecraft:netherite/romeo");
    public static final Cosmetic NETHERITE_ROMEO_CHESTPLATE = new Cosmetic(
            "netherite/romeo_chestplate", "Romeo Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/romeo");
    public static final Cosmetic NETHERITE_ROMEO_LEGGINGS = new Cosmetic(
            "netherite/romeo_leggings", "Romeo Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/romeo");
    public static final Cosmetic NETHERITE_ROMEO_BOOTS = new Cosmetic(
            "netherite/romeo_boots", "Romeo Boots", CosmeticSlot.BOOTS, "minecraft:netherite/romeo");

    public static final Cosmetic NETHERITE_ROOT_ROT_HELMET = new Cosmetic(
            "netherite/root_rot_helmet", "Root Rot Helmet", CosmeticSlot.HELMET, "minecraft:netherite/root_rot");
    public static final Cosmetic NETHERITE_ROOT_ROT_CHESTPLATE = new Cosmetic(
            "netherite/root_rot_chestplate", "Root Rot Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/root_rot");
    public static final Cosmetic NETHERITE_ROOT_ROT_LEGGINGS = new Cosmetic(
            "netherite/root_rot_leggings", "Root Rot Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/root_rot");
    public static final Cosmetic NETHERITE_ROOT_ROT_BOOTS = new Cosmetic(
            "netherite/root_rot_boots", "Root Rot Boots", CosmeticSlot.BOOTS, "minecraft:netherite/root_rot");

    public static final Cosmetic NETHERITE_ROYAL_HELMET = new Cosmetic(
            "netherite/royal_helmet", "Netherite Royal Helmet", CosmeticSlot.HELMET, "minecraft:netherite/royal");
    public static final Cosmetic NETHERITE_ROYAL_CHESTPLATE = new Cosmetic(
            "netherite/royal_chestplate", "Netherite Royal Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/royal");
    public static final Cosmetic NETHERITE_ROYAL_LEGGINGS = new Cosmetic(
            "netherite/royal_leggings", "Netherite Royal Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/royal");
    public static final Cosmetic NETHERITE_ROYAL_BOOTS = new Cosmetic(
            "netherite/royal_boots", "Netherite Royal Boots", CosmeticSlot.BOOTS, "minecraft:netherite/royal");

    public static final Cosmetic NETHERITE_RUBY_HELMET = new Cosmetic(
            "netherite/ruby_helmet", "Netherite Ruby Helmet", CosmeticSlot.HELMET, "minecraft:netherite/ruby");
    public static final Cosmetic NETHERITE_RUBY_CHESTPLATE = new Cosmetic(
            "netherite/ruby_chestplate", "Netherite Ruby Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/ruby");
    public static final Cosmetic NETHERITE_RUBY_LEGGINGS = new Cosmetic(
            "netherite/ruby_leggings", "Netherite Ruby Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/ruby");
    public static final Cosmetic NETHERITE_RUBY_BOOTS = new Cosmetic(
            "netherite/ruby_boots", "Netherite Ruby Boots", CosmeticSlot.BOOTS, "minecraft:netherite/ruby");

    public static final Cosmetic NETHERITE_RUGGED_CLIMBING_GEAR_HELMET = new Cosmetic(
            "netherite/rugged_climbing_gear_helmet", "Rugged Climbing Gear Helmet", CosmeticSlot.HELMET, "minecraft:netherite/rugged_climbing_gear");
    public static final Cosmetic NETHERITE_RUGGED_CLIMBING_GEAR_CHESTPLATE = new Cosmetic(
            "netherite/rugged_climbing_gear_chestplate", "Rugged Climbing Gear Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/rugged_climbing_gear");
    public static final Cosmetic NETHERITE_RUGGED_CLIMBING_GEAR_LEGGINGS = new Cosmetic(
            "netherite/rugged_climbing_gear_leggings", "Rugged Climbing Gear Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/rugged_climbing_gear");
    public static final Cosmetic NETHERITE_RUGGED_CLIMBING_GEAR_BOOTS = new Cosmetic(
            "netherite/rugged_climbing_gear_boots", "Rugged Climbing Gear Boots", CosmeticSlot.BOOTS, "minecraft:netherite/rugged_climbing_gear");

    public static final Cosmetic NETHERITE_SAKURA_HELMET = new Cosmetic(
            "netherite/sakura_helmet", "Sakura Helmet", CosmeticSlot.HELMET, "minecraft:netherite/sakura");
    public static final Cosmetic NETHERITE_SAKURA_CHESTPLATE = new Cosmetic(
            "netherite/sakura_chestplate", "Sakura Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/sakura");
    public static final Cosmetic NETHERITE_SAKURA_LEGGINGS = new Cosmetic(
            "netherite/sakura_leggings", "Sakura Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/sakura");
    public static final Cosmetic NETHERITE_SAKURA_BOOTS = new Cosmetic(
            "netherite/sakura_boots", "Sakura Boots", CosmeticSlot.BOOTS, "minecraft:netherite/sakura");

    public static final Cosmetic NETHERITE_SCARECROW_HELMET = new Cosmetic(
            "netherite/scarecrow_helmet", "Scarecrow Helmet", CosmeticSlot.HELMET, "minecraft:netherite/scarecrow");
    public static final Cosmetic NETHERITE_SCARECROW_CHESTPLATE = new Cosmetic(
            "netherite/scarecrow_chestplate", "Scarecrow Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/scarecrow");
    public static final Cosmetic NETHERITE_SCARECROW_LEGGINGS = new Cosmetic(
            "netherite/scarecrow_leggings", "Scarecrow Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/scarecrow");
    public static final Cosmetic NETHERITE_SCARECROW_BOOTS = new Cosmetic(
            "netherite/scarecrow_boots", "Scarecrow Boots", CosmeticSlot.BOOTS, "minecraft:netherite/scarecrow");

    public static final Cosmetic NETHERITE_SCARLET_HELMET = new Cosmetic(
            "netherite/scarlet_helmet", "Scarlet Helmet", CosmeticSlot.HELMET, "minecraft:netherite/scarlet");
    public static final Cosmetic NETHERITE_SCARLET_CHESTPLATE = new Cosmetic(
            "netherite/scarlet_chestplate", "Scarlet Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/scarlet");
    public static final Cosmetic NETHERITE_SCARLET_LEGGINGS = new Cosmetic(
            "netherite/scarlet_leggings", "Scarlet Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/scarlet");
    public static final Cosmetic NETHERITE_SCARLET_BOOTS = new Cosmetic(
            "netherite/scarlet_boots", "Scarlet Boots", CosmeticSlot.BOOTS, "minecraft:netherite/scarlet");

    public static final Cosmetic NETHERITE_SHADOW_WALKER_HELMET = new Cosmetic(
            "netherite/shadow_walker_helmet", "Shadow Walker Helmet", CosmeticSlot.HELMET, "minecraft:netherite/shadow_walker");
    public static final Cosmetic NETHERITE_SHADOW_WALKER_CHESTPLATE = new Cosmetic(
            "netherite/shadow_walker_chestplate", "Shadow Walker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/shadow_walker");
    public static final Cosmetic NETHERITE_SHADOW_WALKER_LEGGINGS = new Cosmetic(
            "netherite/shadow_walker_leggings", "Shadow Walker Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/shadow_walker");
    public static final Cosmetic NETHERITE_SHADOW_WALKER_BOOTS = new Cosmetic(
            "netherite/shadow_walker_boots", "Shadow Walker Boots", CosmeticSlot.BOOTS, "minecraft:netherite/shadow_walker");

    public static final Cosmetic NETHERITE_SHAMAN_HELMET = new Cosmetic(
            "netherite/shaman_helmet", "Shaman Helmet", CosmeticSlot.HELMET, "minecraft:netherite/shaman");
    public static final Cosmetic NETHERITE_SHAMAN_CHESTPLATE = new Cosmetic(
            "netherite/shaman_chestplate", "Shaman Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/shaman");
    public static final Cosmetic NETHERITE_SHAMAN_LEGGINGS = new Cosmetic(
            "netherite/shaman_leggings", "Shaman Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/shaman");
    public static final Cosmetic NETHERITE_SHAMAN_BOOTS = new Cosmetic(
            "netherite/shaman_boots", "Shaman Boots", CosmeticSlot.BOOTS, "minecraft:netherite/shaman");

    public static final Cosmetic NETHERITE_SHIELD_OF_INFINITY_HELMET = new Cosmetic(
            "netherite/shield_of_infinity_helmet", "Shield Of Infinity Helmet", CosmeticSlot.HELMET, "minecraft:netherite/shield_of_infinity");
    public static final Cosmetic NETHERITE_SHIELD_OF_INFINITY_CHESTPLATE = new Cosmetic(
            "netherite/shield_of_infinity_chestplate", "Shield Of Infinity Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/shield_of_infinity");
    public static final Cosmetic NETHERITE_SHIELD_OF_INFINITY_LEGGINGS = new Cosmetic(
            "netherite/shield_of_infinity_leggings", "Shield Of Infinity Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/shield_of_infinity");
    public static final Cosmetic NETHERITE_SHIELD_OF_INFINITY_BOOTS = new Cosmetic(
            "netherite/shield_of_infinity_boots", "Shield Of Infinity Boots", CosmeticSlot.BOOTS, "minecraft:netherite/shield_of_infinity");

    public static final Cosmetic NETHERITE_SHOGUN_HELMET = new Cosmetic(
            "netherite/shogun_helmet", "Netherite Shogun Helmet", CosmeticSlot.HELMET, "minecraft:netherite/shogun");
    public static final Cosmetic NETHERITE_SHOGUN_CHESTPLATE = new Cosmetic(
            "netherite/shogun_chestplate", "Netherite Shogun Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/shogun");
    public static final Cosmetic NETHERITE_SHOGUN_LEGGINGS = new Cosmetic(
            "netherite/shogun_leggings", "Netherite Shogun Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/shogun");
    public static final Cosmetic NETHERITE_SHOGUN_BOOTS = new Cosmetic(
            "netherite/shogun_boots", "Netherite Shogun Boots", CosmeticSlot.BOOTS, "minecraft:netherite/shogun");

    public static final Cosmetic NETHERITE_SHULKER_HELMET = new Cosmetic(
            "netherite/shulker_helmet", "Shulker Helmet", CosmeticSlot.HELMET, "minecraft:netherite/shulker");
    public static final Cosmetic NETHERITE_SHULKER_CHESTPLATE = new Cosmetic(
            "netherite/shulker_chestplate", "Shulker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/shulker");
    public static final Cosmetic NETHERITE_SHULKER_LEGGINGS = new Cosmetic(
            "netherite/shulker_leggings", "Shulker Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/shulker");
    public static final Cosmetic NETHERITE_SHULKER_BOOTS = new Cosmetic(
            "netherite/shulker_boots", "Shulker Boots", CosmeticSlot.BOOTS, "minecraft:netherite/shulker");

    public static final Cosmetic NETHERITE_SKELLY_HELMET = new Cosmetic(
            "netherite/skelly_helmet", "Skelly Helmet", CosmeticSlot.HELMET, "minecraft:netherite/skelly");
    public static final Cosmetic NETHERITE_SKELLY_CHESTPLATE = new Cosmetic(
            "netherite/skelly_chestplate", "Skelly Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/skelly");
    public static final Cosmetic NETHERITE_SKELLY_LEGGINGS = new Cosmetic(
            "netherite/skelly_leggings", "Skelly Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/skelly");
    public static final Cosmetic NETHERITE_SKELLY_BOOTS = new Cosmetic(
            "netherite/skelly_boots", "Skelly Boots", CosmeticSlot.BOOTS, "minecraft:netherite/skelly");

    public static final Cosmetic NETHERITE_SNOW_HELMET = new Cosmetic(
            "netherite/snow_helmet", "Snow Helmet", CosmeticSlot.HELMET, "minecraft:netherite/snow");
    public static final Cosmetic NETHERITE_SNOW_CHESTPLATE = new Cosmetic(
            "netherite/snow_chestplate", "Snow Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/snow");
    public static final Cosmetic NETHERITE_SNOW_LEGGINGS = new Cosmetic(
            "netherite/snow_leggings", "Snow Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/snow");
    public static final Cosmetic NETHERITE_SNOW_BOOTS = new Cosmetic(
            "netherite/snow_boots", "Snow Boots", CosmeticSlot.BOOTS, "minecraft:netherite/snow");

    public static final Cosmetic NETHERITE_SOREN_HELMET = new Cosmetic(
            "netherite/soren_helmet", "Soren Helmet", CosmeticSlot.HELMET, "minecraft:netherite/soren");
    public static final Cosmetic NETHERITE_SOREN_CHESTPLATE = new Cosmetic(
            "netherite/soren_chestplate", "Soren Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/soren");
    public static final Cosmetic NETHERITE_SOREN_LEGGINGS = new Cosmetic(
            "netherite/soren_leggings", "Soren Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/soren");
    public static final Cosmetic NETHERITE_SOREN_BOOTS = new Cosmetic(
            "netherite/soren_boots", "Soren Boots", CosmeticSlot.BOOTS, "minecraft:netherite/soren");

    public static final Cosmetic NETHERITE_SOUL_HELMET = new Cosmetic(
            "netherite/soul_helmet", "Soul Helmet", CosmeticSlot.HELMET, "minecraft:netherite/soul");
    public static final Cosmetic NETHERITE_SOUL_CHESTPLATE = new Cosmetic(
            "netherite/soul_chestplate", "Soul Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/soul");
    public static final Cosmetic NETHERITE_SOUL_LEGGINGS = new Cosmetic(
            "netherite/soul_leggings", "Soul Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/soul");
    public static final Cosmetic NETHERITE_SOUL_BOOTS = new Cosmetic(
            "netherite/soul_boots", "Soul Boots", CosmeticSlot.BOOTS, "minecraft:netherite/soul");

    public static final Cosmetic NETHERITE_SOULDANCER_HELMET = new Cosmetic(
            "netherite/souldancer_helmet", "Souldancer Helmet", CosmeticSlot.HELMET, "minecraft:netherite/souldancer");
    public static final Cosmetic NETHERITE_SOULDANCER_CHESTPLATE = new Cosmetic(
            "netherite/souldancer_chestplate", "Souldancer Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/souldancer");
    public static final Cosmetic NETHERITE_SOULDANCER_LEGGINGS = new Cosmetic(
            "netherite/souldancer_leggings", "Souldancer Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/souldancer");
    public static final Cosmetic NETHERITE_SOULDANCER_BOOTS = new Cosmetic(
            "netherite/souldancer_boots", "Souldancer Boots", CosmeticSlot.BOOTS, "minecraft:netherite/souldancer");

    public static final Cosmetic NETHERITE_SPACE_HELMET = new Cosmetic(
            "netherite/space_helmet", "Space Helmet", CosmeticSlot.HELMET, "minecraft:netherite/space");
    public static final Cosmetic NETHERITE_SPACE_CHESTPLATE = new Cosmetic(
            "netherite/space_chestplate", "Space Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/space");
    public static final Cosmetic NETHERITE_SPACE_LEGGINGS = new Cosmetic(
            "netherite/space_leggings", "Space Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/space");
    public static final Cosmetic NETHERITE_SPACE_BOOTS = new Cosmetic(
            "netherite/space_boots", "Space Boots", CosmeticSlot.BOOTS, "minecraft:netherite/space");

    public static final Cosmetic NETHERITE_SPELUNKER_HELMET = new Cosmetic(
            "netherite/spelunker_helmet", "Netherite Spelunker Helmet", CosmeticSlot.HELMET, "minecraft:netherite/spelunker");
    public static final Cosmetic NETHERITE_SPELUNKER_CHESTPLATE = new Cosmetic(
            "netherite/spelunker_chestplate", "Netherite Spelunker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/spelunker");
    public static final Cosmetic NETHERITE_SPELUNKER_LEGGINGS = new Cosmetic(
            "netherite/spelunker_leggings", "Netherite Spelunker Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/spelunker");
    public static final Cosmetic NETHERITE_SPELUNKER_BOOTS = new Cosmetic(
            "netherite/spelunker_boots", "Netherite Spelunker Boots", CosmeticSlot.BOOTS, "minecraft:netherite/spelunker");

    public static final Cosmetic NETHERITE_SPIDER_HELMET = new Cosmetic(
            "netherite/spider_helmet", "Spider Helmet", CosmeticSlot.HELMET, "minecraft:netherite/spider");
    public static final Cosmetic NETHERITE_SPIDER_CHESTPLATE = new Cosmetic(
            "netherite/spider_chestplate", "Spider Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/spider");
    public static final Cosmetic NETHERITE_SPIDER_LEGGINGS = new Cosmetic(
            "netherite/spider_leggings", "Spider Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/spider");
    public static final Cosmetic NETHERITE_SPIDER_BOOTS = new Cosmetic(
            "netherite/spider_boots", "Spider Boots", CosmeticSlot.BOOTS, "minecraft:netherite/spider");

    public static final Cosmetic NETHERITE_SPLEEF_HELMET = new Cosmetic(
            "netherite/spleef_helmet", "Netherite Spleef Helmet", CosmeticSlot.HELMET, "minecraft:netherite/spleef");
    public static final Cosmetic NETHERITE_SPLEEF_CHESTPLATE = new Cosmetic(
            "netherite/spleef_chestplate", "Netherite Spleef Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/spleef");
    public static final Cosmetic NETHERITE_SPLEEF_LEGGINGS = new Cosmetic(
            "netherite/spleef_leggings", "Netherite Spleef Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/spleef");
    public static final Cosmetic NETHERITE_SPLEEF_BOOTS = new Cosmetic(
            "netherite/spleef_boots", "Netherite Spleef Boots", CosmeticSlot.BOOTS, "minecraft:netherite/spleef");

    public static final Cosmetic NETHERITE_SPLENDID_HELMET = new Cosmetic(
            "netherite/splendid_helmet", "Splendid Helmet", CosmeticSlot.HELMET, "minecraft:netherite/splendid");
    public static final Cosmetic NETHERITE_SPLENDID_CHESTPLATE = new Cosmetic(
            "netherite/splendid_chestplate", "Splendid Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/splendid");
    public static final Cosmetic NETHERITE_SPLENDID_LEGGINGS = new Cosmetic(
            "netherite/splendid_leggings", "Splendid Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/splendid");
    public static final Cosmetic NETHERITE_SPLENDID_BOOTS = new Cosmetic(
            "netherite/splendid_boots", "Splendid Boots", CosmeticSlot.BOOTS, "minecraft:netherite/splendid");

    public static final Cosmetic NETHERITE_SPOOKY_GOURDIAN_HELMET = new Cosmetic(
            "netherite/spooky_gourdian_helmet", "Spooky Gourdian Helmet", CosmeticSlot.HELMET, "minecraft:netherite/spooky_gourdian");
    public static final Cosmetic NETHERITE_SPOOKY_GOURDIAN_CHESTPLATE = new Cosmetic(
            "netherite/spooky_gourdian_chestplate", "Spooky Gourdian Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/spooky_gourdian");
    public static final Cosmetic NETHERITE_SPOOKY_GOURDIAN_LEGGINGS = new Cosmetic(
            "netherite/spooky_gourdian_leggings", "Spooky Gourdian Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/spooky_gourdian");
    public static final Cosmetic NETHERITE_SPOOKY_GOURDIAN_BOOTS = new Cosmetic(
            "netherite/spooky_gourdian_boots", "Spooky Gourdian Boots", CosmeticSlot.BOOTS, "minecraft:netherite/spooky_gourdian");

    public static final Cosmetic NETHERITE_SPOOKY_HELMET = new Cosmetic(
            "netherite/spooky_helmet", "Spooky Helmet", CosmeticSlot.HELMET, "minecraft:netherite/spooky");
    public static final Cosmetic NETHERITE_SPOOKY_CHESTPLATE = new Cosmetic(
            "netherite/spooky_chestplate", "Spooky Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/spooky");
    public static final Cosmetic NETHERITE_SPOOKY_LEGGINGS = new Cosmetic(
            "netherite/spooky_leggings", "Spooky Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/spooky");
    public static final Cosmetic NETHERITE_SPOOKY_BOOTS = new Cosmetic(
            "netherite/spooky_boots", "Spooky Boots", CosmeticSlot.BOOTS, "minecraft:netherite/spooky");

    public static final Cosmetic NETHERITE_SPROUT_HELMET = new Cosmetic(
            "netherite/sprout_helmet", "Sprout Helmet", CosmeticSlot.HELMET, "minecraft:netherite/sprout");
    public static final Cosmetic NETHERITE_SPROUT_CHESTPLATE = new Cosmetic(
            "netherite/sprout_chestplate", "Sprout Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/sprout");
    public static final Cosmetic NETHERITE_SPROUT_LEGGINGS = new Cosmetic(
            "netherite/sprout_leggings", "Sprout Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/sprout");
    public static final Cosmetic NETHERITE_SPROUT_BOOTS = new Cosmetic(
            "netherite/sprout_boots", "Sprout Boots", CosmeticSlot.BOOTS, "minecraft:netherite/sprout");

    public static final Cosmetic NETHERITE_SQUID_HELMET = new Cosmetic(
            "netherite/squid_helmet", "Squid Helmet", CosmeticSlot.HELMET, "minecraft:netherite/squid");
    public static final Cosmetic NETHERITE_SQUID_CHESTPLATE = new Cosmetic(
            "netherite/squid_chestplate", "Squid Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/squid");
    public static final Cosmetic NETHERITE_SQUID_LEGGINGS = new Cosmetic(
            "netherite/squid_leggings", "Squid Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/squid");
    public static final Cosmetic NETHERITE_SQUID_BOOTS = new Cosmetic(
            "netherite/squid_boots", "Squid Boots", CosmeticSlot.BOOTS, "minecraft:netherite/squid");

    public static final Cosmetic NETHERITE_STALWART_HELMET = new Cosmetic(
            "netherite/stalwart_helmet", "Netherite Stalwart Helmet", CosmeticSlot.HELMET, "minecraft:netherite/stalwart");
    public static final Cosmetic NETHERITE_STALWART_CHESTPLATE = new Cosmetic(
            "netherite/stalwart_chestplate", "Netherite Stalwart Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/stalwart");
    public static final Cosmetic NETHERITE_STALWART_LEGGINGS = new Cosmetic(
            "netherite/stalwart_leggings", "Netherite Stalwart Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/stalwart");
    public static final Cosmetic NETHERITE_STALWART_BOOTS = new Cosmetic(
            "netherite/stalwart_boots", "Netherite Stalwart Boots", CosmeticSlot.BOOTS, "minecraft:netherite/stalwart");

    public static final Cosmetic NETHERITE_STAR_SHIELD_HELMET = new Cosmetic(
            "netherite/star_shield_helmet", "Star Shield Helmet", CosmeticSlot.HELMET, "minecraft:netherite/star_shield");
    public static final Cosmetic NETHERITE_STAR_SHIELD_CHESTPLATE = new Cosmetic(
            "netherite/star_shield_chestplate", "Star Shield Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/star_shield");
    public static final Cosmetic NETHERITE_STAR_SHIELD_LEGGINGS = new Cosmetic(
            "netherite/star_shield_leggings", "Star Shield Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/star_shield");
    public static final Cosmetic NETHERITE_STAR_SHIELD_BOOTS = new Cosmetic(
            "netherite/star_shield_boots", "Star Shield Boots", CosmeticSlot.BOOTS, "minecraft:netherite/star_shield");

    public static final Cosmetic NETHERITE_STRIDER_HELMET = new Cosmetic(
            "netherite/strider_helmet", "Strider Helmet", CosmeticSlot.HELMET, "minecraft:netherite/strider");
    public static final Cosmetic NETHERITE_STRIDER_CHESTPLATE = new Cosmetic(
            "netherite/strider_chestplate", "Strider Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/strider");
    public static final Cosmetic NETHERITE_STRIDER_LEGGINGS = new Cosmetic(
            "netherite/strider_leggings", "Strider Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/strider");
    public static final Cosmetic NETHERITE_STRIDER_BOOTS = new Cosmetic(
            "netherite/strider_boots", "Strider Boots", CosmeticSlot.BOOTS, "minecraft:netherite/strider");

    public static final Cosmetic NETHERITE_STURDY_SHULKER_HELMET = new Cosmetic(
            "netherite/sturdy_shulker_helmet", "Sturdy Shulker Helmet", CosmeticSlot.HELMET, "minecraft:netherite/sturdy_shulker");
    public static final Cosmetic NETHERITE_STURDY_SHULKER_CHESTPLATE = new Cosmetic(
            "netherite/sturdy_shulker_chestplate", "Sturdy Shulker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/sturdy_shulker");
    public static final Cosmetic NETHERITE_STURDY_SHULKER_LEGGINGS = new Cosmetic(
            "netherite/sturdy_shulker_leggings", "Sturdy Shulker Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/sturdy_shulker");
    public static final Cosmetic NETHERITE_STURDY_SHULKER_BOOTS = new Cosmetic(
            "netherite/sturdy_shulker_boots", "Sturdy Shulker Boots", CosmeticSlot.BOOTS, "minecraft:netherite/sturdy_shulker");

    public static final Cosmetic NETHERITE_SWEET_TOOTH_HELMET = new Cosmetic(
            "netherite/sweet_tooth_helmet", "Sweet Tooth Helmet", CosmeticSlot.HELMET, "minecraft:netherite/sweet_tooth");
    public static final Cosmetic NETHERITE_SWEET_TOOTH_CHESTPLATE = new Cosmetic(
            "netherite/sweet_tooth_chestplate", "Sweet Tooth Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/sweet_tooth");
    public static final Cosmetic NETHERITE_SWEET_TOOTH_LEGGINGS = new Cosmetic(
            "netherite/sweet_tooth_leggings", "Sweet Tooth Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/sweet_tooth");
    public static final Cosmetic NETHERITE_SWEET_TOOTH_BOOTS = new Cosmetic(
            "netherite/sweet_tooth_boots", "Sweet Tooth Boots", CosmeticSlot.BOOTS, "minecraft:netherite/sweet_tooth");

    public static final Cosmetic NETHERITE_SWORDBREAKER_HELMET = new Cosmetic(
            "netherite/swordbreaker_helmet", "Swordbreaker Helmet", CosmeticSlot.HELMET, "minecraft:netherite/swordbreaker");
    public static final Cosmetic NETHERITE_SWORDBREAKER_CHESTPLATE = new Cosmetic(
            "netherite/swordbreaker_chestplate", "Swordbreaker Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/swordbreaker");
    public static final Cosmetic NETHERITE_SWORDBREAKER_LEGGINGS = new Cosmetic(
            "netherite/swordbreaker_leggings", "Swordbreaker Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/swordbreaker");
    public static final Cosmetic NETHERITE_SWORDBREAKER_BOOTS = new Cosmetic(
            "netherite/swordbreaker_boots", "Swordbreaker Boots", CosmeticSlot.BOOTS, "minecraft:netherite/swordbreaker");

    public static final Cosmetic NETHERITE_TELEPORTATION_HELMET = new Cosmetic(
            "netherite/teleportation_helmet", "Teleportation Helmet", CosmeticSlot.HELMET, "minecraft:netherite/teleportation");
    public static final Cosmetic NETHERITE_TELEPORTATION_CHESTPLATE = new Cosmetic(
            "netherite/teleportation_chestplate", "Teleportation Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/teleportation");
    public static final Cosmetic NETHERITE_TELEPORTATION_LEGGINGS = new Cosmetic(
            "netherite/teleportation_leggings", "Teleportation Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/teleportation");
    public static final Cosmetic NETHERITE_TELEPORTATION_BOOTS = new Cosmetic(
            "netherite/teleportation_boots", "Teleportation Boots", CosmeticSlot.BOOTS, "minecraft:netherite/teleportation");

    public static final Cosmetic NETHERITE_THIEF_HELMET = new Cosmetic(
            "netherite/thief_helmet", "Netherite Thief Helmet", CosmeticSlot.HELMET, "minecraft:netherite/thief");
    public static final Cosmetic NETHERITE_THIEF_CHESTPLATE = new Cosmetic(
            "netherite/thief_chestplate", "Netherite Thief Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/thief");
    public static final Cosmetic NETHERITE_THIEF_LEGGINGS = new Cosmetic(
            "netherite/thief_leggings", "Netherite Thief Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/thief");
    public static final Cosmetic NETHERITE_THIEF_BOOTS = new Cosmetic(
            "netherite/thief_boots", "Netherite Thief Boots", CosmeticSlot.BOOTS, "minecraft:netherite/thief");

    public static final Cosmetic NETHERITE_TIM_HELMET = new Cosmetic(
            "netherite/tim_helmet", "Tim Helmet", CosmeticSlot.HELMET, "minecraft:netherite/tim");
    public static final Cosmetic NETHERITE_TIM_CHESTPLATE = new Cosmetic(
            "netherite/tim_chestplate", "Tim Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/tim");
    public static final Cosmetic NETHERITE_TIM_LEGGINGS = new Cosmetic(
            "netherite/tim_leggings", "Tim Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/tim");
    public static final Cosmetic NETHERITE_TIM_BOOTS = new Cosmetic(
            "netherite/tim_boots", "Tim Boots", CosmeticSlot.BOOTS, "minecraft:netherite/tim");

    public static final Cosmetic NETHERITE_TITAN_SHROUD_HELMET = new Cosmetic(
            "netherite/titan_shroud_helmet", "Titan Shroud Helmet", CosmeticSlot.HELMET, "minecraft:netherite/titan_shroud");
    public static final Cosmetic NETHERITE_TITAN_SHROUD_CHESTPLATE = new Cosmetic(
            "netherite/titan_shroud_chestplate", "Titan Shroud Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/titan_shroud");
    public static final Cosmetic NETHERITE_TITAN_SHROUD_LEGGINGS = new Cosmetic(
            "netherite/titan_shroud_leggings", "Titan Shroud Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/titan_shroud");
    public static final Cosmetic NETHERITE_TITAN_SHROUD_BOOTS = new Cosmetic(
            "netherite/titan_shroud_boots", "Titan Shroud Boots", CosmeticSlot.BOOTS, "minecraft:netherite/titan_shroud");

    public static final Cosmetic NETHERITE_TOWER_HELMET = new Cosmetic(
            "netherite/tower_helmet", "Tower Helmet", CosmeticSlot.HELMET, "minecraft:netherite/tower");
    public static final Cosmetic NETHERITE_TOWER_CHESTPLATE = new Cosmetic(
            "netherite/tower_chestplate", "Tower Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/tower");
    public static final Cosmetic NETHERITE_TOWER_LEGGINGS = new Cosmetic(
            "netherite/tower_leggings", "Tower Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/tower");
    public static final Cosmetic NETHERITE_TOWER_BOOTS = new Cosmetic(
            "netherite/tower_boots", "Tower Boots", CosmeticSlot.BOOTS, "minecraft:netherite/tower");

    public static final Cosmetic NETHERITE_TRIAL_HELMET = new Cosmetic(
            "netherite/trial_helmet", "Netherite Trial Helmet", CosmeticSlot.HELMET, "minecraft:netherite/trial");
    public static final Cosmetic NETHERITE_TRIAL_CHESTPLATE = new Cosmetic(
            "netherite/trial_chestplate", "Netherite Trial Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/trial");
    public static final Cosmetic NETHERITE_TRIAL_LEGGINGS = new Cosmetic(
            "netherite/trial_leggings", "Netherite Trial Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/trial");
    public static final Cosmetic NETHERITE_TRIAL_BOOTS = new Cosmetic(
            "netherite/trial_boots", "Netherite Trial Boots", CosmeticSlot.BOOTS, "minecraft:netherite/trial");

    public static final Cosmetic NETHERITE_TROUBADOUR_HELMET = new Cosmetic(
            "netherite/troubadour_helmet", "Troubadour Helmet", CosmeticSlot.HELMET, "minecraft:netherite/troubadour");
    public static final Cosmetic NETHERITE_TROUBADOUR_CHESTPLATE = new Cosmetic(
            "netherite/troubadour_chestplate", "Troubadour Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/troubadour");
    public static final Cosmetic NETHERITE_TROUBADOUR_LEGGINGS = new Cosmetic(
            "netherite/troubadour_leggings", "Troubadour Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/troubadour");
    public static final Cosmetic NETHERITE_TROUBADOUR_BOOTS = new Cosmetic(
            "netherite/troubadour_boots", "Troubadour Boots", CosmeticSlot.BOOTS, "minecraft:netherite/troubadour");

    public static final Cosmetic NETHERITE_TURTLE_HELMET = new Cosmetic(
            "netherite/turtle_helmet", "Turtle Helmet", CosmeticSlot.HELMET, "minecraft:netherite/turtle");
    public static final Cosmetic NETHERITE_TURTLE_CHESTPLATE = new Cosmetic(
            "netherite/turtle_chestplate", "Turtle Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/turtle");
    public static final Cosmetic NETHERITE_TURTLE_LEGGINGS = new Cosmetic(
            "netherite/turtle_leggings", "Turtle Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/turtle");
    public static final Cosmetic NETHERITE_TURTLE_BOOTS = new Cosmetic(
            "netherite/turtle_boots", "Turtle Boots", CosmeticSlot.BOOTS, "minecraft:netherite/turtle");

    public static final Cosmetic NETHERITE_UNSTABLE_HELMET = new Cosmetic(
            "netherite/unstable_helmet", "Unstable Helmet", CosmeticSlot.HELMET, "minecraft:netherite/unstable");
    public static final Cosmetic NETHERITE_UNSTABLE_CHESTPLATE = new Cosmetic(
            "netherite/unstable_chestplate", "Unstable Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/unstable");
    public static final Cosmetic NETHERITE_UNSTABLE_LEGGINGS = new Cosmetic(
            "netherite/unstable_leggings", "Unstable Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/unstable");
    public static final Cosmetic NETHERITE_UNSTABLE_BOOTS = new Cosmetic(
            "netherite/unstable_boots", "Unstable Boots", CosmeticSlot.BOOTS, "minecraft:netherite/unstable");

    public static final Cosmetic NETHERITE_VEMI_HELMET = new Cosmetic(
            "netherite/vemi_helmet", "Netherite Vemi Helmet", CosmeticSlot.HELMET, "minecraft:netherite/vemi");
    public static final Cosmetic NETHERITE_VEMI_CHESTPLATE = new Cosmetic(
            "netherite/vemi_chestplate", "Netherite Vemi Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/vemi");
    public static final Cosmetic NETHERITE_VEMI_LEGGINGS = new Cosmetic(
            "netherite/vemi_leggings", "Netherite Vemi Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/vemi");
    public static final Cosmetic NETHERITE_VEMI_BOOTS = new Cosmetic(
            "netherite/vemi_boots", "Netherite Vemi Boots", CosmeticSlot.BOOTS, "minecraft:netherite/vemi");

    public static final Cosmetic NETHERITE_VERDANT_HELMET = new Cosmetic(
            "netherite/verdant_helmet", "Verdant Helmet", CosmeticSlot.HELMET, "minecraft:netherite/verdant");
    public static final Cosmetic NETHERITE_VERDANT_CHESTPLATE = new Cosmetic(
            "netherite/verdant_chestplate", "Verdant Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/verdant");
    public static final Cosmetic NETHERITE_VERDANT_LEGGINGS = new Cosmetic(
            "netherite/verdant_leggings", "Verdant Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/verdant");
    public static final Cosmetic NETHERITE_VERDANT_BOOTS = new Cosmetic(
            "netherite/verdant_boots", "Verdant Boots", CosmeticSlot.BOOTS, "minecraft:netherite/verdant");

    public static final Cosmetic NETHERITE_VORTEX_HELMET = new Cosmetic(
            "netherite/vortex_helmet", "Vortex Helmet", CosmeticSlot.HELMET, "minecraft:netherite/vortex");
    public static final Cosmetic NETHERITE_VORTEX_CHESTPLATE = new Cosmetic(
            "netherite/vortex_chestplate", "Vortex Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/vortex");
    public static final Cosmetic NETHERITE_VORTEX_LEGGINGS = new Cosmetic(
            "netherite/vortex_leggings", "Vortex Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/vortex");
    public static final Cosmetic NETHERITE_VORTEX_BOOTS = new Cosmetic(
            "netherite/vortex_boots", "Vortex Boots", CosmeticSlot.BOOTS, "minecraft:netherite/vortex");

    public static final Cosmetic NETHERITE_WITHER_HELMET = new Cosmetic(
            "netherite/wither_helmet", "Wither Helmet", CosmeticSlot.HELMET, "minecraft:netherite/wither");
    public static final Cosmetic NETHERITE_WITHER_CHESTPLATE = new Cosmetic(
            "netherite/wither_chestplate", "Wither Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/wither");
    public static final Cosmetic NETHERITE_WITHER_LEGGINGS = new Cosmetic(
            "netherite/wither_leggings", "Wither Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/wither");
    public static final Cosmetic NETHERITE_WITHER_BOOTS = new Cosmetic(
            "netherite/wither_boots", "Wither Boots", CosmeticSlot.BOOTS, "minecraft:netherite/wither");

    public static final Cosmetic NETHERITE_WOLF_HELMET = new Cosmetic(
            "netherite/wolf_helmet", "Netherite Wolf Helmet", CosmeticSlot.HELMET, "minecraft:netherite/wolf");
    public static final Cosmetic NETHERITE_WOLF_CHESTPLATE = new Cosmetic(
            "netherite/wolf_chestplate", "Netherite Wolf Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/wolf");
    public static final Cosmetic NETHERITE_WOLF_LEGGINGS = new Cosmetic(
            "netherite/wolf_leggings", "Netherite Wolf Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/wolf");
    public static final Cosmetic NETHERITE_WOLF_BOOTS = new Cosmetic(
            "netherite/wolf_boots", "Netherite Wolf Boots", CosmeticSlot.BOOTS, "minecraft:netherite/wolf");

    public static final Cosmetic NETHERITE_XARA_HELMET = new Cosmetic(
            "netherite/xara_helmet", "Xara Helmet", CosmeticSlot.HELMET, "minecraft:netherite/xara");
    public static final Cosmetic NETHERITE_XARA_CHESTPLATE = new Cosmetic(
            "netherite/xara_chestplate", "Xara Chestplate", CosmeticSlot.CHESTPLATE, "minecraft:netherite/xara");
    public static final Cosmetic NETHERITE_XARA_LEGGINGS = new Cosmetic(
            "netherite/xara_leggings", "Xara Leggings", CosmeticSlot.LEGGINGS, "minecraft:netherite/xara");
    public static final Cosmetic NETHERITE_XARA_BOOTS = new Cosmetic(
            "netherite/xara_boots", "Xara Boots", CosmeticSlot.BOOTS, "minecraft:netherite/xara");

    // </generated>

    public static List<Cosmetic> GetAll() {
        Field[] declaredFields = Cosmetics.class.getDeclaredFields();
        List<Cosmetic> all = new ArrayList<>();
        for (Field field : declaredFields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                try {
                    Object o = field.get(null);
                    if (o instanceof Cosmetic c) {
                        all.add(c);
                    }
                } catch (NullPointerException | IllegalAccessException | IllegalArgumentException e) {
                    ServerMagic.LOGGER.error("Failed to access cosmetic using reflection: " + e.getStackTrace());
                }
            }
        }
        return all;
    }

    public static List<Cosmetic> GetAllForSlot(CosmeticSlot slot) {
        return GetAll().stream().filter(c -> c.getSlot() == slot).toList();
    }

    private static volatile Map<String, Cosmetic> ID_CACHE;

    public static Optional<Cosmetic> GetById(String id) {
        if (ID_CACHE == null) {
            Map<String, Cosmetic> cache = new HashMap<>();
            GetAll().forEach(c -> cache.put(c.getId(), c));
            ID_CACHE = cache;
        }
        return Optional.ofNullable(ID_CACHE.get(id));
    }
}
