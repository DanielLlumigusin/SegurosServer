package com.espe.server.persistence.entity;

import java.sql.Date;

public class Prestamo {
	
	private Long prestamoId;
	private Long usuarioId;
	private double montoSolicitado;
	private int plazoAmortizado;
	private double tazaInteres;
	private String tipoPago;
	private String estadoPrestamo;
	private Date fechaSolicitud;
}
