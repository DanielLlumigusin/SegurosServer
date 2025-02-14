package com.espe.server.persistence.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.espe.server.persistence.entity.Usuario;

import java.util.Collection;

public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;  
    private final Collection<? extends GrantedAuthority> authorities;  

    public UsuarioDetails(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        this.usuario = usuario;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();  
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();  // Usas el correo electrónico como el username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Define según las reglas de tu negocio (puedes usar un campo en la base de datos)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Define según las reglas de tu negocio (puedes usar un campo en la base de datos)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Define según las reglas de tu negocio (puedes usar un campo en la base de datos)
    }

    @Override
    public boolean isEnabled() {
        return true;  // Define según las reglas de tu negocio (puedes usar un campo en la base de datos)
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
