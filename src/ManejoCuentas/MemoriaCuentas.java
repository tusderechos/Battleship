/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ManejoCuentas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import Interfaces.Datos;

/**
 *
 * @author Hp
 */
public class MemoriaCuentas implements Datos {
    
    private String[] Usuarios;
    private String[] Contrasenas;
    
    private int[] Puntos;
    private Calendar[] FechaIngreso;
    private boolean[] Activo;
    private ArrayList<String[]>[] Logs;
    public int Registrados;
    public int MAX;

    public MemoriaCuentas(int max) {
        this.MAX = max;
        Registrados = 0;
        
        Usuarios = new String[MAX];
        Contrasenas = new String[MAX];
        Puntos = new int[MAX];
        FechaIngreso = new Calendar[MAX];
        Activo = new boolean[MAX];
        Logs = new ArrayList[MAX];
        
        for (int i = 0; i < MAX; i++) {
            Logs[i] = new ArrayList<>();
        }
    }
    
    @Override
    public int getIndiceUsuario(String usuario) {
        return BuscarUsuario(usuario, 0);
    }
    
    @Override
    public void AgregarLog(String actor, String fecha, String rival, String resultado) {
        int indice = getIndiceUsuario(actor);
        
        if (indice < 0 || indice >= Logs.length) {
            return;
        }
        
        if (Logs[indice] == null) {
            Logs[indice] = new ArrayList<>();
        }
        
        String[] entrada = new String[] {fecha, rival, resultado};
        Logs[indice].add(entrada);
    }
    
    @Override
    public ArrayList<String[]> ObtenerLogsUsuario(String usuario) {
        int indice = getIndiceUsuario(usuario);
        
        if (indice < 0 || indice >= Registrados || !Activo[indice]) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(Logs[indice]);
    }
    
    public ArrayList<String[]> getLogsUsuario(String usuario) {
        return ObtenerLogsUsuario(usuario);
    }
    
    private int BuscarUsuario(String usuario, int indice) {
        if (usuario == null || indice >= Registrados) {
            return -1;
        }
        
        if (usuario.equalsIgnoreCase(Usuarios[indice])) {
            return indice;
        }
        
        return BuscarUsuario(usuario, indice + 1);
    }
    
    public int ContarUsuariosActivos() {
        return ContarActivos(0);
    }
    
    public int ContarActivos(int indice) {
        if (indice >= Registrados) {
            return 0;
        }
        
        int suma = Activo[indice] ? 1 : 0;
        
        return suma + ContarActivos(indice + 1);
    }
    
    public boolean ExisteUsuario(String usuario) {
        return getIndiceUsuario(usuario) >= 0;
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
    
    public boolean ValidarLogin(String usuario, String contrasena) {
        if (usuario == null || contrasena == null) {
            return false;
        }

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            return false;
        }

        int indice = getIndiceUsuario(usuario);

        if (indice == -1 || !Activo[indice]) {
            return false;
        }

        return contrasena.equals(Contrasenas[indice]);
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
        
        Usuarios[Registrados] = usuario;
        Contrasenas[Registrados] = contrasena;
        Puntos[Registrados] = 0;
        FechaIngreso[Registrados] = Calendar.getInstance();
        Activo[Registrados] = true;
        Logs[Registrados] = new ArrayList<>();
        
        Registrados++;
        
        return true;
    }
    
    public boolean Eliminar(String Usuario) {
        int indice = getIndiceUsuario(Usuario);
        
        if (indice == -1) {
            return false;
        }
        
        int ultimo = Registrados - 1;
        
        //Compactar moviendo el ultimo a un "hueco" por decirlo asi
        Usuarios[indice] = Usuarios[ultimo];
        Contrasenas[indice] = Contrasenas[ultimo];
        Puntos[indice] = Puntos[ultimo];
        FechaIngreso[indice] = FechaIngreso[ultimo];
        Activo[indice] = Activo[ultimo];
        
        //Borrar el ultimo ahora si
        Usuarios[ultimo] = null;
        Contrasenas[ultimo] = null;
        Puntos[ultimo] = 0;
        FechaIngreso[ultimo] = null;
        Activo[ultimo] = false;
        
        Registrados--;
        return true;
    }
    
    public String getUsuario(int indice) {
        if (indice < 0 || indice >= Registrados) {
            return null;
        }
        
        return Usuarios[indice];
    }
    
    public boolean ActualizarContrasena(int indice, String nuevacontra) {
        if (indice < 0 || indice >= Registrados) {
            return false;
        }
        
        if (nuevacontra == null || nuevacontra.isEmpty()) {
            return false;
        }
        
        Contrasenas[indice] = nuevacontra;
        return true;
    }
    
    public boolean ValidarContrasenaActual(int indice, String contraactual) {
        if (indice < 0 || indice >= Registrados) {
            return false;
        }
        
        if (contraactual == null) {
            return false;
        }
        
        return contraactual.equals(Contrasenas[indice]);
    }

    public int getPuntos(int indice) {
        if (indice < 0 || indice >= Registrados) {
            return 0;
        }
        
        return Puntos[indice];
    }

    public void setPuntos(int indice, int puntos) {
        if (indice < 0 || indice >= Registrados) {
            return;
        }
        
        Puntos[indice] = puntos;
    }
    
    public void SumarPuntos(int indice, int suma) {
        if (indice < 0 || indice >= Registrados) {
            return;
        }
        
        SumarPuntos(indice + 1, suma);
        
        if (Activo[indice]) {
            Puntos[indice] += suma;
        }
    }

    public void SumarPuntos(String usuario, int suma) {
        SumarPuntos(0, suma);
    }
    
    public Calendar getFechaIngreso(int indice) {
        if (indice < 0 || indice >= Registrados) {
            return null;
        }
        
        return FechaIngreso[indice];
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
    
    public boolean isActivo(int indice) {
        if (indice < 0 || indice >= Registrados) {
            return false;
        }
        
        return Activo[indice];
    }

    public String[] getUsuarios() {
        if (Registrados == 0) {
            return new String[0];
        }
        ArrayList<String> lista = new ArrayList<>();
        
        for (int i = 0; i < Registrados; i++) {
            if (Activo[i]) {
                lista.add(Usuarios[i]);
            }
        }
        
        return lista.toArray(new String[0]);
    }
}
