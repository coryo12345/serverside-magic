package servermagic.spells;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import servermagic.db.Database;
import servermagic.spells.utils.BoundItems;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class BoundShovel extends BaseSpell {

    public BoundShovel(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        ItemStack shovel = new ItemStack(Items.IRON_SHOVEL);
        ItemStack mainHandItem = this.player.getItemInHand(hand);
        BoundItems.BuildBoundItem(this.player, mainHandItem, shovel);
        this.player.setItemInHand(InteractionHand.MAIN_HAND, shovel);
    }

    @Override
    public String displayName() {
        return "Bound Shovel";
    }

    @Override
    public String description() {
        return "Summon a bound shovel";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.BOUND_SHOVEL);
    }
}
