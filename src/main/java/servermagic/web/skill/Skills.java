package servermagic.web.skill;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import servermagic.ServerMagic;

public class Skills {
        public static final Skill UNLOCK_MAGIC = new Skill("unlock_magic", "Unlock Magic", "Unlock the use of magic!",
                        null).setAdvancement("end/root", "The End");
        public static final Skill SUMMON_MOUNT = new Skill("summon_mount", "Mount: Horse", "Summon your loyal steed",
                        UNLOCK_MAGIC.id())
                        .setAdvancement("husbandry/tame_an_animal", "Best friends forever");
        public static final Skill BOUND_SWORD = new Skill("bound_sword", "Bound Sword", "Conjure a bound sword",
                        UNLOCK_MAGIC.id());
        public static final Skill BATTLEMAGE_ARMOR = new Skill("battlemage_armor", "Battlemage Armor",
                        "Summon a suit of bound armor", UNLOCK_MAGIC.id());
        public static final Skill FIREBOLT = new Skill("firebolt", "Firebolt", "Shoot a short bolt of fire",
                        UNLOCK_MAGIC.id());
        public static final Skill SPEED_SPELL = new Skill("speed_spell", "Speed Spell",
                        "Enhance self with a burst of speed", UNLOCK_MAGIC.id())
                        .setAdvancement("nether/brew_potion", "Local brewery");

        // Movement Effects Branch
        public static final Skill JUMP_BOOST_SPELL = new Skill("jump_boost_spell", "Jump Boost",
                        "Leap higher into the air", SPEED_SPELL.id())
                        .setUnlockDescription("Drink a potion of leaping");
        public static final Skill JUMP_BOOST_SPLASH_SPELL = new Skill("jump_boost_splash_spell", "Jump Boost Others",
                        "Grant jump boost to others", JUMP_BOOST_SPELL.id())
                        .setUnlockDescription("Use a splash potion of leaping");
        public static final Skill SLOW_FALLING_SPELL = new Skill("slow_falling_spell", "Slow Falling",
                        "Fall slowly and safely", JUMP_BOOST_SPELL.id())
                        .setUnlockDescription("Drink a potion of slow falling");
        public static final Skill SLOW_FALLING_SPLASH_SPELL = new Skill("slow_falling_splash_spell",
                        "Slow Falling Others",
                        "Grant slow falling to others", SLOW_FALLING_SPELL.id())
                        .setUnlockDescription("Use a splash potion of slow falling");

        // Utility Effects Branch
        public static final Skill NIGHT_VISION_SPELL = new Skill("night_vision_spell", "Night Vision",
                        "See clearly in the dark", SPEED_SPELL.id())
                        .setUnlockDescription("Drink a potion of night vision");
        public static final Skill NIGHT_VISION_SPLASH_SPELL = new Skill("night_vision_splash_spell",
                        "Night Vision Others",
                        "Grant night vision to others", NIGHT_VISION_SPELL.id())
                        .setUnlockDescription("Use a splash potion of night vision");
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
        public static final Skill WATER_BREATHING_SPLASH_SPELL = new Skill("water_breathing_splash_spell",
                        "Water Breathing Others",
                        "Grant water breathing to others", WATER_BREATHING_SPELL.id())
                        .setUnlockDescription("Use a splash potion of water breathing");

        // Health Branches
        public static final Skill INSTANT_HEALTH_SPELL = new Skill("instant_health_spell", "Heal Spell",
                        "Heal a small amount of hearts", SPEED_SPELL.id())
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

        // Strength & Fire Resist
        public static final Skill FIRE_RESISTANCE_SPELL = new Skill("fire_resistance_spell", "Fire Resistance",
                        "Grant fire resistance to yourself", SPEED_SPELL.id())
                        .setUnlockDescription("Drink a potion of fire resistance");
        public static final Skill FIRE_RESISTANCE_SPLASH_SPELL = new Skill("fire_resistance_splash_spell",
                        "Fire Resistance Others",
                        "Grant fire resistance to others", FIRE_RESISTANCE_SPELL.id())
                        .setUnlockDescription("Use a splash potion of fire resistance");
        public static final Skill STRENGTH_SPELL = new Skill("strength_spell", "Strength",
                        "Increase melee damage", FIRE_RESISTANCE_SPELL.id())
                        .setUnlockDescription("Drink a potion of strength");
        public static final Skill STRENGTH_SPLASH_SPELL = new Skill("strength_splash_spell", "Strength Others",
                        "Grant strength to others", STRENGTH_SPELL.id())
                        .setUnlockDescription("Use a splash potion of strength");

        // Elemental Attakcs
        public static final Skill FIREBALL = new Skill("fireball", "Fireball",
                        "Shoot an explosive fireball", FIREBOLT.id());
        public static final Skill LIGHTNING_STRIKE = new Skill("lightning_strike", "Lightning Strike",
                        "Strike your target with a bolt of lightning from above", FIREBOLT.id());
        public static final Skill CHAIN_LIGHTNING = new Skill("chain_lightning", "Chain Lightning",
                        "Shoot an arcing blast of lightning that chains between enemies", LIGHTNING_STRIKE.id());
        public static final Skill POISON_CLOUD = new Skill("poison_cloud", "Poison Cloud",
                        "Conjure a cloud of poison in an area", FIREBOLT.id());
        public static final Skill WINDCHARGE = new Skill("windcharge", "Wind Charge",
                        "Shoot a powerful gust of wind", POISON_CLOUD.id())
                        .setAdvancement("adventure/who_needs_rockets", "Who Needs Rockets?");

        public static final Skill SECRETS = new Skill("secrets", "Secrets", "secret skills", null);
        public static final Skill ANGEL_WINGS = new Skill("angel_wings", "Angel Wings", "Summon wings and fly!",
                        SECRETS.id())
                        .setAdvancement("end/elytra", "Sky's the limit");
        public static final Skill WITHERBLAST = new Skill("witherblast", "Witherblast",
                        "Shoot an explosive wither skull projectile", SECRETS.id());

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
