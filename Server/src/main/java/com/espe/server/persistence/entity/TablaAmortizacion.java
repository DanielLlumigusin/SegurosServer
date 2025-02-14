package com.espe.server.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tablas_amortizacion")
public class TablaAmortizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amortizacion_id")
    private Long amortizacionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestamo_id", nullable = false)
    private Prestamo prestamo;

    @Column(name = "numero_pago", nullable = false)
    private int numeroPago;

    @Column(name = "monto_pago", nullable = false)
    @Positive(message = "El monto del pago debe ser un valor positivo")
    private BigDecimal montoPago;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "interes", nullable = false)
    @Positive(message = "El interés debe ser un valor positivo")
    private BigDecimal interes;

    @Column(name = "capital", nullable = false)
    @Positive(message = "El capital debe ser un valor positivo")
    private BigDecimal capital;

    @Column(name = "saldo_restante", nullable = false)
    @Positive(message = "El saldo restante debe ser un valor positivo")
    private BigDecimal saldoRestante;

    // Getters y Setters
    public Long getAmortizacionId() {
        return amortizacionId;
    }

    public void setAmortizacionId(Long amortizacionId) {
        this.amortizacionId = amortizacionId;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public int getNumeroPago() {
        return numeroPago;
    }

    public void setNumeroPago(int numeroPago) {
        this.numeroPago = numeroPago;
    }

    public BigDecimal getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(BigDecimal montoPago) {
        this.montoPago = montoPago;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(BigDecimal saldoRestante) {
        this.saldoRestante = saldoRestante;
    }
}
