/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enums;

/**
 *
 * @author USUARIO
 */
public enum Orientacion {
    HORIZONTAL,
    VERTICAL;
    
    public static Orientacion FromChar(char c) {
        return (c == 'V' || c == 'v') ? VERTICAL : HORIZONTAL;
    }
}
