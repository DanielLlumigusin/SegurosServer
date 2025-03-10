package com.espe.server.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.Pago;
import com.espe.server.service.PagoService;

import java.math.BigDecimal;
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
    public ResponseEntity<Pago> findPagoById(@PathVariable Long idPago) {
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

    
    @PostMapping("/{id}/pago")
    public String registrarPago(@PathVariable Long id, @RequestParam int numeroPago, @RequestParam BigDecimal montoPago) {
        boolean exito = pagoService.registrarPago(id, numeroPago, montoPago);
        return exito ? "Pago registrado correctamente" : "Error al registrar el pago";
    }

    // Eliminar un pago por su ID
    @DeleteMapping("/{idPago}")
    public ResponseEntity<Void> deletePago(@PathVariable Long idPago, @RequestBody String username) {
        try {
            boolean eliminado = pagoService.deletePago(idPago, username);
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
    public ResponseEntity<List<Pago>> findPagosByPrestamoId(@PathVariable Long idPrestamo) {
        try {
            List<Pago> pagos = pagoService.findPagosByPrestamoId(idPrestamo);
            return ResponseEntity.status(HttpStatus.OK).body(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Aprobar pago
    @PutMapping("/{idPago}/aprobar")
    public ResponseEntity<String> aprobarPago(@PathVariable Long idPago) {
        boolean exito = pagoService.aprobarPago(idPago);
        if (exito) {
            return ResponseEntity.status(HttpStatus.OK).body("Pago aprobado y registrado en la tabla de amortización");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo aprobar el pago");
        }
    }
}