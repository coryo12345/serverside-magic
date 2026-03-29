package servermagic.spells;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.entitybinding.EntityBindingUtil;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class CowHorse extends BaseSpell {

    public CowHorse(ServerLevel world, ServerPlayer player, Database db) {
        super(world, player, db);
    }

    @Override
    protected void spellImplementation() {
        Vec3 position = player.getPosition(0);
        Vec3 playerView = player.getViewVector(1.0F);
        Vec3 entityPosition = new Vec3(position.x + playerView.x, position.y, position.z + playerView.z);

        // TODO summon cow & horse at position.
        Horse horse = EntityType.HORSE.create(world, EntitySpawnReason.COMMAND);
        horse.setPos(entityPosition.x, entityPosition.y, entityPosition.z);
        Cow cow = EntityType.COW.create(world, EntitySpawnReason.COMMAND);
        cow.setPos(entityPosition.x, entityPosition.y, entityPosition.z);

        EntityBindingUtil.bind(world, horse, cow);

        world.addFreshEntity(cow);
        world.addFreshEntity(horse);
        // then bind them with entitybindingutil.bind
        // then test!
    }

    @Override
    public String displayName() {
        return "Cow Horse";
    }

    @Override
    public String description() {
        return "Summons a cow you can ride like a horse";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.BOUND_SWORD);
    }
}
