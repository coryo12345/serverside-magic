package servermagic.spells;

import java.util.Optional;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class BagOfHolding extends BaseSpell {

    public BagOfHolding(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        player.openMenu(new SimpleMenuProvider(
                (id, playerInv, p) -> ChestMenu.threeRows(id, playerInv, p.getEnderChestInventory()),
                // Component.translatable("container.enderchest")
                Component.literal("Bag of holding")));
    }

    @Override

    public int getFlatXpCost() {
        return 2;
    }

    public double getLevelPercentCost() {
        return 0.05;
    }

    public String displayName() {
        return "Bag of Holding";
    }

    @Override
    public String description() {
        return "Access your ender chest from anywhere";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.BAG_OF_HOLDING);
    }
}
