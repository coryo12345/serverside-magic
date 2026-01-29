package mcservermagic.spells;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;

public class LightningOnTarget extends BaseSpell {

    public LightningOnTarget(ServerLevel world, ServerPlayer player) {
        super(world, player);
    }

    @Override
    protected void spellImplementation() {
        Optional<LivingEntity> target = SpellUtils.getFirstEntityInLineOfSight(player, 10);
        if (target.isEmpty())
            return;
        LightningBolt lb = EntityType.LIGHTNING_BOLT.create(world, EntitySpawnReason.COMMAND);
        lb.moveOrInterpolateTo(target.get().position());
        world.addFreshEntity(lb);
    }

}
