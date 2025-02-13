package com.espe.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.Usuario;
import com.espe.server.persistence.repository.IUsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

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
            return usuarioRepository.save(usuario);
        });
    }
}