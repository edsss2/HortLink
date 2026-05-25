package com.devf.hortilink.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims; // Importe
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	long tempoExpiracao = 1000L * 60 * 60 * 24 * 30;
    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 15; //15 dias

    // Crie a chave uma única vez
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // --- CORREÇÃO AQUI ---
    // Método privado para extrair todas as "claims" (informações) do token
    private Claims extractAllClaims(String token) {
        // A nova API usa parser() e verifyWith()
        return Jwts.parser()
                .verifyWith(key) // 1. Use verifyWith(key)
                .build()
                .parseSignedClaims(token) // 2. Use parseSignedClaims
                .getPayload(); // 3. Use getPayload()
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
    
    public boolean validateToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            // 4. O parser já vai falhar se o token estiver expirado,
            //    então a verificação dupla não é estritamente necessária,
            //    mas esta lógica está correta.
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (JwtException e) {
            // Se o token estiver expirado, com assinatura errada, etc.,
            // o extractAllClaims() vai lançar uma exceção.
            return false;
        }
    }
}