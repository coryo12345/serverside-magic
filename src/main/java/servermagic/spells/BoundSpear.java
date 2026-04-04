package servermagic.spells;

import java.util.Optional;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import servermagic.db.Database;
import servermagic.spells.utils.BoundItems;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class BoundSpear extends BaseSpell {

    public BoundSpear(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        ItemStack spear = new ItemStack(Items.IRON_SPEAR);
        ItemStack mainHandItem = this.player.getItemInHand(hand);
        BoundItems.BuildBoundItem(this.player, mainHandItem, spear);
        this.player.setItemInHand(InteractionHand.MAIN_HAND, spear);
        world.sendParticles(ParticleTypes.ENCHANTED_HIT,
                player.getX(), player.getY() + 1.0, player.getZ(), 15, 0.3, 0.3, 0.3, 0.1);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 0.8F, 1.2F);
    }

    @Override
    public String displayName() {
        return "Bound Spear";
    }

    @Override
    public String description() {
        return "Summon a bound spear";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.BOUND_SPEAR);
    }
}
