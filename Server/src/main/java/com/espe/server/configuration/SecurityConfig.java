package com.espe.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig implements WebMvcConfigurer{

	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Permite solicitudes desde el frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .allowedHeaders("*");
    }
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	        return httpSecurity
	                .csrf(csrf -> csrf.disable())
	                .httpBasic(Customizer.withDefaults())
	                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	                .authorizeHttpRequests(http -> {
	                    // Configurar los endpoints publicos
	                    http.requestMatchers(HttpMethod.GET, "/api/login").permitAll();
	                    http.requestMatchers(HttpMethod.GET, "/api/register").permitAll();
	                    http.requestMatchers(HttpMethod.GET,"/api/usuarios").permitAll();
	                    http.requestMatchers(HttpMethod.GET,"/api/usuarios/{idUsuarios}").permitAll();
	                    http.requestMatchers(HttpMethod.POST,"/api/usuarios").permitAll();
	                    // Cofnigurar los endpoints privados
	                    http.requestMatchers(HttpMethod.POST, "/auth/post").hasAnyRole("ADMIN", "DEVELOPER");
	                    http.requestMatchers(HttpMethod.PATCH, "/auth/patch").hasAnyAuthority("REFACTOR");

	                    // Configurar el resto de endpoint - NO ESPECIFICADOS
	                    http.anyRequest().denyAll();
	                })
	                .build();
	    }
	 
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }

	    @Bean
	    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailService){
	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setPasswordEncoder(passwordEncoder());
	        provider.setUserDetailsService(userDetailService);
	        return provider;
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }
}
