package com.espe.server.persistence.repository;

import com.espe.server.persistence.entity.TablaAmortizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITablaAmortizacionRepository extends JpaRepository<TablaAmortizacion, Long> {
    // Método personalizado para obtener entradas de la tabla de amortización por ID de préstamo
    List<TablaAmortizacion> findByPrestamoId(Long idPrestamo);
}