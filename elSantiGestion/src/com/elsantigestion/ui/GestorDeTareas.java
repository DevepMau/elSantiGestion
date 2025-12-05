package com.elsantigestion.ui;

import java.util.HashMap;

import com.elsantigestion.model.Servicio;
import com.elsantigestion.model.ServicioTrabajo;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GestorDeTareas extends VBox {
	
	private final String TITULO = "Lista de tareas";
	
	private Servicio servicioSeleccionado;
	private TableView<ServicioTrabajo> tabla;
	private HashMap<Integer, String> listaClientes;
	private HashMap<Integer, String> listaTrabajos;
	private GridPane grid;
	private Label lblTitulo;
	private HBox boxTitulo;
	private HBox boxContenido;
	
	public GestorDeTareas(HashMap<Integer, String> listaClientes, HashMap<Integer, String> listaTrabajos) {
		
		this.listaClientes = listaClientes;
		this.listaTrabajos = listaTrabajos;
		this.tabla = new TableView<>();
		this.lblTitulo = new Label(TITULO);
		this.boxTitulo = new HBox(lblTitulo);
		this.grid = new GridPane();
		this.boxContenido = new HBox(tabla, grid);
		
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
		
		this.getStyleClass().add("box-tabla");
		boxContenido.getStyleClass().add("box-tabla");
		boxTitulo.getStyleClass().add("box-titulo");
		
		tabla.getColumns().add(colTrabajo);
		tabla.getColumns().add(colCantidad);
		tabla.getColumns().add(colEstado);
		
		tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tabla.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(tabla, javafx.scene.layout.Priority.ALWAYS);
		
		this.getChildren().addAll(boxTitulo, boxContenido);
		
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/gestorDeTareas.css").toExternalForm()
    	   );
		
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

	public Label getLblTitulo() {
		return lblTitulo;
	}

	public void setLblTitulo(Label lblTitulo) {
		this.lblTitulo = lblTitulo;
	}

}
