package com.espe.server.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.sql.Date;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prestamo_id")
    private Long prestamoId;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "monto_solicitado", nullable = false)
    @Positive(message = "El monto solicitado debe ser un valor positivo")
    private double montoSolicitado;

    @Column(name = "plazo_amortizacion", nullable = false)
    @Min(value = 1, message = "El plazo de amortización debe ser al menos 1 mes")
    private int plazoAmortizacion;

    @Column(name = "tasa_interes", nullable = false)
    @PositiveOrZero(message = "La tasa de interés no puede ser negativa")
    private double tasaInteres;

    @Column(name = "tipo_pago", nullable = false)
    @NotBlank(message = "El tipo de pago no puede estar vacío")
    private String tipoPago;

    @Column(name = "estado_prestamo", nullable = false)
    @NotBlank(message = "El estado del préstamo no puede estar vacío")
    private String estadoPrestamo;

    @Column(name = "fecha_solicitud", nullable = false)
    private Date fechaSolicitud;

    // Getters y Setters
    public Long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(double montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public int getPlazoAmortizacion() {
        return plazoAmortizacion;
    }

    public void setPlazoAmortizacion(int plazoAmortizacion) {
        this.plazoAmortizacion = plazoAmortizacion;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(String estadoPrestamo) {
        this.estadoPrestamo = estadoPrestamo;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
}