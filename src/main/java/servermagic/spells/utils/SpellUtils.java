package servermagic.spells.utils;

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

    public static List<LivingEntity> getAllLivingEntitiesInCone(ServerPlayer player, double maxDistance,
            double halfAngleDegrees) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);

        // Precompute the cosine threshold — dot product must exceed this
        double cosThreshold = Math.cos(Math.toRadians(halfAngleDegrees));

        // Same broad-phase AABB as your original to cheaply cull distant entities
        AABB searchBox = player.getBoundingBox()
                .expandTowards(lookVec.scale(maxDistance))
                .inflate(maxDistance); // inflate by full distance so side angles are included

        List<LivingEntity> hitEntities = new ArrayList<>();

        List<Entity> nearbyEntities = player.level().getEntities(
                player,
                searchBox,
                (entity) -> !entity.isSpectator() && entity.isPickable());

        for (Entity entity : nearbyEntities) {
            if (!(entity instanceof LivingEntity living))
                continue;

            // Vector from eye to the center of the entity
            Vec3 toEntity = entity.getBoundingBox().getCenter().subtract(eyePos);
            double distance = toEntity.length();

            if (distance > maxDistance)
                continue;

            // Normalize and dot with look direction
            double dot = toEntity.normalize().dot(lookVec);

            // dot > cosThreshold means the angle is within the cone
            if (dot >= cosThreshold) {
                hitEntities.add(living);
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
