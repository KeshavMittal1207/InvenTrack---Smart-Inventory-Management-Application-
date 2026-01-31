package com.smartinventorymanagement.ApiGateway_Service.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtUtil {
    private final String SECRET_KEY = "supersecretkey123";

    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String getUsernameFromToken(String token){
        return getClaims(token).getSubject();
    }
}
