/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ManejoCuentas;

/**
 *
 * @author Hp
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Player {
    
    private String Username;
    private String Contrasena;
    private int Puntos;
    private Calendar FechaIngreso;
    private boolean Activo;
    
    private String[] Logs;
    private int ConteoLogs;
    
    public Player(String Username, String Contrasena) {
        this.Username = Username;
        this.Contrasena = Contrasena;
        Puntos = 0;
        FechaIngreso = Calendar.getInstance();
        Activo = true;
        Logs = new String[10];
        ConteoLogs = 0;
    }
    
    public void AgregarLog(String fecha, String rival, String resultado) {
        String entrada = fecha + " | Rival: " + rival + " | " + resultado;
                
        for (int i = Logs.length; i >=  1; i--) {
            Logs[i] = Logs[i - 1];
        }
        
        Logs[0] = entrada;
        
        if (ConteoLogs < Logs.length) {
            ConteoLogs++;
        }
    }
    
    public String[] getUltimosLogs() {
        String[] copia = new String[ConteoLogs];
        
        for (int i = 0; i < ConteoLogs; i++) {
            copia[i] = Logs[i];
        }
        
        return copia;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String Contrasena) {
        this.Contrasena = Contrasena;
    }

    public int getPuntos() {
        return Puntos;
    }

    public void setPuntos(int Puntos) {
        this.Puntos = Puntos;
    }

    public Calendar getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(Calendar FechaIngreso) {
        this.FechaIngreso = FechaIngreso;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean Activo) {
        this.Activo = Activo;
    }

    public String[] getLogs() {
        return Logs;
    }

    public void setLogs(String[] Logs) {
        this.Logs = Logs;
    }

    public int getConteoLogs() {
        return ConteoLogs;
    }

    public void setConteoLogs(int ConteoLogs) {
        this.ConteoLogs = ConteoLogs;
    }
    
    
}
