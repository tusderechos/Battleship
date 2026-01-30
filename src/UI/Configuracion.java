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
import enums.Dificultad;
import enums.ModoJuego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Configuracion extends JFrame {
    
    private final JButton BtnDificultad;
    private final JButton BtnModoJuego;
    private final JButton BtnVolver;
    
    private final String UsuarioActivo;
    
    private Dificultad dificultad = Dificultad.NORMAL;
    private ModoJuego modoJuego = ModoJuego.TUTORIAL;
    
    private MemoriaCuentas Memoria;
    private MenuPrincipal menuPrincipal;
    
    public Configuracion(MemoriaCuentas Memoria, String UsuarioActivo, MenuPrincipal menuPrincipal) {
        this.Memoria = Memoria;
        this.UsuarioActivo = UsuarioActivo;
        this.menuPrincipal = menuPrincipal;
        
        ImageIcon IconoImagen = new ImageIcon(getClass().getResource("/images/bg_configuracion.PNG"));
        Image ImagenFondo = IconoImagen.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("BATTLESHIP - Reportes");
        this.setContentPane(PanelFondo);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS));
        PanelBotones.setOpaque(false);
        PanelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        BtnDificultad = new JButton("DIFICULTAD");
        BtnDificultad.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnDificultad.addActionListener(e -> MostrarDificultad());
        EstilizarBoton(BtnDificultad);

        BtnModoJuego = new JButton("MODO DE JUEGO");
        BtnModoJuego.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnModoJuego.addActionListener(e -> MostrarModoJuego());
        EstilizarBoton(BtnModoJuego);

        BtnVolver = new JButton("VOLVER  ");
        BtnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnVolver.addActionListener(e -> OnVolver());
        EstilizarBoton(BtnVolver);
        
        PanelBotones.add(Box.createVerticalStrut(70));
        PanelBotones.add(BtnDificultad);
        PanelBotones.add(Box.createVerticalStrut(14));
        PanelBotones.add(BtnModoJuego);
        PanelBotones.add(Box.createVerticalStrut(14));
        PanelBotones.add(BtnVolver);
        PanelBotones.add(Box.createVerticalGlue());
        
        PanelFondo.add(PanelBotones, BorderLayout.CENTER);
    }
    
    private void MostrarDificultad() {
        DialogDificultad diadiff = new DialogDificultad(this, dificultad);
        diadiff.setVisible(true);
        dificultad = diadiff.getSeleccion();
        menuPrincipal.setDificultadActual(dificultad);
    }
    
    private void MostrarModoJuego() {
        DialogModoJuego diamodo = new DialogModoJuego(this, modoJuego);
        diamodo.setVisible(true);
        modoJuego = diamodo.getSeleccion();
        menuPrincipal.setModoJuegoActual(modoJuego);
    }
    
    private void OnVolver() {
        this.dispose();
        menuPrincipal.setVisible(true);
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public ModoJuego getModoJuego() {
        return modoJuego;
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
