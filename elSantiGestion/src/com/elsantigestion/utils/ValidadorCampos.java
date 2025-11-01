package com.elsantigestion.utils;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ValidadorCampos {
    
    public static boolean esNoVacio(TextField campo, Label infoCampo) {
        if (campo.getText().trim().isEmpty()) {
        	infoCampo.setText("El campo no puede estar vacio.");
            resaltarError(campo);
            return false;
        }
        infoCampo.setText("");
        limpiarEstilo(campo);
        return true;
    }

    public static boolean esNumerico(TextField campo, Label infoCampo) {
        if (!campo.getText().matches("\\d+([\\.]\\d+)?")) {
        	infoCampo.setText("El campo solo debe contener numeros.");
            resaltarError(campo);
            return false;
        }
        infoCampo.setText("");
        limpiarEstilo(campo);
        return true;
    }

    public static boolean esEmail(TextField campo, Label infoCampo) {
        if (!campo.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            infoCampo.setText("El correo electrónico ingresado no es válido.");
            resaltarError(campo);
            return false;
        }
        infoCampo.setText("");
        limpiarEstilo(campo);
        return true;
    }
    
    public static boolean longitudEntre(TextField campo, int min, int max, Label infoCampo) {
        int len = campo.getText().trim().length();
        if (len < min || len > max) {
        	infoCampo.setText("El campo debe tener entre " + min + " y " + max + " caracteres.");
            resaltarError(campo);
            return false;
        }
        infoCampo.setText("");
        limpiarEstilo(campo);
        return true;
    }
    
    /////////////////////////////////////

    
    /*private static void mostrarAlerta(String mensaje) {
    	Alerta.warning("Validacion de formulario", mensaje);
    }*/
    
    private static void resaltarError(TextField campo) {
        campo.setStyle("-fx-border-color: #FF1A1A; -fx-border-width: 2;");
    }

    private static void limpiarEstilo(TextField campo) {
        campo.setStyle("");
    }
    
}
