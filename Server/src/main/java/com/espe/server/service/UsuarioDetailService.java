package com.espe.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.Usuario;
import com.espe.server.persistence.repository.IUsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioDetailService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findUsuarioByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        usuario.getRoles()
        	.forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        usuario.getRoles().stream()
        	.flatMap(role -> role.getPermisosList().stream())
        	.forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getNombre())));


        return new Usuario(usuario, authorityList);  
    }
}
