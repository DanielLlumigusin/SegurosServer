package com.espe.server.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Obtener la cookie "token" del request
        Cookie cookie = WebUtils.getCookie(request, "token-admin");

        // Verificar si la cookie existe
        if (cookie != null) {
            String token = cookie.getValue();

            if (token != null && !token.isEmpty()) {
                try {
                    // Obtener el username del token
                    String username = jwtService.getUsernameFromToken(token);

                    // Verificar que el username sea válido
                    if (username != null && !username.isEmpty()) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                        // Verificar si el token es válido
                        if (jwtService.isTokenValid(token)) {
                            // Establecer la autenticación en el contexto de seguridad
                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        } else {
                            SecurityContextHolder.clearContext(); // Limpiar el contexto si el token no es válido
                        }
                    } else {
                        SecurityContextHolder.clearContext(); // Limpiar el contexto si el username es inválido
                    }

                } catch (Exception e) {
                    // Limpiar el contexto en caso de cualquier error
                    SecurityContextHolder.clearContext();
                    // Agregar logs de error para depuración (opcional)
                    logger.error("Error durante la autenticación del token", e);
                }
            }
        }

        // Continuar con la cadena de filtros
        chain.doFilter(request, response);
    }
}

