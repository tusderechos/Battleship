/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author USUARIO
 */

import enums.*;
import ManejoCuentas.MemoriaCuentas;
import LogicaJuego.Battleship;

import javax.swing.*;
import java.awt.*;

public class PanelJuego extends JFrame {
    
    private final MemoriaCuentas Memoria;
    private final MenuPrincipal menuPrincipal;
    
    private final Dificultad dificultad;
    private final ModoJuego Modo;
    
    private final String UsuarioActivo;
    
    private Battleship Juego;
    
    private JButton[][] BtnMiTablero;
    private JButton[][] BtnTableroRival;
    
    private JLabel LblTitulo;
    private JLabel LblEstado;
    private JLabel LblTurno;
    private JLabel LblColocando;
    private JLabel LblSeleccion;
    
    private JComboBox<String> CBBarco;
    private JButton BtnRotar;
    private JButton BtnConfirmarColocacion;
    private JButton BtnRetirar;
    
    private boolean FaseColocacion = true;
    private int JugadorColocando = 1;
    private Orientacion orientacion = Orientacion.HORIZONTAL;
    
    public PanelJuego(MemoriaCuentas Memoria, String UsuarioActivo, MenuPrincipal menuPrincipal, Dificultad dificultad, ModoJuego Modo) {
        this.Memoria = Memoria;
        this.UsuarioActivo = (UsuarioActivo == null) ? "" : UsuarioActivo.trim();
        this.menuPrincipal = menuPrincipal;
        this.dificultad = (dificultad == null) ? Dificultad.NORMAL : dificultad;
        this.Modo = (Modo == null) ? ModoJuego.TUTORIAL : Modo;
        
        if (this.UsuarioActivo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero inicia sesion", "Aviso", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }
        
        Juego = new Battleship(Memoria, this.dificultad, this.Modo);
        
        String jugador2 = PedirJugador2();
        
        if (jugador2 == null) {
            dispose();
            
            if (menuPrincipal != null)
                menuPrincipal.setVisible(true);
            
            return;
        }
        
        boolean ok = Juego.IniciarPartida(this.UsuarioActivo, jugador2);
        
        if (!ok) {
            JOptionPane.showMessageDialog(null, "No se pudo iniciar la partida", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            
            if (menuPrincipal != null)
                menuPrincipal.setVisible(true);
            
            return;
        }
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_juego.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("BATTLESHIP - Juego");
        setSize(1100, 820);
        setResizable(false);
        setContentPane(PanelFondo);
        setLocationRelativeTo(menuPrincipal);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        JPanel PanelHeader = new JPanel();
        PanelHeader.setLayout(new BoxLayout(PanelHeader, BoxLayout.Y_AXIS));
        PanelHeader.setBackground(new Color(10, 10, 20));
        PanelHeader.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
        
        LblTitulo = new JLabel("BATTLESHIP");
        LblTitulo.setForeground(new Color(235, 235, 255));
        LblTitulo.setFont(new Font("ITC Machine Std", Font.PLAIN, 40));
        LblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        LblTurno = new JLabel("");
        LblTurno.setForeground(new Color(200, 220, 255));
        LblTurno.setFont(new Font("DIN Condensed", Font.BOLD, 16));
        LblTurno.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        LblEstado = new JLabel("");
        LblEstado.setForeground(new Color(220, 220, 220));
        LblEstado.setFont(new Font("DIN Condensed", Font.BOLD, 14));
        LblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        PanelHeader.add(LblTitulo);
        PanelHeader.add(Box.createVerticalStrut(6));
        PanelHeader.add(LblTurno);
        PanelHeader.add(Box.createVerticalStrut(4));
        PanelHeader.add(LblEstado);
        
        PanelFondo.add(PanelHeader, BorderLayout.NORTH);
        
        //Panel central
        JPanel PanelCentral = new JPanel();
        PanelCentral.setLayout(new GridLayout(1, 2, 18, 0));
        PanelCentral.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        PanelCentral.setBackground(new Color(15, 15, 25));
        
        //Todo lo que tiene que ver con el tablero del usuario conectado
        JPanel PanelMiTablero = new JPanel();
        PanelMiTablero.setLayout(new BorderLayout());
        PanelMiTablero.setBackground(new Color(20, 20, 35));
        PanelMiTablero.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        JLabel LblMiTablero = new JLabel("MI TABLERO", SwingConstants.CENTER);
        LblMiTablero.setForeground(new Color(220, 220, 255));
        LblMiTablero.setFont(new Font("DIN Condensed", Font.BOLD, 16));
        
        PanelMiTablero.add(LblMiTablero, BorderLayout.NORTH);
        
        JPanel PanelGridMiTablero = new JPanel();
        PanelGridMiTablero.setBackground(new Color(20, 20, 35));
        BtnMiTablero = CrearGridBotones(PanelGridMiTablero, true);
        
        PanelMiTablero.add(PanelGridMiTablero, BorderLayout.CENTER);
        
        //Todo lo que tiene que ver con el tablero del rival
        JPanel PanelTableroRival = new JPanel();
        PanelTableroRival.setLayout(new BorderLayout());
        PanelTableroRival.setBackground(new Color(20, 20, 35));
        PanelTableroRival.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        JLabel LblTableroRival = new JLabel("TABLERO RIVAL", SwingConstants.CENTER);
        LblTableroRival.setForeground(new Color(220, 220, 255));
        LblTableroRival.setFont(new Font("DIN Condensed", Font.BOLD, 16));
        
        PanelTableroRival.add(LblTableroRival, BorderLayout.NORTH);
        
        JPanel PanelGridTableroRival = new JPanel();
        PanelGridTableroRival.setBackground(new Color(20, 20, 35));
        BtnTableroRival = CrearGridBotones(PanelGridTableroRival, false);
        
        PanelTableroRival.add(PanelGridTableroRival, BorderLayout.CENTER);
        
        PanelCentral.add(PanelMiTablero);
        PanelCentral.add(PanelTableroRival);
        
        PanelFondo.add(PanelCentral, BorderLayout.CENTER);
        
        //Panel inferior
        JPanel PanelBajo = new JPanel();
        PanelBajo.setLayout(new BorderLayout());
        PanelBajo.setBackground(new Color(10, 10, 20));
        PanelBajo.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
        
        JPanel PanelInfo = new JPanel();
        PanelInfo.setOpaque(false);
        PanelInfo.setLayout(new BoxLayout(PanelInfo, BoxLayout.Y_AXIS));
        
        LblColocando = new JLabel("");
        LblColocando.setForeground(new Color(220, 220, 220));
        LblColocando.setFont(new Font("DIN Condensed", Font.PLAIN, 14));
        
        LblSeleccion = new JLabel("");
        LblSeleccion.setForeground(new Color(220, 220, 220));
        LblSeleccion.setFont(new Font("DIN Condensed", Font.PLAIN, 14));
        
        PanelInfo.add(LblColocando);
        PanelInfo.add(Box.createVerticalStrut(4));
        PanelInfo.add(LblSeleccion);
        
        PanelBajo.add(PanelInfo, BorderLayout.WEST);
        
        JPanel PanelControles = new JPanel();
        PanelControles.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        PanelControles.setOpaque(false);
        
        CBBarco = new JComboBox<>(new String[]{"PA", "AZ", "SM", "DT"});
        CBBarco.setPreferredSize(new Dimension(90, 30));
        
        BtnRotar = new JButton("ROTAR (H)");
        BtnRotar.addActionListener(e -> onRotar());
        
        BtnConfirmarColocacion = new JButton("LISTO / CAMBIAR JUGADOR");
        BtnConfirmarColocacion.addActionListener(e -> onConfirmarColocacion());
        
        BtnRetirar = new JButton("RETIRAR");
        BtnRetirar.addActionListener(e -> onRetirar());
        
        PanelControles.add(new JLabel("Barco:"));
        PanelControles.add(CBBarco);
        PanelControles.add(BtnRotar);
        PanelControles.add(BtnConfirmarColocacion);
        PanelControles.add(BtnRetirar);
        
        PanelBajo.add(PanelControles, BorderLayout.EAST);
        
        PanelFondo.add(PanelBajo, BorderLayout.SOUTH);
        
        //Ajuste inicial de botones segun fase
        RefrescarControlesPorFase();
        
        //Renderizacion inicial
        ActualizarLabels();
        RenderizarTodo();
    }
    
    private void RefrescarControlesPorFase() {
        CBBarco.setEnabled(FaseColocacion);
        BtnRotar.setEnabled(FaseColocacion);
        BtnConfirmarColocacion.setEnabled(FaseColocacion);
        BtnRetirar.setEnabled(!FaseColocacion);
    }
    
    private void ActualizarLabels() {
        LblTurno.setText("Jugador 1: " + Juego.getJugador1() + "   |   Jugador 2: " + Juego.getJugador2());
        LblEstado.setText("Dificultad: " + dificultad + " (" + dificultad.getBarcos() + " barcos)" + "   |   Modo: " + Modo);
        
        if (FaseColocacion) {
            LblColocando.setText("Colocacion: Jugador " + JugadorColocando + " (" + (JugadorColocando == 1 ? Juego.getJugador1() : Juego.getJugador2()) + ")");
            LblSeleccion.setText("Seleccionado: " + CBBarco.getSelectedItem() + " | Orientacion: " + orientacion);
        } else {
            LblColocando.setText("Turno de: " + Juego.getJugadorTurno());
            LblSeleccion.setText("Dispara en el tablero rival");
        }
    }
    
    private void RenderizarTodo() {
        ActualizarLabels();
        
        if (FaseColocacion) {
            //Aqui aseguro que solo se muestre el tablero del jugador que esta en la fase de colocacion
            char[][] tablero = Juego.getTablero(JugadorColocando);
            RenderizarTablero(tablero, BtnMiTablero, true);
            
            //Durante la fase de colocacion, el tablero del rival se mostrara en blanco
            LimpiarGrid(BtnTableroRival);
            return;
        }
        
        /*
            Durante el bombardeo al rival:
            - El tablero del jugador va a ser visible
            - El tablero del rival va a ser visible solamente si estan en el modo tutorial
        */
        boolean mostrarP1 = true;
        boolean mostrarP2 = true;
        
        if (Modo == ModoJuego.ARCADE) {
            //Ocultar barcos del rival del que esta jugando
            if (Juego.getTurno() == 1) {
                mostrarP2 = false; //Turno de P1, se oculta P2
            } else {
                mostrarP1 = false; //Turno de P2, se oculta P1
            }
        }
        
        RenderizarTablero(Juego.getTablero(1), BtnMiTablero, mostrarP1);
        RenderizarTablero(Juego.getTablero(2), BtnTableroRival, mostrarP2);
        
        HabilitarClickDisparo();
    }
    
    private void HabilitarClickDisparo() {
        if (FaseColocacion) {
            setGridEnabled(BtnMiTablero, true);
            setGridEnabled(BtnTableroRival, false);
            return;
        }
        
        boolean turnoP1 = (Juego.getTurno() == 1);
        
        setGridEnabled(BtnMiTablero, !turnoP1);
        setGridEnabled(BtnTableroRival, turnoP1);
    }
    
    private void setGridEnabled(JButton[][] grid, boolean habilitado) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                grid[fila][col].setEnabled(habilitado);
            }
        }
    }
    
    private JButton[][] CrearGridBotones(JPanel contenedor, boolean esmitablero) {
        contenedor.removeAll();
        contenedor.setLayout(new GridLayout(8, 8, 2, 2));
        
        JButton[][] botones = new JButton[8][8];
        
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                JButton boton = new JButton();
                boton.setFont(new Font("DIN Condensed", Font.BOLD, 14));
                boton.setPreferredSize(new Dimension(60, 60));
                boton.setBackground(new Color(30, 30, 55));
                boton.setForeground(new Color(240, 240, 255));
                boton.putClientProperty("fila", fila);
                boton.putClientProperty("col", col);
                
                if (esmitablero) {
                    boton.addActionListener(e -> onClickMiTablero(boton));
                } else {
                    boton.addActionListener(e -> onClickRival(boton));
                }
                
                botones[fila][col] = boton;
                contenedor.add(boton);
            }
        }
        
        contenedor.revalidate();
        contenedor.repaint();
        return botones;
    }
    
    private void onClickMiTablero(JButton boton) {
        if (!FaseColocacion) {
            return;
        }
        
        int fila = (int) boton.getClientProperty("fila");
        int col = (int) boton.getClientProperty("col");
        
        String codigo = (String) CBBarco.getSelectedItem();
        Resultado resultado = Juego.ColocarBarco(JugadorColocando, codigo, fila, col, orientacion);
        
        if (resultado != Resultado.OK) {
            JOptionPane.showMessageDialog(this, ColocarMensaje(resultado), "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        RenderizarTodo();
    }
    
    private void onClickRival(JButton boton) {
        if (FaseColocacion) {
            return;
        }
        
        int fila = (int) boton.getClientProperty("fila");
        int col = (int) boton.getClientProperty("col");
        
        Resultado resultado = Juego.Disparar(fila, col, false);
        
        if (resultado == Resultado.INVALIDO) {
            JOptionPane.showMessageDialog(this, "Tiro invalido o repetido", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (resultado == Resultado.FUERA_RANGO) {
            JOptionPane.showMessageDialog(this, "Fuera del Tablero", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (resultado == Resultado.MISS) {
            JOptionPane.showMessageDialog(this, "Fallaste!", "Turno", JOptionPane.INFORMATION_MESSAGE);
        } else if (resultado == Resultado.HIT) {
            JOptionPane.showMessageDialog(this, "Impacto!! \nTablero rival se regenero", "Turno", JOptionPane.INFORMATION_MESSAGE);
        } else if (resultado == Resultado.GANO) {
            JOptionPane.showMessageDialog(this, "Gano " + Juego.getJugadorTurno() + "\n(+3 puntos)", "Fin Partida", JOptionPane.INFORMATION_MESSAGE);
            Volver();
            return;
        } else if (resultado == Resultado.RETIRO) {
            JOptionPane.showMessageDialog(this, "Retiro confirmado", "Fin Partida", JOptionPane.INFORMATION_MESSAGE);
            Volver();
            return;
        }
        
        ActualizarLabels();
        RenderizarTodo();
    }
    
    private void onRetirar() {
        if (FaseColocacion) {
            JOptionPane.showMessageDialog(this, "Solo te puedes retirar durante la partida", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int opcion = JOptionPane.showConfirmDialog(this, "Seguro que quieres retirarte?\nTu rival ganara", "Retiro", JOptionPane.YES_NO_OPTION);
        
        if (opcion != JOptionPane.YES_OPTION)
            return;
        
        Resultado resultado = Juego.Disparar(-1, -1, true);
        
        if (resultado == Resultado.RETIRO) {
            JOptionPane.showMessageDialog(this, "Retiro Confirmado", "Fin Partida", JOptionPane.INFORMATION_MESSAGE);
            Volver();
        }
    }
    
    private void Volver() {
        dispose();
            
        if (menuPrincipal != null) {
            menuPrincipal.setVisible(true);
        }
    }
    
    private void onRotar() {
        orientacion = (orientacion == Orientacion.HORIZONTAL) ? Orientacion.VERTICAL : Orientacion.HORIZONTAL;
        BtnRotar.setText(orientacion == Orientacion.HORIZONTAL ? "Rotar (H)" : "Rotar (V)");
        ActualizarLabels();
    }
    
    private void onConfirmarColocacion() {
        if (!FaseColocacion)
            return;
        
        int max = dificultad.getBarcos();
        int colocados = Juego.getColocados(JugadorColocando);
        
        if (colocados < max) {
            JOptionPane.showMessageDialog(this, "Te faltan barcos: " + colocados + "/" + max, "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (JugadorColocando == 1) {
            JugadorColocando = 2;
            LimpiarVistaTableros();
//            ActualizarLabels();
            RenderizarTodo();
            return;
        }
        
        //Inicia el disparo
        FaseColocacion = false;
//        ActualizarLabels();
        RefrescarControlesPorFase();
        RenderizarTodo();
    }
    
    private void LimpiarVistaTableros() {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                BtnMiTablero[fila][col].setText("");
                BtnTableroRival[fila][col].setText("");
            }
        }
    }
    
    private void LimpiarGrid(JButton[][] vista) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                vista[fila][col].setText("");
            }
        }
    }
    
    private void RenderizarTablero(char[][] modelo, JButton[][] vista, boolean mostrarbarcos) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                char var = modelo[fila][col];
                
                if (!mostrarbarcos) {
                    if (var == TipoBarco.PA.simbolo || var == TipoBarco.AZ.simbolo || var == TipoBarco.SM.simbolo || var == TipoBarco.DT.simbolo) {
                        var = Battleship.AGUA;
                    }
                }
                
                JButton boton = vista[fila][col];
                
                if (var == Battleship.AGUA) {
                    boton.setText("");
                } else {
                    boton.setText(String.valueOf(var));
                }
            }
        }
    }
    
    private String PedirJugador2() {
        while (true) {
            String jugador2 = JOptionPane.showInputDialog(null, "Ingresa el username del JUGADOR 2 (o escribe EXIT para cancelar): ", "Player 2", JOptionPane.QUESTION_MESSAGE);
            
            if (jugador2 == null) {
                return null;
            }
            
            jugador2 = jugador2.trim();
            
            if (jugador2.equalsIgnoreCase("EXIT")) {
                return null;
            }
            if (jugador2.isEmpty()) {
                continue;
            }
            if (jugador2.equalsIgnoreCase(UsuarioActivo)) {
                JOptionPane.showMessageDialog(null, "El jugador 2 no puede ser el mismo que el jugador 1", "Aviso", JOptionPane.WARNING_MESSAGE);
                continue;
            }
            
            int indice = Memoria.getIndiceUsuario(jugador2);
            
            if (indice == -1) {
                JOptionPane.showMessageDialog(null, "Ese usuario no existe, intentalo otra vez", "Aviso", JOptionPane.WARNING_MESSAGE);
                continue;
            }
            if (!Memoria.isActivo(indice)) {
                JOptionPane.showMessageDialog(null, "Ese usuario esta inactivo", "Aviso", JOptionPane.WARNING_MESSAGE);
                continue;
            }
            
            return jugador2;
        }
    }
    
    private String ColocarMensaje(Resultado resultado) {
        if (resultado == Resultado.FUERA_RANGO) {
            return "Ese barco se sale del tablero";
        }
        if (resultado == Resultado.CHOCA) {
            return "No se puede colocar ahi: choca con otro barco";
        }
        if (resultado == Resultado.REPETIDO) {
            return "Ese tipo ya fue usado\nSolo en EASY se puede repetir el barco DT una vez";
        }
        if (resultado == Resultado.YA_NO_PUEDE) {
            return "Ya colocaste todos los barcos para esta dificultad";
        }
        
        return "Entrada invalida";
    }
}