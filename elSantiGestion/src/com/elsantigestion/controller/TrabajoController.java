package com.elsantigestion.controller;

import com.elsantigestion.dao.TrabajoDAO;
import com.elsantigestion.model.Trabajo;
import com.elsantigestion.ui.Alerta;
import com.elsantigestion.ui.TrabajoForm;
import com.elsantigestion.ui.TrabajoView;

import javafx.collections.FXCollections;

public class TrabajoController {
	
	private TrabajoView view;
	private TrabajoDAO dao;
	
	public TrabajoController(TrabajoView view, TrabajoDAO dao) {
		this.view = view;
		this.dao = dao;
		inicializar();
	}
	
	public TrabajoView getView() {
		return this.view;
	}
	
	public void guardarTrabajo(Trabajo trabajo) {
	    try {
	        trabajo.validar();
	        if(dao.existeTrabajoPorId(trabajo.getId())) {
	        	dao.actualizarTrabajo(trabajo);
	        }
	        else{
	        	dao.agregarTrabajo(trabajo);
	        }
	        Alerta.info("Formulario de trabajo", "El trabajo se guardo correctamente!");
	    } catch (IllegalStateException e) {
	    	Alerta.warning("Error al validar datos", e.getMessage());
	    } catch (Exception e) {
	        Alerta.warning("Error al guardar el trabajo", e.getMessage());
	    }
	}
	
	public void inicializar() {
		view.getTabla().setItems(FXCollections.observableArrayList(dao.obtenerTrabajos()));
		
		view.getBtnNuevo().setOnAction(e -> mostrarFormularioNuevo());
		view.getBtnModificar().setOnAction(e -> mostrarFormularioModificar());
		view.getBtnEliminar().setOnAction(e -> eliminarSeleccionado());
	}
	
	public void refrescarTabla() {
		view.getTabla().getItems().setAll(dao.obtenerTrabajos());
	}
	
	private void mostrarFormularioNuevo() {
		TrabajoForm form = new TrabajoForm(null);
		form.showAndWait();
		
		Trabajo trabajo = form.getTrabajo();
	    if (trabajo != null) {
	        guardarTrabajo(trabajo);
	        refrescarTabla();
	    }
	}
	
	private void mostrarFormularioModificar() {
		Trabajo seleccionado = view.getTabla().getSelectionModel().getSelectedItem();
	    
	    if (seleccionado != null) {
	        TrabajoForm form = new TrabajoForm(seleccionado);
	        form.showAndWait();
	        
	        Trabajo trabajo = form.getTrabajo();
		    if (form.getTrabajo() != null) {
		        guardarTrabajo(trabajo);
		        refrescarTabla();
		    }
	    } else {
	    	Alerta.warning("Atención", "Debe seleccionar un trabajo para modificar.");
	    }
	}
	
	private void eliminarSeleccionado() {
		Trabajo seleccionado = view.getTabla().getSelectionModel().getSelectedItem();

	    if (seleccionado != null) {
	        boolean confirmado = Alerta.confirmar(
	            "Confirmar eliminación",
	            "¿Está seguro de eliminar el trabajo: " + seleccionado.getNombre() + "?"
	        );
	        if (confirmado) {
	            dao.eliminarTrabajo(seleccionado.getId());
	            refrescarTabla();
	        }
	    } else {
	        Alerta.warning("Atención", "Debe seleccionar un trabajo para eliminar.");
	    }
	}

}
