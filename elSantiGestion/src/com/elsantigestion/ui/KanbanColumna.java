package com.elsantigestion.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class KanbanColumna extends VBox {
	
	private Label titulo;
	private HBox boxTitulo;
	private VBox contenedor;
	
	public KanbanColumna(String nombreColumna) {
		this.titulo = new Label(nombreColumna);
		this.boxTitulo = new HBox(titulo);
		this.contenedor = new VBox(5);
		
		this.getStyleClass().add("columna");
		contenedor.getStyleClass().add("columna-contenedor");
		boxTitulo.getStyleClass().add("columna-titulo");
		
		this.getChildren().addAll(boxTitulo, contenedor);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/kanban.css").toExternalForm()
    	   );
	}
	
	public void añadirTarjeta(KanbanTarjeta c) {
        contenedor.getChildren().add(c);
    }

}
