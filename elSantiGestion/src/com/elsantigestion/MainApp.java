package com.elsantigestion;

import com.elsantigestion.dao.DatabaseSetup;
import com.elsantigestion.ui.ClienteView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        DatabaseSetup.init();

        // Logo
        Image logo = new Image(getClass().getResource("/logo/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(150);
        logoView.setPreserveRatio(true);

        // Botones del menú lateral
        Button btnClientes = new Button("Clientes");
        Button btnPedidos = new Button("Pedidos");
        Button btnGastos = new Button("Gastos");
        Button btnCronograma = new Button("Cronograma");
        Button btnReportes = new Button("Reportes");

        Button[] botones = {btnClientes, btnPedidos, btnGastos, btnCronograma, btnReportes};

        // Clase común para todos los botones
        for (Button btn : botones) {
            btn.getStyleClass().add("menu-boton");
        }

        VBox menuLateral = new VBox(10, logoView, btnClientes, btnPedidos, btnGastos, btnCronograma, btnReportes);
        menuLateral.getStyleClass().add("menu-lateral");

        // Panel principal
        BorderPane root = new BorderPane();
        root.setLeft(menuLateral);

        // Función para manejar selección de botón
        for (Button btn : botones) {
            btn.setOnAction(e -> {
                // Quitar estado "activo" de todos
                for (Button b : botones) b.getStyleClass().remove("activo");
                // Marcar el botón clickeado como activo
                btn.getStyleClass().add("activo");

                // Cambiar el contenido central
                if (btn == btnClientes) root.setCenter(new ClienteView());
                else if (btn == btnPedidos) root.setCenter(new javafx.scene.control.Label("Vista de Pedidos"));
                else if (btn == btnGastos) root.setCenter(new javafx.scene.control.Label("Vista de Gastos"));
                else if (btn == btnCronograma) root.setCenter(new javafx.scene.control.Label("Vista de Cronograma"));
                else if (btn == btnReportes) root.setCenter(new javafx.scene.control.Label("Vista de Reportes"));
            });
        }

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("css/general.css").toExternalForm());
        stage.setTitle("Sistema de Gestión");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
