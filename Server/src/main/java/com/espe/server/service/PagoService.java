package com.espe.server.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.Pago;
import com.espe.server.persistence.entity.Prestamo;
import com.espe.server.persistence.entity.TablaAmortizacion;
import com.espe.server.persistence.repository.IPagoRepository;
import com.espe.server.persistence.repository.IPrestamoRepository;
import com.espe.server.persistence.repository.ITablaAmortizacionRepository;

@Service
public class PagoService {

    private final IPagoRepository pagoRepository;
    private final IPrestamoRepository prestamoRepository;
    private final ITablaAmortizacionRepository tablaAmortizacionRepository;
    public PagoService(IPagoRepository pagoRepository, IPrestamoRepository prestamoRepository, ITablaAmortizacionRepository tablaAmortizacionRepository) {
    	this.pagoRepository = pagoRepository;
    	this.prestamoRepository = prestamoRepository;
    	this.tablaAmortizacionRepository = tablaAmortizacionRepository;
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
    
    public boolean registrarSolicitudPago(Long prestamoId, BigDecimal montoPago) {
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(prestamoId);
        if (!prestamoOpt.isPresent()) {
            return false;  // Si no existe el préstamo, no se puede realizar la solicitud.
        }

        Prestamo prestamo = prestamoOpt.get();
        
        // Verificar si el saldo restante del préstamo es mayor o igual al monto de la solicitud
        if (prestamo.getMontoSolicitado().compareTo(montoPago) < 0) {
            return false;  // No se puede realizar la solicitud si el monto excede el saldo restante.
        }
        
        int numeroPago = calcularNumeroPago(prestamoId);

        // Crear el pago en estado pendiente
        Pago nuevoPago = new Pago();
        nuevoPago.setPrestamo(prestamo);
        nuevoPago.setMontoPago(montoPago.doubleValue());
        nuevoPago.setEstadoPago("PENDIENTE");
        nuevoPago.setFechaPago(LocalDate.now());  
        nuevoPago.setMetodoPago("Solicitud Pendiente");  
        nuevoPago.setNumeroPago(numeroPago);  // Esto puede ser calculado según las cuotas del préstamo

        // Guardar el pago en la base de datos
        pagoRepository.save(nuevoPago);

        return true;  // Solicitud registrada correctamente.
    }
    
    public int calcularNumeroPago(Long prestamoId) {
        // Buscar la última cuota registrada en la tabla de amortización para el préstamo
        Optional<TablaAmortizacion> ultimaCuotaOpt = tablaAmortizacionRepository
                .findTopByPrestamoPrestamoIdOrderByNumeroPagoDesc(prestamoId);
        
        // Si no hay cuotas, el primer pago es el número 1
        if (!ultimaCuotaOpt.isPresent()) {
            return 1;
        }
        
        // Si ya hay cuotas, calcular el siguiente número de pago
        TablaAmortizacion ultimaCuota = ultimaCuotaOpt.get();
        return ultimaCuota.getNumeroPago() + 1;  // El siguiente pago será el siguiente número
    }


    

}