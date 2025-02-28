package com.espe.server.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.LogActividad;
import com.espe.server.persistence.entity.TipoRol;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.persistence.repository.ILogActividadRepository;
import com.espe.server.persistence.repository.IUsuarioRepository;

@Service
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final ILogActividadRepository logActividadRepository;
    
    public UsuarioService(
    		IUsuarioRepository usuarioRepository,
    		ILogActividadRepository logActividadRepository
    		) {
		this.usuarioRepository = usuarioRepository;
		this.logActividadRepository = logActividadRepository;
	}
    
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


 // Eliminar un usuario por su ID
    public boolean deleteUser(Long idUsuario, String username) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findUsuarioByUsername(username);
        
        if (!usuarioOpt.isPresent()) {
            return false;
        }

        Usuario usuario = usuarioOpt.get();
        LogActividad logActividad = new LogActividad(
                usuario, 
                "Eliminar Usuario",
                LocalDate.now(), 
                "El usuario con ID " + idUsuario + " ha sido eliminado"
        );
        
        if (usuarioRepository.existsById(idUsuario)) {
           
            usuarioRepository.deleteById(idUsuario);

            logActividadRepository.save(logActividad);

            return true;
        } else {
            return false;
        }
    }


 // Actualizar un usuario existente
    public Optional<Usuario> updateUser(Long idUsuario, Usuario updatedUsuario, String username) {
        // Buscar el usuario en la base de datos
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        
        // Si no existe el usuario, devolver Optional vacío
        if (!usuarioOpt.isPresent()) {
            return Optional.empty();
        }

        // Obtener el usuario existente
        Usuario usuarioExistente = usuarioOpt.get();

        // Crear el log de actividad para la actualización
        LogActividad logActividad = new LogActividad(
                usuarioExistente, 
                "Actualizar Usuario",
                LocalDate.now(), 
                "El usuario con ID " + idUsuario + " ha sido actualizado."
        );

        // Verificar qué campos han cambiado
        boolean cambios = false;
        if (!usuarioExistente.getNombreCompleto().equals(updatedUsuario.getNombreCompleto())) {
            usuarioExistente.setNombreCompleto(updatedUsuario.getNombreCompleto());
            cambios = true;
        }
        if (!usuarioExistente.getFechaNacimiento().equals(updatedUsuario.getFechaNacimiento())) {
            usuarioExistente.setFechaNacimiento(updatedUsuario.getFechaNacimiento());
            cambios = true;
        }
        if (!usuarioExistente.getDireccion().equals(updatedUsuario.getDireccion())) {
            usuarioExistente.setDireccion(updatedUsuario.getDireccion());
            cambios = true;
        }

        // Si hay cambios, guardamos la actividad en el log y actualizamos el usuario
        if (cambios) {
            logActividadRepository.save(logActividad);
            return Optional.of(usuarioRepository.save(usuarioExistente));
        } else {
            return Optional.empty();  // Si no hubo cambios, no actualizamos ni registramos nada
        }
    }
}

