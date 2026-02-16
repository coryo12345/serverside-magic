package servermagic.web.skill;

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
}
