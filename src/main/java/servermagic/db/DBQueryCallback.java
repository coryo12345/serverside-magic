package servermagic.db;

import java.util.Optional;

import org.sql2o.Connection;

@FunctionalInterface
public interface DBQueryCallback {
    public Optional<Object> callback(Connection conn);
}
