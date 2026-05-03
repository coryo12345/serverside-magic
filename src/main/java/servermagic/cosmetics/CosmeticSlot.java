package servermagic.cosmetics;

import java.util.Optional;

import net.minecraft.world.entity.EquipmentSlot;

public enum CosmeticSlot {
    HELMET("helmet", new EquipmentSlot[] { EquipmentSlot.HEAD }),
    CHESTPLATE("chestplate", new EquipmentSlot[] { EquipmentSlot.CHEST }),
    LEGGINGS("leggings", new EquipmentSlot[] { EquipmentSlot.LEGS }),
    BOOTS("boots", new EquipmentSlot[] { EquipmentSlot.FEET }),
    SPELLBOOK("spellbook", new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });

    private final String id;
    private final EquipmentSlot[] equipmentSlots;

    CosmeticSlot(String id, EquipmentSlot[] equipmentSlots) {
        this.id = id;
        this.equipmentSlots = equipmentSlots;
    }

    public String getId() {
        return id;
    }

    public EquipmentSlot[] getEquipmentSlots() {
        return equipmentSlots;
    }

    public static Optional<CosmeticSlot> fromId(String id) {
        for (CosmeticSlot slot : values()) {
            if (slot.id.equals(id)) {
                return Optional.of(slot);
            }
        }
        return Optional.empty();
    }

    public boolean isModelBased() {
        return this == SPELLBOOK;
    }

    public Optional<String> getDefaultModel() {
        if (this == SPELLBOOK) {
            return Optional.of("servermagic:spellbook");
        }
        return Optional.empty();
    }

    public static Optional<CosmeticSlot> fromEquipmentSlot(EquipmentSlot mcSlot) {
        for (CosmeticSlot slot : values()) {
            for (EquipmentSlot eq : slot.equipmentSlots) {
                if (eq == mcSlot) {
                    return Optional.of(slot);
                }
            }
        }
        return Optional.empty();
    }
}
