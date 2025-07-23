package app.vista.taller2;

import app.controlador.Servicio;
import app.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Map;

public class Catalogos extends JFrame {
    private JTextField textCodigo;
    private JTextField textPrecio;
    private JButton nuevoButton;
    private JTextField textNombre;
    private JButton mostrarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JTable tableProduc;
    private JPanel catalogos;
    private DefaultTableModel model;
    private Servicio servicio = new Servicio();
    private Map<Integer, Producto> mapa = null;

    private Object[] columns = {"ID", "Codigo", "Producto", "Precio"};

    public void obtenerRegistroTabla() {
        model = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                return false;
            }
        };
        model.setColumnIdentifiers(columns);
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        mapa = servicio.seleccionarTodo();
        for (Map.Entry<Integer, Producto> entry : mapa.entrySet()) {
            Object[] row = {
                    entry.getKey(),
                    entry.getValue().getCodigo(),
                    entry.getValue().getNombre(),
                    String.format("%.2f", entry.getValue().getPrecio())
            };
            model.addRow(row);
        }
        limpiarCampos();
        tableProduc.setModel(model);
    }

    public void limpiarCampos() {
        textCodigo.setText("");
        textNombre.setText("");
        textPrecio.setText("");
    }

    public Catalogos() {
        setTitle("Catálogos");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(catalogos);
        setLocationRelativeTo(null);

        // Configurar la tabla
        tableProduc.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableProduc.getSelectedRow();
                if (selectedRow >= 0) {
                    textCodigo.setText((String) model.getValueAt(selectedRow, 1));
                    textNombre.setText((String) model.getValueAt(selectedRow, 2));
                    textPrecio.setText((String) model.getValueAt(selectedRow, 3));
                }
            }
        });

        // ActionListener para botones
        mostrarButton.addActionListener(e -> obtenerRegistroTabla());

        nuevoButton.addActionListener(e -> {
            try {
                String codigo = textCodigo.getText();
                String nombre = textNombre.getText();
                double precio = Double.parseDouble(textPrecio.getText());
                servicio.insertar(new Producto(codigo, nombre, precio));
                obtenerRegistroTabla();
                JOptionPane.showMessageDialog(null, "Producto insertado correctamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Precio debe ser un número válido");
            }
        });

        actualizarButton.addActionListener(e -> {
            try {
                int selectedRow = tableProduc.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) model.getValueAt(selectedRow, 0);
                    String codigo = textCodigo.getText();
                    String nombre = textNombre.getText();
                    double precio = Double.parseDouble(textPrecio.getText());
                    servicio.actualizar(new Producto(id, codigo, nombre, precio));
                    obtenerRegistroTabla();
                    JOptionPane.showMessageDialog(null, "Producto actualizado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un producto para actualizar");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Precio debe ser un número válido");
            }
        });

        eliminarButton.addActionListener(e -> {
            int selectedRow = tableProduc.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) model.getValueAt(selectedRow, 0);
                servicio.eliminar(id);
                obtenerRegistroTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar");
            }
        });

        obtenerRegistroTabla();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Catalogos().setVisible(true));
    }
}