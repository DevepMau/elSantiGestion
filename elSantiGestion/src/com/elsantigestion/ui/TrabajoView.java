package com.elsantigestion.ui;

import com.elsantigestion.model.Trabajo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TrabajoView extends VBox {
	
	private final int iconoTamaño = 50;
	private final String TITULO_INACTIVOS = "Tabla de trabajos archivados e inactivos";
	private final String TITULO_ACTIVOS = "Tabla de trabajos activos";
	
	private TableView<Trabajo> tabla;
	private Button btnNuevo;
	private Button btnModificar;
	private Button btnEliminar;
	private Button btnSwap;
	
	private Image agregar;
	private Image modificar;
	private Image eliminar;
	private Image swap;
	private Image recover;
	private ImageView iconoAgregar;
	private ImageView iconoModificar;
	private ImageView iconoEliminar;
	private ImageView iconoSwap;
	private ImageView iconoRecover;
	private Tooltip tooltipNuevo;
	private Tooltip tooltipModificar;
	private Tooltip tooltipEliminar;
	private Tooltip tooltipSwap;
	private Tooltip tooltipRecover;
	private HBox barraAcciones;
	private HBox footer;
	private Label lblFooter;
	
	public TrabajoView() {
		
		tabla = new TableView<>();
		btnNuevo = new Button();
		btnModificar = new Button();
		btnEliminar = new Button();
		btnSwap = new Button();
		
		agregar = new Image(getClass().getResource("/iconos/add.png").toExternalForm());
		modificar = new Image(getClass().getResource("/iconos/edit.png").toExternalForm());
		eliminar = new Image(getClass().getResource("/iconos/delete.png").toExternalForm());
		swap = new Image(getClass().getResource("/iconos/table-swap.png").toExternalForm());
		recover = new Image(getClass().getResource("/iconos/recover.png").toExternalForm());
		iconoAgregar = new ImageView(agregar);
		iconoModificar = new ImageView(modificar);
		iconoEliminar = new ImageView(eliminar);
		iconoSwap = new ImageView(swap);
		iconoRecover = new ImageView(recover);
		tooltipNuevo = new Tooltip("Agregar trabajo");
		tooltipModificar = new Tooltip("Modificar trabajo");
		tooltipEliminar = new Tooltip("Eliminar trabajo");
		tooltipSwap = new Tooltip("Cambiar tabla");
		tooltipRecover = new Tooltip("Recuperar trabajo");
		lblFooter = new Label(TITULO_ACTIVOS);
		barraAcciones = new HBox(2, btnSwap, btnEliminar, btnModificar, btnNuevo);
		footer = new HBox(lblFooter);
		
		iconoAgregar.setFitWidth(iconoTamaño);
		iconoAgregar.setFitHeight(iconoTamaño);
		iconoModificar.setFitWidth(iconoTamaño);
		iconoModificar.setFitHeight(iconoTamaño);
		iconoEliminar.setFitWidth(iconoTamaño);
		iconoEliminar.setFitHeight(iconoTamaño);
		iconoSwap.setFitWidth(iconoTamaño);
		iconoSwap.setFitHeight(iconoTamaño);
		iconoRecover.setFitHeight(iconoTamaño);
		iconoRecover.setFitWidth(iconoTamaño);
		
		btnNuevo.getStyleClass().add("barra-boton");
		btnModificar.getStyleClass().add("barra-boton");
		btnEliminar.getStyleClass().add("barra-boton");
		btnSwap.getStyleClass().add("barra-boton");
		
		btnNuevo.setGraphic(iconoAgregar);
		btnModificar.setGraphic(iconoModificar);
		btnEliminar.setGraphic(iconoEliminar);
		btnSwap.setGraphic(iconoSwap);
		
		btnNuevo.setTooltip(tooltipNuevo);
		btnModificar.setTooltip(tooltipModificar);
		btnEliminar.setTooltip(tooltipEliminar);
		btnSwap.setTooltip(tooltipSwap);
		
		barraAcciones.setAlignment(Pos.CENTER_RIGHT);
		barraAcciones.getStyleClass().add("barra");
		
		footer.setAlignment(Pos.CENTER_RIGHT);
		footer.getStyleClass().add("footer");
		
		TableColumn<Trabajo, String> colNombre = new TableColumn<>("Nombre");
		TableColumn<Trabajo, String> colDetalle = new TableColumn<>("Detalle");
		TableColumn<Trabajo, Number> colPrecio = new TableColumn<>("Precio");
		TableColumn<Trabajo, String> colUnidad = new TableColumn<>("Unidad");
		
		colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
		colDetalle.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDetalle()));
		colPrecio.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrecio()));
		colUnidad.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUnidad()));
		
		colNombre.getStyleClass().add("columna-texto");
		colDetalle.getStyleClass().add("columna-texto");
		colPrecio.getStyleClass().add("columna-numero");
		colUnidad.getStyleClass().add("columna-especial");
		
		tabla.getColumns().add(colNombre);
		tabla.getColumns().add(colDetalle);
		tabla.getColumns().add(colPrecio);
		tabla.getColumns().add(colUnidad);
    	tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	tabla.setPrefWidth(Double.MAX_VALUE);
    	VBox.setVgrow(tabla, Priority.ALWAYS);
		
		
		this.getChildren().addAll(barraAcciones, tabla, footer);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/tabla.css").toExternalForm()
    	    );
		
		colNombre.setMinWidth(120);
		colDetalle.setMinWidth(280);
		colPrecio.setMinWidth(80);
		colUnidad.setMinWidth(80);
		
	
	}

	public TableView<Trabajo> getTabla(){
    	return tabla;
    }
	
	public void alternarIcono(Boolean boo) {
		if(boo) {
			btnEliminar.setGraphic(iconoEliminar);
			btnEliminar.setTooltip(tooltipEliminar);
		}
		else {
			btnEliminar.setGraphic(iconoRecover);
			btnEliminar.setTooltip(tooltipRecover);
		}
	}

	public Button getBtnNuevo() {
		return btnNuevo;
	}

	public void setBtnNuevo(Button btnNuevo) {
		this.btnNuevo = btnNuevo;
	}

	public Button getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(Button btnModificar) {
		this.btnModificar = btnModificar;
	}

	public Button getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(Button btnEliminar) {
		this.btnEliminar = btnEliminar;
	}
	
	public Button getBtnSwap() {
		return btnSwap;
	}
	
	public void setBtnSwap(Button btnSwap) {
		this.btnSwap = btnSwap;
	}

	public String getTITULO_INACTIVOS() {
		return TITULO_INACTIVOS;
	}

	public String getTITULO_ACTIVOS() {
		return TITULO_ACTIVOS;
	}
	
	public void setTituloDeTabla(Boolean boo) {
		if(boo) {
			this.lblFooter.setText(TITULO_ACTIVOS);
		}
		else {
			this.lblFooter.setText(TITULO_INACTIVOS);
		}
	}

}
