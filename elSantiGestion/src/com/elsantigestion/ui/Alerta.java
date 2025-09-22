package com.elsantigestion.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.util.Optional;

public class Alerta {

    public static void info(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
            Alerta.class.getResource("/com/elsantigestion/css/alerta.css").toExternalForm()
        );
        
        alert.showAndWait();

    }

    public static void warning(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
            Alerta.class.getResource("/com/elsantigestion/css/alerta.css").toExternalForm()
        );
        
        alert.showAndWait();
        
    }

    public static boolean confirmar(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
            Alerta.class.getResource("/com/elsantigestion/css/alerta.css").toExternalForm()
        );

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
