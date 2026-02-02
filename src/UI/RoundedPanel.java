/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author USUARIO
 */

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    
    private final int Radio;
    private final Color BG;
    
    public RoundedPanel(int Radio, Color BG) {
        this.Radio = Radio;
        this.BG = BG;
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(BG);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), Radio, Radio);
        super.paintComponent(g);
    }
}
 