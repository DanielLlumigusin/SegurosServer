package com.espe.SegurosServer.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;

@Entity
public class LogActividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotEmpty(message = "La acción es obligatoria")
    private String accion;

    @NotEmpty(message = "La fecha y hora son obligatorias")
    @PastOrPresent(message = "La fecha y hora deben ser pasadas o presentes")
    private LocalDate fechaHora;

    @NotEmpty(message = "La IP es obligatoria")
    private String ip;

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

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public LocalDate getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDate fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

    
    
}

