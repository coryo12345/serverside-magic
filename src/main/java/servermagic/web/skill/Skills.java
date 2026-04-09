package servermagic.web.skill;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import servermagic.ServerMagic;

public class Skills {
        // ===================== Building Tree =====================
        public static final Skill UNLOCK_MAGIC_BUILDING = new Skill("unlock_magic_building", "Unlock Magic",
                        "Unlock the use of magic!",
                        null)
                        .setTabTitle("Building")
                        .setAdvancement("end/root", "The End");
        public static final Skill BUILD_LINE = new Skill("build_line", "Build: Line", "Build blocks in a line",
                        UNLOCK_MAGIC_BUILDING.id())
                        .setAdvancement("husbandry/plant_seed", "A Seedy Place");
        public static final Skill BUILD_SQUARE = new Skill("build_square", "Build: Square",
                        "Build a hollow square around the player", UNLOCK_MAGIC_BUILDING.id())
                        .setAdvancement("adventure/summon_iron_golem", "Hired Help");
        public static final Skill BUILD_RING = new Skill("build_ring", "Build: Ring",
                        "Build a hollow ring around the player", UNLOCK_MAGIC_BUILDING.id())
                        .setAdvancement("adventure/trade", "What a Deal!");

        // ===================== Elemental Tree =====================
        public static final Skill UNLOCK_MAGIC_ELEMENTAL = new Skill("unlock_magic_elemental", "Unlock Magic",
                        "Unlock the use of magic!",
                        null)
                        .setTabTitle("Elemental")
                        .setAdvancement("end/root", "The End");
        public static final Skill FIREBOLT = new Skill("firebolt", "Firebolt", "Shoot a short bolt of fire",
                        UNLOCK_MAGIC_ELEMENTAL.id())
                        .setAdvancement("nether/obtain_blaze_rod", "Into Fire");
        public static final Skill FIREBALL = new Skill("fireball", "Fireball",
                        "Shoot an explosive fireball", FIREBOLT.id())
                        .setAdvancement("nether/return_to_sender", "Return to Sender");
        public static final Skill RING_OF_FIRE = new Skill("ring_of_fire", "Ring of Fire",
                        "Ignite a ring of fire that burns enemies who enter", FIREBALL.id())
                        .setAdvancement("nether/explore_nether", "Hot Tourist Destinations");
        public static final Skill METEOR_SHOWER = new Skill("meteor_shower", "Meteor Shower",
                        "Call down a shower of blazing meteors upon a target location", FIREBALL.id())
                        .setAdvancement("nether/summon_wither", "Withering Heights");
        public static final Skill LIGHTNING_STRIKE = new Skill("lightning_strike", "Lightning Strike",
                        "Strike your target with a bolt of lightning from above", UNLOCK_MAGIC_ELEMENTAL.id())
                        .setAdvancement("adventure/lightning_rod_with_villager_no_fire", "Surge Protector");
        public static final Skill SUNBEAM = new Skill("sunbeam", "Sunbeam",
                        "Summon a pillar of holy light that devastates undead mobs within",
                        LIGHTNING_STRIKE.id())
                        .setAdvancement("adventure/totem_of_undying", "Postmortal");
        public static final Skill CHAIN_LIGHTNING = new Skill("chain_lightning", "Chain Lightning",
                        "Shoot an arcing blast of lightning that chains between enemies", LIGHTNING_STRIKE.id())
                        .setAdvancement("adventure/two_birds_one_arrow", "Two Birds, One Arrow");
        public static final Skill ARCANE_MISSILES = new Skill("arcane_missiles", "Arcane Missiles",
                        "Launch homing arcane projectiles at the nearest enemy", CHAIN_LIGHTNING.id())
                        .setAdvancement("adventure/arbalistic", "Arbalistic");
        public static final Skill POISON_CLOUD = new Skill("poison_cloud", "Poison Cloud",
                        "Conjure a cloud of poison in an area", UNLOCK_MAGIC_ELEMENTAL.id())
                        .setAdvancement("nether/brew_potion", "Local Brewery");
        public static final Skill BEE_SWARM = new Skill("bee_swarm", "Bee Swarm",
                        "Summon a swarm of bees to attack the nearest enemy", POISON_CLOUD.id())
                        .setAdvancement("husbandry/safely_harvest_honey", "Bee Our Guest");
        public static final Skill WINDCHARGE = new Skill("windcharge", "Wind Charge",
                        "Shoot a powerful gust of wind", POISON_CLOUD.id())
                        .setAdvancement("adventure/who_needs_rockets", "Who Needs Rockets?");
        public static final Skill WIND_GUST = new Skill("wind_gust", "Wind Gust",
                        "Blast a powerful cone of wind that flings all nearby enemies away", WINDCHARGE.id())
                        .setAdvancement("adventure/fall_from_world_height", "Caves & Cliffs");
        public static final Skill GRAVITY_WELL = new Skill("gravity_well", "Gravity Well",
                        "Open a vortex that pulls nearby enemies toward its center", WINDCHARGE.id())
                        .setUnlockDescription("Throw an ender pearl into the void while in The End");
        public static final Skill FREEZE = new Skill("freeze", "Freeze",
                        "Shoot a block of ice that freezes your target", UNLOCK_MAGIC_ELEMENTAL.id())
                        .setAdvancement("adventure/walk_on_powder_snow_with_leather_boots", "Light as a Rabbit");
        public static final Skill FROST_NOVA = new Skill("frost_nova", "Frost Nova",
                        "Blast nearby enemies back with a freezing shockwave", FREEZE.id())
                        .setUnlockDescription("Kill a Stray with a Fireball");
        public static final Skill EARTHEN_SPIKE = new Skill("earthen_spike", "Earthen Spike",
                        "Summon a line of earth spikes in front of you", UNLOCK_MAGIC_ELEMENTAL.id())
                        .setAdvancement("adventure/adventuring_time", "Adventuring Time");
        public static final Skill SPECTRAL_GRASP = new Skill("spectral_grasp", "Spectral Grasp",
                        "Lift a target mob into the air with a spectral force, suspending them before they fall",
                        GRAVITY_WELL.id())
                        .setUnlockDescription("Kill a Shulker while you have the Levitation effect");
        public static final Skill IRON_MAIDEN = new Skill("iron_maiden", "Iron Maiden",
                        "Encase a target in iron bars for 2 seconds, then crush them with a burst of piercing damage",
                        SPECTRAL_GRASP.id())
                        .setUnlockDescription("Right-click an Anvil while holding a Soul Lantern");
        public static final Skill DESECRATED_GROUND = new Skill("desecrated_ground", "Desecrated Ground",
                        "Corrupt the earth beneath your feet, withering any mob that enters the zone",
                        RING_OF_FIRE.id())
                        .setAdvancement("nether/get_wither_skull", "Spooky Scary Skeleton");
        public static final Skill REAP = new Skill("reap", "Reap",
                        "Execute mobs below 20% health in a wide sweeping arc", DESECRATED_GROUND.id())
                        .setAdvancement("adventure/kill_all_mobs", "Monsters Hunted");

