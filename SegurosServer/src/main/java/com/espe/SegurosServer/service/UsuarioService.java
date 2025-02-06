package com.espe.SegurosServer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espe.SegurosServer.repository.IUsuarioRepository;
import com.espe.SegurosServer.entities.Usuario;


@Service
public class UsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	public List<Usuario> getUsuarios(){
		return(List<Usuario>) usuarioRepository.findAll();
	}
	
	public Optional<Usuario> findByUsuarioId(Long id){
		return usuarioRepository.findById(id);
	}
	
	public Usuario saveUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public Usuario updateUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public Boolean deleteUsuario(Long id) {
		if(usuarioRepository.existsById(id)) {
			usuarioRepository.deleteById(id);
			return true;
		}else {
			return false;
		}
	}
	
}
