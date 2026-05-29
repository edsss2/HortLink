package com.devf.hortilink.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    long tempoExpiracao = 1000L * 60 * 60 * 24 * 30;
    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 15; //15 dias

    // Crie a chave uma única vez (mantida enquanto a aplicação roda)
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Gera token adicionando claims importantes: userId, role e opcionalmente commerceId
    public String generateToken(String username, Long userId, String role, Long commerceId) {
        Map<String, Object> claims = new HashMap<>();
        if (userId != null) claims.put("userId", userId);
        if (role != null) claims.put("role", role);
        if (commerceId != null) claims.put("commerceId", commerceId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Método privado para extrair todas as "claims" (informações) do token
    private Claims extractAllClaims(String token) {
        // Usa a API compatível com a versão presente no projeto
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        Object v = extractAllClaims(token).get("userId");
        if (v == null) return null;
        if (v instanceof Integer) return ((Integer) v).longValue();
        if (v instanceof Long) return (Long) v;
        return Long.valueOf(v.toString());
    }

    public String extractRole(String token) {
        Object v = extractAllClaims(token).get("role");
        return v != null ? v.toString() : null;
    }

    public Long extractCommerceId(String token) {
        Object v = extractAllClaims(token).get("commerceId");
        if (v == null) return null;
        if (v instanceof Integer) return ((Integer) v).longValue();
        if (v instanceof Long) return (Long) v;
        return Long.valueOf(v.toString());
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (JwtException e) {
            return false;
        }
    }
}