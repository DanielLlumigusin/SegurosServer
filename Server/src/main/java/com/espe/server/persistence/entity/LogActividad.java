package com.espe.server.persistence.entity;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "logs_actividades")
public class LogActividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "accion", nullable = false)
    private String accion;

    @Column(name = "fecha_accion", nullable = false)
    private Date fechaAccion;

    @Column(name = "detalles")
    private String detalles;

    // Getters y Setters
    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Date getFechaAccion() {
        return fechaAccion;
    }

    public void setFechaAccion(Date fechaAccion) {
        this.fechaAccion = fechaAccion;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}