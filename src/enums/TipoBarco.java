/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enums;

/**
 *
 * @author USUARIO
 */
public enum TipoBarco {
    PA("PA", 'P', 5),
    AZ("AZ", 'A', 4),
    SM("SM", 'S', 3),
    DT("DT", 'D', 2);
    
    public final String codigo;
    public final char simbolo;
    public final int longitud;
    
    TipoBarco(String codigo, char simbolo, int longitud) {
        this.codigo = codigo;
        this.simbolo = simbolo;
        this.longitud = longitud;
    }
    
    public static TipoBarco FromCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        
        codigo = codigo.trim().toUpperCase();
        
        for (TipoBarco tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        
        return null;
    }
}
