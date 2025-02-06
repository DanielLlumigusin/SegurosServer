package com.espe.SegurosServer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espe.SegurosServer.repository.IPrestamoRepository;
import com.espe.SegurosServer.entities.Prestamo;

@Service
public class PrestamoService {
	
	@Autowired
	private IPrestamoRepository prestamoRepository;
	
	public List<Prestamo> getPrestamos(){
		return (List<Prestamo>) prestamoRepository.findAll();
	}
	
	public Optional<Prestamo> findByPrestamoId(Long id){
		return prestamoRepository.findById(id);
	}
	
	public Prestamo savePrestamo(Prestamo Prestamo) {
		return prestamoRepository.save(Prestamo);
	}
	
	public Prestamo updatePrestamo(Prestamo Prestamo) {
		return prestamoRepository.save(Prestamo);
	}
	
	public Boolean deletePrestamo(Long id) {
		if(prestamoRepository.existsById(id)) {
			prestamoRepository.deleteById(id);
			return true;
		}else {
			return false;
		}
	}
	
}
