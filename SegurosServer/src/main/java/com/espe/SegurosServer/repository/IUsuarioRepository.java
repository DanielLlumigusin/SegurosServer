package com.espe.SegurosServer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.espe.SegurosServer.entities.Usuario;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long>{
	Optional<Usuario> findUsuarioByUsername(String username);
}
