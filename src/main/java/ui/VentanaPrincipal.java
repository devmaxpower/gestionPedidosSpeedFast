package ui;

import javax.swing.*;

/**
 * Ventana principal de la aplicación SpeedFast.
 * Integra los paneles de gestión mediante pestañas (JTabbedPane) para
 * interactuar con la base de datos.
 *
 * @author Maximiliano Villalobos
 * @version 1.0
 */
public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        // Configuración básica del JFrame
        setTitle("Sistema de Gestión - SpeedFast");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en la pantalla

        // Crear el contenedor de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Instanciar los paneles de cada módulo
        GestionRepartidoresPanel panelRepartidores = new GestionRepartidoresPanel();
        GestionPedidosPanel panelPedidos = new GestionPedidosPanel();
        GestionEntregasPanel panelEntregas = new GestionEntregasPanel();

        // Agregar los paneles a las pestañas
        tabbedPane.addTab("Gestión de Repartidores", panelRepartidores);
        tabbedPane.addTab("Gestión de Pedidos", panelPedidos);
        tabbedPane.addTab("Gestión de Entregas", panelEntregas);

        // Listener para recargar los datos de la base de datos cada vez que se cambia de pestaña
        tabbedPane.addChangeListener(e -> {
            int index = tabbedPane.getSelectedIndex();
            if (index == 0) {
                panelRepartidores.cargarTabla();
            } else if (index == 1) {
                panelPedidos.cargarTabla();
            } else if (index == 2) {
                panelEntregas.actualizarDatos();
            }
        });

        // Agregar el JTabbedPane a la ventana principal
        add(tabbedPane);
    }

    /**
     * Método principal (Entry Point) para iniciar la aplicación.
     */
    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica en el hilo de eventos de Swing de forma segura[cite: 3]
        SwingUtilities.invokeLater(() -> {
            try {
                // Cambiar el diseño visual para que se vea como el sistema operativo nativo
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Error al configurar el LookAndFeel: " + e.getMessage());
            }

            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}