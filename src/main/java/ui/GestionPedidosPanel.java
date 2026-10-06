package ui;

import dao.PedidoDAO;
import model.EstadoPedido;
import model.Pedido;
import model.TipoPedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionPedidosPanel extends JPanel {

    private JTextField txtDireccion;
    private JComboBox<TipoPedido> cbTipoPedido;
    private JComboBox<EstadoPedido> cbEstadoPedido;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private PedidoDAO pedidoDAO;

    public GestionPedidosPanel() {
        pedidoDAO = new PedidoDAO();
        setLayout(new BorderLayout(10, 10));

        // --- PANEL SUPERIOR ---
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Pedido"));

        panelFormulario.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelFormulario.add(txtDireccion);

        panelFormulario.add(new JLabel("Tipo de Pedido:"));
        cbTipoPedido = new JComboBox<>(TipoPedido.values());
        panelFormulario.add(cbTipoPedido);

        panelFormulario.add(new JLabel("Estado:"));
        cbEstadoPedido = new JComboBox<>(EstadoPedido.values());
        panelFormulario.add(cbEstadoPedido);

        JButton btnGuardar = new JButton("Guardar Pedido");
        btnGuardar.addActionListener(e -> guardarPedido());
        panelFormulario.add(new JLabel());
        panelFormulario.add(btnGuardar);

        add(panelFormulario, BorderLayout.NORTH);

        // --- PANEL CENTRAL ---
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Dirección", "Tipo", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaPedidos = new JTable(modeloTabla);
        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        // --- PANEL INFERIOR ---
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        btnEliminar.addActionListener(e -> eliminarPedido());
        panelAcciones.add(btnEliminar);
        add(panelAcciones, BorderLayout.SOUTH);

        cargarTabla();
    }

    private void guardarPedido() {
        String direccion = txtDireccion.getText().trim();
        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La dirección es obligatoria.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TipoPedido tipo = (TipoPedido) cbTipoPedido.getSelectedItem();
        EstadoPedido estado = (EstadoPedido) cbEstadoPedido.getSelectedItem();

        Pedido pedido = new Pedido(direccion, tipo, estado);

        if (pedidoDAO.agregarPedido(pedido)) {
            JOptionPane.showMessageDialog(this, "Pedido registrado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            txtDireccion.setText("");
            cbTipoPedido.setSelectedIndex(0);
            cbEstadoPedido.setSelectedIndex(0);
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPedido() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar Pedido #" + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (pedidoDAO.eliminarPedido(id)) {
                JOptionPane.showMessageDialog(this, "Eliminado correctamente.");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Pedido> lista = pedidoDAO.listarPedidos();
        for (Pedido p : lista) {
            modeloTabla.addRow(new Object[]{ p.getId(), p.getDireccion(), p.getTipoPedido(), p.getEstado() });
        }
    }
}