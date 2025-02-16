package com.espe.server.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.EstadoPrestamo;
import com.espe.server.persistence.entity.Prestamo;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.persistence.repository.IPrestamoRepository;
import com.espe.server.persistence.repository.IUsuarioRepository;

@Service
public class PrestamoService {

    @Autowired
    private IPrestamoRepository prestamoRepository;
    
    @Autowired
    private IUsuarioRepository usuarioRepository;
    
    // Obtener un préstamo por su ID
    public Optional<Prestamo> findPrestamoById(Long idPrestamo) {
        return prestamoRepository.findById(idPrestamo);
    }

    // Crear un nuevo préstamo
    public Prestamo createPrestamo(Prestamo newPrestamo) {
        
    	Long usuarioId = newPrestamo.getUsuario().getUsuarioId();

        boolean tienePrestamoActivo = prestamoRepository.existsByUsuarioUsuarioIdAndEstadoPrestamo(usuarioId, EstadoPrestamo.ACTIVO);

        if (tienePrestamoActivo) {
            throw new IllegalStateException("El usuario ya tiene un préstamo activo y no puede solicitar otro.");
        }
        
        newPrestamo.setEstadoPrestamo(EstadoPrestamo.SOLICITADO);
        newPrestamo.setFechaSolicitud(LocalDate.now());
        return prestamoRepository.save(newPrestamo);
    }

    public List<Prestamo> findPrestamoAprobadoByUsuarioId(Long usuarioId) {
    	Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            return prestamoRepository.findByUsuarioAndEstadoPrestamo(usuarioOpt.get(), EstadoPrestamo.APROBADO);
        } else {
            return Collections.emptyList(); 
        }
    }
    
    public List<Prestamo> findPrestamosSolicitadosByUsuarioId(Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            return prestamoRepository.findByUsuarioAndEstadoPrestamo(usuarioOpt.get(), EstadoPrestamo.SOLICITADO);
        } else {
            return Collections.emptyList(); 
        }
    }

    
    // Obtener todos los préstamos
    public List<Prestamo> findAllPrestamos() {
        return (List<Prestamo>) prestamoRepository.findAll();
    }

    // Eliminar un préstamo por su ID
    public boolean deletePrestamo(Long idPrestamo) {
        if (prestamoRepository.existsById(idPrestamo)) {
            prestamoRepository.deleteById(idPrestamo);
            return true;
        } else {
            return false;
        }
    }

    // Actualizar un préstamo existente
    public Optional<Prestamo> updatePrestamo(Long idPrestamo, Prestamo updatedPrestamo) {
        return prestamoRepository.findById(idPrestamo).map(prestamo -> {
            prestamo.setMontoSolicitado(updatedPrestamo.getMontoSolicitado());
            prestamo.setPlazoAmortizacion(updatedPrestamo.getPlazoAmortizacion());
            prestamo.setTasaInteres(updatedPrestamo.getTasaInteres());
            prestamo.setTipoPago(updatedPrestamo.getTipoPago());
            prestamo.setEstadoPrestamo(updatedPrestamo.getEstadoPrestamo());
            return prestamoRepository.save(prestamo);
        });
    }
}