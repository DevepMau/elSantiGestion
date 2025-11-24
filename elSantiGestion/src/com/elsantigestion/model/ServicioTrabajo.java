package com.elsantigestion.model;

public class ServicioTrabajo {
	
	private int servicioId;
	private int trabajoId;
	private int cantidad;
	private boolean activo;
	
	public ServicioTrabajo(int servicioId, int trabajoId, int cantidad, boolean activo) {
		
		this.servicioId = servicioId;
		this.trabajoId = trabajoId;
		this.cantidad = cantidad;
		this.activo = activo;
		
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	

}
