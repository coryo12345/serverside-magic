package servermagic.entitybinding;

import java.util.Set;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class EntityBindingTickHandler {

    /**
     * Call this once per server tick, e.g. from a ServerTickEvents.END_SERVER_TICK
     * listener.
     */
    public static void tick(MinecraftServer server) {
        for (ServerLevel level : server.getAllLevels()) {
            tickLevel(level);
        }
    }

    private static void tickLevel(ServerLevel level) {
        EntityBindingManager manager = EntityBindingManager.getOrCreate(level);

        for (EntityBinding binding : manager.getAllBindings()) {
            Entity driver = level.getEntity(binding.driverUUID);
            Entity follower = level.getEntity(binding.followerUUID);

            // Skip this tick if either entity isn't loaded, but don't remove the binding
            if (driver == null || !driver.isAlive()
                    || follower == null || !follower.isAlive()) {
                continue;
            }

            applyBinding(driver, follower, binding);
        }
    }

    private static void applyBinding(Entity driver, Entity follower, EntityBinding binding) {
        float newYaw = driver.getYRot() + binding.yawOffset;
        float newPitch = driver.getXRot() + binding.pitchOffset;

        Vec3 targetPos;

        if (binding.positionOffset.equals(Vec3.ZERO)) {
            targetPos = driver.position();
        } else {
            // Rotate the offset vector to align with the driver's yaw
            // so "forward" always means in front of the driver
            targetPos = offsetInDriverSpace(driver.position(), newYaw, binding.positionOffset);
        }

        // Teleport without dismounting passengers
        follower.teleportTo(
                (net.minecraft.server.level.ServerLevel) follower.level(),
                targetPos.x, targetPos.y, targetPos.z,
                Set.of(),
                newYaw,
                newPitch,
                true // keepPassengers / recheck portal
        );

        // Sync rotation fields directly — teleportTo handles most of it
        // but setting these ensures no one-tick lag on the server side
        follower.setYRot(newYaw);
        follower.setXRot(newPitch);
        follower.yRotO = newYaw;
        follower.xRotO = newPitch;

        // Kill any residual velocity so the follower doesn't drift
        follower.setDeltaMovement(Vec3.ZERO);
    }

    /**
     * Rotates a local-space offset vector by the driver's yaw and adds it to the
     * base position.
     * This means (1, 0, 0) is always "to the right of the driver", etc.
     */
    private static Vec3 offsetInDriverSpace(Vec3 base, float yawDegrees, Vec3 offset) {
        double yawRad = Math.toRadians(yawDegrees);
        double cos = Math.cos(yawRad);
        double sin = Math.sin(yawRad);

        // Standard 2D rotation in the XZ plane
        double worldX = offset.x * cos - offset.z * sin;
        double worldZ = offset.x * sin + offset.z * cos;

        return base.add(worldX, offset.y, worldZ);
    }
}