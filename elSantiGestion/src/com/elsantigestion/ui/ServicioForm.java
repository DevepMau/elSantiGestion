package com.elsantigestion.ui;

import java.time.LocalDate;
import java.util.List;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.dao.ServicioDAO;
import com.elsantigestion.model.Cliente;
import com.elsantigestion.model.Servicio;

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
    private double xOffset;
    private double yOffset;
    private Label titulo;
    private Label lblFechaFinal;
    private Label lblPrecio;
    private Label lblGastos;
    private Label lblMontoFinal;
    private Label lblPeriodisidad;
    private ComboBox<String> cmbPeriodisidad;
    private ComboBox<Cliente> cmbCliente;
    private ComboBox<String> cmbEstado;
    private Button btnGuardar;
    private Button btnCerrar;
    private Button btnTrabajos;
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
    private HBox boxFechaFinal;
    private HBox boxMontoFinal;
    private HBox boxPeriodisidad;

    @SuppressWarnings("exports") 
    public ServicioForm(ServicioDAO dao, Runnable onSaveCallBack, Servicio servicioExistente) {

        this.dao = dao;
        this.onSaveCallBack = onSaveCallBack;
        this.xOffset = 0;
        this.yOffset = 0;
        this.initStyle(StageStyle.UNDECORATED);

        clienteDao = new ClienteDAO();
        grid = new GridPane();
        btnGuardar = new Button("Guardar");
        btnCerrar = new Button("X");
        btnTrabajos = new Button("Trabajos");
        spacer = new Region();
        spacerFechaFinal = new Region();
        spacerPrecio = new Region();
        spacerMontoFinal = new Region();
        spacerGastos = new Region();
        spacerPeriodisidad = new Region();
        lblFechaFinal = new Label("Fecha programada: ");
        lblPrecio = new Label("Precio:");
        lblGastos = new Label("Costos/insumos:");
        lblMontoFinal = new Label("Monto a cobrar:");
        lblPeriodisidad = new Label("Tipo de servicio:");
        dpFechaProgramada = new DatePicker();
        barraSuperior = new HBox();
        boxFechaFinal = new HBox();
        boxPrecio = new HBox();
        boxGastos = new HBox();
        boxMontoFinal = new HBox();
        boxPeriodisidad = new HBox();
        txtPrecio = new TextField();
        txtGastos = new TextField();
        txtMontoFinal = new TextField();
        scene = new Scene(grid, 400, 650);
        
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
        
        txtPrecio.setMaxWidth(150);
        txtGastos.setMaxWidth(150);
        txtMontoFinal.setMaxWidth(150);
        cmbPeriodisidad.setMaxWidth(200);
        
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
        } else {
            titulo = new Label("Agregar servicio");
        }
        
        boxFechaFinal.setAlignment(Pos.TOP_RIGHT);
        boxFechaFinal.setSpacing(10);
        boxFechaFinal.getChildren().addAll(lblFechaFinal, spacerFechaFinal, dpFechaProgramada);
        
        boxPrecio.setAlignment(Pos.TOP_RIGHT);
        boxPrecio.setSpacing(10);
        boxPrecio.getChildren().addAll(lblPrecio, spacerPrecio, txtPrecio);
        
        boxGastos.setAlignment(Pos.TOP_RIGHT);
        boxGastos.setSpacing(10);
        boxGastos.getChildren().addAll(lblGastos, spacerGastos, txtGastos);
        
        boxMontoFinal.setAlignment(Pos.TOP_RIGHT);
        boxMontoFinal.setSpacing(10);
        boxMontoFinal.getChildren().addAll(lblMontoFinal, spacerMontoFinal, txtMontoFinal);
        
        boxPeriodisidad.setAlignment(Pos.TOP_RIGHT);
        boxPeriodisidad.setSpacing(10);
        boxPeriodisidad.getChildren().addAll(lblPeriodisidad, spacerPeriodisidad, cmbPeriodisidad);
        
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

        btnGuardar.getStyleClass().add("boton");
        btnGuardar.setOnAction(e -> {
            
        	Cliente clienteSeleccionado = cmbCliente.getValue();
        	
        	if(dpFechaProgramada.getValue() == null) {
        		dpFechaProgramada.setValue(LocalDate.now());
        	}

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
                dao.agregarServicio(nuevo);
            } else {
            	servicioExistente.setClienteId(clienteSeleccionado.getId());
                servicioExistente.setFechaProgramada(dpFechaProgramada.getValue());
                servicioExistente.setTipo(cmbPeriodisidad.getValue());
                servicioExistente.setPrecio(Double.parseDouble(txtPrecio.getText()));
                servicioExistente.setGastos(Double.parseDouble(txtGastos.getText()));
                servicioExistente.setMontoFinal(Double.parseDouble(txtMontoFinal.getText()));
                servicioExistente.setEstado(cmbEstado.getValue());
               
                dao.actualizarServicio(servicioExistente);

            }

            if (onSaveCallBack != null) {
                onSaveCallBack.run();
            }
            close();
        });

        btnCerrar.setOnAction(e -> close());
        
        btnTrabajos.setOnAction(e -> {
        	TrabajoManager tm = new TrabajoManager();
        	tm.showAndWait();
        });

        // LAYOUT //////////////////////////////

        grid.getStyleClass().add("formulario");
        grid.setPadding(new Insets(10));
        grid.add(barraSuperior, 0, 0);
        grid.addRow(1, cmbCliente);
        grid.addRow(2, boxPeriodisidad);
        grid.addRow(3, boxFechaFinal);
        grid.addRow(4, btnTrabajos);
        grid.addRow(5, boxPrecio);
        grid.addRow(6, boxGastos);
        grid.addRow(7, boxMontoFinal);
        grid.add(btnGuardar, 0, 8);
        GridPane.setMargin(cmbCliente, new Insets(30, 20, 10, 20));
        GridPane.setMargin(boxFechaFinal, new Insets(0, 20, 0, 20));
        GridPane.setMargin(boxPeriodisidad, new Insets(0, 20, 0, 20));
        GridPane.setMargin(btnTrabajos, new Insets(0, 20, 0, 20));
        GridPane.setMargin(boxPrecio, new Insets(0, 20, 0, 20));
        GridPane.setMargin(boxGastos, new Insets(0, 20, 0, 20));
        GridPane.setMargin(boxMontoFinal, new Insets(0, 20, 25, 20));

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