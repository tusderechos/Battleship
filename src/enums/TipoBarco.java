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
    PA("PA", 5),
    AZ("AZ", 4),
    SM("SM", 3),
    DT("DT", 2);
    
    public final String codigo;
    public final int longitud;
    
    TipoBarco(String codigo, int longitud) {
        this.codigo = codigo;
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
