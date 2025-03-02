package com.espe.server.controller.user;

import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.Usuario;
import com.espe.server.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    
    private final UsuarioService usuarioService;
    
    public UsuarioController(UsuarioService usuarioService) {
    	this.usuarioService = usuarioService;
    }
    
    @GetMapping("/data-user")
    public ResponseEntity<Usuario> dataUser(HttpServletRequest request) {
        try {
        	String username = getUsernameFromCookies(request);
            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Optional<Usuario> usuario = usuarioService.findByUsername(username);
            return usuario.map(value -> ResponseEntity.ok().body(value))
                          .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario newUsuario) {
        try {
            Usuario usuarioCreado = usuarioService.createUser(newUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Actualizar un usuario existente
    @PutMapping
    public ResponseEntity<Usuario> updateUser(HttpServletRequest request, @RequestBody Usuario updatedUsuario) {
        try {
        	 String username = getUsernameFromCookies(request);
             if (username == null) {
                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
             }
            Optional<Usuario> usuarioActualizado = usuarioService.updateUser(username, updatedUsuario);
            if (usuarioActualizado.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(usuarioActualizado.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String getUsernameFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) { 
                    String token = cookie.getValue();
                    String[] parts = token.split("\\.");
                    if (parts.length == 3) {
                        String payload = parts[1];
                        // Agregar relleno para base64 si es necesario
                        int padding = payload.length() % 4;
                        if (padding > 0) {
                            payload += "=".repeat(4 - padding);
                        }
                        // Decodificar en base64
                        String decodedPayload = new String(Base64.getDecoder().decode(payload));
                        try {
                            // Usar Jackson para convertir el payload en un objeto Map
                            ObjectMapper objectMapper = new ObjectMapper();
                            Map<String, Object> payloadMap = objectMapper.readValue(decodedPayload, Map.class);
                            return (String) payloadMap.get("sub");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
    
}