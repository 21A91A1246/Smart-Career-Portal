package com.example.api_gateway.filter;

import com.example.api_gateway.exceptions.InvalidTokenException;
import com.example.api_gateway.exceptions.InvalidUserException;
import com.example.api_gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component

public class JwtFilter implements GlobalFilter {

    private JwtUtil jwtUtil;

    JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil=jwtUtil;
    }

    @Override
    //ServerWebExchange exchange=represents http request and response
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path=exchange.getRequest().getURI().getPath();

        System.out.println("PATH = " + path);


        if(path.contains("/api/users/login")){
            return chain.filter(exchange);
        }

        if(path.contains("/api/users/register")){
            return chain.filter(exchange);
        }
        //Reads the Authorization header from the HTTP request,Example header=Authorization: Bearer <JWT_TOKEN>
        String authHeader=exchange.getRequest().getHeaders().getFirst("Authorization");

        //If header is missing or does not start with "Bearer" throws exception
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            throw new InvalidTokenException("Missing Token");
        }

        //Authorization: Bearer abc.def.ghi
        //token = "abc.def.ghi"
        String token=authHeader.substring(7);

        System.out.println("TOKEN = " + token);

        //Returns claims (payload data)
        Claims claims= jwtUtil.validateToken(token);
        //Reads the role from JWT claims
        String role=claims.get("role",String.class);
        System.out.println("role======="+role);

        if(!isAllowed(role,path)) {
            throw new InvalidUserException("access denied role"+" "+role);
        }
        return chain.filter(exchange);
    }

    private boolean isAllowed(String role, String path) {
        switch (role) {
            // =========================
            // RECRUITER ROLE
            // =========================
            case "RECRUITER":
                return path.startsWith("/api/jobs") ||
                        path.startsWith("/api/users") ||
                        path.startsWith("/api/users/") ||
                        path.startsWith("/api/applications") ||
                        path.startsWith("/uploads/resumes")||
                        path.startsWith("/api/users/{userId}/profile");

            // =========================
            // USER ROLE
            // =========================
            case "USER":
                return path.startsWith("/api/users/{userId}/profile") ||
                        path.startsWith("/api/applications") ||
                        path.startsWith("/api/jobs") ||
                        path.startsWith("/api/v1/resumes");
            default:
                return false;
        }
    }



}