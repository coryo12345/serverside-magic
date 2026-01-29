package mcservermagic.web.auth;

import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class AuthTokens {

    // server restarts will reset auth, which is okay i think
    private static final SecretKey JWT_SECRET = Jwts.SIG.HS256.key().build();
    private static final long JWT_EXPIRATION = 86400000; // 24 hours

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
