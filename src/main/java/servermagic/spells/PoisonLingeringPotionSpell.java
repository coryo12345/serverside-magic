package servermagic.spells;

import java.util.Optional;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownLingeringPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class PoisonLingeringPotionSpell extends BaseSpell {

    public PoisonLingeringPotionSpell(ServerLevel world, ServerPlayer player, Database db) {
        super(world, player, db);
    }

    @Override
    protected void spellImplementation() {
        // Create the lingering potion item stack
        ItemStack potionStack = new ItemStack(Items.LINGERING_POTION);

        // Set the potion contents to Poison. In 1.21.x, PotionContents handles the
        // potion type.
        // Potions.POISON is a Holder<Potion> in 1.21.x Mojang mappings.
        potionStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.POISON));

        // Create the thrown lingering potion entity
        // Constructor in this version (1.21.2+): (Level, LivingEntity, ItemStack)
        ThrownLingeringPotion thrownPotion = new ThrownLingeringPotion(world, player, potionStack);

        // Match the player's throw behavior:
        // shootFromRotation(shooter, xRot, yRot, roll, speed, divergence)
        // A standard potion throw uses an offset of -20.0F for pitch, speed of 0.5F,
        // and divergence of 1.0F.
        thrownPotion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);

        // Spawn the entity in the world
        world.addFreshEntity(thrownPotion);

        // Play the splash potion throw sound at the player's location
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 0.5F,
                0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    @Override
    public String displayName() {
        return "Poison Cloud";
    }

    @Override
    public String description() {
        return "Launches a potion of poison cloud in the direction you are looking";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.POISON_CLOUD);
    }
}