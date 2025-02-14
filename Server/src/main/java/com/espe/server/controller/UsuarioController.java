package com.espe.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.Usuario;
import com.espe.server.service.UsuarioService;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<?> findAllUsers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAllUsers());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los usuarios: " + e.getMessage());
        }
    }

    // Obtener un usuario por su ID
    @GetMapping("/{idUser}")
    public ResponseEntity<?> findUser(@PathVariable("idUser") Long idUser) {
        try {
            Optional<Usuario> usuario = usuarioService.findByIdUsuario(idUser);
            if (usuario.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con ID: " + idUser);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el usuario: " + e.getMessage());
        }
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Usuario newUsuario) {
        try {
            Usuario usuarioCreado = usuarioService.createUser(newUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el usuario: " + e.getMessage());
        }
    }

    // Actualizar un usuario existente
    @PutMapping("/{idUser}")
    public ResponseEntity<?> updateUser(@PathVariable("idUser") Long idUser, @RequestBody Usuario updatedUsuario) {
        try {
            Optional<Usuario> usuarioActualizado = usuarioService.updateUser(idUser, updatedUsuario);
            if (usuarioActualizado.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(usuarioActualizado.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con ID: " + idUser);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    // Eliminar un usuario por su ID
    @DeleteMapping("/{idUser}")
    public ResponseEntity<?> deleteUser(@PathVariable("idUser") Long idUser) {
        try {
            boolean eliminado = usuarioService.deleteUser(idUser);
            if (eliminado) {
                return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con ID: " + idUser);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario: " + e.getMessage());
        }
    }


}