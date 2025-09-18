package com.elsantigestion.ui;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.model.Cliente;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


public class ClienteView extends VBox {
	
	private TableView<Cliente> tabla;
    private ClienteDAO dao;
    private final int iconoTamaño = 15;

    @SuppressWarnings("unchecked")
    public ClienteView() {
    	
    	dao = new ClienteDAO();
        // Barra de acciones
        Button btnNuevo = new Button("➕ Nuevo Cliente");
        HBox barraAcciones = new HBox(10, btnNuevo);
        barraAcciones.setAlignment(Pos.CENTER_RIGHT); // todo el contenido va a la derecha
        barraAcciones.getStyleClass().add("barra");  
    	
        // Tabla de clientes
        tabla = new TableView<>();
        tabla.getStyleClass().add("table-view");

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));

        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));

        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        
        TableColumn<Cliente, Boolean> colActivo = new TableColumn<>("Activo");
        colActivo.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isActivo()));

        TableColumn<Cliente, Void> colAcciones = new TableColumn<>("Acciones");
        Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>> cellFactory = param -> {
            return new TableCell<>() {
            	Image borrar = new Image(getClass().getResource("/iconos/borrar.png").toExternalForm());
            	Image editar = new Image(getClass().getResource("/iconos/editar.png").toExternalForm());
            	ImageView iconoBorrar = new ImageView(borrar);
            	ImageView iconoEditar = new ImageView(editar);
                private final Button btnEditar = new Button();
                private final Button btnEliminar = new Button();
                {
                	btnEditar.setOnAction(e -> {
                	    Cliente cliente = getTableView().getItems().get(getIndex());
                	    ClienteFormEditar form = new ClienteFormEditar(dao, ClienteView.this::refrescarTabla, cliente);
                	    form.showAndWait();
                	});
                	btnEditar.setGraphic(iconoEditar);
                	btnEditar.getStyleClass().add("table-boton");
                	iconoEditar.setFitWidth(iconoTamaño);
                	iconoEditar.setFitHeight(iconoTamaño);

                    btnEliminar.setOnAction(e -> {
                        Cliente cliente = getTableView().getItems().get(getIndex());
                        dao.eliminarCliente(cliente.getId());
                        refrescarTabla();
                    });
                	btnEliminar.setGraphic(iconoBorrar);
                	btnEliminar.getStyleClass().add("table-boton");
                	iconoBorrar.setFitWidth(iconoTamaño);
                	iconoBorrar.setFitHeight(iconoTamaño);
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox contenedor = new HBox(5, btnEditar, btnEliminar);
                        contenedor.setPadding(new Insets(2, 0, 2, 0));
                        setGraphic(contenedor);
                    }
                }
            };
        };

        colAcciones.setCellFactory(cellFactory);

        tabla.getColumns().addAll(colNombre, colTelefono, colEmail, colActivo);
        
        tabla.getColumns().add(colAcciones);

        // Cargar datos iniciales
        refrescarTabla();

        // Acción del botón
        btnNuevo.setOnAction(e -> {
            ClienteFormNuevo form = new ClienteFormNuevo(dao, this::refrescarTabla);
            form.showAndWait();
        });

        // Layout
        this.getChildren().addAll(barraAcciones, tabla);
        
    }
        
    private void refrescarTabla() {
    	
    	tabla.getItems().setAll(dao.obtenerClientes());
    	
    }
        
}
