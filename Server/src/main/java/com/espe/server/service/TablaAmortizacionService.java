package com.espe.server.service;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.EstadoPrestamo;
import com.espe.server.persistence.entity.Prestamo;
import com.espe.server.persistence.entity.TablaAmortizacion;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.persistence.repository.IPrestamoRepository;
import com.espe.server.persistence.repository.ITablaAmortizacionRepository;
import com.espe.server.persistence.repository.IUsuarioRepository;
import com.espe.server.utils.InfoCookie;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TablaAmortizacionService {

    private final ITablaAmortizacionRepository tablaAmortizacionRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IPrestamoRepository prestamoRepository;
    
    public TablaAmortizacionService(ITablaAmortizacionRepository tablaAmortizacionRepository,
    		IUsuarioRepository usuarioRepository, IPrestamoRepository prestamoRepository) {
    	this.tablaAmortizacionRepository = tablaAmortizacionRepository;
    	this.usuarioRepository = usuarioRepository;
    	this.prestamoRepository = prestamoRepository;
    }

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
    public List<TablaAmortizacion> findTablasAmortizacionByPrestamoId(HttpServletRequest request) {
    	InfoCookie infoCookie = new InfoCookie();
    	String username = infoCookie.getUsernameFromCookies(request);
    	Optional<Usuario> usuarioOpt = usuarioRepository.findUsuarioByUsername(username);
    	
    	Usuario usuario = usuarioOpt.get();
    	
    	List<Prestamo> prestamoOpt = prestamoRepository.findByUsuarioAndEstadoPrestamo(usuario, EstadoPrestamo.APROBADO);
    	
    	Prestamo prestamo = prestamoOpt.get(0);
    	
    	List<TablaAmortizacion> tablaOpt = tablaAmortizacionRepository.findByPrestamo_PrestamoId(prestamo.getPrestamoId());
    	
    	
        return  tablaOpt;
    }
}