package com.espe.server.utils;

import java.util.Base64;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class InfoCookie {
	public  String getUsernameFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) { 
                    String token = cookie.getValue();
                    String[] parts = token.split("\\.");
                    if (parts.length == 3) {
                        String payload = parts[1];
                        // Agregar relleno para base64 si es necesario
                        int padding = payload.length() % 4;
                        if (padding > 0) {
                            payload += "=".repeat(4 - padding);
                        }
                        // Decodificar en base64
                        String decodedPayload = new String(Base64.getDecoder().decode(payload));
                        try {
                            // Usar Jackson para convertir el payload en un objeto Map
                            ObjectMapper objectMapper = new ObjectMapper();
                            Map<String, Object> payloadMap = objectMapper.readValue(decodedPayload, Map.class);
                            return (String) payloadMap.get("sub");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
}
