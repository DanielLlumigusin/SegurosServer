package com.espe.server.persistence.entity;

import java.sql.Date;

public class TablaAmortizacion {

	private Long amortizacionId;
	private Long prestamoId;
	private Long numeroPago;
	private double montoPago;
	private Date fechaPago;
	private double interes;
	private double capital;
	private double saldoRestante;
}
