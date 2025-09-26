package com.elsantigestion.ui;

import com.elsantigestion.dao.TrabajoDAO;
import com.elsantigestion.model.Trabajo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TrabajoForm extends Stage {
	
	@SuppressWarnings("unused")
	private TrabajoDAO dao;
	@SuppressWarnings("unused")
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
	private Label chkText;
	
	@SuppressWarnings("exports") 
	public TrabajoForm(TrabajoDAO dao, Runnable onSaveCallBack, Trabajo trabajoExistente) {
		
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
		chkText = new Label("¿El trabajo esta activo?");
		scene = new Scene(grid, 400, 500);
		
		txtNombre = new TextField();
		txtDetalle = new TextField();
		txtPrecio = new TextField();
		txtUnidad = new TextField();
		chkActivo = new CheckBox();
		
		txtNombre.setPromptText("Ingrese nombre del trabajo...");
		txtDetalle.setPromptText("Ingrese detalle del trabajo...");
		txtPrecio.setPromptText("Ingrese valor del trabajo...");
		txtUnidad.setPromptText("Ingrese unidad...");
		
		if(trabajoExistente != null) {
			
			titulo = new Label("Modificar trabajo");
			txtNombre.setText(trabajoExistente.getNombre());
			txtDetalle.setText(trabajoExistente.getDetalle());
			txtPrecio.setText(String.valueOf(trabajoExistente.getPrecio()));
			txtUnidad.setText(trabajoExistente.getUnidad());
			chkActivo.setSelected(trabajoExistente.isActivo());
			
		} else {
			
			titulo = new Label("Agregar trabajo");
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
		
		HBox.setMargin(titulo, new Insets(15, 10, 0, 10));
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		HBox chkBar = new HBox();
		HBox.setMargin(chkActivo, new Insets(10, 0, 10, 0));
		
	    chkBar.setAlignment(Pos.CENTER);
	    chkBar.getChildren().addAll(chkText ,chkActivo);
	    chkBar.setSpacing(20); 
		
	    chkActivo.getStyleClass().add("check-box");
	    
		btnGuardar.getStyleClass().add("boton");
		btnGuardar.setOnAction(e -> {
			if(trabajoExistente == null) {
				Trabajo nuevo = new Trabajo(
						0,
						txtNombre.getText(),
						txtDetalle.getText(),
						Double.parseDouble(txtPrecio.getText()),
						txtUnidad.getText(),
						chkActivo.isSelected()
						);
				dao.agregarTrabajo(nuevo);
			} else {
				trabajoExistente.setNombre(txtNombre.getText());
				trabajoExistente.setDetalle(txtDetalle.getText());
				trabajoExistente.setPrecio(Double.parseDouble(txtPrecio.getText()));
				trabajoExistente.setUnidad(txtUnidad.getText());
				trabajoExistente.setActivo(chkActivo.isSelected());
				
				dao.actualizarTrabajo(trabajoExistente);
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
		grid.addRow(5, chkBar);
		grid.add(btnGuardar, 0, 6);
		GridPane.setMargin(txtNombre, new Insets(45, 20, 0, 20));
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
