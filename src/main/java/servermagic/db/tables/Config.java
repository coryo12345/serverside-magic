package servermagic.db.tables;

import java.util.List;
import java.util.Optional;

import servermagic.db.Database;

public class Config {

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
