/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author quier
 */

import enums.ModoJuego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DialogModoJuego extends JDialog {
    
    private ModoJuego Seleccion;
    
    private final JButton BtnConfirmar;
    private final JButton BtnCancelar;
    
    public DialogModoJuego(JFrame padre, ModoJuego actual) {
        super(padre, "Modo de Juego", true);
        this.Seleccion = actual;
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_modojuego.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setSize(420, 320);
        setLocationRelativeTo(padre);
        setResizable(false);
        
        JPanel raiz = new JPanel();
        raiz.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        raiz.setOpaque(false);
        
        setContentPane(PanelFondo);
                
        JPanel PanelOpciones = new JPanel();
        PanelOpciones.setOpaque(false);
        PanelOpciones.setLayout(new BoxLayout(PanelOpciones, BoxLayout.Y_AXIS));
        
        ButtonGroup bg = new ButtonGroup();
        
        JRadioButton RBTutorial = CrearRadio(ModoJuego.TUTORIAL, actual);
        RBTutorial.setFont(new Font("DIN Condensed", Font.BOLD, 16));
        JRadioButton RBArcade = CrearRadio(ModoJuego.ARCADE, actual);
        RBArcade.setFont(new Font("DIN Condensed", Font.BOLD, 16));

        
        bg.add(RBTutorial);
        bg.add(RBArcade);
        
        PanelOpciones.add(Box.createVerticalStrut(30));
        PanelOpciones.add(RBTutorial);
        PanelOpciones.add(RBArcade);
        
        raiz.add(PanelOpciones, BorderLayout.CENTER);
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS));
        PanelBotones.setOpaque(false);
        
        BtnConfirmar = new JButton("CONFIRMAR");
        EstilizarBoton(BtnConfirmar);
        
        BtnCancelar = new JButton("VOLVER");
        EstilizarBoton(BtnCancelar);
        
        BtnConfirmar.addActionListener(e -> dispose());
        BtnCancelar.addActionListener(e -> {
            Seleccion = actual;
            dispose();
        });
        
        PanelBotones.add(Box.createVerticalStrut(15));
        PanelBotones.add(BtnConfirmar);
        PanelBotones.add(Box.createVerticalStrut(15));
        PanelBotones.add(BtnCancelar);
        
        raiz.add(PanelBotones, BorderLayout.SOUTH);
        
        PanelFondo.setLayout(new BorderLayout());
        PanelFondo.add(raiz);
    }
    
    private JRadioButton CrearRadio(ModoJuego modo, ModoJuego actual) {
        JRadioButton rb = new JRadioButton(modo.getEtiqueta());
        rb.setOpaque(false);
        rb.setForeground(Color.WHITE);
        rb.setFont(new Font("DIN Condensed", Font.PLAIN, 14));
        rb.setSelected(modo == actual);
        rb.addActionListener(e -> Seleccion = modo);
        
        return rb;
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
        boton.setPreferredSize(new Dimension(160, 44));
        
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
    
    public ModoJuego getSeleccion() {
        return Seleccion;
    }
}

