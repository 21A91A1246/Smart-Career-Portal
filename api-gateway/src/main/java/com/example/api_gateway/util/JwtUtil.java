package com.example.api_gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    // Secure key for HS256
    private static final String SECRET = "mysecretkeymysecretkeymysecretkey"; // same as auth-service
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());  // Generate token


    // Validate token
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // must be SecretKey object
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract username/email
    public String getUsername(String token) {
        return validateToken(token).getSubject();
    }
}