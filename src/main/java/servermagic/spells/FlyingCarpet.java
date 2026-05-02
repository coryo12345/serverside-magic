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
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.entitybinding.EntityBindingUtil;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class FlyingCarpet extends BaseSpell {

    public static final String TAG = "server-magic-flying-carpet";

    // Y offset from ghast base to the carpet display entity position.
    // Tune this if the carpet model appears too high or too low while riding.
    private static final double CARPET_Y_OFFSET = 3.0;

    // Uniform scale applied to the ItemDisplay entity on top of the model's own head transforms.
    // Increase to make the carpet larger.
    private static final float CARPET_SCALE = 2.0f;

    private static final float CARPET_SPEED = 1.4f;

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
        ghast.setSpeed(CARPET_SPEED);

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

        // 3. ItemDisplay entity showing the servermagic:flying_carpet model.
        //    Using HEAD display context so the model's head transforms apply, then an
        //    additional Transformation to scale it up. Tune CARPET_SCALE as needed.
        Display.ItemDisplay carpetDisplay = new Display.ItemDisplay(EntityType.ITEM_DISPLAY, world);
        carpetDisplay.setPos(player.getX(), player.getY(), player.getZ());
        carpetDisplay.setNoGravity(true);
        carpetDisplay.addTag(TAG);

        ItemStack carpetItem = new ItemStack(Items.CARVED_PUMPKIN);
        carpetItem.set(DataComponents.ITEM_MODEL,
                Identifier.fromNamespaceAndPath("servermagic", "flying_carpet"));
        carpetDisplay.setItemStack(carpetItem);
        carpetDisplay.setItemTransform(ItemDisplayContext.HEAD);
        // Smooth out the per-tick teleports from EntityBindingTickHandler.
        // The client interpolates position over this many client ticks instead of snapping.
        carpetDisplay.setPosRotInterpolationDuration(3);
        carpetDisplay.setTransformation(new Transformation(
                new Vector3f(0f, 0f, 0f),
                new Quaternionf(),
                new Vector3f(CARPET_SCALE, CARPET_SCALE, CARPET_SCALE),
                new Quaternionf()));

        world.addFreshEntity(carpetDisplay);

        // 4. Bind carpet display (follower) to ghast (driver) with upward offset.
        // bindLevelFollower locks the display's pitch to 0 so it stays horizontal
        // regardless of where the player looks. Also makes the ghast invisible+silent.
        EntityBindingUtil.bindLevelFollower(world, ghast, carpetDisplay, new Vec3(0, CARPET_Y_OFFSET, 0), 0f);

        // 5. Mount the player onto the ghast
        MinecraftServer server = world.getServer();
        if (server != null) {
            server.execute(() -> player.startRiding(ghast));
        }
    }

    public static boolean isFlyingCarpetGhast(Entity entity) {
        return entity instanceof HappyGhast && entity.entityTags().contains(TAG);
    }

    /**
     * Safety net: discard any un-ridden flying carpet ghasts.
     * Called periodically from ServerMagic. The dismount mixin handles the
     * normal case; this catches edge cases (e.g. ghast pushed out of world).
     */
    public static void tickCleanup(ServerLevel world) {
        var stray = world.getEntities(EntityType.HAPPY_GHAST,
                ghast -> ghast.entityTags().contains(TAG) && !ghast.isVehicle());
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
