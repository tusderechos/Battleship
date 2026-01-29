/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author quier
 */

import enums.Dificultad;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DialogDificultad extends JDialog {
    
    private Dificultad Seleccion;
    
    private JButton BtnConfirmar;
    private JButton BtnCancelar;
    
    public DialogDificultad(JFrame padre, Dificultad actual) {
        super(padre, "Dificultad", true);
        this.Seleccion = actual;
        
        setSize(420, 320);
        setLocationRelativeTo(padre);
        setResizable(false);
        
        JPanel raiz = new JPanel();
        raiz.setLayout(new BorderLayout());
        raiz.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        raiz.setBackground(new Color(0, 0, 0, 180));
        setContentPane(raiz);
        
        JLabel lbltitulo = new JLabel("SELECCIONA LA DIFICULTAD", SwingConstants.CENTER);
        lbltitulo.setFont(new Font("DIN Condensed", Font.BOLD, 18));
        lbltitulo.setForeground(new Color(220, 180, 120));
        
        raiz.add(lbltitulo, BorderLayout.NORTH);
        
        JPanel PanelOpciones = new JPanel();
        PanelOpciones.setOpaque(false);
        PanelOpciones.setLayout(new BoxLayout(PanelOpciones, BoxLayout.Y_AXIS));
        
        ButtonGroup bg = new ButtonGroup();
        
        JRadioButton RBEasy = CrearRadio(Dificultad.EASY, actual);
        JRadioButton RBNormal = CrearRadio(Dificultad.NORMAL, actual);
        JRadioButton RBExpert = CrearRadio(Dificultad.EXPERT, actual);
        JRadioButton RBGenius = CrearRadio(Dificultad.GENIUS, actual);
        
        bg.add(RBEasy);
        bg.add(RBNormal);
        bg.add(RBExpert);
        bg.add(RBGenius);
        
        PanelOpciones.add(RBEasy);
        PanelOpciones.add(RBNormal);
        PanelOpciones.add(RBExpert);
        PanelOpciones.add(RBGenius);
        
        raiz.add(PanelOpciones, BorderLayout.CENTER);
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
        PanelBotones.setOpaque(false);
        
        BtnConfirmar = new JButton("GUARDAR");
        EstilizarBoton(BtnConfirmar);
        
        BtnCancelar = new JButton("VOLVER");
        EstilizarBoton(BtnCancelar);
        
        BtnConfirmar.addActionListener(e -> dispose());
        BtnCancelar.addActionListener(e -> {
            Seleccion = actual;
            dispose();
        });
        
        PanelBotones.add(BtnConfirmar);
        PanelBotones.add(BtnCancelar);
        
        raiz.add(PanelBotones, BorderLayout.SOUTH);
    }
    
    private JRadioButton CrearRadio(Dificultad dificultad, Dificultad actual) {
        JRadioButton rb = new JRadioButton(dificultad.getEtiqueta());
        rb.setOpaque(false);
        rb.setForeground(Color.WHITE);
        rb.setFont(new Font("DIN Condensed", Font.PLAIN, 14));
        rb.setSelected(dificultad == actual);
        rb.addActionListener(e -> Seleccion = dificultad);
        
        return rb;
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
    
    public Dificultad getSeleccion() {
        return Seleccion;
    }
}
