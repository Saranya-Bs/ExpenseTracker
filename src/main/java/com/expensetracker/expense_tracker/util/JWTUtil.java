package com.expensetracker.expense_tracker.util;

import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Service;

import com.expensetracker.expense_tracker.entity.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTUtil {

    private String key="this-is-a-secret-key-used-for-authentication-of-our-user-123456@!!";

    public String generateToken(String username) {
        long EXPIRY_TIME=1000*60*60;
        return Jwts.builder()
                    .subject(username)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+EXPIRY_TIME))
                    .signWith(getKey(),SignatureAlgorithm.HS256)
                    .compact();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    public Claims extractClaims(String token){
        Claims body= Jwts.parser()
                        .setSigningKey(getKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
        return body;
    }

    public boolean validateToken(String username, CustomUserDetails details,String token) {
            System.out.println("Token Validation!!!!");
            System.out.println(username);
            System.out.println("......................");
            System.out.println(details.getUser().getEmail());
        
           return username.equals(details.getUser().getEmail()) && !isTokenExpired(token);
       }

    private boolean isTokenExpired(String token) {
        System.out.println("Token !!!!!!!!!!!!!!!!!!!!!!!!!");
        return extractClaims(token).getExpiration().before(new Date());
    }
}
    
