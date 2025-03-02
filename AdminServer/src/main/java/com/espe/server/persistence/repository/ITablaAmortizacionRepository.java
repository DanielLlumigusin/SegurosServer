package com.espe.server.persistence.repository;

import com.espe.server.persistence.entity.TablaAmortizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITablaAmortizacionRepository extends JpaRepository<TablaAmortizacion, Long> {
    List<TablaAmortizacion> findByPrestamoPrestamoId(Long prestamoId); // Corregida
    Optional<TablaAmortizacion> findByPrestamoPrestamoIdAndNumeroPago(Long prestamoId, int numeroPago);
}
