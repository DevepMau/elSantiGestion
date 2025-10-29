package com.elsantigestion.model;

public class Cliente {
	
	private int id;
	private String nombre;
	private String telefono;
	private String email;
	private boolean barrioPrivado;
	private String barrioNombre;
	private int barrioLote;
	private String localidad;
	private String direccion;
	private boolean activo;
	
	public Cliente(int id, String nombre, String telefono, String email, boolean barrioPrivado, String barrioNombre, int barrioLote, String localidad, String direccion, boolean activo) {
		
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.barrioPrivado = barrioPrivado;
		this.barrioNombre = barrioNombre;
		this.barrioLote = barrioLote;
		this.localidad = localidad;
		this.direccion = direccion;
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

	public boolean isBarrioPrivado() {
		return barrioPrivado;
	}

	public void setBarrioPrivado(boolean barriPrivado) {
		this.barrioPrivado = barriPrivado;
	}

	public String getBarrioNombre() {
		return barrioNombre;
	}

	public void setBarrioNombre(String barrioNombre) {
		this.barrioNombre = barrioNombre;
	}

	public int getBarrioLote() {
		return barrioLote;
	}

	public void setBarrioLote(int barrioLote) {
		this.barrioLote = barrioLote;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	

}
