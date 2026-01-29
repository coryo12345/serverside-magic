package servermagic.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class SpellUtils {

    public static List<LivingEntity> getAllLivingEntitiesInLineOfSight(ServerPlayer player, double maxDistance) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 endPos = eyePos.add(lookVec.scale(maxDistance));

        AABB searchBox = player.getBoundingBox()
                .expandTowards(lookVec.scale(maxDistance))
                .inflate(1.0);

        List<LivingEntity> hitEntities = new ArrayList<>();

        // Get all entities in the expanded bounding box
        List<Entity> nearbyEntities = player.level().getEntities(
                player,
                searchBox,
                (entity) -> !entity.isSpectator() && entity.isPickable());

        // Check which ones the ray actually intersects
        for (Entity entity : nearbyEntities) {
            // we only care about mobs & players
            if (entity instanceof LivingEntity) {
                AABB entityBox = entity.getBoundingBox().inflate(0.3); // Slight expansion for hit detection
                Optional<Vec3> hit = entityBox.clip(eyePos, endPos);

                if (hit.isPresent()) {
                    hitEntities.add((LivingEntity) entity);
                }
            }
        }

        return hitEntities;
    }

    public static Optional<LivingEntity> getFirstEntityInLineOfSight(ServerPlayer player, double maxDistance) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 endPos = eyePos.add(lookVec.scale(maxDistance));

        AABB searchBox = player.getBoundingBox()
                .expandTowards(lookVec.scale(maxDistance))
                .inflate(1.0);

        EntityHitResult result = ProjectileUtil.getEntityHitResult(
                player,
                eyePos,
                endPos,
                searchBox,
                (entity) -> !entity.isSpectator() && entity.isPickable() && (entity instanceof LivingEntity),
                maxDistance * maxDistance);

        if (result == null) {
            return Optional.empty();
        }

        Entity e = result.getEntity();
        if (e instanceof LivingEntity) {
            return Optional.of((LivingEntity) e);
        }
        return Optional.empty();
    }

}
