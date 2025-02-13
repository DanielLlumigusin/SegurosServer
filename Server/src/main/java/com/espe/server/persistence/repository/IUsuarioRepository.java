package com.espe.server.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.espe.server.persistence.entity.Usuario;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long>{

}
