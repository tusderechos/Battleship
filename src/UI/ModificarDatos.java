/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author USUARIO
 */

import ManejoCuentas.MemoriaCuentas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.text.JTextComponent;

public class ModificarDatos extends JDialog {
    
    private final MemoriaCuentas Memoria;
    private final MenuPrincipal menuPrincipal;
    private final int Indice;
    
    private final String UsuarioActivo;
    
    private JTextField TxtNuevoUsuario;
    private JPasswordField TxtPassActual;
    private JPasswordField TxtPassNueva;
    private JPasswordField TxtPassConfirm;
    
    private JLabel LblMensaje;
    
    public ModificarDatos(JFrame padre, MemoriaCuentas Memoria, String UsuarioActivo, MenuPrincipal menuPrincipal) {
        super(padre, "Modificar Datos", true);
        this.Memoria = Memoria;
        this.menuPrincipal = menuPrincipal;
        this.UsuarioActivo = (UsuarioActivo == null) ? "" : UsuarioActivo.trim();
        String usuario = menuPrincipal.getUsuarioActivo();
        this.Indice = Memoria.getIndiceUsuario(usuario);
        
        setSize(620, 520);
        setResizable(false);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        if (this.UsuarioActivo.isEmpty() || Indice < 0) {
            JOptionPane.showMessageDialog(padre, "No hay sesion activa", "Aviso", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }
        
        ImageIcon IconoFondo = new ImageIcon(getClass().getResource("/images/bg_modificardatos.PNG"));
        Image ImagenFondo = IconoFondo.getImage();
        
        JPanel PanelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        PanelFondo.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        PanelFondo.setBackground(new Color(10, 10, 20));
        setContentPane(PanelFondo);
        
        JPanel PanelCentral = new JPanel();
        PanelCentral.setLayout(new BorderLayout());
        PanelCentral.setOpaque(true);
        PanelCentral.setBackground(new Color(0, 0, 0, 110));
        PanelCentral.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        
        PanelFondo.add(PanelCentral, BorderLayout.CENTER);
        
        JPanel PanelForm = new JPanel();
        PanelForm.setLayout(new GridBagLayout());
        PanelForm.setOpaque(false);
        
        PanelFondo.add(PanelForm, BorderLayout.CENTER);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JTextField TxtUsuarioActivo = new JTextField(UsuarioActivo);
        TxtUsuarioActivo.setEnabled(false);
        EstilizarCampoTexto(TxtUsuarioActivo);
        
        TxtNuevoUsuario = new JTextField();
        EstilizarCampoTexto(TxtNuevoUsuario);
        
        TxtPassActual = new JPasswordField();
        EstilizarCampoTexto(TxtPassActual);
        
        TxtPassNueva = new JPasswordField();
        EstilizarCampoTexto(TxtPassNueva);
        
        TxtPassConfirm = new JPasswordField();
        EstilizarCampoTexto(TxtPassConfirm);
        
        AnadirFila(PanelForm, gbc, 0, "Usuario Actual:", TxtUsuarioActivo);
        AnadirFila(PanelForm, gbc, 1, "Nuevo Usuario:", TxtNuevoUsuario);
        
        JSeparator separador = new JSeparator();
        separador.setForeground(new Color(120, 120, 120));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        
        PanelForm.add(separador, gbc);
        
        AnadirFila(PanelForm, gbc, 3, "Contrasena Actual (Obligatoria):", TxtPassActual);
        AnadirFila(PanelForm, gbc, 4, "Nueva Contraseña (Opcional):", TxtPassNueva);
        AnadirFila(PanelForm, gbc, 5, "Confirmar Nueva Contraseña (Opcional):", TxtPassConfirm);
        
        LblMensaje = new JLabel(" ");
        EstilizarLabel(LblMensaje);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        
        PanelForm.add(LblMensaje, gbc);
        
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        PanelBotones.setOpaque(false);
        
        PanelFondo.add(PanelBotones, BorderLayout.SOUTH);
        
        JButton BtnGuardar = new JButton("GUARDAR");
        EstilizarBoton(BtnGuardar);
        
        JButton BtnVolver = new JButton("VOLVER");
        EstilizarBoton(BtnVolver);
        
        BtnGuardar.addActionListener(e -> onGuardar());
        BtnVolver.addActionListener(e -> dispose());
        
        PanelBotones.add(BtnGuardar);
        PanelBotones.add(BtnVolver);
        
        getRootPane().setDefaultButton(BtnGuardar);
    }
    
    private void AnadirFila(JPanel form, GridBagConstraints gbc, int fila, String label, JComponent field) {
        JLabel lbl = new JLabel(label);
        EstilizarLabel(lbl);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = fila;
        
        form.add(lbl, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = fila;
        
        form.add(field, gbc);
    }
    
    private void onGuardar() {
        String nuevousuario = TxtNuevoUsuario.getText().trim();
        String passactual = new String(TxtPassActual.getPassword()).trim();
        String passnueva = new String(TxtPassNueva.getPassword()).trim();
        String passconfirm = new String(TxtPassConfirm.getPassword()).trim();
        
        boolean quierecambiarusuario = !nuevousuario.isEmpty() && !nuevousuario.equalsIgnoreCase(UsuarioActivo);
        boolean quierecambiarcontra = !passnueva.isEmpty() || !passconfirm.isEmpty();
        
        if (!quierecambiarusuario && !quierecambiarcontra) {
            setMensaje("No cambiaste nada (si queres salir dale a 'VOLVER')!");
            return;
        }
        
        if (passactual.isEmpty()) {
            setMensaje("Debes ingresar tu contraseña actual");
            return;
        }
        
        if (!Memoria.ValidarContrasenaActual(Indice, passactual)) {
            setMensaje("Contraseña actual incorrecta");
            return;
        }
        
        if (quierecambiarcontra) {
            if (passnueva.isEmpty() || passconfirm.isEmpty()) {
                setMensaje("Completa nueva contraseña y la confirmacion");
                return;
            }
            
            if (!passnueva.equals(passconfirm)) {
                setMensaje("La nueva contraseña no coincide");
                return;
            }
        }
        
        if (quierecambiarusuario) {
            boolean okuser = Memoria.ActualizarUsuario(Indice, nuevousuario);
            if (!okuser) {
                setMensaje("No se pudo cambiar el usuario (asegurate de que sea unico!)");
                return;
            }
        }
        
        if (quierecambiarcontra) {
            boolean okpass = Memoria.ActualizarContrasena(Indice, passnueva);
            if (!okpass) {
                setMensaje("No se pudo cambiar la contraseña");
                return;
            }
        }
        
        if (quierecambiarusuario && menuPrincipal != null) {
            menuPrincipal.setUsuarioActivo(nuevousuario);
        }
        
        JOptionPane.showMessageDialog(this, "Datos actualizados correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
    
    private void setMensaje(String mensaje) {
        LblMensaje.setText(mensaje);
    }
    
    private void EstilizarBoton(JButton boton) {
        boton.setFont(new Font("DIN Condensed", Font.BOLD, 18));
        boton.setBackground(new Color(25, 25, 25)); //Gris oscuro tipo metal
        boton.setForeground(new Color(220, 180, 120)); //Dorado suave
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(120, 0, 0), 2), BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        boton.setOpaque(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(220, 44));
        
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
    
    private void EstilizarCampoTexto(JTextComponent campo) {
        campo.setFont(new Font("DIN Condensed", Font.BOLD, 18));
        campo.setBackground(new Color(25, 25, 25));
        campo.setForeground(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 0, 120), 2), BorderFactory.createEmptyBorder(5, 20, 5, 20)));
        campo.setOpaque(true);
        campo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        campo.setPreferredSize(new Dimension(220, 44));
        
        campo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                campo.setBackground(new Color(60, 0, 0));
                campo.setForeground(new Color(255, 220, 130));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                campo.setBackground(new Color(25, 25, 25));
                campo.setForeground(Color.WHITE);
            }
        });
    }
    
    private void EstilizarLabel(JLabel label) {
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setFont(new Font("DIN Condensed", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(0, 0, 0, 180));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6), BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 30, 0))));
    }
}
