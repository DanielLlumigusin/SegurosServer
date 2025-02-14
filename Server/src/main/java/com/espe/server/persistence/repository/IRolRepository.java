package com.espe.server.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.espe.server.persistence.entity.Rol;
import com.espe.server.persistence.entity.TipoRol;

@Repository
public interface IRolRepository extends CrudRepository<Rol, Long> {
    Optional<Rol> findByRoleEnum(TipoRol roleEnum);
}
