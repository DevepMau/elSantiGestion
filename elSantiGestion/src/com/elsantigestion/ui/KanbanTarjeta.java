package com.elsantigestion.ui;

import com.elsantigestion.model.TareaProvisorio;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class KanbanTarjeta extends Pane {
	
	private TareaProvisorio tarea;
	private Label lblTitulo;
	private Label lblDescripcion;
	private VBox contenedor;
	
	public KanbanTarjeta(TareaProvisorio tarea) {
		this.setTarea(tarea);
		this.lblTitulo = new Label(this.tarea.getTitulo());
		this.lblDescripcion = new Label(this.tarea.getDescripcion());
		this.contenedor = new VBox(5, lblTitulo, lblDescripcion);
		
		this.getStyleClass().add("mi-pane");
		contenedor.getStyleClass().add("tarjeta");
		
		this.getChildren().addAll(contenedor);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/kanban.css").toExternalForm()
    	   );
		
	}
	
	/////////////////////////////////////////////////////

	public TareaProvisorio getTarea() {
		return tarea;
	}

	public void setTarea(TareaProvisorio tarea) {
		this.tarea = tarea;
	}
	
	public String getTitulo() {
		return lblTitulo.getText();
	}
	
	public String getDescripcion() {
		return lblDescripcion.getText();
	}
	
	

}
