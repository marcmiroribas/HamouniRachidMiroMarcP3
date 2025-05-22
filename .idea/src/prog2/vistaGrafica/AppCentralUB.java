package prog2.vistaGrafica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AppCentralUB extends JFrame {
    private JPanel mainPanel;
    private JButton btnGestioComponentsCentral;
    private JButton btnVisualitzarInformacio;
    private JButton btnFinalitzarDia;
    private JButton btnGuardar;
    private JButton btnCarregar;
    private JLabel lblDia;
    private JLabel lblDemanda;
    private JLabel lblGuanys;

    public AppCentralUB() {
        super("Central Nuclear UB");

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        actualizarInformacion(1, 1000, 0.0);

        configurarEventos();
    }

    private void configurarEventos() {
        btnGestioComponentsCentral.addActionListener(e -> {
            FrmGestioComponentsCentral dialog = new FrmGestioComponentsCentral(this);
            dialog.setVisible(true);
        });

        btnVisualitzarInformacio.addActionListener(e -> {
            FrmVisualitzarInformacio dialog = new FrmVisualitzarInformacio(this);
            dialog.setVisible(true);
        });

        btnFinalitzarDia.addActionListener(e -> {
            int nuevoDia = Integer.parseInt(lblDia.getText()) + 1;
            double nuevosGanancias = Double.parseDouble(lblGuanys.getText().replace(" €", "")) + 1000;
            actualizarInformacion(nuevoDia, 1000, nuevosGanancias);
            JOptionPane.showMessageDialog(this, "Dia finalizado correctamente");
        });

        btnGuardar.addActionListener(e -> guardarDatos());
        btnCarregar.addActionListener(e -> cargarDatos());
    }

    private void actualizarInformacion(int dia, int demanda, double ganancias) {
        lblDia.setText(String.valueOf(dia));
        lblDemanda.setText(demanda + " MW");
        lblGuanys.setText(String.format("%.2f €", ganancias));
    }

    private void guardarDatos() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Datos guardados en: " + file.getAbsolutePath());
        }
    }

    private void cargarDatos() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Datos cargados de: " + file.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppCentralUB app = new AppCentralUB();
            app.setVisible(true);
        });
    }
}