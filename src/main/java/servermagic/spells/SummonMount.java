package servermagic.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import servermagic.db.Database;
import servermagic.db.tables.PlayerSpellConfig;
import servermagic.db.tables.SkillUnlocks;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;
import servermagic.web.spell.SpellConfigField;

public class SummonMount extends BaseSpell {

    private final static String CUSTOM_HORSE_TAG = "server-magic-mount-horse";
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public SummonMount(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        super(world, player, db, hand);
    }

    @Override
    protected void spellImplementation() {
        Horse horse = EntityType.HORSE.create(world, EntitySpawnReason.MOB_SUMMONED);
        horse.setPos(player.getX(), player.getY(), player.getZ());
        horse.setOwner(player);
        horse.addTag(CUSTOM_HORSE_TAG);
        horse.setTamed(true);
        horse.forceSetRotation(player.getYHeadRot(), true, player.xRotO, true);
        horse.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20);
        horse.setHealth(20);
        horse.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(1.5);
        horse.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(getHorseSpeed());
        ItemStack saddle = new ItemStack(Items.SADDLE);
        horse.equipItemIfPossible(world, saddle);
        ItemStack armor = new ItemStack(Items.COPPER_HORSE_ARMOR);
        horse.equipItemIfPossible(world, armor);

        world.addFreshEntity(horse);

        world.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                horse.getX(), horse.getY() + 1.0, horse.getZ(), 20, 0.6, 0.8, 0.6, 0.1);
        world.sendParticles(ParticleTypes.POOF,
                horse.getX(), horse.getY() + 0.5, horse.getZ(), 10, 0.5, 0.5, 0.5, 0.05);
        world.playSound(null, horse.getX(), horse.getY(), horse.getZ(),
                SoundEvents.HORSE_AMBIENT, SoundSource.NEUTRAL, 1.0F, 1.0F);

        MinecraftServer server = world.getServer();
        if (server != null) {
            server.execute(() -> {
                player.startRiding(horse);
            });
        }
    }

    private double getHorseSpeed() {
        if (db == null || player == null) {
            return 0.2;
        }
        try {
            String username = player.getPlainTextName();
            Optional<PlayerSpellConfig> cfg = PlayerSpellConfig.GetConfigForPlayer(db, username, this.id());
            if (cfg.isPresent()) {
                @SuppressWarnings("unchecked")
                Map<String, Object> configMap = OBJECT_MAPPER.readValue(cfg.get().config, Map.class);
                String speedVal = (String) configMap.get("speed");
                if ("Gallop".equals(speedVal) && SkillUnlocks.IsSkillUnlocked(db, username, Skills.MOUNT_GALLOP, false)) {
                    return 0.4;
                }
            }
        } catch (Exception e) {
            // fall back to default
        }
        return 0.2;
    }

    @Override
    public Optional<List<SpellConfigField>> getConfigSchema(String username) {
        List<String> options = new ArrayList<>();
        options.add("Walk");
        if (db != null && SkillUnlocks.IsSkillUnlocked(db, username, Skills.MOUNT_GALLOP, false)) {
            options.add("Gallop");
        }
        return Optional.of(List.of(SpellConfigField.select("speed", options, "Walk")));
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

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.SUMMON_MOUNT);
    }
}
