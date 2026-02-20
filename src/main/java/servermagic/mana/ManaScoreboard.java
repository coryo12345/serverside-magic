package servermagic.mana;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundResetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ManaScoreboard {

    private static final String OBJECTIVE_NAME = "mana_hud";

    // We need a persistent dummy scoreboard/objective to pass into packets.
    // One shared instance is fine - it's never registered anywhere, just used
    // to satisfy the Objective constructor.
    private static final Scoreboard DUMMY_SB = new Scoreboard();
    private static final Objective DUMMY_OBJ = new Objective(
            DUMMY_SB,
            OBJECTIVE_NAME,
            ObjectiveCriteria.DUMMY,
            Component.literal("✦ Mana"),
            ObjectiveCriteria.RenderType.INTEGER,
            true,
            null);

    // Score indices - higher number = higher up in the sidebar
    private static final int SCORE_MANA_TEXT = 1;

    // These are the "template" entry strings - used to remove stale entries
    // We track what we last sent so we can remove exactly those
    private static final Map<UUID, String[]> lastEntries = new HashMap<>();

    public static void onPlayerJoin(ServerPlayer player) {
        // Register the objective on the client (METHOD_ADD = 0)
        player.connection.send(new ClientboundSetObjectivePacket(DUMMY_OBJ, 0));

        // Tell the client to show it in the sidebar slot
        player.connection.send(new ClientboundSetDisplayObjectivePacket(
                DisplaySlot.SIDEBAR,
                DUMMY_OBJ));
    }

    public static void updateManaDisplay(ManaInfo mana, ServerPlayer player) {
        String manaText = "§e" + mana.current() + " §7/ §f" + mana.max();
        String[] newEntries = { manaText };

        // Remove previous entries for this player
        String[] old = lastEntries.get(player.getUUID());
        if (old != null) {
            for (String entry : old) {
                player.connection.send(new ClientboundResetScorePacket(entry, OBJECTIVE_NAME));
            }
        }

        // Send new entries
        sendScore(player, manaText, SCORE_MANA_TEXT);

        // Remember what we sent
        lastEntries.put(player.getUUID(), newEntries);
    }

    private static void sendScore(ServerPlayer player, String entry, int score) {
        player.connection.send(new ClientboundSetScorePacket(
                entry, // the text that appears as the line (the "fake player name")
                OBJECTIVE_NAME, // which objective this belongs to
                score, // numeric score, controls vertical order
                Optional.empty(), // optional custom display (unused)
                Optional.empty() // optional number format (unused)
        ));
    }

    public static void onPlayerQuit(ServerPlayer player) {
        lastEntries.remove(player.getUUID());
        // METHOD_REMOVE = 1 - tells the client to discard this objective entirely
        player.connection.send(new ClientboundSetObjectivePacket(DUMMY_OBJ, 1));
    }
}