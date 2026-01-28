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

public class MenuPrincipal extends JFrame {
    
    private JLabel LblTitulo;
    
    private JButton BtnJugar;
    private JButton BtnConfiguracion;
    private JButton BtnReportes;
    private JButton BtnMiPerfil;
    private JButton BtnLogout;
    
    private String UsuarioActivo;
    private JLabel LblUsuario;
    
    private final MemoriaCuentas Memoria;
    

    public MenuPrincipal(MemoriaCuentas Memoria, String UsuarioActivo) {
        this.Memoria = Memoria;
        this.UsuarioActivo = (UsuarioActivo == null) ? "" : UsuarioActivo.trim();
        
        if (this.UsuarioActivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inicia sesion o crea una cuenta!", "Error", JOptionPane.WARNING_MESSAGE);
            dispose();
            
            new MenuInicial().setVisible(true);
            return;
        }
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_principal.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("BATTLESHIP - Menu Principal");
        this.setContentPane(PanelFondo);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        JPanel PanelHeader = new JPanel();
        PanelHeader.setLayout(new BoxLayout(PanelHeader, BoxLayout.Y_AXIS));
        PanelHeader.setOpaque(false);
        
        LblTitulo = new JLabel("BATTLESHIP") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 6;
                
                //Sombra (solo es el mismo texto dibujado varias veces alrededor del texto original)
                g2d.setColor(new Color(0, 0, 0, 160));
                g2d.drawString(getText(), x - 3, y);
                g2d.drawString(getText(), x + 3, y);
                g2d.drawString(getText(), x, y - 3);
                g2d.drawString(getText(), x, y + 3);
                
                //Texto principal
                g2d.setColor(getForeground());
                g2d.drawString(getText(), x, y);
            }
        };
        
        EstilizarTitulo(LblTitulo);
        
        PanelHeader.add(Box.createVerticalStrut(10));
        PanelHeader.add(LblTitulo);
        PanelHeader.add(Box.createVerticalGlue());
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS));
        PanelBotones.setOpaque(false);
        PanelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        BtnJugar = new JButton("JUGAR BATTLESHIP");
        BtnJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnJugar.addActionListener(e -> onJugar());
        EstilizarBoton(BtnJugar);

        BtnConfiguracion = new JButton("CONFIGURACION");
        BtnConfiguracion.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnConfiguracion.addActionListener(e -> AbrirConfiguracion());
        EstilizarBoton(BtnConfiguracion);
        
        BtnMiPerfil = new JButton("MI PERFIL");
        BtnMiPerfil.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnMiPerfil.addActionListener(e -> AbrirMiCuenta());
        EstilizarBoton(BtnMiPerfil);

        BtnReportes = new JButton("REPORTES");
        BtnReportes.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnReportes.addActionListener(e -> AbrirReportes());
        EstilizarBoton(BtnReportes);

        BtnLogout = new JButton("LOG OUT");
        BtnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnLogout.addActionListener(e -> onLogout());
        EstilizarBoton(BtnLogout);
        
        PanelBotones.add(Box.createVerticalStrut(70));
        PanelBotones.add(BtnJugar);
        PanelBotones.add(Box.createVerticalStrut(14));
        PanelBotones.add(BtnConfiguracion);
        PanelBotones.add(Box.createVerticalStrut(14));
        PanelBotones.add(BtnMiPerfil);
        PanelBotones.add(Box.createVerticalStrut(14));
        PanelBotones.add(BtnReportes);
        PanelBotones.add(Box.createVerticalStrut(14));
        PanelBotones.add(BtnLogout);
        PanelBotones.add(Box.createVerticalGlue());
        
        JPanel PanelUsuario = new JPanel(new BorderLayout());
        PanelUsuario.setOpaque(true);
        PanelUsuario.setBackground(new Color(0, 0, 0, 160));
        
        LblUsuario = new JLabel("Usuario: " + this.UsuarioActivo);
        LblUsuario.setForeground(Color.WHITE);
        LblUsuario.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        LblUsuario.setFont(new Font("Bookman Old Style", Font.PLAIN, 16));
        LblUsuario.setBorder(BorderFactory.createEmptyBorder(0, 12, 8, 0));
        
        PanelUsuario.add(LblUsuario, BorderLayout.WEST);
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(PanelHeader, BorderLayout.NORTH);
        PanelFondo.add(PanelBotones, BorderLayout.CENTER);
        PanelFondo.add(PanelUsuario, BorderLayout.SOUTH);
        
        getRootPane().setDefaultButton(BtnJugar);
        
        PanelFondo.repaint();
    }
    
    private void onJugar() {
        if (UsuarioActivo == null || UsuarioActivo.isBlank()) {
            JOptionPane.showMessageDialog(this, "Primero inicia sesion o crea una cuenta!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String[] activos = (Memoria != null) ? Memoria.getUsuariosActivos(): new String[0];
        
        if (activos.length < 2) {
            JOptionPane.showMessageDialog(this, "Necesitas como minimo 2 jugadores para poder iniciar el juego!", "Insuficientes Jugadores", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int cuenta = 0;
        
        for (int i = 0; i < activos.length; i++) {
            if (activos[i] != null && !activos[i].equalsIgnoreCase(UsuarioActivo))
                cuenta++;
        }
        
        if (cuenta == 0) {
            JOptionPane.showMessageDialog(this, "No hay oponentes conectados actualmente", "Sin Rivales", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //Arreglo con rivales
        String[] rivales = new String[cuenta];
        int k = 0;
        
        for (int i = 0; i < activos.length; i++) {
            if (activos[i] != null && !activos[i].equalsIgnoreCase(UsuarioActivo)) {
                rivales[k++] = activos[i];
            }
        }
        
        JLabel LblBlancas = new JLabel("BLANCAS: " + UsuarioActivo);
        
        JComboBox<String> CmbNegras = new JComboBox<>(rivales);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 6, 6));
        
        panel.add(new JLabel("Jugador BLANCAS: "));
        panel.add(LblBlancas);
        panel.add(new JLabel("Jugador (Elige oponente): "));
        panel.add(CmbNegras);
        
        int eleccion = JOptionPane.showConfirmDialog(this, panel, "Elegir Oponente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (eleccion != JOptionPane.OK_OPTION) {
            return;
        }
        
        String negras = (String) CmbNegras.getSelectedItem();
        
        if (negras == null || negras.isBlank()) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un oponente!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
//        PanelJuego juego = new PanelJuego(Memoria, UsuarioActivo, negras, this);
//        this.setVisible(false);
        
//        juego.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                MenuPrincipal.this.setVisible(true);
//            }
//        });
//        
//        juego.setVisible(true);
    }
    
    private void AbrirConfiguracion() {
        if (UsuarioActivo == null || UsuarioActivo.isBlank()) {
            JOptionPane.showMessageDialog(this, "Primero inicia sesion o crea una cuenta!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        new Configuracion(Memoria, UsuarioActivo, this).setVisible(true);
        this.dispose();
    }
    private void AbrirMiCuenta() {
        if (UsuarioActivo == null || UsuarioActivo.isBlank()) {
            JOptionPane.showMessageDialog(this, "Primero inicia sesion o crea una cuenta!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        new MiPerfil(Memoria, UsuarioActivo, this).setVisible(true);
        this.dispose();
    }
    
    private void AbrirReportes() {
        if (UsuarioActivo == null || UsuarioActivo.isBlank()) {
            JOptionPane.showMessageDialog(this, "Primero inicia sesion o crea una cuenta!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        new Reportes(Memoria, UsuarioActivo, this).setVisible(true);
        this.dispose();
    }
    
    private void onLogout() {
        int opcion = JOptionPane.showConfirmDialog(this, "Estas seguro que quieres regresar al menu inicial?", "Aviso", JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
            new MenuInicial(Memoria).setVisible(true);
        }
    }

    public String getUsuarioActivo() {
        return UsuarioActivo;
    }

    public void setUsuarioActivo(String UsuarioActivo) {
        this.UsuarioActivo = (UsuarioActivo == null) ? "" : UsuarioActivo.trim();
        
        if (LblUsuario != null) {
            LblUsuario.setText("Usuario: " + this.UsuarioActivo);
        }
        
        setTitle("BATTLESHIP - Menu Principal" + (this.UsuarioActivo.isEmpty() ? "" : " (" + this.UsuarioActivo + ")"));
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
    
    private void EstilizarTitulo(JLabel titulo) {
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("ITC Machine Std", Font.BOLD, 85));
        titulo.setForeground(Color.WHITE);
        titulo.setOpaque(false);
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
