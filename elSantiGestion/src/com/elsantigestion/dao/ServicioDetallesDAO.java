package com.elsantigestion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.elsantigestion.model.ServicioDetalles;

public class ServicioDetallesDAO {
    
    // Insertar relación
    public void agregar(ServicioDetalles set) {
        String sql = "INSERT INTO servicio_detalles (servicio_id, trabajo_id, cantidad, estado) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, set.getServicioId());
            pstmt.setInt(2, set.getTrabajoId());
            pstmt.setInt(3, set.getCantidad());
            pstmt.setString(4, set.getEstado());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Obtener todos los registros
    public List<ServicioDetalles> obtenerTodos() {
        List<ServicioDetalles> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicio_detalles";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                ServicioDetalles set = new ServicioDetalles(
                        rs.getInt("servicio_id"),
                        rs.getInt("trabajo_id"),
                        rs.getInt("cantidad"),
                        rs.getString("estado")
                );
                lista.add(set);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    // Obtener todos los trabajos de un servicio eventual
    public List<ServicioDetalles> obtenerPorServicio(int servicioId) {
        List<ServicioDetalles> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicio_detalles WHERE servicio_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, servicioId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ServicioDetalles set = new ServicioDetalles(
                        rs.getInt("servicio_id"),
                        rs.getInt("trabajo_id"),
                        rs.getInt("cantidad"),
                        rs.getString("estado")
                );
                lista.add(set);
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
    
    // Obtener un registro específico (por PK compuesta)
    public ServicioDetalles obtenerPorIds(int servicioId, int trabajoId) {
        String sql = "SELECT * FROM servicio_detalles WHERE servicio_id = ? AND trabajo_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, servicioId);
            pstmt.setInt(2, trabajoId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new ServicioDetalles(
                        rs.getInt("servicio_id"),
                        rs.getInt("trabajo_id"),
                        rs.getInt("cantidad"),
                        rs.getString("estado")
                );
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Actualizar cantidad de un registro
    public void actualizarCantidad(ServicioDetalles set) {
        String sql = "UPDATE servicio_detalles SET cantidad = ? WHERE servicio_id = ? AND trabajo_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, set.getCantidad());
            pstmt.setInt(2, set.getServicioId());
            pstmt.setInt(3, set.getTrabajoId());
            pstmt.setString(4, set.getEstado());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Eliminar relación
    public void eliminar(int servicioId, int trabajoId) {
        String sql = "DELETE FROM servicio_detalles WHERE servicio_id = ? AND trabajo_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, servicioId);
            pstmt.setInt(2, trabajoId);
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
