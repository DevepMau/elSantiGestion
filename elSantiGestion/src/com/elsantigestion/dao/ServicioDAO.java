package com.elsantigestion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.elsantigestion.model.Servicio;

public class ServicioDAO {
	
	//Agregar un cliente nuevo
	public void agregarServicio(Servicio servicio) {
		
		String sql = "INSERT INTO servicio(nombre, detalle, precio, unidad, activo) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = Database.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, servicio.getNombre());
			pstmt.setString(2, servicio.getDetalle());
			pstmt.setDouble(3, servicio.getPrecio());
			pstmt.setString(4, servicio.getUnidad());
			pstmt.setBoolean(5, servicio.isActivo());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//Obtener todos los servicios.
	public List<Servicio> obtenerServicios(){
		
		List<Servicio> lista = new ArrayList<>();
		String sql = "SELECT * FROM servicios";
		
		try (Connection conn = Database.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql);
			 ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()){
				
				Servicio s = new Servicio(
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
	
	//Actualizar servicio
	public void actualizarServicio(Servicio servicio) {
		
		String sql = "UPDATE servicios SET nombre = ?, detalle = ?, precio = ?, unidad = ?, activo = ?";
		
		try (Connection conn = Database.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, servicio.getNombre());
			pstmt.setString(2, servicio.getDetalle());
			pstmt.setDouble(3, servicio.getPrecio());
			pstmt.setString(4, servicio.getUnidad());
			pstmt.setBoolean(5, servicio.isActivo());
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	//Eliminar servicio
	public void eliminarServicio(int id) {
		
		String sql = "DELETE FROM servicios WHERE id = ?";
		
		try (Connection conn = Database.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}

}
