package servermagic.db;

public abstract class BaseMigration {
    public abstract String getName();

    public String[] getDependencies() {
        return new String[] {};
    }

    public abstract String getSql();
}
