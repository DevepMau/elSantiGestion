package com.elsantigestion.dao;

import com.elsantigestion.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Insertar un cliente
    public void agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes(nombre, telefono, localidad, direccion, email, activo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getTelefono());
            pstmt.setString(3, cliente.getLocalidad());
            pstmt.setString(4, cliente.getDireccion());
            pstmt.setString(5, cliente.getEmail());
            pstmt.setBoolean(6, cliente.isActivo());

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
                        rs.getString("localidad"),
                        rs.getString("direccion"),
                        rs.getString("email"),
                        rs.getBoolean("activo")
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
        String sql = "UPDATE clientes SET nombre = ?, telefono = ?, localidad = ?, direccion = ?, email = ?, activo = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getTelefono());
            pstmt.setString(3, cliente.getLocalidad());
            pstmt.setString(4, cliente.getDireccion());
            pstmt.setString(5, cliente.getEmail());
            pstmt.setBoolean(6, cliente.isActivo());
            pstmt.setInt(7, cliente.getId());

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
    
}