package com.espe.server.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.Pago;
import com.espe.server.persistence.repository.IPagoRepository;

@Service
public class PagoService {

    @Autowired
    private IPagoRepository pagoRepository;

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
    public Optional<Pago> updatePago(Long idPago, Pago updatedPago) {
        return pagoRepository.findById(idPago).map(pago -> {
            pago.setMontoPago(updatedPago.getMontoPago());
            pago.setFechaPago(updatedPago.getFechaPago());
            pago.setMetodoPago(updatedPago.getMetodoPago());
            return pagoRepository.save(pago);
        });
    }

    // Eliminar un pago por su ID
    public boolean deletePago(Long idPago) {
        if (pagoRepository.existsById(idPago)) {
            pagoRepository.deleteById(idPago);
            return true;
        } else {
            return false;
        }
    }

    // Obtener todos los pagos asociados a un préstamo
    public List<Pago> findPagosByPrestamoId(Long idPrestamo) {
        return pagoRepository.findByPrestamo_PrestamoId(idPrestamo);
    }
}