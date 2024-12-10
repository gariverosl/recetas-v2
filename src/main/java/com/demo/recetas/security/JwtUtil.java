package com.demo.recetas.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys; // Importación añadida

@Component
public class JwtUtil {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 horas

    // Se elimina el SecurityContextHolder, ya que no se usa en ningún lugar.
    
    public static SecretKey getSecretKey() {
        return SECRET_KEY;
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("created", new Date());
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new JwtException("Error al procesar el token: " + e.getMessage());
        }
    }

    public String extractUsername(String token) {
        try {
            return extractClaims(token).getSubject();
        } catch (Exception e) {
            throw new JwtException("Error al extraer username del token");
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            Date expiration = extractClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isTokenValid(String token, String username) {
        try {
            final String usernameFromToken = extractUsername(token);
            return (username.equals(usernameFromToken) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    public String refreshToken(String token) {
        try {
            Claims claims = extractClaims(token);
            claims.setIssuedAt(new Date());
            claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));
            return createToken(claims, claims.getSubject());
        } catch (Exception e) {
            throw new JwtException("Error al refrescar token");
        }
    }

    public void validateToken(String token) {
        if (token == null) {
            throw new JwtException("Token no proporcionado");
        }
        if (isTokenExpired(token)) {
            throw new JwtException("Token expirado");
        }
        // Si llega aquí, el token es válido
    }
}
