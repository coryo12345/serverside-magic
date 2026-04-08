package servermagic.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.skeleton.Stray;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import servermagic.db.Database;
import servermagic.web.skill.SkillGranter;
import servermagic.web.skill.Skills;

@Mixin(LivingEntity.class)
public class MobDeathMixin {

    @Inject(method = "die", at = @At("HEAD"))
    private void onDie(DamageSource source, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        Optional<Database> db = Database.GetDB();
        if (db.isEmpty()) {
            return;
        }

        // FROST_NOVA: Kill a Stray with a Fireball (LargeFireball projectile)
        if (entity instanceof Stray) {
            if (source.getDirectEntity() instanceof LargeFireball fireball
                    && fireball.getOwner() instanceof ServerPlayer attacker) {
                SkillGranter.grantSkillForPlayer(db.get(), attacker, Skills.FROST_NOVA);
            }
        }

        // SPECTRAL_GRASP: Kill a Shulker while the attacker has Levitation
        if (entity instanceof Shulker) {
            if (source.getEntity() instanceof ServerPlayer attacker
                    && attacker.hasEffect(MobEffects.LEVITATION)) {
                SkillGranter.grantSkillForPlayer(db.get(), attacker, Skills.SPECTRAL_GRASP);
            }
        }
    }
}
