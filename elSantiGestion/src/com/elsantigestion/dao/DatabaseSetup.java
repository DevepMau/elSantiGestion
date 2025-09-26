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
                    "localidad TEXT," +
                    "direccion TEXT," +
                    "email TEXT," +
                    "activo INTEGER DEFAULT 1" +
                    ");";

            stmt.execute(sqlClientes);
            
            String sqlTrabajos = "CREATE TABLE IF NOT EXISTS trabajos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "detalle TEXT," +
                    "precio REAL NOT NULL DEFAULT 0," +
                    "unidad TEXT NOT NULL DEFAULT 'unidad' CHECK (unidad IN ('unidad','m2','hora','paquete','otro'))," +
                    "activo INTEGER NOT NULL DEFAULT 1" +
                    ");";

            stmt.execute(sqlTrabajos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 }