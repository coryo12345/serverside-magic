package servermagic.spells;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SummonMount extends BaseSpell {

    private final static String CUSTOM_HORSE_TAG = "server-magic-mount-horse";

    public SummonMount(ServerLevel world, ServerPlayer player) {
        super(world, player);
    }

    @Override
    protected void spellImplementation() {
        Horse horse = EntityType.HORSE.create(world, EntitySpawnReason.MOB_SUMMONED);
        horse.setPos(player.getX(), player.getY(), player.getZ());
        // TODO rotation is not right
        horse.setXRot(player.getXRot());
        horse.setYBodyRot(player.getYRot());
        horse.setOwner(player);
        horse.addTag(CUSTOM_HORSE_TAG);
        horse.setTamed(true);

        // TODO we'll need to go get info about what unlocks the user has
        // to determine these...
        // these values are NOT BASE
        // check wiki for base values (lets keep them realistic)
        horse.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20);
        horse.setHealth(20);
        horse.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(1.5);
        horse.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4);
        ItemStack saddle = new ItemStack(Items.SADDLE);
        horse.equipItemIfPossible(world, saddle);
        ItemStack armor = new ItemStack(Items.COPPER_HORSE_ARMOR);
        horse.equipItemIfPossible(world, armor);

        world.addFreshEntity(horse);
        MinecraftServer server = world.getServer();
        if (server != null) {
            server.execute(() -> {
                player.startRiding(horse);
            });
        }
    }

    public static boolean isCustomHorse(Entity entity) {
        // check custom tags on a supplied entity to see if it is one of our horses
        return entity instanceof Horse && entity.getTags().contains(CUSTOM_HORSE_TAG);
    }

    public static void tickCleanup(ServerLevel world) {
        var horses = world.getEntities(EntityType.HORSE,
                horse -> horse.getTags().contains(CUSTOM_HORSE_TAG) && !horse.isVehicle());
        for (Horse horse : horses) {
            horse.discard();
        }
    }

    @Override
    public String displayName() {
        return "Mount: Horse";
    }

    @Override
    public String description() {
        return "Summon your loyal steed and mount, ready to ride. This summon uses all enabled upgrades from the spell tree";
    }

}
