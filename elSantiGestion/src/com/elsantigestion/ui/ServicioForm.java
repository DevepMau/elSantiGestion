package com.elsantigestion.ui;

import java.time.LocalDate;
import java.util.List;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.dao.ServicioDAO;
import com.elsantigestion.dao.ServicioTrabajoDAO;
import com.elsantigestion.model.Cliente;
import com.elsantigestion.model.Servicio;
import com.elsantigestion.model.ServicioTrabajo;
import com.elsantigestion.model.Trabajo;
import com.elsantigestion.utils.ValidadorCampos;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ServicioForm extends Stage {

	@SuppressWarnings("unused")
	private Runnable onSaveCallBack;
	@SuppressWarnings("unused")
	private ServicioDAO dao;
    private ClienteDAO clienteDao;
    private ServicioTrabajoDAO stDao;
    private double xOffset;
    private double yOffset;
    private Label titulo;
    private Label lblFechaFinal;
    private Label lblPrecio;
    private Label lblGastos;
    private Label lblMontoFinal;
    private Label lblPeriodisidad;
    private Label msgCliente;
    private Label msgFechaFinal;
    private Label msgPrecio;
    private Label msgGastos;
    private ComboBox<String> cmbPeriodisidad;
    private ComboBox<Cliente> cmbCliente;
    private ComboBox<String> cmbEstado;
    private Button btnGuardar;
    private Button btnCerrar;
    private DatePicker dpFechaProgramada;
    private TextField txtPrecio;
    private TextField txtGastos;
    private TextField txtMontoFinal;
    private Region spacer;
    private Region spacerFechaFinal;
    private Region spacerPrecio;
    private Region spacerGastos;
    private Region spacerMontoFinal;
    private Region spacerPeriodisidad;
    private GridPane grid;
    private Scene scene;
    private HBox barraSuperior;
    private HBox boxPrecio;
    private HBox boxGastos;
    private HBox boxMontoFinal;
    private HBox boxTipo;
    private TrabajoChecker tChecker;

    @SuppressWarnings("exports") 
    public ServicioForm(ServicioDAO dao, Runnable onSaveCallBack, Servicio servicioExistente) {

        this.dao = dao;
        this.onSaveCallBack = onSaveCallBack;
        this.xOffset = 0;
        this.yOffset = 0;
        this.initStyle(StageStyle.UNDECORATED);

        clienteDao = new ClienteDAO();
        stDao = new ServicioTrabajoDAO();
        grid = new GridPane();
        btnGuardar = new Button("Guardar");
        btnCerrar = new Button("X");
        spacer = new Region();
        spacerFechaFinal = new Region();
        spacerPrecio = new Region();
        spacerMontoFinal = new Region();
        spacerGastos = new Region();
        spacerPeriodisidad = new Region();
        lblFechaFinal = new Label("Fecha programada: ");
        lblPrecio = new Label("Precio:");
        lblGastos = new Label("insumos:");
        lblMontoFinal = new Label("Monto a cobrar:");
        lblPeriodisidad = new Label("Tipo de servicio:");
        msgCliente = new Label("");
        msgFechaFinal = new Label("");
        msgPrecio = new Label("");
        msgGastos = new Label("");
        dpFechaProgramada = new DatePicker();
        barraSuperior = new HBox();
        boxPrecio = new HBox();
        boxGastos = new HBox();
        boxMontoFinal = new HBox();
        boxTipo = new HBox();
        txtPrecio = new TextField();
        txtGastos = new TextField();
        txtMontoFinal = new TextField();
        scene = new Scene(grid, 400, 600);
        
        cmbPeriodisidad = new ComboBox<>();
        cmbPeriodisidad.getItems().addAll( "Eventual", "Mensual");
        cmbPeriodisidad.setValue("Eventual");
        
        cmbEstado = new ComboBox<>();
        cmbEstado.getItems().addAll("En Curso", "Finalizado", "Suspendido");
        cmbEstado.setValue("En Curso");
        
        List<Cliente> clientes = clienteDao.obtenerClientes();
        cmbCliente = new ComboBox<>();
        cmbCliente.getItems().addAll(clientes);
        cmbCliente.setPromptText("Seleccione un cliente...");

        // Mostrar nombre pero mantener ID internamente
        cmbCliente.setCellFactory(listView -> new javafx.scene.control.ListCell<Cliente>() {
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getId() + " - " + item.getNombre());
                }
            }
        });
        cmbCliente.setButtonCell(new javafx.scene.control.ListCell<Cliente>() {
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getId() + " - " + item.getNombre());
                }
            }
        });
        
        cmbPeriodisidad.setOnAction(e -> {
        	String seleccion = cmbPeriodisidad.getValue();
        	
        	if(seleccion.equals("Mensual")) {
        		dpFechaProgramada.setDisable(true);
        		dpFechaProgramada.setValue(null);
        	}
        	else {
        		dpFechaProgramada.setDisable(false);
        	}
        });

        txtPrecio.setPromptText("Ingrese precio...");
        txtGastos.setPromptText("Ingrese gastos...");
        
        //txtPrecio.setMaxWidth(250);
        //txtGastos.setMaxWidth(250);
        txtMontoFinal.setMaxWidth(150);
        cmbPeriodisidad.setMaxWidth(200);
        
        lblFechaFinal.getStyleClass().add("label-info");
        lblPrecio.getStyleClass().add("label-info");
        lblGastos.getStyleClass().add("label-info");
        lblMontoFinal.getStyleClass().add("label-info");
        lblPeriodisidad.getStyleClass().add("label-info");
        msgCliente.getStyleClass().add("label-error");
        msgFechaFinal.getStyleClass().add("label-error");
        msgPrecio.getStyleClass().add("label-error");
        msgGastos.getStyleClass().add("label-error");
        
        txtMontoFinal.setEditable(false);
        txtMontoFinal.setDisable(true);
        
        txtPrecio.textProperty().addListener((obs, oldVal, newVal) -> actualizarMontoFinal());
        txtGastos.textProperty().addListener((obs, oldVal, newVal) -> actualizarMontoFinal());       

        if (servicioExistente != null) {
            titulo = new Label("Modificar servicio");
            // Seleccionamos el cliente correcto en el ComboBox
            clientes.stream()
                    .filter(c -> c.getId() == servicioExistente.getClienteId())
                    .findFirst()
                    .ifPresent(cmbCliente::setValue);

            dpFechaProgramada.setValue(servicioExistente.getFechaProgramada());
            cmbPeriodisidad.setValue(servicioExistente.getTipo());
            txtPrecio.setText(String.valueOf(servicioExistente.getPrecio()));
            txtGastos.setText(String.valueOf(servicioExistente.getGastos()));
            txtMontoFinal.setText(String.valueOf(servicioExistente.getMontoFinal()));
            tChecker = new TrabajoChecker(stDao.obtenerIdsPorServicio(servicioExistente.getId()));
        } else {
        	tChecker = new TrabajoChecker(null);
            titulo = new Label("Agregar servicio");
        }
        
        dpFechaProgramada.setDisable(cmbPeriodisidad.getValue().equals("Mensual"));
        
        cmbCliente.focusedProperty().addListener((obs, oldVal, newVal) -> {
        	if(!newVal) {
        		if(!ValidadorCampos.esSeleccionValida(cmbCliente, msgCliente)) return;
        	}
        });      
        cmbPeriodisidad.setOnAction(c -> {
        	String seleccion = cmbPeriodisidad.getValue();
    		if(seleccion.equals("Mensual")) {
    			msgFechaFinal.setText("");
    			dpFechaProgramada.setStyle("");
    			dpFechaProgramada.setValue(LocalDate.now());
    			dpFechaProgramada.setDisable(true);
    		}
    		else {
    			dpFechaProgramada.setDisable(false);
    		}
        });
        dpFechaProgramada.focusedProperty().addListener((obs, oldVal, newVal) -> {
        	if(!newVal) {
        		if(!ValidadorCampos.esFechaValida(dpFechaProgramada, msgFechaFinal)) return;
        	}
        });
        txtPrecio.focusedProperty().addListener((obs, oldVal, newVal) -> {
        	if(!newVal) {
        		if(!ValidadorCampos.esNoVacio(txtPrecio, msgPrecio)) return;
        		if(!ValidadorCampos.esNumerico(txtPrecio, msgPrecio)) return;
        	}
        });
        txtGastos.focusedProperty().addListener((obs, oldVal, newVal) -> {
        	if(!newVal) {
        		if(!ValidadorCampos.esNoVacio(txtGastos, msgGastos)) return;
        		if(!ValidadorCampos.esNumerico(txtGastos, msgGastos)) return;
        	}
        });
        
        boxPrecio.setAlignment(Pos.TOP_RIGHT);
        boxPrecio.setSpacing(10);
        boxPrecio.getChildren().addAll(lblPrecio, spacerPrecio, txtPrecio);
        
        boxGastos.setAlignment(Pos.TOP_RIGHT);
        boxGastos.setSpacing(10);
        boxGastos.getChildren().addAll(lblGastos, spacerGastos, txtGastos);
        
        boxMontoFinal.setAlignment(Pos.TOP_RIGHT);
        boxMontoFinal.setSpacing(10);
        boxMontoFinal.getChildren().addAll(lblMontoFinal, spacerMontoFinal, txtMontoFinal);
        
        boxTipo.setAlignment(Pos.TOP_RIGHT);
        boxTipo.setSpacing(10);
        boxTipo.getChildren().addAll(cmbPeriodisidad, dpFechaProgramada);
        
        barraSuperior.setAlignment(Pos.TOP_RIGHT);
        barraSuperior.setSpacing(10);
        barraSuperior.getChildren().addAll(titulo, spacer, btnCerrar);
        barraSuperior.getStyleClass().add("topBar");
        barraSuperior.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        barraSuperior.setOnMouseDragged(event -> {
            barraSuperior.getScene().getWindow().setX(event.getScreenX() - xOffset);
            barraSuperior.getScene().getWindow().setY(event.getScreenY() - yOffset);
        });
        
        HBox.setMargin(lblFechaFinal, new Insets(10, 10, 10, 10));
        HBox.setHgrow(spacerFechaFinal, Priority.ALWAYS);
        
        HBox.setMargin(lblPrecio, new Insets(10, 10, 10, 10));
        HBox.setHgrow(spacerPrecio, Priority.ALWAYS);
        
        HBox.setMargin(lblGastos, new Insets(10, 10, 10, 10));
        HBox.setHgrow(spacerGastos, Priority.ALWAYS);
        
        HBox.setMargin(lblMontoFinal, new Insets(10, 10, 10, 10));
        HBox.setHgrow(spacerMontoFinal, Priority.ALWAYS);
        
        HBox.setMargin(lblPeriodisidad, new Insets(10, 10, 10, 10));
        HBox.setHgrow(spacerPeriodisidad, Priority.ALWAYS);

        HBox.setMargin(titulo, new Insets(15, 10, 0, 10));
        HBox.setHgrow(spacer, Priority.ALWAYS);
        titulo.getStyleClass().add("label-info");

        btnGuardar.getStyleClass().add("boton");
        btnGuardar.setOnAction(e -> {
        	
        	Cliente clienteSeleccionado = cmbCliente.getValue();
        	
        	if(!ValidadorCampos.esSeleccionValida(cmbCliente, msgCliente)) return;
        	if(!ValidadorCampos.esFechaValida(dpFechaProgramada, msgFechaFinal)) return;
        	if(!ValidadorCampos.esNoVacio(txtPrecio, msgPrecio)) return;
    		if(!ValidadorCampos.esNumerico(txtPrecio, msgPrecio)) return;
    		if(!ValidadorCampos.esNoVacio(txtGastos, msgGastos)) return;
    		if(!ValidadorCampos.esNumerico(txtGastos, msgGastos)) return;
        	
        	if(txtPrecio.getText().equals("")) {txtPrecio.setText("0");}
        	if(txtGastos.getText().equals("")) {txtGastos.setText("0");}

            if (servicioExistente == null) {
                Servicio nuevo = new Servicio(
                        0,
                        clienteSeleccionado.getId(),
                        LocalDate.now(),
                        dpFechaProgramada.getValue(),
                        cmbPeriodisidad.getValue(),
                        Double.parseDouble(txtPrecio.getText()),
                        Double.parseDouble(txtGastos.getText()),
                        Double.parseDouble(txtMontoFinal.getText()),
                        cmbEstado.getValue());
                int idGenerado = dao.agregarServicio(nuevo);
                
                List<Trabajo> trabajos = tChecker.obtenerTrabajos(); 
            	for(int i=0; i<trabajos.size(); i++) {
            		ServicioTrabajo st = new ServicioTrabajo(idGenerado, trabajos.get(i).getId(), 1);
            		stDao.agregar(st);
            	}
                
            } else {
            	servicioExistente.setClienteId(clienteSeleccionado.getId());
                servicioExistente.setFechaProgramada(dpFechaProgramada.getValue());
                servicioExistente.setTipo(cmbPeriodisidad.getValue());
                servicioExistente.setPrecio(Double.parseDouble(txtPrecio.getText()));
                servicioExistente.setGastos(Double.parseDouble(txtGastos.getText()));
                servicioExistente.setMontoFinal(Double.parseDouble(txtMontoFinal.getText()));
                servicioExistente.setEstado(cmbEstado.getValue());
               
                dao.actualizarServicio(servicioExistente);
                
                stDao.eliminarPorServicio(servicioExistente.getId());
                
                List<Trabajo> trabajos = tChecker.obtenerTrabajos(); 
            	for(int i=0; i<trabajos.size(); i++) {
            		ServicioTrabajo st = new ServicioTrabajo(servicioExistente.getId(), trabajos.get(i).getId(), 1);
            		stDao.agregar(st);
            	}

            }

            if (onSaveCallBack != null) {
                onSaveCallBack.run();
            }
            close();
        });

        btnCerrar.setOnAction(e -> close());

        // LAYOUT //////////////////////////////

        grid.getStyleClass().add("formulario");
        grid.setPadding(new Insets(10));
        grid.add(barraSuperior, 0, 0);
        grid.addRow(1, msgCliente);
        grid.addRow(2, cmbCliente);
        grid.addRow(3, msgFechaFinal);
        grid.addRow(4, boxTipo);
        grid.addRow(5, tChecker);
        grid.addRow(6, msgPrecio);
        grid.addRow(7, txtPrecio);
        grid.addRow(8, msgGastos);
        grid.addRow(9, txtGastos);
        grid.add(btnGuardar, 0, 10);
        GridPane.setMargin(msgCliente, new Insets(30, 20, 0, 20));
        GridPane.setMargin(cmbCliente, new Insets(0, 20, 0, 20));
        GridPane.setMargin(msgFechaFinal, new Insets(0, 20, 0, 20));
        GridPane.setMargin(boxTipo, new Insets(0, 20, 0, 20));
        GridPane.setMargin(tChecker, new Insets(20, 20, 0, 20));
        GridPane.setMargin(msgPrecio, new Insets(0, 20, 0, 20));
        GridPane.setMargin(txtPrecio, new Insets(0, 20, 0, 20));
        GridPane.setMargin(msgGastos, new Insets(0, 20, 0, 20));
        GridPane.setMargin(txtGastos, new Insets(0, 20, 25, 20));

        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);

        scene.getStylesheets().add(
                getClass().getResource("/com/elsantigestion/css/formulario.css").toExternalForm());

    }
    
    private void actualizarMontoFinal() {
        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            double gastos = Double.parseDouble(txtGastos.getText());
            double montoFinal = precio - gastos;
            txtMontoFinal.setText(String.valueOf(montoFinal));
        } catch (NumberFormatException e) {
        }
    }
}