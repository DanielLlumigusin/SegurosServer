package com.espe.server.persistence.entity;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tablas_amortizacion")
public class TablaAmortizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amortizacion_id")
    private Long amortizacionId;

    @ManyToOne
    @JoinColumn(name = "prestamo_id", nullable = false)
    private Prestamo prestamo;

    @Column(name = "numero_pago", nullable = false)
    private int numeroPago;

    @Column(name = "monto_pago", nullable = false)
    private double montoPago;

    @Column(name = "fecha_pago", nullable = false)
    private Date fechaPago;

    @Column(name = "interes", nullable = false)
    private double interes;

    @Column(name = "capital", nullable = false)
    private double capital;

    @Column(name = "saldo_restante", nullable = false)
    private double saldoRestante;

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

    public double getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(double montoPago) {
        this.montoPago = montoPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public double getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(double saldoRestante) {
        this.saldoRestante = saldoRestante;
    }
}