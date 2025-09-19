package com.elsantigestion.ui;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.model.Cliente;

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


public class ClienteForm extends Stage {
	
	private ClienteDAO dao;
    private Runnable onSaveCallback;
    private Label titulo;
    private double xOffset = 0;
    private double yOffset = 0;
	
	public ClienteForm(ClienteDAO dao, Runnable onSaveCallback, Cliente clienteExistente) {
	    this.dao = dao;
	    this.onSaveCallback = onSaveCallback;
	    this.initStyle(StageStyle.UNDECORATED);

	    // Campos
	    TextField txtNombre = new TextField();
	    txtNombre.setPromptText("Ingrese nombre...");
	    TextField txtTelefono = new TextField();
	    txtTelefono.setPromptText("Ingrese telefono...");
	    TextField txtLocalidad = new TextField();
	    txtLocalidad.setPromptText("Ingrese localidad...");
	    TextField txtDireccion = new TextField();
	    txtDireccion.setPromptText("Ingrese direccion...");
	    TextField txtEmail = new TextField();
	    txtEmail.setPromptText("Ingrese Email...");
	    CheckBox chkActivo = new CheckBox();

	    if (clienteExistente != null) {
	    	titulo = new Label("Modificar cliente");
	        txtNombre.setText(clienteExistente.getNombre());
	        txtTelefono.setText(clienteExistente.getTelefono());
	        txtLocalidad.setText(clienteExistente.getLocalidad());
	        txtDireccion.setText(clienteExistente.getDireccion());
	        txtEmail.setText(clienteExistente.getEmail());
	        chkActivo.setSelected(clienteExistente.isActivo());
	    } else {
	    	titulo = new Label("Agregar cliente");
	        chkActivo.setSelected(true);
	    }

	    Button btnGuardar = new Button("Guardar");
	    btnGuardar.getStyleClass().add("boton");
	    btnGuardar.setOnAction(e -> {
	        if (clienteExistente == null) {
	            // Nuevo cliente
	            Cliente nuevo = new Cliente(
	                0,
	                txtNombre.getText(),
	                txtTelefono.getText(),
	                txtLocalidad.getText(),
	                txtDireccion.getText(),
	                txtEmail.getText(),
	                chkActivo.isSelected()
	            );
	            dao.agregarCliente(nuevo);
	        } else {
	            // Editar cliente
	            clienteExistente.setNombre(txtNombre.getText());
	            clienteExistente.setTelefono(txtTelefono.getText());
	            clienteExistente.setLocalidad(txtLocalidad.getText());
	            clienteExistente.setDireccion(txtDireccion.getText());
	            clienteExistente.setEmail(txtEmail.getText());
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
	    
	    
	    
	    HBox chkBar = new HBox();
	    chkBar.setAlignment(Pos.CENTER);
	    Label chkText = new Label("¿El Cliente esta activo?");
	    chkBar.getChildren().addAll(chkText ,chkActivo);
	    HBox.setMargin(chkActivo, new Insets(10, 0, 10, 0));
	    chkBar.setSpacing(20);
	    chkActivo.getStyleClass().add("check-box");

	    // Layout igual que antes
	    GridPane grid = new GridPane(); 
	    grid.setPadding(new Insets(10));
	    grid.add(topBar, 0, 0);
	    grid.addRow(1, txtNombre);
	    grid.addRow(2, txtTelefono);
	    grid.addRow(3, txtEmail);
	    grid.addRow(4, txtDireccion);
	    grid.addRow(5, txtLocalidad);
	    grid.addRow(6, chkBar);
	    grid.add(btnGuardar, 0, 7);
	    GridPane.setMargin(txtNombre, new Insets(40, 20, 0, 20));
	    GridPane.setMargin(txtTelefono, new Insets(0, 20, 0, 20));
	    GridPane.setMargin(txtEmail, new Insets(0, 20, 0, 20));
	    GridPane.setMargin(txtDireccion, new Insets(0, 20, 0, 20));
	    GridPane.setMargin(txtLocalidad, new Insets(0, 20, 0, 20));

	    Scene scene = new Scene(grid, 400, 550);
	    setScene(scene);
	    initModality(Modality.APPLICATION_MODAL);
	    
	    grid.getStyleClass().add("formulario"); // aplica la clase CSS

	    scene.getStylesheets().add(
	        getClass().getResource("/com/elsantigestion/css/formulario.css").toExternalForm()
	    );
	    
	}


}
