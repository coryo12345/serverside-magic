package servermagic.cosmetics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import servermagic.ServerMagic;

public class Cosmetics {
    public static final Cosmetic LUNAR_STAFF = new Cosmetic(
            "lunar_staff", "Lunar Staff", CosmeticSlot.SPELLBOOK, "servermagic:lunar_staff");

    public static List<Cosmetic> GetAll() {
        Field[] declaredFields = Cosmetics.class.getDeclaredFields();
        List<Cosmetic> all = new ArrayList<>();
        for (Field field : declaredFields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                try {
                    Object o = field.get(null);
                    if (o instanceof Cosmetic c) {
                        all.add(c);
                    }
                } catch (NullPointerException | IllegalAccessException | IllegalArgumentException e) {
                    ServerMagic.LOGGER.error("Failed to access cosmetic using reflection: " + e.getStackTrace());
                }
            }
        }
        return all;
    }

    public static List<Cosmetic> GetAllForSlot(CosmeticSlot slot) {
        return GetAll().stream().filter(c -> c.getSlot() == slot).toList();
    }

    public static Optional<Cosmetic> GetById(String id) {
        return GetAll().stream().filter(c -> c.getId().equals(id)).findFirst();
    }
}
