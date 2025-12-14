package com.elsantigestion.controller;

import com.elsantigestion.ui.KanbanColumna;
import com.elsantigestion.ui.KanbanView;
import com.elsantigestion.ui.KanbanTarjeta;

import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

public class KanbanController {
	
	private KanbanView tablero;
	
	public KanbanController(KanbanView tablero) {
		this.tablero = tablero;
		inicializar();
	}
	
	private void inicializar() {
		setOnDragAndDrop(tablero.getColPorHacer());
		setOnDragAndDrop(tablero.getColEnProgreso());
		setOnDragAndDrop(tablero.getColCompletado());
		setOnDragAndDrop(tablero.getColArchivado());
	}
	
	private void setOnDragAndDrop(KanbanColumna col) {
		col.setOnDragOver(e -> {
            if (e.getGestureSource() instanceof KanbanTarjeta && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });

		col.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasString()) {
            	KanbanTarjeta cardOrigen = (KanbanTarjeta) e.getGestureSource();
                ((VBox) cardOrigen.getParent()).getChildren().remove(cardOrigen);
                col.añadirTarjeta(cardOrigen);
                e.setDropCompleted(true);
            }
            e.consume();
        });
	}
	
	public KanbanView getView() {
		return this.tablero;
	}

}
