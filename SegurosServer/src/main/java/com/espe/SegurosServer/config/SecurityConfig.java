package com.espe.SegurosServer.config;

import java.net.Authenticator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.espe.SegurosServer.entities.Usuario;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(http -> {
					//Configurar los endpoints publicos
					http.requestMatchers(HttpMethod.GET,"/auth/get").permitAll();
					
					//Configurar los endpoints privados
					http.requestMatchers(HttpMethod.POST, "/auth/post").hasAnyRole("ADMIN", "DEVELOPER");
					
					
					//Configurar el resto de los endpoints
					http.anyRequest().denyAll();
				}
						
						).build();
	}
	
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
//		return authenticationConfigruation.getAuthenticationManager();
//	}
//	
//	@Bean
//	public AuthenticationProvider authenticationProvider(Usuario usuario) {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(passwordEncoder());
//		provider.setUserDetailsService(usuario);
//		return provider;
//	}
//	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
