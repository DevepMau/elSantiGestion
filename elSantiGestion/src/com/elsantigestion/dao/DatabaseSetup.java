package com.elsantigestion.dao;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {
    public static void init() {
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {

            // Tabla de clientes
            String sqlClientes = "CREATE TABLE IF NOT EXISTS clientes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "telefono TEXT," +
                    "email TEXT" +
                    ");";

            stmt.execute(sqlClientes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}