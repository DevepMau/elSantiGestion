package com.elsantigestion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.elsantigestion.model.ServicioEventualTrabajo;

public class ServicioEventualTrabajoDAO {
    
    // Insertar relación
    public void agregar(ServicioEventualTrabajo set) {
        String sql = "INSERT INTO servicio_eventual_trabajos (servicio_eventual_id, trabajo_id, cantidad) VALUES (?, ?, ?)";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, set.getServicioEventualId());
            pstmt.setInt(2, set.getTrabajoId());
            pstmt.setInt(3, set.getCantidad());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Obtener todos los registros
    public List<ServicioEventualTrabajo> obtenerTodos() {
        List<ServicioEventualTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicio_eventual_trabajos";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                ServicioEventualTrabajo set = new ServicioEventualTrabajo(
                        rs.getInt("servicio_eventual_id"),
                        rs.getInt("trabajo_id"),
                        rs.getInt("cantidad")
                );
                lista.add(set);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    // Obtener todos los trabajos de un servicio eventual
    public List<ServicioEventualTrabajo> obtenerPorServicio(int servicioId) {
        List<ServicioEventualTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicio_eventual_trabajos WHERE servicio_eventual_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, servicioId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ServicioEventualTrabajo set = new ServicioEventualTrabajo(
                        rs.getInt("servicio_eventual_id"),
                        rs.getInt("trabajo_id"),
                        rs.getInt("cantidad")
                );
                lista.add(set);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    // Obtener un registro específico (por PK compuesta)
    public ServicioEventualTrabajo obtenerPorIds(int servicioId, int trabajoId) {
        String sql = "SELECT * FROM servicio_eventual_trabajos WHERE servicio_eventual_id = ? AND trabajo_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, servicioId);
            pstmt.setInt(2, trabajoId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new ServicioEventualTrabajo(
                        rs.getInt("servicio_eventual_id"),
                        rs.getInt("trabajo_id"),
                        rs.getInt("cantidad")
                );
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Actualizar cantidad de un registro
    public void actualizarCantidad(ServicioEventualTrabajo set) {
        String sql = "UPDATE servicio_eventual_trabajos SET cantidad = ? WHERE servicio_eventual_id = ? AND trabajo_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, set.getCantidad());
            pstmt.setInt(2, set.getServicioEventualId());
            pstmt.setInt(3, set.getTrabajoId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Eliminar relación
    public void eliminar(int servicioId, int trabajoId) {
        String sql = "DELETE FROM servicio_eventual_trabajos WHERE servicio_eventual_id = ? AND trabajo_id = ?";
        
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
        String sql = "DELETE FROM servicio_eventual_trabajos WHERE servicio_eventual_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, servicioId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
