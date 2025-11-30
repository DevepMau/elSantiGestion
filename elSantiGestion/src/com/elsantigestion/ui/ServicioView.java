package com.elsantigestion.ui;

import java.time.LocalDate;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.model.Servicio;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ServicioView extends VBox {
	
	private final int iconoTamaño = 50;
	private final String TITULO_EVENTUALES = "Tabla de servicios Eventuales";
	private final String TITULO_MENSUALES = "Tabla de servicios Mensuales";
	
	private TableView<Servicio> tablaEventuales;
	private TableView<Servicio> tablaMensuales;
	private StackPane stack;
	private ClienteDAO clienteDao;
	private Button btnNuevo;
	private Button btnModificar;
	private Button btnEliminar;
	private Button btnSwap;
	private Image agregar;
	private Image modificar;
	private Image eliminar;
	private Image swap;
	private ImageView iconoAgregar;
	private ImageView iconoModificar;
	private ImageView iconoEliminar;
	private ImageView iconoSwap;
	private Tooltip tooltipNuevo;
	private Tooltip tooltipModificar;
	private Tooltip tooltipEliminar;
	private Tooltip tooltipSwap;
	private HBox barraAcciones;
	private HBox footer;
	private Label lblFooter;
	
	private GestorDeTareas gestor;
	
	public ServicioView(GestorDeTareas gdt) {
		
		clienteDao = new ClienteDAO();
		tablaEventuales = new TableView<>();
		tablaMensuales = new TableView<>();
		stack = new StackPane(tablaMensuales, tablaEventuales);
		tablaMensuales.setVisible(false);
        tablaEventuales.setVisible(true);
		btnNuevo = new Button();
		btnModificar = new Button();
		btnEliminar = new Button();
		btnSwap = new Button();
		agregar = new Image(getClass().getResource("/iconos/add.png").toExternalForm());
		modificar = new Image(getClass().getResource("/iconos/edit.png").toExternalForm());
		eliminar = new Image(getClass().getResource("/iconos/delete.png").toExternalForm());
		swap = new Image(getClass().getResource("/iconos/table-swap.png").toExternalForm());
		iconoAgregar = new ImageView(agregar);
		iconoModificar = new ImageView(modificar);
		iconoEliminar = new ImageView(eliminar);
		iconoSwap = new ImageView(swap);
		tooltipNuevo = new Tooltip("Agregar servicio");
		tooltipModificar = new Tooltip("Modificar servicio");
		tooltipEliminar = new Tooltip("Eliminar servicio");
		tooltipSwap = new Tooltip("Cambiar tabla");
		lblFooter = new Label(TITULO_EVENTUALES);
		barraAcciones = new HBox(2, btnSwap, btnEliminar, btnModificar, btnNuevo);
		footer = new HBox(lblFooter);
		
		gestor = gdt;
		
		iconoAgregar.setFitWidth(iconoTamaño);
		iconoAgregar.setFitHeight(iconoTamaño);
		iconoModificar.setFitWidth(iconoTamaño);
		iconoModificar.setFitHeight(iconoTamaño);
		iconoEliminar.setFitWidth(iconoTamaño);
		iconoEliminar.setFitHeight(iconoTamaño);
		iconoSwap.setFitWidth(iconoTamaño);
		iconoSwap.setFitHeight(iconoTamaño);
		
		btnNuevo.getStyleClass().add("barra-boton");
		btnModificar.getStyleClass().add("barra-boton");
		btnEliminar.getStyleClass().add("barra-boton");
		btnSwap.getStyleClass().add("barra-boton");
		
		btnNuevo.setGraphic(iconoAgregar);
		btnModificar.setGraphic(iconoModificar);
		btnEliminar.setGraphic(iconoEliminar);
		btnSwap.setGraphic(iconoSwap);
		
		btnNuevo.setTooltip(tooltipNuevo);
		btnModificar.setTooltip(tooltipModificar);
		btnEliminar.setTooltip(tooltipEliminar);
		btnSwap.setTooltip(tooltipSwap);
		
		barraAcciones.setAlignment(Pos.CENTER_RIGHT);
		barraAcciones.getStyleClass().add("barra");
		
		footer.setAlignment(Pos.CENTER_RIGHT);
		footer.getStyleClass().add("footer");
		
		//Columnas para la tabla de servicios eventuales/////////////////////////////////////
		TableColumn<Servicio, String> colClienteSE = new TableColumn<>("Cliente");
		TableColumn<Servicio, LocalDate> colFechaCreacionSE = new TableColumn<>("Creado");
		TableColumn<Servicio, LocalDate> colFechaProgramadaSE = new TableColumn<>("Programado");
		TableColumn<Servicio, Number> colPrecioSE = new TableColumn<>("Precio");
		TableColumn<Servicio, Number> colGastosSE = new TableColumn<>("Gastos");
		TableColumn<Servicio, Number> colMontoFinalSE = new TableColumn<>("Monto\nfinal");
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
		
		colEstadoSE.setCellFactory(col -> new TableCell<Servicio, String>() {
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                	setText(item);

                    // Estilo según el valor del estado
                    switch (item.toLowerCase()) {
                        case "en curso":
                            setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-font-weight: bold;"); // azul
                            break;
                        case "suspendido":
                            setStyle("-fx-background-color: #E5A50A; -fx-text-fill: white; -fx-font-weight: bold;"); // amarillo
                            break;
                        case "atrasado":
                            setStyle("-fx-background-color: #D90429; -fx-text-fill: white; -fx-font-weight: bold;"); // rojo
                            break;
                        case "finalizado":
                            setStyle("-fx-background-color: #2E8B57; -fx-text-fill: white; -fx-font-weight: bold;"); // verde
                            break;
                        default:
                            setStyle("-fx-background-color: white; -fx-text-fill: black;");
                            break;
                    }
                }
                setAlignment(Pos.CENTER);
            }
        });
		
		colClienteSE.getStyleClass().add("columna-texto");
		colFechaCreacionSE.getStyleClass().add("columna-especial");
		colFechaProgramadaSE.getStyleClass().add("columna-especial");
		colPrecioSE.getStyleClass().add("columna-numero");
		colGastosSE.getStyleClass().add("columna-numero");
		colMontoFinalSE.getStyleClass().add("columna-numero");
		colEstadoSE.getStyleClass().add("columna-especial");
		
		tablaEventuales.getColumns().add(colFechaCreacionSE);
		tablaEventuales.getColumns().add(colClienteSE);
		tablaEventuales.getColumns().add(colFechaProgramadaSE);
		tablaEventuales.getColumns().add(colPrecioSE);
		tablaEventuales.getColumns().add(colGastosSE);
		tablaEventuales.getColumns().add(colMontoFinalSE);
		tablaEventuales.getColumns().add(colEstadoSE);
    	tablaEventuales.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	tablaEventuales.setPrefWidth(Double.MAX_VALUE);
    	
    	//Columnas para la tabla de servicios mensuales///////////////////////////////////////////
    	TableColumn<Servicio, String> colClienteSM = new TableColumn<>("Cliente");
    	TableColumn<Servicio, LocalDate> colFechaCreacionSM = new TableColumn<>("Creado");
    	TableColumn<Servicio, Number> colPrecioSM = new TableColumn<>("Precio");
    	TableColumn<Servicio, Number> colGastosSM = new TableColumn<>("Gastos");
    	TableColumn<Servicio, Number> colMontoFinalSM = new TableColumn<>("Monto\nfinal");
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
    	
    	colEstadoSM.setCellFactory(col -> new TableCell<Servicio, String>() {
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                	setText(item);

                    // Estilo según el valor del estado
                    switch (item.toLowerCase()) {
                        case "en curso":
                            setStyle("-fx-text-fill: #0078D7; -fx-font-weight: bold;"); // azul
                            break;
                        case "suspendido":
                            setStyle("-fx-text-fill: #E5A50A; -fx-font-weight: bold;"); // amarillo
                            break;
                        case "atrasado":
                            setStyle("-fx-text-fill: #D90429; -fx-font-weight: bold;"); // rojo
                            break;
                        case "finalizado":
                            setStyle("-fx-text-fill: #2E8B57; -fx-font-weight: bold;"); // verde
                            break;
                        default:
                            setStyle("-fx-text-fill: black;");
                            break;
                    }
                }
                setAlignment(Pos.CENTER);
            }
        });
    	
    	colClienteSM.getStyleClass().add("columna-texto");
    	colFechaCreacionSM.getStyleClass().add("columna-especial");
    	colPrecioSM.getStyleClass().add("columna-numero");
    	colGastosSM.getStyleClass().add("columna-numero");
    	colMontoFinalSM.getStyleClass().add("columna-numero");
    	colEstadoSM.getStyleClass().add("columna-especial");
    			
    	tablaMensuales.getColumns().add(colFechaCreacionSM);
    	tablaMensuales.getColumns().add(colClienteSM);
    	tablaMensuales.getColumns().add(colPrecioSM);
    	tablaMensuales.getColumns().add(colGastosSM);
    	tablaMensuales.getColumns().add(colMontoFinalSM);
    	tablaMensuales.getColumns().add(colEstadoSM);
    	tablaMensuales.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	tablaMensuales.setPrefWidth(Double.MAX_VALUE);
    	
    	VBox.setVgrow(stack, Priority.ALWAYS);
		
    	this.getChildren().addAll(barraAcciones, stack, gestor, footer);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/tabla.css").toExternalForm()
    	    );
	}
	
	public void setOnServicioEventualSeleccionado(ChangeListener<Servicio> listener) {
        tablaEventuales.getSelectionModel().selectedItemProperty().addListener(listener);
    }
	
	public void setOnServicioMensualSeleccionado(ChangeListener<Servicio> listener) {
        tablaMensuales.getSelectionModel().selectedItemProperty().addListener(listener);
    }
	
	public TableView<Servicio> getTablaEventuales(){
		return this.tablaEventuales;
	}
	
	public TableView<Servicio> getTablaMensuales(){
		return this.tablaMensuales;
	}

	public Button getBtnNuevo() {
		return btnNuevo;
	}

	public void setBtnNuevo(Button btnNuevo) {
		this.btnNuevo = btnNuevo;
	}

	public Button getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(Button btnModificar) {
		this.btnModificar = btnModificar;
	}

	public Button getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(Button btnEliminar) {
		this.btnEliminar = btnEliminar;
	}
	
	public Button getBtnSwap() {
		return btnSwap;
	}
	
	public void setBtnSwap(Button btnSwap) {
		this.btnSwap = btnSwap;
	}
	
	public GestorDeTareas getGestorDeTareas() {
		return gestor;
	}

	public String getTITULO_MENSUALES() {
		return TITULO_MENSUALES;
	}

	public String getTITULO_EVENTUALES() {
		return TITULO_EVENTUALES;
	}
	
	public void setTituloDeTabla(Boolean boo) {
		if(boo) {
			this.lblFooter.setText(TITULO_EVENTUALES);
		}
		else {
			this.lblFooter.setText(TITULO_MENSUALES);
		}
	}

	public void setTablaMensuales(TableView<Servicio> tablaMensuales) {
		this.tablaMensuales = tablaMensuales;
	}

}
