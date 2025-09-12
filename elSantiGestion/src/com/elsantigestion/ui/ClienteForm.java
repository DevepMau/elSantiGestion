package com.elsantigestion.ui;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.model.Cliente;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClienteForm extends Stage {

    private ClienteDAO dao;
    private Runnable onSaveCallback;

    public ClienteForm(ClienteDAO dao, Runnable onSaveCallback) {
        this.dao = dao;
        this.onSaveCallback = onSaveCallback;

        setTitle("Nuevo Cliente");

        // Formulario
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField();

        Label lblTelefono = new Label("Teléfono:");
        TextField txtTelefono = new TextField();

        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();

        CheckBox chkActivo = new CheckBox("Activo");
        chkActivo.setSelected(true);

        Button btnGuardar = new Button("Guardar");

        grid.add(lblNombre, 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(lblTelefono, 0, 1);
        grid.add(txtTelefono, 1, 1);
        grid.add(lblEmail, 0, 2);
        grid.add(txtEmail, 1, 2);
        grid.add(chkActivo, 1, 3);
        grid.add(btnGuardar, 1, 4);

        // Acción del botón
        btnGuardar.setOnAction(e -> {
            Cliente nuevo = new Cliente(
                0,
                txtNombre.getText(),
                txtTelefono.getText(),
                txtEmail.getText(),
                chkActivo.isSelected()
            );

            dao.agregarCliente(nuevo);

            if (onSaveCallback != null) {
                onSaveCallback.run();
            }

            close();
        });

        Scene scene = new Scene(grid, 400, 250);
        setScene(scene);

        initModality(Modality.APPLICATION_MODAL); // Bloquea hasta cerrar
    }
}
