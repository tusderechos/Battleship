package UI;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Hp
 */

import ManejoCuentas.MemoriaCuentas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MiCuenta extends JFrame {
    
    public JButton BtnCambiarPass; //Cambiar a modificar datos
    public JButton BtnCerrarCuenta; //cambiar a eliminar cuenta
    public JButton BtnSalir;
    
    public JLabel LblUsuario;
    public JLabel LblPuntos;
    public JLabel LblFechaIngreso;
    public JLabel LblActivo;
    
    private final String UsuarioActivo;
    private final MenuPrincipal menuPrincipal;
    private final MemoriaCuentas Memoria;
    
    public MiCuenta (MemoriaCuentas Memoria, String UsuarioActivo, MenuPrincipal menuPrincipal) {
        this.Memoria = Memoria;
        this.UsuarioActivo = (UsuarioActivo == null) ? "" : UsuarioActivo.trim();
        this.menuPrincipal = menuPrincipal;
        
        if (this.UsuarioActivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero inicia sesion o crea una cuenta!", "Aviso", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_micuenta.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
    
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("Vampire Wargame - Mi Cuenta");
        this.setContentPane(PanelFondo);
        setSize(800, 700);
        setResizable(false);
        setLocationRelativeTo(menuPrincipal);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        BtnCambiarPass = new JButton("Cambiar Contraseña");
//        BtnCambiarPass.addActionListener(e -> ModificarDatos());

        BtnCerrarCuenta = new JButton("Cerrar Cuenta");
        BtnCerrarCuenta.addActionListener(e -> onCerrarCuenta());

        BtnSalir = new JButton("Salir");
        BtnSalir.addActionListener(e -> onSalir());
        
        EstilizarBoton(BtnCambiarPass);
        EstilizarBoton(BtnCerrarCuenta);
        EstilizarBoton(BtnSalir);
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS));
        PanelBotones.setOpaque(false);
        PanelBotones.setBorder(BorderFactory.createEmptyBorder(30, 0, 25, 0));
        
        PanelBotones.add(BtnCambiarPass);
        PanelBotones.add(Box.createVerticalStrut(10));
        PanelBotones.add(BtnCerrarCuenta);
        PanelBotones.add(Box.createVerticalStrut(10));
        PanelBotones.add(BtnSalir);
        
        JPanel PanelInfoWrapper = new JPanel();
        PanelInfoWrapper.setOpaque(false);
        PanelInfoWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
        
        JPanel PanelInfo = new JPanel();
        PanelInfo.setLayout(new BoxLayout(PanelInfo, BoxLayout.Y_AXIS));
        PanelInfo.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        PanelInfo.setBackground(new Color(0, 0, 0, 130));
        PanelInfo.setOpaque(true);
        
        int indice = Memoria.getIndiceUsuario(UsuarioActivo);
        String puntos = String.valueOf(Memoria.getPuntos(indice));
        String fecha = "soy una fecha no formateada"; //Memoria.getFechaIngresoFormat(indice, "dd/MM/yyyy HH:mm");
        String estado = Memoria.isActivo(indice) ? "ACTIVO" : "INACTIVO";
        
        LblUsuario = new JLabel("Nombre de Usuario: " + UsuarioActivo);
        EstilizarLabel(LblUsuario);
        LblUsuario.setFont(new Font("Bookman Old Style", Font.BOLD, 24));
        
        LblPuntos = new JLabel("Puntaje: " + puntos);
        EstilizarLabel(LblPuntos);
        LblPuntos.setFont(new Font("Bookman Old Style", Font.BOLD, 24));
        
        LblFechaIngreso = new JLabel("Fecha de ingreso: " + fecha);
        EstilizarLabel(LblFechaIngreso);
        LblFechaIngreso.setFont(new Font("Bookman Old Style", Font.BOLD, 24));
        
        LblActivo = new JLabel("Estado: " + estado);
        EstilizarLabel(LblActivo);
        LblActivo.setFont(new Font("Bookman Old Style", Font.BOLD, 24));

        PanelInfo.add(Box.createVerticalStrut(15));
        PanelInfo.add(LblUsuario);
        PanelInfo.add(Box.createVerticalStrut(10));
        PanelInfo.add(LblPuntos);
        PanelInfo.add(Box.createVerticalStrut(10));
        PanelInfo.add(LblFechaIngreso);
        PanelInfo.add(Box.createVerticalStrut(10));
        PanelInfo.add(LblActivo);
        
        PanelInfoWrapper.add(PanelInfo);
                 
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(PanelInfoWrapper, BorderLayout.CENTER);
        PanelFondo.add(PanelBotones, BorderLayout.SOUTH);
        PanelFondo.repaint();
    }
    
    private void onCerrarCuenta() {
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres eliminar tu cuenta?\nEsta accion no se puede deshacer", "Confirmacion", JOptionPane.YES_NO_OPTION);
        
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        
        boolean confirm = Memoria.Eliminar(UsuarioActivo);
        if (!confirm) {
            JOptionPane.showMessageDialog(this, "Hubo un error al momento de eliminar la cuenta", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, "Cuenta Eliminada", "Error", JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
        if (menuPrincipal != null) {
            menuPrincipal.dispose();
        }
        
        new MenuInicial(Memoria).setVisible(true);
    }
    
    private void onSalir() {
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres salir al menu principal", "Confirmacion", JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            menuPrincipal.setVisible(true);
        }
    }
    
    public String getUsuarioActual() {
        return menuPrincipal.getUsuarioActivo();
    }
    
    public int getIndiceUsuarioActual() {
        return Memoria.getIndiceUsuario(getUsuarioActual());
    }
    
    private void EstilizarBoton(JButton boton) {
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFont(new Font("Bookman Old Style", Font.BOLD, 18));
        boton.setBackground(new Color(25, 25, 25)); //Gris oscuro tipo metal
        boton.setForeground(new Color(220, 180, 120)); //Dorado suave
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 2), BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        boton.setOpaque(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(220, 44));
        
        //Mi querido, hermoso y celestial efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(60, 0, 0));
                boton.setForeground(new Color(255, 220, 130));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(25, 25, 25));
                boton.setForeground(new Color(220, 180, 80));
            }
        });
    }
    
    private void EstilizarTitulo(JLabel titulo) {
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        Font base = new Font("Old English Text MT", Font.BOLD, 38);
        titulo.setFont(base);
        
        titulo.setForeground(new Color(230, 200, 120));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(0, 0, 0, 170)); //Franja oscura
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void EstilizarLabel(JLabel label) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Old English Text MT", Font.BOLD, 22));
        label.setForeground(new Color(230, 230, 150));
        label.setBackground(new Color(0, 0, 0, 150));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6), BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 30, 0))));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}