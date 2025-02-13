package com.espe.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.TablaAmortizacion;
import com.espe.server.persistence.repository.ITablaAmortizacionRepository;

@Service
public class TablaAmortizacionService {

    @Autowired
    private ITablaAmortizacionRepository tablaAmortizacionRepository;

    // Obtener todas las entradas de la tabla de amortización
    public List<TablaAmortizacion> findAllTablasAmortizacion() {
        return (List<TablaAmortizacion>)tablaAmortizacionRepository.findAll();
    }

    // Obtener una entrada de la tabla de amortización por su ID
    public Optional<TablaAmortizacion> findTablaAmortizacionById(Long idAmortizacion) {
        return tablaAmortizacionRepository.findById(idAmortizacion);
    }

    // Crear una nueva entrada en la tabla de amortización
    public TablaAmortizacion createTablaAmortizacion(TablaAmortizacion newTablaAmortizacion) {
        return tablaAmortizacionRepository.save(newTablaAmortizacion);
    }

    // Actualizar una entrada existente en la tabla de amortización
    public Optional<TablaAmortizacion> updateTablaAmortizacion(Long idAmortizacion, TablaAmortizacion updatedTablaAmortizacion) {
        return tablaAmortizacionRepository.findById(idAmortizacion).map(tabla -> {
            tabla.setNumeroPago(updatedTablaAmortizacion.getNumeroPago());
            tabla.setMontoPago(updatedTablaAmortizacion.getMontoPago());
            tabla.setFechaPago(updatedTablaAmortizacion.getFechaPago());
            tabla.setInteres(updatedTablaAmortizacion.getInteres());
            tabla.setCapital(updatedTablaAmortizacion.getCapital());
            tabla.setSaldoRestante(updatedTablaAmortizacion.getSaldoRestante());
            return tablaAmortizacionRepository.save(tabla);
        });
    }

    // Eliminar una entrada de la tabla de amortización por su ID
    public boolean deleteTablaAmortizacion(Long idAmortizacion) {
        if (tablaAmortizacionRepository.existsById(idAmortizacion)) {
            tablaAmortizacionRepository.deleteById(idAmortizacion);
            return true;
        } else {
            return false;
        }
    }

    // Obtener todas las entradas de la tabla de amortización asociadas a un préstamo
    public List<TablaAmortizacion> findTablasAmortizacionByPrestamoId(Long idPrestamo) {
        return tablaAmortizacionRepository.findByPrestamoId(idPrestamo);
    }
}