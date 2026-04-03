package com.smartinventorymanagement.ApiGateway_Service.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Component
public class JwtUtil {
    private final String SECRET = "a_very_long_and_secure_secret_key_at_least_32_bytes";

    public String generateToken(String username , String role){
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()),SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    public String extractRole(String token){
        return getClaims(token).get("role" , String.class);
    }

    public boolean validateToken(String token){
        try{
            getClaims(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }
    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
