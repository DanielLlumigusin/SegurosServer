package com.espe.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.TipoRol;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.persistence.repository.IUsuarioRepository;

@Service
public class UsuarioService {

	private final IUsuarioRepository usuarioRepository;

	private final PasswordEncoder passwordEncoder;

	public UsuarioService(IUsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
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

	// Crear un nuevo usuario
	public Usuario createUser(Usuario newUsuario) {

		// Encriptamos la contraseña
		newUsuario.setPassword(passwordEncoder.encode(newUsuario.getPassword()));

		newUsuario.setRol(TipoRol.USER);

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
	public Optional<Usuario> updateUser(String username, Usuario updatedUsuario) {
		return usuarioRepository.findUsuarioByUsername(username).map(usuarioExistente -> {
			usuarioExistente.setNombreCompleto(updatedUsuario.getNombreCompleto());
			usuarioExistente.setFechaNacimiento(updatedUsuario.getFechaNacimiento());
			usuarioExistente.setDireccion(updatedUsuario.getDireccion());

			return usuarioRepository.save(usuarioExistente);
		});
	}

	// Actualizar la contrasenia
	public boolean updatePassword(String password, String username) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findUsuarioByUsername(username);

		if (usuarioOpt.isEmpty()) {
			return false;
		}

		Usuario usuario = usuarioOpt.get();

		if (passwordEncoder.matches(password, usuario.getPassword())) {
			throw new IllegalArgumentException("La nueva contraseña no puede ser igual a la anterior");
		}

		usuario.setPassword(passwordEncoder.encode(password));
		usuarioRepository.save(usuario);
		return true;
	}

}
