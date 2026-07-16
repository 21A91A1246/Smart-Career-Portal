//package org.virtusa.EmployeesService.security;
//
//import jakarta.ws.rs.HttpMethod;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class Config {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .cors(cors -> {}) // ✅ modern replacement
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .requestMatchers("/api/employees/login").permitAll()
//                        .anyRequest().authenticated()
//                );
//
//        return http.build();
//    }
//}