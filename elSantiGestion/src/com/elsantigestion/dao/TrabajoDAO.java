package com.elsantigestion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.elsantigestion.model.Trabajo;

public class TrabajoDAO {
	
	//Agregar un trabajo nuevo
	public void agregarTrabajo(Trabajo trabajo) {
		
		String sql = "INSERT INTO trabajos(nombre, detalle, precio, unidad, activo) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = Database.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, trabajo.getNombre());
			pstmt.setString(2, trabajo.getDetalle());
			pstmt.setDouble(3, trabajo.getPrecio());
			pstmt.setString(4, trabajo.getUnidad());
			pstmt.setBoolean(5, trabajo.isActivo());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//Obtener todos los trabajos.
	public List<Trabajo> obtenerTrabajos(){
		
		List<Trabajo> lista = new ArrayList<>();
		String sql = "SELECT * FROM trabajos";
		
		try (Connection conn = Database.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql);
			 ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()){
				
				Trabajo s = new Trabajo(
						rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("detalle"),
						rs.getDouble("precio"),
						rs.getString("unidad"),
						rs.getBoolean("activo")
				);
				lista.add(s);
				
			}
	
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
		return lista;
		
	}
	
	//Actualizar trabajo
	public void actualizarTrabajo(Trabajo trabajo) {
		
		String sql = "UPDATE trabajos SET nombre = ?, detalle = ?, precio = ?, unidad = ?, activo = ? WHERE id = ?";
		
		try (Connection conn = Database.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, trabajo.getNombre());
			pstmt.setString(2, trabajo.getDetalle());
			pstmt.setDouble(3, trabajo.getPrecio());
			pstmt.setString(4, trabajo.getUnidad());
			pstmt.setBoolean(5, trabajo.isActivo());
			pstmt.setInt(6, trabajo.getId());
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	// alternar activo / inactivo
    public void alternarActivo(int trabajoId) {
        String sql = "UPDATE trabajos SET activo = NOT activo WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, trabajoId);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	//Eliminar trabajo
	public void eliminarTrabajo(int id) {
		
		String sql = "DELETE FROM trabajos WHERE id = ?";
		
		try (Connection conn = Database.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	// Obtener trabajo por id
	public Trabajo obtenerTrabajoPorId(int id) {
		
		String sql = "SELECT * FROM trabajos WHERE id = ?";
		
		try (Connection conn = Database.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return new Trabajo(
						rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("detalle"),
						rs.getDouble("precio"),
						rs.getString("unidad"),
						rs.getBoolean("activo")
				);
			}
		
		} catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		return null;
	}
	
	public boolean existeTrabajoPorId(int id) {
		String sql = "SELECT 1 FROM trabajos WHERE id = ?";
		
		try(Connection conn = Database.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setInt(1, id);
			try(ResultSet rs = pstmt.executeQuery()){
				return rs.next();
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// Listar todos los clientes por estado
	public List<Trabajo> obtenerTrabajosPorEstado(boolean activo){
		List<Trabajo> lista = new ArrayList<>();
		String sql = "SELECT * FROM trabajos WHERE activo = ?";
		
		try(Connection conn = Database.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setBoolean(1, activo);
			
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					Trabajo t = new Trabajo(
							rs.getInt("id"),
							rs.getString("nombre"),
							rs.getString("detalle"),
							rs.getDouble("precio"),
							rs.getString("unidad"),
							rs.getBoolean("activo")
					);
					lista.add(t);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	} 

}
