package dao;

import config.ConexionDB;
import model.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar la persistencia
 * y operaciones CRUD de la entidad Repartidor en la base de datos.
 *
 * @author Maximiliano Villalobos
 * @version 1.0
 */
public class RepartidorDAO {

    /**
     * Inserta un nuevo repartidor en la base de datos.
     *
     * @param repartidor Objeto {@link Repartidor} con la información a registrar.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    public boolean agregarRepartidor(Repartidor repartidor) {
        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, repartidor.getNombre());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar repartidor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene la lista completa de repartidores registrados en la base de datos.
     *
     * @return Lista de objetos {@link Repartidor}.
     */
    public List<Repartidor> listarRepartidores() {
        List<Repartidor> lista = new ArrayList<>();
        String sql = "SELECT * FROM repartidores";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Repartidor r = new Repartidor();
                r.setId(rs.getInt("id"));
                r.setNombre(rs.getString("nombre"));
                lista.add(r);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar repartidores: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Obtiene un repartidor por su ID único.
     *
     * @param id Identificador del repartidor.
     * @return Objeto {@link Repartidor} si existe, o {@code null} si no se encuentra.
     */
    public Repartidor obtenerPorId(int id) {
        String sql = "SELECT * FROM repartidores WHERE id = ?";
        Repartidor repartidor = null;

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    repartidor = new Repartidor(
                            rs.getInt("id"),
                            rs.getString("nombre")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar repartidor por ID: " + e.getMessage());
        }

        return repartidor;
    }

    /**
     * Actualiza el nombre de un repartidor existente.
     *
     * @param repartidor Objeto {@link Repartidor} con los datos actualizados.
     * @return {@code true} si se actualizó correctamente, {@code false} de lo contrario.
     */
    public boolean actualizarRepartidor(Repartidor repartidor) {
        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, repartidor.getNombre());
            stmt.setInt(2, repartidor.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar repartidor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un repartidor según su ID.
     *
     * @param id Identificador del repartidor a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    public boolean eliminarRepartidor(int id) {
        String sql = "DELETE FROM repartidores WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar repartidor: " + e.getMessage());
            return false;
        }
    }
}