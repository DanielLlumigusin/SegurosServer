package com.espe.server.persistence.repository;

import com.espe.server.persistence.entity.LogActividad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ILogActividadRepository extends CrudRepository<LogActividad, Long> {
}