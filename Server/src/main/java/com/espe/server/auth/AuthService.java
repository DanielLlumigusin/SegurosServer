package com.espe.server.auth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import javax.management.relation.Role;
import javax.management.relation.RoleInfoNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.espe.server.jwt.JwtService;
import com.espe.server.persistence.entity.Rol;
import com.espe.server.persistence.entity.TipoRol;
import com.espe.server.persistence.entity.Usuario;
import com.espe.server.persistence.repository.IUsuarioRepository;

@Service
public class AuthService {

    private final IUsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
        IUsuarioRepository userRepository,
        JwtService jwtService,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(LoginRequest request) {
        Usuario user = userRepository.findUsuarioByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtService.getToken(user);
        return new AuthResponse(token);
    }
    
    public AuthResponse loginAdmin(LoginRequest request) {
        // Buscar al usuario por nombre de usuario
        Usuario usuario = userRepository.findUsuarioByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si el usuario tiene el rol ADMIN
        boolean esAdmin = usuario.getRoles().stream()
                .anyMatch(rol -> rol.getRoleEnum().name().equals("ADMIN"));
        if (!esAdmin) {
            throw new RuntimeException("Acceso denegado: No eres administrador");
        }

        // Verificar la contraseña
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Generar token JWT
        String token = jwtService.getToken(usuario);
        return new AuthResponse(token);
    }




    public AuthResponse register(RegisterRequest request) throws ParseException {
        Set<Rol> roles = new HashSet<>();
        roles.add(new Rol(TipoRol.USER)); 

        Usuario user = new Usuario();
        
        user.setNombreCompleto(request.getNombreCompleto());
        user.setCedula(request.getCedula());
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        user.setFechaNacimiento(sdf.parse((String) request.getFechaNacimiento()));
            

        user.setDireccion(request.getDireccion());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles);

        userRepository.save(user);

        return new AuthResponse(jwtService.getToken(user));
    }
}
