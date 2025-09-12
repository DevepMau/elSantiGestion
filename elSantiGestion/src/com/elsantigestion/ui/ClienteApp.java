package com.elsantigestion.ui;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.model.Cliente;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClienteApp extends Application {

    @SuppressWarnings("unchecked")
	@Override
    public void start(Stage stage) {
        // Tabla de clientes
        TableView<Cliente> tabla = new TableView<>();

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));

        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));

        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        
        TableColumn<Cliente, Boolean> colActivo = new TableColumn<>("Activo");
        colActivo.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isActivo()));

        tabla.getColumns().addAll(colNombre, colTelefono, colEmail, colActivo);

        // Cargar datos desde la base
        ClienteDAO dao = new ClienteDAO();
        tabla.getItems().addAll(dao.obtenerClientes());

        // Layout
        VBox root = new VBox(tabla);
        Scene scene = new Scene(root, 800, 400);

        stage.setTitle("Gestión de Clientes");
        stage.setScene(scene);
        stage.show();
    }
}