package com.espe.server.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.Usuario;
import com.espe.server.service.UsuarioService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    private final UsuarioService usuarioService;

    public AdminUsuarioController(UsuarioService usuarioService) {
    	this.usuarioService = usuarioService;
    }
    
    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> findAllUsers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAllUsers());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un usuario por su ID
    @GetMapping("/{idUser}")
    public ResponseEntity<Usuario> findUser(@PathVariable Long idUser) {
        try {
            Optional<Usuario> usuario = usuarioService.findByIdUsuario(idUser);
            if (usuario.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Actualizar un usuario existente
    @PutMapping("/{idUser}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Long idUser, @RequestBody Usuario updatedUsuario, @RequestBody String username) {
        try {
            Optional<Usuario> usuarioActualizado = usuarioService.updateUser(idUser, updatedUsuario, username);
            if (usuarioActualizado.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(usuarioActualizado.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar un usuario por su ID
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deleteUser(HttpServletRequest request, @PathVariable Long usuarioId) {
        try {
        	
            String username = getUsernameFromCookies(request);
            
            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            boolean eliminado = usuarioService.deleteUser(usuarioId, username);
            
            if (eliminado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
 // Método para obtener el username de las cookies
    private String getUsernameFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sub".equals(cookie.getName())) { // Ajusta el nombre del cookie según lo que uses
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


}