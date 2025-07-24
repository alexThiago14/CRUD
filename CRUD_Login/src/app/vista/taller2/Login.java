package app.vista.taller2;

import app.controlador.Usuario;
import app.modelo.UsuarioServicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel panelLogin;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;


    public Login() {
        setTitle("Login - Sistema de Ventas");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        // Título
        JLabel titulo = new JLabel("BIENVENIDOS AL SISTEMA DE VENTAS");
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titulo, gbc);

        // Etiqueta Usuario
        gbc.gridwidth = 1;
        gbc.gridy++;
        add(new JLabel("Usuario:"), gbc);

        // Campo Usuario
        txtUsuario = new JTextField(15);
        gbc.gridx = 1;
        add(txtUsuario, gbc);

        // Etiqueta Contraseña
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Contraseña:"), gbc);

        // Campo Contraseña
        txtContrasena = new JPasswordField(15);
        gbc.gridx = 1;
        add(txtContrasena, gbc);

        // Botón Ingresar
        btnIngresar = new JButton("Ingresar");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(btnIngresar, gbc);

        // Acción del botón
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = txtUsuario.getText().trim();
                String password = String.valueOf(txtContrasena.getPassword());

                UsuarioServicio servicio = new UsuarioServicio();
                if (servicio.validar(user, password)) {
                    Usuario.setUsuario(user);
                    dispose(); // Cierra el login
                    new Catalogos().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                }
            }
        });

        ;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}