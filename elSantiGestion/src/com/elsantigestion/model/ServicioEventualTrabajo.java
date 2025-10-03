package com.elsantigestion.model;

public class ServicioEventualTrabajo {
	
	private int servicioEventualId;
	private int trabajoId;
	private int cantidad;
	
	public ServicioEventualTrabajo(int servicioId, int trabajoId, int cantidad) {
		
		this.servicioEventualId = servicioId;
		this.trabajoId = trabajoId;
		this.cantidad = cantidad;
		
	}

	public int getServicioEventualId() {
		return servicioEventualId;
	}

	public void setServicioEventualId(int servicioEventualId) {
		this.servicioEventualId = servicioEventualId;
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
