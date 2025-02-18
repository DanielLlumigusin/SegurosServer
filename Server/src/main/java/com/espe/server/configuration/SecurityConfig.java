package com.espe.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.espe.server.Jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationFilter jwtRequestFilter; // Filtro JWT

    // Configuración de CORS para permitir solicitudes desde el frontend
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:5173") // Origen permitido (tu frontend)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
            .allowCredentials(true) // Permite credenciales (cookies, tokens)
            .allowedHeaders("*"); // Encabezados permitidos
    }

    // Configuración de seguridad para los endpoints
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(csrf -> csrf.disable()) // Deshabilita CSRF
            .cors(Customizer.withDefaults()) // Habilita CORS con la configuración definida en addCorsMappings
            .httpBasic(Customizer.withDefaults()) // Configura autenticación básica (opcional)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless JWT
            .authorizeHttpRequests(http -> {
                // Endpoints públicos (se permiten sin autenticación)
                http.requestMatchers("/auth/register", "/auth/login").permitAll();
                http.requestMatchers(HttpMethod.GET, "/auth/**").permitAll();
                
                http.requestMatchers("/api/**").authenticated();
                http.requestMatchers("/admin/**").authenticated();
                // Todos los demás requieren autenticación
                http.anyRequest().denyAll();
            })
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class) // Agrega el filtro JWT
            .build();
    }
}