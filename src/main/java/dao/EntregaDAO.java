package dao;

import config.ConexionDB;
import model.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar la asignación y registro de Entregas
 * en la base de datos speedfast_db.
 *
 * @author Maximiliano Villalobos
 * @version 1.0
 */
public class EntregaDAO {

    /**
     * Asigna un pedido a un repartidor registrando una nueva entrega.
     *
     * @param entrega Objeto {@link Entrega} con los ID de repartidor y pedido.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    public boolean registrarEntrega(Entrega entrega) {
        String sql = "INSERT INTO entregas (id_repartidor, id_pedido) VALUES (?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entrega.getIdRepartidor());
            stmt.setInt(2, entrega.getIdPedido());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar entrega: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el listado de entregas con información detallada
     * uniendo los datos del repartidor y del pedido (JOIN).
     *
     * @return Lista de objetos {@link Entrega} con información enriquecida.
     */
    public List<Entrega> listarEntregasConDetalle() {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.id_repartidor, e.id_pedido, e.fecha_entrega, " +
                "r.nombre AS nombre_repartidor, " +
                "CONCAT('Pedido #', p.id, ' (', p.tipo_pedido, ') - ', p.estado) AS detalle_pedido " +
                "FROM entregas e " +
                "INNER JOIN repartidores r ON e.id_repartidor = r.id " +
                "INNER JOIN pedidos p ON e.id_pedido = p.id";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Entrega e = new Entrega();
                e.setId(rs.getInt("id"));
                e.setIdRepartidor(rs.getInt("id_repartidor"));
                e.setIdPedido(rs.getInt("id_pedido"));
                e.setFechaEntrega(rs.getTimestamp("fecha_entrega"));
                e.setNombreRepartidor(rs.getString("nombre_repartidor"));
                e.setDetallePedido(rs.getString("detalle_pedido"));
                lista.add(e);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar entregas: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Elimina un registro de entrega por su ID.
     *
     * @param id Identificador de la entrega a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} en caso contrario.
     */
    public boolean eliminarEntrega(int id) {
        String sql = "DELETE FROM entregas WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar entrega: " + e.getMessage());
            return false;
        }
    }
}