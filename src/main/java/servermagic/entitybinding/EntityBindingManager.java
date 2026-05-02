package servermagic.entitybinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.phys.Vec3;

public class EntityBindingManager extends SavedData {

    // Codec for Vec3
    private static final Codec<Vec3> VEC3_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("x").forGetter(v -> v.x),
            Codec.DOUBLE.fieldOf("y").forGetter(v -> v.y),
            Codec.DOUBLE.fieldOf("z").forGetter(v -> v.z)).apply(instance, Vec3::new));

    // Codec for EntityBinding
    private static final Codec<EntityBinding> BINDING_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            com.mojang.serialization.Codec.STRING.xmap(UUID::fromString, UUID::toString)
                    .fieldOf("driver").forGetter(b -> b.driverUUID),
            com.mojang.serialization.Codec.STRING.xmap(UUID::fromString, UUID::toString)
                    .fieldOf("follower").forGetter(b -> b.followerUUID),
            VEC3_CODEC.fieldOf("position_offset").forGetter(b -> b.positionOffset),
            Codec.FLOAT.fieldOf("yaw_offset").forGetter(b -> b.yawOffset),
            Codec.FLOAT.fieldOf("pitch_offset").forGetter(b -> b.pitchOffset),
            Codec.BOOL.optionalFieldOf("lock_pitch", false).forGetter(b -> b.lockPitch))
            .apply(instance, EntityBinding::new));

    // Codec for the manager itself — just a list of bindings
    private static final Codec<EntityBindingManager> CODEC = BINDING_CODEC.listOf()
            .xmap(
                    list -> {
                        EntityBindingManager manager = new EntityBindingManager();
                        list.forEach(manager::addBinding);
                        return manager;
                    },
                    manager -> new ArrayList<>(manager.bindingsByFollower.values()));

    public static final SavedDataType<EntityBindingManager> TYPE = new SavedDataType<>(
            Identifier.parse("servermagic:entity_bindings"),
            EntityBindingManager::new,
            CODEC,
            DataFixTypes.LEVEL // closest generic type, or use LEVEL if available
    );

    // -------------------------------------------------------------------------
    // Storage maps
    // -------------------------------------------------------------------------

    private final Map<UUID, EntityBinding> bindingsByFollower = new HashMap<>();
    private final Map<UUID, Set<UUID>> followersByDriver = new HashMap<>();

    // -------------------------------------------------------------------------
    // Access
    // -------------------------------------------------------------------------

    private static final Map<ResourceKey<Level>, EntityBindingManager> CACHE = new HashMap<>();

    public static EntityBindingManager getOrCreate(ServerLevel level) {
        return CACHE.computeIfAbsent(level.dimension(), k -> {
            EntityBindingManager instance = level.getDataStorage().computeIfAbsent(TYPE);
            return instance;
        });
    }

    // Need to call this when the server stops to prevent stale data on world reload
    public static void clearCache() {
        CACHE.clear();
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public void addBinding(EntityBinding binding) {
        bindingsByFollower.put(binding.followerUUID, binding);
        followersByDriver
                .computeIfAbsent(binding.driverUUID, k -> new HashSet<>())
                .add(binding.followerUUID);
        setDirty();
    }

    public void removeBinding(UUID followerUUID) {
        EntityBinding removed = bindingsByFollower.remove(followerUUID);
        if (removed != null) {
            Set<UUID> followers = followersByDriver.get(removed.driverUUID);
            if (followers != null) {
                followers.remove(followerUUID);
                if (followers.isEmpty())
                    followersByDriver.remove(removed.driverUUID);
            }
        }
        setDirty();
    }

    public void removeAllBindingsForDriver(UUID driverUUID) {
        Set<UUID> followers = followersByDriver.remove(driverUUID);
        if (followers != null) {
            followers.forEach(bindingsByFollower::remove);
        }
        setDirty();
    }

    public EntityBinding getBindingForFollower(UUID followerUUID) {
        return bindingsByFollower.get(followerUUID);
    }

    public Collection<EntityBinding> getBindingsForDriver(UUID driverUUID) {
        Set<UUID> followers = followersByDriver.getOrDefault(driverUUID, Collections.emptySet());
        List<EntityBinding> result = new ArrayList<>();
        for (UUID f : followers) {
            EntityBinding b = bindingsByFollower.get(f);
            if (b != null)
                result.add(b);
        }
        return result;
    }

    public boolean hasBinding(UUID followerUUID) {
        return bindingsByFollower.containsKey(followerUUID);
    }

    public Collection<EntityBinding> getAllBindings() {
        return Collections.unmodifiableCollection(bindingsByFollower.values());
    }
}