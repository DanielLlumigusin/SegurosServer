package com.espe.server.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.espe.server.persistence.entity.LogActividad;

@Repository
public interface ILogActividad extends CrudRepository<LogActividad, Long>{

}
