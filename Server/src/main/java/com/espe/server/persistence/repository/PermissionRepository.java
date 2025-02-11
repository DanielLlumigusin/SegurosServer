package com.espe.server.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.espe.server.persistence.entity.PermissionEntity;

public interface PermissionRepository extends CrudRepository<PermissionEntity, Long>{
    boolean findByName(String name);

}
