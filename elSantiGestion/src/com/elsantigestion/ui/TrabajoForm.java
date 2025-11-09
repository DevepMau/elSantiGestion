package com.elsantigestion.ui;

import com.elsantigestion.model.Trabajo;
import com.elsantigestion.utils.ValidadorCampos;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TrabajoForm extends Stage {
	
	private Trabajo trabajo;
	private Label titulo;
	private double xOffset;
	private double yOffset;
	private Button btnGuardar;
	private Button btnCerrar;
	private TextField txtNombre;
	private TextArea txtDetalle;
	private TextField txtPrecio;
	private Label lblPrecio;
	private Label lblUnidad;
	private Label msgNombre;
	private Label msgPrecio;
	private ComboBox<String> cmbUnidad;
	private CheckBox chkActivo;
	private HBox barraSuperior;
	private Region spacer;
	private GridPane grid;
	private Scene scene;
	private Label chkText;
	 
	public TrabajoForm(Trabajo trabajoExistente) {
		
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
		
		msgNombre = new Label("");
		msgPrecio = new Label("");
		
		lblPrecio = new Label("AR$");
		lblPrecio.setMinWidth(30);
		lblUnidad = new Label("X");
		lblUnidad.setMinWidth(10);
		
		txtNombre = new TextField();
		txtDetalle = new TextArea();
		txtPrecio = new TextField();
		chkActivo = new CheckBox();
		
		cmbUnidad = new ComboBox<>();
		cmbUnidad.getItems().addAll("Mes", "Semana", "Dia", "Hora", "M3", "M2", "Combo", "Paquete", "Unidad", "N/A");
		cmbUnidad.setValue("N/A");
		
		txtDetalle.setPrefRowCount(4);
		txtDetalle.setWrapText(true);
		
		txtNombre.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if(!newVal) {
				if(!ValidadorCampos.esNoVacio(txtNombre, msgNombre)) return;
			}
		});
		
		txtNombre.setPromptText("Ingrese nombre del trabajo...");
		txtDetalle.setPromptText("Ingrese detalle del trabajo...");
		txtPrecio.setPromptText("Precio...");
		
		txtPrecio.focusedProperty().addListener((obs, oldVal, newVal) -> {
		    if (!newVal) {
		    	if(!ValidadorCampos.esNoVacio(txtPrecio, msgPrecio)) return;
		    	if(!ValidadorCampos.esNumerico(txtPrecio, msgPrecio)) return;
		    }
		});
		
		if(trabajoExistente != null) {
			
			titulo = new Label("Modificar trabajo");
			txtNombre.setText(trabajoExistente.getNombre());
			txtDetalle.setText(trabajoExistente.getDetalle());
			txtPrecio.setText(String.valueOf(trabajoExistente.getPrecio()));
			cmbUnidad.setValue(trabajoExistente.getUnidad());
			chkActivo.setSelected(trabajoExistente.isActivo());
			
		} else {
			
			titulo = new Label("Agregar trabajo");
			chkActivo.setSelected(true);
			
		}
		
		titulo.getStyleClass().add("label-info");
		lblPrecio.getStyleClass().add("label-info");
		lblUnidad.getStyleClass().add("label-info");
		
		HBox boxPrecio = new HBox();
		boxPrecio.setAlignment(Pos.CENTER);
		boxPrecio.getChildren().addAll(lblPrecio, txtPrecio);
		boxPrecio.setSpacing(10);
		
		HBox boxUnidad = new HBox();
		boxUnidad.setAlignment(Pos.CENTER);
		boxUnidad.getChildren().addAll(lblUnidad, cmbUnidad);
		boxUnidad.setSpacing(10);
		
		HBox boxDatos = new HBox();
	    boxDatos.setAlignment(Pos.CENTER);
	    boxDatos.getChildren().addAll(boxPrecio, boxUnidad);
	    boxDatos.setSpacing(10);
		
		barraSuperior.setAlignment(Pos.TOP_RIGHT);
		barraSuperior.setSpacing(10);
		barraSuperior.getChildren().addAll(titulo, spacer, btnCerrar);
		barraSuperior.getStyleClass().add("topBar");
		barraSuperior.setOnMousePressed(event -> {
	        xOffset = event.getSceneX();
	        yOffset = event.getSceneY();
	    });
	    barraSuperior.setOnMouseDragged(event -> {
	        barraSuperior.getScene().getWindow().setX(event.getScreenX() - xOffset);
	        barraSuperior.getScene().getWindow().setY(event.getScreenY() - yOffset);
	    });
		
		HBox.setMargin(titulo, new Insets(15, 10, 0, 10));
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		HBox chkBar = new HBox();
		HBox.setMargin(chkActivo, new Insets(10, 0, 10, 0));
		
		chkText.getStyleClass().add("label-info");
	    chkBar.setAlignment(Pos.CENTER);
	    chkBar.getChildren().addAll(chkText ,chkActivo);
	    chkBar.setSpacing(20); 
		
	    msgNombre.getStyleClass().add("label-error");
	    msgPrecio.getStyleClass().add("label-error");
	    chkActivo.getStyleClass().add("check-box");
	    
		btnGuardar.getStyleClass().add("boton");
		btnGuardar.setOnAction(e -> {
			
			if(!ValidadorCampos.esNoVacio(txtNombre, msgNombre)) return;
			if(!ValidadorCampos.esNoVacio(txtPrecio, msgPrecio)) return;
	    	if(!ValidadorCampos.esNumerico(txtPrecio, msgPrecio)) return;
	    	
	    	if(txtDetalle.getText().equals("")) {
	    		txtDetalle.setText("-");
	    	}
			
			if(trabajoExistente == null) {
				Trabajo nuevo = new Trabajo(
						0,
						txtNombre.getText(),
						txtDetalle.getText(),
						Double.parseDouble(txtPrecio.getText()),
						cmbUnidad.getValue(),
						chkActivo.isSelected()
						);
				
				setTrabajo(nuevo);
			} else {
				trabajoExistente.setNombre(txtNombre.getText());
				trabajoExistente.setDetalle(txtDetalle.getText());
				trabajoExistente.setPrecio(Double.parseDouble(txtPrecio.getText()));
				trabajoExistente.setUnidad(cmbUnidad.getValue());
				trabajoExistente.setActivo(chkActivo.isSelected());
				
				setTrabajo(trabajoExistente);
			}
			
			close();
		});
		
		btnCerrar.setOnAction(e -> close());
		
		// LAYOUT //////////////////////////////
		
		grid.getStyleClass().add("formulario");
		grid.setPadding(new Insets(10));
		grid.add(barraSuperior, 0, 0);
		grid.addRow(1, msgNombre);
		grid.addRow(2, txtNombre);
		grid.addRow(3, msgPrecio);
		grid.addRow(4, boxDatos);
		grid.addRow(5, txtDetalle);
		grid.addRow(6, chkBar);
		grid.add(btnGuardar, 0, 7);
		GridPane.setMargin(msgNombre, new Insets(30, 20, 0, 20));
		GridPane.setMargin(txtNombre, new Insets(0, 20, 0, 20));
		GridPane.setMargin(msgPrecio, new Insets(0, 20, 0, 20));
		GridPane.setMargin(boxDatos, new Insets(0, 20, 0, 20));
		GridPane.setMargin(txtDetalle, new Insets(20, 20, 0, 20));
		GridPane.setMargin(chkBar, new Insets(20, 0, 20, 0));
		
		setScene(scene);
		initModality(Modality.APPLICATION_MODAL);
		
		scene.getStylesheets().add(
		        getClass().getResource("/com/elsantigestion/css/formulario.css").toExternalForm()
		    );
		
	}

	public Trabajo getTrabajo() {
		return trabajo;
	}

	public void setTrabajo(Trabajo trabajo) {
		this.trabajo = trabajo;
	}

}
