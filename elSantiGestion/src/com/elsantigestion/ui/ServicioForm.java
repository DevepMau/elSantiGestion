package com.elsantigestion.ui;

import com.elsantigestion.dao.ServicioDAO;
import com.elsantigestion.model.Servicio;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ServicioForm extends Stage {
	
	private ServicioDAO dao;
	private Runnable onSaveCallBack;
	private Label titulo;
	private double xOffset;
	private double yOffset;
	private Button btnGuardar;
	private Button btnCerrar;
	private TextField txtNombre;
	private TextField txtDetalle;
	private TextField txtPrecio;
	private TextField txtUnidad;
	private CheckBox chkActivo;
	private HBox barraSuperior;
	private Region spacer;
	private GridPane grid;
	private Scene scene;
	
	public ServicioForm(ServicioDAO dao, Runnable onSaveCallBack, Servicio servicioExistente) {
		
		this.dao = dao;
		this.onSaveCallBack = onSaveCallBack;
		this.xOffset = 0;
		this.yOffset = 0;
		this.initStyle(StageStyle.UNDECORATED);

		grid = new GridPane();
		btnGuardar = new Button("Guardar");
		btnCerrar = new Button("X");
		barraSuperior = new HBox();
		spacer = new Region();
		scene = new Scene(grid, 400, 550);
		
		txtNombre = new TextField();
		txtDetalle = new TextField();
		txtPrecio = new TextField();
		txtUnidad = new TextField();
		chkActivo = new CheckBox();
		
		txtNombre.setPromptText("Ingrese nombre del servicio...");
		txtDetalle.setPromptText("Ingrese detalle del servicio...");
		txtPrecio.setPromptText("Ingrese valor del servicio...");
		txtUnidad.setPromptText("Ingrese unidad...");
		
		if(servicioExistente != null) {
			
			titulo = new Label("Modificar servicio");
			txtNombre.setText(servicioExistente.getNombre());
			txtDetalle.setText(servicioExistente.getDetalle());
			txtPrecio.setText(String.valueOf(servicioExistente.getPrecio()));
			txtUnidad.setText(servicioExistente.getUnidad());
			chkActivo.setSelected(servicioExistente.isActivo());
			
		} else {
			
			titulo = new Label("Agregar servicio");
			chkActivo.setSelected(true);
			
		}
		
		barraSuperior.setAlignment(Pos.TOP_RIGHT);
		barraSuperior.setSpacing(10);
		barraSuperior.getChildren().addAll(titulo, spacer, btnCerrar);
		barraSuperior.getStyleClass().add("topBar");
		barraSuperior.setOnMousePressed(event -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});
		barraSuperior.setOnMouseDragged(event -> {
			barraSuperior.getScene().getWindow().setX(event.getSceneX() - xOffset);
			barraSuperior.getScene().getWindow().setY(event.getSceneY() - yOffset);
		});	
		
		btnGuardar.setOnAction(e -> {
			if(servicioExistente == null) {
				Servicio nuevo = new Servicio(
						0,
						txtNombre.getText(),
						txtDetalle.getText(),
						Double.parseDouble(txtPrecio.getText()),
						txtUnidad.getText(),
						chkActivo.isSelected()
						);
				dao.agregarServicio(nuevo);
			} else {
				servicioExistente.setNombre(txtNombre.getText());
				servicioExistente.setDetalle(txtDetalle.getText());
				servicioExistente.setPrecio(Double.parseDouble(txtPrecio.getText()));
				servicioExistente.setUnidad(txtUnidad.getText());
				servicioExistente.setActivo(chkActivo.isSelected());
				
				dao.actualizarServicio(servicioExistente);
			}
			
			if(onSaveCallBack != null) {
				onSaveCallBack.run();
			}
			close();
		});
		
		btnCerrar.setOnAction(e -> close());
		
		// LAYOUT //////////////////////////////
		
		grid.getStyleClass().add("formulario");
		grid.setPadding(new Insets(10));
		grid.add(barraSuperior, 0, 0);
		grid.addRow(1, txtNombre);
		grid.addRow(2, txtDetalle);
		grid.addRow(3, txtPrecio);
		grid.addRow(4, txtUnidad);
		grid.add(btnGuardar, 0, 5);
		GridPane.setMargin(txtNombre, new Insets(40, 20, 0, 20));
		GridPane.setMargin(txtDetalle, new Insets(0, 20, 0, 20));
		GridPane.setMargin(txtPrecio, new Insets(0, 20, 0, 20));
		GridPane.setMargin(txtUnidad, new Insets(0, 20, 0, 20));
		
		setScene(scene);
		initModality(Modality.APPLICATION_MODAL);
		
		scene.getStylesheets().add(
		        getClass().getResource("/com/elsantigestion/css/formulario.css").toExternalForm()
		    );
		
	}

}
