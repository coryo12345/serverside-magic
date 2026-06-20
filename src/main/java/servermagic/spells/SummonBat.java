package servermagic.spells;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.entities.Entities;
import servermagic.entities.TestBatEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class SummonBat extends BaseSpell {

    public SummonBat(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        TestBatEntity bat = new TestBatEntity(Entities.TEST_BAT, world);
        Vec3 pos = player.position().add(player.getLookAngle().scale(3));
        bat.setPos(pos.x, pos.y, pos.z);
        bat.setYRot(player.getYRot());
        world.addFreshEntity(bat);
        bat.getHolder().getAnimator().playAnimation("idle");
    }

    @Override
    public String displayName() {
        return "Summon Bat";
    }

    @Override
    public String description() {
        return "Summons a bat creature. Punch it to dismiss.";
    }

    @Override
    public int getFlatXpCost() {
        return 5;
    }
}
