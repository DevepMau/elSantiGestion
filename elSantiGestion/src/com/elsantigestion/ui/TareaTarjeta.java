package com.elsantigestion.ui;

import com.elsantigestion.model.Tarea;

import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TareaTarjeta extends Pane {
	
	private Tarea tarea;
	private Label lblTitulo;
	private Label lblDescripcion;
	private VBox contenedor;
	
	public TareaTarjeta(Tarea tarea) {
		this.setTarea(tarea);
		this.lblTitulo = new Label(this.tarea.getTitulo());
		this.lblDescripcion = new Label(this.tarea.getDescripcion());
		this.contenedor = new VBox(5, lblTitulo, lblDescripcion);
		
		this.getStyleClass().add("mi-pane");
		contenedor.getStyleClass().add("tarjeta");
		
		this.setOnDragDetected(event -> {
            Dragboard db = this.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString(lblTitulo.getText()+lblDescripcion.getText());
            db.setContent(content);
            
            db.setDragView(snapshot(null, null));

            event.consume();
        });
		
		this.getChildren().addAll(contenedor);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/kanban.css").toExternalForm()
    	   );
		
	}
	
	/////////////////////////////////////////////////////

	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}
	
	

}
