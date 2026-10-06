package dao;

import config.ConexionDB;
import model.EstadoPedido;
import model.Pedido;
import model.TipoPedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar la persistencia
 * y operaciones CRUD de la entidad Pedido en la base de datos speedfast_db.
 *
 * @author Maximiliano Villalobos
 * @version 1.0
 */
public class PedidoDAO {

    /**
     * Inserta un nuevo pedido en la base de datos.
     *
     * @param pedido Objeto {@link Pedido} a registrar.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    public boolean agregarPedido(Pedido pedido) {
        String sql = "INSERT INTO pedidos (direccion_origen, direccion_destino, tipo_pedido, estado, precio) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pedido.getDireccionOrigen());
            stmt.setString(2, pedido.getDireccionDestino());
            stmt.setString(3, pedido.getTipoPedido().name());
            stmt.setString(4, pedido.getEstado().name());
            stmt.setDouble(5, pedido.getPrecio());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene la lista completa de pedidos registrados.
     *
     * @return Lista de objetos {@link Pedido}.
     */
    public List<Pedido> listarPedidos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("id"));
                p.setDireccionOrigen(rs.getString("direccion_origen"));
                p.setDireccionDestino(rs.getString("direccion_destino"));
                p.setTipoPedido(TipoPedido.valueOf(rs.getString("tipo_pedido")));
                p.setEstado(EstadoPedido.valueOf(rs.getString("estado")));
                p.setPrecio(rs.getDouble("precio"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Obtiene un pedido según su ID.
     *
     * @param id Identificador del pedido.
     * @return Objeto {@link Pedido} si se encuentra, o {@code null}.
     */
    public Pedido obtenerPorId(int id) {
        String sql = "SELECT * FROM pedidos WHERE id = ?";
        Pedido pedido = null;

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pedido = new Pedido(
                            rs.getInt("id"),
                            rs.getString("direccion_origen"),
                            rs.getString("direccion_destino"),
                            TipoPedido.valueOf(rs.getString("tipo_pedido")),
                            EstadoPedido.valueOf(rs.getString("estado")),
                            rs.getDouble("precio")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar pedido por ID: " + e.getMessage());
        }

        return pedido;
    }

    /**
     * Actualiza la información de un pedido existente.
     *
     * @param pedido Objeto {@link Pedido} con los datos actualizados.
     * @return {@code true} si la actualización fue exitosa, {@code false} de lo contrario.
     */
    public boolean actualizarPedido(Pedido pedido) {
        String sql = "UPDATE pedidos SET direccion_origen = ?, direccion_destino = ?, tipo_pedido = ?, estado = ?, precio = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pedido.getDireccionOrigen());
            stmt.setString(2, pedido.getDireccionDestino());
            stmt.setString(3, pedido.getTipoPedido().name());
            stmt.setString(4, pedido.getEstado().name());
            stmt.setDouble(5, pedido.getPrecio());
            stmt.setInt(6, pedido.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un pedido por su ID.
     *
     * @param id Identificador del pedido a eliminar.
     * @return {@code true} si se eliminó, {@code false} en caso contrario.
     */
    public boolean eliminarPedido(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar pedido: " + e.getMessage());
            return false;
        }
    }
}