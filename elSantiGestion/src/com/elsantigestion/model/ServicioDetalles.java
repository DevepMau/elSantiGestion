package com.elsantigestion.model;

public class ServicioDetalles {

    private Servicio servicio;
    private Trabajo trabajo;
    private int cantidad;
    private String estado;

    public ServicioDetalles(Servicio servicio, Trabajo trabajo, int cantidad, String estado) {
        this.servicio = servicio;
        this.trabajo = trabajo;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
