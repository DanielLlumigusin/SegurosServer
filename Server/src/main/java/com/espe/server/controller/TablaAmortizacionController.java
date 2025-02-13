package com.espe.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.TablaAmortizacion;
import com.espe.server.service.TablaAmortizacionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tablas-amortizacion")
public class TablaAmortizacionController {

    @Autowired
    private TablaAmortizacionService tablaAmortizacionService;

    // Obtener todas las entradas de la tabla de amortización
    @GetMapping
    public ResponseEntity<?> findAllTablasAmortizacion() {
        try {
            List<TablaAmortizacion> tablas = tablaAmortizacionService.findAllTablasAmortizacion();
            return ResponseEntity.status(HttpStatus.OK).body(tablas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las tablas de amortización: " + e.getMessage());
        }
    }

    // Obtener una entrada de la tabla de amortización por su ID
    @GetMapping("/{idAmortizacion}")
    public ResponseEntity<?> findTablaAmortizacionById(@PathVariable("idAmortizacion") Long idAmortizacion) {
        try {
            Optional<TablaAmortizacion> tabla = tablaAmortizacionService.findTablaAmortizacionById(idAmortizacion);
            if (tabla.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(tabla.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tabla de amortización no encontrada con ID: " + idAmortizacion);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la tabla de amortización: " + e.getMessage());
        }
    }

    // Crear una nueva entrada en la tabla de amortización
    @PostMapping
    public ResponseEntity<?> createTablaAmortizacion(@RequestBody TablaAmortizacion newTablaAmortizacion) {
        try {
            TablaAmortizacion tablaCreada = tablaAmortizacionService.createTablaAmortizacion(newTablaAmortizacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(tablaCreada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la tabla de amortización: " + e.getMessage());
        }
    }

    // Actualizar una entrada existente en la tabla de amortización
    @PutMapping("/{idAmortizacion}")
    public ResponseEntity<?> updateTablaAmortizacion(@PathVariable("idAmortizacion") Long idAmortizacion, @RequestBody TablaAmortizacion updatedTablaAmortizacion) {
        try {
            Optional<TablaAmortizacion> tablaActualizada = tablaAmortizacionService.updateTablaAmortizacion(idAmortizacion, updatedTablaAmortizacion);
            if (tablaActualizada.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(tablaActualizada.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tabla de amortización no encontrada con ID: " + idAmortizacion);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la tabla de amortización: " + e.getMessage());
        }
    }

    // Eliminar una entrada de la tabla de amortización por su ID
    @DeleteMapping("/{idAmortizacion}")
    public ResponseEntity<?> deleteTablaAmortizacion(@PathVariable("idAmortizacion") Long idAmortizacion) {
        try {
            boolean eliminado = tablaAmortizacionService.deleteTablaAmortizacion(idAmortizacion);
            if (eliminado) {
                return ResponseEntity.status(HttpStatus.OK).body("Tabla de amortización eliminada correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tabla de amortización no encontrada con ID: " + idAmortizacion);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la tabla de amortización: " + e.getMessage());
        }
    }

    // Obtener todas las entradas de la tabla de amortización asociadas a un préstamo
    @GetMapping("/prestamo/{idPrestamo}")
    public ResponseEntity<?> findTablasAmortizacionByPrestamoId(@PathVariable("idPrestamo") Long idPrestamo) {
        try {
            List<TablaAmortizacion> tablas = tablaAmortizacionService.findTablasAmortizacionByPrestamoId(idPrestamo);
            return ResponseEntity.status(HttpStatus.OK).body(tablas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las tablas de amortización: " + e.getMessage());
        }
    }
}