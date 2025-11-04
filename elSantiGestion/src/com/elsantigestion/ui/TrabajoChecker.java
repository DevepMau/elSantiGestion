package com.elsantigestion.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.elsantigestion.dao.TrabajoDAO;
import com.elsantigestion.model.Trabajo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class TrabajoChecker extends HBox {
	
	//private final int iconoTamaño = 15;
	
	private TrabajoDAO dao;
	private TableView<Trabajo> tabla;
	private ObservableList<Trabajo> iniciales;
	private HashMap<Integer, Trabajo> marcados;
	private List<Integer> previos;
	
	public TrabajoChecker(List<Integer> idsPrevios) {
		
		this.dao = new TrabajoDAO();
		this.iniciales = FXCollections.observableArrayList(dao.obtenerTrabajos());
		this.tabla = new TableView<>();
		this.marcados = new HashMap<>();
		this.previos = idsPrevios;
		
		TableColumn<Trabajo, Integer> colId = new TableColumn<>("ID");
		TableColumn<Trabajo, String> colNombre = new TableColumn<>("Nombre");
		TableColumn<Trabajo, Void> colAccion = new TableColumn<>("");

		Callback<TableColumn<Trabajo, Void>, TableCell<Trabajo, Void>> cellFactory = new Callback<>() {
		    @Override
		    public TableCell<Trabajo, Void> call(final TableColumn<Trabajo, Void> param) {
		        return new TableCell<>() {
		            private final CheckBox checkBox = new CheckBox();

		            {
		                checkBox.setOnAction(event -> {
		                    Trabajo trabajo = getTableView().getItems().get(getIndex());
		                    boolean seleccionado = checkBox.isSelected();
		                    if(seleccionado) {
		                    	marcados.put(trabajo.getId(), trabajo);
		                    }
		                    else {
		                    	marcados.remove(trabajo.getId());
		                    }
		                });
		            }

		            @Override
		            protected void updateItem(Void item, boolean empty) {
		                super.updateItem(item, empty);
		                if (empty) {
		                    setGraphic(null);
		                } else {
		                    Trabajo trabajo = getTableView().getItems().get(getIndex());
		                    // Marcar el checkbox si el trabajo está en la lista previos
		                    if (previos != null) {
		                    	if(previos.contains(trabajo.getId())) {
		                    		checkBox.setSelected(true);
			                        marcados.put(trabajo.getId(), trabajo);
		                    	}  
		                    } else {
		                        checkBox.setSelected(false);
		                    }

		                    setGraphic(checkBox);
		                    setStyle("-fx-alignment: CENTER;");
		                }
		            }
		        };
		    }
		};
		
		colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getId()).asObject());
		colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
		colAccion.setCellFactory(cellFactory);
		
		colId.setMinWidth(50);
		colNombre.setMinWidth(200);
		colAccion.setMinWidth(50);
		
		colId.getStyleClass().add("columna-personalizada");
		colNombre.getStyleClass().add("columna-personalizada");
		colAccion.getStyleClass().add("columna-personalizada");
		
		tabla.getColumns().add(colId);
		tabla.getColumns().add(colNombre);
		tabla.getColumns().add(colAccion);
		tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tabla.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(tabla, javafx.scene.layout.Priority.ALWAYS);
		
		this.tabla.setItems(iniciales);
		
		this.getChildren().addAll(tabla);
		
		this.getStylesheets().add(
    	        getClass().getResource("/com/elsantigestion/css/trabajoChecker.css").toExternalForm()
    	   );
		
	}
	
	@SuppressWarnings("exports")
	public List<Trabajo> obtenerTrabajos() {
        return new ArrayList<>(marcados.values());
    }

}
