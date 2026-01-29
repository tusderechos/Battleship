/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enums;

/**
 *
 * @author quier
 */
public enum Dificultad {
    EASY(5, "EASY (5 barcos)"),
    NORMAL(5, "NORMAL (4 barcos)"),
    EXPERT(5, "EXPERT (2 barcos)"),
    GENIUS(5, "GENIUS (1 barco)");
    
    private final int barcos;
    private final String etiqueta;
    
    Dificultad(int barcos, String etiqueta) {
        this.barcos = barcos;
        this.etiqueta = etiqueta;
    }

    public int getBarcos() {
        return barcos;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
