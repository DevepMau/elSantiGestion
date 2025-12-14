package com.elsantigestion.ui;

import java.util.ArrayList;

import com.elsantigestion.model.TareaProvisorio;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

public class KanbanView extends HBox {
	
	private KanbanColumna colPorHacer;
	private KanbanColumna colEnProgreso;
	private KanbanColumna colCompletado;
	private KanbanColumna colArchivado;
	
	private TareaProvisorio task1 = new TareaProvisorio("Laura", "Canteros", 3, false, false);
	private KanbanTarjeta card1 = new KanbanTarjeta(task1);
	private TareaProvisorio task2 = new TareaProvisorio("Maria", "Corte", 1, false, false);
	private KanbanTarjeta card2 = new KanbanTarjeta(task2);
	private TareaProvisorio task3 = new TareaProvisorio("Silvia", "Piscina", 1, false, false);
	private KanbanTarjeta card3 = new KanbanTarjeta(task3);
	
	private ArrayList<KanbanTarjeta> listaCard = new ArrayList<>();
	
	public KanbanView() {
		
		this.colPorHacer = new KanbanColumna("Por Hacer");
		this.colEnProgreso = new KanbanColumna("En Progreso");
		this.colCompletado = new KanbanColumna("Completado");
		this.colArchivado = new KanbanColumna("Archivado");
		
		listaCard.add(card1);
		listaCard.add(card2);
		listaCard.add(card3);
		
		
		for(KanbanTarjeta card : listaCard) {
			card.setOnDragDetected(event -> {
	            Dragboard db = card.startDragAndDrop(TransferMode.MOVE);

	            ClipboardContent content = new ClipboardContent();
	            content.putString(card.getTitulo()+card.getDescripcion());
	            System.out.println(content);
	            db.setContent(content);
	            
	            db.setDragView(card.snapshot(null, null));

	            event.consume();
	        });
			
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
