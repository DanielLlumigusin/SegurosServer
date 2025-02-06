package com.espe.SegurosServer.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.espe.SegurosServer.repository.ILogActividadRepository;
import com.espe.SegurosServer.entities.LogActividad;

public class LogActividadService {

	@Autowired
	private ILogActividadRepository logActividadRepository;;
	
	public List<LogActividad> getLogActividads(){
		return (List<LogActividad>) logActividadRepository.findAll();
	}
	
	public Optional<LogActividad> findByLogActividadId(Long id){
		return logActividadRepository.findById(id);
	}
	
	public LogActividad saveLogActividad(LogActividad LogActividad) {
		LogActividad.setFechaHora(LocalDate.now());
		return logActividadRepository.save(LogActividad);
	}
	
	public LogActividad updateLogActividad(LogActividad LogActividad) {
		return logActividadRepository.save(LogActividad);
	}
	
	public Boolean deleteLogActividad(Long id) {
		if(logActividadRepository.existsById(id)) {
			logActividadRepository.deleteById(id);
			return true;
		}else {
			return false;
		}
	}
	
}