        // ===================== Utility Tree =====================
        public static final Skill UNLOCK_MAGIC_UTILITY = new Skill("unlock_magic_utility", "Unlock Magic",
                        "Unlock the use of magic!",
                        null)
                        .setTabTitle("Utility")
                        .setAdvancement("end/root", "The End");
        public static final Skill SUMMON_MOUNT = new Skill("summon_mount", "Mount: Horse", "Summon your loyal steed",
                        UNLOCK_MAGIC_UTILITY.id())
                        .setAdvancement("husbandry/tame_an_animal", "Best Friends Forever");
        public static final Skill MOUNT_GALLOP = new Skill("mount_gallop", "Mount: Gallop",
                        "Unlock an additional speed tier for your summoned mount. Configure your mount's speed in the Spellbook.",
                        SUMMON_MOUNT.id())
                        .setAdvancement("husbandry/breed_an_animal", "The Parrots and the Bats");
        public static final Skill MOUNT_JUMP = new Skill("mount_jump", "Mount: High Jump",
                        "Unlock an enhanced jump height for your summoned mount. Configure in the Spellbook.",
                        SUMMON_MOUNT.id())
                        .setAdvancement("adventure/bullseye", "Bullseye");
        public static final Skill MOUNT_ARMOR = new Skill("mount_armor", "Mount: Armor",
                        "Equip your summoned mount with horse armor. Configure in the Spellbook.",
                        SUMMON_MOUNT.id())
                        .setAdvancement("story/shiny_gear", "Cover Me with Diamonds");
        public static final Skill BOUND_SPYGLASS = new Skill("bound_spyglass", "Bound Spyglass",
                        "Conjure a bound spyglass", UNLOCK_MAGIC_UTILITY.id())
                        .setAdvancement("adventure/spyglass_at_parrot", "Is It a Bird?");
        public static final Skill BAG_OF_HOLDING = new Skill("bag_of_holding", "Bag of Holding",
                        "Access your ender chest from anywhere", BOUND_SPYGLASS.id())
                        .setUnlockDescription("Right-click an Ender Chest while holding a Shulker Box");
        public static final Skill BOUND_SWORD = new Skill("bound_sword", "Bound Sword", "Conjure a bound sword",
                        UNLOCK_MAGIC_UTILITY.id())
                        .setUnlockDescription("Smith a Netherite Sword");
        public static final Skill BOUND_AXE = new Skill("bound_axe", "Bound Axe", "Conjure a bound axe",
                        BOUND_SWORD.id())
                        .setUnlockDescription("Smith a Netherite Axe");
        public static final Skill BOUND_SPEAR = new Skill("bound_spear", "Bound Spear", "Conjure a bound spear",
                        BOUND_SWORD.id())
                        .setUnlockDescription("Smith a Netherite Spear");
        public static final Skill BOUND_PICKAXE = new Skill("bound_pickaxe", "Bound Pickaxe",
                        "Conjure a bound pickaxe", BOUND_SPYGLASS.id())
                        .setUnlockDescription("Smith a Netherite Pickaxe");
        public static final Skill BOUND_SHOVEL = new Skill("bound_shovel", "Bound Shovel", "Conjure a bound shovel",
                        BOUND_SPYGLASS.id())
                        .setUnlockDescription("Smith a Netherite Shovel");
        public static final Skill BOUND_HOE = new Skill("bound_hoe", "Bound Hoe", "Conjure a bound hoe",
                        BOUND_SPYGLASS.id())
                        .setUnlockDescription("Smith a Netherite Hoe");
        public static final Skill BOUND_SHEARS = new Skill("bound_shears", "Bound Shears", "Conjure bound shears",
                        BOUND_SPYGLASS.id())
                        .setAdvancement("adventure/honey_block_slide", "Sticky Situation");
        public static final Skill BOUND_FISHING_ROD = new Skill("bound_fishing_rod", "Bound Fishing Rod",
                        "Conjure a bound fishing rod", BOUND_SPYGLASS.id())
                        .setAdvancement("husbandry/fishy_business", "Fishy Business");
        public static final Skill SPECTRAL_HAMMER = new Skill("spectral_hammer", "Spectral Hammer",
                        "Summon a spectral hammer that smashes a 3x3 face of blocks", BOUND_PICKAXE.id())
                        .setUnlockDescription("Take a pickaxe to the depths of the overworld.");

