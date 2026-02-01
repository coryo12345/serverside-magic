package servermagic.db;

public class MigrationFailedException extends Exception {
    public MigrationFailedException(String msg) {
        super(msg);
    }
}
