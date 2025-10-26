package com.elsantigestion.model;

public class ServicioTrabajo {
	
	private int servicioId;
	private int trabajoId;
	private int cantidad;
	
	public ServicioTrabajo(int servicioId, int trabajoId, int cantidad) {
		
		this.servicioId = servicioId;
		this.trabajoId = trabajoId;
		this.cantidad = cantidad;
		
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
	

}
