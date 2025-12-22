package com.elsantigestion.controller;

import java.util.List;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.dao.ServicioDetallesDAO;
import com.elsantigestion.model.Cliente;
import com.elsantigestion.model.ServicioDetalles;
import com.elsantigestion.ui.KanbanColumna;
import com.elsantigestion.ui.KanbanView;
import com.elsantigestion.ui.KanbanTarjeta;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

public class KanbanController {
	
	private final String POR_HACER = "Para Hacer";
	private final String EN_PROGRESO = "En Progreso";
	private final String COMPLETADO = "Completado";
	private final String ARCHIVADO = "Archivado";
	
	private KanbanView tablero;
	private List<ServicioDetalles> listaTareas;
	private ClienteDAO clienteDao;
	private ServicioDetallesDAO detallesDao;
	
	public KanbanController(KanbanView tablero, ServicioDetallesDAO sdDao, ClienteDAO cDao) {
		this.tablero = tablero;
		this.clienteDao = cDao;
		this.detallesDao = sdDao;
		this.listaTareas = detallesDao.obtenerTodos();
		inicializar();
	}
	
	private void inicializar() {
		
		for(ServicioDetalles tarea : listaTareas) {
			Cliente cliente = clienteDao.obtenerClientePorId(tarea.getServicio().getClienteId());
			KanbanTarjeta card = new KanbanTarjeta(cliente, tarea);
			tablero.addCard(card);
		}
		
		for(KanbanTarjeta card : tablero.getLista()) {
			card.setOnDragDetected(event -> {
	            Dragboard db = card.startDragAndDrop(TransferMode.MOVE);

	            ClipboardContent content = new ClipboardContent();
	            content.putString(card.getTitulo()+card.getDescripcion());
	            System.out.println(content);
	            db.setContent(content);
	            
	            db.setDragView(card.snapshot(null, null));

	            event.consume();
	        });
			añadirTarjeta(card);
		}
		
		setOnDragAndDrop(tablero.getColPorHacer(), POR_HACER);
		setOnDragAndDrop(tablero.getColEnProgreso(), EN_PROGRESO);
		setOnDragAndDrop(tablero.getColCompletado(), COMPLETADO);
		setOnDragAndDrop(tablero.getColArchivado(), ARCHIVADO);
	}
	
	private void añadirTarjeta(KanbanTarjeta card) {
		String estado = card.getTarea().getEstado();
		switch(estado) {
		case POR_HACER -> tablero.getColPorHacer().añadirTarjeta(card);
		case EN_PROGRESO -> tablero.getColEnProgreso().añadirTarjeta(card);
		case COMPLETADO -> tablero.getColCompletado().añadirTarjeta(card);
		default -> tablero.getColArchivado().añadirTarjeta(card);
		}
	}
	
	private void setOnDragAndDrop(KanbanColumna col, String nuevoEstado) {
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
                cardOrigen.getTarea().setEstado(nuevoEstado);
                detallesDao.actualizarEstado(cardOrigen.getTarea());
                e.setDropCompleted(true);
            }
            e.consume();
        });
	}
	
	public KanbanView getView(List<ServicioDetalles> listaTareas) {
		this.setListaTareas(listaTareas);
		return this.tablero;
	}

	public List<ServicioDetalles> getListaTareas() {
		return listaTareas;
	}

	public void setListaTareas(List<ServicioDetalles> listaTareas) {
		this.listaTareas = listaTareas;
	}

}
