package com.espe.server.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.Pago;
import com.espe.server.service.PagoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/pagos")
public class AdminPagoController {

    private final PagoService pagoService;

    public AdminPagoController(PagoService pagoService) {
    	this.pagoService = pagoService;
    }
    
    
    // Obtener todos los pagos
    @GetMapping
    public ResponseEntity<List<Pago>> findAllPagos() {
        try {
            List<Pago> pagos = pagoService.findAllPagos();
            return ResponseEntity.status(HttpStatus.OK).body(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un pago por su ID
    @GetMapping("/{idPago}")
    public ResponseEntity<Pago> findPagoById(@PathVariable("idPago") Long idPago) {
        try {
            Optional<Pago> pago = pagoService.findPagoById(idPago);
            if (pago.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(pago.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear un nuevo pago
    @PostMapping
    public ResponseEntity<Pago> createPago(@RequestBody Pago newPago) {
        try {
            Pago pagoCreado = pagoService.createPago(newPago);
            return ResponseEntity.status(HttpStatus.CREATED).body(pagoCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Actualizar un pago existente
    @PutMapping("/{idPago}")
    public ResponseEntity<Pago> updatePago(@PathVariable("idPago") Long idPago, @RequestBody Pago updatedPago) {
        try {
            Optional<Pago> pagoActualizado = pagoService.updatePago(idPago, updatedPago);
            if (pagoActualizado.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(pagoActualizado.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar un pago por su ID
    @DeleteMapping("/{idPago}")
    public ResponseEntity<Void> deletePago(@PathVariable("idPago") Long idPago) {
        try {
            boolean eliminado = pagoService.deletePago(idPago);
            if (eliminado) {
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener todos los pagos asociados a un préstamo
    @GetMapping("/prestamo/{idPrestamo}")
    public ResponseEntity<List<Pago>> findPagosByPrestamoId(@PathVariable("idPrestamo") Long idPrestamo) {
        try {
            List<Pago> pagos = pagoService.findPagosByPrestamoId(idPrestamo);
            return ResponseEntity.status(HttpStatus.OK).body(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}