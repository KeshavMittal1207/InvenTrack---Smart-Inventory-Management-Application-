package com.smartinventorymanagement.Auth_Service.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Component
public class JwtUtil {
    private static final String SECRET = "supersecretkey123";

    public static String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(SignatureAlgorithm.HS256,SECRET.getBytes())
                .compact();
    }

    public boolean validateToken(String token){
        try{
            getClaims(token);
            return true;
        }catch (Exception e ){
            return false;
        }
    }
    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String getUsernameFromToken(String token){
        return getClaims(token).getSubject();
    }

}
