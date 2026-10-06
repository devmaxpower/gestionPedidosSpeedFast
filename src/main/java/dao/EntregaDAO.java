package dao;

import config.ConexionDB;
import model.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    public boolean registrarEntrega(Entrega entrega) {
        // La guía pide guardar fecha y hora, usaremos las funciones nativas de MySQL
        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, CURRENT_DATE, CURRENT_TIME)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entrega.getIdPedido());
            stmt.setInt(2, entrega.getIdRepartidor());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar entrega: " + e.getMessage());
            return false;
        }
    }

    public List<Entrega> listarEntregasConDetalle() {
        List<Entrega> lista = new ArrayList<>();
        // Unimos los campos fecha y hora de MySQL para mostrarlos juntos
        String sql = "SELECT e.id, e.id_repartidor, e.id_pedido, CONCAT(e.fecha, ' ', e.hora) AS fecha_entrega, " +
                "r.nombre AS nombre_repartidor, " +
                "CONCAT('Pedido #', p.id, ' (', p.tipo, ') - ', p.estado) AS detalle_pedido " +
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
                // Usamos el campo seteado como string en tu clase modelo
                e.setDetallePedido(rs.getString("detalle_pedido"));
                e.setNombreRepartidor(rs.getString("nombre_repartidor"));
                lista.add(e);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar entregas: " + e.getMessage());
        }
        return lista;
    }

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