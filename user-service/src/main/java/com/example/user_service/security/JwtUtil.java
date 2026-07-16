package com.example.user_service.security;

import com.example.user_service.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "mysecretkeymysecretkeymysecretkey"; // same as auth-service
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(User user) {
        long expirationTime = 1000 * 60 * 60; // 1 hour

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("empId",user.getId())
                .claim("role", user.getRole())
                .claim("name",user.getFirstName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key) // Use SecretKey, not String
                .compact();
    }
}