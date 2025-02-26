package com.espe.server.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private TipoRol roleEnum;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permiso> permisosList = new HashSet<>();

    public Rol() {
		// TODO Auto-generated constructor stub
	}
    
	public Rol(TipoRol roleEnum) {
		super();
		this.roleEnum = roleEnum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoRol getRoleEnum() {
		return roleEnum;
	}

	public void setRoleEnum(TipoRol roleEnum) {
		this.roleEnum = roleEnum;
	}

	public Set<Permiso> getPermisosList() {
		return permisosList;
	}

	public void setPermisosList(Set<Permiso> permisosList) {
		this.permisosList = permisosList;
	}
    
    
}