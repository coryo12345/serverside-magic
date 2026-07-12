package servermagic.spells;

import java.lang.reflect.Method;
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
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.decoration.Mannequin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.phys.Vec3;
import servermagic.ServerMagic;
import servermagic.db.Database;
import servermagic.entitybinding.EntityBindingUtil;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class ShadowClone extends BaseSpell {

    private static final String WOLF_TAG = "shadow-clone-wolf";
    private static final String MANNEQUIN_TAG = "shadow-clone-mannequin";
    private static final int DURATION = 600; // 30 seconds
    private static final List<ActiveClone> active = new ArrayList<>();

    public ShadowClone(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        UUID casterId = player.getUUID();
        dismissClone(casterId);

        Vec3 spawnPos = player.position();

        // Wolf is the invisible AI driver: follows player, attacks player's enemies
        Wolf wolf = EntityType.WOLF.create(world, EntitySpawnReason.MOB_SUMMONED);
        wolf.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
        wolf.setOwner(player);
        wolf.setTame(true, false);
        wolf.setHealth(20.0f);
        world.addFreshEntity(wolf);
        // Tags must be added after addFreshEntity — ENTITY_LOAD fires on add and would
        // immediately discard any entity that already has our cleanup tag.
        wolf.addTag(WOLF_TAG);
        wolf.addTag(casterId.toString());

        // Mannequin is the visible follower: shows player skin, holds sword
        Mannequin mannequin = EntityType.MANNEQUIN.create(world, EntitySpawnReason.MOB_SUMMONED);
        mannequin.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

        try {
            Method setProfile = Mannequin.class.getDeclaredMethod("setProfile", ResolvableProfile.class);
            setProfile.setAccessible(true);
            setProfile.invoke(mannequin, ResolvableProfile.createResolved(player.getGameProfile()));
        } catch (Exception e) {
            ServerMagic.LOGGER.warn("ShadowClone: could not set mannequin profile via reflection", e);
        }

        // Fresh stone sword — never copied from player inventory, drops nothing on death
        mannequin.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
        world.addFreshEntity(mannequin);
        mannequin.addTag(MANNEQUIN_TAG);

        // Bind mannequin (follower) to wolf (driver); wolf becomes invisible+silent automatically
        EntityBindingUtil.bindLevelFollower(world, wolf, mannequin, Vec3.ZERO, 0f);

        active.add(new ActiveClone(wolf.getUUID(), mannequin.getUUID(), casterId, world));

        world.sendParticles(ParticleTypes.LARGE_SMOKE,
                spawnPos.x, spawnPos.y + 1.0, spawnPos.z, 20, 0.5, 0.8, 0.5, 0.04);
        world.sendParticles(ParticleTypes.REVERSE_PORTAL,
                spawnPos.x, spawnPos.y + 1.0, spawnPos.z, 35, 0.5, 0.9, 0.5, 0.1);
        world.sendParticles(ParticleTypes.SCULK_SOUL,
                spawnPos.x, spawnPos.y + 1.0, spawnPos.z, 12, 0.4, 0.6, 0.4, 0.06);
        world.playSound(null, spawnPos.x, spawnPos.y, spawnPos.z,
                SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 0.85F);
    }

    public static void dismissClone(UUID casterId) {
        Iterator<ActiveClone> iter = active.iterator();
        while (iter.hasNext()) {
            ActiveClone clone = iter.next();
            if (clone.casterId.equals(casterId)) {
                Entity wolf = clone.level.getEntity(clone.wolfId);
                if (wolf != null && wolf.isAlive()) {
                    Vec3 pos = wolf.position();
                    clone.level.sendParticles(ParticleTypes.LARGE_SMOKE,
                            pos.x, pos.y + 1.0, pos.z, 12, 0.4, 0.6, 0.4, 0.06);
                    clone.level.sendParticles(ParticleTypes.REVERSE_PORTAL,
                            pos.x, pos.y + 1.0, pos.z, 20, 0.4, 0.7, 0.4, 0.08);
                    wolf.discard(); // EntityBindingLifecycleHandler auto-discards the mannequin
                }
                iter.remove();
                return;
            }
        }
    }

    public static void tick(MinecraftServer server) {
        Iterator<ActiveClone> iter = active.iterator();
        while (iter.hasNext()) {
            ActiveClone clone = iter.next();
            Entity wolf = clone.level.getEntity(clone.wolfId);

            if (wolf == null || !wolf.isAlive()) {
                iter.remove();
                continue;
            }

            clone.ticksAlive++;

            if (clone.ticksAlive >= DURATION) {
                Vec3 pos = wolf.position();
                clone.level.sendParticles(ParticleTypes.LARGE_SMOKE,
                        pos.x, pos.y + 1.0, pos.z, 12, 0.4, 0.6, 0.4, 0.06);
                clone.level.sendParticles(ParticleTypes.REVERSE_PORTAL,
                        pos.x, pos.y + 1.0, pos.z, 20, 0.4, 0.7, 0.4, 0.08);
                wolf.discard();
                iter.remove();
                continue;
            }

            if (clone.ticksAlive % 15 == 0) {
                Vec3 pos = wolf.position();
                clone.level.sendParticles(ParticleTypes.REVERSE_PORTAL,
                        pos.x, pos.y + 1.0, pos.z, 3, 0.3, 0.6, 0.3, 0.04);
                clone.level.sendParticles(ParticleTypes.LARGE_SMOKE,
                        pos.x, pos.y + 0.5, pos.z, 1, 0.2, 0.3, 0.2, 0.01);
            }
        }
    }

    public static void onServerStopping() {
        for (ActiveClone clone : active) {
            Entity wolf = clone.level.getEntity(clone.wolfId);
            if (wolf != null) wolf.discard();
            Entity mannequin = clone.level.getEntity(clone.mannequinId);
            if (mannequin != null) mannequin.discard();
        }
        active.clear();
    }

    @Override
    public int getFlatXpCost() {
        return 50;
    }

    @Override
    public double getLevelPercentCost() {
        return 0.75;
    }

    @Override
    public String displayName() {
        return "Shadow Clone";
    }

    @Override
    public String description() {
        return "Summon a shadow clone wearing your skin to fight alongside you. Lasts 30 seconds, or cast again to dismiss it.";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.SHADOW_CLONE);
    }

    private static class ActiveClone {
        final UUID wolfId;
        final UUID mannequinId;
        final UUID casterId;
        final ServerLevel level;
        int ticksAlive = 0;

        ActiveClone(UUID wolfId, UUID mannequinId, UUID casterId, ServerLevel level) {
            this.wolfId = wolfId;
            this.mannequinId = mannequinId;
            this.casterId = casterId;
            this.level = level;
        }
    }
}
