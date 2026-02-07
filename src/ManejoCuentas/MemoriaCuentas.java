/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ManejoCuentas;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import Interfaces.Datos;

/**
 *
 * @author Hp
 */
public class MemoriaCuentas implements Datos {
    
    private Player[] Jugadores;
    public int Registrados;
    public int MAX;

    public MemoriaCuentas(int max) {
        this.MAX = max;
        Registrados = 0;
        Jugadores = new Player[max];
    }
    
    public boolean isFull() {
        return Registrados == MAX;
    }
    
    public boolean isEmpty() {
        return Registrados == 0;
    }

    public int getRegistrados() {
        return Registrados;
    }

    public int getMAX() {
        return MAX;
    }
    
    @Override
    public int getIndiceUsuario(String usuario) {
        if (usuario == null || usuario.isEmpty()) 
            return -1;
        
        for (int i = 0; i < Registrados; i++) {
            if (Jugadores[i] != null && Jugadores[i].isActivo() && usuario.equalsIgnoreCase(Jugadores[i].getUsername())) {
                return i;
            }
        }
        
        return -1;
    }
    
    public int getIndiceUsuarioExacto(String usuario) {
        for (int i = 0; i < Registrados; i++) {
            if (Jugadores[i].getUsername().equals(usuario)) {
                return i;
            }
        }
        
        return -1;
    }
    
    @Override
    public void AgregarLog(String actor, String fecha, String rival, String resultado) {
        int indice = getIndiceUsuario(actor);
        
        if (indice == -1)
            return;
        
        Jugadores[indice].AgregarLog(fecha, rival, resultado);
    }
    
    @Override
    public String[] ObtenerLogsUsuario(String usuario) {
        int indice = getIndiceUsuario(usuario);
        
        if (indice == -1) {
            return new String[0];
        }
        
        return Jugadores[indice].getUltimosLogs();
    }
    
    public boolean ExisteUsuario(String usuario) {
        return getIndiceUsuario(usuario) >= 0;
    }
    
    public boolean ValidarLogin(String usuario, String contrasena) {
        if (usuario == null || contrasena == null)
            return false;

        if (usuario.isEmpty() || contrasena.isEmpty())
            return false;

        int indice = getIndiceUsuario(usuario);

        if (indice == -1)
            return false;

        return contrasena.equals(Jugadores[indice].getContrasena());
    }
    
    public boolean Agregar(String usuario, String contrasena) {
        if (isFull()) {
            return false;
        }
        
        if (usuario == null || contrasena == null || usuario.isEmpty() || contrasena.isEmpty()) {
            return false;
        }
        
        if (ExisteUsuario(usuario)) {
            return false;
        }
        
        Jugadores[Registrados] = new Player(usuario, contrasena);
        Registrados++;
        
        return true;
    }
    
    public boolean Eliminar(String Usuario) {
        int indice = getIndiceUsuario(Usuario);
        
        if (indice == -1)
            return false;
        
        int ultimo = Registrados - 1;
        
        Jugadores[indice] = Jugadores[ultimo];
        Jugadores[ultimo] = null;
        
        Registrados--;
        return true;
    }
    
    public boolean ValidarContrasenaActual(int indice, String contraactual) {
        if (indice < 0 || indice >= Registrados)
            return false;
        
        if (contraactual == null)
            return false;
        
        return contraactual.equals(Jugadores[indice].getContrasena());
    }
    
    public boolean ActualizarContrasena(int indice, String nuevacontra) {
        if (indice < 0 || indice >= Registrados)
            return false;
        
        if (nuevacontra == null || nuevacontra.isEmpty())
            return false;
        
        Jugadores[indice].setContrasena(nuevacontra);
        return true;
    }
    
    public boolean ActualizarUsuario(int indice, String nuevousuario) {
        if (indice < 0 || indice >= Registrados)
            return false;
        
        if (nuevousuario == null || nuevousuario.isEmpty())
            return false;
        
        //Validar que no exista en otro jugador activo
        for (int i = 0; i < Registrados; i++) {
            if (i != indice && Jugadores[i] != null && Jugadores[i].isActivo() && nuevousuario.equalsIgnoreCase(Jugadores[i].getUsername())) {
                return false;
            }
        }
        
        Jugadores[indice].setUsername(nuevousuario);
        return true;
    }
    
    public int getPuntos(int indice) {
        if (indice < 0 || indice >= Registrados) {
            return 0;
        }
        
        return Jugadores[indice].getPuntos();
    }

    public void setPuntos(int indice, int puntos) {
        if (indice < 0 || indice >= Registrados) {
            return;
        }
        
        Jugadores[indice].setPuntos(puntos);
    }
    
    public void SumarPuntos(String usuario, int suma) {
        int indice = getIndiceUsuario(usuario);
        
        if (indice == -1)
            return;
        
        int actuales = Jugadores[indice].getPuntos();
        Jugadores[indice].setPuntos(actuales + suma);
    }
    
    public Calendar getFechaIngreso(int indice) {
        if (indice < 0 || indice >= Registrados) {
            return null;
        }
        
        return Jugadores[indice].getFechaIngreso();
    }
    
    public String getFechaIngresoFormat(int indice, String patron) {
        Calendar calendario = getFechaIngreso(indice);
        if (calendario == null) {
            return "";
        }
        
        String formato = (patron == null || patron.isEmpty()) ? "dd/MM/yyyy HH:mm" : patron;
        
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        
        return sdf.format(calendario.getTime());
    }
    
    public Player getPlayer(int indice) {
        if (indice < 0 || indice >= Registrados)
            return null;
        
        return Jugadores[indice];
    }
    
    public String getUsuario(int indice) {
        Player jugador = getPlayer(indice);
        
        return (jugador == null) ? null : jugador.getUsername();
    }
    
    public boolean isActivo(int indice) {
        Player jugador = getPlayer(indice);
        
        return jugador != null && jugador.isActivo();
    }
    
    public int ContarUsuariosActivos() {
        int cuenta = 0;
        
        for (int i = 0; i < Registrados; i++) {
            if (Jugadores[i] != null && Jugadores[i].isActivo())
                cuenta++;
        }
        
        return cuenta;
    }
    
    public String[] getUsuariosActivos() {
        int cuenta = ContarUsuariosActivos();
        String[] activos = new String[cuenta];
        
        int k = 0;
        
        for (int i = 0; i < Registrados; i++) {
            if (Jugadores[i] != null && Jugadores[i].isActivo()) {
                activos[k++] = Jugadores[i].getUsername();
            }
        }
        
        return activos;
    }
}
