package servermagic.data.items;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import servermagic.db.Database;
import servermagic.db.tables.SpellSlot;
import servermagic.spells.BaseSpell;
import servermagic.web.spell.Spells;
import servermagic.web.spell.UISpellDefinition;

public class PlayerSpellFocusCaster {
    private static PlayerSpellFocusCaster caster;

    public static void Init(Database db) {
        caster = new PlayerSpellFocusCaster(db);
    }

    public static PlayerSpellFocusCaster Get() throws Exception {
        if (caster == null) {
            throw new Exception("NOT INITIALIZED");
        }
        return caster;
    }

    // =============================================

    private Database db;

    private PlayerSpellFocusCaster(Database db) {
        this.db = db;
    }

    public InteractionResult handleClick(ServerLevel world, ServerPlayer player) {
        // TODO this will need to handle left/right click interactions
        // to determine spell casts for now we'll just use slot 0

        Optional<SpellSlot> ss = SpellSlot.GetSlotForPlayer(this.db, player.getPlainTextName(), 0);
        if (ss.isEmpty()) {
            // the player doesn't have a spell in this slot.
            // that means there's nothing to do so we can just move on
            return InteractionResult.SUCCESS;
        }
        String spellId = ss.get().spell_id;

        Optional<UISpellDefinition> def = Spells.Get().getSpell(spellId);
        if (def.isEmpty()) {
            // in theory this shouldn't happen but we will handle
            return InteractionResult.SUCCESS;
        }

        Class<? extends BaseSpell> clazz = def.get().getClazz();

        try {
            BaseSpell spell = clazz.getDeclaredConstructor(ServerLevel.class, ServerPlayer.class).newInstance(world,
                    player);
            InteractionResult ir = spell.castAsInteraction();
            return ir;
        } catch (Exception e) {
            return InteractionResult.FAIL;
        }
    }
}
