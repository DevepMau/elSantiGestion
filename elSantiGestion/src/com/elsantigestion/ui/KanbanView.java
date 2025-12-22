package com.elsantigestion.ui;

import java.util.ArrayList;
import java.util.List;

import com.elsantigestion.model.ServicioDetalles;

import javafx.scene.layout.HBox;

public class KanbanView extends HBox {
	
	private KanbanColumna colPorHacer;
	private KanbanColumna colEnProgreso;
	private KanbanColumna colCompletado;
	private KanbanColumna colArchivado;
	
	private ArrayList<KanbanTarjeta> listaCard = new ArrayList<>();
	
	public KanbanView(List<ServicioDetalles> lista) {
		
		this.colPorHacer = new KanbanColumna("Por Hacer");
		this.colEnProgreso = new KanbanColumna("En Progreso");
		this.colCompletado = new KanbanColumna("Completado");
		this.colArchivado = new KanbanColumna("Archivado");
		
		this.getChildren().addAll(colPorHacer, colEnProgreso, colCompletado, colArchivado);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/kanban.css").toExternalForm()
    	   );
		
	}
	
	public void addCard(KanbanTarjeta card) {
		this.listaCard.add(card);
	}
	
	public List<KanbanTarjeta> getLista(){
		return this.listaCard;
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
