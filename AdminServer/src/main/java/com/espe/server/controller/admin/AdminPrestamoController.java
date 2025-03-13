package com.espe.server.controller.admin;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.EstadoPrestamo;
import com.espe.server.persistence.entity.LogActividad;
import com.espe.server.persistence.entity.Prestamo;
import com.espe.server.service.LogActividadService;
import com.espe.server.service.PrestamoService;
import com.espe.server.service.TablaAmortizacionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/prestamos")
public class AdminPrestamoController {

    private final PrestamoService prestamoService;
    private final LogActividadService logActividadService;
    private final TablaAmortizacionService tablaAmortizacionService;
    public AdminPrestamoController(
    		PrestamoService prestamoService,
    		LogActividadService logActividadService,
            TablaAmortizacionService tablaAmortizacionService) {
    	this.prestamoService = prestamoService;
    	this.logActividadService = logActividadService;
        this.tablaAmortizacionService = tablaAmortizacionService;
    }
    
    // Obtener un préstamo por su ID
    @GetMapping("/{idPrestamo}")
    public ResponseEntity<Prestamo> findPrestamoById(@PathVariable Long idPrestamo) {
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
    public ResponseEntity<List<Prestamo>> findPrestamoAprobadoByUsuarioId(@PathVariable Long usuarioId) {
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
    public ResponseEntity<List<Prestamo>> findPrestamosSolicitadosByUsuarioId(@PathVariable Long usuarioId) {
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
    
    //Obtener todos los prestmos segun el usuario
    @GetMapping("/usuario")
    public ResponseEntity<List<Prestamo>> findAllPrestamosByUsuarioId(@RequestParam Long usuarioId) {
        try {
            List<Prestamo> prestamos = prestamoService.findAllPrestamosByUsuarioId(usuarioId);
            return ResponseEntity.status(HttpStatus.OK).body(prestamos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    @PutMapping
    public ResponseEntity<String> aprobarPrestamo(@RequestBody Prestamo prestamo) {
        if (prestamo == null || prestamo.getPrestamoId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos de préstamo inválidos");
        }

        Optional<Prestamo> prestamoOpt = prestamoService.findPrestamoById(prestamo.getPrestamoId());
        if (prestamoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Préstamo no encontrado");
        }

        Prestamo prestamoAux = prestamoOpt.get();

        try {
            boolean aprobado = prestamoService.aprobarPrestamo(prestamo);

            String mensaje;
            String tipoActividad;

            if (aprobado) {
                // Cambiar estado del préstamo
                prestamoAux.setEstadoPrestamo(EstadoPrestamo.APROBADO);
                prestamoService.updatePrestamo(prestamoAux); // Guardar los cambios

                // Generar y guardar la tabla de amortización
                tablaAmortizacionService.generarYGuardarTablaAmortizacion(prestamoAux);

                mensaje = "Se aprobó un préstamo";
                tipoActividad = "APROBAR PRESTAMO";
            } else {
                mensaje = "Se denegó la aprobación, ya tiene un préstamo APROBADO";
                tipoActividad = "ERROR APROBAR PRESTAMO";
            }

            // Log de la actividad
            LogActividad logActividad = new LogActividad(
                prestamoAux.getUsuario(),
                tipoActividad,
                LocalDate.now(),
                mensaje
            );

            logActividadService.createLog(logActividad);

            return aprobado
                ? ResponseEntity.ok("Préstamo aprobado")
                : ResponseEntity.status(HttpStatus.CONFLICT).body("El cliente ya tiene un préstamo aprobado");

        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la base de datos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al aprobar préstamo");
        }
    }




    // Actualizar un préstamo existente
    @PutMapping("/{idPrestamo}")
    public ResponseEntity<Prestamo> updatePrestamo(@RequestBody Prestamo updatedPrestamo) {
        try {
            Optional<Prestamo> prestamoActualizado = prestamoService.updatePrestamo(updatedPrestamo);
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
    public ResponseEntity<Void> deletePrestamo(@PathVariable Long idPrestamo, @RequestBody String username) {
        try {
            boolean eliminado = prestamoService.deletePrestamo(idPrestamo, username);
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