        // ===================== Effects Tree =====================
        public static final Skill UNLOCK_MAGIC_EFFECTS = new Skill("unlock_magic_effects", "Unlock Magic",
                        "Unlock the use of magic!",
                        null)
                        .setTabTitle("Effects")
                        .setAdvancement("end/root", "The End");
        public static final Skill SPEED_SPELL = new Skill("speed_spell", "Speed Spell",
                        "Enhance self with a burst of speed", UNLOCK_MAGIC_EFFECTS.id())
                        .setAdvancement("nether/brew_potion", "Local Brewery");
        public static final Skill JUMP_BOOST_SPELL = new Skill("jump_boost_spell", "Jump Boost",
                        "Leap higher into the air", SPEED_SPELL.id())
                        .setUnlockDescription("Drink a potion of leaping");
        public static final Skill SLOW_FALLING_SPELL = new Skill("slow_falling_spell", "Slow Falling",
                        "Fall slowly and safely", JUMP_BOOST_SPELL.id())
                        .setUnlockDescription("Drink a potion of slow falling");
        public static final Skill NIGHT_VISION_SPELL = new Skill("night_vision_spell", "Night Vision",
                        "See clearly in the dark", UNLOCK_MAGIC_EFFECTS.id())
                        .setUnlockDescription("Drink a potion of night vision");
        public static final Skill INVISIBILITY_SPELL = new Skill("invisibility_spell", "Invisibility",
                        "Vanish from sight", NIGHT_VISION_SPELL.id())
                        .setUnlockDescription("Drink a potion of invisibility");
        public static final Skill INVISIBILITY_SPLASH_SPELL = new Skill("invisibility_splash_spell",
                        "Invisibility Others",
                        "Grant invisibility to others", INVISIBILITY_SPELL.id())
                        .setUnlockDescription("Use a splash potion of invisibility");
        public static final Skill WATER_BREATHING_SPELL = new Skill("water_breathing_spell", "Water Breathing",
                        "Breathe underwater", NIGHT_VISION_SPELL.id())
                        .setUnlockDescription("Drink a potion of water breathing");
        public static final Skill INSTANT_HEALTH_SPELL = new Skill("instant_health_spell", "Heal Spell",
                        "Heal a small amount of hearts", UNLOCK_MAGIC_EFFECTS.id())
                        .setUnlockDescription("Drink a potion of healing");
        public static final Skill INSTANT_HEALTH_SPLASH_SPELL = new Skill("instant_health_splash_spell", "Heal Others",
                        "Throw a splash healing mist", INSTANT_HEALTH_SPELL.id())
                        .setUnlockDescription("Use a splash potion of healing");
        public static final Skill REGENERATION_SPELL = new Skill("regeneration_spell", "Regeneration",
                        "Regenerate health over time", INSTANT_HEALTH_SPELL.id())
                        .setUnlockDescription("Drink a potion of regeneration");
        public static final Skill REGENERATION_SPLASH_SPELL = new Skill("regeneration_splash_spell",
                        "Regeneration Others",
                        "Grant regeneration to others", REGENERATION_SPELL.id())
                        .setUnlockDescription("Use a splash potion of regeneration");
        public static final Skill FIRE_RESISTANCE_SPELL = new Skill("fire_resistance_spell", "Fire Resistance",
                        "Grant fire resistance to yourself", UNLOCK_MAGIC_EFFECTS.id())
                        .setUnlockDescription("Drink a potion of fire resistance");
        public static final Skill FIRE_RESISTANCE_SPLASH_SPELL = new Skill("fire_resistance_splash_spell",
                        "Fire Resistance Others",
                        "Grant fire resistance to others", FIRE_RESISTANCE_SPELL.id())
                        .setUnlockDescription("Use a splash potion of fire resistance");
        public static final Skill STRENGTH_SPELL = new Skill("strength_spell", "Strength",
                        "Increase melee damage", FIRE_RESISTANCE_SPELL.id())
                        .setUnlockDescription("Drink a potion of strength");

