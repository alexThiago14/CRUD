package app.vista.taller2;

//import taller.taller;

import app.controlador.Servicio;
import app.controlador.Usuario;
import app.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class Catalogos extends JFrame{
    private JTextField textCodigo;
    private JTextField textPrecio;
    private JButton nuevoButton;
    private JTextField textNombre;
    private JButton mostrarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JTable tableProduc;
    private JPanel catalogos;
    private JButton IMPRIMIRREPORTEButton;
    private JButton salirButton;
    private DefaultTableModel model;
    private JFrame frame;

    private Object[] columns = {"ID","Codigo","Producto","Precio"};
    private Object[] row = new Object[4];
    private JLabel LblTitulo,LblId,LblPrecio,LblProducto;
    //private JButton nuevoButton,mostrarButton,actualizarButton,eliminarButton;
    private JScrollPane scrollPane = null;
    private Servicio servicio = new Servicio();
    private Map<Integer, Producto> mapa = null;
    private String clave;

    public void obtenerRegistroTabla() {
        model = new DefaultTableModel(){
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int filas, int columnas)
            {return false;}
        };
        model.setColumnIdentifiers(columns);
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        mapa = servicio.seleccionarTodo();

        for (Map.Entry<Integer, Producto> entry : mapa.entrySet()) {
            row[0] = entry.getKey();
            row[1] = entry.getValue().getCodigo();
            row[2] = entry.getValue().getNombre();
            row[3] = String.format("%.2f", entry.getValue().getPrecio());
            model.addRow(row);
        }
        limpiarCampos();
        tableProduc.setModel(model);
    }
    public void limpiarCampos(){
        textCodigo.setText("");
        textNombre.setText("");
        textPrecio.setText("");
    }
//    private void initialize(){
//        frame = new JFrame();
//        frame.setBounds(100,100,450,600);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        tableProduc = new JTable();
//
//
//
//
//        tableProduc.getColumnModel().getColumn(0).setPreferredWidth(50);
//        tableProduc.getColumnModel().getColumn(1).setPreferredWidth(50);
//        tableProduc.getColumnModel().getColumn(2).setPreferredWidth(180);
//        tableProduc.getColumnModel().getColumn(3).setPreferredWidth(50);
//
//    }
    public Catalogos(){
        setTitle("Catalogos");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(catalogos);
        setLocationRelativeTo(null);
        obtenerRegistroTabla();
        JOptionPane.showMessageDialog(null, "Bienvenido, " + Usuario.getUsuario() + "!");
        tableProduc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i=tableProduc.getSelectedRow();
                clave=model.getValueAt(i,0).toString();
                textCodigo.setText(model.getValueAt(i,1).toString());
                textNombre.setText(model.getValueAt(i,2).toString());
                textPrecio.setText(model.getValueAt(i,3).toString());

            }
        });
        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = textCodigo.getText().trim();
                String nombre = textNombre.getText().trim();
                String precioStr = textPrecio.getText().trim().replace(",", ".");

                if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.");
                    return;
                }

                try {
                    double precio = Double.parseDouble(precioStr);
                    servicio.insertar(new Producto(codigo, nombre, precio));
                    obtenerRegistroTabla();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Precio inválido.");
                }
            }
        });

        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obtenerRegistroTabla();
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id=Integer.parseInt(clave);
                String codigo=textCodigo.getText();
                String nombre=textNombre.getText();
                String precioStr=textPrecio.getText().replace(",",".");
                double precio=Double.parseDouble(precioStr);
                servicio.actualizar(new Producto(id,codigo,nombre,precio));
                obtenerRegistroTabla();
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id=Integer.parseInt(clave);
                servicio.eliminar(id);
                obtenerRegistroTabla();
            }
        });
        IMPRIMIRREPORTEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    tableProduc.print();
                }catch (Exception e2){
                    System.out.println(e2.getMessage());
                }
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                new Login().setVisible(true); // Abre la ventana de login
            }
        });

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {new Catalogos().setVisible(true);});
    }
}
