package servermagic.db;

import java.util.List;

public abstract class BaseMigration {
    public abstract String getName();

    public String[] getDependencies() {
        return new String[] {};
    }

    public abstract List<String> getSql();
}
