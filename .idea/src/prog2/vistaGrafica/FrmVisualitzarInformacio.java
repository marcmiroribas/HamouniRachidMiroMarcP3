package prog2.vistaGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmVisualitzarInformacio extends JDialog {
    private JComboBox<String> cmbOpcions;
    private JButton btnVisualitzar;
    private JTextArea txtInformacio;

    public FrmVisualitzarInformacio(JFrame parent) {
        super(parent, "Visualitzar Informació", true);
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new FlowLayout());
        cmbOpcions = new JComboBox<>(new String[]{"Estat de la central", "Quadern de bitàcola", "Incidències"});
        btnVisualitzar = new JButton("Visualitzar");

        panelSuperior.add(new JLabel("Selecciona informació a visualitzar:"));
        panelSuperior.add(cmbOpcions);
        panelSuperior.add(btnVisualitzar);

        txtInformacio = new JTextArea();
        txtInformacio.setEditable(false);

        btnVisualitzar.addActionListener(e -> visualitzarInformacio());

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(txtInformacio), BorderLayout.CENTER);
    }

    private void visualitzarInformacio() {
        String opcio = (String) cmbOpcions.getSelectedItem();
        String informacio = "";

        switch (opcio) {
            case "Estat de la central":
                informacio = "Informació de l'estat de la central...";
                break;
            case "Quadern de bitàcola":
                informacio = "Informació del quadern de bitàcola...";
                break;
            case "Incidències":
                informacio = "Llistat d'incidències...";
                break;
        }

        txtInformacio.setText(informacio);
    }
}