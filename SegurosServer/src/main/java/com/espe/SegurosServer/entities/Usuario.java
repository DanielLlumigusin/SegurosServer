package com.espe.SegurosServer.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre completo es obligatorio")
    @Size(max = 20, message = "El o los nombres completos no debe exceder los 20 caracteres")
    private String nombre;
    
    @NotEmpty(message = "El nombre completo es obligatorio")
    @Size(max = 20, message = "El o los apellidos completos no debe exceder los 20 caracteres")
    private String apellido;

    @NotEmpty(message = "El número de identificación es obligatorio")
    @Size(max = 10, message = "El número de identificación no debe exceder los 10 caracteres")
    @Column(unique = true)
    private String numeroIdentificacion;

    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private Date fechaNacimiento;

    @NotEmpty(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no debe exceder los 255 caracteres")
    private String direccion;

    @NotEmpty(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "La contraseña es obligatoria")
    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;

    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;

    @Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();
    
}
