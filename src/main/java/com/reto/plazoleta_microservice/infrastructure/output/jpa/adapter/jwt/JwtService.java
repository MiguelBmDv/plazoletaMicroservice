package com.reto.plazoleta_microservice.infrastructure.output.jpa.adapter.jwt;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKeyJwt;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKeyJwt.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesObject = claims.get("roles");
    
        if (rolesObject instanceof List<?>) {
            return ((List<?>) rolesObject).stream()
                .filter(role -> role instanceof Map)
                .map(role -> ((Map<?, ?>) role).get("authority"))
                .filter(authority -> authority instanceof String)
                .map(authority -> (String) authority)
                .collect(Collectors.toList());
        }
    
        return List.of(); 
    }

    public Long extractDocumentNumber(String token) {
        Claims claims = extractAllClaims(token);
        Object documentNumberClaim = claims.get("documentNumber");
    
        if (documentNumberClaim instanceof Integer) {
            return ((Integer) documentNumberClaim).longValue();
        } else {
            throw new IllegalStateException("El documentNumber no es un tipo numérico válido");
        }
    }

}
