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
import javax.swing.text.JTextComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CrearCuenta extends JDialog {
    
    private final JLabel LblUsuario;
    private final JTextField TxtUsuario;
    
    private final JLabel LblContra;
    private final JPasswordField PassContrasena;
    
    private final JLabel LblConfirmarContra;
    private final JPasswordField PassConfirmarContra;
    
    private final JButton BtnCrear;
    private final JButton BtnCancelar;
    
    private JLabel LblReqLongitud;
    private JLabel LblReqMayuscula;
    private JLabel LblReqSimbolo;
    private JLabel LblReqEspacios;
    
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
        
        setTitle("BATTLESHIP - Crear Cuenta");
        setContentPane(PanelFondo);
        setSize(750, 700);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel PanelInfo = new JPanel();
        PanelInfo.setLayout(new BoxLayout(PanelInfo, BoxLayout.Y_AXIS));
        PanelInfo.setBorder(BorderFactory.createEmptyBorder(100, 15, 0, 0));
        PanelInfo.setOpaque(false);
        
        LblUsuario = new JLabel("Usuario");
        LblUsuario.setForeground(Color.WHITE);
        EstilizarLabel(LblUsuario);
        TxtUsuario = new JTextField("");
        TxtUsuario.setMaximumSize(new Dimension(250, 45));
        EstilizarCampoTexto(TxtUsuario);
        
        LblContra = new JLabel("Contraseña");
        LblContra.setForeground(Color.WHITE);
        EstilizarLabel(LblContra);
        PassContrasena = new JPasswordField("");
        PassContrasena.setMaximumSize(new Dimension(250, 45));
        EstilizarCampoTexto(PassContrasena);
        
        PassContrasena.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ActualizarRequisitos(new String(PassContrasena.getPassword()));
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                ActualizarRequisitos(new String(PassContrasena.getPassword()));
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                ActualizarRequisitos(new String(PassContrasena.getPassword()));
            }
        });
        
        LblConfirmarContra = new JLabel("Confirmar Contraseña");
        LblConfirmarContra.setForeground(Color.WHITE);
        EstilizarLabel(LblConfirmarContra);
        PassConfirmarContra = new JPasswordField("");
        PassConfirmarContra.setMaximumSize(new Dimension(250, 45));
        EstilizarCampoTexto(PassConfirmarContra);
        
        PanelInfo.add(Box.createVerticalStrut(30));
        PanelInfo.add(LblUsuario);
        PanelInfo.add(TxtUsuario);
        PanelInfo.add(LblContra);
        PanelInfo.add(PassContrasena);
        PanelInfo.add(LblConfirmarContra);
        PanelInfo.add(PassConfirmarContra);
        PanelInfo.add(Box.createVerticalGlue());
        
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
        
        JPanel PanelCentral = new JPanel();
        PanelCentral.setLayout(new BorderLayout());
        PanelCentral.setOpaque(false);
        
        JPanel PanelIzquierdo = new JPanel();
        PanelIzquierdo.setLayout(new BorderLayout());
        PanelIzquierdo.setOpaque(false);
        PanelIzquierdo.add(PanelInfo, BorderLayout.CENTER);
        
        JPanel PanelDerecho = new JPanel();
        PanelDerecho.setLayout(new BorderLayout());
        PanelDerecho.setOpaque(false);
        
        JPanel PanelRequisitos = CrearPanelRequisitos();
        
        PanelDerecho.add(PanelRequisitos, BorderLayout.NORTH);
        
        JPanel PanelContenidos = new JPanel();
        PanelContenidos.setLayout(new GridLayout(1, 2, 30, 0));
        PanelContenidos.setOpaque(false);
        
        PanelContenidos.add(PanelIzquierdo);
        PanelContenidos.add(PanelDerecho);
        
        PanelCentral.add(PanelContenidos, BorderLayout.CENTER);
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(PanelCentral, BorderLayout.CENTER);
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
    
    private JPanel CrearPanelRequisitos() {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setOpaque(false);
        contenedor.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
        
        JPanel panelrequisitos = new JPanel();
        panelrequisitos.setLayout(new BoxLayout(panelrequisitos, BoxLayout.Y_AXIS));
        panelrequisitos.setBackground(new Color(20, 20, 35));
        panelrequisitos.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 3), BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        panelrequisitos.setPreferredSize(new Dimension(340, 195));
        panelrequisitos.setMinimumSize(new Dimension(340, 195));
        panelrequisitos.setMaximumSize(new Dimension(340, 195));
        
        JLabel titulo = new JLabel("REQUISITOS DE CONTRASEÑA");
        titulo.setForeground(new Color(220, 180, 120));
        titulo.setFont(new Font("DIN Condensed", Font.BOLD, 14));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelrequisitos.add(titulo);
        panelrequisitos.add(Box.createVerticalStrut(12));
        
        LblReqLongitud = CrearLabelRequisito("✗ Exactamente 5 caracteres", false);
        LblReqMayuscula = CrearLabelRequisito("✗ Al menos 1 mayuscula", false);
        LblReqSimbolo = CrearLabelRequisito("✗ Al menos 1 simbolo", false);
        LblReqEspacios = CrearLabelRequisito("✗ Sin espacios", false);
    
        panelrequisitos.add(LblReqLongitud);
        panelrequisitos.add(Box.createVerticalStrut(8));
        panelrequisitos.add(LblReqMayuscula);
        panelrequisitos.add(Box.createVerticalStrut(8));
        panelrequisitos.add(LblReqSimbolo);
        panelrequisitos.add(Box.createVerticalStrut(8));
        panelrequisitos.add(LblReqEspacios);
        panelrequisitos.add(Box.createVerticalGlue());
        
        contenedor.add(Box.createVerticalStrut(150));
        contenedor.add(panelrequisitos);
        contenedor.add(Box.createVerticalGlue());
        
        return contenedor;
    }
    
    private JLabel CrearLabelRequisito(String texto, boolean cumplido) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("DIN Condensed", Font.BOLD, 17));
        label.setForeground(cumplido ? new Color(100, 255, 100) : new Color(255, 100, 100));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        return label;
    }
    
    private void ActualizarRequisitos(String contrasena) {
        //Primer requisito
        boolean longitudok = contrasena.length() == 5;
        ActualizarLabelRequisito(LblReqLongitud, "Exactamente 5 caracteres", longitudok);
        
        //Segundo requisito
        boolean mayusculaok = false;
        
        for (int i = 0; i < contrasena.length(); i++) {
            if (Character.isUpperCase(contrasena.charAt(i))) {
                mayusculaok = true;
                break;
            }
        }
        
        ActualizarLabelRequisito(LblReqMayuscula, "Al menos 1 mayuscula", mayusculaok);
        
        //Tercer requisito
        String simbolos = "!#$/()?-_,.<>|";
        boolean simbolook = false;
        
        for (int i = 0; i < simbolos.length(); i++) {
            if (contrasena.indexOf(simbolos.charAt(i)) >= 0) {
                simbolook = true;
                break;
            }
        }
        
        ActualizarLabelRequisito(LblReqSimbolo, "Al menos 1 simbolo", simbolook);
        
        //Cuarto requisito
        boolean espaciosok = true;
        
        for (int i = 0; i < contrasena.length(); i++) {
            if (Character.isWhitespace(contrasena.charAt(i))) {
                espaciosok = false;
                break;
            }
        }
        
        ActualizarLabelRequisito(LblReqEspacios, "Sin espacios", espaciosok);
    }
    
    private void ActualizarLabelRequisito(JLabel label, String texto, boolean cumplido) {
        String icono = cumplido ? "✓" : "✗";
        label.setText(icono + " " + texto);
        label.setForeground(cumplido ? new Color(100, 255, 100) : new Color(255, 100, 100));
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
    
    private void EstilizarCampoTexto(JTextComponent campo) {
        campo.setFont(new Font("DIN Condensed", Font.BOLD, 18));
        campo.setBackground(new Color(25, 25, 25));
        campo.setForeground(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 0, 120), 2), BorderFactory.createEmptyBorder(5, 20, 5, 20)));
        campo.setOpaque(true);
        campo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        campo.setPreferredSize(new Dimension(220, 44));
        
        campo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                campo.setBackground(new Color(60, 0, 0));
                campo.setForeground(new Color(255, 220, 130));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                campo.setBackground(new Color(25, 25, 25));
                campo.setForeground(Color.WHITE);
            }
        });
    }
    
    private void EstilizarLabel(JLabel label) {
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setFont(new Font("DIN Condensed", Font.BOLD, 22));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(0, 0, 0, 180));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6), BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 30, 0))));
    }
}
