package ui;

import dao.PedidoDAO;
import model.EstadoPedido;
import model.Pedido;
import model.TipoPedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel de interfaz gráfica para la gestión de Pedidos.
 * Permite registrar, listar y eliminar pedidos utilizando los Enums TipoPedido y EstadoPedido.
 *
 * @author Maximiliano Villalobos
 * @version 1.0
 */
public class GestionPedidosPanel extends JPanel {

    private JTextField txtOrigen;
    private JTextField txtDestino;
    private JTextField txtPrecio;
    private JComboBox<TipoPedido> cbTipoPedido;
    private JComboBox<EstadoPedido> cbEstadoPedido;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private PedidoDAO pedidoDAO;

    public GestionPedidosPanel() {
        pedidoDAO = new PedidoDAO();
        setLayout(new BorderLayout(10, 10));

        // --- PANEL SUPERIOR: Formulario ---
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Pedido"));

        panelFormulario.add(new JLabel("Dirección Origen:"));
        txtOrigen = new JTextField();
        panelFormulario.add(txtOrigen);

        panelFormulario.add(new JLabel("Dirección Destino:"));
        txtDestino = new JTextField();
        panelFormulario.add(txtDestino);

        panelFormulario.add(new JLabel("Tipo de Pedido:"));
        cbTipoPedido = new JComboBox<>(TipoPedido.values());
        panelFormulario.add(cbTipoPedido);

        panelFormulario.add(new JLabel("Estado:"));
        cbEstadoPedido = new JComboBox<>(EstadoPedido.values());
        panelFormulario.add(cbEstadoPedido);

        panelFormulario.add(new JLabel("Precio ($):"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        JButton btnGuardar = new JButton("Guardar Pedido");
        btnGuardar.addActionListener(e -> guardarPedido());
        panelFormulario.add(new JLabel()); // Espacio vacío para alinear botón
        panelFormulario.add(btnGuardar);

        add(panelFormulario, BorderLayout.NORTH);

        // --- PANEL CENTRAL: Tabla ---
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Origen", "Destino", "Tipo", "Estado", "Precio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPedidos = new JTable(modeloTabla);
        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        // --- PANEL INFERIOR: Botones de Acción ---
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        btnEliminar.addActionListener(e -> eliminarPedido());
        panelAcciones.add(btnEliminar);

        add(panelAcciones, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarTabla();
    }

    private void guardarPedido() {
        String origen = txtOrigen.getText().trim();
        String destino = txtDestino.getText().trim();
        String precioStr = txtPrecio.getText().trim();

        if (origen.isEmpty() || destino.isEmpty() || precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TipoPedido tipo = (TipoPedido) cbTipoPedido.getSelectedItem();
        EstadoPedido estado = (EstadoPedido) cbEstadoPedido.getSelectedItem();

        Pedido pedido = new Pedido(origen, destino, tipo, estado, precio);

        if (pedidoDAO.agregarPedido(pedido)) {
            JOptionPane.showMessageDialog(this, "Pedido registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPedido() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el Pedido #" + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (pedidoDAO.eliminarPedido(id)) {
                JOptionPane.showMessageDialog(this, "Pedido eliminado correctamente.");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        txtOrigen.setText("");
        txtDestino.setText("");
        txtPrecio.setText("");
        cbTipoPedido.setSelectedIndex(0);
        cbEstadoPedido.setSelectedIndex(0);
    }

    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Pedido> lista = pedidoDAO.listarPedidos();
        for (Pedido p : lista) {
            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getDireccionOrigen(),
                    p.getDireccionDestino(),
                    p.getTipoPedido(),
                    p.getEstado(),
                    "$" + p.getPrecio()
            });
        }
    }
}