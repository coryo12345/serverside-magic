package servermagic.spells;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.spells.utils.SpellUtils;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class SpectralGrasp extends BaseSpell {

    private static final int MAX_LIFT = 6;
    private static final int FREEZE_TICKS = 40; // 2 seconds

    private static final List<FrozenTarget> frozen = new ArrayList<>();

    public SpectralGrasp(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Optional<LivingEntity> targetOpt = SpellUtils.getFirstEntityInLineOfSight(player, 20.0);
        if (targetOpt.isEmpty()) {
            return;
        }

        LivingEntity target = targetOpt.get();

        // Don't double-freeze the same target
        if (frozen.stream().anyMatch(f -> f.targetId.equals(target.getUUID()))) {
            return;
        }

        Vec3 origin = target.position();
        int lift = calculateSafeLift(target);
        Vec3 frozenPos = new Vec3(origin.x, origin.y + lift, origin.z);

        // Departure burst at original position
        world.sendParticles(ParticleTypes.PORTAL,
                origin.x, origin.y + target.getBbHeight() * 0.5, origin.z,
                30, 0.4, 0.5, 0.4, 0.18);
        world.sendParticles(ParticleTypes.REVERSE_PORTAL,
                origin.x, origin.y + target.getBbHeight() * 0.5, origin.z,
                15, 0.3, 0.4, 0.3, 0.1);

        // Teleport up and suspend
        target.setPos(frozenPos.x, frozenPos.y, frozenPos.z);
        target.setDeltaMovement(Vec3.ZERO);
        target.setNoGravity(true);

        // Arrival burst at lifted position
        double midH = target.getBbHeight() * 0.5;
        world.sendParticles(ParticleTypes.ENCHANT,
                frozenPos.x, frozenPos.y + midH, frozenPos.z,
                25, 0.5, 0.4, 0.5, 0.18);
        world.sendParticles(ParticleTypes.END_ROD,
                frozenPos.x, frozenPos.y + midH, frozenPos.z,
                12, 0.4, 0.4, 0.4, 0.06);
        world.sendParticles(ParticleTypes.SOUL,
                frozenPos.x, frozenPos.y + midH, frozenPos.z,
                8, 0.3, 0.3, 0.3, 0.03);

        world.playSound(null, origin.x, origin.y, origin.z,
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 0.8F);

        frozen.add(new FrozenTarget(target.getUUID(), world, frozenPos,
                target.getBbWidth(), target.getBbHeight()));
    }

    /**
     * Finds the highest safe number of blocks to raise the entity, from MAX_LIFT
     * down to 0, checking that the entity's hitbox fits without colliding with blocks.
     */
    private int calculateSafeLift(LivingEntity target) {
        for (int lift = MAX_LIFT; lift >= 0; lift--) {
            AABB testAABB = target.getBoundingBox().move(0, lift, 0);
            if (world.noCollision(target, testAABB)) {
                return lift;
            }
        }
        return 0;
    }

    public static void tick(MinecraftServer server) {
        Iterator<FrozenTarget> iter = frozen.iterator();
        while (iter.hasNext()) {
            FrozenTarget entry = iter.next();
            entry.ticksAlive++;

            Entity entity = entry.level.getEntity(entry.targetId);

            if (entity == null || !entity.isAlive() || entry.ticksAlive > FREEZE_TICKS) {
                // Release: restore gravity so the entity falls naturally
                if (entity != null && entity.isAlive()) {
                    entity.setNoGravity(false);
                    Vec3 pos = entity.position();
                    entry.level.sendParticles(ParticleTypes.SCULK_SOUL,
                            pos.x, pos.y + entry.bbHeight * 0.5, pos.z,
                            14, 0.4, 0.4, 0.4, 0.08);
                    entry.level.playSound(null, pos.x, pos.y, pos.z,
                            SoundEvents.WIND_CHARGE_BURST, SoundSource.PLAYERS, 0.7F, 1.3F);
                }
                iter.remove();
                continue;
            }

            // Hold entity in place each tick — overrides AI velocity and gravity
            entity.setPos(entry.frozenPos.x, entry.frozenPos.y, entry.frozenPos.z);
            entity.setDeltaMovement(Vec3.ZERO);

            // Ambient soul particles rising through the entity
            Vec3 pos = entry.frozenPos;
            if (entry.ticksAlive % 3 == 0) {
                entry.level.sendParticles(ParticleTypes.SOUL,
                        pos.x, pos.y + entry.bbHeight * 0.2, pos.z,
                        2, entry.bbWidth * 0.3, entry.bbHeight * 0.4, entry.bbWidth * 0.3, 0.01);
            }

            // Orbiting END_ROD ring around the entity
            double angle = entry.ticksAlive * 0.25;
            double r = entry.bbWidth * 0.5 + 0.6;
            double rx = Math.cos(angle) * r;
            double rz = Math.sin(angle) * r;
            double orbitY = pos.y + entry.bbHeight * 0.5;
            entry.level.sendParticles(ParticleTypes.END_ROD,
                    pos.x + rx, orbitY, pos.z + rz, 1, 0, 0, 0, 0);
            entry.level.sendParticles(ParticleTypes.END_ROD,
                    pos.x - rx, orbitY, pos.z - rz, 1, 0, 0, 0, 0);
        }
    }

    @Override
    public int cost() {
        return 4;
    }

    @Override
    public String displayName() {
        return "Spectral Grasp";
    }

    @Override
    public String description() {
        return "Lift a target mob into the air with a spectral force, suspending them before they fall";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.SPECTRAL_GRASP);
    }

    // -------------------------------------------------------------------------

    private static class FrozenTarget {
        final UUID targetId;
        final ServerLevel level;
        final Vec3 frozenPos;
        final double bbWidth;
        final double bbHeight;
        int ticksAlive = 0;

        FrozenTarget(UUID targetId, ServerLevel level, Vec3 frozenPos, double bbWidth, double bbHeight) {
            this.targetId = targetId;
            this.level = level;
            this.frozenPos = frozenPos;
            this.bbWidth = bbWidth;
            this.bbHeight = bbHeight;
        }
    }
}
