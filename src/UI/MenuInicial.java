/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package UI;

/**
 *
 * @author USUARIO
 */

import javax.swing.*;
import java.awt.*;
import ManejoCuentas.MemoriaCuentas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuInicial extends JFrame {
    
    private final JButton BtnIniciarSesion;
    private final JButton BtnCrearCuenta;
    private final JButton BtnSalir;
    private final MemoriaCuentas Memoria;
    
    public MenuInicial() {
        this(new MemoriaCuentas(60));
    }
    
    public MenuInicial(MemoriaCuentas Memoria) {
        this.Memoria = Memoria;
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_inicial.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("BATTLESHIP");
        this.setContentPane(PanelFondo);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS));
        PanelBotones.setOpaque(false);
        
        BtnIniciarSesion = new JButton("INICIAR SESION");
        BtnIniciarSesion.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        BtnIniciarSesion.addActionListener(e -> AbrirIniciarSesion());
        BtnIniciarSesion.setBounds(330, 460, 150, 30);
        EstilizarBoton(BtnIniciarSesion);
        
        BtnCrearCuenta = new JButton("CREAR CUENTA");
        BtnCrearCuenta.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        BtnCrearCuenta.addActionListener(e -> AbrirCrearCuenta());
        BtnCrearCuenta.setBounds(330, 460, 150, 30);
        EstilizarBoton(BtnCrearCuenta);
        
        BtnSalir = new JButton("SALIR");
        BtnSalir.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        BtnSalir.addActionListener(e -> onSalir());
        BtnSalir.setBounds(330, 460, 150, 30);
        EstilizarBoton(BtnSalir);
        
        PanelBotones.add(Box.createVerticalStrut(10));
        PanelBotones.add(BtnIniciarSesion);
        PanelBotones.add(Box.createVerticalStrut(6));
        PanelBotones.add(BtnCrearCuenta);
        PanelBotones.add(Box.createVerticalStrut(6));
        PanelBotones.add(BtnSalir);
        PanelBotones.add(Box.createVerticalStrut(10));
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(PanelBotones, BorderLayout.SOUTH);
        PanelFondo.repaint();
    }
    
    private void AbrirIniciarSesion() {
        IniciarSesion iniciarSesion = new IniciarSesion(this, Memoria, true);
        iniciarSesion.setVisible(true);
    }
    
    private void AbrirCrearCuenta() {
        CrearCuenta crearCuenta = new CrearCuenta(this, Memoria, true);
        crearCuenta.setVisible(true);
    }
    
    public void onLoginExitoso(String usuario) {
        if (usuario == null || usuario.isBlank()) {
            MostrarMensaje("Usuario Invalido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        dispose();
        new MenuPrincipal(Memoria, usuario).setVisible(true);
    }
    
    private void onSalir() {
        int Opcion = MostrarConfirmacion("Estas seguro que quieres salir?", "Confirmacion");
        
        if (Opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    private void EstilizarBoton(JButton boton) {
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
    
    private void MostrarMensaje(String mensaje, String titulo, int tipo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(20, 20, 35));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 3), BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        JLabel lblmensaje = new JLabel("<html><div style='text-align: center; width: 250px;'>" + mensaje.replace("\n", "<br>") + "</div></html>");
        lblmensaje.setForeground(Color.WHITE);
        lblmensaje.setFont(new Font("DIN Condensed", Font.BOLD, 16));
        lblmensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lblmensaje);
        
        UIManager.put("OptionPane.background", new Color(20, 20, 35));
        UIManager.put("Panel.background", new Color(20, 20, 35));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(25, 25, 25));
        UIManager.put("Button.foreground", new Color(220, 180, 120));
        UIManager.put("Button.border", BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 1), BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        
        JOptionPane.showMessageDialog(this, panel, titulo, tipo);
        
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Button.border", null);
    }
    
    private int MostrarConfirmacion(String mensaje, String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(20, 20, 35));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 3), BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        JLabel lblmensaje = new JLabel("<html><div style='text-align: center; width: 250px;'>" + mensaje.replace("\n", "<br>") + "</div></html>");
        lblmensaje.setForeground(Color.WHITE);
        lblmensaje.setFont(new Font("DIN Condensed", Font.BOLD, 16));
        lblmensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lblmensaje);
        
        UIManager.put("OptionPane.background", new Color(20, 20, 35));
        UIManager.put("Panel.background", new Color(20, 20, 35));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(25, 25, 25));
        UIManager.put("Button.foreground", new Color(220, 180, 120));
        UIManager.put("Button.border", BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 1), BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        
        int resultado = JOptionPane.showConfirmDialog(this, panel, titulo, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Button.border", null);
        
        return resultado;
    }
}
