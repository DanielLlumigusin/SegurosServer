package com.espe.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.Prestamo;
import com.espe.server.service.PrestamoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    // Obtener un préstamo por su ID
    @GetMapping("/{idPrestamo}")
    public ResponseEntity<?> findPrestamoById(@PathVariable("idPrestamo") Long idPrestamo) {
        try {
            Optional<Prestamo> prestamo = prestamoService.findPrestamoById(idPrestamo);
            if (prestamo.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(prestamo.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Préstamo no encontrado con ID: " + idPrestamo);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el préstamo: " + e.getMessage());
        }
    }
    
    @GetMapping("/usuario/{usuarioId}/aprobado")
    public ResponseEntity<?> findPrestamoAprobadoByUsuarioId(@PathVariable("usuarioId") Long usuarioId) {
        try {
            List<Prestamo> prestamo = prestamoService.findPrestamoAprobadoByUsuarioId(usuarioId);
            if (!prestamo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(prestamo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró un préstamo aprobado para el usuario con ID: " + usuarioId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el préstamo: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}/solicitados")
    public ResponseEntity<?> findPrestamosSolicitadosByUsuarioId(@PathVariable("usuarioId") Long usuarioId) {
        try {
            List<Prestamo> prestamos = prestamoService.findPrestamosSolicitadosByUsuarioId(usuarioId);
            if (!prestamos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(prestamos);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron préstamos solicitados para el usuario con ID: " + usuarioId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los préstamos: " + e.getMessage());
        }
    }

    // Obtener todos los préstamos
    @GetMapping
    public ResponseEntity<?> findAllPrestamos() {
        try {
            List<Prestamo> prestamos = prestamoService.findAllPrestamos();
            return ResponseEntity.status(HttpStatus.OK).body(prestamos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los préstamos: " + e.getMessage());
        }
    }

    // Crear un nuevo préstamo
    @PostMapping
    public ResponseEntity<?> createPrestamo(@RequestBody Prestamo newPrestamo) {
        try {
            Prestamo prestamoCreado = prestamoService.createPrestamo(newPrestamo);
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamoCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el préstamo: " + e.getMessage());
        }
    }

    // Actualizar un préstamo existente
    @PutMapping("/{idPrestamo}")
    public ResponseEntity<?> updatePrestamo(@PathVariable("idPrestamo") Long idPrestamo, @RequestBody Prestamo updatedPrestamo) {
        try {
            Optional<Prestamo> prestamoActualizado = prestamoService.updatePrestamo(idPrestamo, updatedPrestamo);
            if (prestamoActualizado.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(prestamoActualizado.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Préstamo no encontrado con ID: " + idPrestamo);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el préstamo: " + e.getMessage());
        }
    }

    // Eliminar un préstamo por su ID
    @DeleteMapping("/{idPrestamo}")
    public ResponseEntity<?> deletePrestamo(@PathVariable("idPrestamo") Long idPrestamo) {
        try {
            boolean eliminado = prestamoService.deletePrestamo(idPrestamo);
            if (eliminado) {
                return ResponseEntity.status(HttpStatus.OK).body("Préstamo eliminado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Préstamo no encontrado con ID: " + idPrestamo);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el préstamo: " + e.getMessage());
        }
    }
}