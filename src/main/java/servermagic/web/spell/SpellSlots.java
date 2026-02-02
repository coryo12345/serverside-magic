package servermagic.web.spell;

public class SpellSlots {
    // <shift>-3-2-1
    // 1 is always a right click because we start casting with a right click
    public final int RRR = Integer.parseInt("0000", 2);
    public final int RRL = Integer.parseInt("0100", 2);
    public final int RLR = Integer.parseInt("0010", 2);
    public final int RLL = Integer.parseInt("0110", 2);
    public final int SRRR = Integer.parseInt("1000", 2);
    public final int SRRL = Integer.parseInt("1100", 2);
    public final int SRLR = Integer.parseInt("1010", 2);
    public final int SRLL = Integer.parseInt("1110", 2);
}
