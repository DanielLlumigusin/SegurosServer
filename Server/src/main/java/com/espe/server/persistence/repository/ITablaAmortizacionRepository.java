package com.espe.server.persistence.repository;

import com.espe.server.persistence.entity.TablaAmortizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITablaAmortizacionRepository extends JpaRepository<TablaAmortizacion, Long> {
	List<TablaAmortizacion> findByPrestamo_PrestamoId(Long id);
}