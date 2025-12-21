package com.elsantigestion.model;

public class Trabajo {
	
	private int id;
	private String nombre;
	private String detalle;
	private double precio;
	private String unidad;
	private boolean activo;
	
	public Trabajo(int id, String nombre, String detalle, double precio, String unidad, boolean activo) {
		
		this.id = id;
		this.nombre = nombre;
		this.detalle = detalle;
		this.precio = precio;
		this.unidad = unidad;
		this.activo = activo;
		
	}
	
	public Trabajo(int id, String nombre) {
	    this.id = id;
	    this.nombre = nombre;
	}
	
	public void validar() {
		if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser un número positivo.");
        }
        
        if (unidad == null || unidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La unidad no puede estar vacío.");
        }
	}

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

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
