package com.elsantigestion.model;

public class ServicioDetalles {
	
	private int servicioId;
	private int trabajoId;
	private int cantidad;
	private String estado;
	
	public ServicioDetalles(int servicioId, int trabajoId, int cantidad, String estado) {
		
		this.servicioId = servicioId;
		this.trabajoId = trabajoId;
		this.cantidad = cantidad;
		this.estado = estado;
		
	}

	public int getServicioId() {
		return servicioId;
	}

	public void setServicioId(int servicioEventualId) {
		this.servicioId = servicioEventualId;
	}

	public int getTrabajoId() {
		return trabajoId;
	}

	public void setTrabajoId(int trabajoId) {
		this.trabajoId = trabajoId;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	

}
