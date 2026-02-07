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
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private JLabel LblColocando;
    private JLabel LblSeleccion;
    
    private JLabel LblMiTableroTitulo;
    private JLabel LblTableroRivalTitulo;
    
    private JPanel PanelMiTablero;
    private JPanel PanelTableroRival;
    
    private JComboBox<String> CBBarco;
    private JButton BtnRotar;
    private JButton BtnConfirmarColocacion;
    private JButton BtnRetirar;
    
    private boolean FaseColocacion = true;
    private int JugadorColocando = 1;
    private Orientacion orientacion = Orientacion.HORIZONTAL;
    
    private final Point[] PreviewCeldas = new Point[5];
    private int CuentaPreview = 0;
    
    private boolean InicializacionExitosa = false;
    
    public PanelJuego(MemoriaCuentas Memoria, String UsuarioActivo, MenuPrincipal menuPrincipal, Dificultad dificultad, ModoJuego Modo) {
        this.Memoria = Memoria;
        this.UsuarioActivo = (UsuarioActivo == null) ? "" : UsuarioActivo.trim();
        this.menuPrincipal = menuPrincipal;
        this.dificultad = (dificultad == null) ? Dificultad.NORMAL : dificultad;
        this.Modo = (Modo == null) ? ModoJuego.TUTORIAL : Modo;
        
        if (this.UsuarioActivo.isEmpty()) {
            MostrarMensaje("Primero inicia sesion", "Aviso", JOptionPane.WARNING_MESSAGE);
//            dispose();
            return;
        }
        
        Juego = new Battleship(Memoria, this.dificultad, this.Modo);
        
        String jugador2 = PedirJugador2();
        
        if (jugador2 == null) {
//            dispose();
//            
//            if (menuPrincipal != null)
//                menuPrincipal.setVisible(true);
//            
            return;
        }
                
        if (!Juego.IniciarPartida(this.UsuarioActivo, jugador2)) {
            MostrarMensaje("No se pudo iniciar la partida", "Error", JOptionPane.ERROR_MESSAGE);
//            dispose();
//            
//            if (menuPrincipal != null)
//                menuPrincipal.setVisible(true);
//            
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
        PanelFondo.setLayout(new BorderLayout());
        
        setTitle("BATTLESHIP - Juego");
        setSize(1100, 820);
        setResizable(false);
        setContentPane(PanelFondo);
        setLocationRelativeTo(menuPrincipal);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        //Todo el header
        JPanel PanelHeader = new JPanel();
        PanelHeader.setLayout(new BoxLayout(PanelHeader, BoxLayout.Y_AXIS));
        PanelHeader.setBackground(new Color(10, 10, 20));
        PanelHeader.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(80, 80, 120)), BorderFactory.createEmptyBorder(18, 20, 18, 20)));
        
        LblTitulo = new JLabel("BATTLESHIP", SwingConstants.CENTER);
        LblTitulo.setForeground(new Color(240, 240, 255));
        LblTitulo.setFont(new Font("ITC Machine Std", Font.PLAIN, 46));
        LblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        LblEstado = new JLabel("", SwingConstants.CENTER);
        LblEstado.setForeground(Color.WHITE);
        LblEstado.setFont(new Font("DIN Condensed", Font.BOLD, 20));
        LblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel PanelSubEstado = new JPanel();
        PanelSubEstado.setLayout(new GridLayout(1, 2, 40, 0));
        PanelSubEstado.setOpaque(false);
        PanelSubEstado.setMaximumSize(new Dimension(800, 30));
        
        LblColocando = new JLabel();
        LblColocando.setHorizontalAlignment(SwingConstants.CENTER);
        LblColocando.setForeground(Color.WHITE);
        LblColocando.setFont(new Font("DIN Condensed", Font.BOLD, 16));
        
        LblSeleccion = new JLabel();
        LblSeleccion.setHorizontalAlignment(SwingConstants.CENTER);
        LblSeleccion.setForeground(Color.WHITE);
        LblSeleccion.setFont(new Font("DIN Condensed", Font.BOLD, 16));
        
        PanelSubEstado.add(LblColocando);
        PanelSubEstado.add(LblSeleccion);
        
        PanelHeader.add(LblTitulo);
        PanelHeader.add(Box.createVerticalStrut(8));
        PanelHeader.add(LblEstado);
        PanelHeader.add(Box.createVerticalStrut(10));
        PanelHeader.add(PanelSubEstado);
        
        PanelFondo.add(PanelHeader, BorderLayout.NORTH);
        
        //Panel central
        JPanel PanelCentral = new JPanel();
        PanelCentral.setLayout(new GridLayout(1, 2, 18, 0));
        PanelCentral.setOpaque(false);
        PanelCentral.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        
        PanelMiTablero = CrearPanelTablero(true);
        BtnMiTablero = CrearGrid(PanelMiTablero, true);
        
        PanelTableroRival = CrearPanelTablero(false);
        BtnTableroRival = CrearGrid(PanelTableroRival, false);
        
        PanelCentral.add(PanelMiTablero);
        PanelCentral.add(PanelTableroRival);
        
        PanelFondo.add(PanelCentral, BorderLayout.CENTER);
        
        //Barra inferior
        JPanel BarraInferior = new JPanel();
        BarraInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 8));
        BarraInferior.setBackground(new Color(0, 0, 0, 170));
        
        JLabel LblBarco = new JLabel("BARCO:");
        LblBarco.setForeground(Color.WHITE);
        LblBarco.setFont(new Font("DIN Condensed", Font.BOLD, 18));

        CBBarco = new JComboBox<>(new String[]{"PA", "AZ", "SM", "DT"});
        EstilizarBoton(CBBarco);
        CBBarco.setPreferredSize(new Dimension(90, 45));
        
        BtnRotar = new JButton("ROTAR (H)");
        EstilizarBoton(BtnRotar);
        BtnRotar.addActionListener(e -> onRotar());
        
        BtnConfirmarColocacion = new JButton("TERMINAR FASE");
        EstilizarBoton(BtnConfirmarColocacion);
        BtnConfirmarColocacion.setPreferredSize(new Dimension(180, 40));
        BtnConfirmarColocacion.addActionListener(e -> onConfirmarColocacion());
        
        BtnRetirar = new JButton("RETIRAR");
        EstilizarBoton(BtnRetirar);
        BtnRetirar.addActionListener(e -> onRetirar());
        
        BarraInferior.add(LblBarco);
        BarraInferior.add(CBBarco);
        BarraInferior.add(BtnRotar);
        BarraInferior.add(BtnConfirmarColocacion);
        BarraInferior.add(BtnRetirar);
        
        PanelFondo.add(BarraInferior, BorderLayout.SOUTH);
        
        //Ajuste inicial de botones segun fase
        RefrescarControlesPorFase();
        RenderizarTodo();
        
        InicializacionExitosa = true;
    }
    
    private JPanel CrearPanelTablero(boolean esmitablero) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(20, 20, 35));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(esmitablero ? UIColors.BORDE_P1.getColor() : UIColors.BORDE_P2.getColor(), 2), BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        
        JLabel titulo = new JLabel("", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("DIN Condensed", Font.BOLD, 18));
        
        if (esmitablero) {
            LblMiTableroTitulo = titulo;
        } else {
            LblTableroRivalTitulo = titulo;
        }
        
        panel.add(titulo, BorderLayout.NORTH);
        
        return panel;
    }
    
    private JButton[][] CrearGrid(JPanel panel, boolean esmitablero) {
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(8, 8, 2, 2));
        grid.setOpaque(false);
        
        JButton[][] botones = new JButton[8][8];
        
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                JButton boton = new JButton();
                boton.setFont(new Font("DIN Condensed", Font.BOLD, 20));
                boton.setForeground(Color.WHITE);
                boton.setMargin(new Insets(0, 0, 0, 0));
                boton.setPreferredSize(new Dimension(60, 60));
                boton.setBackground(esmitablero ? UIColors.AGUA_P1.getColor() : UIColors.AGUA_P2.getColor());
                boton.setOpaque(true);
                boton.setContentAreaFilled(true);
                boton.setBorderPainted(true);
                boton.setFocusPainted(false);
                boton.putClientProperty("fila", fila);
                boton.putClientProperty("col", col);
                
                if (esmitablero) {
                    boton.addActionListener(e -> onClickMiTablero(boton));
                } else {
                    boton.addActionListener(e -> onClickRival(boton));
                }
                
                boton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        onHoverCelda(boton, esmitablero);
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        LimpiarPreview();
                    }
                });
                
                botones[fila][col] = boton;
                grid.add(boton);
            }
        }
        
        panel.add(grid, BorderLayout.CENTER);
        
        return botones;
    }
    
    private void ActualizarLabels() {
        LblMiTableroTitulo.setText("TABLERO DE: " + Juego.getJugador1());
        LblTableroRivalTitulo.setText("TABLERO DE RIVAL: " + Juego.getJugador2());
        
        LblEstado.setText("Dificultad: " + dificultad + " (" + dificultad.getBarcos() + " barcos)  |  Modo: " + Modo);
        
        if (FaseColocacion) {
            LblColocando.setText("Fase de Colocacion: Jugador " + JugadorColocando + " (" + (JugadorColocando == 1 ? Juego.getJugador1() : Juego.getJugador2()) + ")");
            LblSeleccion.setText("Seleccionado: " + CBBarco.getSelectedItem() + " | Orientacion: " + orientacion);
        } else {
            LblColocando.setText("Turno de: " + Juego.getJugadorTurno());
            LblSeleccion.setText("Dispara en el tablero del rival!");
        }
    }
    
    private void RenderizarTodo() {
        ActualizarLabels();
        
        if (FaseColocacion) {
            if (JugadorColocando == 1) {
                RenderizarTablero(Juego.getTablero(1), BtnMiTablero, true, false, true);
                LimpiarGrid(BtnTableroRival);
                
                setGridEnabled(BtnMiTablero, true);
                setGridEnabled(BtnTableroRival, false);
            } else {
                LimpiarGrid(BtnMiTablero);
                RenderizarTablero(Juego.getTablero(2), BtnTableroRival, true, true, true);
                
                setGridEnabled(BtnMiTablero, false);
                setGridEnabled(BtnTableroRival, true);
            }
            
            return;
        }
        
        boolean mostrarp1 = true;
        boolean mostrarp2 = true;
        boolean turnop1 = Juego.getTurno() == 1;
        
        if (Modo == ModoJuego.ARCADE) {
            if (Juego.getTurno() == 1) {
                mostrarp2 = false;
            } else {
                mostrarp1 = false;
            }
        }
        
        RenderizarTablero(Juego.getTablero(1), BtnMiTablero, mostrarp1, false, !turnop1);
        RenderizarTablero(Juego.getTablero(2), BtnTableroRival, mostrarp2, true, turnop1);
        
        HabilitarClickDisparo();
    }
    
    private void onHoverCelda(JButton boton, boolean esmitablero) {
        if (!FaseColocacion)
            return;
        
        if ((JugadorColocando == 1 && !esmitablero) || (JugadorColocando == 2 && esmitablero))
            return;
        
        LimpiarPreview();
        
        int fila = (int) boton.getClientProperty("fila");
        int col = (int) boton.getClientProperty("col");
        
        int longitudbarco = getLenBarco((String) CBBarco.getSelectedItem());
        boolean ok = true;
        
        JButton[][] grid = (JugadorColocando == 1) ? BtnMiTablero : BtnTableroRival;
        String[][] modelo = Juego.getTablero(JugadorColocando);
        
        //Verificar si el barco cabe
        for (int i = 0; i < longitudbarco; i++) {
            int f = fila + (orientacion == Orientacion.VERTICAL ? i : 0);
            int c = col + (orientacion == Orientacion.HORIZONTAL ? i : 0);
            
            if (f < 0 || f >= 8 || c < 0 || c >= 8) {
                ok = false;
                break;
            }
            if (!modelo[f][c].equals(Battleship.AGUA)) {
                ok = false;
                break;
            }
        }
        
        CuentaPreview = 0;
        
        //Pintar el preview
        for (int i = 0; i < longitudbarco; i++) {
            int f = fila + (orientacion == Orientacion.VERTICAL ? i : 0);
            int c = col + (orientacion == Orientacion.HORIZONTAL ? i : 0);
            
            if (f < 0 || f >= 8 || c < 0 || c >= 8)
                continue;
            
            if (!modelo[f][c].equals(Battleship.AGUA))
                continue;
            
            grid[f][c].setBackground(ok ? UIColors.HOVER_OK.getColor() : UIColors.HOVER_BAD.getColor());
            
            if (CuentaPreview < PreviewCeldas.length) {
                PreviewCeldas[CuentaPreview++] = new Point(f, c);
            }
        }
    }
    
    private void LimpiarPreview() {
        if (CuentaPreview == 0)
            return;
        
        int jugador = JugadorColocando;
        JButton[][] grid = (JugadorColocando == 1) ? BtnMiTablero : BtnTableroRival;
        String[][] modelo = Juego.getTablero(jugador);

        for (int i = 0; i < CuentaPreview; i++) {
            Point punto = PreviewCeldas[i];
            
            if (punto == null)
                continue;
            
            String var = modelo[punto.x][punto.y];
            
            //Restaurar segun lo que realmente hay en el tablero
            if (var.equals(Battleship.AGUA)) {
                grid[punto.x][punto.y].setText("");
                grid[punto.x][punto.y].setBackground(jugador == 1 ? UIColors.AGUA_P1.getColor() : UIColors.AGUA_P2.getColor());
            } else if (esCodigoBarco(var)) {
                grid[punto.x][punto.y].setText(var);
                grid[punto.x][punto.y].setForeground(Color.WHITE);
                grid[punto.x][punto.y].setBackground(UIColors.BARCO.getColor());
            } else if (var.equalsIgnoreCase("X")) {
                grid[punto.x][punto.y].setText("X");
                grid[punto.x][punto.y].setForeground(Color.WHITE);
                grid[punto.x][punto.y].setBackground(UIColors.HIT.getColor());
            } else if (var.equalsIgnoreCase("F")) {
                grid[punto.x][punto.y].setText("F");
                grid[punto.x][punto.y].setForeground(Color.WHITE);
                grid[punto.x][punto.y].setBackground(UIColors.MISS.getColor());
            } else {
                grid[punto.x][punto.y].setText(var);
            }
            
            grid[punto.x][punto.y].repaint();
            PreviewCeldas[i] = null;
        }
        
        CuentaPreview = 0;
    }
    
    private int getLenBarco(String codigo) {
        switch(codigo) {
            case "PA":
                return 5;
            case "AZ":
                return 4;
            case "SM":
                return 3;
            case "DT":
                return 2;
        }
        
        return 2;
    }
    
    private void RefrescarControlesPorFase() {
        CBBarco.setEnabled(FaseColocacion);
        BtnRotar.setEnabled(FaseColocacion);
        BtnConfirmarColocacion.setEnabled(FaseColocacion);
        BtnRetirar.setEnabled(!FaseColocacion);
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
        Cursor cursor = habilitado ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
        
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                grid[fila][col].setCursor(cursor);
                grid[fila][col].setEnabled(habilitado);
            }
        }
    }
    
    private void onClickMiTablero(JButton boton) {
        int fila = (int) boton.getClientProperty("fila");
        int col = (int) boton.getClientProperty("col");
        
        if (FaseColocacion) {
            if (JugadorColocando != 1)
                return;
            
            String codigo = (String) CBBarco.getSelectedItem();
            Resultado resultado = Juego.ColocarBarco(1, codigo, fila, col, orientacion);

            if (resultado != Resultado.OK) {
                MostrarMensaje(ColocarMensaje(resultado), "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            RenderizarTodo();
            return;
        }
        
        if (Juego.getTurno() != 2)
            return;
        
        ProcesarDisparo(fila, col);
    }
    
    private void onClickRival(JButton boton) {
        int fila = (int) boton.getClientProperty("fila");
        int col = (int) boton.getClientProperty("col");
        
        
        if (FaseColocacion) {
            if (JugadorColocando != 2)
                return;
            
            String codigo = (String) CBBarco.getSelectedItem();
            Resultado resultado = Juego.ColocarBarco(2, codigo, fila, col, orientacion);

            if (resultado != Resultado.OK) {
                MostrarMensaje(ColocarMensaje(resultado), "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            RenderizarTodo();
            return;
        }
        
        if (Juego.getTurno() != 1)
            return;
        
        ProcesarDisparo(fila, col);
    }
    
    private void ProcesarDisparo(int fila, int col) {
        Resultado resultado = Juego.Disparar(fila, col, false);
        
        if (resultado == Resultado.INVALIDO) {
            MostrarMensaje("Tiro invalido o repetido", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (resultado == Resultado.FUERA_RANGO) {
            MostrarMensaje("Fuera del tablero", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (resultado == Resultado.MISS) {
            MostrarMensaje("Fallaste!\nEl agua salpica...", "Fallo", JOptionPane.INFORMATION_MESSAGE);
        } else if (resultado == Resultado.HIT) {
            String barcohundido = Juego.getUltimoBarcoHundido();
            int jugadorafectado = Juego.getUltimoJugadorAfectado();
            String nombrerival = (jugadorafectado == 1) ? Juego.getJugador1() : Juego.getJugador2();
            
            if (barcohundido != null && !barcohundido.isEmpty()) {
                String nombrebarco = getNombreBarco(barcohundido);
                MostrarMensaje("BARCO HUNDIDO!!\n\n" + "Has destruido el " + nombrebarco + "\n" + "del jugador " + nombrerival + "!\n\n" + "El tablero del rival se regenera...", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                MostrarMensaje("IMPACTO!!\nLe diste a un barco enemigo!\n\nEl tablero del rival se regenera...", "Golpe", JOptionPane.WARNING_MESSAGE);
            }
            
        } else if (resultado == Resultado.GANO) {
            MostrarMensaje("VICTORIA!!\n" + "El jugador " + Juego.getJugadorTurno() + " ha ganado la partida!\n\n(+3 puntos)", "Fin de Partida", JOptionPane.INFORMATION_MESSAGE);
            Volver();
            return;
        } else if (resultado == Resultado.RETIRO) {
            MostrarMensaje("Retiro del jugador " + Juego.getJugadorTurno() + " confirmado", "Fin de Partida", JOptionPane.INFORMATION_MESSAGE);
            Volver();
            return;
        }
        
        RenderizarTodo();
    }
    
    private void onRetirar() {
        if (FaseColocacion) {
            MostrarMensaje("Solo te puedes retirar durante la partida", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int opcion = MostrarConfirmacion("Seguro que quieres retirarte?\nTu rival ganara", "Retiro");
        
        if (opcion != JOptionPane.YES_OPTION)
            return;
        
        Resultado resultado = Juego.Disparar(-1, -1, true);
        
        if (resultado == Resultado.RETIRO) {
            MostrarMensaje("Retiro del jugador: " + Juego.getJugadorTurno() + "\nHa sido Confirmado" + "\nHa ganado el jugador " + Juego.getOtroJugador() + "!" + "\n\n(+3 puntos)", "Fin Partida", JOptionPane.INFORMATION_MESSAGE);
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
        RenderizarTodo();
    }
    
    private void onConfirmarColocacion() {
        if (!FaseColocacion)
            return;
        
        int max = dificultad.getBarcos();
        int colocados = Juego.getColocados(JugadorColocando);
        
        if (colocados < max) {
            MostrarMensaje("Te faltan barcos: " + colocados + "/" + max, "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (JugadorColocando == 1) {
            JugadorColocando = 2;
            LimpiarVistaTableros();
            RenderizarTodo();
            return;
        }
        
        //Inicia el disparo
        FaseColocacion = false;
        RefrescarControlesPorFase();
        RenderizarTodo();
    }
    
    private void LimpiarVistaTableros() {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                BtnMiTablero[fila][col].setText("");
                BtnTableroRival[fila][col].setText("");
                BtnMiTablero[fila][col].setBackground(UIColors.AGUA_P1.getColor());
                BtnTableroRival[fila][col].setBackground(UIColors.AGUA_P2.getColor());
            }
        }
    }
    
    private void LimpiarGrid(JButton[][] vista) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                vista[fila][col].setText("");
                vista[fila][col].setBackground(vista == BtnMiTablero ? UIColors.AGUA_P1.getColor() : UIColors.AGUA_P2.getColor());
            }
        }
    }
    
    private void RenderizarTablero(String[][] modelo, JButton[][] vista, boolean mostrarbarcos, boolean establerorival, boolean activo) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                String var = modelo[fila][col];
                
                if (var == null) {
                    var = Battleship.AGUA;
                }
                
                if (!mostrarbarcos && esCodigoBarco(var)) {
                    var = Battleship.AGUA;
                }
                
                JButton boton = vista[fila][col];
                boton.setOpaque(true);
                boton.setContentAreaFilled(true);
                boton.setBorderPainted(true);
                boton.setFocusPainted(false);
                
                Color aguacolor;
                
                if (establerorival) {
                    aguacolor = activo ? UIColors.AGUA_P2.getColor() : UIColors.AGUA_P2_INACTIVO.getColor();
                } else {
                    aguacolor = activo ? UIColors.AGUA_P1.getColor() : UIColors.AGUA_P1_INACTIVO.getColor();

                }
                
                if (var.equals(Battleship.AGUA)) {
                    boton.setText("");
                    boton.setBackground(aguacolor);
                    continue;
                }
                
                if (esCodigoBarco(var)) {
                    boton.setText(var);
                    boton.setForeground(Color.WHITE);
                    boton.setBackground(activo ? UIColors.BARCO.getColor() : UIColors.BARCO_INACTIVO.getColor());
                    continue;
                }
                
                if (var.equalsIgnoreCase("X")) {
                    boton.setText("X");
                    boton.setForeground(Color.WHITE);
                    boton.setBackground(new Color(255, 80, 80, 170));
                    continue;
                }
                
                if (var.equalsIgnoreCase("F")) {
                    boton.setText("F");
                    boton.setForeground(Color.WHITE);
                    boton.setBackground(new Color(200, 200, 200, 140));
                    continue;
                }
                
                boton.setText(var);
                boton.setForeground(Color.WHITE);
                boton.setBackground(new Color(80, 80, 120, 140));
            }
        }
        
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                vista[fila][col].repaint();
            }
        }
    }
    
    private String PedirJugador2() {
        while (true) {
            String jugador2 = MostrarInput("Ingresa el username del JUGADOR 2 (o escribe EXIT para cancelar): ", "Player 2");
            
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
            if (jugador2.equals(UsuarioActivo)) {
                MostrarMensaje("El jugador 2 no puede ser el mismo que el jugador 1", "Aviso", JOptionPane.WARNING_MESSAGE);
                continue;
            }
            
            int indice = Memoria.getIndiceUsuarioExacto(jugador2);
            
            if (indice == -1) {
                MostrarMensaje("Ese usuario no existe, intentalo otra vez", "Aviso", JOptionPane.WARNING_MESSAGE);
                continue;
            }
            if (!Memoria.isActivo(indice)) {
                MostrarMensaje("Ese usuario esta inactivo", "Aviso", JOptionPane.WARNING_MESSAGE);
                continue;
            }
            
            return Memoria.getUsuario(indice);
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
    
    private boolean esCodigoBarco(String label) {
        if (label == null) {
            return false;
        }
        
        return label.equals(TipoBarco.PA.codigo) || label.equals(TipoBarco.AZ.codigo) || label.equals(TipoBarco.SM.codigo) || label.equals(TipoBarco.DT.codigo);
    }
    
    private String getNombreBarco(String codigo) {
        switch(codigo) {
            case "PA":
                return "PORTAAVIONES";
            case "AZ":
                return "ACORAZADO";
            case "SM":
                return "SUBMARINO";
            case "DT":
                return "DESTRUCTOR";
            default:
                return "BARCO";
        }
    }
    
    public boolean isInicializacionExitosa() {
        return InicializacionExitosa;
    }
    
    private void MostrarMensaje(String mensaje, String titulo, int tipo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(20, 20, 35));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 3), BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        JLabel lblmensaje = new JLabel("<html><div style='text-align: center; width: 250px;'>" + mensaje.replace("\n", "<br>") + "</div></html>");
        lblmensaje.setForeground(Color.WHITE);
        lblmensaje.setFont(new Font("DIN Condensed", Font.BOLD, 16));
        lblmensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lblmensaje);
        
        UIManager.put("OptionPane.background", new Color(20, 20, 35));
        UIManager.put("Panel.background", new Color(20, 20, 35));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(25, 25, 25));
        UIManager.put("Button.foreground", new Color(220, 180, 120));
        UIManager.put("Button.border", BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 1), BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        
        JOptionPane.showMessageDialog(this, panel, titulo, tipo);
        
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Button.border", null);
    }
    
    private int MostrarConfirmacion(String mensaje, String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(20, 20, 35));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 3), BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        JLabel lblmensaje = new JLabel("<html><div style='text-align: center; width: 250px;'>" + mensaje.replace("\n", "<br>") + "</div></html>");
        lblmensaje.setForeground(Color.WHITE);
        lblmensaje.setFont(new Font("DIN Condensed", Font.BOLD, 16));
        lblmensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lblmensaje);
        
        UIManager.put("OptionPane.background", new Color(20, 20, 35));
        UIManager.put("Panel.background", new Color(20, 20, 35));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(25, 25, 25));
        UIManager.put("Button.foreground", new Color(220, 180, 120));
        UIManager.put("Button.border", BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 1), BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        
        int resultado = JOptionPane.showConfirmDialog(this, panel, titulo, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Button.border", null);
        
        return resultado;
    }
    
