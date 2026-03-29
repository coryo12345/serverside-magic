package servermagic.entitybinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class EntityBindingLifecycleHandler {
    public static void onEntityUnload(Entity entity, ServerLevel level) {
        // Only care about actual removal, not chunk unloads
        if (!entity.isRemoved())
            return;

        EntityBindingManager manager = EntityBindingManager.getOrCreate(level);
        UUID entityId = entity.getUUID();

        // Case 1: This entity is a FOLLOWER — remove it from its binding,
        // and kill the driver too
        EntityBinding asFollower = manager.getBindingForFollower(entityId);
        if (asFollower != null) {
            manager.removeBinding(entityId);
            Entity driver = level.getEntity(asFollower.driverUUID);
            if (driver != null && !driver.isRemoved()) {
                driver.discard(); // discard = remove without drop/death effects
            }
        }

        // Case 2: This entity is a DRIVER — kill all followers (which will
        // then re-trigger this event for each follower, handling any chains)
        Collection<EntityBinding> asDriver = manager.getBindingsForDriver(entityId);
        if (!asDriver.isEmpty()) {
            // Snapshot the list first to avoid CME as removals cascade
            List<EntityBinding> snapshot = new ArrayList<>(asDriver);
            manager.removeAllBindingsForDriver(entityId);
            for (EntityBinding binding : snapshot) {
                Entity follower = level.getEntity(binding.followerUUID);
                if (follower != null && !follower.isRemoved()) {
                    follower.discard();
                }
            }
        }
    }
}