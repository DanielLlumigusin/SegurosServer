package com.espe.server.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.TablaAmortizacion;
import com.espe.server.service.TablaAmortizacionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/tablas-amortizacion")
public class AdminTablasAmortizacion {

    private final TablaAmortizacionService tablaAmortizacionService;
    
    public AdminTablasAmortizacion(TablaAmortizacionService tablaAmortizacionService) {
    	this.tablaAmortizacionService = tablaAmortizacionService;
    }

    // Obtener todas las entradas de la tabla de amortización
    @GetMapping
    public ResponseEntity<List<TablaAmortizacion>> findAllTablasAmortizacion() {
        try {
            List<TablaAmortizacion> tablas = tablaAmortizacionService.findAllTablasAmortizacion();
            return ResponseEntity.ok(tablas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Obtener una entrada de la tabla de amortización por su ID
    @GetMapping("/{idAmortizacion}")
    public ResponseEntity<TablaAmortizacion> findTablaAmortizacionById(@PathVariable Long idAmortizacion) {
        try {
            Optional<TablaAmortizacion> tabla = tablaAmortizacionService.findTablaAmortizacionById(idAmortizacion);
            if (tabla.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(tabla.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Actualizar una entrada existente en la tabla de amortización
    @PutMapping("/{idAmortizacion}")
    public ResponseEntity<TablaAmortizacion> updateTablaAmortizacion(@PathVariable Long idAmortizacion, @RequestBody TablaAmortizacion updatedTablaAmortizacion) {
        try {
            Optional<TablaAmortizacion> tablaActualizada = tablaAmortizacionService.updateTablaAmortizacion(idAmortizacion, updatedTablaAmortizacion);
            if (tablaActualizada.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(tablaActualizada.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar una entrada de la tabla de amortización por su ID
    @DeleteMapping("/{idAmortizacion}")
    public ResponseEntity<Void> deleteTablaAmortizacion(@PathVariable Long idAmortizacion) {
        try {
            boolean eliminado = tablaAmortizacionService.deleteTablaAmortizacion(idAmortizacion);
            if (eliminado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener todas las entradas de la tabla de amortización asociadas a un préstamo
    @GetMapping("/prestamo/{idPrestamo}")
    public ResponseEntity<List<TablaAmortizacion>> findTablasAmortizacionByPrestamoId(@PathVariable Long idPrestamo) {
        try {
            List<TablaAmortizacion> tablas = tablaAmortizacionService.findTablasAmortizacionByPrestamoId(idPrestamo);
            return ResponseEntity.status(HttpStatus.OK).body(tablas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}