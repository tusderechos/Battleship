package UI;

 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Hp
 */

import ManejoCuentas.MemoriaCuentas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class Reportes extends JFrame {
    
    private JTable TblRanking;
    private JTable TblLogs;
    private JScrollPane ScrollRanking;
    private JScrollPane ScrollLogs;
    
    private JButton BtnRefrescar;
    private JButton BtnSalir;
        
    private MenuPrincipal menuPrincipal;
    private MemoriaCuentas Memoria;
    private String UsuarioActivo;
    
    
    public Reportes(MemoriaCuentas Memoria, String UsuarioActivo, MenuPrincipal menuPrincipal) {
        this.Memoria = Memoria;
        this.menuPrincipal = menuPrincipal;
        this.UsuarioActivo = UsuarioActivo;
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_reportes.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        setTitle("Vampire Wargame - Reportes");
        this.setContentPane(PanelFondo);
        setSize(800, 700);
        setResizable(false);
        setLocationRelativeTo(menuPrincipal);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        //Panel para las dos tablas
        JPanel PanelTablas = new JPanel();
        PanelTablas.setLayout(new GridLayout(2, 1, 10, 10));
        PanelTablas.setOpaque(false);
        
        DefaultTableModel ModeloRanking = new DefaultTableModel(new String[]{"Posicion", "Usuario", "Puntos"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int col) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int indicecolumna) {
                if (indicecolumna == 0 || indicecolumna == 2) {
                    return Integer.class;
                }
                
                return String.class;
            }
        };
        
        DefaultTableModel ModeloLogs = new DefaultTableModel(new String[] {"Fecha", "Rival", "Resultado"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int col) {
                return false;
            }
        };
        
        Color fondosemi = new Color(10, 10, 10);
        Color grid = new Color(80, 80, 80);
        Color headerbg = new Color(30, 20, 20);
        Color dorado = new Color(230, 220, 150);
        
        TblRanking = new JTable(ModeloRanking);
        TblLogs = new JTable(ModeloLogs);
        
        for (JTable tabla : new JTable[]{TblRanking, TblLogs}) {
            tabla.setRowHeight(26);
            tabla.setShowHorizontalLines(true);
            tabla.setShowVerticalLines(false);
            tabla.setGridColor(grid);
            tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tabla.setFillsViewportHeight(true);
            tabla.setSelectionForeground(Color.WHITE);
            tabla.setSelectionBackground(new Color(0, 0, 0, 0));
            tabla.setOpaque(false);
        }
        
        //Header compartdo
        DefaultTableCellRenderer RendererHeader = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
                lbl.setForeground(dorado);
                lbl.setBackground(headerbg);
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200, 120)));
                lbl.setOpaque(true);
                
                return lbl;
            }
        };
                
        JTableHeader hdrR = TblRanking.getTableHeader();
        hdrR.setReorderingAllowed(false);
        hdrR.setResizingAllowed(false);
        hdrR.setDefaultRenderer(RendererHeader);
        
        JTableHeader hdrL = TblLogs.getTableHeader();
        hdrL.setReorderingAllowed(false);
        hdrL.setResizingAllowed(false);
        hdrL.setDefaultRenderer(RendererHeader);
        
        //Renderer base
        DefaultTableCellRenderer RendererBase = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                lbl.setForeground(Color.WHITE);
                lbl.setFont(lbl.getFont().deriveFont(isSelected ? Font.BOLD : Font.PLAIN));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setBackground(isSelected ? new Color(41, 41, 81) : new Color(20, 20, 20));
                lbl.setOpaque(true);
                
                return lbl;
            }
        };
        
        //renderer dorado para Usuario y para toda la tabla de logs
        DefaultTableCellRenderer RendererDorado = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                lbl.setForeground(dorado);
                lbl.setFont(lbl.getFont().deriveFont(isSelected ? Font.BOLD : Font.PLAIN));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setBackground(isSelected ? new Color(41, 41, 81) : new Color(20, 20, 20));
                lbl.setOpaque(true);
                
                return lbl;
            }
        };
        
        
        //Alineacion de columnas
        TableColumnModel tcmR = TblRanking.getColumnModel();
        for (int i = 0; i < tcmR.getColumnCount(); i++) {
            tcmR.getColumn(i).setCellRenderer(RendererBase);
        }
        
        //Colocar usuario en dorado
        tcmR.getColumn(1).setCellRenderer(RendererDorado);
        
        TableColumnModel tcmL = TblLogs.getColumnModel();
        for (int i = 0; i < tcmL.getColumnCount(); i++) {
            tcmL.getColumn(i).setCellRenderer(RendererDorado);
        }
        
        TblRanking.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tcmR.getColumn(0).setPreferredWidth(90);
        tcmR.getColumn(1).setPreferredWidth(360);
        tcmR.getColumn(2).setPreferredWidth(120);
        
        TblLogs.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tcmL.getColumn(0).setPreferredWidth(180);
        tcmL.getColumn(1).setPreferredWidth(180);
        tcmL.getColumn(2).setPreferredWidth(320);
        
        ScrollRanking = new JScrollPane(TblRanking);
        ScrollRanking.setOpaque(false);
        ScrollRanking.getViewport().setOpaque(false);
        
        JPanel CajaRanking = new JPanel(new BorderLayout());
        CajaRanking.setBackground(fondosemi);
        CajaRanking.setOpaque(true);
        CajaRanking.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        CajaRanking.add(ScrollRanking, BorderLayout.CENTER);
        
        AjustarAnchosProporcional(TblRanking, 15, 55, 30);
        HookResizeProporcional(ScrollRanking, () -> AjustarAnchosProporcional(TblRanking, 15, 55, 30));
        
        ScrollLogs = new JScrollPane(TblLogs);
        ScrollLogs.setOpaque(false);
        ScrollLogs.getViewport().setOpaque(false);
        
        JPanel CajaLogs = new JPanel(new BorderLayout());
        CajaLogs.setBackground(fondosemi);
        CajaLogs.setOpaque(true);
        CajaLogs.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        CajaLogs.add(ScrollLogs, BorderLayout.CENTER);
        
        PanelTablas.add(CajaRanking);
        PanelTablas.add(CajaLogs);
        
        AjustarAnchosProporcional(TblLogs, 20, 30, 50);
        HookResizeProporcional(ScrollLogs, () -> AjustarAnchosProporcional(TblLogs, 20, 30, 50));
        
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.X_AXIS));
        PanelBotones.setOpaque(false);
        
        BtnRefrescar = new JButton("REFRESCAR");
        EstilizarBoton(BtnRefrescar);
        BtnRefrescar.addActionListener(e -> {
            MostrarRanking();
            MostrarLogs();
        });
        
        BtnSalir = new JButton("SALIR");
        EstilizarBoton(BtnSalir);
        BtnSalir.addActionListener(e -> onSalir());
        
        PanelBotones.add(Box.createHorizontalGlue());
        PanelBotones.add(BtnRefrescar);
        PanelBotones.add(Box.createHorizontalStrut(40));
        PanelBotones.add(BtnSalir);
        PanelBotones.add(Box.createHorizontalGlue());
        
        
        PanelFondo.setLayout(new BorderLayout(10, 10));
        add(PanelTablas, BorderLayout.CENTER);
        add(PanelBotones, BorderLayout.SOUTH);
        
        MostrarRanking();
        
        repaint();
    }
    
    private void MostrarRanking() {
        DefaultTableModel modelo = (DefaultTableModel) TblRanking.getModel();
        modelo.setRowCount(0);
        
        int total = Memoria.getRegistrados();
        
        int vartemp = 0;
        
        for (int i = 0; i < total; i++) {
            if (Memoria.isActivo(i)) {
                String usuario = Memoria.getUsuario(i);
                
                if (usuario != null && !usuario.isBlank()) {
                    vartemp++;
                }
            }
        }
        
        if (vartemp == 0) {
            JOptionPane.showMessageDialog(this, "No hay jugadores activos", "Ranking Vacio", JOptionPane.INFORMATION_MESSAGE);
            AjustarAnchosProporcional(TblRanking, 15, 55, 30);
            return;
        }
        
        String[] usuarios = new String[vartemp];
        int[] puntos = new int[vartemp];
        int vartemp2 = 0;
        
        for (int i = 0; i < total; i++) {
            if (Memoria.isActivo(i)) {
                String usu = Memoria.getUsuario(i);
                
                if (usu != null && !usu.isBlank()) {
                    usuarios[vartemp2] = usu;
                    puntos[vartemp2] = Memoria.getPuntos(i);
                    vartemp2++;
                }
            }
        }
        
        for (int i = 0; i < vartemp - 1; i++) {
            for (int j = 0; j < vartemp - 1 - i; j++) {
                if (puntos[j] < puntos[j + 1]) {
                    //Intercambiar puntos
                    int vartemp3 = puntos[j];
                    puntos[j] = puntos[j + 1];
                    puntos[j + 1] = vartemp3;
                    
                    //Intercambiar usuarios
                    String usuariotemp = usuarios[j];
                    usuarios[j] = usuarios[j + 1];
                    usuarios[j + 1] = usuariotemp;
                }
            }
        }
        
        for (int i = 0; i < vartemp; i++) {
            modelo.addRow(new Object[]{i + 1, usuarios[i], puntos[i]});
        }
        
        AjustarAnchosProporcional(TblRanking, 15, 55, 30);
        SeleccionarFilaUsuarioActivo();
    }
    
    private void MostrarLogs() {
        DefaultTableModel modelo = (DefaultTableModel) TblLogs.getModel();
        modelo.setRowCount(0);
        
        String[] logs = Memoria.ObtenerLogsUsuario(UsuarioActivo);
        
        if (logs == null || logs.length == 0) {
            JOptionPane.showMessageDialog(this, "No hay partidas registradas para este jugador", "Registro vacio", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        for (int i = 0; i < logs.length; i++) {
            if (logs[i] != null && !logs[i].isBlank()) {
                String[] partes = logs[i].split("\\|"); //Para separar los campos tipo "rival | fecha | resultado"
                
                if (partes.length >= 3) {
                    String fecha = partes[0].trim();
                    String rival = partes[1].replace("Rival:", "").trim();
                    String resultado = partes[2].trim();
                    
                    modelo.addRow(new Object[]{fecha, rival, resultado});
                }
            }
        }
        
        AjustarAnchosProporcional(TblLogs, 20, 30, 50);
    }
    
    private void onSalir() {        
        this.setVisible(false);
        menuPrincipal.setVisible(true);
    }
    
    private void AjustarAnchosProporcional(JTable tabla, int... porcentajes) {
        if (tabla.getColumnCount() != porcentajes.length) {
            return;
        }
        
        int total = 0;
        
        for (int p : porcentajes) {
            total += p;
        }
        
        if (total == 0) {
            return;
        }
        
        int anchotabla = tabla.getParent() != null ? tabla.getParent().getWidth() : tabla.getWidth();
        
        if (anchotabla <= 0) {
            anchotabla = 600;
        }
        
        TableColumnModel tcm = tabla.getColumnModel();
        
        for (int i = 0; i < porcentajes.length; i++) {
            int w = (anchotabla * porcentajes[i]) / total;
            tcm.getColumn(i).setPreferredWidth(Math.max(60, w));
        }
    }
    
    private void HookResizeProporcional(JScrollPane scroll, Runnable ajustar) {
        scroll.getViewport().addComponentListener(new ComponentAdapter() {
            @Override 
            public void componentResized(ComponentEvent e) { 
                ajustar.run(); 
            }
        });
    }
    
    private void SeleccionarFilaUsuarioActivo() {
        if (UsuarioActivo == null) {
            return;
        }
        
        for (int i = 0; i < TblRanking.getRowCount(); i++) {
            Object v = TblRanking.getValueAt(i, 1);
            
            if (v != null && UsuarioActivo.equals(v.toString())) {
                TblRanking.setRowSelectionInterval(i, i);
                TblRanking.scrollRectToVisible(TblRanking.getCellRect(i, 0, true));
                break;
            }
        }
    }
    
    private void EstilizarBoton(JButton boton) {
        boton.setFont(new Font("Bookman Old Style", Font.BOLD, 18));
        boton.setBackground(new Color(25, 25, 25)); //Gris oscuro tipo metal
        boton.setForeground(new Color(220, 180, 120)); //Dorado suave
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 2), BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        boton.setOpaque(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(220, 44));
        
        //Mi querido, hermoso y celestial efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(60, 0, 0));
                boton.setForeground(new Color(255, 220, 130));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(25, 25, 25));
                boton.setForeground(new Color(220, 180, 80));
            }
        });
    }
    
    private void EstilizarTitulo(JLabel titulo) {
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        Font base = new Font("Old English Text MT", Font.BOLD, 38);
        titulo.setFont(base);
        
        titulo.setForeground(new Color(230, 200, 120));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(0, 0, 0, 170)); //Franja oscura
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}

