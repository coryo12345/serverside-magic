package servermagic.web.skill;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import servermagic.ServerMagic;

public class Skills {
    public static final Skill ANGEL_WINGS = new Skill("angel_wings", "Angel Wings", "Summon wings and fly!", null);
    public static final Skill SUMMON_MOUNT = new Skill("summon_mount", "Mount: Horse", "Summon your loyal steed",
            ANGEL_WINGS.id());
    public static final Skill BATTLEMAGE_ARMOR = new Skill("battlemage_armor", "Battlemage Armor",
            "Summon a suit of bound armor", ANGEL_WINGS.id());
    public static final Skill FIREBOLT = new Skill("firebolt", "Firebolt", "Shoot a short bolt of fire",
            ANGEL_WINGS.id());
    public static final Skill SPEED_SPELL = new Skill("speed_spell", "Speed Spell",
            "Enhance self with a burst of speed", ANGEL_WINGS.id());
    public static final Skill CHAIN_LIGHTNING = new Skill("chain_lightning", "Chain Lightning",
            "Shoot an arcing blast of lightning that chains between enemies", ANGEL_WINGS.id());
    public static final Skill FIREBALL = new Skill("fireball", "Fireball",
            "Shoot an explosive fireball", FIREBOLT.id());
    public static final Skill WITHERBLAST = new Skill("witherblast", "Witherblast",
            "Shoot an explosive wither skull projectile", FIREBOLT.id());
    public static final Skill LIGHTNING_STRIKE = new Skill("lightning_strike", "Lightning Strike",
            "Strike your target with a bolt of lightning from above", FIREBOLT.id());
    public static final Skill POISON_CLOUD = new Skill("poison_cloud", "Poison Cloud",
            "Conjure a cloud of poison in an area", SPEED_SPELL.id());
    public static final Skill WINDCHARGE = new Skill("windcharge", "Wind Charge",
            "Shoot a powerful gust of wind", SPEED_SPELL.id())
            .setAdvancement("adventure/who_needs_rockets", "Who Needs Rockets?");

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
