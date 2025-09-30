package com.elsantigestion.ui;

import java.time.LocalDate;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.dao.ServicioEventualDAO;
import com.elsantigestion.model.ServicioEventual;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ServicioEventualView extends VBox {
	
	private final int iconoTamaño = 50;
	
	private TableView<ServicioEventual> tabla;
	private ServicioEventualDAO servicioDao;
	private ClienteDAO clienteDao;
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
	
	public ServicioEventualView() {
		
		servicioDao = new ServicioEventualDAO();
		clienteDao = new ClienteDAO();
		tabla = new TableView<>();
		btnNuevo = new Button();
		btnModificar = new Button();
		btnEliminar = new Button();
		agregar = new Image(getClass().getResource("/iconos/add.png").toExternalForm());
		modificar = new Image(getClass().getResource("/iconos/edit.png").toExternalForm());
		eliminar = new Image(getClass().getResource("/iconos/delete.png").toExternalForm());
		iconoAgregar = new ImageView(agregar);
		iconoModificar = new ImageView(modificar);
		iconoEliminar = new ImageView(eliminar);
		tooltipNuevo = new Tooltip("Agregar servicio");
		tooltipModificar = new Tooltip("Modificar servicio");
		tooltipEliminar = new Tooltip("Eliminar servicio");
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
		
		TableColumn<ServicioEventual, String> colCliente = new TableColumn<>("Cliente");
		TableColumn<ServicioEventual, LocalDate> colFechaCreacion = new TableColumn<>("Creado");
		TableColumn<ServicioEventual, LocalDate> colFechaProgramada = new TableColumn<>("Programado");
		TableColumn<ServicioEventual, Number> colPrecio = new TableColumn<>("Precio");
		TableColumn<ServicioEventual, Number> colGastos = new TableColumn<>("Gastos");
		TableColumn<ServicioEventual, Number> colMontoFinal = new TableColumn<>("Monto final");
	
		colCliente.setCellValueFactory(c -> {
		    int id = c.getValue().getClienteId();
		    String nombre = clienteDao.obtenerClientePorId(id).getNombre();
		    return new SimpleStringProperty(nombre);
		});
		colFechaCreacion.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getFechaCreacion()));
		colFechaProgramada.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getFechaProgramada()));
		colPrecio.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrecio()));
		colGastos.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getGastos()));
		colMontoFinal.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getMontoFinal()));
		
		
		colCliente.getStyleClass().add("columnaPersonalizada");
		colFechaCreacion.getStyleClass().add("columnaPersonalizada");
		colFechaProgramada.getStyleClass().add("columnaPersonalizada");
		colPrecio.getStyleClass().add("columnaPersonalizada");
		colGastos.getStyleClass().add("columnaPersonalizada");
		colMontoFinal.getStyleClass().add("columnaPersonalizada");
		
		tabla.getColumns().add(colFechaCreacion);
		tabla.getColumns().add(colCliente);
		tabla.getColumns().add(colFechaProgramada);
		tabla.getColumns().add(colPrecio);
		tabla.getColumns().add(colGastos);
		tabla.getColumns().add(colMontoFinal);
		tabla.getItems().setAll(servicioDao.obtenerServiciosEventuales());
    	tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	tabla.setPrefWidth(Double.MAX_VALUE);
    	VBox.setVgrow(tabla, Priority.ALWAYS);
		
    	this.getChildren().addAll(barraAcciones, tabla);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/tabla.css").toExternalForm()
    	    );
    	
		//Botones acciones/////////////////////
		btnNuevo.setOnAction(e -> {
			ServicioEventualForm form = new ServicioEventualForm(servicioDao, ServicioEventualView.this::refrescarTabla, null);
			form.showAndWait();
		});
		
		btnModificar.setOnAction(e -> {
		    ServicioEventual seleccionado = tabla.getSelectionModel().getSelectedItem();
		    
		    if (seleccionado != null) {
		        ServicioEventualForm form = new ServicioEventualForm(servicioDao, ServicioEventualView.this::refrescarTabla, seleccionado);
		        form.showAndWait();
		    } else {
		    	Alerta.warning("Atención", "Debe seleccionar un servicio para modificar.");
		    }
		});
		
		btnEliminar.setOnAction(e -> {
		    ServicioEventual seleccionado = tabla.getSelectionModel().getSelectedItem();

		    if (seleccionado != null) {
		        boolean confirmado = Alerta.confirmar(
		            "Confirmar eliminación",
		            "¿Está seguro de eliminar el servicio con ID: " + seleccionado.getId() + "?"
		        );
		        if (confirmado) {
		            servicioDao.eliminarServicioEventual(seleccionado.getId());
		            refrescarTabla();
		        }
		    } else {
		        Alerta.warning("Atención", "Debe seleccionar un servicio para eliminar.");
		    }
		});
		
		///////////////////////////////////////
		refrescarTabla();
		
	}
	
	private void refrescarTabla() {
    	
		tabla.getItems().setAll(servicioDao.obtenerServiciosEventuales());
    		
    }
	
	/*/private HashMap<Integer, String> crearListaDeClientes(ClienteDAO dao) {
	    HashMap<Integer, String> map = new HashMap<>();

	    dao.obtenerClientes().forEach(cliente -> {
	        map.put(cliente.getId(), cliente.getNombre());
	    });

	    return map;
	}/*/

}
