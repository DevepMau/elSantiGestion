package com.elsantigestion;

import com.elsantigestion.dao.DatabaseSetup;

public class Main {
    public static void main(String[] args) {
        // Inicializar DB
        DatabaseSetup.init();
        System.out.println("Base de datos lista!");
    }
}