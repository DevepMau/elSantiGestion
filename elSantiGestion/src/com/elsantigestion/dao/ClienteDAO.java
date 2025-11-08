package com.elsantigestion.dao;

import com.elsantigestion.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Insertar un cliente
    public void agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes(nombre, telefono, email, barrio_privado, barrio_nombre, barrio_lote, localidad, direccion, activo, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getTelefono());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setBoolean(4, cliente.isBarrioPrivado());
            pstmt.setString(5, cliente.getBarrioNombre());
            pstmt.setInt(6, cliente.getBarrioLote());
            pstmt.setString(7, cliente.getLocalidad());
            pstmt.setString(8, cliente.getDireccion());
            pstmt.setBoolean(9, cliente.isActivo());
            pstmt.setDate(10, java.sql.Date.valueOf(cliente.getFechaCreacion()));

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listar todos los clientes
    public List<Cliente> obtenerClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getBoolean("barrio_privado"),
                        rs.getString("barrio_nombre"),
                        rs.getInt("barrio_lote"),
                        rs.getString("localidad"),
                        rs.getString("direccion"),
                        rs.getBoolean("activo"),
                        rs.getDate("fecha_creacion").toLocalDate()
                );
                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    // Actualizar un cliente existente
    public void actualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, telefono = ?, email = ?, barrio_privado = ?, barrio_nombre = ?, barrio_lote = ?, localidad = ?, direccion = ?, activo = ?, fecha_creacion = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getTelefono());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setBoolean(4, cliente.isBarrioPrivado());
            pstmt.setString(5, cliente.getBarrioNombre());
            pstmt.setInt(6, cliente.getBarrioLote());
            pstmt.setString(7, cliente.getLocalidad());
            pstmt.setString(8, cliente.getDireccion());
            pstmt.setBoolean(9, cliente.isActivo());
            pstmt.setDate(10, java.sql.Date.valueOf(cliente.getFechaCreacion()));
            pstmt.setInt(11, cliente.getId());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Eliminar un cliente
    public void eliminarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Cliente obtenerClientePorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        Cliente cliente = null;

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            rs.getBoolean("barrio_privado"),
                            rs.getString("barrio_nombre"),
                            rs.getInt("barrio_lote"),
                            rs.getString("localidad"),
                            rs.getString("direccion"),
                            rs.getBoolean("activo"),
                            rs.getDate("fecha_creacion").toLocalDate()
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cliente;
    }
    
    public boolean existeClientePorId(int id) {
        String sql = "SELECT 1 FROM clientes WHERE id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    
}