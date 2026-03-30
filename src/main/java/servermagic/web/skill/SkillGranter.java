package servermagic.web.skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import servermagic.db.Database;
import servermagic.db.tables.SkillUnlocks;

public class SkillGranter {
    private ServerLevel world;
    private ServerPlayer player;
    private Database db;

    public SkillGranter(ServerLevel world, ServerPlayer player, Database db) {
        this.world = world;
        this.player = player;
        this.db = db;
    }

    public void grantFromItemUse(ItemStack item) {
        List<Skill> skillsPotentiallyEarned = new ArrayList<>();

        // determine what skills to earn
        if (item.is(Items.POTION)) {
            PotionContents contents = item.get(DataComponents.POTION_CONTENTS);
            if (contents != null) {
                for (MobEffectInstance effect : contents.getAllEffects()) {
                    if (effect.getEffect().equals(MobEffects.FIRE_RESISTANCE)) {
                        skillsPotentiallyEarned.add(Skills.FIRE_RESISTANCE_SPELL);
                    } else if (effect.getEffect().equals(MobEffects.INSTANT_HEALTH)) {
                        skillsPotentiallyEarned.add(Skills.INSTANT_HEALTH_SPELL);
                    } else if (effect.getEffect().equals(MobEffects.JUMP_BOOST)) {
                        skillsPotentiallyEarned.add(Skills.JUMP_BOOST_SPELL);
                    } else if (effect.getEffect().equals(MobEffects.SLOW_FALLING)) {
                        skillsPotentiallyEarned.add(Skills.SLOW_FALLING_SPELL);
                    } else if (effect.getEffect().equals(MobEffects.NIGHT_VISION)) {
                        skillsPotentiallyEarned.add(Skills.NIGHT_VISION_SPELL);
                    } else if (effect.getEffect().equals(MobEffects.INVISIBILITY)) {
                        skillsPotentiallyEarned.add(Skills.INVISIBILITY_SPELL);
                    } else if (effect.getEffect().equals(MobEffects.WATER_BREATHING)) {
                        skillsPotentiallyEarned.add(Skills.WATER_BREATHING_SPELL);
                    } else if (effect.getEffect().equals(MobEffects.REGENERATION)) {
                        skillsPotentiallyEarned.add(Skills.REGENERATION_SPELL);
                    } else if (effect.getEffect().equals(MobEffects.STRENGTH)) {
                        skillsPotentiallyEarned.add(Skills.STRENGTH_SPELL);
                    }
                }
            }
        } else if (item.is(Items.SPLASH_POTION)) {
            PotionContents contents = item.get(DataComponents.POTION_CONTENTS);
            if (contents != null) {
                for (MobEffectInstance effect : contents.getAllEffects()) {
                    if (effect.getEffect().equals(MobEffects.INSTANT_HEALTH)) {
                        skillsPotentiallyEarned.add(Skills.INSTANT_HEALTH_SPLASH_SPELL);
                    } else if (effect.getEffect().equals(MobEffects.FIRE_RESISTANCE)) {
                        skillsPotentiallyEarned.add(Skills.FIRE_RESISTANCE_SPLASH_SPELL);
                    } else if (effect.getEffect().equals(MobEffects.INVISIBILITY)) {
                        skillsPotentiallyEarned.add(Skills.INVISIBILITY_SPLASH_SPELL);
                    } else if (effect.getEffect().equals(MobEffects.REGENERATION)) {
                        skillsPotentiallyEarned.add(Skills.REGENERATION_SPLASH_SPELL);
                    }
                }
            }
        }

        // now try to grant the skills
        if (!skillsPotentiallyEarned.isEmpty()) {
            boolean anyUnlocked = false;
            for (Skill skill : skillsPotentiallyEarned) {
                Optional<SkillUnlocks> su = SkillUnlocks.UnlockSkillForPlayerIfAble(db, player.getPlainTextName(),
                        skill);
                if (su.isPresent()) {
                    anyUnlocked = true;
                }
            }
            if (anyUnlocked) {
                SkillTree.UpdateSpellAvailabiltyForPlayer(db, player.getPlainTextName());
            }
        }
    }
}
