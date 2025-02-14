package com.espe.server.service;

import java.nio.charset.IllegalCharsetNameException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.print.attribute.SetOfIntegerSyntax;

import org.springframework.beans.factory.annotation.Autowired;
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

    
    // Obtener todos los usuarios
    public List<Usuario> findAllUsers() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    // Obtener un usuario por su ID
    public Optional<Usuario> findByIdUsuario(Long idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    // Crear un nuevo usuario
    public Usuario createUser(Usuario newUsuario) {
        // Buscar si el rol ya existe
        Rol defaultRol = rolRepository.findByNombreRol(TipoRol.ADMIN)
                .orElseGet(() -> {
                    Rol nuevoRol = new Rol();
                    nuevoRol.setNombreRol(TipoRol.ADMIN);
                    return rolRepository.save(nuevoRol); 
                });

        newUsuario.setRol(defaultRol);
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
    public Optional<Usuario> updateUser(Long idUsuario, Usuario updatedUsuario) {
        return usuarioRepository.findById(idUsuario).map(usuario -> {
            usuario.setNombreCompleto(updatedUsuario.getNombreCompleto());
            usuario.setIdentificacion(updatedUsuario.getIdentificacion());
            usuario.setFechaNacimiento(updatedUsuario.getFechaNacimiento());
            usuario.setDireccion(updatedUsuario.getDireccion());
            usuario.setCorreoElectronico(updatedUsuario.getCorreoElectronico());
            usuario.setContrasenaHash(updatedUsuario.getContrasenaHash());
            usuario.setRol(updatedUsuario.getRol());
            return usuarioRepository.save(usuario);
        });
    }
}