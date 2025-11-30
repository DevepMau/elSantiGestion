package com.elsantigestion.ui;

import java.util.HashMap;

import com.elsantigestion.model.Servicio;
import com.elsantigestion.model.ServicioTrabajo;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

public class GestorDeTareas extends HBox {
	
	private Servicio servicioSeleccionado;
	private TableView<ServicioTrabajo> tabla;
	private HashMap<Integer, String> listaClientes;
	private HashMap<Integer, String> listaTrabajos;
	private HBox boxTabla;
	private HBox boxOpciones;
	
	public GestorDeTareas(HashMap<Integer, String> listaClientes, HashMap<Integer, String> listaTrabajos) {
		
		this.listaClientes = listaClientes;
		this.listaTrabajos = listaTrabajos;
		this.tabla = new TableView<>();
		this.boxTabla = new HBox(tabla);
		this.boxOpciones = new HBox();
		
		TableColumn<ServicioTrabajo, String> colTrabajo = new TableColumn<>("Trabajo");
		TableColumn<ServicioTrabajo, Integer> colCantidad = new TableColumn<>("Cantidad");
		TableColumn<ServicioTrabajo, Boolean> colEstado = new TableColumn<>("Estado");
		
		colTrabajo.setCellValueFactory(c -> {
		    int id = c.getValue().getTrabajoId();
		    String nombre = listaTrabajos.get(id);
		    return new SimpleStringProperty(nombre);
		});
		colCantidad.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getCantidad()).asObject());
		colEstado.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isActivo()));
		
		colTrabajo.getStyleClass().add("columna-personalizada");
		colCantidad.getStyleClass().add("columna-personalizada");
		colEstado.getStyleClass().add("columna-personalizada");
		
		tabla.getColumns().add(colTrabajo);
		tabla.getColumns().add(colCantidad);
		tabla.getColumns().add(colEstado);
		
		tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tabla.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(tabla, javafx.scene.layout.Priority.ALWAYS);
		
		this.getChildren().addAll(boxTabla, boxOpciones);
		
		/*this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/trabajoChecker.css").toExternalForm()
    	   );*/
		
	}
	
	public TableView<ServicioTrabajo> getTabla() {
		return tabla;
	}

	public Servicio getServicioSeleccionado() {
		return servicioSeleccionado;
	}

	public HashMap<Integer, String> getListaClientes() {
		return listaClientes;
	}

	public HashMap<Integer, String> getListaTrabajos() {
		return listaTrabajos;
	}

	public void setServicioSeleccionado(Servicio servicioSeleccionado) {
		this.servicioSeleccionado = servicioSeleccionado;
	}

	public void setListaClientes(HashMap<Integer, String> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public void setListaTrabajos(HashMap<Integer, String> listaTrabajos) {
		this.listaTrabajos = listaTrabajos;
	}

}
