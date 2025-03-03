package com.espe.server.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.Usuario;
import com.espe.server.service.UsuarioService;
import com.espe.server.utils.InfoCookie;
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
        	InfoCookie infoCookie = new InfoCookie();
       	 	String username = infoCookie.getUsernameFromCookies(request);
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
        	 InfoCookie infoCookie = new InfoCookie();
        	 String username = infoCookie.getUsernameFromCookies(request);
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

    
}