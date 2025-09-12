package com.elsantigestion.ui;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.model.Cliente;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ClienteView extends VBox {
	
	private TableView<Cliente> tabla;
    private ClienteDAO dao;

    @SuppressWarnings("unchecked")
    public ClienteView() {
    	
    	dao = new ClienteDAO();
        
        // Barra de acciones
        Button btnNuevo = new Button("➕ Nuevo Cliente");
        HBox barraAcciones = new HBox(10, btnNuevo);
    	
        // Tabla de clientes
        tabla = new TableView<>();

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));

        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));

        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        
        TableColumn<Cliente, Boolean> colActivo = new TableColumn<>("Activo");
        colActivo.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isActivo()));

        tabla.getColumns().addAll(colNombre, colTelefono, colEmail, colActivo);

        // Cargar datos iniciales
        refrescarTabla();

        // Acción del botón
        btnNuevo.setOnAction(e -> {
            ClienteForm form = new ClienteForm(dao, this::refrescarTabla);
            form.showAndWait();
        });

        // Layout
        this.getChildren().addAll(barraAcciones, tabla);
        
    }
        
    private void refrescarTabla() {
    	
    	tabla.getItems().setAll(dao.obtenerClientes());
    	
    }
        
}
