package com.elsantigestion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.elsantigestion.model.Servicio;
import com.elsantigestion.model.ServicioDetalles;
import com.elsantigestion.model.Trabajo;

public class ServicioDetallesDAO {
    
    // Insertar relación
	public void agregar(ServicioDetalles detalle) {
	    String sql = "INSERT INTO servicio_detalles (servicio_id, trabajo_id, cantidad, estado) VALUES (?, ?, ?, ?)";

	    try (Connection conn = Database.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, detalle.getServicio().getId());
	        pstmt.setInt(2, detalle.getTrabajo().getId());
	        pstmt.setInt(3, detalle.getCantidad());
	        pstmt.setString(4, detalle.getEstado());

	        pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
    
    // Obtener todos los registros
	public List<ServicioDetalles> obtenerTodos() {
	    List<ServicioDetalles> lista = new ArrayList<>();

	    String sql = """
	        SELECT 
	            sd.cantidad,
	            sd.estado,

	            s.id AS s_id,
	            s.cliente_id AS s_cliente_id,

	            t.id AS t_id,
	            t.nombre AS t_nombre

	        FROM servicio_detalles sd
	        JOIN servicios s ON sd.servicio_id = s.id
	        JOIN trabajos t ON sd.trabajo_id = t.id
	    """;

	    try (Connection conn = Database.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {

	            Servicio servicio = new Servicio(
	                rs.getInt("s_id"),
	                rs.getInt("s_cliente_id")
	            );

	            Trabajo trabajo = new Trabajo(
	                rs.getInt("t_id"),
	                rs.getString("t_nombre")
	            );

	            ServicioDetalles detalle = new ServicioDetalles(
	                servicio,
	                trabajo,
	                rs.getInt("cantidad"),
	                rs.getString("estado")
	            );

	            lista.add(detalle);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return lista;
	}
    
    // Obtener todos los trabajos de un servicio eventual
	public List<ServicioDetalles> obtenerPorServicio(int servicioId) {
	    List<ServicioDetalles> lista = new ArrayList<>();

	    String sql = """
	        SELECT 
	            sd.cantidad,
	            sd.estado,

	            s.id AS s_id,
	            s.cliente_id AS s_cliente_id,

	            t.id AS t_id,
	            t.nombre AS t_nombre

	        FROM servicio_detalles sd
	        JOIN servicios s ON sd.servicio_id = s.id
	        JOIN trabajos t ON sd.trabajo_id = t.id
	        WHERE s.id = ?
	    """;

	    try (Connection conn = Database.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, servicioId);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {

	            Servicio servicio = new Servicio(
	                rs.getInt("s_id"),
	                rs.getInt("s_cliente_id")
	            );

	            Trabajo trabajo = new Trabajo(
	                rs.getInt("t_id"),
	                rs.getString("t_nombre")
	            );

	            ServicioDetalles detalle = new ServicioDetalles(
	                servicio,
	                trabajo,
	                rs.getInt("cantidad"),
	                rs.getString("estado")
	            );

	            lista.add(detalle);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return lista;
	}
    
    // Obtener todos los ID de los trabajos de un servicio
    public HashMap<Integer, Integer> obtenerTrabajosPorServicio(int servicioId) {
        HashMap<Integer, Integer> mapIds = new HashMap<>();
        String sql = "SELECT trabajo_id, cantidad FROM servicio_detalles WHERE servicio_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, servicioId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                mapIds.put(rs.getInt("trabajo_id"), rs.getInt("cantidad"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return mapIds;
    }
    
    
    // Actualizar cantidad de un registro
    public void actualizarCantidad(ServicioDetalles detalle) {
        String sql = "UPDATE servicio_detalles SET cantidad = ?, estado = ? WHERE servicio_id = ? AND trabajo_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, detalle.getCantidad());
            pstmt.setString(2, detalle.getEstado());
            pstmt.setInt(3, detalle.getServicio().getId());
            pstmt.setInt(4, detalle.getTrabajo().getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Actualizar estado de un registro
    public void actualizarEstado(ServicioDetalles detalle) {
    	String sql = "UPDATE servicio_detalles SET estado = ? WHERE servicio_id = ? AND trabajo_id = ?";
    	
    	try (Connection conn = Database.connect();
    		 PreparedStatement pstmt = conn.prepareStatement(sql)){
    		
    		pstmt.setString(1, detalle.getEstado());
    		pstmt.setInt(2, detalle.getServicio().getId());
    		pstmt.setInt(3, detalle.getTrabajo().getId());
    		
    		pstmt.executeUpdate();
    		
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    // Eliminar relación
    public void eliminar(ServicioDetalles detalle) {
        String sql = "DELETE FROM servicio_detalles WHERE servicio_id = ? AND trabajo_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, detalle.getServicio().getId());
            pstmt.setInt(2, detalle.getTrabajo().getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Eliminar todos los trabajos de un servicio eventual
    public void eliminarPorServicio(int servicioId) {
        String sql = "DELETE FROM servicio_detalles WHERE servicio_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, servicioId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
