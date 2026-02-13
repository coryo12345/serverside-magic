package servermagic.spells;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import servermagic.spells.utils.SpellUtils;

public class ChainLightning extends BaseSpell {

    public ChainLightning(ServerLevel world, ServerPlayer player) {
        super(world, player);
    }

    @Override
    protected void spellImplementation() {
        List<LivingEntity> entities = SpellUtils.getAllLivingEntitiesInCone(player, 10, 30);

        // we need to build the path between each entity, as line segments
        List<Vec3> points = new ArrayList<>();
        points.add(player.getEyePosition(0));
        for (LivingEntity entity : entities) {
            points.add(entity.getEyePosition(0));
        }

        // spawn particles on the lines
        for (int i = 0; i < points.size() - 1; i++) {
            Vec3 startingPoint = points.get(i);
            Vec3 endingPoint = points.get(i + 1);
            Vec3 segment = startingPoint.vectorTo(endingPoint);
            double distance = startingPoint.distanceTo(endingPoint);
            int pointsBetweenCount = (int) Math.floor(distance / 0.25);
            Vec3 interval = new Vec3(segment.x / pointsBetweenCount, segment.y / pointsBetweenCount,
                    segment.z / pointsBetweenCount);
            for (int j = 1; j <= pointsBetweenCount; j++) {
                Vec3 addBy = interval.multiply(j, j, j);
                Vec3 particlePos = startingPoint.add(addBy);
                world.sendParticles(ParticleTypes.END_ROD, particlePos.x, particlePos.y, particlePos.z, 1, 0.1,
                        0.1, 0.1, 0);
            }
        }

        // Now do the damage
        DamageSource ds = player.damageSources().magic();
        for (LivingEntity entity : entities) {
            if (entity.isAttackable()) {
                entity.hurtServer(world, ds, 7);
            }
        }
    }

    @Override
    public String displayName() {
        return "Chain Lightning";
    }

    @Override
    public String description() {
        return "Strike the targets in front of you with arc lightning";
    }

}
