package servermagic.spells.utils;

import java.util.List;
import java.util.Optional;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownLingeringPotion;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownSplashPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;

public class PotionSpellUtils {
    public static void ThrowSplashPotion(ServerLevel world, ServerPlayer player, List<MobEffectInstance> effects) {
        ItemStack stack = new ItemStack(Items.SPLASH_POTION);
        PotionContents contents = new PotionContents(Optional.empty(), Optional.empty(), effects, Optional.empty());
        stack.set(DataComponents.POTION_CONTENTS, contents);

        ThrownSplashPotion potion = new ThrownSplashPotion(world, player, stack);
        potion.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
        potion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);

        world.addFreshEntity(potion);
    }

    public static void ThrowLingeringPotion(ServerLevel world, ServerPlayer player, List<MobEffectInstance> effects) {
        ItemStack stack = new ItemStack(Items.LINGERING_POTION);
        PotionContents contents = new PotionContents(Optional.empty(), Optional.empty(), effects, Optional.empty());
        stack.set(DataComponents.POTION_CONTENTS, contents);

        ThrownLingeringPotion potion = new ThrownLingeringPotion(world, player, stack);
        potion.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
        potion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);

        world.addFreshEntity(potion);
    }
}
