package com.espe.server.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.espe.server.jwt.JwtService;
import com.espe.server.persistence.entity.TokenRecovery;
import com.espe.server.persistence.entity.UpdatePasswordRequest;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.service.MailService;
import com.espe.server.service.TokenRecoveryService;
import com.espe.server.service.UsuarioService;
import com.espe.server.utils.InfoCookie;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService userService;
    private final TokenRecoveryService tokenRecoveryService;
    private final MailService mailService;
    
    public AuthController(
    		AuthenticationManager authenticationManager, 
    		UsuarioService userService,
    		TokenRecoveryService tokenRecoveryService,
    		JwtService jwtService,
    		MailService mailService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenRecoveryService = tokenRecoveryService;
        this.jwtService = jwtService;
        this.mailService = mailService;
    }
    
    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }
        return ResponseEntity.ok("Autenticado");
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
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Login exitoso");
            responseBody.put("role", user.getRol().name());

            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

        // Eliminar cookie del token
        Cookie cookie = new Cookie("token", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("Logout exitoso");
    }
    
    @GetMapping("/reset-password")
    public ResponseEntity<?> verifyResetPassword(@RequestParam String token) {
    	
    	
    	System.out.print(token);
    	
    	if (!tokenRecoveryService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Token no válido o expirado");
        }

    	Optional<TokenRecovery> tokenOpt = tokenRecoveryService.findByToken(token);
    	
    	if (tokenOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token no encontrado");
        }
    	String username = tokenOpt.get().getUsuario().getUsername();
        
        if (username == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        return ResponseEntity.ok("Token válido, puedes proceder con el cambio de contraseña");
    }

    
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody UpdatePasswordRequest request) {
        
    	Optional<TokenRecovery> tokenOpt = tokenRecoveryService.findByToken(request.getToken());

        if (tokenOpt.isEmpty() || tokenOpt.get().isUsed()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido o expirado");
        }
        
        Optional<Usuario> usuarioOpt = userService.findByUsername(tokenOpt.get().getUsuario().getUsername());
    	
    	if(!usuarioOpt.isPresent()) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no esta registrado");
    	}
        
        
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Las contraseñas no coinciden");
        }
        
        
        try {
        	TokenRecovery token = tokenOpt.get();
        	userService.updatePassword(request.getNewPassword(), token.getUsuario().getUsername());
        	
        	tokenRecoveryService.markTokenAsUsed(token.getToken());
        	            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la contraseña"+ e);
        }

        return ResponseEntity.ok("Contraseña actualizada exitosamente.");
    }



    @PostMapping("/send-email")
    public ResponseEntity<?> sendRecoveryEmail(@RequestBody Map<String, String> requestBody) {
        
        // Extraer el username del request
        String username = requestBody.get("username");

        // Validar si el username es nulo o vacío
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo no puede estar vacío");
        }

        Optional<Usuario> usuarioOpt = userService.findByUsername(username);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        // Generar el token de recuperación
        String token = UUID.randomUUID().toString();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);
        
        try {
            tokenRecoveryService.saveToken(token, usuario, expirationTime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el token");
        }

        // Enviar correo con el enlace para la recuperación
        String recoveryUrl = "http://10.40.10.45:3000/reset-password?token=" + token;
        mailService.sendMessage(username, "Recuperación de contraseña: Usa este enlace para restablecer tu contraseña: " + recoveryUrl);

        return ResponseEntity.ok("Se ha enviado un enlace de recuperación a tu correo.");
    }

}
