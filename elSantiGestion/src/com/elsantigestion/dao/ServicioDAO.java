package com.elsantigestion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.elsantigestion.model.Servicio;

public class ServicioDAO {
	
	//Agregar servicio eventual
	public void agregarServicio(Servicio servicio) {
		
		String sql = "INSERT INTO servicios (cliente_id, fecha_creacion, fecha_programada, tipo, precio, gastos, monto_final, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try(Connection conn = Database.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setInt(1, servicio.getClienteId());
			pstmt.setDate(2, java.sql.Date.valueOf(servicio.getFechaCreacion()));
			pstmt.setDate(3, java.sql.Date.valueOf(servicio.getFechaProgramada()));
			pstmt.setString(4, servicio.getTipo());
			pstmt.setDouble(5, servicio.getPrecio());
			pstmt.setDouble(6, servicio.getGastos());
			pstmt.setDouble(7, (servicio.getPrecio() - servicio.getGastos()));
			pstmt.setString(8, servicio.getEstado());
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	//Obtener todos los servicios eventuales
	public List<Servicio> obtenerServicios(){
		
		List<Servicio> lista = new ArrayList<>();
		String sql = "SELECT * FROM servicios";
		
		try(Connection conn = Database.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()) {
				
				Servicio s = new Servicio(
						 rs.getInt("id"),
						 rs.getInt("cliente_id"),
						 rs.getDate("fecha_creacion").toLocalDate(),
						 rs.getDate("fecha_programada").toLocalDate(),
						 rs.getString("tipo"),
						 rs.getDouble("precio"),
						 rs.getDouble("gastos"),
						 rs.getDouble("monto_final"),
						 rs.getString("estado")
				);
				lista.add(s);
				
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
		return lista;
		
	}
	
	//Obtener todos los servicios eventuales por periodisidad
	public List<Servicio> obtenerServiciosPorTipo(String tipo) {
	    List<Servicio> lista = new ArrayList<>();
	    String sql = "SELECT * FROM servicios WHERE tipo = ?";

	    try (Connection conn = Database.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, tipo);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Servicio s = new Servicio(
	                    rs.getInt("id"),
	                    rs.getInt("cliente_id"),
	                    rs.getDate("fecha_creacion").toLocalDate(),
	                    rs.getDate("fecha_programada").toLocalDate(),
	                    rs.getString("tipo"),
	                    rs.getDouble("precio"),
	                    rs.getDouble("gastos"),
	                    rs.getDouble("monto_final"),
	                    rs.getString("estado")
	                );
	                lista.add(s);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return lista;
	}
	
	
	
	//Actualizar servicio eventual
		public void actualizarServicio(Servicio servicio) {
			
			String sql = "UPDATE servicios SET cliente_id = ?, fecha_creacion = ?, fecha_programada = ?, tipo = ?, precio = ?, gastos = ?, monto_final = ?, estado = ? WHERE id = ?";
			
			try (Connection conn = Database.connect();
				 PreparedStatement pstmt = conn.prepareStatement(sql)){
				
				pstmt.setInt(1, servicio.getClienteId());
				pstmt.setDate(2, java.sql.Date.valueOf(servicio.getFechaCreacion()));
				pstmt.setDate(3, java.sql.Date.valueOf(servicio.getFechaProgramada()));
				pstmt.setString(4, servicio.getTipo());
				pstmt.setDouble(5, servicio.getPrecio());
				pstmt.setDouble(6, servicio.getGastos());
				pstmt.setDouble(7, (servicio.getPrecio() - servicio.getGastos()));
				pstmt.setString(8, servicio.getEstado());
				pstmt.setInt(9, servicio.getId());
				
				pstmt.executeUpdate();
				
			} catch(Exception e) {
				
				e.printStackTrace();
				
			}
			
		}
		
		//Eliminar servicio eventual
		public void eliminarServicio(int id) {
			
			String sql = "DELETE FROM servicios WHERE id = ?";
			
			try(Connection conn = Database.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
				
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
				
			} catch(Exception e) {
				
				e.printStackTrace();
				
			}
			
		}

}
