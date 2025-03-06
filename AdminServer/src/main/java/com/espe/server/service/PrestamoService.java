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
import com.espe.server.persistence.repository.ITablaAmortizacionRepository;
import com.espe.server.persistence.repository.IUsuarioRepository;

@Service
public class PrestamoService {

    private final IPrestamoRepository prestamoRepository;
    private final ILogActividadRepository logActividadRepository;
    private final IUsuarioRepository usuarioRepository;
    private final TablaAmortizacionService tablaAmortizacionService;
    public PrestamoService(
    		IPrestamoRepository prestamoRepository,
    		IUsuarioRepository usuarioRepository,
    		ILogActividadRepository logActividadRepository,
    		ITablaAmortizacionRepository tablaAutorizacionRepository,
    		TablaAmortizacionService tablaAmortizacionService) {
    	this.prestamoRepository = prestamoRepository;
    	this.logActividadRepository = logActividadRepository;
    	this.usuarioRepository = usuarioRepository;
    	this.tablaAmortizacionService = tablaAmortizacionService;
    }
    
    // Obtener todos los préstamos
    public List<Prestamo> findAllPrestamos() {
        return (List<Prestamo>) prestamoRepository.findAll();
    }
    
    // Obtener un préstamo por su ID
    public Optional<Prestamo> findPrestamoById(Long idPrestamo) {
        return prestamoRepository.findById(idPrestamo);
    }
    
    //Aprobar el prestamo
    public boolean aprobarPrestamo(Prestamo prestamoByAprobar) {
    	
    	Optional<Prestamo> prestamoOpt = prestamoRepository.findById(prestamoByAprobar.getPrestamoId());
    	
    	if(prestamoOpt.isPresent()) {
    		Prestamo prestamo = prestamoOpt.get();
    		prestamo.setEstadoPrestamo(EstadoPrestamo.APROBADO);
    		prestamoRepository.save(prestamo);
    		return true;
    	}else {
    		return false;
    	}
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

    //Actualizar Prestamo
    public Optional<Prestamo> updatePrestamo(Prestamo updatedPrestamo) {
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(updatedPrestamo.getPrestamoId());

        if (!prestamoOpt.isPresent()) {
            return Optional.empty();
        }

        Prestamo prestamoExistente = prestamoOpt.get();
        Optional<Usuario> usuarioOpt = usuarioRepository.findUsuarioByUsername(prestamoExistente.getUsuario().getUsername());

        if (!usuarioOpt.isPresent()) {
            return Optional.empty();
        }

        Usuario usuario = usuarioOpt.get();
        LogActividad logActividad = new LogActividad(
                usuario, 
                "Actualizar Prestamo",
                LocalDate.now(), 
                "El préstamo con ID " + updatedPrestamo.getPrestamoId() + " ha sido actualizado."
        );

        boolean cambios = false;

        if (!prestamoExistente.getEstadoPrestamo().equals(updatedPrestamo.getEstadoPrestamo())) {
            prestamoExistente.setEstadoPrestamo(updatedPrestamo.getEstadoPrestamo());
            cambios = true;

            // Si el préstamo es aprobado, generar y guardar la tabla de amortización
            if (updatedPrestamo.getEstadoPrestamo() == EstadoPrestamo.APROBADO) {
                tablaAmortizacionService.generarYGuardarTablaAmortizacion(prestamoExistente);
            }
        }

        if (cambios) {
            logActividadRepository.save(logActividad);
            return Optional.of(prestamoRepository.save(prestamoExistente));
        } else {
            return Optional.empty();
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