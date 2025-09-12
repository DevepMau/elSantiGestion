package com.elsantigestion.model;

public class Cliente {
	
	private int id;
	private String nombre;
	private String telefono;
	private String email;
	private boolean activo;
	
	public Cliente(int id, String nombre, String telefono, String email, boolean activo) {
		
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.activo = activo;
		
	}
	
	//GETTERS & SETTERS

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
