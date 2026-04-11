package servermagic.spells;

import java.util.Optional;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.mojang.math.Transformation;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.entitybinding.EntityBindingUtil;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class FlyingCarpet extends BaseSpell {

    public static final String TAG = "server-magic-flying-carpet";

    // Y offset from ghast base to carpet position (sits just below rider's feet).
    // Tune this if the carpet appears too high or too low while riding.
    private static final double CARPET_Y_OFFSET = 4.0;

    public FlyingCarpet(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        // 1. Spawn Happy Ghast at player position
        HappyGhast ghast = EntityType.HAPPY_GHAST.create(world, EntitySpawnReason.MOB_SUMMONED);
        ghast.setPos(player.getX(), player.getY(), player.getZ());
        ghast.forceSetRotation(player.getYHeadRot(), true, 0, true);
        ghast.addTag(TAG);

        // 2. Equip harness so the client recognises this as a controllable vehicle
        //    and sends forward/strafe inputs. Override the equippable asset_id so the
        //    harness renders as invisible via the resource pack's servermagic:invisible asset.
        ItemStack harness = new ItemStack(Items.RED_HARNESS);
        Equippable existing = harness.get(DataComponents.EQUIPPABLE);
        if (existing != null) {
            ResourceKey<net.minecraft.world.item.equipment.EquipmentAsset> invisibleAsset =
                    ResourceKey.create(EquipmentAssets.ROOT_ID,
                            Identifier.fromNamespaceAndPath("servermagic", "invisible"));
            Equippable.Builder builder = Equippable.builder(existing.slot())
                    .setEquipSound(existing.equipSound())
                    .setAsset(invisibleAsset)
                    .setDispensable(existing.dispensable())
                    .setSwappable(existing.swappable())
                    .setDamageOnHurt(existing.damageOnHurt())
                    .setEquipOnInteract(existing.equipOnInteract())
                    .setCanBeSheared(existing.canBeSheared())
                    .setShearingSound(existing.shearingSound());
            existing.allowedEntities().ifPresent(builder::setAllowedEntities);
            existing.cameraOverlay().ifPresent(builder::setCameraOverlay);
            harness.set(DataComponents.EQUIPPABLE, builder.build());
        }
        ghast.setItemSlot(EquipmentSlot.BODY, harness);

        world.addFreshEntity(ghast);

        // 3. Red carpet BlockDisplay — 4×4, very thin, centered under rider
        Display.BlockDisplay carpet = new Display.BlockDisplay(EntityType.BLOCK_DISPLAY, world);
        carpet.setPos(player.getX(), player.getY(), player.getZ());
        carpet.setBlockState(Blocks.RED_CARPET.defaultBlockState());
        float scale = 4.0f;
        carpet.setTransformation(new Transformation(
                new Vector3f(-scale / 2f, 0f, -scale / 2f),
                new Quaternionf(),
                new Vector3f(scale, 0.08f, scale),
                new Quaternionf()));
        world.addFreshEntity(carpet);

        // 4. Bind carpet (follower) to ghast (driver) with upward offset.
        // bindLevelFollower locks the carpet's pitch to 0 so it stays horizontal
        // regardless of where the player looks. Also makes the ghast invisible+silent.
        EntityBindingUtil.bindLevelFollower(world, ghast, carpet, new Vec3(0, CARPET_Y_OFFSET, 0), 0f);

        // 5. Mount the player onto the ghast
        MinecraftServer server = world.getServer();
        if (server != null) {
            server.execute(() -> player.startRiding(ghast));
        }
    }

    public static boolean isFlyingCarpetGhast(Entity entity) {
        return entity instanceof HappyGhast && entity.getTags().contains(TAG);
    }

    /**
     * Safety net: discard any un-ridden flying carpet ghasts.
     * Called periodically from ServerMagic. The dismount mixin handles the
     * normal case; this catches edge cases (e.g. ghast pushed out of world).
     */
    public static void tickCleanup(ServerLevel world) {
        var stray = world.getEntities(EntityType.HAPPY_GHAST,
                ghast -> ghast.getTags().contains(TAG) && !ghast.isVehicle());
        for (var ghast : stray) {
            ghast.discard();
        }
    }

    @Override
    public String displayName() {
        return "Flying Carpet";
    }

    @Override
    public String description() {
        return "Summon a magical flying carpet and soar through the skies";
    }

    @Override

    public int getFlatXpCost() {
        return 8;
    }

    public double getLevelPercentCost() {
        return 0.2;
    }
    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.FLYING_CARPET);
    }
}
