package servermagic.web.spell;

import java.util.List;

public class SpellSlots {
    // 3-2-1-<shift>
    // 1 is always a right click because we start casting with a right click
    public static final int RRR = Integer.parseInt("0000", 2);
    public static final int RLR = Integer.parseInt("0100", 2);
    public static final int RRL = Integer.parseInt("1000", 2);
    public static final int RLL = Integer.parseInt("1100", 2);
    public static final int SRRR = Integer.parseInt("0001", 2);
    public static final int SRLR = Integer.parseInt("0101", 2);
    public static final int SRRL = Integer.parseInt("1001", 2);
    public static final int SRLL = Integer.parseInt("1101", 2);

    public static final int SPELL_SLOT_BINARY_CODE_LEN = 4; // if we want to add more slots we need to increase this bad boy and define them all

    public static boolean isValidSlot(int slot) {
        // THeres probably a better way to do this but i don't forsee adding more slots
        // so this probably fine
        return List.of(RRR, RLR, RRL, RLL, SRRR, SRLR, SRRL, SRLL).contains(slot);
    }
}
