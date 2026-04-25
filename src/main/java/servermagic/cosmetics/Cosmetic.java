package servermagic.cosmetics;

public class Cosmetic {
    private final String id;
    private final String displayName;
    private final CosmeticSlot slot;
    private final String itemModel;

    public Cosmetic(String id, String displayName, CosmeticSlot slot, String itemModel) {
        this.id = id;
        this.displayName = displayName;
        this.slot = slot;
        this.itemModel = itemModel;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public CosmeticSlot getSlot() {
        return slot;
    }

    public String getItemModel() {
        return itemModel;
    }
}
