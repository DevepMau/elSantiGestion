package com.elsantigestion.ui;

import com.elsantigestion.model.Trabajo;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
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
	
	private TableView<Trabajo> tabla;
	private Button btnNuevo;
	private Button btnModificar;
	private Button btnEliminar;
	
	private Image agregar;
	private Image modificar;
	private Image eliminar;
	private ImageView iconoAgregar;
	private ImageView iconoModificar;
	private ImageView iconoEliminar;
	private Tooltip tooltipNuevo;
	private Tooltip tooltipModificar;
	private Tooltip tooltipEliminar;
	private HBox barraAcciones;
	
	public TrabajoView() {
		
		tabla = new TableView<>();
		btnNuevo = new Button();
		btnModificar = new Button();
		btnEliminar = new Button();
		
		agregar = new Image(getClass().getResource("/iconos/add.png").toExternalForm());
		modificar = new Image(getClass().getResource("/iconos/edit.png").toExternalForm());
		eliminar = new Image(getClass().getResource("/iconos/delete.png").toExternalForm());
		iconoAgregar = new ImageView(agregar);
		iconoModificar = new ImageView(modificar);
		iconoEliminar = new ImageView(eliminar);
		tooltipNuevo = new Tooltip("Agregar trabajo");
		tooltipModificar = new Tooltip("Modificar trabajo");
		tooltipEliminar = new Tooltip("Eliminar trabajo");
		barraAcciones = new HBox(2, btnEliminar, btnModificar, btnNuevo);
		
		iconoAgregar.setFitWidth(iconoTamaño);
		iconoAgregar.setFitHeight(iconoTamaño);
		iconoModificar.setFitWidth(iconoTamaño);
		iconoModificar.setFitHeight(iconoTamaño);
		iconoEliminar.setFitWidth(iconoTamaño);
		iconoEliminar.setFitHeight(iconoTamaño);
		
		btnNuevo.getStyleClass().add("barra-boton");
		btnModificar.getStyleClass().add("barra-boton");
		btnEliminar.getStyleClass().add("barra-boton");
		
		btnNuevo.setGraphic(iconoAgregar);
		btnModificar.setGraphic(iconoModificar);
		btnEliminar.setGraphic(iconoEliminar);
		
		btnNuevo.setTooltip(tooltipNuevo);
		btnModificar.setTooltip(tooltipModificar);
		btnEliminar.setTooltip(tooltipEliminar);
		
		barraAcciones.setAlignment(Pos.CENTER_RIGHT);
		barraAcciones.getStyleClass().add("barra");
		
		TableColumn<Trabajo, String> colNombre = new TableColumn<>("Nombre");
		TableColumn<Trabajo, String> colDetalle = new TableColumn<>("Detalle");
		TableColumn<Trabajo, Number> colPrecio = new TableColumn<>("Precio");
		TableColumn<Trabajo, String> colUnidad = new TableColumn<>("Unidad");
		TableColumn<Trabajo, Boolean> colActivo = new TableColumn<>("Activo");
		
		colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
		colDetalle.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDetalle()));
		colPrecio.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrecio()));
		colUnidad.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUnidad()));
		colActivo.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().isActivo()));
		
		colNombre.getStyleClass().add("columna-texto");
		colDetalle.getStyleClass().add("columna-texto");
		colPrecio.getStyleClass().add("columna-numero");
		colUnidad.getStyleClass().add("columna-especial");
		colActivo.getStyleClass().add("columna-especial");
		
		colActivo.setCellFactory(col -> new TableCell<Trabajo, Boolean>() {
			protected void updateItem(Boolean item, boolean empty) {
				super.updateItem(item, empty);
				if(empty || item == null) {
					setText(null);
					setStyle("");	
				} 
				else {
					if(item) {	
						setText("Activo");
						setStyle("-fx-text-fill: green; -fx-padding: 0 0 -1 0;");	
					} else {
						setText("Inactivo");
						setStyle("-fx-text-fill: red; -fx-padding: 0 0 -1 0;");
					}
				}
				setAlignment(Pos.CENTER);
			}
		});
		
		tabla.getColumns().add(colNombre);
		tabla.getColumns().add(colDetalle);
		tabla.getColumns().add(colPrecio);
		tabla.getColumns().add(colUnidad);
		tabla.getColumns().add(colActivo);
    	tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	tabla.setPrefWidth(Double.MAX_VALUE);
    	VBox.setVgrow(tabla, Priority.ALWAYS);
		
		
		this.getChildren().addAll(barraAcciones, tabla);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/tabla.css").toExternalForm()
    	    );
		
		colNombre.setMinWidth(120);
		colDetalle.setMinWidth(280);
		colPrecio.setMinWidth(80);
		colUnidad.setMinWidth(80);
		colActivo.setMinWidth(80);
		
	
	}

	public TableView<Trabajo> getTabla() {
		return tabla;
	}

	public void setTabla(TableView<Trabajo> tabla) {
		this.tabla = tabla;
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

}
