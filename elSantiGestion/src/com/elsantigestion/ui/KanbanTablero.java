package com.elsantigestion.ui;

import java.util.ArrayList;

import com.elsantigestion.model.Tarea;

import javafx.scene.layout.HBox;

public class KanbanTablero extends HBox {
	
	private KanbanColumna colPorHacer;
	private KanbanColumna colEnProgreso;
	private KanbanColumna colCompletado;
	private KanbanColumna colArchivado;
	
	private Tarea task1 = new Tarea("Laura", "Canteros", 3, false, false);
	private TareaTarjeta card1 = new TareaTarjeta(task1);
	private Tarea task2 = new Tarea("Maria", "Corte", 1, false, false);
	private TareaTarjeta card2 = new TareaTarjeta(task2);
	private Tarea task3 = new Tarea("Silvia", "Piscina", 1, false, false);
	private TareaTarjeta card3 = new TareaTarjeta(task3);
	
	private ArrayList<TareaTarjeta> listaCard = new ArrayList<>();
	
	public KanbanTablero() {
		
		this.colPorHacer = new KanbanColumna("Por Hacer");
		this.colEnProgreso = new KanbanColumna("En Progreso");
		this.colCompletado = new KanbanColumna("Completado");
		this.colArchivado = new KanbanColumna("Archivado");
		
		listaCard.add(card1);
		listaCard.add(card2);
		listaCard.add(card3);
		
		
		for(TareaTarjeta card : listaCard) {
			colPorHacer.añadirTarjeta(card);
		}
		
		this.getChildren().addAll(colPorHacer, colEnProgreso, colCompletado, colArchivado);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/kanban.css").toExternalForm()
    	   );
		
	}
	
	///////////////////////////////////////////////////////////////////

	public KanbanColumna getColPorHacer() {
		return colPorHacer;
	}

	public KanbanColumna getColEnProgreso() {
		return colEnProgreso;
	}

	public KanbanColumna getColCompletado() {
		return colCompletado;
	}

	public KanbanColumna getColArchivado() {
		return colArchivado;
	}

	public void setColPorHacer(KanbanColumna colPorHacer) {
		this.colPorHacer = colPorHacer;
	}

	public void setColEnProgreso(KanbanColumna colEnProgreso) {
		this.colEnProgreso = colEnProgreso;
	}

	public void setColCompletado(KanbanColumna colCompletado) {
		this.colCompletado = colCompletado;
	}

	public void setColArchivado(KanbanColumna colArchivado) {
		this.colArchivado = colArchivado;
	}

}
