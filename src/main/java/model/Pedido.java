package model;

/**
 * Clase que representa un Pedido dentro del sistema SpeedFast.
 * Mapea la tabla 'pedidos' de la base de datos.
 *
 * @author Maximiliano Villalobos
 * @version 1.0
 */
public class Pedido {

    private int id;
    private String direccionOrigen;
    private String direccionDestino;
    private TipoPedido tipoPedido;
    private EstadoPedido estado;
    private double precio;

    /**
     * Constructor vacío por defecto.
     */
    public Pedido() {
    }

    /**
     * Constructor para crear un nuevo pedido (sin ID asignado en BD).
     *
     * @param direccionOrigen  Dirección donde se recoge el paquete.
     * @param direccionDestino Dirección de entrega.
     * @param tipoPedido       Categoría del pedido (COMIDA, ENCOMIENDA, EXPRESS).
     * @param estado           Estado inicial del pedido (PENDIENTE, EN_REPARTO, ENTREGADO).
     * @param precio           Costo del envío.
     */
    public Pedido(String direccionOrigen, String direccionDestino, TipoPedido tipoPedido, EstadoPedido estado, double precio) {
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
        this.tipoPedido = tipoPedido;
        this.estado = estado;
        this.precio = precio;
    }

    /**
     * Constructor completo con ID.
     */
    public Pedido(int id, String direccionOrigen, String direccionDestino, TipoPedido tipoPedido, EstadoPedido estado, double precio) {
        this.id = id;
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
        this.tipoPedido = tipoPedido;
        this.estado = estado;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccionOrigen() {
        return direccionOrigen;
    }

    public void setDireccionOrigen(String direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(TipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Pedido #" + id + " [" + tipoPedido + " - $" + precio + "]";
    }
}