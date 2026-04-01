package servermagic.entitybinding;

import net.minecraft.world.phys.Vec3;
import java.util.UUID;

public class EntityBinding {

    public final UUID driverUUID;
    public final UUID followerUUID;

    // Positional offset in driver-local space (applied after rotation)
    public final Vec3 positionOffset;

    // Rotational offsets in degrees
    public final float yawOffset;
    public final float pitchOffset;

    // When true, follower pitch is always 0 (ignores driver pitch entirely)
    public final boolean lockPitch;

    public EntityBinding(UUID driverUUID, UUID followerUUID,
            Vec3 positionOffset, float yawOffset, float pitchOffset, boolean lockPitch) {
        this.driverUUID = driverUUID;
        this.followerUUID = followerUUID;
        this.positionOffset = positionOffset;
        this.yawOffset = yawOffset;
        this.pitchOffset = pitchOffset;
        this.lockPitch = lockPitch;
    }

    // Convenience constructor — no offsets, pitch not locked
    public EntityBinding(UUID driverUUID, UUID followerUUID,
            Vec3 positionOffset, float yawOffset, float pitchOffset) {
        this(driverUUID, followerUUID, positionOffset, yawOffset, pitchOffset, false);
    }

    // Convenience constructor — no offsets
    public EntityBinding(UUID driverUUID, UUID followerUUID) {
        this(driverUUID, followerUUID, Vec3.ZERO, 0f, 0f, false);
    }
}