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
import ManejoCuentas.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MiPerfil extends JFrame {
    
    private JButton BtnVerDatos;
    private JButton BtnModificarDatos;
    private JButton BtnVolver;
    
    private JLabel LblUsuario;
    private JLabel LblPuntos;
    private JLabel LblFechaIngreso;
    private JLabel LblActivo;
    
    private String UsuarioActivo;
    private final MenuPrincipal menuPrincipal;
    private final MemoriaCuentas Memoria;
    
    public MiPerfil (MemoriaCuentas Memoria, String UsuarioActivo, MenuPrincipal menuPrincipal) {
        this.Memoria = Memoria;
        this.UsuarioActivo = (UsuarioActivo == null) ? "" : UsuarioActivo.trim();
        this.menuPrincipal = menuPrincipal;
        
        if (this.UsuarioActivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero inicia sesion o crea una cuenta!", "Aviso", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_miperfil.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
    
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("BATTLESHIP - Mi Perfil");
        this.setContentPane(PanelFondo);
        setSize(800, 700);
        setResizable(false);
        setLocationRelativeTo(menuPrincipal);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(160, 0, 0, 0));
        
        BtnVerDatos = new JButton("VER MIS DATOS");
        BtnVerDatos.addActionListener(e -> VerMisDatos());

        BtnModificarDatos = new JButton("MODIFICAR MIS DATOS");
        BtnModificarDatos.addActionListener(e -> ModificarMisDatos());

        BtnVolver = new JButton("SALIR");
        BtnVolver.addActionListener(e -> Volver());
        
        EstilizarBoton(BtnVerDatos);
        EstilizarBoton(BtnModificarDatos);
        EstilizarBoton(BtnVolver);
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS));
        PanelBotones.setOpaque(false);
        PanelBotones.setBorder(BorderFactory.createEmptyBorder(30, 0, 25, 0));
        
        PanelBotones.add(BtnVerDatos);
        PanelBotones.add(Box.createVerticalStrut(10));
        PanelBotones.add(BtnModificarDatos);
        PanelBotones.add(Box.createVerticalStrut(10));
        PanelBotones.add(BtnVolver);
        
        JPanel PanelInfoWrapper = new JPanel();
        PanelInfoWrapper.setOpaque(false);
        PanelInfoWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
        
        JPanel PanelInfo = new JPanel();
        PanelInfo.setLayout(new BoxLayout(PanelInfo, BoxLayout.Y_AXIS));
        PanelInfo.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        PanelInfo.setBackground(new Color(0, 0, 0, 130));
        PanelInfo.setOpaque(true);
        
        LblUsuario = new JLabel("");
        EstilizarLabel(LblUsuario);
        
        LblPuntos = new JLabel("");
        EstilizarLabel(LblPuntos);
        
        LblFechaIngreso = new JLabel("");
        EstilizarLabel(LblFechaIngreso);
        
        LblActivo = new JLabel("");
        EstilizarLabel(LblActivo);
        
        /*
            Lo que tiene que ver con mostrar los datos del usuario solamente es un tipo de placeholder
            hasta que lo arregle (posiblemente hoy o manana), asi que nadie se vaya a asustar
        */

        PanelInfo.add(Box.createVerticalStrut(10));
        PanelInfo.add(LblUsuario);
        PanelInfo.add(Box.createVerticalStrut(10));
        PanelInfo.add(LblPuntos);
        PanelInfo.add(Box.createVerticalStrut(10));
        PanelInfo.add(LblFechaIngreso);
        PanelInfo.add(Box.createVerticalStrut(10));
        PanelInfo.add(LblActivo);
        
        PanelInfoWrapper.add(PanelInfo);
                 
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(panel, BorderLayout.NORTH);
        PanelFondo.add(PanelInfoWrapper, BorderLayout.CENTER);
        PanelFondo.add(PanelBotones, BorderLayout.SOUTH);
        
        getRootPane().setDefaultButton(BtnVerDatos);
        PanelFondo.repaint();
    }
    
    private void VerMisDatos() {
        int indice = Memoria.getIndiceUsuario(UsuarioActivo);
        
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado o inactivo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Player jugador = Memoria.getPlayer(indice);
        
        if (jugador == null) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el perfil del usuario", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LblUsuario.setText("Nombre de Usuario: " + UsuarioActivo);
        LblPuntos.setText("Puntaje: " + jugador.getPuntos());
        LblFechaIngreso.setText("Fecha de Ingreso: " + Memoria.getFechaIngresoFormat(indice, "dd/MM/yyyy HH:mm"));
        LblActivo.setText("Estado: " + (jugador.isActivo() ? "ACTIVO" : "INACTIVO"));
    }
    
    private void ModificarMisDatos() {
        new ModificarDatos(this, Memoria, UsuarioActivo, menuPrincipal).setVisible(true);
        RefrescarDatos();
    }
    
    private void RefrescarDatos() {
        String usuario = menuPrincipal.getUsuarioActivo();
        
        if (usuario == null || usuario.isBlank()) {
            JOptionPane.showMessageDialog(this, "No hay un usuario activo", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int indice = Memoria.getIndiceUsuario(usuario);
        
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Ese usuario no se encuentra", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int puntos = Memoria.getPuntos(indice);
        String fecha = Memoria.getFechaIngresoFormat(indice, "dd/MM/yyyy HH:mm");
        boolean activo = Memoria.isActivo(indice);
        
        LblUsuario.setText("Usuario: " + usuario);
        LblPuntos.setText("Puntos: " + puntos);
        LblFechaIngreso.setText("Fecha de ingreso: " + fecha);
        LblActivo.setText("Estado: " + (activo ? "ACTIVO" : "INACTIVO"));
        
        repaint();
        revalidate();
    }
    
    private void Volver() {
        this.setVisible(false);
        
        if (menuPrincipal != null) {
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
    
    private void EstilizarLabel(JLabel label) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("DIN Condensed", Font.BOLD, 22));
        label.setForeground(new Color(230, 230, 150));
        label.setBackground(new Color(0, 0, 0, 150));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6), BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 30, 0))));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}