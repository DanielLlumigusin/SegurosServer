package com.espe.server.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.espe.server.persistence.entity.Usuario;

@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario, Long>{
	 Optional<Usuario> findUsuarioByUsername(String username);

}
