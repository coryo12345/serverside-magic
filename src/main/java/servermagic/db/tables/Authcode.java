package servermagic.db.tables;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import servermagic.db.Database;

public class Authcode {
    public Long id;
    public String username;
    public String code;
    public String expires;

    public static Optional<Authcode> GenerateCodeForUser(Database db, String username) {
        String code = String.format("%d", (int) Math.floor(Math.random() * 9999));
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
}