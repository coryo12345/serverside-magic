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
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class ArrowVolley extends BaseSpell {

    private static final List<PendingVolley> pending = new ArrayList<>();

    public ArrowVolley(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Vec3 direction = player.getLookAngle();
        Vec3 eyePos = player.getEyePosition();

        world.sendParticles(ParticleTypes.CRIT, eyePos.x, eyePos.y, eyePos.z, 12, 0.3, 0.3, 0.3, 0.1);
        world.playSound(null, eyePos.x, eyePos.y, eyePos.z,
                SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 0.9F);

        pending.add(new PendingVolley(player.getUUID(), world, direction, 3));
    }

    public static void tick(MinecraftServer server) {
        Iterator<PendingVolley> iter = pending.iterator();
        while (iter.hasNext()) {
            PendingVolley volley = iter.next();
            if (volley.arrowsLeft <= 0) {
                iter.remove();
                continue;
            }
            if (volley.ticksUntilNext > 0) {
                volley.ticksUntilNext--;
                continue;
            }

            ServerPlayer player = (ServerPlayer) volley.level.getPlayerByUUID(volley.casterId);
            if (player != null && player.isAlive()) {
                fireArrow(volley.level, player, volley.direction);
            }

            volley.arrowsLeft--;
            volley.ticksUntilNext = 2;
        }
    }

    private static void fireArrow(ServerLevel level, ServerPlayer player, Vec3 dir) {
        Vec3 eyePos = player.getEyePosition();

        Arrow arrow = new Arrow(level, player, new ItemStack(Items.ARROW), null);
        arrow.setPos(eyePos.x, eyePos.y, eyePos.z);
        arrow.shoot(dir.x, dir.y, dir.z, 3.0F, 0.5F);
        level.addFreshEntity(arrow);

        level.sendParticles(ParticleTypes.CRIT, eyePos.x, eyePos.y, eyePos.z, 5, 0.15, 0.15, 0.15, 0.05);
        level.sendParticles(ParticleTypes.SWEEP_ATTACK, eyePos.x, eyePos.y, eyePos.z, 3, 0.2, 0.2, 0.2, 0.0);
        level.playSound(null, eyePos.x, eyePos.y, eyePos.z,
                SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 0.8F,
                1.0F + level.getRandom().nextFloat() * 0.15F);
    }

    @Override
    public int getFlatXpCost() {
        return 30;
    }

    @Override
    public double getLevelPercentCost() {
        return 0.5;
    }

    @Override
    public String displayName() {
        return "Arrow Volley";
    }

    @Override
    public String description() {
        return "Rapidly fire a volley of 3 arrows at your target";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.ARROW_VOLLEY);
    }

    private static class PendingVolley {
        final UUID casterId;
        final ServerLevel level;
        final Vec3 direction;
        int arrowsLeft;
        int ticksUntilNext;

        PendingVolley(UUID casterId, ServerLevel level, Vec3 direction, int arrowsLeft) {
            this.casterId = casterId;
            this.level = level;
            this.direction = direction;
            this.arrowsLeft = arrowsLeft;
            this.ticksUntilNext = 0;
        }
    }
}
