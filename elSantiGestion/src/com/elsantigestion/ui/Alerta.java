// Alerta.java
package com.elsantigestion.ui;

import com.elsantigestion.dao.ClienteDAO;
import com.elsantigestion.model.Cliente;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Alerta {

    private double xOffset = 0;
    private double yOffset = 0;

    public Alerta(String titulo, Cliente cliente, ClienteDAO dao, Runnable refrescarTabla) {
        Stage alertStage = new Stage();
        alertStage.initStyle(StageStyle.UNDECORATED);

        // Barra superior
        Label nombreApp = new Label(titulo);
        Button btnCerrar = new Button("X");
        btnCerrar.setOnAction(e -> alertStage.close());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox barraSuperior = new HBox(nombreApp, spacer, btnCerrar);
        barraSuperior.setAlignment(Pos.CENTER_LEFT); // mantiene centrado verticalmente
        barraSuperior.setPadding(new Insets(0, 0, 0, 10)); // top, right, bottom, left
        HBox.setHgrow(spacer, Priority.ALWAYS); // el spacer empuja el botón a la derecha
        barraSuperior.getStyleClass().add("barra-superior");

        // Mover ventana
        barraSuperior.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        barraSuperior.setOnMouseDragged(e -> {
            alertStage.setX(e.getScreenX() - xOffset);
            alertStage.setY(e.getScreenY() - yOffset);
        });

        // Contenido
        Label mensaje = new Label("¿Quieres eliminar al cliente?");
        Label nombreCliente = new Label(cliente.getNombre());
        nombreCliente.getStyleClass().add("nombreCliente");
        
        VBox contenido = new VBox(mensaje, nombreCliente);
        contenido.setSpacing(15);
        contenido.setAlignment(Pos.CENTER);

        // Botón confirmar
        Button btnConfirmar = new Button("Eliminar");
        btnConfirmar.getStyleClass().add("btnConfirmar");
        btnConfirmar.setOnAction(e -> {
            dao.eliminarCliente(cliente.getId());
            refrescarTabla.run();
            alertStage.close();
        });
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> alertStage.close());
        
        HBox botonesBox = new HBox(btnConfirmar, btnCancelar);
        
        VBox vbox = new VBox(barraSuperior, contenido, botonesBox);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("vbox");
        vbox.getStylesheets().add(getClass().getResource("/com/elsantigestion/css/alerta.css").toExternalForm());

        Scene scene = new Scene(vbox);
        alertStage.setScene(scene);
        alertStage.show();
    }
}
