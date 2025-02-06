package com.espe.SegurosServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.espe.SegurosServer.service.UsuarioService;


@Controller
@RequestMapping("api/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioService.getUsuarios());
	}
	
}
