package com.espe.server.persistence.repository;

import com.espe.server.persistence.entity.LogActividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILogActividadRepository extends JpaRepository<LogActividad, Long> {
    // Método personalizado para obtener logs por ID de usuario
    List<LogActividad> findByUsuarioId(Long idUsuario);
}