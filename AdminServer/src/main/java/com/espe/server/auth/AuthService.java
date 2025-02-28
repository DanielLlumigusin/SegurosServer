package com.espe.server.auth;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.espe.server.jwt.JwtService;
import com.espe.server.persistence.entity.TipoRol;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.persistence.repository.IUsuarioRepository;

@Service
public class AuthService {

    private final IUsuarioRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
        IUsuarioRepository userRepository,
        JwtService jwtService,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest request) {

    	authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Usuario user = userRepository.findUsuarioByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

}
