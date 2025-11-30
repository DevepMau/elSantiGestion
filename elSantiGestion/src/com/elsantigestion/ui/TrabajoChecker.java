package com.elsantigestion.ui;

import java.util.HashMap;
import java.util.List;

import com.elsantigestion.model.Trabajo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class TrabajoChecker extends HBox {
	
	private TableView<Trabajo> tabla;
	private ObservableList<Trabajo> iniciales;
	private HashMap<Integer, Integer> listaAuxiliar;
	private HashMap<Integer, Integer> marcados;
	
	public TrabajoChecker(List<Trabajo> trabajosRegistrados, HashMap<Integer, Integer> trabajosPrevios) {
		
		this.iniciales = FXCollections.observableArrayList(trabajosRegistrados);
		this.tabla = new TableView<>();
		if(trabajosPrevios != null) {
			this.marcados = trabajosPrevios;
			this.listaAuxiliar = trabajosPrevios;
		}
		else {
			this.marcados = new HashMap<>();
			this.listaAuxiliar = new HashMap<>();
		}
		
		TableColumn<Trabajo, String> colNombre = new TableColumn<>("Nombre");
		TableColumn<Trabajo, Integer> colCantidad = new TableColumn<>("Cantidad");
		TableColumn<Trabajo, Void> colAccion = new TableColumn<>("");

		Callback<TableColumn<Trabajo, Integer>, TableCell<Trabajo, Integer>> cellSpinner = new Callback<>() {
			@Override
			public TableCell<Trabajo, Integer> call(final TableColumn<Trabajo, Integer> param) {
				return new TableCell<>() {
					private final Spinner<Integer> spinner = new Spinner<>(1, 100, 1);
					
					{
						spinner.valueProperty().addListener((obs, oldVal, newVal) -> {
						    if (newVal != null && !newVal.equals(oldVal)) {
						    	
						    	Trabajo trabajo = getTableView().getItems().get(getIndex());
						    	int id = trabajo.getId();
						    	
						    	if(marcados.containsKey(id)) {
						    		marcados.put(id, spinner.getValue());
						    	}
						    	else {
						    		listaAuxiliar.put(id, spinner.getValue());
						    	}
						    }
						});
					}
					
					@Override
		            protected void updateItem(Integer item, boolean empty) {
		                super.updateItem(item, empty);
		                if (empty) {
		                    setGraphic(null);
		                } else {
		                	if(marcados != null) {
		                		
		                		Trabajo trabajo = getTableView().getItems().get(getIndex());
		                		int id = trabajo.getId();
		                		
		                		if(marcados.containsKey(id)) {
		                			spinner.getValueFactory().setValue(marcados.get(id));
		                		}
		                	}
		                	
		                    setGraphic(spinner);
		                    setStyle("-fx-alignment: CENTER;");
		                }
		            }
				};
			}
		};
		
		Callback<TableColumn<Trabajo, Void>, TableCell<Trabajo, Void>> cellFactory = new Callback<>() {
		    @Override
		    public TableCell<Trabajo, Void> call(final TableColumn<Trabajo, Void> param) {
		        return new TableCell<>() {
		            private final CheckBox checkBox = new CheckBox();

		            {
		                checkBox.setOnAction(event -> {
		                	
		                    boolean seleccionado = checkBox.isSelected();
		                    Trabajo trabajo = getTableView().getItems().get(getIndex());
		                    int id = trabajo.getId();
		                    
		                    if(seleccionado) {
		                    	if(listaAuxiliar.containsKey(id)) {
		                    		marcados.put(id, listaAuxiliar.get(id));
		                    	}
		                    	else {
		                    		marcados.put(id, 1);
		                    	}
		                    }
		                    else {
		                    	marcados.remove(id);
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
		                    int id = trabajo.getId();
		                    
		                    if (listaAuxiliar != null) {
		                    	
		                    	if(listaAuxiliar.containsKey(id)) {
		                    		
		                    		checkBox.setSelected(true);
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
		
		colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
		colCantidad.setCellFactory(cellSpinner);
		colAccion.setCellFactory(cellFactory);
		
		colNombre.setMinWidth(150);
		colCantidad.setMinWidth(60);
		colAccion.setMinWidth(50);
		
		colNombre.getStyleClass().add("columna-personalizada");
		colCantidad.getStyleClass().add("columna-personalizada");
		colAccion.getStyleClass().add("columna-personalizada");
		
		tabla.getColumns().add(colCantidad);
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
	
	public HashMap<Integer, Integer> obtenerTrabajosAAgregar() {
        return this.marcados;
    }
	
	public void cargarTrabajosPrevios(HashMap<Integer, Integer> map) {
		this.marcados = map;
		this.listaAuxiliar = map;
	}

}
