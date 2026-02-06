package servermagic.db.tables;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import servermagic.ServerMagic;
import servermagic.db.Database;
import servermagic.spells.utils.ClickType;
import servermagic.web.spell.SpellSlots;

public class PlayerCastStatus {
    public Long id;
    public String username;
    public String last_updated;

    // This value is a binary string representation of the spell slot numbers from
    // SpellSlots.java
    // We store as a string to make debugging easier since i'm not building this
    // with 100+ player servers in mind
    public String cast_state;

    public boolean isCastComplete() {
        boolean isNull = this.cast_state == null;
        boolean emptyStr = "".equals(this.cast_state);
        boolean hasInvalidChars = this.cast_state.contains("-");
        if (isNull || emptyStr || hasInvalidChars) {
            return false;
        }

        try {
            Integer.parseInt(cast_state, 2);
        } catch (Exception e) {
            return false;
        }

        return this.cast_state.length() >= SpellSlots.SPELL_SLOT_BINARY_CODE_LEN;
    }

    public Optional<Integer> toSpellSlotNum() {
        int stateSlotValue = Integer.parseInt(this.cast_state, 2);
        if (SpellSlots.isValidSlot(stateSlotValue)) {
            return Optional.of(stateSlotValue);
        }
        return Optional.empty();
    }

    private final static int MAX_CAST_TIME_WINDOW_SEC = 3;
    private final static String DEFAULT_CAST_STATE = "00";

    /**
     * This could slice off values if padding is smaller than the power of value
     * 
     * @param val     the integer value to encode as a string with padding
     * @param padding the number of binary digits to make the resulting string
     */
    private static String toPaddedBinaryString(int val, int padding) {
        // Step 1: limit `val` to not have a greater length than `padding` indicates
        // Step 2: use bitwise operators to add an additional 1 to the start of the
        // encoded string (so that the resulting length is 1+padding)
        // Step 3: encode to binary string
        // step 4: substring(1) to remove the 1 we prepended
        //
        // So if val = 2, padding = 3
        // val = 10, padding = 3
        // mask val with 111
        // val = 10
        // prepend 3 digits with a 1:
        // val = 1010
        // toString then slice the starting 1 off:
        // return "010"

        int prepend = (int) Math.pow(2, padding);
        int mask = (prepend - 1);
        val = val & mask;
        val = val | prepend;
        return Integer.toBinaryString(val).substring(1);
    }

    public static Optional<PlayerCastResult> UpdateCastForPlayer(Database db, String username, ClickType clickType) {
        return UpdateCastForPlayer(db, username, clickType, false);
    }

    public static Optional<PlayerCastResult> UpdateCastForPlayer(Database db, String username, ClickType clickType,
            boolean sneak) {
        return db.transaction(conn -> {
            List<PlayerCastStatus> statuses = conn
                    .createQuery("select * from playercaststatus where username = :username")
                    .addParameter("username", username)
                    .executeAndFetch(PlayerCastStatus.class);
            if (statuses == null) {
                // something went wrong
                return Optional.empty();
            }

            PlayerCastStatus status;
            boolean needToCreate = false; // remember this for when we need to store this value

            // make sure we have some initial state to work with
            if (statuses.isEmpty()) {
                needToCreate = true;
                status = new PlayerCastStatus();
                status.username = username;
                status.cast_state = DEFAULT_CAST_STATE;
                status.last_updated = "1970-01-01T00:00:01";
            } else {
                status = statuses.get(0);
            }

            // we need to determine if we are continuing a valid in-progress cast, or
            // starting a new one
            boolean continuingACast = false;
            if (LocalDateTime.parse(status.last_updated).plusSeconds(MAX_CAST_TIME_WINDOW_SEC)
                    .isAfter(LocalDateTime.now())) {
                // we have a recent cast, but we need to make sure it isn't already completed
                // before we can continue it
                if (status.cast_state.length() < SpellSlots.SPELL_SLOT_BINARY_CODE_LEN) {
                    continuingACast = true;
                }
            }

            if (continuingACast) {
                int codeLen = status.cast_state.length();
                if (codeLen == 1) {
                    status.cast_state = toPaddedBinaryString((clickType.getValue() << 1) | (sneak ? 1 : 0), 2);
                } else {
                    try {
                        int previousState = Integer.parseInt(status.cast_state);
                        int newState = previousState | (clickType.getValue() << codeLen);
                        status.cast_state = toPaddedBinaryString(newState, codeLen + 1);
                    } catch (NumberFormatException e) {
                        // We somehow ended up with an invalid state in the DB ...
                        // i don't know how this would happen, but we'll handle by just "resetting"
                        ServerMagic.LOGGER
                                .error("Invalid Cast State found (id: " + status.id + "): " + status.cast_state);
                        status.cast_state = toPaddedBinaryString((clickType.getValue() << 1) | (sneak ? 1 : 0), 2);
                    }
                }
                status.last_updated = LocalDateTime.now().toString();
            } else {
                if (clickType == ClickType.LEFT_CLICK) {
                    // you can't start a cast with left click
                    return Optional.of(new PlayerCastResult(status, false));
                }
                int shiftModifierChar = sneak ? 1 : 0;
                int firstClickKey = ClickType.RIGHT_CLICK.getValue(); // we know you are always right clicking to start
                Integer castState = 0 | firstClickKey << 1 | shiftModifierChar;
                status.cast_state = toPaddedBinaryString(castState, 2);
                status.last_updated = LocalDateTime.now().toString();
            }

            // Now we store the updated state
            if (needToCreate) {
                conn.createQuery(
                        "insert into playercaststatus (username, cast_state, last_updated) values (:username, :cast_state, :last_updated)")
                        .addParameter("username", status.username)
                        .addParameter("cast_state", status.cast_state)
                        .addParameter("last_updated", status.last_updated)
                        .executeUpdate();
            } else {
                conn.createQuery(
                        "update playercaststatus set cast_state = :cast_state, last_updated = :last_updated where username = :username")
                        .addParameter("username", status.username)
                        .addParameter("cast_state", status.cast_state)
                        .addParameter("last_updated", status.last_updated)
                        .executeUpdate();
            }
            conn.commit();

            return Optional.of(new PlayerCastResult(status, true));
        });
    }

    public record PlayerCastResult(PlayerCastStatus status, Boolean updated) {
    }
}
