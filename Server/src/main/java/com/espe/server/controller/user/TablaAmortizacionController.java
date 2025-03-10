package com.espe.server.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.TablaAmortizacion;
import com.espe.server.service.TablaAmortizacionService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tablas-amortizacion")
public class TablaAmortizacionController {

    private final TablaAmortizacionService tablaAmortizacionService;

    public TablaAmortizacionController(TablaAmortizacionService tablaAmortizacionService) {
    	this.tablaAmortizacionService = tablaAmortizacionService;
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


    // Obtener todas las entradas de la tabla de amortización asociadas a un préstamo
    @GetMapping("/prestamo")
    public ResponseEntity<List<TablaAmortizacion>> findTablasAmortizacionByPrestamoId(HttpServletRequest request) {
        try {
            List<TablaAmortizacion> tablas = tablaAmortizacionService.findTablasAmortizacionByPrestamoId(request);
            return ResponseEntity.status(HttpStatus.OK).body(tablas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Crear una nueva entrada en la tabla de amortización
    @PostMapping
    public ResponseEntity<TablaAmortizacion> createTablaAmortizacion(@RequestBody TablaAmortizacion newTablaAmortizacion) {
    	try {
    		TablaAmortizacion tablaCreada = tablaAmortizacionService.createTablaAmortizacion(newTablaAmortizacion);
    		return ResponseEntity.status(HttpStatus.CREATED).body(tablaCreada);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	}
    }
}