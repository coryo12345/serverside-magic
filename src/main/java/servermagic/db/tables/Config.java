package servermagic.db.tables;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import servermagic.db.Database;

public class Config {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static boolean IsSuperuser(Database db, String username) {
        Optional<Object> result = db.query(conn -> {
            List<String> results = conn
                    .createQuery("select superusers from config where id = 1")
                    .executeScalarList(String.class);
            if (results == null || results.isEmpty()) return Optional.empty();
            try {
                List<String> superusers = OBJECT_MAPPER.readValue(results.get(0), new TypeReference<List<String>>() {});
                return Optional.of(superusers.contains(username));
            } catch (Exception e) {
                return Optional.empty();
            }
        });
        if (result.isEmpty()) return false;
        return (Boolean) result.get();
    }

    public static Optional<String> GetWebUrl(Database db) {
        return db.query(conn -> {
            List<String> results = conn
                    .createQuery("select web_url from config where id = 1")
                    .executeScalarList(String.class);
            if (results == null || results.isEmpty()) return Optional.empty();
            return Optional.of(results.get(0));
        });
    }

    public static void SetWebUrl(Database db, String url) {
        db.transaction(conn -> {
            conn.createQuery(
                    "insert into config(id, web_url) values(1, :url) " +
                    "on conflict(id) do update set web_url = excluded.web_url")
                    .addParameter("url", url)
                    .executeUpdate();
            conn.commit();
            return Optional.empty();
        });
    }

}
