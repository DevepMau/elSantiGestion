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
        		    "email TEXT," +
        		    "barrio_privado INTEGER DEFAULT 0," +
        		    "barrio_nombre TEXT," +
        		    "barrio_lote INTEGER," +
        		    "localidad TEXT," +
        		    "direccion TEXT," +
        		    "activo INTEGER DEFAULT 1," +
        		    "fecha_creacion DATE NOT NULL" +
        		    ");";

        	stmt.execute(sqlClientes);
            
            String sqlTrabajos = "CREATE TABLE IF NOT EXISTS trabajos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "detalle TEXT," +
                    "precio REAL NOT NULL DEFAULT 0," +
                    "unidad TEXT NOT NULL," +
                    "activo INTEGER NOT NULL DEFAULT 1" +
                    ");";

            stmt.execute(sqlTrabajos);
            
            String sqlServicios = "CREATE TABLE IF NOT EXISTS servicios (" +
            	    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            	    "cliente_id INT NOT NULL, "+
            	    "fecha_creacion DATE NOT NULL," +
            	    "fecha_programada DATE NOT NULL," +
            	    "tipo TEXT," +
            	    "precio REAL NOT NULL DEFAULT 0," +
            	    "gastos REAL NOT NULL DEFAULT 0," +
            	    "monto_final REAL NOT NULL DEFAULT 0," +
            	    "estado TEXT," +
            	    "FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE" +
            	    ");";
            
            stmt.execute(sqlServicios);
            
            String sqlServicioTrabajos = "CREATE TABLE IF NOT EXISTS servicio_trabajos (" +
            	    "servicio_id INT NOT NULL," +
            	    "trabajo_id INT NOT NULL," +
            	    "cantidad INT NOT NULL DEFAULT 1," +
            	    "PRIMARY KEY (servicio_id, trabajo_id)," +
            	    "FOREIGN KEY (servicio_id) REFERENCES servicios(id) ON DELETE CASCADE," +
            	    "FOREIGN KEY (trabajo_id) REFERENCES trabajos(id) ON DELETE CASCADE" +
            	    ");";
            
            stmt.execute(sqlServicioTrabajos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 }