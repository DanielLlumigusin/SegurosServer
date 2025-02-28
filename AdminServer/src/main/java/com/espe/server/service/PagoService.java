package com.espe.server.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.LogActividad;
import com.espe.server.persistence.entity.Pago;
import com.espe.server.persistence.entity.TablaAmortizacion;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.persistence.repository.ILogActividadRepository;
import com.espe.server.persistence.repository.IPagoRepository;
import com.espe.server.persistence.repository.ITablaAmortizacionRepository;
import com.espe.server.persistence.repository.IUsuarioRepository;

@Service
public class PagoService {

    private final IPagoRepository pagoRepository;
    private final ILogActividadRepository logActividadRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ITablaAmortizacionRepository tablaAmortizacionRepository;
    public PagoService(
    		IPagoRepository pagoRepository,
    		ILogActividadRepository logActividadRepository,
    		IUsuarioRepository usuarioRepository,
    		ITablaAmortizacionRepository tablaAmortizacionRepository
    		) {
    	this.pagoRepository = pagoRepository;
    	this.logActividadRepository = logActividadRepository;
    	this.usuarioRepository = usuarioRepository;
    	this.tablaAmortizacionRepository = tablaAmortizacionRepository;
    }

    // Obtener todos los pagos
    public List<Pago> findAllPagos() {
        return (List<Pago>) pagoRepository.findAll();
    }

    // Obtener un pago por su ID
    public Optional<Pago> findPagoById(Long idPago) {
        return pagoRepository.findById(idPago);
    }

    // Crear un nuevo pago
    public Pago createPago(Pago newPago) {
    	newPago.setFechaPago(LocalDate.now());
        return pagoRepository.save(newPago);
    }

 // Actualizar un pago existente
    public Optional<Pago> updatePago(Long idPago, Pago updatedPago, String username) {

    	
    	Optional<Usuario> usuarioOpt = usuarioRepository.findUsuarioByUsername(username);
    	
    	if (!usuarioOpt.isPresent()) {
            return Optional.empty();
        }
    	
    	Usuario usuario = usuarioOpt.get();
        
        Optional<Pago> pagoOpt = pagoRepository.findById(idPago);
        
        if (!pagoOpt.isPresent()) {
            return Optional.empty();
        }
        
        Pago pagoExistente = pagoOpt.get();

        // Verificar qué campos han cambiado
        boolean cambios = false;

        if (!pagoExistente.getEstadoPago().equals(updatedPago.getEstadoPago())) {
            pagoExistente.setEstadoPago(updatedPago.getEstadoPago());
            cambios = true;
        }
        
        TablaAmortizacion tablaAmortizacion = new TablaAmortizacion(
        		idPago, 
        		pagoExistente.getPrestamo(), 
        		pagoExistente.getMontoPago(), 
        		null, 
        		null, 
        		null, 
        		null, 
        		null);
        
        // Crear el log de actividad para la actualización
        LogActividad logActividad = new LogActividad(
                usuario, 
                "Actualizar Pago",
                LocalDate.now(), 
                "El pago con ID " + idPago + " ha sido actualizado."
        );

        // Si hay cambios, guardamos la actividad en el log y actualizamos el pago
        if (cambios) {
            logActividadRepository.save(logActividad);
            return Optional.of(pagoRepository.save(pagoExistente));
        } else {
            return Optional.empty();  
        }
    }

 // Eliminar un pago por su ID
    public boolean deletePago(Long idPago, String username) {
        Optional<Pago> pagoOpt = pagoRepository.findById(idPago);
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findUsuarioByUsername(username);
    	
    	if (!usuarioOpt.isPresent()) {
            return false;
        }
    	
    	Usuario usuario = usuarioOpt.get();
        
        // Si el pago no existe, devolver false
        if (!pagoOpt.isPresent()) {
            return false;
        }

        // Crear el log de actividad para la eliminación
        LogActividad logActividad = new LogActividad(
                usuario, 
                "Eliminar Pago",
                LocalDate.now(), 
                "El pago con ID " + idPago + " ha sido eliminado."
        );

        // Eliminar el pago
        pagoRepository.deleteById(idPago);
        
        // Guardar el log de actividad
        logActividadRepository.save(logActividad);

        return true;
    }


    // Obtener todos los pagos asociados a un préstamo
    public List<Pago> findPagosByPrestamoId(Long idPrestamo) {
        return pagoRepository.findByPrestamo_PrestamoId(idPrestamo);
    }
}