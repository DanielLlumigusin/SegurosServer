package com.espe.server.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.espe.server.persistence.entity.EstadoPrestamo;
import com.espe.server.persistence.entity.Prestamo;
import com.espe.server.persistence.entity.Usuario;

public interface IPrestamoRepository extends CrudRepository<Prestamo, Long>{
	boolean existsByUsuarioUsuarioIdAndEstadoPrestamo(Long usuarioId, EstadoPrestamo estadoPrestamo);
       
	//Me devuelve el prestamo de acuerdo al Usuario con el estado Solicitado
    List<Prestamo> findByUsuarioAndEstadoPrestamo(Usuario usuario, EstadoPrestamo estadoPrestamo);
    List<Prestamo> findByUsuarioUsuarioId(Long usuarioId);
}
