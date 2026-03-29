package servermagic.entitybinding;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public final class EntityBindingUtil {

    private EntityBindingUtil() {
    }

    /**
     * Bind follower to driver with no offsets.
     * The follower will exactly mirror the driver's position and rotation every
     * tick.
     */
    public static void bind(ServerLevel level, Entity driver, Entity follower) {
        bind(level, driver, follower, Vec3.ZERO, 0f, 0f);
    }

    /**
     * Bind follower to driver with positional and rotational offsets.
     *
     * @param positionOffset offset in driver-local space (x=right, y=up, z=forward)
     * @param yawOffset      added to the driver's yaw before applying to follower
     * @param pitchOffset    added to the driver's pitch before applying to follower
     */
    public static void bind(ServerLevel level, Entity driver, Entity follower,
            Vec3 positionOffset, float yawOffset, float pitchOffset) {
        // Make the driver invisible and silent

        driver.setInvisible(true);
        driver.setSilent(true);

        EntityBinding binding = new EntityBinding(
                driver.getUUID(),
                follower.getUUID(),
                positionOffset,
                yawOffset,
                pitchOffset);
        EntityBindingManager.getOrCreate(level).addBinding(binding);
    }

    /**
     * Unbind a specific follower. Does NOT remove the entities themselves.
     */
    public static void unbindFollower(ServerLevel level, Entity follower) {
        EntityBindingManager.getOrCreate(level).removeBinding(follower.getUUID());
    }

    /**
     * Unbind all followers attached to a driver.
     */
    public static void unbindAllForDriver(ServerLevel level, Entity driver) {
        EntityBindingManager.getOrCreate(level).removeAllBindingsForDriver(driver.getUUID());
    }

    /**
     * Returns true if this entity is currently a follower in any binding.
     */
    public static boolean isBound(ServerLevel level, Entity entity) {
        return EntityBindingManager.getOrCreate(level).hasBinding(entity.getUUID());
    }
}