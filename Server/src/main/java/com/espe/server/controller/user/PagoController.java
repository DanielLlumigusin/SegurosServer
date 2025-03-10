package com.espe.server.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.Pago;
import com.espe.server.persistence.entity.Prestamo;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.service.PagoService;
import com.espe.server.service.PrestamoService;
import com.espe.server.service.UsuarioService;
import com.espe.server.utils.InfoCookie;

import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;
    private final PrestamoService prestamoService;
    private final UsuarioService usuarioService;
    public PagoController(
    		PagoService pagoService,
    		PrestamoService prestamoService,
    		UsuarioService usuarioService
    		) {
    	this.pagoService = pagoService;
    	this.prestamoService = prestamoService;
    	this.usuarioService = usuarioService;
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
    
    @PostMapping("/solicitar")
    public ResponseEntity<String> solicitarPago(@RequestParam Long prestamoId, @RequestParam BigDecimal montoPago) {
        boolean exito = pagoService.registrarSolicitudPago(prestamoId, montoPago);
        
        if (exito) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Solicitud de pago registrada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar la solicitud de pago");
        }
    }
    

 // Obtener todos los pagos asociados a un préstamo
    @GetMapping("/prestamo")
    public ResponseEntity<?> findPagosByPrestamoId(HttpServletRequest httpRequest) {
        try {
            InfoCookie infoCookie = new InfoCookie();
            String username = infoCookie.getUsernameFromCookies(httpRequest);

            Optional<Usuario> usuarioOpt = usuarioService.findByUsername(username);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
            }

            Usuario usuario = usuarioOpt.get();
            List<Prestamo> prestamosAprobados = prestamoService.findPrestamoAprobadoByUsuarioId(usuario.getUsuarioId());

            if (prestamosAprobados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tienes préstamos aprobados.");
            }

            // Tomamos el primer préstamo aprobado del usuario
            Long prestamoId = prestamosAprobados.get(0).getPrestamoId();
            List<Pago> pagos = pagoService.findPagosByPrestamoId(prestamoId);

            if (pagos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay pagos registrados para este préstamo.");
            }

            return ResponseEntity.ok(pagos);

        } catch (Exception e) {
            e.printStackTrace(); // Log de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }
}