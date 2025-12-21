package com.elsantigestion.ui;

import com.elsantigestion.model.Cliente;
import com.elsantigestion.model.ServicioDetalles;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class KanbanTarjeta extends Pane {
	
	private ServicioDetalles tarea;
	private Label lblCliente;
	private Label lblTrabajo;
	private Label lblCantidad;
	private VBox contenedor;
	
	public KanbanTarjeta(Cliente cliente, ServicioDetalles tarea) {
		this.setTarea(tarea);
		this.lblCliente = new Label(cliente.getNombre());
		this.lblTrabajo = new Label(tarea.getTrabajo().getNombre());
		this.lblCantidad = new Label("cant: "+tarea.getCantidad()+"/"+tarea.getCantidad());
		this.contenedor = new VBox(5, lblCliente, lblTrabajo, lblCantidad);
		
		this.getStyleClass().add("mi-pane");
		this.setStyle("-fx-background-color: " + cliente.getColor() + ";");
		contenedor.getStyleClass().add("tarjeta");
		
		this.getChildren().addAll(contenedor);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/kanban.css").toExternalForm()
    	   );
		
	}
	
	/////////////////////////////////////////////////////

	public ServicioDetalles getTarea() {
		return tarea;
	}

	public void setTarea(ServicioDetalles tarea) {
		this.tarea = tarea;
	}
	
	public String getTitulo() {
		return lblCliente.getText();
	}
	
	public String getDescripcion() {
		return lblTrabajo.getText();
	}
	
	

}
