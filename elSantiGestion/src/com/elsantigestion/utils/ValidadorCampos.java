package com.elsantigestion.utils;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    
    public static boolean esSeleccionValida(ComboBox<?> combo, Label infoCampo) {
        if (combo.getValue() == null) {
            infoCampo.setText("Debe seleccionar una opción.");
            resaltarErrorCombo(combo);
            return false;
        }
        infoCampo.setText("");
        limpiarEstiloCombo(combo);
        return true;
    }
    
    public static boolean esFechaValida(DatePicker picker, Label infoCampo) {
        if (picker.getValue() == null) {
            infoCampo.setText("Debe seleccionar una fecha.");
            resaltarErrorDatePicker(picker);
            return false;
        }
        infoCampo.setText("");
        limpiarEstiloDatePicker(picker);
        return true;
    }
    
    /////////////////////////////////////
    
    private static void resaltarError(TextField campo) {
        campo.setStyle("-fx-border-color: #FF1A1A; -fx-border-width: 2;");
    }

    private static void limpiarEstilo(TextField campo) {
        campo.setStyle("");
    }
    
    private static void resaltarErrorCombo(ComboBox<?> combo) {
        combo.setStyle("-fx-border-color: #FF1A1A; -fx-border-width: 2;");
    }

    private static void limpiarEstiloCombo(ComboBox<?> combo) {
        combo.setStyle("");
    }
    
    private static void resaltarErrorDatePicker(DatePicker picker) {
        picker.setStyle("-fx-border-color: #FF1A1A; -fx-border-width: 2;");
    }

    private static void limpiarEstiloDatePicker(DatePicker picker) {
        picker.setStyle("");
    }
    
}