//    private String MostrarInput(String mensaje, String titulo, String valorinicial) {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setBackground(new Color(20, 20, 35));
//        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 3), BorderFactory.createEmptyBorder(20, 20, 20, 20)));
//
//        JLabel lblmensaje = new JLabel("<html><div style='text-align: center; width: 280px;'>" + mensaje.replace("\n", "<br>") + "</div></html>");
//        lblmensaje.setForeground(Color.WHITE);
//        lblmensaje.setFont(new Font("DIN Condensed", Font.BOLD, 16));
//        lblmensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        JTextField txtinput = new JTextField(valorinicial != null ? valorinicial : "");
//        txtinput.setMaximumSize(new Dimension(280, 35));
//        txtinput.setPreferredSize(new Dimension(280, 35));
//        txtinput.setFont(new Font("DIN Condensed", Font.BOLD, 16));
//        txtinput.setBackground(new Color(40, 40, 60));
//        txtinput.setForeground(Color.WHITE);
//        txtinput.setCaretColor(Color.WHITE);
//        txtinput.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(80, 120, 160), 2), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
//        txtinput.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        panel.add(lblmensaje);
//        panel.add(Box.createVerticalStrut(15));
//        panel.add(txtinput);
//        
//        JButton BtnOk = new JButton("OK");
//        JButton BtnCancelar = new JButton("CANCELAR");
//        EstilizarBoton(BtnOk);
//        EstilizarBoton(BtnCancelar);
//        
//        final String[] resultado = {null};
//        
//        BtnOk.addActionListener(e -> {
//            resultado[0] = txtinput.getText();
//        });
//        BtnCancelar.addActionListener(e -> {
//            resultado[0] = null;
//        });
//        
//        JOptionPane opcion = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, new Object[]{BtnOk, BtnCancelar}, BtnOk);
//        opcion.setBackground(new Color(20, 20, 35));
//        
//        JDialog dialogo = opcion.createDialog(this, titulo);
//        dialogo.setModal(true);
//        
//        dialogo.getContentPane().setBackground(new Color(20, 20, 35));
//        
//        txtinput.addActionListener(e -> {
//            BtnOk.doClick();
//            dialogo.dispose();
//        });
//        
//        BtnOk.addActionListener(e -> {
//            resultado[0] = txtinput.getText();
//            dialogo.dispose();
//        });
//        BtnCancelar.addActionListener(e -> {
//            resultado[0] = null;
//            dialogo.dispose();
//        });
//        
//        dialogo.getRootPane().setDefaultButton(BtnOk);
//        dialogo.getRootPane().registerKeyboardAction(e -> {
//            resultado[0] = null;
//            dialogo.dispose();
//        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
//        
//        dialogo.pack();
//        dialogo.setResizable(false);
//        dialogo.setLocationRelativeTo(this);
//        
//        SwingUtilities.invokeLater(() -> txtinput.requestFocusInWindow());
//        dialogo.setVisible(true);
//        
//        return resultado[0];
//    }

    private String MostrarInput(String mensaje, String titulo, String valorinicial) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(20, 20, 35));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 3), BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel lblmensaje = new JLabel("<html><div style='text-align: center; width: 280px;'>" + mensaje.replace("\n", "<br>") + "</div></html>");
        lblmensaje.setForeground(Color.WHITE);
        lblmensaje.setFont(new Font("DIN Condensed", Font.BOLD, 16));
        lblmensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lblmensaje);
        panel.add(Box.createVerticalStrut(15));

        JTextField txtinput = new JTextField(valorinicial != null ? valorinicial : "");
        txtinput.setMaximumSize(new Dimension(280, 35));
        txtinput.setPreferredSize(new Dimension(280, 35));
        txtinput.setFont(new Font("DIN Condensed", Font.BOLD, 16));
        txtinput.setBackground(new Color(40, 40, 60));
        txtinput.setForeground(Color.WHITE);
        txtinput.setCaretColor(Color.WHITE);
        txtinput.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(80, 120, 160), 2), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        txtinput.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(txtinput);

        UIManager.put("OptionPane.background", new Color(20, 20, 35));
        UIManager.put("Panel.background", new Color(20, 20, 35));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(25, 25, 25));
        UIManager.put("Button.foreground", new Color(220, 180, 120));
        UIManager.put("Button.font", new Font("DIN Condensed", Font.BOLD, 14));

        int resultado = JOptionPane.showConfirmDialog(this, panel, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Button.font", null);

        if (resultado == JOptionPane.OK_OPTION) {
            return txtinput.getText();
        }

        return null;
    }
    
    private String MostrarInput(String mensaje, String titulo) {
        return MostrarInput(mensaje, titulo, "");
    }
    
    private void EstilizarBoton(JComponent boton) {
        boton.setFont(new Font("DIN Condensed", Font.BOLD, 15));
        boton.setBackground(new Color(25, 25, 25)); //Gris oscuro tipo metal
        boton.setForeground(new Color(220, 180, 120)); //Dorado suave
//        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 2), BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        boton.setOpaque(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(140, 40));
        
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(0, 0, 60));
                boton.setForeground(new Color(255, 220, 130));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(25, 25, 25));
                boton.setForeground(new Color(220, 180, 80));
            }
        });
    }
}