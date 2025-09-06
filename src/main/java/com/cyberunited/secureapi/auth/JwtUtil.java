package com.cyberunited.secureapi.auth;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final SecretKey key = Keys.hmacShaKeyFor("super-secret-key-for-demo-super-secret".getBytes());

    public String generateToken(Long id, String username, Set<String> roles) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(username)
                .claim("id", id)
                .claim("roles", roles)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(300)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public JwtParser parser() {
        return Jwts.parser().verifyWith(key).build();
    }
}
