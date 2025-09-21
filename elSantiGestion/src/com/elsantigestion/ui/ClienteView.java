package com.elsantigestion.ui;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.model.Cliente;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


public class ClienteView extends VBox {
	
	private TableView<Cliente> tabla;
    private ClienteDAO dao;
    private final int iconoTamaño = 20;

    @SuppressWarnings("unchecked")
    public ClienteView() {
    	
    	dao = new ClienteDAO();
        // Barra de acciones
    	Image añadir = new Image(getClass().getResource("/iconos/anadir.png").toExternalForm());
    	ImageView iconoAñadir = new ImageView(añadir);
        Button btnNuevo = new Button();
        btnNuevo.setGraphic(iconoAñadir);
        btnNuevo.getStyleClass().add("barra-boton");
        iconoAñadir.setFitWidth(50);
    	iconoAñadir.setFitHeight(50);
        HBox barraAcciones = new HBox(10, btnNuevo);
        barraAcciones.setAlignment(Pos.CENTER_RIGHT); // todo el contenido va a la derecha
        barraAcciones.getStyleClass().add("barra");  
    	
        // Tabla de clientes
        tabla = new TableView<>();
        //tabla.getStyleClass().add("table-view");
        tabla.setStyle("-fx-background-color: transparent;");

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colNombre.setId("col-nombre");
        colNombre.getStyleClass().add("columnaPersonalizada");

        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));
        colTelefono.getStyleClass().add("columnaPersonalizada");
        
        TableColumn<Cliente, String> colLocalidad = new TableColumn<>("Localidad");
        colLocalidad.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLocalidad()));
        colLocalidad.getStyleClass().add("columnaPersonalizada");
        
        TableColumn<Cliente, String> colDireccion = new TableColumn<>("Direccion");
        colDireccion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDireccion()));
        colDireccion.getStyleClass().add("columnaPersonalizada");
        
        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        colEmail.getStyleClass().add("columnaPersonalizada");
        
        TableColumn<Cliente, Boolean> colActivo = new TableColumn<>("Activo");
        colActivo.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().isActivo()));
        colActivo.getStyleClass().add("columnaPersonalizada");

        // CellFactory para mostrar "Activo"/"Inactivo" con fondo verde/rojo
        colActivo.setCellFactory(col -> new TableCell<Cliente, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    if (item) {
                        setText("Activo");
                        setStyle("-fx-text-fill: green; -fx-padding: 0 0 -1 0;");
                    } else {
                        setText("Inactivo");
                        setStyle("-fx-text-fill: red; -fx-padding: 0 0 -1 0;");
                    }
                }
                setAlignment(Pos.CENTER);
            }
        });
        
        TableColumn<Cliente, Void> colAcciones = new TableColumn<>("");
        colAcciones.getStyleClass().add("columnaPersonalizada");
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
                	    ClienteForm form = new ClienteForm(dao, ClienteView.this::refrescarTabla, cliente);
                	    form.showAndWait();
                	});
                	btnEditar.setGraphic(iconoEditar);
                	btnEditar.getStyleClass().add("table-boton");
                	iconoEditar.setFitWidth(iconoTamaño);
                	iconoEditar.setFitHeight(iconoTamaño);

                    btnEliminar.setOnAction(e -> {
                        Cliente cliente = getTableView().getItems().get(getIndex());
                        
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmar eliminación");
                        alert.setHeaderText(null);
                        alert.setContentText("¿Seguro que deseas eliminar al cliente: " + cliente.getNombre() + "?");

                        // Espera la respuesta del usuario
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                dao.eliminarCliente(cliente.getId()); // elimina de la DB
                                getTableView().getItems().remove(cliente); // elimina de la tabla
                                refrescarTabla();
                            }
                        });
                    });
                	btnEliminar.setGraphic(iconoBorrar);
                	btnEliminar.getStyleClass().add("table-boton");
                	iconoBorrar.setFitWidth(iconoTamaño);
                	iconoBorrar.setFitHeight(iconoTamaño);
                	setStyle("-fx-padding: 0 0 0 0;");
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox contenedor = new HBox(5, btnEditar, btnEliminar);
                        contenedor.setPadding(new Insets(0, 0, 0, 0));
                        contenedor.setAlignment(Pos.CENTER);
                        setGraphic(contenedor);
                    }
                }
            };
        };

        colAcciones.setCellFactory(cellFactory);

        tabla.getColumns().addAll(colNombre, colTelefono, colLocalidad, colDireccion, colEmail, colActivo);
        
        tabla.getColumns().add(colAcciones);  

        // Cargar datos iniciales
        refrescarTabla();

        // Acción del botón
        btnNuevo.setOnAction(e -> {
            ClienteForm form = new ClienteForm(dao, ClienteView.this::refrescarTabla, null);
            form.showAndWait();
        });

        // Layout
        this.getChildren().addAll(barraAcciones, tabla);
        
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tabla, Priority.ALWAYS);
        tabla.setPrefWidth(Double.MAX_VALUE);

        // Columnas con mínimo ancho
        colNombre.setMinWidth(150);
        colTelefono.setMinWidth(100);
        colLocalidad.setMinWidth(100);
        colDireccion.setMinWidth(150);
        colEmail.setMinWidth(120);
        colActivo.setMinWidth(80);
        colAcciones.setMinWidth(80);
        //centrarColumna(colActivo);  

        
        this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/tabla.css").toExternalForm()
    	    );
        
    }
        
    private void refrescarTabla() {
    	
    	tabla.getItems().setAll(dao.obtenerClientes());
    	
    }
    
    private <T> void centrarColumna(TableColumn<Cliente, T> columna) {
        columna.setCellFactory(col -> new TableCell<Cliente, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
                setAlignment(Pos.CENTER);
            }
        });
    }
        
}