        // ===================== Secrets =====================
        public static final Skill SECRETS = new Skill("secrets", "Secrets", "secret skills", null);

        public static final Skill SHADOW_STEP = new Skill("shadow_step", "Shadow Step",
                        "Teleport behind a target mob and gain Strength II for 2 seconds", SECRETS.id())
                        .setAdvancement("end/respawn_dragon", "The End... Again...");
        public static final Skill BATTLEMAGE_ARMOR = new Skill("battlemage_armor", "Battlemage Armor",
                        "Summon a suit of bound armor", SECRETS.id())
                        .setUnlockDescription("Smith a piece of Netherite Armor");
        public static final Skill ANGEL_WINGS = new Skill("angel_wings", "Angel Wings", "Summon wings and fly!",
                        SECRETS.id())
                        .setUnlockDescription("Reach Y > 1000 while wearing an Elytra");
        public static final Skill VOID_RIFT = new Skill("void_rift", "Void Rift",
                        "Fire a void skull that blinds and withers nearby enemies", SECRETS.id())
                        .setAdvancement("end/dragon_breath", "You Need a Mint");
        public static final Skill FLYING_CARPET = new Skill("flying_carpet", "Flying Carpet",
                        "Summon a magical flying carpet", SECRETS.id())
                        .setUnlockDescription("Hold a carpet while riding a Happy Ghast");

        public static List<Skill> GetAllSkills() {
                Field[] declaredFields = Skills.class.getDeclaredFields();
                List<Skill> allSkills = new ArrayList<>();
                for (Field field : declaredFields) {
                        if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                                try {
                                        Object o = field.get(null);
                                        if (o instanceof Skill s) {
                                                allSkills.add(s);
                                        }
                                } catch (NullPointerException | IllegalAccessException | IllegalArgumentException e) {
                                        // shouldn't happen since we're using static fields
                                        ServerMagic.LOGGER.error("Failed to access skill using reflection: "
                                                        + e.getStackTrace());
                                }
                        }
                }
                return allSkills;
        }

        public static List<Skill> GetSkillsAwardedForAdvancement(String advancementResourceLocation) {
                if (advancementResourceLocation == null) {
                        return List.of();
                }
                List<Skill> skills = GetAllSkills();
                List<Skill> awardedSkills = new ArrayList<>();
                for (Skill s : skills) {
                        if (advancementResourceLocation.equals(s.advancementResourceLocation())) {
                                awardedSkills.add(s);
                        }
                }
                return awardedSkills;
        }
}
