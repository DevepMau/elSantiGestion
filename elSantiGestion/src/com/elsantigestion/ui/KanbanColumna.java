package com.elsantigestion.ui;

import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
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
		
		setOnDragOver(e -> {
            if (e.getGestureSource() instanceof TareaTarjeta && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });

        setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasString()) {

            	TareaTarjeta cardOrigen = (TareaTarjeta) e.getGestureSource();

                ((VBox) cardOrigen.getParent()).getChildren().remove(cardOrigen);

                getChildren().add(cardOrigen);

                e.setDropCompleted(true);
            }
            e.consume();
        });
		
		this.getChildren().addAll(boxTitulo, contenedor);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/kanban.css").toExternalForm()
    	   );
	}
	
	public void añadirTarjeta(TareaTarjeta c) {
        contenedor.getChildren().add(c);
    }

}
