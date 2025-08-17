package com.shahed.authservice.auth_service.util;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shahed.authservice.auth_service.entity.Role;
import com.shahed.authservice.auth_service.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Component
@Getter
public class JwtUtil {

    private final Key key;
    private final long accessTokenExpMs;
    private final long refreshTokenExpMs;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-exp-ms}") long accessTokenExpMs,
            @Value("${jwt.refresh-token-exp-ms}") long refreshTokenExpMs) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("JWT secret must not be null or empty");
        }

        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        this.accessTokenExpMs = accessTokenExpMs;
        this.refreshTokenExpMs = refreshTokenExpMs;
    }

    public String generateAccessToken(User user) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getRoles().stream().map(Role::getName).toList())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + accessTokenExpMs))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(User user) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + refreshTokenExpMs))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public boolean isTokenExpired(String token) {
        try {
            Date exp = parseClaims(token).getBody().getExpiration();
            return exp.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token).getBody().getSubject();
    }
}
