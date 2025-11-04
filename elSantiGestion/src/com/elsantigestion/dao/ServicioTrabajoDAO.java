package com.elsantigestion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.elsantigestion.model.ServicioTrabajo;

public class ServicioTrabajoDAO {
    
    // Insertar relación
    public void agregar(ServicioTrabajo set) {
        String sql = "INSERT INTO servicio_trabajos (servicio_id, trabajo_id, cantidad) VALUES (?, ?, ?)";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, set.getServicioId());
            pstmt.setInt(2, set.getTrabajoId());
            pstmt.setInt(3, set.getCantidad());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Obtener todos los registros
    public List<ServicioTrabajo> obtenerTodos() {
        List<ServicioTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicio_trabajos";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                ServicioTrabajo set = new ServicioTrabajo(
                        rs.getInt("servicio_id"),
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
    public List<ServicioTrabajo> obtenerPorServicio(int servicioId) {
        List<ServicioTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicio_trabajos WHERE servicio_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, servicioId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ServicioTrabajo set = new ServicioTrabajo(
                        rs.getInt("servicio_id"),
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
    
    // Obtener todos los ID de los trabajos de un servicio
    public List<Integer> obtenerIdsPorServicio(int servicioId) {
        List<Integer> listaIds = new ArrayList<>();
        String sql = "SELECT trabajo_id FROM servicio_trabajos WHERE servicio_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, servicioId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                listaIds.add(rs.getInt("trabajo_id"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listaIds;
    }
    
    // Obtener un registro específico (por PK compuesta)
    public ServicioTrabajo obtenerPorIds(int servicioId, int trabajoId) {
        String sql = "SELECT * FROM servicio_trabajos WHERE servicio_id = ? AND trabajo_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, servicioId);
            pstmt.setInt(2, trabajoId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new ServicioTrabajo(
                        rs.getInt("servicio_id"),
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
    public void actualizarCantidad(ServicioTrabajo set) {
        String sql = "UPDATE servicio_trabajos SET cantidad = ? WHERE servicio_id = ? AND trabajo_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, set.getCantidad());
            pstmt.setInt(2, set.getServicioId());
            pstmt.setInt(3, set.getTrabajoId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Eliminar relación
    public void eliminar(int servicioId, int trabajoId) {
        String sql = "DELETE FROM servicio_trabajos WHERE servicio_id = ? AND trabajo_id = ?";
        
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
        String sql = "DELETE FROM servicio_trabajos WHERE servicio_id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, servicioId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
