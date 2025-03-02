package com.espe.server.service;

import java.math.BigDecimal;
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
    public boolean registrarPago(Long prestamoId, int numeroPago, BigDecimal montoPago) {
        Optional<TablaAmortizacion> cuotaOpt = tablaAmortizacionRepository.findByPrestamoPrestamoIdAndNumeroPago(prestamoId, numeroPago);

        if (!cuotaOpt.isPresent()) {
            return false;
        }

        TablaAmortizacion cuota = cuotaOpt.get();

        if (montoPago.compareTo(cuota.getMontoPago()) < 0) {
            return false; 
        }

        BigDecimal nuevoSaldo = cuota.getSaldoRestante().subtract(cuota.getCapital());
        cuota.setSaldoRestante(nuevoSaldo);

        // Guardar actualización
        tablaAmortizacionRepository.save(cuota);

        return true;
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