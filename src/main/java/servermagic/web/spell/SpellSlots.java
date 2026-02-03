package servermagic.web.spell;

import java.util.List;

public class SpellSlots {
    // <shift>-3-2-1
    // 1 is always a right click because we start casting with a right click
    public static final int RRR = Integer.parseInt("0000", 2);
    public static final int RLR = Integer.parseInt("0010", 2);
    public static final int RRL = Integer.parseInt("0100", 2);
    public static final int RLL = Integer.parseInt("0110", 2);
    public static final int SRRR = Integer.parseInt("1000", 2);
    public static final int SRLR = Integer.parseInt("1010", 2);
    public static final int SRRL = Integer.parseInt("1100", 2);
    public static final int SRLL = Integer.parseInt("1110", 2);

    public static boolean isValidSlot(int slot) {
        // THeres probably a better way to do this but i don't forsee adding more slots
        // so this probably fine
        return List.of(RRR, RLR, RRL, RLL, SRRR, SRLR, SRRL, SRLL).contains(slot);
    }
}
