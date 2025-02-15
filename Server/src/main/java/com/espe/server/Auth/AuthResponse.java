package com.espe.server.Auth;

public class AuthResponse {
    String token;

    public AuthResponse() {
		// TODO Auto-generated constructor stub
	}
    
	public AuthResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	} 
    
    
}
