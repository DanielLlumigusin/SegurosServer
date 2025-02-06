package com.espe.SegurosServer.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotEmpty(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotEmpty(message = "El plazo de amortización es obligatorio")
    @Min(value = 1, message = "El plazo de amortización debe ser al menos 1 mes")
    private Integer plazoAmortizacion;

    @NotEmpty(message = "El tipo de pago es obligatorio")
    private String tipoPago;

    @NotEmpty(message = "La tasa de interés es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La tasa de interés debe ser mayor a 0")
    private BigDecimal tasaInteres;

    @NotEmpty(message = "El estado del préstamo es obligatorio")
    private String estadoPrestamo;

    @NotEmpty(message = "El método de pago es obligatorio")
    private String metodoPago;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Integer getPlazoAmortizacion() {
		return plazoAmortizacion;
	}

	public void setPlazoAmortizacion(Integer plazoAmortizacion) {
		this.plazoAmortizacion = plazoAmortizacion;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public BigDecimal getTasaInteres() {
		return tasaInteres;
	}

	public void setTasaInteres(BigDecimal tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	public String getEstadoPrestamo() {
		return estadoPrestamo;
	}

	public void setEstadoPrestamo(String estadoPrestamo) {
		this.estadoPrestamo = estadoPrestamo;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}
}
