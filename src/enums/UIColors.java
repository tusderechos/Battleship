/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enums;

/**
 *
 * @author USUARIO
 */

import java.awt.Color;

public enum UIColors {
    HIT(176, 16, 16),
    MISS(148, 129, 129),
    AGUA_P1(25, 30, 55),
    AGUA_P1_INACTIVO(18, 22, 41),
    AGUA_P2(55, 25, 25),
    AGUA_P2_INACTIVO(38, 17, 17),
    BARCO(186, 182, 182),
    HOVER_OK(60, 90, 150),
    HOVER_BAD(140, 60, 60),
    DESHABILITADO(18, 18, 30),
   
    BORDE_P1(80, 140, 255, 160),
    BORDE_P2(255, 120, 120, 160),
    BORDE_INACTIVO(255, 255, 255, 70);
    
    private final Color color;
    
    UIColors(int r, int g, int b) {
        color = new Color(r, g, b);
    }
    
    UIColors(int r, int g, int b, int a) {
        color = new Color(r, g, b, a);
    }
    
    public Color getColor() {
        return color;
    }
}