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

public class MenuInicial extends JFrame {
    
    private JButton BtnIniciarSesion;
    private JButton BtnCrearCuenta;
    private JButton BtnSalir;
    
    public MenuInicial() {
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
//        BtnIniciarSesion.addActionListener(e -> AbrirIniciarSesion());
        BtnIniciarSesion.setBounds(330, 460, 150, 30);
        
        BtnCrearCuenta = new JButton("CREAR CUENTA");
        BtnCrearCuenta.setAlignmentX(JComponent.CENTER_ALIGNMENT);
//        BtnCrearCuenta.addActionListener(e -> AbrirCrearCuenta());
        BtnCrearCuenta.setBounds(330, 460, 150, 30);
        
        BtnSalir = new JButton("SALIR");
        BtnSalir.setAlignmentX(JComponent.CENTER_ALIGNMENT);
//        BtnSalir.addActionListener(e -> onSalir());
        BtnSalir.setBounds(330, 460, 150, 30);
        
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
}
