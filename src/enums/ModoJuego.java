/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enums;

/**
 *
 * @author quier
 */
public enum ModoJuego {
    TUTORIAL("TUTORIAL (Muestra Barcos)"),
    ARCADE("ARCADE (Oculta Barcos)");
    
    private final String etiqueta;
    
    ModoJuego(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    
    public String getEtiqueta() {
        return etiqueta;
    }
}
