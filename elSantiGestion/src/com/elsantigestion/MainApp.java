package com.elsantigestion;

import com.elsantigestion.dao.DatabaseSetup;
import com.elsantigestion.ui.ClienteView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
    	
    	DatabaseSetup.init();
        // Botones del menú lateral
        Button btnClientes = new Button("Clientes");
        Button btnPedidos = new Button("Pedidos");
        Button btnGastos = new Button("Gastos");
        Button btnCronograma = new Button("Cronograma");
        Button btnReportes = new Button("Reportes");
        btnClientes.getStyleClass().add("menu-boton");
        btnPedidos.getStyleClass().add("menu-boton");
        btnGastos.getStyleClass().add("menu-boton");
        btnCronograma.getStyleClass().add("menu-boton");
        btnReportes.getStyleClass().add("menu-boton");

        VBox menuLateral = new VBox(10, btnClientes, btnPedidos, btnGastos, btnCronograma, btnReportes);
        menuLateral.getStyleClass().add("menu-lateral");

        
        // Panel principal donde se cargan las vistas
        BorderPane root = new BorderPane();
        root.setLeft(menuLateral);

        // Ejemplo: cuando clickeo "Clientes", muestro texto (luego será ClienteApp/ClienteView)
        btnClientes.setOnAction(e -> root.setCenter(new ClienteView()));
        btnPedidos.setOnAction(e -> root.setCenter(new javafx.scene.control.Label("Vista de Pedidos")));

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("ui/estilos.css").toExternalForm());
        stage.setTitle("Sistema de Gestión");
        stage.setScene(scene);
        stage.show();
        
        
    }

    public static void main(String[] args) {
        launch();
    }
}