package com.espe.server.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.Prestamo;
import com.espe.server.persistence.entity.TablaAmortizacion;
import com.espe.server.persistence.repository.ITablaAmortizacionRepository;

@Service
public class TablaAmortizacionService {

    private final ITablaAmortizacionRepository tablaAmortizacionRepository;
    
    public TablaAmortizacionService(ITablaAmortizacionRepository tablaAmortizacionRepository) {
    	this.tablaAmortizacionRepository = tablaAmortizacionRepository;
    }

    // Obtener todas las entradas de la tabla de amortización
    public List<TablaAmortizacion> findAllTablasAmortizacion() {
        return (List<TablaAmortizacion>)tablaAmortizacionRepository.findAll();
    }

    // Obtener una entrada de la tabla de amortización por su ID
    public Optional<TablaAmortizacion> findTablaAmortizacionById(Long idAmortizacion) {
        return tablaAmortizacionRepository.findById(idAmortizacion);
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
        return tablaAmortizacionRepository.findByPrestamo_PrestamoId(idPrestamo);
    }
    
    
    
    // Generar la Tabla de Amortizacion
    public void generarYGuardarTablaAmortizacion(Prestamo prestamo) {
        List<TablaAmortizacion> tabla = new ArrayList<>();

        BigDecimal monto = prestamo.getMontoSolicitado();
        BigDecimal tasaInteres = prestamo.getTasaInteres().divide(BigDecimal.valueOf(100)); // Convertir a decimal
        int plazo = prestamo.getPlazoAmortizacion();
        BigDecimal tasaMensual = tasaInteres.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        BigDecimal cuota = monto.multiply(tasaMensual)
                .divide(BigDecimal.ONE.subtract(BigDecimal.ONE
                        .divide(tasaMensual.add(BigDecimal.ONE).pow(plazo), 10, RoundingMode.HALF_UP)),
                        2, RoundingMode.HALF_UP);

        BigDecimal saldoRestante = monto;

        for (int i = 1; i <= plazo; i++) {
            BigDecimal interes = saldoRestante.multiply(tasaMensual).setScale(2, RoundingMode.HALF_UP);
            BigDecimal capital = cuota.subtract(interes);
            saldoRestante = saldoRestante.subtract(capital);

            TablaAmortizacion pago = new TablaAmortizacion(
                    prestamo,
                    i,
                    cuota,
                    prestamo.getFechaSolicitud().plusMonths(i),
                    interes,
                    capital,
                    saldoRestante
            );

            tabla.add(pago);
        }

        // Guardar la tabla de amortización en la base de datos
        tablaAmortizacionRepository.saveAll(tabla);
    }
    
}