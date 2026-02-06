package servermagic.spells.utils;

public enum ClickType {
    LEFT_CLICK(1),
    RIGHT_CLICK(0);

    private final Integer val;

    ClickType(final Integer val) {
        this.val = val;
    }

    public Integer getValue() {
        return this.val;
    }

    @Override
    public String toString() {
        return Integer.toBinaryString(this.val);
    }
}
