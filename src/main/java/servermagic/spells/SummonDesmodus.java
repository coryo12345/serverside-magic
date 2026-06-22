package servermagic.spells;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.entities.DesmodusEntity;
import servermagic.entities.Entities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class SummonDesmodus extends BaseSpell {

    public SummonDesmodus(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        DesmodusEntity desmodus = new DesmodusEntity(Entities.DESMODUS, world);
        Vec3 look = player.getLookAngle();
        Vec3 horizontal = new Vec3(look.x, 0, look.z).normalize().scale(3);
        Vec3 pos = player.position().add(horizontal);
        desmodus.setPos(pos.x, pos.y, pos.z);
        desmodus.setYRot(player.getYRot());
        world.addFreshEntity(desmodus);
        desmodus.getHolder().getAnimator().playAnimation("idle_ground");
    }

    @Override
    public String displayName() {
        return "Summon Desmodus";
    }

    @Override
    public String description() {
        return "Summons a vampire bat. Right-click to mount and fly. Punch to dismiss.";
    }

    @Override
    public int getFlatXpCost() {
        return 8;
    }
}
