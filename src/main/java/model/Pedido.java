package model;

/**
 * Clase que representa un Pedido dentro del sistema SpeedFast.
 * Mapea exactamente la tabla 'pedidos' de la guía.
 */
public class Pedido {

    private int id;
    private String direccion;
    private TipoPedido tipoPedido;
    private EstadoPedido estado;

    public Pedido() {
    }

    public Pedido(String direccion, TipoPedido tipoPedido, EstadoPedido estado) {
        this.direccion = direccion;
        this.tipoPedido = tipoPedido;
        this.estado = estado;
    }

    public Pedido(int id, String direccion, TipoPedido tipoPedido, EstadoPedido estado) {
        this.id = id;
        this.direccion = direccion;
        this.tipoPedido = tipoPedido;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public TipoPedido getTipoPedido() { return tipoPedido; }
    public void setTipoPedido(TipoPedido tipoPedido) { this.tipoPedido = tipoPedido; }
    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Pedido #" + id + " [" + tipoPedido + " - " + estado + "]";
    }
}