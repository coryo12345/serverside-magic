package servermagic.spells;

import java.util.ArrayList;
import java.util.Comparator;
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
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class BeeSwarm extends BaseSpell {

    private static final int BEE_COUNT = 5;
    private static final int BEE_DURATION = 160; // 8 seconds max lifetime
    private static final double SEARCH_RADIUS = 20.0;
    private static final double SPAWN_RADIUS = 1.2;
    private static final String BEE_TAG = "bee_swarm";

    private static final List<ActiveBee> active = new ArrayList<>();

    public BeeSwarm(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        LivingEntity target = findNearestTarget();

        Vec3 pos = player.position();

        // Summoning burst at player
        world.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                pos.x, pos.y + 1.0, pos.z, 30, 0.8, 0.5, 0.8, 0.18);
        world.sendParticles(ParticleTypes.POOF,
                pos.x, pos.y + 1.0, pos.z, 15, 0.6, 0.4, 0.6, 0.1);
        world.sendParticles(ParticleTypes.DRIPPING_HONEY,
                pos.x, pos.y + 1.5, pos.z, 20, 0.5, 0.3, 0.5, 0.04);

        world.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.BEE_LOOP_AGGRESSIVE, SoundSource.PLAYERS, 1.6F, 0.9F);
        world.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 0.5F, 1.3F);

        for (int i = 0; i < BEE_COUNT; i++) {
            double angle = (Math.PI * 2.0 / BEE_COUNT) * i;
            double spawnX = pos.x + Math.cos(angle) * SPAWN_RADIUS;
            double spawnZ = pos.z + Math.sin(angle) * SPAWN_RADIUS;
            double spawnY = pos.y + 0.5;

            Bee bee = new Bee(EntityType.BEE, world);
            bee.setPos(spawnX, spawnY, spawnZ);
            bee.addTag(BEE_TAG);

            if (target != null) {
                bee.setTarget(target);
                bee.setPersistentAngerEndTime(world.getGameTime() + BEE_DURATION + 50);
                bee.setPersistentAngerTarget(EntityReference.of(target.getUUID()));
            }

            world.addFreshEntity(bee);
            active.add(new ActiveBee(bee.getUUID(), player.getUUID(), world));

            // Per-bee spawn sparkle
            world.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                    spawnX, spawnY + 0.5, spawnZ, 4, 0.2, 0.2, 0.2, 0.08);
        }
    }

    private LivingEntity findNearestTarget() {
        Vec3 pos = player.position();
        AABB searchBox = AABB.ofSize(pos, SEARCH_RADIUS * 2, SEARCH_RADIUS * 2, SEARCH_RADIUS * 2);

        // Priority 1: hostile mobs
        List<Monster> hostiles = world.getEntitiesOfClass(Monster.class, searchBox,
                e -> e.isAlive() && e.distanceTo(player) <= SEARCH_RADIUS);
        if (!hostiles.isEmpty()) {
            return hostiles.stream()
                    .min(Comparator.comparingDouble(e -> e.distanceTo(player)))
                    .orElse(null);
        }

        // Priority 2: other players
        List<ServerPlayer> otherPlayers = world.getEntitiesOfClass(ServerPlayer.class, searchBox,
                e -> e.isAlive() && !e.getUUID().equals(player.getUUID())
                        && e.distanceTo(player) <= SEARCH_RADIUS);
        if (!otherPlayers.isEmpty()) {
            return otherPlayers.stream()
                    .min(Comparator.comparingDouble(e -> e.distanceTo(player)))
                    .orElse(null);
        }

        // Priority 3: passive mobs (excluding our own bees)
        List<Animal> animals = world.getEntitiesOfClass(Animal.class, searchBox,
                e -> e.isAlive() && !e.getTags().contains(BEE_TAG)
                        && e.distanceTo(player) <= SEARCH_RADIUS);
        if (!animals.isEmpty()) {
            return animals.stream()
                    .min(Comparator.comparingDouble(e -> e.distanceTo(player)))
                    .orElse(null);
        }

        return null;
    }

    public static void tick(MinecraftServer server) {
        Iterator<ActiveBee> iter = active.iterator();
        while (iter.hasNext()) {
            ActiveBee entry = iter.next();
            entry.ticksAlive++;

            Entity entity = entry.level.getEntity(entry.beeId);

            if (entity == null || !entity.isAlive() || entry.ticksAlive > BEE_DURATION) {
                if (entity != null && entity.isAlive()) {
                    Vec3 pos = entity.position();
                    entry.level.sendParticles(ParticleTypes.POOF,
                            pos.x, pos.y + 0.5, pos.z, 5, 0.3, 0.3, 0.3, 0.06);
                    entity.discard();
                }
                iter.remove();
                continue;
            }

            // Ambient honey-glow particles while the bees are active
            if (entry.ticksAlive % 12 == 0) {
                Vec3 pos = entity.position();
                entry.level.sendParticles(ParticleTypes.DRIPPING_HONEY,
                        pos.x, pos.y + 0.8, pos.z, 1, 0.15, 0.15, 0.15, 0.0);
            }
        }
    }

    @Override
    public int cost() {
        return 5;
    }

    @Override
    public String displayName() {
        return "Bee Swarm";
    }

    @Override
    public String description() {
        return "Summon a swarm of bees to attack the nearest enemy";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.BEE_SWARM);
    }

    // -------------------------------------------------------------------------

    private static class ActiveBee {
        final UUID beeId;
        final UUID casterId;
        final ServerLevel level;
        int ticksAlive = 0;

        ActiveBee(UUID beeId, UUID casterId, ServerLevel level) {
            this.beeId = beeId;
            this.casterId = casterId;
            this.level = level;
        }
    }
}
