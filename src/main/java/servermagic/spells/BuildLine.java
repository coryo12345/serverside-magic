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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class BuildLine extends BaseSpell {

    public BuildLine(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        // Prefer offhand blocks, fall back to mainhand
        ItemStack offhand = player.getOffhandItem();
        ItemStack mainhand = player.getMainHandItem();

        ItemStack source;
        if (!offhand.isEmpty() && offhand.getItem() instanceof BlockItem) {
            source = offhand;
        } else if (!mainhand.isEmpty() && mainhand.getItem() instanceof BlockItem) {
            source = mainhand;
        } else {
            return;
        }

        // Raycast to find the targeted block
        HitResult hitResult = player.pick(5.0, 1.0F, false);
        if (!(hitResult instanceof BlockHitResult blockHit) || blockHit.getType() == HitResult.Type.MISS) {
            return;
        }

        // Start where the player would normally place a block (adjacent to the hit face)
        BlockPos startPos = blockHit.getBlockPos().relative(blockHit.getDirection());

        BlockPos dir = getLineDirection();
        BlockState blockState = ((BlockItem) source.getItem()).getBlock().defaultBlockState();

        int length = getLineLength();
        for (int i = 0; i < length; i++) {
            BlockPos placePos = startPos.offset(dir.getX() * i, dir.getY() * i, dir.getZ() * i);

            if (!world.getBlockState(placePos).canBeReplaced()) {
                break;
            }
            if (source.isEmpty()) {
                break;
            }

            world.setBlock(placePos, blockState, Block.UPDATE_ALL);
            source.shrink(1);
        }
    }

    private int getLineLength() {
        return 5;
    }

    private BlockPos getLineDirection() {
        Vec3 look = player.getViewVector(1.0F);
        double ax = Math.abs(look.x);
        double ay = Math.abs(look.y);
        double az = Math.abs(look.z);

        if (ax >= ay && ax >= az) {
            return new BlockPos(look.x > 0 ? 1 : -1, 0, 0);
        } else if (ay >= ax && ay >= az) {
            return new BlockPos(0, look.y > 0 ? 1 : -1, 0);
        } else {
            return new BlockPos(0, 0, look.z > 0 ? 1 : -1);
        }
    }

    @Override
    public String displayName() {
        return "Build: Line";
    }

    @Override
    public String description() {
        return "Builds in a line";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.BUILD_LINE);
    }
}
