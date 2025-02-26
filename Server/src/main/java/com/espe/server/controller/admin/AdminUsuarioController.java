package com.espe.server.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.Usuario;
import com.espe.server.service.UsuarioService;

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
    
    //Obtener un usuario por username
    @GetMapping("/username")
    public ResponseEntity<Usuario> findUser(@RequestParam String username) {
        try {
            Optional<Usuario> usuario = usuarioService.findByUsername(username);
            if (usuario.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
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
    @PutMapping("/{idUser}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Long idUser, @RequestBody Usuario updatedUsuario) {
        try {
            Optional<Usuario> usuarioActualizado = usuarioService.updateUser(idUser, updatedUsuario);
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
    @DeleteMapping("/{idUser}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long idUser) {
        try {
            boolean eliminado = usuarioService.deleteUser(idUser);
            if (eliminado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}