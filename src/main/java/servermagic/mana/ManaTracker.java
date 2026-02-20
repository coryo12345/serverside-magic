package servermagic.mana;

import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ManaTracker {
    private ConcurrentHashMap<String, ManaInfo> map;

    public ManaTracker() {
        this.map = new ConcurrentHashMap<>();
    }

    public Optional<ManaInfo> getManaInfoForPlayer(String username) {
        ManaInfo mi = map.get(username);
        if (mi == null) {
            return Optional.empty();
        }
        return Optional.of(mi);
    }

    public void manaRegenTick(double multiplier) {
        for (Entry<String, ManaInfo> entry : map.entrySet()) {
            ManaInfo old = entry.getValue();
            ManaInfo updated = new ManaInfo(old.max(),
                    Math.min(old.current() + ((int) Math.round(old.mps() * multiplier)), old.max()), old.mps());
            map.put(entry.getKey(), updated);
        }
    }

    public ManaInfo initPlayer(String username) {
        // TODO - this should determine maximum & mps based on player skills
        // We can just always start players with current = max
        ManaInfo mi = new ManaInfo(100, 10, 1);
        map.put(username, mi);
        return mi;
    }

    public void removePlayer(String username) {
        this.map.remove(username);
    }
}
