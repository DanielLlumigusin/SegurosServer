package com.espe.server.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;

    @Column(nullable = false, unique = true)
    private TipoRol nombreRol;

    public Rol() {
    	
    }
    
    // Getters y Setters
    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public TipoRol getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(TipoRol nombreRol) {
        this.nombreRol = nombreRol;
    }
}