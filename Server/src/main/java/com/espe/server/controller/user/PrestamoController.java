package com.espe.server.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.Prestamo;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.service.PrestamoService;
import com.espe.server.service.UsuarioService;
import com.espe.server.utils.InfoCookie;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final UsuarioService usuarioService;
    
    public PrestamoController(
    		PrestamoService prestamoService,
    		UsuarioService usuarioService
    		) {
    	this.prestamoService = prestamoService;
    	this.usuarioService = usuarioService;
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
    
    @GetMapping("/aprobado")
    public ResponseEntity<List<Prestamo>> findPrestamoAprobado(HttpServletRequest request) {
        try {
            InfoCookie infoCookie = new InfoCookie();
            String username = infoCookie.getUsernameFromCookies(request);
            
            Optional<Usuario> usuarioOptional = usuarioService.findByUsername(username);
            
            // Si el usuario no existe, devolver UNAUTHORIZED (401)
            if (usuarioOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            Long usuarioId = usuarioOptional.get().getUsuarioId();
            List<Prestamo> prestamos = prestamoService.findPrestamoAprobadoByUsuarioId(usuarioId);

            // Devolver 200 OK con lista vacía en caso de no encontrar préstamos
            return ResponseEntity.ok(prestamos);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/solicitado")
    public ResponseEntity<List<Prestamo>> findPrestamoSolicitado(HttpServletRequest request) {
        try {
            InfoCookie infoCookie = new InfoCookie();
            String username = infoCookie.getUsernameFromCookies(request);
            
            Optional<Usuario> usuarioOptional = usuarioService.findByUsername(username);
            
            // Si el usuario no existe, devolver UNAUTHORIZED (401)
            if (usuarioOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            Long usuarioId = usuarioOptional.get().getUsuarioId();
            List<Prestamo> prestamos = prestamoService.findPrestamosSolicitadosByUsuarioId(usuarioId);

            // Devolver 200 OK con lista vacía en caso de no encontrar préstamos
            return ResponseEntity.ok(prestamos);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear un nuevo préstamo
    @PostMapping
    public ResponseEntity<Prestamo> createPrestamo(HttpServletRequest request, @RequestBody Prestamo newPrestamo) {
        try {
        	InfoCookie infoCookie = new InfoCookie();
        	String username = infoCookie.getUsernameFromCookies(request);
            Prestamo prestamoCreado = prestamoService.createPrestamo(newPrestamo, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamoCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}