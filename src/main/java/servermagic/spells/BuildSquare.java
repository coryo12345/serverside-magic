package servermagic.spells;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class BuildSquare extends BaseSpell {

    public BuildSquare(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        ItemStack source = player.getOffhandItem();
        if (source.isEmpty() || !(source.getItem() instanceof BlockItem)) {
            return;
        }

        int radius = getRadius();
        int cx = player.getBlockX();
        int cy = player.getBlockY();
        int cz = player.getBlockZ();
        BlockState blockState = ((BlockItem) source.getItem()).getBlock().defaultBlockState();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (Math.abs(dx) != radius && Math.abs(dz) != radius) {
                    continue;
                }
                if (source.isEmpty()) {
                    return;
                }
                BlockPos pos = new BlockPos(cx + dx, cy, cz + dz);
                if (world.getBlockState(pos).canBeReplaced()) {
                    world.setBlock(pos, blockState, Block.UPDATE_ALL);
                    source.shrink(1);
                }
            }
        }
    }

    private int getRadius() {
        return 3;
    }

    @Override
    public String displayName() {
        return "Build: Square";
    }

    @Override
    public String description() {
        return "Builds a hollow square around the player";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.BUILD_SQUARE);
    }
}
