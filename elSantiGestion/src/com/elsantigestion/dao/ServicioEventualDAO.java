package com.elsantigestion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.elsantigestion.model.ServicioEventual;

public class ServicioEventualDAO {
	
	//Agregar servicio eventual
	public void agregarServicioEventual(ServicioEventual servicio) {
		
		String sql = "INSERT INTO servicio_eventual(cliente_id, fecha_creacion, fecha_programada, precio, gastos, monto_final) VALUES (?, ?, ?, ?, ?, ?)";
		
		try(Connection conn = Database.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setInt(1, servicio.getClienteId());
			pstmt.setDate(2, java.sql.Date.valueOf(servicio.getFechaCreacion()));
			pstmt.setDate(3, java.sql.Date.valueOf(servicio.getFechaProgramada()));
			pstmt.setDouble(4, servicio.getPrecio());
			pstmt.setDouble(5, servicio.getGastos());
			pstmt.setDouble(6, (servicio.getPrecio() - servicio.getGastos()));
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	//Obtener todos los servicios eventuales
	public List<ServicioEventual> obtenerServiciosEventuales(){
		
		List<ServicioEventual> lista = new ArrayList<>();
		String sql = "SELECT * FROM servicio_eventual";
		
		try(Connection conn = Database.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()) {
				
				ServicioEventual s = new ServicioEventual(
						 rs.getInt("id"),
						 rs.getInt("cliente_id"),
						 rs.getDate("fecha_creacion").toLocalDate(),
						 rs.getDate("fecha_programada").toLocalDate(),
						 rs.getDouble("precio"),
						 rs.getDouble("gastos"),
						 rs.getDouble("monto_final")
				);
				lista.add(s);
				
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
		return lista;
		
	}
	
	//Actualizar servicio eventual
		public void actualizarServicioEventual(ServicioEventual servicio) {
			
			String sql = "UPDATE servicio_eventual SET cliente_id = ?, fecha_creacion = ?, fecha_programada = ?, precio = ?, gastos = ?, monto_final = ? WHERE id = ?";
			
			try (Connection conn = Database.connect();
				 PreparedStatement pstmt = conn.prepareStatement(sql)){
				
				pstmt.setInt(1, servicio.getClienteId());
				pstmt.setDate(2, java.sql.Date.valueOf(servicio.getFechaCreacion()));
				pstmt.setDate(3, java.sql.Date.valueOf(servicio.getFechaProgramada()));
				pstmt.setDouble(4, servicio.getPrecio());
				pstmt.setDouble(5, servicio.getGastos());
				pstmt.setDouble(6, (servicio.getPrecio() - servicio.getGastos()));
				pstmt.setInt(7, servicio.getId());
				
				pstmt.executeUpdate();
				
			} catch(Exception e) {
				
				e.printStackTrace();
				
			}
			
		}
		
		//Eliminar servicio eventual
		public void eliminarServicioEventual(int id) {
			
			String sql = "DELETE FROM servicio_eventual WHERE id = ?";
			
			try(Connection conn = Database.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
				
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
				
			} catch(Exception e) {
				
				e.printStackTrace();
				
			}
			
		}

}
