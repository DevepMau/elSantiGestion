package com.elsantigestion.controller;

import java.util.HashMap;
import java.util.List;

import com.elsantigestion.dao.ServicioDAO;
import com.elsantigestion.dao.ServicioTrabajoDAO;
import com.elsantigestion.model.Cliente;
import com.elsantigestion.model.Servicio;
import com.elsantigestion.model.ServicioTrabajo;
import com.elsantigestion.model.Trabajo;
import com.elsantigestion.ui.Alerta;
import com.elsantigestion.ui.ServicioForm;
import com.elsantigestion.ui.ServicioView;
import com.elsantigestion.ui.TrabajoChecker;

import javafx.collections.FXCollections;

public class ServicioController {
	
	private final String SERVICIO_MENSUAL = "Mensual";
	private final String SERVICIO_EVENTUAL = "Eventual";
	
	private ServicioView view;
	private ServicioDAO dao;
	private ServicioTrabajoDAO stDao;
	private List<Cliente> listaClientes;
	private List<Trabajo> listaTrabajos;
	private boolean soloEventuales;
	
	public ServicioController(ServicioView view, ServicioDAO dao, ServicioTrabajoDAO stDao, List<Cliente> listaClientes, List<Trabajo> listaTrabajos) {
		this.view = view;
		this.dao = dao;
		this.stDao = stDao;
		this.listaClientes = listaClientes;
		this.listaTrabajos = listaTrabajos;
		this.soloEventuales = true;
		inicializar();	
	}
	
	public ServicioView getView() {
		return this.view;
	}
	
	public void guardarServicio(Servicio servicio, HashMap<Integer, Integer> listaTrabajos) {
		try {
	        servicio.validar();
	        int idGenerado = dao.agregarServicio(servicio);
	        for(var trabajo : listaTrabajos.entrySet()) {
	        	ServicioTrabajo st = new ServicioTrabajo(idGenerado, trabajo.getKey(), trabajo.getValue(), true);
	        	stDao.agregar(st);
	        }
	        
	        Alerta.info("Formulario de servicio", "El servicio se guardo correctamente!");
	    } catch (IllegalStateException e) {
	    	Alerta.warning("Error al validar datos", e.getMessage());
	    } catch (Exception e) {
	        Alerta.warning("Error al guardar el servicio", e.getMessage());
	    }
	}
	
	public void modificarServicio(Servicio servicio, HashMap<Integer, Integer> listaTrabajos) {
		try {
	        servicio.validar();
	        stDao.eliminarPorServicio(servicio.getId());
	        for(var trabajo : listaTrabajos.entrySet()) {
	        	ServicioTrabajo st = new ServicioTrabajo(servicio.getId(), trabajo.getKey(), trabajo.getValue(), true);
	        	stDao.agregar(st);
	        }
	        
	        Alerta.info("Formulario de servicio", "El servicio se guardo correctamente!");
	    } catch (IllegalStateException e) {
	    	Alerta.warning("Error al validar datos", e.getMessage());
	    } catch (Exception e) {
	        Alerta.warning("Error al guardar el servicio", e.getMessage());
	    }
	}
	
	private void inicializar() {
		view.getTablaEventuales().setItems(FXCollections.observableArrayList(dao.obtenerServiciosPorTipo(SERVICIO_EVENTUAL)));
		view.getTablaMensuales().setItems(FXCollections.observableArrayList(dao.obtenerServiciosPorTipo(SERVICIO_MENSUAL)));
		
		view.getBtnNuevo().setOnAction(e -> mostrarFormularioNuevo());
		view.getBtnModificar().setOnAction(e -> mostrarFormularioModificar());
		view.getBtnEliminar().setOnAction(e -> eliminarSeleccionado());
		view.getBtnSwap().setOnAction(e -> alternarTablas());
	}
	
	public void refrescarTabla() {
		view.getTablaEventuales().getItems().setAll(dao.obtenerServiciosPorTipo(SERVICIO_EVENTUAL));
		view.getTablaMensuales().getItems().setAll(dao.obtenerServiciosPorTipo(SERVICIO_MENSUAL));
	}
	
	private void alternarTablas() {
		soloEventuales = !soloEventuales;
		cambiarOrdenDeTablas(soloEventuales);
		view.setTituloDeTabla(soloEventuales);
		refrescarTabla();
	}
	
	private void cambiarOrdenDeTablas(boolean boo) {
		if(boo) {
			view.getTablaEventuales().toFront();
			view.getTablaEventuales().setVisible(true);
			view.getTablaMensuales().setVisible(false);
		}
		else {
			view.getTablaMensuales().toFront();
			view.getTablaMensuales().setVisible(true);
			view.getTablaEventuales().setVisible(false);
		}		
	}
	
	private void mostrarFormularioNuevo() {
		ServicioForm form = new ServicioForm(null, listaClientes, new TrabajoChecker(listaTrabajos, null));
		form.showAndWait();
		
		Servicio servicio = form.getServicioCreado();
		if (servicio != null) {
			HashMap<Integer, Integer> listaTrabajos = form.getListaTrabajos();
	        guardarServicio(servicio, listaTrabajos);
	        refrescarTabla();
	    }
	}
	
	private void mostrarFormularioModificar() {
		Servicio seleccionado = null;
		
		if(soloEventuales) {
			seleccionado = view.getTablaEventuales().getSelectionModel().getSelectedItem();
		}
		else {
			seleccionado = view.getTablaMensuales().getSelectionModel().getSelectedItem();
		}
	    
	    if (seleccionado != null) {
	    	ServicioForm form = new ServicioForm(seleccionado, listaClientes, new TrabajoChecker(listaTrabajos, stDao.obtenerTrabajosPorServicio(seleccionado.getId())));
	    	form.showAndWait();
	        
	        Servicio servicio = form.getServicioCreado();
	        if (servicio != null) {
				HashMap<Integer, Integer> listaTrabajos = form.getListaTrabajos();
		        modificarServicio(servicio, listaTrabajos);
		        refrescarTabla();
		    }
	    } else {
	    	Alerta.warning("Atención", "Debe seleccionar un servicio para modificar.");
	    }
	}
	
	private void eliminarSeleccionado() {
		Servicio seleccionado = null;
		
		if(soloEventuales) {
			seleccionado = view.getTablaEventuales().getSelectionModel().getSelectedItem();
		}
		else {
			seleccionado = view.getTablaMensuales().getSelectionModel().getSelectedItem();
		}

	    if (seleccionado != null) {
	        boolean confirmado = Alerta.confirmar(
	            "Confirmar eliminación",
	            "¿Está seguro de eliminar el servicio con ID: " + seleccionado.getId() + "?"
	        );
	        if (confirmado) {
	        	stDao.eliminarPorServicio(seleccionado.getId());
	            dao.eliminarServicio(seleccionado.getId());
	            refrescarTabla();
	        }
	    } else {
	        Alerta.warning("Atención", "Debe seleccionar un servicio para eliminar.");
	    }
	}

}
