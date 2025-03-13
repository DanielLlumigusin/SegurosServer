package com.espe.server.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.EstadoPrestamo;
import com.espe.server.persistence.entity.Pago;
import com.espe.server.persistence.entity.Prestamo;
import com.espe.server.persistence.entity.TablaAmortizacion;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.persistence.repository.IPagoRepository;
import com.espe.server.persistence.repository.IPrestamoRepository;
import com.espe.server.persistence.repository.ITablaAmortizacionRepository;
import com.espe.server.persistence.repository.IUsuarioRepository;
import com.espe.server.utils.InfoCookie;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PagoService {

    private final IPagoRepository pagoRepository;
    private final IPrestamoRepository prestamoRepository;
    private final ITablaAmortizacionRepository tablaAmortizacionRepository;
    private final IUsuarioRepository usuarioRepository;
    
    public PagoService(
    		IPagoRepository pagoRepository, 
    		IPrestamoRepository prestamoRepository, 
    		ITablaAmortizacionRepository tablaAmortizacionRepository,
    		IUsuarioRepository usuarioRepository) {
    	this.pagoRepository = pagoRepository;
    	this.prestamoRepository = prestamoRepository;
    	this.tablaAmortizacionRepository = tablaAmortizacionRepository;
    	this.usuarioRepository = usuarioRepository;
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
    
    public boolean registrarSolicitudPago(Pago pago, HttpServletRequest request) {
        // Validación de datos de entrada
        if (pago.getMontoPago() <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a 0.");
        }

        if (pago.getMetodoPago() == null || pago.getMetodoPago().trim().isEmpty()) {
            throw new IllegalArgumentException("El método de pago es obligatorio.");
        }

        // Obtener usuario desde las cookies
        InfoCookie infoCookie = new InfoCookie();
        String username = infoCookie.getUsernameFromCookies(request);

        Usuario usuario = usuarioRepository.findUsuarioByUsername(username)
            .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        // Buscar préstamos aprobados
        List<Prestamo> prestamoList = prestamoRepository.findByUsuarioAndEstadoPrestamo(usuario, EstadoPrestamo.APROBADO);

        if (prestamoList.isEmpty()) {
            throw new IllegalStateException("No se encontraron préstamos aprobados para el usuario.");
        }

        Prestamo prestamo = prestamoList.get(0);

        // Calcular número de pago
        int numeroPago = calcularNumeroPago(prestamo.getPrestamoId());

        // Crear el pago en estado pendiente
        Pago nuevoPago = new Pago();
        nuevoPago.setPrestamo(prestamo);
        nuevoPago.setMontoPago(pago.getMontoPago());
        nuevoPago.setEstadoPago("PENDIENTE");
        nuevoPago.setFechaPago(LocalDate.now());  
        nuevoPago.setMetodoPago(pago.getMetodoPago());  
        nuevoPago.setNumeroPago(numeroPago);

        // Guardar en la base de datos
        pagoRepository.save(nuevoPago);

        return true;
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