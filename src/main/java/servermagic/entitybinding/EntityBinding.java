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

    public EntityBinding(UUID driverUUID, UUID followerUUID,
            Vec3 positionOffset, float yawOffset, float pitchOffset) {
        this.driverUUID = driverUUID;
        this.followerUUID = followerUUID;
        this.positionOffset = positionOffset;
        this.yawOffset = yawOffset;
        this.pitchOffset = pitchOffset;
    }

    // Convenience constructor — no offsets
    public EntityBinding(UUID driverUUID, UUID followerUUID) {
        this(driverUUID, followerUUID, Vec3.ZERO, 0f, 0f);
    }
}