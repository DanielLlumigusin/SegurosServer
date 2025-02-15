package com.espe.server.Auth;

public class RegisterRequest {

	String nombreCompleto;
	String cedula;
	String fechaNacimiento;
	String direccion;
	String username;
	String password;
	
	public RegisterRequest() {
	}
	
	public RegisterRequest(String nombreCompleto, String cedula, String fechaNacimiento, String direccion,
			String username, String password) {
		super();
		this.nombreCompleto = nombreCompleto;
		this.cedula = cedula;
		this.fechaNacimiento = fechaNacimiento;
		this.direccion = direccion;
		this.username = username;
		this.password = password;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
