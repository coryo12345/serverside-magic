package servermagic.spells;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mojang.math.Transformation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import servermagic.db.Database;
import servermagic.mixin.DisplayPosInterpolationAccessor;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class FloatingShield extends BaseSpell {

    private static final int DURATION = 1200; // 1 minute
    private static final int CHARGES = 3;
    private static final double ORBIT_RADIUS = 1.5;

    private static final List<ActiveShield> active = new ArrayList<>();

    public FloatingShield(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        UUID casterId = player.getUUID();

        // Remove any existing shield (re-cast resets it)
        removeShield(casterId, true);

        ItemStack displayItem = pickDisplayItem();

        List<UUID> entityIds = new ArrayList<>();
        for (int i = 0; i < CHARGES; i++) {
            double angle = Math.toRadians(i * 120.0);
            double x = player.getX() + Math.cos(angle) * ORBIT_RADIUS;
            double z = player.getZ() + Math.sin(angle) * ORBIT_RADIUS;
            double y = player.getY() + 1.0;

            Display.ItemDisplay display = new Display.ItemDisplay(EntityType.ITEM_DISPLAY, world);
            display.setPos(x, y, z);
            display.setItemStack(displayItem.copy());
            display.setNoGravity(true);
            display.setInvulnerable(true);
            display.setTransformation(new Transformation(
                    new Vector3f(0, 0, 0),
                    new Quaternionf().rotateX(0.3f),
                    new Vector3f(0.6f, 0.6f, 0.6f),
                    new Quaternionf()
            ));
            ((DisplayPosInterpolationAccessor) display).invokeSetPosRotInterpolationDuration(1);
            world.addFreshEntity(display);
            display.addTag("servermagic:floating_shield");
            entityIds.add(display.getUUID());
        }

        active.add(new ActiveShield(casterId, world, entityIds));

        double px = player.getX(), py = player.getY(), pz = player.getZ();
        world.sendParticles(ParticleTypes.ENCHANT, px, py + 1.0, pz, 60, 1.2, 0.8, 1.2, 0.3);
        world.sendParticles(ParticleTypes.END_ROD, px, py + 1.0, pz, 20, 0.8, 0.6, 0.8, 0.1);
        world.playSound(null, px, py, pz, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 0.8F, 1.2F);
    }

    private ItemStack pickDisplayItem() {
        ItemStack offhand = player.getOffhandItem();
        if (!offhand.isEmpty()) {
            return offhand.copy();
        }
        List<Integer> occupied = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (!player.getInventory().getItem(i).isEmpty()) {
                occupied.add(i);
            }
        }
        if (occupied.isEmpty()) {
            return new ItemStack(Items.SHIELD);
        }
        int slot = occupied.get(player.getRandom().nextInt(occupied.size()));
        return player.getInventory().getItem(slot).copy();
    }

    public static void tick(MinecraftServer server) {
        Iterator<ActiveShield> iter = active.iterator();
        while (iter.hasNext()) {
            ActiveShield shield = iter.next();
            shield.ticksAlive++;

            if (shield.displayEntityIds.isEmpty() || shield.ticksAlive > DURATION) {
                removeShieldEntities(shield, true);
                iter.remove();
                continue;
            }

            ServerPlayer player = server.getPlayerList().getPlayer(shield.casterId);
            if (player == null || player.level() != shield.level) continue;

            tickShield(shield, player);
        }
    }

    private static void tickShield(ActiveShield shield, ServerPlayer player) {
        shield.angle += 4.0;
        double y = player.getY() + 1.0;
        int i = 0;
        for (UUID id : shield.displayEntityIds) {
            double angle = Math.toRadians(shield.angle + i * 120.0);
            double x = player.getX() + Math.cos(angle) * ORBIT_RADIUS;
            double z = player.getZ() + Math.sin(angle) * ORBIT_RADIUS;
            Entity entity = shield.level.getEntity(id);
            if (entity != null && entity.isAlive()) {
                entity.teleportTo(shield.level, x, y, z, java.util.Set.of(),
                        entity.getYRot(), entity.getXRot(), true);
            }
            i++;
        }

        // Ambient particle trail every 10 ticks
        if (shield.ticksAlive % 10 == 0) {
            for (int j = 0; j < shield.displayEntityIds.size(); j++) {
                double angle = Math.toRadians(shield.angle + j * 120.0);
                double px = player.getX() + Math.cos(angle) * ORBIT_RADIUS;
                double pz = player.getZ() + Math.sin(angle) * ORBIT_RADIUS;
                shield.level.sendParticles(ParticleTypes.CRIT, px, player.getY() + 1.0, pz, 3, 0.15, 0.15, 0.15, 0.04);
            }
        }
    }

    public static boolean tryAbsorbHit(ServerPlayer player) {
        ActiveShield found = null;
        for (ActiveShield shield : active) {
            if (shield.casterId.equals(player.getUUID()) && !shield.displayEntityIds.isEmpty()) {
                found = shield;
                break;
            }
        }
        if (found == null) return false;

        UUID consumedId = found.displayEntityIds.remove(found.displayEntityIds.size() - 1);
        Entity entity = found.level.getEntity(consumedId);
        if (entity != null) entity.discard();

        double px = player.getX(), py = player.getY(), pz = player.getZ();
        found.level.sendParticles(ParticleTypes.CRIT, px, py + 1.0, pz, 25, 0.7, 0.6, 0.7, 0.12);

        if (found.displayEntityIds.isEmpty()) {
            active.remove(found);
            found.level.sendParticles(ParticleTypes.EXPLOSION, px, py + 1.0, pz, 6, 0.5, 0.5, 0.5, 0.0);
            found.level.sendParticles(ParticleTypes.ENCHANT, px, py + 1.0, pz, 30, 1.0, 0.8, 1.0, 0.2);
            found.level.playSound(null, px, py, pz, SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 1.0F, 0.7F);
        } else {
            found.level.playSound(null, px, py, pz, SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.1F);
        }
        return true;
    }

    public static void onPlayerDisconnect(ServerPlayer player) {
        removeShield(player.getUUID(), false);
    }

    public static void onServerStopping() {
        for (ActiveShield shield : active) {
            removeShieldEntities(shield, false);
        }
        active.clear();
    }

    private static void removeShield(UUID casterId, boolean withEffect) {
        Iterator<ActiveShield> iter = active.iterator();
        while (iter.hasNext()) {
            ActiveShield shield = iter.next();
            if (shield.casterId.equals(casterId)) {
                removeShieldEntities(shield, withEffect);
                iter.remove();
                return;
            }
        }
    }

    private static void removeShieldEntities(ActiveShield shield, boolean withEffect) {
        for (UUID id : shield.displayEntityIds) {
            Entity entity = shield.level.getEntity(id);
            if (entity != null) {
                if (withEffect) {
                    shield.level.sendParticles(ParticleTypes.POOF,
                            entity.getX(), entity.getY(), entity.getZ(), 8, 0.2, 0.2, 0.2, 0.05);
                }
                entity.discard();
            }
        }
    }

    @Override
    public int getFlatXpCost() {
        return 25;
    }

    @Override
    public double getLevelPercentCost() {
        return 0.6;
    }

    @Override
    public String displayName() {
        return "Floating Shield";
    }

    @Override
    public String description() {
        return "Summon 3 orbiting item shields. Each shield absorbs one hit. Lasts up to 1 minute.";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.FLOATING_SHIELD);
    }

    private static class ActiveShield {
        final UUID casterId;
        final ServerLevel level;
        final List<UUID> displayEntityIds;
        int ticksAlive = 0;
        double angle = 0.0;

        ActiveShield(UUID casterId, ServerLevel level, List<UUID> displayEntityIds) {
            this.casterId = casterId;
            this.level = level;
            this.displayEntityIds = new ArrayList<>(displayEntityIds);
        }
    }
}
