package com.espe.server.configuration;


import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

  @Value("${daniel.app.jwtSecret}")
  private String jwtSecret;

  @Value("${daniel.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {
	    UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

	    return Jwts.builder()
	        .setSubject((userPrincipal.getUsername())) // El subject es el nombre de usuario.
	        .setIssuedAt(new Date()) // Establece la fecha de emisión del token.
	        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Establece la fecha de expiración.
	        .signWith(key(), SignatureAlgorithm.HS256) // Firma con la clave secreta utilizando el algoritmo HS256.
	        .compact(); // Devuelve el JWT en formato compactado.
	}

  
  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public String getUserNameFromJwtToken(String token) {
	    return Jwts.parser() // Use parserBuilder for jjwt 0.11.0+
	            .setSigningKey(key())  // Set the signing key
	            .build()  // Build the parser
	            .parseClaimsJws(token) // Parse the JWT token
	            .getBody() // Get the body of the claims
	            .getSubject(); // Extract the subject (username)
	}



  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(key()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}