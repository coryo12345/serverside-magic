package servermagic.web.auth;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class AuthTokens {

    // server restarts will reset auth, which is okay i think
    // private static final SecretKey JWT_SECRET = Jwts.SIG.HS256.key().build();
    // ^ PROD | DEV v
    private static final SecretKey JWT_SECRET = Keys
            .hmacShaKeyFor("debug-key-must-be-at-least-32-characters-long-12345678".getBytes(StandardCharsets.UTF_8));
    private static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 24 hours

    public AuthTokens() {
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(JWT_SECRET)
                .compact();
    }

    public Optional<Claims> parseToken(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(JWT_SECRET).build().parseSignedClaims(token).getPayload();
            return Optional.of(claims);
        } catch (JwtException e) {
            return Optional.empty();
        }
    }
}
