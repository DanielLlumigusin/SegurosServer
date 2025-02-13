package com.espe.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.Prestamo;
import com.espe.server.persistence.repository.IPrestamoRepository;

@Service
public class PrestamoService {

    @Autowired
    private IPrestamoRepository prestamoRepository;

    // Obtener un préstamo por su ID
    public Optional<Prestamo> findPrestamoById(Long idPrestamo) {
        return prestamoRepository.findById(idPrestamo);
    }

    // Crear un nuevo préstamo
    public Prestamo createPrestamo(Prestamo newPrestamo) {
        return prestamoRepository.save(newPrestamo);
    }

    // Obtener todos los préstamos
    public List<Prestamo> findAllPrestamos() {
        return prestamoRepository.findAll();
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