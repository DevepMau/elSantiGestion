package com.elsantigestion.ui;

import java.time.LocalDate;

import com.elsantigestion.model.Cliente;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class ClienteView extends VBox {
	
	private final int iconoTamaño = 50;
	private final String TITULO_INACTIVOS = "Tabla de clientes archivados e inactivos";
	private final String TITULO_ACTIVOS = "Tabla de clientes activos";
	
	private TableView<Cliente> tabla;
	private Button btnNuevo;
	private Button btnModificar;
	private Button btnEliminar;
	private Button btnSwap;
	
	private Image agregar;
	private Image modificar;
	private Image eliminar;
	private Image swap;
	private Image recover;
	private ImageView iconoAgregar;
	private ImageView iconoModificar;
	private ImageView iconoEliminar;
	private ImageView iconoSwap;
	private ImageView iconoRecover;
	private Tooltip tooltipNuevo;
	private Tooltip tooltipModificar;
	private Tooltip tooltipEliminar;
	private Tooltip tooltipSwap;
	private Tooltip tooltipRecover;
	private HBox barraAcciones;
	private HBox footer;
	private Label lblFooter;

    public ClienteView() {
    	
		tabla = new TableView<>();
		btnNuevo = new Button();
		btnModificar = new Button();
		btnEliminar = new Button();
		btnSwap = new Button();
		
		agregar = new Image(getClass().getResource("/iconos/cliente.png").toExternalForm());
		modificar = new Image(getClass().getResource("/iconos/edit.png").toExternalForm());
		eliminar = new Image(getClass().getResource("/iconos/delete.png").toExternalForm());
		swap = new Image(getClass().getResource("/iconos/table-swap.png").toExternalForm());
		recover = new Image(getClass().getResource("/iconos/recover.png").toExternalForm());
		iconoAgregar = new ImageView(agregar);
		iconoModificar = new ImageView(modificar);
		iconoEliminar = new ImageView(eliminar);
		iconoSwap = new ImageView(swap);
		iconoRecover = new ImageView(recover);
		tooltipNuevo = new Tooltip("Agregar cliente");
		tooltipModificar = new Tooltip("Modificar cliente");
		tooltipEliminar = new Tooltip("Archivar cliente");
		tooltipSwap = new Tooltip("Cambiar tabla");
		tooltipRecover = new Tooltip("Recuperar cliente");
		lblFooter = new Label(TITULO_ACTIVOS);
		barraAcciones = new HBox(2, btnSwap, btnEliminar, btnModificar, btnNuevo);
		footer = new HBox(lblFooter);
		iconoAgregar.setFitWidth(iconoTamaño);
		iconoAgregar.setFitHeight(iconoTamaño);
		iconoModificar.setFitWidth(iconoTamaño);
		iconoModificar.setFitHeight(iconoTamaño);
		iconoEliminar.setFitWidth(iconoTamaño);
		iconoEliminar.setFitHeight(iconoTamaño);
		iconoSwap.setFitWidth(iconoTamaño);
		iconoSwap.setFitHeight(iconoTamaño);
		iconoRecover.setFitWidth(iconoTamaño);
		iconoRecover.setFitHeight(iconoTamaño);
		
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
        
		TableColumn<Cliente, LocalDate> colFechaCreacion = new TableColumn<>("Fecha de\nRegistro");
        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Teléfono");
        TableColumn<Cliente, String> colLocalidad = new TableColumn<>("Localidad");
        TableColumn<Cliente, String> colDireccion = new TableColumn<>("Direccion");
        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        TableColumn<Cliente, Boolean> colBarrioPrivado = new TableColumn<>("Barrio\nPrivado");
        TableColumn<Cliente, String> colBarrioNombre = new TableColumn<>("Nombre del Barrio");
        TableColumn<Cliente, Number> colBarrioLote = new TableColumn<>("N°.\nLote");
        TableColumn<Cliente, String> colColor = new TableColumn<>("Color");
        
        colFechaCreacion.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getFechaCreacion()));
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));
        colLocalidad.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLocalidad()));
        colDireccion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDireccion()));
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        colBarrioPrivado.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().isBarrioPrivado()));
        colBarrioNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBarrioNombre()));
        colBarrioLote.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getBarrioLote()));
        colColor.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getColor()));
        
        colFechaCreacion.getStyleClass().add("columna-especial");
        colNombre.getStyleClass().add("columna-texto");
        colTelefono.getStyleClass().add("columna-numero");
        colLocalidad.getStyleClass().add("columna-texto");
        colDireccion.getStyleClass().add("columna-texto");
        colEmail.getStyleClass().add("columna-texto");
        colBarrioPrivado.getStyleClass().add("columna-especial");
        colBarrioNombre.getStyleClass().add("columna-texto");
        colBarrioLote.getStyleClass().add("columna-especial");
        colColor.getStyleClass().add("columna-especial");
        
        colColor.setCellFactory(column -> new TableCell<Cliente, String>() {
            private final Pane colorPane = new Pane();

            {
                colorPane.setPrefSize(20, 20);
                colorPane.setStyle("-fx-border-color: grey;");
            }

            @Override
            protected void updateItem(String colorHex, boolean empty) {
                super.updateItem(colorHex, empty);

                if (empty || colorHex == null) {
                    setGraphic(null);
                } else {
                    colorPane.setStyle(
                        "-fx-background-color: " + colorHex + ";" +
                        "-fx-border-color: grey;"
                    );
                    setGraphic(colorPane);
                }
            }
        });

        colBarrioPrivado.setCellFactory(col -> new TableCell<Cliente, Boolean>() {
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    if (item) {
                        setText("Si");
                        setStyle("-fx-text-fill: green; -fx-padding: 0 0 -1 0;");
                    } else {
                        setText("No");
                        setStyle("-fx-text-fill: red; -fx-padding: 0 0 -1 0;");
                    }
                }
                setAlignment(Pos.CENTER);
            }
        });

        tabla.getColumns().add(colFechaCreacion);
        tabla.getColumns().add(colNombre);
        tabla.getColumns().add(colTelefono); 
        tabla.getColumns().add(colEmail);
        tabla.getColumns().add(colBarrioPrivado);
        tabla.getColumns().add(colLocalidad); 
        tabla.getColumns().add(colDireccion);  
        tabla.getColumns().add(colBarrioNombre); 
        tabla.getColumns().add(colBarrioLote);
        tabla.getColumns().add(colColor);
    	tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	tabla.setPrefWidth(Double.MAX_VALUE);
    	VBox.setVgrow(tabla, Priority.ALWAYS);
        
    	this.getChildren().addAll(barraAcciones, tabla, footer);
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/tabla.css").toExternalForm()
    	    );

		colFechaCreacion.setMinWidth(100);
        colNombre.setMinWidth(150);
        colTelefono.setMinWidth(100);
        colLocalidad.setMinWidth(100);
        colDireccion.setMinWidth(150);
        colEmail.setMinWidth(120); 
        colBarrioNombre.setMinWidth(150);
        colBarrioLote.setMinWidth(60);
        colBarrioPrivado.setMinWidth(60);
        colColor.setMinWidth(60);

    }
    
	public TableView<Cliente> getTabla(){
    	return tabla;
    }
	
	public void alternarIcono(Boolean boo) {
		if(boo) {
			btnEliminar.setGraphic(iconoEliminar);
			btnEliminar.setTooltip(tooltipEliminar);
		}
		else {
			btnEliminar.setGraphic(iconoRecover);
			btnEliminar.setTooltip(tooltipRecover);
		}
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

	public String getTITULO_INACTIVOS() {
		return TITULO_INACTIVOS;
	}

	public String getTITULO_ACTIVOS() {
		return TITULO_ACTIVOS;
	}
	
	public void setTituloDeTabla(Boolean boo) {
		if(boo) {
			this.lblFooter.setText(TITULO_ACTIVOS);
		}
		else {
			this.lblFooter.setText(TITULO_INACTIVOS);
		}
	}
        
}
