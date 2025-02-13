package com.espe.server.persistence.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="logsactividades")
public class LogActividad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long logId;
	
	private Long usuarioId;
	
	private String accion;
	
	private Date fechaAccion;
	
	private String detalles;
	
}
