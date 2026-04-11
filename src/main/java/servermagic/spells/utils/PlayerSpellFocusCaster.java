package servermagic.spells.utils;

import java.util.Optional;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import servermagic.db.Database;
import servermagic.db.tables.PlayerCastStatus;
import servermagic.db.tables.PlayerCastStatus.PlayerCastResult;
import servermagic.db.tables.SpellSlot;
import servermagic.spells.BaseSpell;
import servermagic.web.spell.SpellSlots;
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

    /**
     * @return SUCCESS if spell was cast, FAIL if something went wrong, PASS
     *         otherwise
     */
    public InteractionResult handleClick(ServerLevel world, ServerPlayer player, InteractionHand hand, ClickType clickType) {
        Optional<PlayerCastResult> playerCastStatus = PlayerCastStatus.UpdateCastForPlayer(db,
                player.getPlainTextName(), clickType, player.isCrouching());
        if (playerCastStatus.isEmpty()) {
            // Something went wrong
            return InteractionResult.FAIL;
        }
        if (!playerCastStatus.get().updated()) {
            // this means we were able to process the request, but we didn't actually update
            // the status
            // So we didn't try to cast anything because of invalid key inputs (AKA starting
            // a spell with left click)
            return InteractionResult.PASS;
        }

        this.sendCastStatusToPlayer(player, playerCastStatus.get().status());

        if (!playerCastStatus.get().status().isCastComplete()) {
            // still casting, this is okay, we have nothing more to do
            return InteractionResult.PASS;
        }

        Optional<Integer> slotVal = playerCastStatus.get().status().toSpellSlotNum();
        if (slotVal.isEmpty()) {
            // I'm not sure if this should be pass or fail
            return InteractionResult.PASS;
        }

        Optional<SpellSlot> ss = SpellSlot.GetSlotForPlayer(this.db, player.getPlainTextName(), slotVal.get());
        if (ss.isEmpty()) {
            // the player doesn't have a spell in this slot.
            // that means there's nothing to do so we can just move on
            return InteractionResult.PASS;
        }
        String spellId = ss.get().spell_id;

        Optional<UISpellDefinition> def = Spells.Get().getSpell(spellId);
        if (def.isEmpty()) {
            // in theory this shouldn't happen but we will handle
            return InteractionResult.PASS;
        }

        Class<? extends BaseSpell> clazz = def.get().getClazz();

        try {
            BaseSpell spell = clazz.getDeclaredConstructor(
                    ServerLevel.class, ServerPlayer.class, Database.class, InteractionHand.class)
                    .newInstance(world, player, db, hand);
            if (!spell.playerHasRequiredSkill()) {
                return InteractionResult.PASS;
            }

            int xpForLevel = player.getXpNeededForNextLevel();
            int totalCost = spell.getFlatXpCost() + (int)(spell.getLevelPercentCost() * xpForLevel);
            if (getPlayerTotalXp(player) < totalCost) {
                player.displayClientMessage(
                    Component.literal("Not enough XP to cast this spell!").withStyle(ChatFormatting.RED), true);
                return InteractionResult.FAIL;
            }

            player.giveExperiencePoints(-totalCost);
            return spell.castAsInteraction();
        } catch (Exception e) {
            return InteractionResult.FAIL;
        }
    }

    private static int getPlayerTotalXp(ServerPlayer player) {
        int level = player.experienceLevel;
        int barXp = (int)(player.experienceProgress * player.getXpNeededForNextLevel());
        int cumulative = 0;
        for (int l = 0; l < level; l++) {
            if (l >= 30) cumulative += 9 * l - 158;
            else if (l >= 16) cumulative += 5 * l - 38;
            else cumulative += 2 * l + 7;
        }
        return cumulative + barXp;
    }

    private void sendCastStatusToPlayer(ServerPlayer player, PlayerCastStatus status) {
        if (status.cast_state.length() < 2)
            return;
        StringBuilder s = new StringBuilder();
        if (status.cast_state.charAt(status.cast_state.length() - 1) == '1') {
            s.append("Shift-");
        }
        int itemCount = 1; // 1 for shift
        for (int i = status.cast_state.length() - 2; i >= 0; i--) {
            if (status.cast_state.charAt(i) == ClickType.LEFT_CLICK.getValue().toString().charAt(0)) {
                s.append("L");
            } else {
                s.append("R");
            }
            itemCount++;
            if (itemCount < SpellSlots.SPELL_SLOT_BINARY_CODE_LEN) {
                s.append("-");
            }
        }

        ChatFormatting color = status.cast_state.length() >= SpellSlots.SPELL_SLOT_BINARY_CODE_LEN
                ? ChatFormatting.GREEN
                : ChatFormatting.RED;

        Component combo = Component.literal(s.toString()).withStyle(ChatFormatting.BOLD, color);
        player.displayClientMessage(combo, true);
    }
}
