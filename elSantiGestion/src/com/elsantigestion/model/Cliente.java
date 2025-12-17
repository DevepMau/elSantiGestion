package com.elsantigestion.model;

import java.time.LocalDate;

public class Cliente {
	
	private int id;
	private String nombre;
	private String telefono;
	private String email;
	private boolean barrioPrivado;
	private int numeroLote;
	private String localidad;
	private String direccion;
	private String color;
	private boolean activo;
	private LocalDate fechaCreacion;
	
	public Cliente(int id, String nombre, String telefono, String email, boolean barrioPrivado, int numeroLote, String localidad, String direccion, String color, boolean activo, LocalDate fechaCreacion) {
		
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.barrioPrivado = barrioPrivado;
		this.numeroLote = numeroLote;
		this.localidad = localidad;
		this.direccion = direccion;
		this.color = color;
		this.activo = activo;
		this.fechaCreacion = fechaCreacion;
		
	}
	
	public void validar() {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalStateException("El nombre no puede estar vacío.");
        }
        
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalStateException("El telefono no puede estar vacío.");
        }

        if (telefono != null && !telefono.matches("\\d{7,15}")) {
            throw new IllegalStateException("El teléfono debe tener entre 7 y 15 números.");
        }

        if (localidad == null || localidad.trim().isEmpty()) {
            throw new IllegalStateException("Debe especificar la localidad del cliente.");
        }
        
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalStateException("Debe especificar el color del cliente.");
        }
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

	public int getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(int barrioLote) {
		this.numeroLote = barrioLote;
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
	
	public String getColor() {
	    return color;
	}

	public void setColor(String color) {
	    this.color = color;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}	

}
