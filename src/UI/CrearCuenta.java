/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author Hp
 */

import ManejoCuentas.MemoriaCuentas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CrearCuenta extends JDialog {
    
    private JLabel LblUsuario;
    private JTextField TxtUsuario;
    
    private JLabel LblContra;
    private JPasswordField PassContrasena;
    
    private JLabel LblConfirmarContra;
    private JPasswordField PassConfirmarContra;
    
    private JButton BtnCrear;
    private JButton BtnCancelar;
    
    private final MemoriaCuentas Memoria;
    private final MenuInicial menuInicial;

    public CrearCuenta(MenuInicial menuInicial, MemoriaCuentas Memoria, boolean modal) {
        super(menuInicial, modal);
        this.Memoria = Memoria;
        this.menuInicial = menuInicial;
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_crearcuenta.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("Vampire Wargame - Crear Cuenta");
        setContentPane(PanelFondo);
        setSize(700, 700);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        JPanel PanelInfo = new JPanel();
        PanelInfo.setLayout(new BoxLayout(PanelInfo, BoxLayout.Y_AXIS));
        PanelInfo.setBorder(BorderFactory.createEmptyBorder(100, 15, 0, 0));
        PanelInfo.setOpaque(false);
        
        LblUsuario = new JLabel("Usuario");
        LblUsuario.setForeground(Color.WHITE);
        TxtUsuario = new JTextField("");
        TxtUsuario.setMaximumSize(new Dimension(250, 45));
        
        LblContra = new JLabel("Contraseña");
        LblContra.setForeground(Color.WHITE);
        PassContrasena = new JPasswordField("");
        PassContrasena.setMaximumSize(new Dimension(250, 45));
        
        LblConfirmarContra = new JLabel("Confirmar Contraseña");
        LblConfirmarContra.setForeground(Color.WHITE);
        PassConfirmarContra = new JPasswordField("");
        PassConfirmarContra.setMaximumSize(new Dimension(250, 45));
        
        PanelInfo.add(LblUsuario);
        PanelInfo.add(TxtUsuario);
        PanelInfo.add(LblContra);
        PanelInfo.add(PassContrasena);
        PanelInfo.add(LblConfirmarContra);
        PanelInfo.add(PassConfirmarContra);
        
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.X_AXIS));
        PanelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        PanelBotones.setOpaque(false);
        
        BtnCrear = new JButton("CREAR CUENTA");
        BtnCrear.addActionListener(e -> onCrear());
        EstilizarBoton(BtnCrear);
        
        BtnCancelar = new JButton("CANCELAR");
        BtnCancelar.addActionListener(e -> onSalir());
        EstilizarBoton(BtnCancelar);
        
        PanelBotones.add(Box.createHorizontalStrut(40));
        PanelBotones.add(Box.createHorizontalGlue());
        PanelBotones.add(BtnCrear);
        PanelBotones.add(Box.createHorizontalStrut(60));
        PanelBotones.add(BtnCancelar);
        PanelBotones.add(Box.createHorizontalGlue());
        PanelBotones.add(Box.createHorizontalStrut(40));
        
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(PanelInfo, BorderLayout.CENTER);
        PanelFondo.add(PanelBotones, BorderLayout.SOUTH);
        PanelFondo.repaint();
    }
    
    public void mostrar() {
        LimpiarCampos();
        setLocationRelativeTo(menuInicial);        
        getRootPane().setDefaultButton(BtnCrear);
        setVisible(true);
    }
    
    public void LimpiarCampos() {
        TxtUsuario.setText("");
        PassContrasena.setText("");
        PassConfirmarContra.setText("");
        TxtUsuario.requestFocus();
    }
    
    public void onCrear() {
        String usuario = TxtUsuario.getText();        
        String contrasena = new String(PassContrasena.getPassword());        
        String confirmarcontra = new String(PassConfirmarContra.getPassword());
        
        if (usuario.isEmpty() || contrasena.isEmpty() || confirmarcontra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese algun dato", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        for (int i = 0; i < usuario.length(); i++) {
            if (Character.isWhitespace(usuario.charAt(i))) {
                JOptionPane.showMessageDialog(this, "El usuario no puede contener espacios", "Error", JOptionPane.ERROR_MESSAGE);
                TxtUsuario.requestFocus();
                return;
            }
        }
        
        if (contrasena.length() != 5 || confirmarcontra.length() != 5) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener exactamente 5 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            PassContrasena.requestFocus();
            return;
        }
        
        for (int i = 0; i < contrasena.length(); i++) {
            if (Character.isWhitespace(contrasena.charAt(i))) {
                JOptionPane.showMessageDialog(this, "La contraseña no puede contener espacios", "Error", JOptionPane.ERROR_MESSAGE);
                PassContrasena.requestFocus();
                return;
            }
        }
        
        boolean tienemayuscula = false;
        
        for (int i = 0; i < contrasena.length(); i++) {
            if (Character.isUpperCase(contrasena.charAt(i))) {
                tienemayuscula = true;
                break;
            }
        }
        
        if (!tienemayuscula) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos una letra mayuscula", "Error", JOptionPane.ERROR_MESSAGE);
            PassContrasena.requestFocus();
            return;
        }
        
        String simbolos = "!#$/()?-_.,<>|";
        boolean TieneSimbolo = false;
        
        for (int i = 0; i < simbolos.length(); i++) {            
            if (contrasena.indexOf(simbolos.charAt(i)) >= 0) {
                TieneSimbolo = true;
                break;
            }
        }
            
        if (!TieneSimbolo) {
            JOptionPane.showMessageDialog(this, "La contraseña tiene que tener como minimo un simbolo", "Error", JOptionPane.ERROR_MESSAGE);
            PassContrasena.requestFocus();
            return;
        }
        
        if (!contrasena.equals(confirmarcontra)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas deben ser iguales", "Error", JOptionPane.ERROR_MESSAGE);
            PassConfirmarContra.requestFocus();
            return;
        }
        
        if (Memoria.isFull()) {
            JOptionPane.showMessageDialog(this, "Capacidad de cuentas llena", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Memoria.ExisteUsuario(usuario)) {
            JOptionPane.showMessageDialog(this, "El usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (Memoria.Agregar(usuario, contrasena)) {
            JOptionPane.showMessageDialog(this, "Cuenta creada exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            menuInicial.onLoginExitoso(usuario);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Hubo un error creando la cuenta", "Error", JOptionPane.ERROR_MESSAGE);
            LimpiarCampos();
        }
    }
    
    private void onSalir() {        
        dispose();
    }
    
    private void EstilizarBoton(JButton boton) {
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFont(new Font("DIN Condensed", Font.BOLD, 18));
        boton.setBackground(new Color(25, 25, 25)); //Gris oscuro tipo metal
        boton.setForeground(new Color(220, 180, 120)); //Dorado suave
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 2), BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        boton.setOpaque(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(220, 44));
        
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(0, 0, 60));
                boton.setForeground(new Color(255, 220, 130));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(25, 25, 25));
                boton.setForeground(new Color(220, 180, 80));
            }
        });
    }
}
