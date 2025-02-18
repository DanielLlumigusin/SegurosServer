package com.espe.server.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PagoService pagoService;

    // Obtener todos los pagos
    @GetMapping
    public ResponseEntity<?> findAllPagos() {
        try {
            List<Pago> pagos = pagoService.findAllPagos();
            return ResponseEntity.status(HttpStatus.OK).body(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los pagos: " + e.getMessage());
        }
    }

    // Obtener un pago por su ID
    @GetMapping("/{idPago}")
    public ResponseEntity<?> findPagoById(@PathVariable("idPago") Long idPago) {
        try {
            Optional<Pago> pago = pagoService.findPagoById(idPago);
            if (pago.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(pago.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no encontrado con ID: " + idPago);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el pago: " + e.getMessage());
        }
    }

    // Crear un nuevo pago
    @PostMapping
    public ResponseEntity<?> createPago(@RequestBody Pago newPago) {
        try {
            Pago pagoCreado = pagoService.createPago(newPago);
            return ResponseEntity.status(HttpStatus.CREATED).body(pagoCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el pago: " + e.getMessage());
        }
    }

    // Actualizar un pago existente
    @PutMapping("/{idPago}")
    public ResponseEntity<?> updatePago(@PathVariable("idPago") Long idPago, @RequestBody Pago updatedPago) {
        try {
            Optional<Pago> pagoActualizado = pagoService.updatePago(idPago, updatedPago);
            if (pagoActualizado.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(pagoActualizado.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no encontrado con ID: " + idPago);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el pago: " + e.getMessage());
        }
    }

    // Eliminar un pago por su ID
    @DeleteMapping("/{idPago}")
    public ResponseEntity<?> deletePago(@PathVariable("idPago") Long idPago) {
        try {
            boolean eliminado = pagoService.deletePago(idPago);
            if (eliminado) {
                return ResponseEntity.status(HttpStatus.OK).body("Pago eliminado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no encontrado con ID: " + idPago);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el pago: " + e.getMessage());
        }
    }

    // Obtener todos los pagos asociados a un préstamo
    @GetMapping("/prestamo/{idPrestamo}")
    public ResponseEntity<?> findPagosByPrestamoId(@PathVariable("idPrestamo") Long idPrestamo) {
        try {
            List<Pago> pagos = pagoService.findPagosByPrestamoId(idPrestamo);
            return ResponseEntity.status(HttpStatus.OK).body(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los pagos: " + e.getMessage());
        }
    }
}