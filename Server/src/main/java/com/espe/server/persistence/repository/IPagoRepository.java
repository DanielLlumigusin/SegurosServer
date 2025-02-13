package com.espe.server.persistence.repository;

import com.espe.server.persistence.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPagoRepository extends JpaRepository<Pago, Long> {
    // Método personalizado para obtener pagos por ID de préstamo
    List<Pago> findByPrestamoId(Long idPrestamo);
}