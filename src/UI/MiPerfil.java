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
    
    public JButton BtnVerDatos;
    public JButton BtnModificarDatos;
    public JButton BtnVolver;
    
    public JLabel LblUsuario;
    public JLabel LblPuntos;
    public JLabel LblFechaIngreso;
    public JLabel LblActivo;
    
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
        
        LblUsuario = new JLabel("Nombre de Usuario: ");
        EstilizarLabel(LblUsuario);
        LblUsuario.setFont(new Font("Bookman Old Style", Font.BOLD, 24));
        
        LblPuntos = new JLabel("Puntaje: ");
        EstilizarLabel(LblPuntos);
        LblPuntos.setFont(new Font("Bookman Old Style", Font.BOLD, 24));
        
        LblFechaIngreso = new JLabel("Fecha de ingreso: ");
        EstilizarLabel(LblFechaIngreso);
        LblFechaIngreso.setFont(new Font("Bookman Old Style", Font.BOLD, 24));
        
        LblActivo = new JLabel("Estado: ");
        EstilizarLabel(LblActivo);
        LblActivo.setFont(new Font("Bookman Old Style", Font.BOLD, 24));

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
        int indice = Memoria.getIndiceUsuario(UsuarioActivo);
        
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado o inactivo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] opciones = {"Cambiar Username", "Cambiar Contraseña", "Volver"};
        
        int opcion = JOptionPane.showOptionDialog(this, "Elige el dato que quieras modificar", "Modificar Datos", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        
        if (opcion == 0) {
            CambiarUsername(indice);
            VerMisDatos();
        } else if (opcion == 1) {
            CambiarContrasena(indice);
            VerMisDatos();
        }
    }
    
    private void CambiarUsername(int indice) {
        String nuevouser = JOptionPane.showInputDialog(this, "Ingresa el nuevo nombre de usuario");
        
        if (nuevouser == null)
            return;
        
        nuevouser = nuevouser.trim();
        
        if (nuevouser.isEmpty()) {
            JOptionPane.showMessageDialog(this, "el nuevo usuario no puede estar vacio", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean actualizado = Memoria.ActualizarUsuario(indice, nuevouser);
        
        if (!actualizado) {
            JOptionPane.showMessageDialog(this, "El usuario ya existe o no se pudo cambiar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        UsuarioActivo = nuevouser;
        
        if (menuPrincipal != null) {
            menuPrincipal.setUsuarioActivo(nuevouser);
        }
    }
    
    private void CambiarContrasena(int indice) {
        JPasswordField passactual = new JPasswordField();
        JPasswordField passnueva = new JPasswordField();
        JPasswordField passconfirm = new JPasswordField();
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 6, 6));
        
        panel.add(new JLabel("Contraseña actual: "));
        panel.add(passactual);
        
        panel.add(new JLabel("Contraseña nueva: "));
        panel.add(passnueva);
        
        panel.add(new JLabel("Confirmar contraseña nueva: "));
        panel.add(passconfirm);
        
        int respuesta = JOptionPane.showConfirmDialog(this, panel, "Cambiar Contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (respuesta != JOptionPane.OK_OPTION)
            return;
        
        String actual = new String(passactual.getPassword()).trim();
        String nueva = new String(passnueva.getPassword()).trim();
        String confirm = new String(passconfirm.getPassword()).trim();
        
        if (actual.isEmpty() || nueva.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se puede dejar campos vacios", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!nueva.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "La nueva contraseña no coincide", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!Memoria.ValidarContrasenaActual(indice, nueva)) {
            JOptionPane.showMessageDialog(this, "Contraseña actual incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        boolean actualizado = Memoria.ActualizarContrasena(indice, nueva);
        
        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Contraseña actualizada", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la contraseña", "Error", JOptionPane.ERROR_MESSAGE);
        }
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