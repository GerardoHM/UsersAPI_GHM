package com.chakray.api.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;

@Service
public class JwtService {
    private final String secret;

    // Constructor donde Spring inyectará el valor de jwt.secret desde la configuración
    public JwtService(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 🔐 GENERAR TOKEN
    public String generateToken(String taxId) {
        return Jwts.builder()
                .setSubject(taxId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey())
                .compact();
    }

    // 🔍 EXTRAER TAX_ID (y validar token)
    public String extractTaxId(String token) {
        return getClaims(token).getSubject();
    }

    // 🧠 VALIDAR TOKEN Y OBTENER CLAIMS
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // (opcional) validar expiración
    public boolean isTokenValid(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }
}
