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

public class Configuracion extends JFrame {
    
    private final JButton BtnDificultad;
    private final JButton BtnModoJuego;
    private final JButton BtnVolver;
    
    private final String UsuarioActivo;
    
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
//        BtnDificultad.addActionListener(e -> MostrarDificultad());

        BtnModoJuego = new JButton("MODO DE JUEGO");
        BtnModoJuego.setAlignmentX(Component.CENTER_ALIGNMENT);
//        BtnModoJuego.addActionListener(e -> MostrarModoJuego());
        
        BtnVolver = new JButton("VOLVER  ");
        BtnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnVolver.addActionListener(e -> OnVolver());
        
        PanelBotones.add(Box.createVerticalStrut(70));
        PanelBotones.add(BtnDificultad);
        PanelBotones.add(Box.createVerticalStrut(14));
        PanelBotones.add(BtnModoJuego);
        PanelBotones.add(Box.createVerticalStrut(14));
        PanelBotones.add(BtnVolver);
        PanelBotones.add(Box.createVerticalGlue());
        
        PanelFondo.add(PanelBotones, BorderLayout.CENTER);
    }
    
    private void OnVolver() {
        this.dispose();
        menuPrincipal.setVisible(true);
    }
}
