package app.modelo;

import javax.swing.*;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class ImplCrud implements Crud {

    // Consultas SQL
    private final String SELECT = "SELECT * FROM producto";
    private final String SELECT_BY_ID = "SELECT * FROM producto WHERE id = ?";
    private final String INSERT = "INSERT INTO producto (codigo, nombre, precio) VALUES (?, ?, ?)";
    private final String UPDATE = "UPDATE producto SET codigo = ?, nombre = ?, precio = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM producto WHERE id = ?";

    // Método para conectar
    private Connection conectar() {
        Conexion conexion = new Conexion();
        return conexion.conectar();
    }

    @Override
    public Map<Integer, Producto> seleccinarTodo() {
        Map<Integer, Producto> map = new LinkedHashMap<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SELECT);
            while (rs.next()) {
                Producto producto = new Producto(rs.getInt("id"),
                        rs.getString("codigo"), rs.getString("nombre"),
                        rs.getDouble("precio"));
                System.out.println(producto);
                map.put(rs.getInt("id"), producto);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return map;
    }

    @Override
    public Producto buscar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Producto producto = null;
        try {
            conn = this.conectar();
            stmt = conn.prepareStatement(SELECT_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                producto = new Producto(rs.getInt("id"), rs.getString("codigo"),
                        rs.getString("nombre"), rs.getDouble("precio"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return producto;
    }

    @Override
    public void insertar(Producto producto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = this.conectar();
            pstmt = conn.prepareStatement(INSERT);
            pstmt.setString(1, producto.getCodigo());
            pstmt.setString(2, producto.getNombre());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    public Producto seleccionarProdNOSEGURO(int id, String codigo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Producto producto = null;
        try {
            conn = this.conectar();
            stmt = conn.prepareStatement("SELECT * FROM producto WHERE id = ? AND codigo = ?");
            stmt.setInt(1, id);
            stmt.setString(2, codigo);
            rs = stmt.executeQuery();
            if (rs.next()) {
                producto = new Producto(rs.getInt("id"), rs.getString("codigo"),
                        rs.getString("nombre"), rs.getDouble("precio"));
                System.out.println(producto);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return producto;
    }

    @Override
    public void actualizar(Producto producto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = this.conectar();
            pstmt = conn.prepareStatement(UPDATE);
            pstmt.setString(1, producto.getCodigo());
            pstmt.setString(2, producto.getNombre());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    @Override
    public void eliminar(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = this.conectar();
            pstmt = conn.prepareStatement(DELETE);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        ImplCrud i = new ImplCrud();
        i.seleccinarTodo();
        i.insertar(new Producto("P021", "Cámara Web 4K", 99.99));
        i.seleccinarTodo();
        i.actualizar(new Producto(1, "P001", "Monitor LED 27 pulgadas", 150.00));
        i.seleccinarTodo();
        i.seleccionarProdNOSEGURO(1, "P001");
    }
}