package com.espe.server.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.espe.server.persistence.entity.Prestamo;

public interface IPrestamoRepository extends CrudRepository<Prestamo, Long>{

}
