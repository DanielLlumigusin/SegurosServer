package com.espe.server.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prestamo_id")
    private Long prestamoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "monto_solicitado", nullable = false)
    @Positive(message = "El monto solicitado debe ser un valor positivo")
    private BigDecimal montoSolicitado;

    @Column(name = "plazo_amortizacion", nullable = false)
    @Min(value = 1, message = "El plazo de amortización debe ser al menos 1 mes")
    private int plazoAmortizacion;

    @Column(name = "tasa_interes", nullable = false)
    @PositiveOrZero(message = "La tasa de interés no puede ser negativa")
    private BigDecimal tasaInteres;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago", nullable = false)
    private TipoPago tipoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_prestamo", nullable = false)
    private EstadoPrestamo estadoPrestamo;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

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

    public BigDecimal getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(BigDecimal montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public int getPlazoAmortizacion() {
        return plazoAmortizacion;
    }

    public void setPlazoAmortizacion(int plazoAmortizacion) {
        this.plazoAmortizacion = plazoAmortizacion;
    }

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public EstadoPrestamo getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(EstadoPrestamo estadoPrestamo) {
        this.estadoPrestamo = estadoPrestamo;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
}
