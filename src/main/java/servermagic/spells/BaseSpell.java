package servermagic.spells;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import servermagic.db.tables.PlayerSpellConfig;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import servermagic.db.Database;
import servermagic.db.tables.SkillUnlocks;
import servermagic.web.skill.Skill;
import servermagic.web.spell.SpellConfigField;

public abstract class BaseSpell {
    protected ServerLevel world;
    protected ServerPlayer player;
    protected Database db;
    protected InteractionHand hand;

    public BaseSpell(ServerLevel world, ServerPlayer player, Database db, InteractionHand hand) {
        this.world = world;
        this.player = player;
        this.db = db;
        this.hand = hand;
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

    public int getFlatXpCost() {
        return 0;
    }

    public double getLevelPercentCost() {
        return 0.0;
    }

    public String id() {
        return this.getClass().getSimpleName();
    }

    public Optional<Skill> getRequiredSkill() {
        return Optional.empty();
    }

    public boolean playerHasRequiredSkill() {
        Optional<Skill> skill = getRequiredSkill();
        if (skill.isEmpty()) {
            return true;
        }
        return SkillUnlocks.IsSkillUnlocked(db, player.getPlainTextName(), skill.get(), false);
    }

    public Optional<List<SpellConfigField>> getConfigSchema(String username) {
        return Optional.empty();
    }

    private static final ObjectMapper BASE_SPELL_OBJECT_MAPPER = new ObjectMapper();

    protected Optional<String> getStringConfig(String key) {
        if (db == null || player == null) return Optional.empty();
        try {
            String username = player.getPlainTextName();
            Optional<PlayerSpellConfig> cfg = PlayerSpellConfig.GetConfigForPlayer(db, username, this.id());
            if (cfg.isPresent()) {
                @SuppressWarnings("unchecked")
                Map<String, Object> map = BASE_SPELL_OBJECT_MAPPER.readValue(cfg.get().config, Map.class);
                Object val = map.get(key);
                return val != null ? Optional.of(val.toString()) : Optional.empty();
            }
        } catch (Exception e) {
            // fall through
        }
        return Optional.empty();
    }

    public abstract String displayName();

    public abstract String description();
}
