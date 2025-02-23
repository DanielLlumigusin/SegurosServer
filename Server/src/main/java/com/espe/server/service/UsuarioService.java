package com.espe.server.service;

import java.nio.charset.IllegalCharsetNameException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.print.attribute.SetOfIntegerSyntax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.Rol;
import com.espe.server.persistence.entity.TipoRol;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.persistence.repository.IRolRepository;
import com.espe.server.persistence.repository.IUsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;
    
    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // Obtener todos los usuarios
    public List<Usuario> findAllUsers() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    // Obtener un usuario por su ID
    public Optional<Usuario> findByIdUsuario(Long idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }
    
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findUsuarioByUsername(username);
    }

    // Crear un nuevo usuario
    public Usuario createUser(Usuario newUsuario) {
        // Encriptamos la contraseña
        newUsuario.setPassword(passwordEncoder.encode(newUsuario.getPassword()));
        
        // Asignar un rol por defecto si no tiene uno
        Rol defaultRol = rolRepository.findByRoleEnum(TipoRol.USER)
                .orElseGet(() -> {
                    Rol nuevoRol = new Rol(TipoRol.USER);
                    nuevoRol.setRoleEnum(TipoRol.USER);
                    return rolRepository.save(nuevoRol); 
                });

        newUsuario.setRoles(Set.of(defaultRol));
        
        return usuarioRepository.save(newUsuario);
    }

    // Eliminar un usuario por su ID
    public boolean deleteUser(Long idUsuario) {
        if (usuarioRepository.existsById(idUsuario)) {
            usuarioRepository.deleteById(idUsuario);
            return true;
        } else {
            return false;
        }
    }

    // Actualizar un usuario existente
 // Actualizar un usuario existente
    public Optional<Usuario> updateUser(Long idUsuario, Usuario updatedUsuario) {
        return usuarioRepository.findById(idUsuario).map(usuarioExistente -> {
            usuarioExistente.setNombreCompleto(updatedUsuario.getNombreCompleto());
            usuarioExistente.setFechaNacimiento(updatedUsuario.getFechaNacimiento());
            usuarioExistente.setDireccion(updatedUsuario.getDireccion());

            return usuarioRepository.save(usuarioExistente);
        });
    }

}

