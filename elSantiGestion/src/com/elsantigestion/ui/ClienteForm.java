package com.elsantigestion.ui;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.model.Cliente;
import com.elsantigestion.utils.ValidadorCampos;

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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ClienteForm extends Stage {
	
	@SuppressWarnings("unused")
	private ClienteDAO dao;
	@SuppressWarnings("unused")
    private Runnable onSaveCallback;
    private Label titulo;
    private double xOffset = 0;
    private double yOffset = 0;
	
    @SuppressWarnings("exports")
	public ClienteForm(ClienteDAO dao, Runnable onSaveCallback, Cliente clienteExistente) {
	    this.dao = dao;
	    this.onSaveCallback = onSaveCallback;
	    this.initStyle(StageStyle.UNDECORATED);

	    TextField txtNombre = new TextField();
	    TextField txtTelefono = new TextField();
	    TextField txtEmail = new TextField();
	    CheckBox chkBarrioPrivado = new CheckBox();
	    TextField txtBarrioNombre = new TextField();
	    TextField txtBarrioLote = new TextField();
	    TextField txtLocalidad = new TextField();
	    TextField txtDireccion = new TextField();
	    CheckBox chkActivo = new CheckBox();
	    
	    txtNombre.setPromptText("Ingrese nombre...");	    
	    txtTelefono.setPromptText("Ingrese telefono...");
	    txtLocalidad.setPromptText("Ingrese localidad...");
	    txtDireccion.setPromptText("Ingrese direccion...");
	    txtEmail.setPromptText("Ingrese Email...");
	    txtBarrioNombre.setPromptText("Ingrese barrio...");
	    txtBarrioLote.setPromptText("Lote...");
	    
    	txtBarrioLote.setMinWidth(70);
    	txtBarrioLote.setMaxWidth(70);
	    
	    Label msgNombre = new Label("");
	    Label msgTelefono = new Label("");
	    Label msgEmail = new Label("");
	    Label msgLocalidad = new Label("");
	    
	    msgNombre.getStyleClass().add("label-error");
	    msgTelefono.getStyleClass().add("label-error");
	    msgEmail.getStyleClass().add("label-error");
	    msgLocalidad.getStyleClass().add("label-error");

	    if (clienteExistente != null) {
	    	titulo = new Label("Modificar cliente");
	    	txtNombre.setText(clienteExistente.getNombre());
	        txtTelefono.setText(clienteExistente.getTelefono());
	        txtEmail.setText(clienteExistente.getEmail());
	        chkBarrioPrivado.setSelected(clienteExistente.isBarrioPrivado());
	        txtBarrioNombre.setText(clienteExistente.getBarrioNombre());
	        txtBarrioLote.setText(String.valueOf(clienteExistente.getBarrioLote()));
	        txtLocalidad.setText(clienteExistente.getLocalidad());
	        txtDireccion.setText(clienteExistente.getDireccion());
	        chkActivo.setSelected(clienteExistente.isActivo());
	    } else {
	    	titulo = new Label("Agregar cliente");
	    	chkBarrioPrivado.setSelected(false);
	        chkActivo.setSelected(true);
	    }
	    
	    txtBarrioNombre.setDisable(!chkBarrioPrivado.isSelected());
    	txtBarrioLote.setDisable(!chkBarrioPrivado.isSelected());
    	txtDireccion.setDisable(chkBarrioPrivado.isSelected());
	    
	    txtNombre.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if(!newVal) {
				if(!ValidadorCampos.esNoVacio(txtNombre, msgNombre)) return;
			}
		});
	    txtTelefono.focusedProperty().addListener((obs, oldVal, newVal) -> {
	    	if(!newVal) {
	    		if(!ValidadorCampos.esNoVacio(txtTelefono, msgTelefono)) return;
	    		if(!ValidadorCampos.esNumerico(txtTelefono, msgTelefono)) return;
	    		if(!ValidadorCampos.longitudEntre(txtTelefono, 8, 16, msgTelefono)) return;
	    	}
	    });
	    txtLocalidad.focusedProperty().addListener((obs, oldVal, newVal) -> {
	    	if(!newVal) {
	    		if(!ValidadorCampos.esNoVacio(txtLocalidad, msgLocalidad)) return;
	    	}
	    });
	    
	    chkBarrioPrivado.setOnAction(e -> {
	    	boolean boo = chkBarrioPrivado.isSelected();
	    	
	    	txtBarrioNombre.setDisable(!boo);
	    	txtBarrioLote.setDisable(!boo);
	    	txtDireccion.setDisable(boo);
	    	
	    	if(boo) {
		    	txtDireccion.setText("");
		    }
		    else {
		    	txtBarrioNombre.setText("");
		    	txtBarrioLote.setText("");
		    }
	    });

	    Button btnGuardar = new Button("Guardar");
	    btnGuardar.getStyleClass().add("boton");
	    btnGuardar.setOnAction(e -> {
	    	
	    	if(!ValidadorCampos.esNoVacio(txtNombre, msgNombre)) return;
	    	if(!ValidadorCampos.esNoVacio(txtTelefono, msgTelefono)) return;
    		if(!ValidadorCampos.esNumerico(txtTelefono, msgTelefono)) return;
    		if(!ValidadorCampos.longitudEntre(txtTelefono, 8, 16, msgTelefono)) return;
    		if(!ValidadorCampos.esNoVacio(txtLocalidad, msgLocalidad)) return;
    		
    		if(txtEmail.getText().equals("")) {
    			txtEmail.setText("-");
    		}
    		
    		if(txtBarrioNombre.getText().equals("")) {
    			txtBarrioNombre.setText("-");
    		}
    		
    		if(txtDireccion.getText().equals("")) {
    			txtDireccion.setText("-");
    		}
    		
    		if(txtBarrioLote.getText().equals("")) {
    			txtBarrioLote.setText("0");
    		}
	    	
	        if (clienteExistente == null) {
	            // Nuevo cliente
	            Cliente nuevo = new Cliente(
	                0,
	                txtNombre.getText(),
	                txtTelefono.getText(),
	                txtEmail.getText(),
	                chkBarrioPrivado.isSelected(),
	                txtBarrioNombre.getText(),
	                Integer.parseInt(txtBarrioLote.getText()),
	                txtLocalidad.getText(),
	                txtDireccion.getText(),
	                chkActivo.isSelected()
	            );
	            dao.agregarCliente(nuevo);
	        } else {
	            // Editar cliente
	            clienteExistente.setNombre(txtNombre.getText());
	            clienteExistente.setTelefono(txtTelefono.getText());
	            clienteExistente.setEmail(txtEmail.getText());
	            clienteExistente.setBarrioPrivado(chkBarrioPrivado.isSelected());
	            clienteExistente.setBarrioNombre(txtBarrioNombre.getText());
	            clienteExistente.setBarrioLote(Integer.parseInt(txtBarrioLote.getText()));
	            clienteExistente.setLocalidad(txtLocalidad.getText());
	            clienteExistente.setDireccion(txtDireccion.getText());
	            clienteExistente.setActivo(chkActivo.isSelected());
	            dao.actualizarCliente(clienteExistente);
	        }

	        if (onSaveCallback != null) {
	            onSaveCallback.run();
	        }
	        close();
	    });
	    
	    Button btnCerrar = new Button("X");
	    
	    btnCerrar.setOnAction(e -> close());
	    
	    HBox topBar = new HBox();
	    topBar.setAlignment(Pos.TOP_RIGHT);
	    topBar.setSpacing(10);
	    Region spacer = new Region();
	    titulo.getStyleClass().add("label-info");
	    HBox.setMargin(titulo, new Insets(15, 10, 0, 10));
	    HBox.setHgrow(spacer, Priority.ALWAYS);

	    topBar.getChildren().addAll(titulo, spacer, btnCerrar);
	    topBar.getStyleClass().add("topBar");
	    
	    topBar.setOnMousePressed(event -> {
	        // Guardamos la posición del mouse cuando se hace clic
	        xOffset = event.getSceneX();
	        yOffset = event.getSceneY();
	    });
	    
	    topBar.setOnMouseDragged(event -> {
	        // Movemos la ventana según la posición del mouse
	        topBar.getScene().getWindow().setX(event.getScreenX() - xOffset);
	        topBar.getScene().getWindow().setY(event.getScreenY() - yOffset);
	    });
	    
	    
	    
	    HBox boxActivo = new HBox();
	    boxActivo.setAlignment(Pos.CENTER);
	    Label lblActivo = new Label("¿El Cliente esta activo?");
	    lblActivo.getStyleClass().add("label-info");
	    boxActivo.getChildren().addAll(lblActivo ,chkActivo);
	    HBox.setMargin(chkActivo, new Insets(10, 0, 10, 0));
	    boxActivo.setSpacing(20);
	    chkActivo.getStyleClass().add("check-box");
	    
	    HBox boxContactos = new HBox();
	    boxContactos.setAlignment(Pos.CENTER);
	    boxContactos.getChildren().addAll(txtTelefono, txtEmail);
	    boxContactos.setSpacing(10);
	    
	    HBox boxBarrioChk = new HBox();
	    boxBarrioChk.setAlignment(Pos.CENTER);
	    Label lblBarrioPrivado = new Label("¿Vive en un barrio privado?");
	    lblBarrioPrivado.getStyleClass().add("label-info");
	    boxBarrioChk.getChildren().addAll(lblBarrioPrivado, chkBarrioPrivado);
	    HBox.setMargin(chkBarrioPrivado, new Insets(10, 0, 10, 0));
	    boxBarrioChk.setSpacing(20);
	    chkBarrioPrivado.getStyleClass().add("check-box");
	    
	    HBox boxBarrioInfo = new HBox();
	    boxBarrioInfo.setAlignment(Pos.CENTER);
	    boxBarrioInfo.getChildren().addAll(txtBarrioNombre, txtBarrioLote);
	    boxBarrioInfo.setSpacing(10);
	    
	    VBox boxBarrioPrivado = new VBox();
	    boxBarrioPrivado.setAlignment(Pos.CENTER);
	    boxBarrioPrivado.getChildren().addAll(boxBarrioChk, boxBarrioInfo);
	    boxBarrioPrivado.setSpacing(10);
	    boxBarrioPrivado.getStyleClass().add("box-personalizada");

	    GridPane grid = new GridPane(); 
	    grid.setPadding(new Insets(10));
	    grid.add(topBar, 0, 0);
	    grid.addRow(1, msgNombre); //aviso de error
	    grid.addRow(2, txtNombre);
	    grid.addRow(3, msgTelefono); //aviso de error
	    grid.addRow(4, boxContactos);
	    grid.addRow(5, msgLocalidad); //aviso de error
	    grid.addRow(6, txtLocalidad);
	    grid.addRow(7, boxBarrioPrivado);
	    grid.addRow(8, txtDireccion);
	    grid.addRow(9, boxActivo);
	    grid.add(btnGuardar, 0, 10);
	    
	    GridPane.setMargin(msgNombre, new Insets(20, 20, 0, 20));
	    GridPane.setMargin(txtNombre, new Insets(0, 20, 0, 20));
	    GridPane.setMargin(msgTelefono, new Insets(0, 20, 0, 20));
	    GridPane.setMargin(boxContactos, new Insets(0, 20, 0, 20));
	    GridPane.setMargin(msgEmail, new Insets(0, 20, 0, 20));
	    GridPane.setMargin(txtEmail, new Insets(0, 20, 0, 20));
	    GridPane.setMargin(msgLocalidad, new Insets(0, 20, 0, 20));
	    GridPane.setMargin(txtLocalidad, new Insets(0, 20, 0, 20));
	    GridPane.setMargin(boxBarrioPrivado, new Insets(20, 20, 0, 20));
	    GridPane.setMargin(txtDireccion, new Insets(20, 20, 0, 20));
	    GridPane.setMargin(boxActivo, new Insets(20, 0, 20, 0));

	    Scene scene = new Scene(grid, 400, 620);
	    setScene(scene);
	    initModality(Modality.APPLICATION_MODAL);
	    
	    grid.getStyleClass().add("formulario"); // aplica la clase CSS

	    scene.getStylesheets().add(
	        getClass().getResource("/com/elsantigestion/css/formulario.css").toExternalForm()
	    );
	    
	}


}
