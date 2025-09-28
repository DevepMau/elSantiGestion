package com.elsantigestion.model;

import java.time.LocalDate;

public class ServicioEventual {
	
	private int id;
	private int clienteId;
	private LocalDate fechaCreacion;
	private LocalDate fechaProgramada;
	private Double precio;
	private Double gastos;
	private Double montoFinal;
	
	public ServicioEventual(int id, int clienteId, LocalDate fechaCreacion, LocalDate fechaProgramada, Double precio, Double gastos, Double montoFinal) {
		
		this.id = id;
		this.clienteId = clienteId;
		this.fechaCreacion = fechaCreacion;
		this.fechaProgramada = fechaProgramada;
		this.precio = precio;
		this.gastos = gastos;
		this.montoFinal = montoFinal;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDate getFechaProgramada() {
		return fechaProgramada;
	}

	public void setFechaProgramada(LocalDate fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Double getGastos() {
		return gastos;
	}

	public void setGastos(Double gastos) {
		this.gastos = gastos;
	}

	public Double getMontoFinal() {
		return montoFinal;
	}

	public void setMontoFinal(Double montoFinal) {
		this.montoFinal = montoFinal;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

}
