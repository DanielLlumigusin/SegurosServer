package com.espe.server.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.Pago;
import com.espe.server.persistence.repository.IPagoRepository;

@Service
public class PagoService {

    private final IPagoRepository pagoRepository;
    
    public PagoService(IPagoRepository pagoRepository) {
    	this.pagoRepository = pagoRepository;
    }

    // Crear un nuevo pago
    public Pago createPago(Pago newPago) {
    	newPago.setFechaPago(LocalDate.now());
    	newPago.setEstadoPago("PENDIENTE");
        return pagoRepository.save(newPago);
    }


    // Obtener todos los pagos asociados a un préstamo
    public List<Pago> findPagosByPrestamoId(Long idPrestamo) {
        return pagoRepository.findByPrestamo_PrestamoId(idPrestamo);
    }
}