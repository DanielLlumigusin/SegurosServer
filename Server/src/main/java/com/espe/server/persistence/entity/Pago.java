package com.espe.server.persistence.entity;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id")
    private Long pagoId;

    @ManyToOne
    @JoinColumn(name = "prestamo_id", nullable = false)
    private Prestamo prestamo;

    @Column(name = "monto_pago", nullable = false)
    private double montoPago;

    @Column(name = "fecha_pago", nullable = false)
    private Date fechaPago;

    @Column(name = "metodo_pago", nullable = false)
    private String metodoPago;

    // Getters y Setters
    public Long getPagoId() {
        return pagoId;
    }

    public void setPagoId(Long pagoId) {
        this.pagoId = pagoId;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
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

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}