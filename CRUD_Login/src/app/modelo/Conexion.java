package app.modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public Connection conectar() {
        Connection conn = null;
        try {
            // No es obligatorio desde JDBC 4.0, pero no está de más
            Class.forName("org.sqlite.JDBC");

            // Ruta completa al archivo .db
            String url = "jdbc:sqlite:C:/Users/POO/Downloads/prod.db"; // <-- cambia este nombre según tu .db real

            conn = DriverManager.getConnection(url);
            System.out.println("✅ Conexión a SQLite exitosa");
        } catch (Exception e) {
            System.out.println("❌ Error al conectar: " + e.getMessage());
        }
        return conn;
    }
}
