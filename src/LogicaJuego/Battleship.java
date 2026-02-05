/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaJuego;

/**
 *
 * @author USUARIO
 */

import ManejoCuentas.MemoriaCuentas;
import enums.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Battleship {
    
    public static final String AGUA = "~";
    public static final String HIT = "X";
    public static final String MISS = "F";
    
    private final MemoriaCuentas Memoria;
    private final Dificultad dificultad;
    private final ModoJuego Modo;
    
    private String Jugador1;
    private String Jugador2;
    
    private final String[][] Tablero1;
    private final String[][] Tablero2;
    
    private int Turno = 1;
    private int Colocados1 = 0;
    private int Colocados2 = 0;
    
    private final int[] Usados1 = new int[4];
    private final int[] Usados2 = new int[4];
    
    private final Random random = new Random();
    
    private String UltimoBarcoHundido = null;
    private int UltimoJugadorAfectado = -1;
    
    public Battleship(MemoriaCuentas Memoria, Dificultad dificultad, ModoJuego Modo) {
        this.Memoria = Memoria;
        this.dificultad = dificultad;
        this.Modo = Modo;
        
        Tablero1 = new String[8][8];
        Tablero2 = new String[8][8];
        
        Llenar(Tablero1);
        Llenar(Tablero2);
    }
    
    private void Llenar(String[][] tablero) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                tablero[fila][col] = AGUA;
            }
        }
    }
    
    public boolean IniciarPartida(String jug1, String jug2) {
        if (jug1 == null || jug2 == null) {
            return false;
        }
        if (jug1.equalsIgnoreCase(jug2)) {
            return false;
        }
        if (Memoria.getIndiceUsuario(jug1) == -1 || Memoria.getIndiceUsuario(jug2) == -1) {
            return false;
        }
        
        Jugador1 = jug1;
        Jugador2 = jug2;
        
        return true;
    }
    
    public String[][] getTablero(int jugador) {
        return (jugador == 1) ? Tablero1 : Tablero2;
    }
    
    public Resultado ColocarBarco(int jugador, String codigo, int fila, int col, Orientacion orientacion) {
        TipoBarco tb = TipoBarco.FromCodigo(codigo);
        
        if (tb == null) {
            return Resultado.INVALIDO;
        }
        
        if (jugador == 1 && Colocados1 >= dificultad.getBarcos()) {
            return Resultado.YA_NO_PUEDE;
        }
        if (jugador == 2 && Colocados2 >= dificultad.getBarcos()) {
            return Resultado.YA_NO_PUEDE;
        }
        
        int[] usados = (jugador == 1) ? Usados1 : Usados2;
        int indice = tb.ordinal();
        
        //Para la parte de la dificultad easy, donde el barco DT se puede repetir 2 veces
        if (usados[indice] > 0) {
            if (!(dificultad == Dificultad.EASY && tb == TipoBarco.DT && usados[indice] == 1)) {
                return Resultado.REPETIDO;
            }
        }
        
        String[][] tab = (jugador == 1) ? Tablero1 : Tablero2;
        
        int finF = fila + ((orientacion == Orientacion.VERTICAL) ? tb.longitud - 1 : 0);
        int finC = col + ((orientacion == Orientacion.HORIZONTAL) ? tb.longitud - 1 : 0);
        
        if (!enRango(fila, col) || !enRango(finF, finC)) {
            return Resultado.FUERA_RANGO;
        }
        
        for (int i = 0; i < tb.longitud; i++) {
            int f = fila + ((orientacion == Orientacion.VERTICAL) ? i : 0);
            int c = col + ((orientacion == Orientacion.HORIZONTAL) ? i : 0);
            
            if (!tab[f][c].equals(AGUA)) {
                return Resultado.CHOCA;
            }
        }
        
        for (int i = 0; i < tb.longitud; i++) {
            int f = fila + ((orientacion == Orientacion.VERTICAL) ? i : 0);
            int c = col + ((orientacion == Orientacion.HORIZONTAL) ? i : 0);
            
            tab[f][c] = tb.codigo;
        }
        
        usados[indice]++;
        
        if (jugador == 1) {
            Colocados1++;
        } else {
            Colocados2++;
        }
        
        return Resultado.OK;
    }
    
    public Resultado Disparar(int fila, int col, boolean confirmarretiro) {
        if (fila == -1 && col == -1) {
            if (confirmarretiro) {
                FinalizarRetiro();
                return Resultado.RETIRO;
            }
            
            return Resultado.INVALIDO;
        }
        
        String[][] rival = (Turno == 1) ? Tablero2 : Tablero1;
        UltimoJugadorAfectado = (Turno == 1) ? 2 : 1;
        
        if (!enRango(fila, col)) {
            return Resultado.FUERA_RANGO;
        }
        
        if (rival[fila][col].equals(AGUA)) {
            rival[fila][col] = MISS;
            CambiarTurno();
            UltimoBarcoHundido = null;
            
            return Resultado.MISS;
        }
        
        if (rival[fila][col].equals(HIT) || rival[fila][col].equals(MISS)) {
            return Resultado.INVALIDO;
        }
        
        String barcoimpactado = rival[fila][col];
        rival[fila][col] = HIT;
        
        UltimoBarcoHundido = DetectarBarcoHundido(rival, barcoimpactado);
        
        Regenerar(rival);
        
        if (!QuedanBarcos(rival)) {
            FinalizarVictoria();
            return Resultado.GANO;
        }
        
        return Resultado.HIT;
    }
    
    private void CambiarTurno() {
        Turno = (Turno == 1) ? 2 : 1;
    }
    
    private boolean enRango(int f, int c) {
        return f >= 0 && f < 8 && c >= 0 && c < 8;
    }
    
    private boolean QuedanBarcos(String[][] tablero) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                for(TipoBarco tb : TipoBarco.values()) {
                    if (tablero[fila][col].equals(tb.codigo))
                        return true;
                }
            }
        }
        
        return false;
    }
    
    private void Regenerar(String[][] tablero) {
        String[][] nuevo = new String[8][8];
        Llenar(nuevo);
        
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                if (tablero[fila][col].equals(HIT))
                    nuevo[fila][col] = HIT;
            }
        }
        
        for (TipoBarco tb : TipoBarco.values()) {
            int vivos = Contar(tablero, tb.codigo);
            ColocarAleatorio(nuevo, tb.codigo, vivos);
        }
        
        for (int i = 0; i < 8; i++) {
            System.arraycopy(nuevo[i], 0, tablero[i], 0, 8);
        }
    }
    
    private int Contar(String[][] tablero, String s) {
        int c = 0;
        
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                if (tablero[fila][col].equals(s))
                    c++;
            }
        }
        
        return c;
    }
    
    private void ColocarAleatorio(String[][] tablero, String s, int len) {
        if (len == 0) {
            return;
        }
        
        for (int k = 0; k < 2000; k++) {
            boolean wasd = random.nextBoolean();
            int fila = random.nextInt(8);
            int col = random.nextInt(8);
            
            int ffila = fila + (wasd ? 0 : len - 1);
            int ccol = col + (wasd ? len - 1 : 0);
            
            if (!enRango(ffila, ccol)) {
                continue;
            }
            
            boolean ok = true;
            
            for (int i = 0; i < len; i++) {
                int r = fila + (wasd ? 0 : i);
                int co = col + (wasd ? i : 0);
                
                if (!tablero[r][co].equals(AGUA)) {
                    ok = false;
                    break;
                }
            }
            
            if (!ok)
                continue;
            
            for (int i = 0; i < len; i++) {
                int r = fila + (wasd ? 0 : i);
                int co = col + (wasd ? i : 0);
                tablero[r][co] = s;
            }
            
            return;
        }
    }
    
    private void FinalizarVictoria() {
        String ganador = (Turno == 1) ? Jugador1 : Jugador2;
        String perdedor = (Turno == 1) ? Jugador2 : Jugador1;
        String fecha = Fecha();
        
        Memoria.AgregarLog(ganador, fecha, perdedor, "GANO");
        Memoria.AgregarLog(perdedor, fecha, ganador, "PERDIO");
        Memoria.SumarPuntos(ganador, 3);
    }
    
    private void FinalizarRetiro() {
        String perdedor = (Turno == 1) ? Jugador1 : Jugador2;
        String ganador = (Turno == 1) ? Jugador2 : Jugador1;
        String fecha = Fecha();
        
        Memoria.AgregarLog(ganador, fecha, perdedor, "GANO (RETIRO)");
        Memoria.AgregarLog(perdedor, fecha, ganador, "SE HA RETIRADO");
        Memoria.SumarPuntos(ganador, 3);
    }
    
    private String DetectarBarcoHundido(String[][] tablero, String codigobarco) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                if (tablero[fila][col].equals(codigobarco)) {
                    return null;
                }
            }
        }
        
        return codigobarco;
    }
    
    private String Fecha() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime());
    }

    public String getJugador1() {
        return Jugador1;
    }

    public String getJugador2() {
        return Jugador2;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public int getTurno() {
        return Turno;
    }

    public String getJugadorTurno() {
        if (Turno == 1) {
            return Jugador1;
        } else {
            return Jugador2;
        }
    }

    public int getColocados1() {
        return Colocados1;
    }

    public int getColocados2() {
        return Colocados2;
    }
    
    public int getColocados(int jugador) {
        return (jugador == 1) ? Colocados1 : Colocados2;
    }
    
    public int getMaxBarcos() {
        return dificultad.getBarcos();
    }

    public String getUltimoBarcoHundido() {
        return UltimoBarcoHundido;
    }

    public int getUltimoJugadorAfectado() {
        return UltimoJugadorAfectado;
    }
}
