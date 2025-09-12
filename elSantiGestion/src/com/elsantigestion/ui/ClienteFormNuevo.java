package com.elsantigestion.ui;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.model.Cliente;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClienteFormNuevo extends Stage {

    public ClienteFormNuevo(ClienteDAO dao, Runnable onSaveCallback) {
        setTitle("Nuevo Cliente");

        // Campos
        TextField txtNombre = new TextField();
        TextField txtTelefono = new TextField();
        TextField txtEmail = new TextField();
        CheckBox chkActivo = new CheckBox("Activo");
        chkActivo.setSelected(true);

        Button btnGuardar = new Button("Guardar");

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

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Nombre:"), txtNombre);
        grid.addRow(1, new Label("Teléfono:"), txtTelefono);
        grid.addRow(2, new Label("Email:"), txtEmail);
        grid.addRow(3, chkActivo);
        grid.add(btnGuardar, 1, 4);

        Scene scene = new Scene(grid, 400, 250);
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
    }
}
