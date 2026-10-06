package ui;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import model.Entrega;
import model.Pedido;
import model.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel de interfaz gráfica para la gestión de Entregas.
 * Permite asociar un Repartidor con un Pedido y listar las entregas[cite: 2].
 *
 * @author Maximiliano Villalobos
 * @version 1.0
 */
public class GestionEntregasPanel extends JPanel {

    private JComboBox<Repartidor> cbRepartidor;
    private JComboBox<Pedido> cbPedido;
    private JTable tablaEntregas;
    private DefaultTableModel modeloTabla;

    private EntregaDAO entregaDAO;
    private RepartidorDAO repartidorDAO;
    private PedidoDAO pedidoDAO;

    public GestionEntregasPanel() {
        entregaDAO = new EntregaDAO();
        repartidorDAO = new RepartidorDAO();
        pedidoDAO = new PedidoDAO();

        setLayout(new BorderLayout(10, 10));

        // --- PANEL SUPERIOR: Formulario ---
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Nueva Entrega"));

        panelFormulario.add(new JLabel("Seleccionar Repartidor:"));
        cbRepartidor = new JComboBox<>();
        panelFormulario.add(cbRepartidor);

        panelFormulario.add(new JLabel("Seleccionar Pedido:"));
        cbPedido = new JComboBox<>();
        panelFormulario.add(cbPedido);

        JButton btnGuardar = new JButton("Asignar Entrega");
        btnGuardar.addActionListener(e -> guardarEntrega());
        panelFormulario.add(new JLabel()); // Espacio vacío para alinear
        panelFormulario.add(btnGuardar);

        add(panelFormulario, BorderLayout.NORTH);

        // --- PANEL CENTRAL: Tabla ---
        modeloTabla = new DefaultTableModel(new Object[]{"ID Entrega", "Repartidor", "Detalle Pedido", "Fecha/Hora"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita la edición directa en la tabla[cite: 2]
            }
        };
        tablaEntregas = new JTable(modeloTabla);
        add(new JScrollPane(tablaEntregas), BorderLayout.CENTER);

        // --- PANEL INFERIOR: Botones de Acción ---
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnActualizar = new JButton("Actualizar Listas");
        btnActualizar.addActionListener(e -> actualizarDatos());
        panelAcciones.add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar Entrega");
        btnEliminar.addActionListener(e -> eliminarEntrega());
        panelAcciones.add(btnEliminar);

        add(panelAcciones, BorderLayout.SOUTH);

        // Cargar datos iniciales en Combos y Tabla
        actualizarDatos();
    }

    private void guardarEntrega() {
        Repartidor repartidor = (Repartidor) cbRepartidor.getSelectedItem();
        Pedido pedido = (Pedido) cbPedido.getSelectedItem();

        if (repartidor == null || pedido == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un repartidor y un pedido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Se extrae el ID internamente desde el objeto seleccionado en el JComboBox[cite: 2]
        Entrega entrega = new Entrega(repartidor.getId(), pedido.getId());

        if (entregaDAO.registrarEntrega(entrega)) {
            JOptionPane.showMessageDialog(this, "Entrega registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar la entrega.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEntrega() {
        int filaSeleccionada = tablaEntregas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una entrega de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar esta entrega?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (entregaDAO.eliminarEntrega(id)) {
                JOptionPane.showMessageDialog(this, "Entrega eliminada correctamente.");
                actualizarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la entrega.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Refresca los JComboBox cuando se crean, editan o eliminan entidades[cite: 2].
     * También recarga la tabla principal.
     */
    public void actualizarDatos() {
        // Recargar ComboBox de Repartidores
        cbRepartidor.removeAllItems();
        for (Repartidor r : repartidorDAO.listarRepartidores()) {
            cbRepartidor.addItem(r);
        }

        // Recargar ComboBox de Pedidos
        cbPedido.removeAllItems();
        for (Pedido p : pedidoDAO.listarPedidos()) {
            cbPedido.addItem(p);
        }

        // Recargar Tabla de Entregas
        modeloTabla.setRowCount(0);
        for (Entrega e : entregaDAO.listarEntregasConDetalle()) {
            modeloTabla.addRow(new Object[]{
                    e.getId(),
                    e.getNombreRepartidor(),
                    e.getDetallePedido(),
                    e.getFechaEntrega()
            });
        }
    }
}