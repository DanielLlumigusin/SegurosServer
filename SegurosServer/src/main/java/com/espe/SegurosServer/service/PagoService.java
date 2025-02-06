package com.espe.SegurosServer.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espe.SegurosServer.entities.Pago;
import com.espe.SegurosServer.repository.IPagoRepository;

@Service
public class PagoService {
	
	@Autowired
	private IPagoRepository pagoRepository;
	
	public List<Pago> getPagos(){
		return (List<Pago>) pagoRepository.findAll();
	}
	
	public Optional<Pago> findByPagoId(Long id){
		return pagoRepository.findById(id);
	}
	
	public Pago savePago(Pago Pago) {
		Pago.setFechaPago(LocalDate.now());
		return pagoRepository.save(Pago);
	}
	
	public Pago updatePago(Pago Pago) {
		return pagoRepository.save(Pago);
	}
	
	public Boolean deletePago(Long id) {
		if(pagoRepository.existsById(id)) {
			pagoRepository.deleteById(id);
			return true;
		}else {
			return false;
		}
	}
}
