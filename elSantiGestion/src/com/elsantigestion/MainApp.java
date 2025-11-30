package com.elsantigestion;

import com.elsantigestion.controller.ClienteController;
import com.elsantigestion.controller.ServicioController;
import com.elsantigestion.controller.TrabajoController;
import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.dao.DatabaseSetup;
import com.elsantigestion.dao.ServicioDAO;
import com.elsantigestion.dao.ServicioTrabajoDAO;
import com.elsantigestion.dao.TrabajoDAO;
import com.elsantigestion.ui.ClienteView;
import com.elsantigestion.ui.ServicioView;
import com.elsantigestion.ui.TrabajoView;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
	
	private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) {
        DatabaseSetup.init();
        stage.initStyle(StageStyle.UNDECORATED);

        // Logo
        Image logo = new Image(getClass().getResource("/logo/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(150);
        logoView.setPreserveRatio(true);

        // Botones del menú lateral
        Button btnClientes = new Button("Clientes");
        Button btnTrabajos = new Button("Trabajos");
        Button btnServicios = new Button("Servicios");
        Button btnGastos = new Button("Gastos");
        Button btnCronograma = new Button("Cronograma");
        Button btnReportes = new Button("Reportes");

        Button[] botones = {btnClientes, btnTrabajos, btnServicios, btnGastos, btnCronograma, btnReportes};

        // Barra superior
        Button btnCerrar = new Button("X");
        btnCerrar.setOnAction(e -> stage.close());
        
        Button btnMaximizar = new Button("⬜");
        btnMaximizar.setOnAction(e -> stage.setMaximized(!stage.isMaximized()));
        
        HBox botonesBox = new HBox();
        botonesBox.getChildren().addAll(btnMaximizar, btnCerrar);
        
        Label nombreApp = new Label("El Santi Gestion v1");
        
        Region spacer = new Region();
        
        HBox barraSuperior = new HBox();
        barraSuperior.setAlignment(Pos.TOP_RIGHT);
        barraSuperior.setSpacing(10);
        barraSuperior.getChildren().addAll(nombreApp, spacer, botonesBox);
	    HBox.setMargin(nombreApp, new Insets(15, 10, 0, 10));
	    HBox.setHgrow(spacer, Priority.ALWAYS);
	    barraSuperior.getStyleClass().add("barra-superior");
	    
	    barraSuperior.setOnMousePressed(event -> {
	        // Guardamos la posición del mouse cuando se hace clic
	        xOffset = event.getSceneX();
	        yOffset = event.getSceneY();
	    });
	    
	    barraSuperior.setOnMouseDragged(event -> {
	        // Movemos la ventana según la posición del mouse
	        barraSuperior.getScene().getWindow().setX(event.getScreenX() - xOffset);
	        barraSuperior.getScene().getWindow().setY(event.getScreenY() - yOffset);
	    });
        
        // Clase común para todos los botones
        for (Button btn : botones) {
            btn.getStyleClass().add("menu-boton");
        }

        VBox menuLateral = new VBox(10, logoView, btnClientes, btnTrabajos, btnServicios, btnGastos, btnCronograma, btnReportes);
        menuLateral.getStyleClass().add("menu-lateral");

        // Panel principal
        BorderPane root = new BorderPane();
        root.setTop(barraSuperior);
        root.setLeft(menuLateral);
        root.getStyleClass().add("panel");
        
        ClienteView clienteView = new ClienteView();
        ClienteDAO clienteDao = new ClienteDAO();
        ClienteController clientes = new ClienteController(clienteView, clienteDao);
        
        TrabajoView trabajoView = new TrabajoView();
        TrabajoDAO trabajoDao = new TrabajoDAO();
        TrabajoController trabajos = new TrabajoController(trabajoView, trabajoDao);  
        
        ServicioView servicioView = new ServicioView();
        ServicioDAO servicioDao = new ServicioDAO();
        ServicioTrabajoDAO serTraDao = new ServicioTrabajoDAO();
        ServicioController servicios = new ServicioController(servicioView, servicioDao, serTraDao, clienteDao.obtenerClientes(), trabajoDao.obtenerTrabajos());
        

        // Función para manejar selección de botón
        for (Button btn : botones) {
            btn.setOnAction(e -> {
                // Quitar estado "activo" de todos
                for (Button b : botones) b.getStyleClass().remove("activo");
                // Marcar el botón clickeado como activo
                btn.getStyleClass().add("activo");

                // Cambiar el contenido central
                if (btn == btnClientes) root.setCenter(clientes.getView());
                else if (btn == btnTrabajos) root.setCenter(trabajos.getView());
                else if (btn == btnServicios) root.setCenter(servicios.getView());
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
