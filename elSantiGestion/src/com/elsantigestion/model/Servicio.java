package com.elsantigestion.model;

import java.time.LocalDate;

public class Servicio {
	
	private int id;
	private int clienteId;
	private LocalDate fechaCreacion;
	private LocalDate fechaProgramada;
	private String tipo;
	private Double precio;
	private Double gastos;
	private Double montoFinal;
	private String estado;
	
	public Servicio(int id, int clienteId, LocalDate fechaCreacion, LocalDate fechaProgramada, String tipo, Double precio, Double gastos, Double montoFinal, String estado) {
		
		this.id = id;
		this.clienteId = clienteId;
		this.fechaCreacion = fechaCreacion;
		this.fechaProgramada = fechaProgramada;
		this.tipo = tipo;
		this.precio = precio;
		this.gastos = gastos;
		this.montoFinal = montoFinal;
		this.estado = estado;
		
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
