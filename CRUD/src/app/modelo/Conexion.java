package app.modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    public Connection conectar() {
        String url = "jdbc:postgresql://localhost:5432/Tienda";
        String user = "postgres";
        String password = "Thiago14";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión Exitosa a PostgreSQL");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        Connection c = conexion.conectar();
        if (c != null) {
            System.out.println("Conectado");
        } else {
            System.out.println("No conectado");
        }
    }
}