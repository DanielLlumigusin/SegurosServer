package com.espe.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.espe.server.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtRequestFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(csrf -> csrf.disable()) 
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                corsConfig.addAllowedOrigin("http://localhost:5173"); // Dominio del frontend
                corsConfig.addAllowedMethod("*"); // Permitir todos los métodos (GET, POST, etc.)
                corsConfig.addAllowedHeader("*"); // Permitir todos los headers
                corsConfig.setAllowCredentials(true); // Importante para permitir cookies HTTP-only
                return corsConfig;
            }))
            .httpBasic(Customizer.withDefaults()) 
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No usar sesiones en el backend
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/auth/register", "/auth/login", "/auth/admin/login").permitAll();
                auth.requestMatchers(HttpMethod.GET, "/auth/**").permitAll();
                auth.requestMatchers("/api/**", "/admin/**").authenticated(); // Protege rutas privadas
                auth.anyRequest().denyAll(); // Denegar cualquier otra petición
            })
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
