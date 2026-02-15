package servermagic.spells;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import servermagic.db.Database;
import servermagic.db.tables.SkillUnlocks;
import servermagic.web.skill.Skill;

public abstract class BaseSpell {
    protected ServerLevel world;
    protected ServerPlayer player;
    protected Database db;

    public BaseSpell(ServerLevel world, ServerPlayer player, Database db) {
        this.world = world;
        this.player = player;
        this.db = db;
    }

    protected abstract void spellImplementation();

    public boolean cast() {
        try {
            this.spellImplementation();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public InteractionResult castAsInteraction() {
        boolean success = this.cast();
        if (success) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public int cost() {
        return 1;
    }

    public String id() {
        return this.getClass().getSimpleName();
    }

    public Optional<Skill> getRequiredSkill() {
        return Optional.empty();
    }

    public boolean isUnlocked() {
        Optional<Skill> skill = getRequiredSkill();
        if (skill.isEmpty()) {
            return true;
        }
        return SkillUnlocks.IsSkillUnlocked(db, player.getPlainTextName(), skill.get());
    }

    public abstract String displayName();

    public abstract String description();
}
