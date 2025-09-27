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
            
            String sqlServicioEventual = "CREATE TABLE servicio_eventual (" +
            	    "id INT PRIMARY KEY," +
            	    "fecha_creacion DATE NOT NULL," +
            	    "fecha_programada DATE NOT NULL," +
            	    "cliente_id INT NOT NULL, "+
            	    "precio INT NOT NULL," +
            	    "gastos INT NOT NULL," +
            	    "monto_final INT NOT NULL," +
            	    "FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE" +
            	    ");";
            
            stmt.execute(sqlServicioEventual);
            
            String sqlServicioEventualtrabajos = "CREATE TABLE IF NOT EXISTS servicio_eventual_trabajos (" +
            	    "servicio_eventual_id INT NOT NULL," +
            	    "trabajo_id INT NOT NULL," +
            	    "cantidad INT NOT NULL DEFAULT 1," +
            	    "PRIMARY KEY (servicio_eventual_id, trabajo_id)," +
            	    "FOREIGN KEY (servicio_eventual_id) REFERENCES ServicioEventual(id) ON DELETE CASCADE," +
            	    "FOREIGN KEY (trabajo_id) REFERENCES trabajos(id) ON DELETE CASCADE" +
            	    ");";
            
            stmt.execute(sqlServicioEventualtrabajos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 }