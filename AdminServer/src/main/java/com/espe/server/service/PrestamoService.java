package com.espe.server.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.EstadoPrestamo;
import com.espe.server.persistence.entity.LogActividad;
import com.espe.server.persistence.entity.Prestamo;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.persistence.repository.ILogActividadRepository;
import com.espe.server.persistence.repository.IPrestamoRepository;
import com.espe.server.persistence.repository.IUsuarioRepository;

@Service
public class PrestamoService {

    private final IPrestamoRepository prestamoRepository;
    private final ILogActividadRepository logActividadRepository;
    private final IUsuarioRepository usuarioRepository;
    
    public PrestamoService(
    		IPrestamoRepository prestamoRepository,
    		IUsuarioRepository usuarioRepository,
    		ILogActividadRepository logActividadRepository) {
    	this.prestamoRepository = prestamoRepository;
    	this.logActividadRepository = logActividadRepository;
    	this.usuarioRepository = usuarioRepository;
    }
    
    // Obtener todos los préstamos
    public List<Prestamo> findAllPrestamos() {
        return (List<Prestamo>) prestamoRepository.findAll();
    }
    
    // Obtener un préstamo por su ID
    public Optional<Prestamo> findPrestamoById(Long idPrestamo) {
        return prestamoRepository.findById(idPrestamo);
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

 // Actualizar un préstamo existente
    public Optional<Prestamo> updatePrestamo(Long idPrestamo, Prestamo updatedPrestamo, String username) {
        // Buscar el préstamo en la base de datos
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(idPrestamo);
        
        // Si el préstamo no existe, devolver Optional vacío
        if (!prestamoOpt.isPresent()) {
            return Optional.empty();
        }

        // Obtener el préstamo existente
        Prestamo prestamoExistente = prestamoOpt.get();
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findUsuarioByUsername(username);
    	
    	if (!usuarioOpt.isPresent()) {
            return Optional.empty();
        }
    	
    	Usuario usuario = usuarioOpt.get();

        // Crear el log de actividad para la actualización
        LogActividad logActividad = new LogActividad(
                usuario, 
                "Actualizar Prestamo",
                LocalDate.now(), 
                "El préstamo con ID " + idPrestamo + " ha sido actualizado."
        );

        // Verificar qué campos han cambiado
        boolean cambios = false;
        if (!prestamoExistente.getMontoSolicitado().equals(updatedPrestamo.getMontoSolicitado())) {
            prestamoExistente.setMontoSolicitado(updatedPrestamo.getMontoSolicitado());
            cambios = true;
        }
        if (!(prestamoExistente.getPlazoAmortizacion() == updatedPrestamo.getPlazoAmortizacion())) {
            prestamoExistente.setPlazoAmortizacion(updatedPrestamo.getPlazoAmortizacion());
            cambios = true;
        }
        if (!prestamoExistente.getTasaInteres().equals(updatedPrestamo.getTasaInteres())) {
            prestamoExistente.setTasaInteres(updatedPrestamo.getTasaInteres());
            cambios = true;
        }
        if (!prestamoExistente.getTipoPago().equals(updatedPrestamo.getTipoPago())) {
            prestamoExistente.setTipoPago(updatedPrestamo.getTipoPago());
            cambios = true;
        }
        if (!prestamoExistente.getEstadoPrestamo().equals(updatedPrestamo.getEstadoPrestamo())) {
            prestamoExistente.setEstadoPrestamo(updatedPrestamo.getEstadoPrestamo());
            cambios = true;
        }

        // Si hay cambios, guardamos la actividad en el log y actualizamos el préstamo
        if (cambios) {
            logActividadRepository.save(logActividad);
            return Optional.of(prestamoRepository.save(prestamoExistente));
        } else {
            return Optional.empty();  // Si no hubo cambios, no actualizamos ni registramos nada
        }
    }



 // Eliminar un préstamo por su ID
    public boolean deletePrestamo(Long idPrestamo, String username) {
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(idPrestamo);
        
        // Si el préstamo no existe, devolver false
        if (!prestamoOpt.isPresent()) {
            return false;
        }
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findUsuarioByUsername(username);
    	
    	if (!usuarioOpt.isPresent()) {
            return false;
        }
    	
    	Usuario usuario = usuarioOpt.get();

        // Crear el log de actividad para la eliminación
        LogActividad logActividad = new LogActividad(
                usuario, 
                "Eliminar Prestamo",
                LocalDate.now(), 
                "El préstamo con ID " + idPrestamo + " ha sido eliminado."
        );

        // Eliminar el préstamo
        prestamoRepository.deleteById(idPrestamo);
        
        // Guardar el log de actividad
        logActividadRepository.save(logActividad);

        return true;
    }
}