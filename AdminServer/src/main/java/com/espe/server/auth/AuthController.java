package com.espe.server.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.espe.server.jwt.JwtService;
import com.espe.server.persistence.entity.LogActividad;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.service.LogActividadService;
import com.espe.server.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService userService;
    private final LogActividadService logActividadService;
    
    public AuthController(
    		AuthenticationManager authenticationManager, 
    		UsuarioService userService, 
    		JwtService jwtService,
    		LogActividadService logActividadService
    		) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.logActividadService = logActividadService;
    }
    
    @GetMapping("/check")
    public ResponseEntity<String> checkAuth(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Autenticado");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletResponse response) {
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
            
            LogActividad logActividad = new LogActividad(user,"Iniciar sesion", LocalDate.now(),"El usuario acaba de iniciar sesión exitoso");
            logActividadService.createLog(logActividad);
            
            return ResponseEntity.status(HttpStatus.OK).body("Login Exitoso");
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {

        // Eliminar cookie del token
        Cookie cookie = new Cookie("token", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); 
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).body("Exito en Cerrar Sesion");
    }
}
