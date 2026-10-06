package ui;

import dao.RepartidorDAO;
import model.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel de interfaz gráfica para la gestión de Repartidores.
 * Permite registrar, listar y eliminar repartidores.
 *
 * @author Maximiliano Villalobos
 * @version 1.0
 */
public class GestionRepartidoresPanel extends JPanel {

    private JTextField txtNombre;
    private JTable tablaRepartidores;
    private DefaultTableModel modeloTabla;
    private RepartidorDAO repartidorDAO;

    public GestionRepartidoresPanel() {
        repartidorDAO = new RepartidorDAO();
        setLayout(new BorderLayout(10, 10));

        // --- PANEL SUPERIOR: Formulario ---
        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Repartidor"));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre);

        JButton btnGuardar = new JButton("Guardar Repartidor");
        btnGuardar.addActionListener(e -> guardarRepartidor());
        panelFormulario.add(btnGuardar);

        add(panelFormulario, BorderLayout.NORTH);

        // --- PANEL CENTRAL: Tabla ---
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer tabla de solo lectura
            }
        };
        tablaRepartidores = new JTable(modeloTabla);
        add(new JScrollPane(tablaRepartidores), BorderLayout.CENTER);

        // --- PANEL INFERIOR: Botones de Acción ---
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        btnEliminar.addActionListener(e -> eliminarRepartidor());
        panelAcciones.add(btnEliminar);

        add(panelAcciones, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarTabla();
    }

    private void guardarRepartidor() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el nombre del repartidor.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Repartidor r = new Repartidor(nombre);
        if (repartidorDAO.agregarRepartidor(r)) {
            JOptionPane.showMessageDialog(this, "Repartidor guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            txtNombre.setText("");
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el repartidor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarRepartidor() {
        int filaSeleccionada = tablaRepartidores.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un repartidor de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este repartidor?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (repartidorDAO.eliminarRepartidor(id)) {
                JOptionPane.showMessageDialog(this, "Repartidor eliminado correctamente.");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el repartidor (puede estar asignado a una entrega).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Repartidor> lista = repartidorDAO.listarRepartidores();
        for (Repartidor r : lista) {
            modeloTabla.addRow(new Object[]{r.getId(), r.getNombre()});
        }
    }
}