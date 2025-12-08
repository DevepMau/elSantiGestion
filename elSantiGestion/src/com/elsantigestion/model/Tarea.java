package com.elsantigestion.model;

public class Tarea {
	
	private String titulo;
	private String descripcion;
	private int cantidad;
	private boolean iniciada;
	private boolean completada;
	
	public Tarea(String titulo, String descripcion, int cantidad, boolean iniciada, boolean completada) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.iniciada = iniciada;
		this.completada = completada;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public boolean isIniciada() {
		return iniciada;
	}

	public boolean isCompletada() {
		return completada;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public void setIniciada(boolean iniciada) {
		this.iniciada = iniciada;
	}

	public void setCompletada(boolean completada) {
		this.completada = completada;
	}
	

}
