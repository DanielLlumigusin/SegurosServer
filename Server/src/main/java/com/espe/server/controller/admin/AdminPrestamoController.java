package com.espe.server.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.Prestamo;
import com.espe.server.service.PrestamoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/prestamos")
public class AdminPrestamoController {

    private final PrestamoService prestamoService;

    public AdminPrestamoController(PrestamoService prestamoService) {
    	this.prestamoService = prestamoService;
    }
    
    // Obtener un préstamo por su ID
    @GetMapping("/{idPrestamo}")
    public ResponseEntity<Prestamo> findPrestamoById(@PathVariable("idPrestamo") Long idPrestamo) {
        try {
            Optional<Prestamo> prestamo = prestamoService.findPrestamoById(idPrestamo);
            if (prestamo.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(prestamo.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/usuario/{usuarioId}/aprobado")
    public ResponseEntity<List<Prestamo>> findPrestamoAprobadoByUsuarioId(@PathVariable("usuarioId") Long usuarioId) {
        try {
            List<Prestamo> prestamo = prestamoService.findPrestamoAprobadoByUsuarioId(usuarioId);
            if (!prestamo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(prestamo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}/solicitados")
    public ResponseEntity<List<Prestamo>> findPrestamosSolicitadosByUsuarioId(@PathVariable("usuarioId") Long usuarioId) {
        try {
            List<Prestamo> prestamos = prestamoService.findPrestamosSolicitadosByUsuarioId(usuarioId);
            if (!prestamos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(prestamos);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener todos los préstamos
    @GetMapping
    public ResponseEntity<List<Prestamo>> findAllPrestamos() {
        try {
            List<Prestamo> prestamos = prestamoService.findAllPrestamos();
            return ResponseEntity.status(HttpStatus.OK).body(prestamos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear un nuevo préstamo
    @PostMapping
    public ResponseEntity<Prestamo> createPrestamo(@RequestBody Prestamo newPrestamo) {
        try {
            Prestamo prestamoCreado = prestamoService.createPrestamo(newPrestamo);
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamoCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Actualizar un préstamo existente
    @PutMapping("/{idPrestamo}")
    public ResponseEntity<Prestamo> updatePrestamo(@PathVariable("idPrestamo") Long idPrestamo, @RequestBody Prestamo updatedPrestamo) {
        try {
            Optional<Prestamo> prestamoActualizado = prestamoService.updatePrestamo(idPrestamo, updatedPrestamo);
            if (prestamoActualizado.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(prestamoActualizado.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar un préstamo por su ID
    @DeleteMapping("/{idPrestamo}")
    public ResponseEntity<Void> deletePrestamo(@PathVariable("idPrestamo") Long idPrestamo) {
        try {
            boolean eliminado = prestamoService.deletePrestamo(idPrestamo);
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