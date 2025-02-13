package com.espe.server.persistence.entity;

import java.sql.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="pagos")
public class Pago {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pagoId;
	private Long prestamoId;
	private double montoPago;
	private Date fechaPago;
	private String metodoPago;
}
