package com.elsantigestion.ui;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.model.Cliente;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class ClienteView extends VBox {
	
private final int iconoTamaño = 50;
	
	private TableView<Cliente> tabla;
	private ClienteDAO dao;
	private Button btnNuevo;
	private Button btnModificar;
	private Button btnEliminar;
	private Image agregar;
	private Image modificar;
	private Image eliminar;
	private ImageView iconoAgregar;
	private ImageView iconoModificar;
	private ImageView iconoEliminar;
	private Tooltip tooltipNuevo;
	private Tooltip tooltipModificar;
	private Tooltip tooltipEliminar;
	private HBox barraAcciones;

    public ClienteView() {
    	
    	dao = new ClienteDAO();
		tabla = new TableView<>();
		btnNuevo = new Button();
		btnModificar = new Button();
		btnEliminar = new Button();
		agregar = new Image(getClass().getResource("/iconos/cliente.png").toExternalForm());
		modificar = new Image(getClass().getResource("/iconos/edit.png").toExternalForm());
		eliminar = new Image(getClass().getResource("/iconos/delete.png").toExternalForm());
		iconoAgregar = new ImageView(agregar);
		iconoModificar = new ImageView(modificar);
		iconoEliminar = new ImageView(eliminar);
		tooltipNuevo = new Tooltip("Agregar cliente");
		tooltipModificar = new Tooltip("Modificar cliente");
		tooltipEliminar = new Tooltip("Eliminar cliente");
		barraAcciones = new HBox(2, btnEliminar, btnModificar, btnNuevo);
		iconoAgregar.setFitWidth(iconoTamaño);
		iconoAgregar.setFitHeight(iconoTamaño);
		iconoModificar.setFitWidth(iconoTamaño);
		iconoModificar.setFitHeight(iconoTamaño);
		iconoEliminar.setFitWidth(iconoTamaño);
		iconoEliminar.setFitHeight(iconoTamaño);
		
		btnNuevo.getStyleClass().add("barra-boton");
		btnModificar.getStyleClass().add("barra-boton");
		btnEliminar.getStyleClass().add("barra-boton");
		
		btnNuevo.setGraphic(iconoAgregar);
		btnModificar.setGraphic(iconoModificar);
		btnEliminar.setGraphic(iconoEliminar);
		
		btnNuevo.setTooltip(tooltipNuevo);
		btnModificar.setTooltip(tooltipModificar);
		btnEliminar.setTooltip(tooltipEliminar);
		
		barraAcciones.setAlignment(Pos.CENTER_RIGHT);
		barraAcciones.getStyleClass().add("barra");
        
        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Teléfono");
        TableColumn<Cliente, String> colLocalidad = new TableColumn<>("Localidad");
        TableColumn<Cliente, String> colDireccion = new TableColumn<>("Direccion");
        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        TableColumn<Cliente, Boolean> colActivo = new TableColumn<>("Activo");
        
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));
        colLocalidad.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLocalidad()));
        colDireccion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDireccion()));
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        colActivo.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().isActivo()));
        
        colNombre.getStyleClass().add("columnaPersonalizada");
        colTelefono.getStyleClass().add("columnaPersonalizada");
        colLocalidad.getStyleClass().add("columnaPersonalizada");
        colDireccion.getStyleClass().add("columnaPersonalizada");
        colEmail.getStyleClass().add("columnaPersonalizada");
        colActivo.getStyleClass().add("columnaPersonalizada");

        colActivo.setCellFactory(col -> new TableCell<Cliente, Boolean>() {
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

        tabla.getColumns().add(colNombre);
        tabla.getColumns().add(colTelefono); 
        tabla.getColumns().add(colLocalidad); 
        tabla.getColumns().add(colDireccion); 
        tabla.getColumns().add(colEmail); 
        tabla.getColumns().add(colActivo); 
        tabla.getItems().setAll(dao.obtenerClientes());
    	tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	tabla.setPrefWidth(Double.MAX_VALUE);
    	VBox.setVgrow(tabla, Priority.ALWAYS);
        
    	this.getChildren().addAll(barraAcciones, tabla);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/tabla.css").toExternalForm()
    	    );
		
		//Botones acciones/////////////////////
		btnNuevo.setOnAction(e -> {
			ClienteForm form = new ClienteForm(dao, ClienteView.this::refrescarTabla, null);
			form.showAndWait();
		});
		
		btnModificar.setOnAction(e -> {
		    Cliente seleccionado = tabla.getSelectionModel().getSelectedItem();
		    
		    if (seleccionado != null) {
		        ClienteForm form = new ClienteForm(dao, ClienteView.this::refrescarTabla, seleccionado);
		        form.showAndWait();
		    } else {
		    	Alerta.warning("Atención", "Debe seleccionar un cliente para modificar.");
		    }
		});
		
		btnEliminar.setOnAction(e -> {
		    Cliente seleccionado = tabla.getSelectionModel().getSelectedItem();

		    if (seleccionado != null) {
		        boolean confirmado = Alerta.confirmar(
		            "Confirmar eliminación",
		            "¿Está seguro de eliminar el cliente: " + seleccionado.getNombre() + "?"
		        );
		        if (confirmado) {
		            dao.eliminarCliente(seleccionado.getId());
		            refrescarTabla();
		        }
		    } else {
		        Alerta.warning("Atención", "Debe seleccionar un cliente para eliminar.");
		    }
		});
		
		///////////////////////////////////////
		refrescarTabla();

        colNombre.setMinWidth(150);
        colTelefono.setMinWidth(100);
        colLocalidad.setMinWidth(100);
        colDireccion.setMinWidth(150);
        colEmail.setMinWidth(120);
        colActivo.setMinWidth(80); 

    }
        
    private void refrescarTabla() {
    	
    	tabla.getItems().setAll(dao.obtenerClientes());
    	
    }
        
}
