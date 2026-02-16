package servermagic.spells;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.minecraft.world.phys.Vec3;
import servermagic.db.Database;
import servermagic.web.skill.Skill;
import servermagic.web.skill.Skills;

public class WitherBlast extends BaseSpell {

    public WitherBlast(ServerLevel world, ServerPlayer player, Database db) {
        super(world, player, db);
    }

    @Override
    protected void spellImplementation() {
        // TODO this could be a good test of a custom projectile so we can make this not
        // explode
        // OR we can just add some custom data to this summoned projectile, and mixin
        // the WitherSkull onHit to cancel early
        Vec3 startPos = player.getEyePosition().add(player.getLookAngle().scale(1.0));
        WitherSkull ws = new WitherSkull(world, player, player.getLookAngle());
        ws.setPos(startPos);
        world.addFreshEntity(ws);
    }

    @Override
    public String displayName() {
        return "Wither blast";
    }

    @Override
    public String description() {
        return "Shoots a wither skull";
    }

    @Override
    public Optional<Skill> getRequiredSkill() {
        return Optional.of(Skills.WITHERBLAST);
    }
}
