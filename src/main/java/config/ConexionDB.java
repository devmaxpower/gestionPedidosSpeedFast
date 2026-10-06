package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar y proveer la conexión a la base de datos MySQL
 * para el sistema de gestión de la empresa SpeedFast mediante JDBC[cite: 1, 2].
 *
 * @author Maximiliano Villalobos
 * @version 1.0
 */
public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:8889/speedfast_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConexion() {
        Connection conexion = null;
        try {
            // Forzamos el registro explícito del Driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL (Revisa pom.xml): " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public static void main(String[] args) {
        Connection conn = getConexion();
        if (conn != null) {
            System.out.println("¡Conexión exitosa a MAMP / speedfast_db!");
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        } else {
            System.out.println("No se pudo establecer la conexión. Revisa si MySQL está activo en MAMP.");
        }
    }
}