package com.elsantigestion.ui;

import java.time.LocalDate;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.dao.ServicioDAO;
import com.elsantigestion.model.Servicio;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ServicioView extends VBox {
	
	private final int iconoTamaño = 50;
	
	private TableView<Servicio> tablaEventuales;
	private TableView<Servicio> tablaMensuales;
	private StackPane stack;
	private ServicioDAO servicioDao;
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
	private ComboBox<String> cmbTabla;
	private HBox barraAcciones;
	private HBox boxBotones;
	private Region spacer;
	
	public ServicioView() {
		
		servicioDao = new ServicioDAO();
		clienteDao = new ClienteDAO();
		tablaEventuales = new TableView<>();
		tablaMensuales = new TableView<>();
		stack = new StackPane(tablaMensuales, tablaEventuales);
		tablaMensuales.setVisible(true);
        tablaEventuales.setVisible(false);
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
		cmbTabla = new ComboBox<>();
		spacer = new Region();
		boxBotones = new HBox(2, btnEliminar, btnModificar, btnNuevo);
		barraAcciones = new HBox();
		
		cmbTabla.getItems().addAll("Servicios Eventuales", "Servicios Mensuales");
		cmbTabla.setValue("Servicios Mensuales");
		
		iconoAgregar.setFitWidth(iconoTamaño);
		iconoAgregar.setFitHeight(iconoTamaño);
		iconoModificar.setFitWidth(iconoTamaño);
		iconoModificar.setFitHeight(iconoTamaño);
		iconoEliminar.setFitWidth(iconoTamaño);
		iconoEliminar.setFitHeight(iconoTamaño);
		
		btnNuevo.getStyleClass().add("barra-boton");
		btnModificar.getStyleClass().add("barra-boton");
		btnEliminar.getStyleClass().add("barra-boton");
		cmbTabla.getStyleClass().add("combo-tabla");
		
		btnNuevo.setGraphic(iconoAgregar);
		btnModificar.setGraphic(iconoModificar);
		btnEliminar.setGraphic(iconoEliminar);
		
		btnNuevo.setTooltip(tooltipNuevo);
		btnModificar.setTooltip(tooltipModificar);
		btnEliminar.setTooltip(tooltipEliminar);
		
		boxBotones.setAlignment(Pos.CENTER_RIGHT);
		
		barraAcciones.getStyleClass().add("barra");	
		barraAcciones.setAlignment(Pos.TOP_RIGHT);
		barraAcciones.setSpacing(10);
		barraAcciones.getChildren().addAll(cmbTabla, spacer, boxBotones);
		
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		//Columnas para la tabla de servicios eventuales/////////////////////////////////////
		TableColumn<Servicio, String> colClienteSE = new TableColumn<>("Cliente");
		TableColumn<Servicio, LocalDate> colFechaCreacionSE = new TableColumn<>("Creado");
		TableColumn<Servicio, LocalDate> colFechaProgramadaSE = new TableColumn<>("Programado");
		TableColumn<Servicio, Number> colPrecioSE = new TableColumn<>("Precio");
		TableColumn<Servicio, Number> colGastosSE = new TableColumn<>("Gastos");
		TableColumn<Servicio, Number> colMontoFinalSE = new TableColumn<>("Monto final");
		TableColumn<Servicio, String> colEstadoSE = new TableColumn<>("Estado");
	
		colClienteSE.setCellValueFactory(c -> {
		    int id = c.getValue().getClienteId();
		    String nombre = clienteDao.obtenerClientePorId(id).getNombre();
		    return new SimpleStringProperty(nombre);
		});
		colFechaCreacionSE.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getFechaCreacion()));
		colFechaProgramadaSE.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getFechaProgramada()));
		colPrecioSE.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrecio()));
		colGastosSE.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getGastos()));
		colMontoFinalSE.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getMontoFinal()));
		colEstadoSE.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado()));
		
		
		colClienteSE.getStyleClass().add("columnaPersonalizada");
		colFechaCreacionSE.getStyleClass().add("columnaPersonalizada");
		colFechaProgramadaSE.getStyleClass().add("columnaPersonalizada");
		colPrecioSE.getStyleClass().add("columnaPersonalizada");
		colGastosSE.getStyleClass().add("columnaPersonalizada");
		colMontoFinalSE.getStyleClass().add("columnaPersonalizada");
		colEstadoSE.getStyleClass().add("columnaPersonalizada");
		
		tablaEventuales.getColumns().add(colFechaCreacionSE);
		tablaEventuales.getColumns().add(colClienteSE);
		tablaEventuales.getColumns().add(colFechaProgramadaSE);
		tablaEventuales.getColumns().add(colPrecioSE);
		tablaEventuales.getColumns().add(colGastosSE);
		tablaEventuales.getColumns().add(colMontoFinalSE);
		tablaEventuales.getColumns().add(colEstadoSE);
		tablaEventuales.getItems().setAll(servicioDao.obtenerServiciosPorTipo("Eventual"));
    	tablaEventuales.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	tablaEventuales.setPrefWidth(Double.MAX_VALUE);
    	VBox.setVgrow(tablaEventuales, Priority.ALWAYS);
    	
    	//Columnas para la tabla de servicios mensuales///////////////////////////////////////////
    	TableColumn<Servicio, String> colClienteSM = new TableColumn<>("Cliente");
    	TableColumn<Servicio, LocalDate> colFechaCreacionSM = new TableColumn<>("Creado");
    	TableColumn<Servicio, Number> colPrecioSM = new TableColumn<>("Precio");
    	TableColumn<Servicio, Number> colGastosSM = new TableColumn<>("Gastos");
    	TableColumn<Servicio, Number> colMontoFinalSM = new TableColumn<>("Monto final");
    	TableColumn<Servicio, String> colEstadoSM = new TableColumn<>("Estado");
    			
    	colClienteSM.setCellValueFactory(c -> {
    	    int id = c.getValue().getClienteId();
    	    String nombre = clienteDao.obtenerClientePorId(id).getNombre();
    	    return new SimpleStringProperty(nombre);
    	});
    	colFechaCreacionSM.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getFechaCreacion()));
    	colPrecioSM.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrecio()));
    	colGastosSM.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getGastos()));
    	colMontoFinalSM.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getMontoFinal()));
    	colEstadoSM.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado()));
    	
    	
    	colClienteSM.getStyleClass().add("columnaPersonalizada");
    	colFechaCreacionSM.getStyleClass().add("columnaPersonalizada");
    	colPrecioSM.getStyleClass().add("columnaPersonalizada");
    	colGastosSM.getStyleClass().add("columnaPersonalizada");
    	colMontoFinalSM.getStyleClass().add("columnaPersonalizada");
    	colEstadoSM.getStyleClass().add("columnaPersonalizada");
    			
    	tablaMensuales.getColumns().add(colFechaCreacionSM);
    	tablaMensuales.getColumns().add(colClienteSM);
    	tablaMensuales.getColumns().add(colPrecioSM);
    	tablaMensuales.getColumns().add(colGastosSM);
    	tablaMensuales.getColumns().add(colMontoFinalSM);
    	tablaMensuales.getColumns().add(colEstadoSM);
    	tablaMensuales.getItems().setAll(servicioDao.obtenerServiciosPorTipo("Mensual"));
    	tablaMensuales.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	tablaMensuales.setPrefWidth(Double.MAX_VALUE);
    	VBox.setVgrow(tablaMensuales, Priority.ALWAYS);
		
    	this.getChildren().addAll(barraAcciones, stack);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/tabla.css").toExternalForm()
    	    );
    	
		//Botones acciones/////////////////////
		cmbTabla.setOnAction(e -> {
            String seleccion = cmbTabla.getValue();
            
            refrescarTabla();

            if ("Servicios Mensuales".equals(seleccion)) {
                tablaMensuales.toFront();
                tablaMensuales.setVisible(true);
                tablaEventuales.setVisible(false);
            } else {
                tablaEventuales.toFront();
                tablaEventuales.setVisible(true);
                tablaMensuales.setVisible(false);
            }
        });
		
		btnNuevo.setOnAction(e -> {
			ServicioForm form = new ServicioForm(servicioDao, ServicioView.this::refrescarTabla, null);
			form.showAndWait();
		});
		
		btnModificar.setOnAction(e -> {
			
			Servicio seleccionado = null;
			String seleccion = cmbTabla.getValue();
			if("Servicios Mensuales".equals(seleccion)) {
				seleccionado = tablaMensuales.getSelectionModel().getSelectedItem();
			}
			else {
				seleccionado = tablaEventuales.getSelectionModel().getSelectedItem();
			}
		    
		    if (seleccionado != null) {
		        ServicioForm form = new ServicioForm(servicioDao, ServicioView.this::refrescarTabla, seleccionado);
		        form.showAndWait();
		    } else {
		    	Alerta.warning("Atención", "Debe seleccionar un servicio para modificar.");
		    }
		});
		
		btnEliminar.setOnAction(e -> {
			
			Servicio seleccionado = null;
			String seleccion = cmbTabla.getValue();
			if("Servicios Mensuales".equals(seleccion)) {
				seleccionado = tablaMensuales.getSelectionModel().getSelectedItem();
			}
			else {
				seleccionado = tablaEventuales.getSelectionModel().getSelectedItem();
			}

		    if (seleccionado != null) {
		        boolean confirmado = Alerta.confirmar(
		            "Confirmar eliminación",
		            "¿Está seguro de eliminar el servicio con ID: " + seleccionado.getId() + "?"
		        );
		        if (confirmado) {
		            servicioDao.eliminarServicio(seleccionado.getId());
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
    	
		tablaEventuales.getItems().setAll(servicioDao.obtenerServiciosPorTipo("Eventual"));
		tablaMensuales.getItems().setAll(servicioDao.obtenerServiciosPorTipo("Mensual"));
    		
    }
	
	/*/private HashMap<Integer, String> crearListaDeClientes(ClienteDAO dao) {
	    HashMap<Integer, String> map = new HashMap<>();

	    dao.obtenerClientes().forEach(cliente -> {
	        map.put(cliente.getId(), cliente.getNombre());
	    });

	    return map;
	}/*/

}
