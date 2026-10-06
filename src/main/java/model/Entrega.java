package model;

import java.sql.Timestamp;

/**
 * Clase que representa una Entrega dentro del sistema SpeedFast.
 * Asocia un Repartidor con un Pedido y registra la fecha de asignación.
 * Mapea la tabla 'entregas' de la base de datos.
 *
 * @author Maximiliano Villalobos
 * @version 1.0
 */
public class Entrega {

    private int id;
    private int idRepartidor;
    private int idPedido;
    private Timestamp fechaEntrega;

    // Atributos auxiliares para facilitar el despliegue en Vistas / Tablas
    private String nombreRepartidor;
    private String detallePedido;

    /**
     * Constructor vacío por defecto.
     */
    public Entrega() {
    }

    /**
     * Constructor para registrar una nueva entrega (sin ID asignado en BD).
     *
     * @param idRepartidor Identificador del repartidor asignado.
     * @param idPedido     Identificador del pedido a entregar.
     */
    public Entrega(int idRepartidor, int idPedido) {
        this.idRepartidor = idRepartidor;
        this.idPedido = idPedido;
    }

    /**
     * Constructor completo con ID y timestamp.
     */
    public Entrega(int id, int idRepartidor, int idPedido, Timestamp fechaEntrega) {
        this.id = id;
        this.idRepartidor = idRepartidor;
        this.idPedido = idPedido;
        this.fechaEntrega = fechaEntrega;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Timestamp getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Timestamp fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getNombreRepartidor() {
        return nombreRepartidor;
    }

    public void setNombreRepartidor(String nombreRepartidor) {
        this.nombreRepartidor = nombreRepartidor;
    }

    public String getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(String detallePedido) {
        this.detallePedido = detallePedido;
    }

    @Override
    public String toString() {
        return "Entrega #" + id + " [Repartidor ID: " + idRepartidor + ", Pedido ID: " + idPedido + "]";
    }
}