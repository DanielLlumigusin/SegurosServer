package com.espe.server.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;
import com.espe.server.jwt.JwtService;
import com.espe.server.persistence.entity.TipoRol;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService userService;
    
    public AuthController(AuthenticationManager authenticationManager, UsuarioService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            Optional<Usuario> optionalUser = userService.findByUsername(request.getUsername());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
            }

            Usuario user = optionalUser.get();
            String token = jwtService.generateToken(user);

            // Configuración segura de la cookie
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // Cambiar a true en producción con HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);

            // Devolver solo información necesaria (sin el token)
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Login exitoso");
            responseBody.put("role", user.getRol().name());

            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }

    @GetMapping("/validate-admin")
    public ResponseEntity<?> validateAdmin(HttpServletRequest request) {
        return validateRole(request, TipoRol.ADMIN); // Solo ADMIN
    }

    private ResponseEntity<?> validateRole(HttpServletRequest request, TipoRol rolRequerido) {
        Cookie cookie = WebUtils.getCookie(request, "token");
        if (cookie == null) {
            return ResponseEntity.status(401).body("No autenticado");
        }

        String token = cookie.getValue();
        String role;
        
        try {
            role = jwtService.getClaim(token, claims -> claims.get("role", String.class));
            if (!jwtService.isTokenValid(token)) {
                return ResponseEntity.status(401).body("Token inválido");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido o expirado");
        }

        if (rolRequerido != null && !rolRequerido.name().equals(role)) {
            return ResponseEntity.status(403).body("Acceso denegado");
        }

        return ResponseEntity.ok(Map.of("message", "Autenticado", "role", role));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Verificar si el usuario está autenticado antes de limpiar la sesión
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            SecurityContextHolder.clearContext(); // Limpiar la autenticación en Spring Security
        }

        // Invalidar la sesión en el servidor si existe
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }

        // Eliminar cookie del token
        Cookie cookie = new Cookie("token", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // ⚠️ Cambiar a `true` en producción si usas HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("Logout exitoso");
    }
}
