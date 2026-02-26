package servermagic.mixin;

import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import servermagic.db.Database;
import servermagic.db.tables.SkillUnlocks;
import servermagic.web.skill.Skill;
import servermagic.web.skill.SkillTree;
import servermagic.web.skill.Skills;

@Mixin(PlayerAdvancements.class)
public abstract class PlayerAdvancementsMixin {

    @Shadow
    private ServerPlayer player;

    @Inject(method = "award", at = @At("RETURN"))
    private void onAdvancementAwarded(AdvancementHolder advancementHolder, String criterionName,
            CallbackInfoReturnable<Boolean> cir) {
        // cir.getReturnValue() is true only when the advancement was just completed
        if (!cir.getReturnValue())
            return;

        Optional<Database> db = Database.GetDB();
        if (db.isEmpty()) {
            return;
        }

        String advancementResourceLocation = advancementHolder.id().toShortString();
        List<Skill> skillsToUnlock = Skills.GetSkillsAwardedForAdvancement(advancementResourceLocation);
        boolean anyUnlocked = false;
        for (Skill s : skillsToUnlock) {
            Optional<SkillUnlocks> su = SkillUnlocks.UnlockSkillForPlayerIfAble(db.get(), player.getPlainTextName(), s);
            if (su.isPresent()) {
                anyUnlocked = true;
            }
        }

        if (anyUnlocked) {
            SkillTree.UpdateSpellAvailabiltyForPlayer(db.get(), player.getPlainTextName());
        }
    }
}
