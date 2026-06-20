package servermagic.spells;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.db.tables.PlayerSpellConfig;
import servermagic.web.skill.Skill;
import servermagic.web.skill.SkillGranter;
import servermagic.web.skill.Skills;

public class SetGhostTool extends BaseSpell {

    static final String SPELL_CONFIG_KEY = "GhostTool";
    static final Gson GSON = new Gson();

    private static final Map<UUID, Long> pendingItemInVoid = new ConcurrentHashMap<>();
    private static final Map<UUID, Long> pendingPlayerInVoid = new ConcurrentHashMap<>();
    private static final long ITEM_TIMEOUT_TICKS = 60;
    private static final long SURVIVE_TIMEOUT_TICKS = 200;

    public SetGhostTool(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        ItemStack target = player.getOffhandItem();
        if (target.isEmpty()) {
            player.sendSystemMessage(Component.literal("Hold an item in your offhand to store it."));
            return;
        }
        if (target.getMaxStackSize() > 1) {
            player.sendSystemMessage(Component.literal("Only tools and equipment can be stored as a ghost tool."));
            return;
        }
        if (target.has(DataComponents.CONTAINER) || target.has(DataComponents.BUNDLE_CONTENTS)) {
            player.sendSystemMessage(Component.literal("Items that contain other items cannot be stored as a ghost tool."));
            return;
        }

        HolderLookup.Provider registries = player.level().registryAccess();

        JsonElement itemJson;
        try {
            itemJson = ItemStack.CODEC
                    .encodeStart(registries.createSerializationContext(JsonOps.INSTANCE), target.copyWithCount(1))
                    .getOrThrow();
        } catch (Exception e) {
            player.sendSystemMessage(Component.literal("Failed to capture item."));
            return;
        }

        PlayerSpellConfig.UpsertConfigForPlayer(
                db, player.getPlainTextName(), SPELL_CONFIG_KEY, GSON.toJson(itemJson));

        Vec3 pos = player.position();
        Vec3 eye = player.getEyePosition();
        world.sendParticles(ParticleTypes.PORTAL, pos.x, pos.y + 1.0, pos.z, 30, 0.4, 0.5, 0.4, 0.15);
        world.sendParticles(ParticleTypes.SMOKE, eye.x, eye.y, eye.z, 8, 0.15, 0.15, 0.15, 0.04);
        world.playSound(null, pos.x, pos.y, pos.z,
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.7F, 1.8F);
        player.sendSystemMessage(Component.literal(
                target.getHoverName().getString() + " has been stored as your ghost tool."));
    }

    public static void onItemEntityLoadedInEnd(ItemEntity item, ServerLevel world) {
        long now = world.getGameTime();
        for (ServerPlayer player : world.players()) {
            UUID uuid = player.getUUID();
            if (player.distanceTo(item) < 15.0
                    && player.getY() > -10
                    && !pendingItemInVoid.containsKey(uuid)
                    && !pendingPlayerInVoid.containsKey(uuid)) {
                pendingItemInVoid.put(uuid, now);
            }
        }
    }

    public static void tickVoidCheck(ServerLevel world) {
        if (!world.dimensionTypeRegistration().is(BuiltinDimensionTypes.END)) return;

        Optional<Database> db = Database.GetDB();
        if (db.isEmpty()) return;

        long now = world.getGameTime();

        pendingItemInVoid.entrySet().removeIf(entry -> {
            if (now - entry.getValue() > ITEM_TIMEOUT_TICKS) return true;

            ServerPlayer player = world.getServer().getPlayerList().getPlayer(entry.getKey());
            if (player == null) return true;

            if (player.getY() < -10) {
                pendingPlayerInVoid.put(entry.getKey(), now);
                return true;
            }
            return false;
        });

        pendingPlayerInVoid.entrySet().removeIf(entry -> {
            if (now - entry.getValue() > SURVIVE_TIMEOUT_TICKS) return true;

            ServerPlayer player = world.getServer().getPlayerList().getPlayer(entry.getKey());
            if (player == null || !player.isAlive()) return true;

            if (player.getY() > 0) {
                SkillGranter.grantSkillForPlayer(db.get(), player, Skills.GHOST_TOOL);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getFlatXpCost() { return 5; }

    @Override
    public String displayName() { return "Set Ghost Tool"; }

    @Override
    public String description() {
        return "Store a copy of the item in your offhand in the void — summon it to your hand at any time";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.GHOST_TOOL);
    }
}
