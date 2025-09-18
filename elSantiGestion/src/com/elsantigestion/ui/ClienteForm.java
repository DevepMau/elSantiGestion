package com.elsantigestion.ui;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.model.Cliente;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ClienteForm extends Stage {
	
	private ClienteDAO dao;
    private Runnable onSaveCallback;
	
	public ClienteForm(ClienteDAO dao, Runnable onSaveCallback, Cliente clienteExistente) {
	    this.dao = dao;
	    this.onSaveCallback = onSaveCallback;

	    setTitle(clienteExistente == null ? "Nuevo Cliente" : "Editar Cliente");

	    // Campos
	    TextField txtNombre = new TextField();
	    TextField txtTelefono = new TextField();
	    TextField txtLocalidad = new TextField();
	    TextField txtDireccion = new TextField();
	    TextField txtEmail = new TextField();
	    CheckBox chkActivo = new CheckBox("Activo");

	    if (clienteExistente != null) {
	        txtNombre.setText(clienteExistente.getNombre());
	        txtTelefono.setText(clienteExistente.getTelefono());
	        txtLocalidad.setText(clienteExistente.getLocalidad());
	        txtDireccion.setText(clienteExistente.getDireccion());
	        txtEmail.setText(clienteExistente.getEmail());
	        chkActivo.setSelected(clienteExistente.isActivo());
	    } else {
	        chkActivo.setSelected(true);
	    }

	    Button btnGuardar = new Button("Guardar");

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

	    // Layout igual que antes
	    GridPane grid = new GridPane();
	    grid.setPadding(new Insets(10));
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.addRow(0, new Label("Nombre:"), txtNombre);
	    grid.addRow(1, new Label("Teléfono:"), txtTelefono);
	    grid.addRow(2, new Label("Email:"), txtEmail);
	    grid.addRow(3, new Label("Direccion:"), txtDireccion);
	    grid.addRow(4, new Label("Localidad:"), txtLocalidad);
	    grid.addRow(5, chkActivo);
	    grid.add(btnGuardar, 1, 5);

	    Scene scene = new Scene(grid, 400, 250);
	    setScene(scene);
	    initModality(Modality.APPLICATION_MODAL);
	}


}
