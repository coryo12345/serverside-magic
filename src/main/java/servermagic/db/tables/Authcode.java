package servermagic.db.tables;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import servermagic.db.Database;

public class Authcode {
    public Long id;
    public String username;
    public String code;
    public String expires;

    public static Optional<Authcode> GenerateCodeForUser(Database db, String username) {
        Random rand = new Random();
        String code = String.format("%d", rand.nextInt(9000) + 1000);
        LocalDateTime expires = LocalDateTime.now().plusMinutes(5);
        return db.transaction(conn -> {
            conn.createQuery("delete from authcode where username = :username")
                    .addParameter("username", username)
                    .executeUpdate();

            List<Authcode> ac = conn
                    .createQuery(
                            "insert into authcode (username, code, expires) values (:username, :code, :expires) returning *;")
                    .addParameter("username", username)
                    .addParameter("code", code)
                    .addParameter("expires", expires)
                    .executeAndFetch(Authcode.class);

            conn.commit();
            if (ac.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(ac.get(0));
        });
    }

    public static Optional<Authcode> GetCodeForUser(Database db, String username) {
        return db.query(conn -> {
            List<Authcode> ac = conn.createQuery("select * from authcode where username = :username limit 1")
                    .addParameter("username", username)
                    .executeAndFetch(Authcode.class);

            if (ac.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(ac.get(0));
        });
    }

    public static Optional<Authcode> ClearForUser(Database db, String username) {
        return db.query(conn -> {
            conn.createQuery("delete from authcode where username = :username")
                    .addParameter("username", username)
                    .executeUpdate();
            return Optional.empty();
        });
    }
}