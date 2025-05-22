package prog2.vistaGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmGestioComponentsCentral extends JDialog {
    private JSlider sldBarresControl;
    private JTextField txtIntroduirInsercioBarresControl;
    private JButton btnAplicarInsercio;
    private JCheckBox chkReactor;
    private JCheckBox[] chkBombes;
    private JList<String> lstBombesForaServei;
    private JButton btnAplicar, btnCancelar;

    public FrmGestioComponentsCentral(JFrame parent) {
        super(parent, "Gestió Components Central", true);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new GridLayout(4, 1, 10, 10));

        // Barres de Control
        JPanel panelBarres = new JPanel(new FlowLayout());
        panelBarres.setBorder(BorderFactory.createTitledBorder("Barres de Control"));
        sldBarresControl = new JSlider(0, 100, 50);
        txtIntroduirInsercioBarresControl = new JTextField(5);
        btnAplicarInsercio = new JButton("Aplicar");

        panelBarres.add(new JLabel("Inserció (%):"));
        panelBarres.add(sldBarresControl);
        panelBarres.add(txtIntroduirInsercioBarresControl);
        panelBarres.add(btnAplicarInsercio);

        //Reactor
        JPanel panelReactor = new JPanel(new FlowLayout());
        panelReactor.setBorder(BorderFactory.createTitledBorder("Reactor"));
        chkReactor = new JCheckBox("Reactor activat");
        panelReactor.add(chkReactor);

        // Bombes Refrigerants
        JPanel panelBombes = new JPanel(new GridLayout(1, 2));
        panelBombes.setBorder(BorderFactory.createTitledBorder("Bombes Refrigerants"));

        JPanel panelBombesActives = new JPanel(new GridLayout(4, 1));
        chkBombes = new JCheckBox[4];
        for (int i = 0; i < 4; i++) {
            chkBombes[i] = new JCheckBox("Bomba " + (i+1));
            panelBombesActives.add(chkBombes[i]);
        }

        JPanel panelBombesForaServei = new JPanel(new BorderLayout());
        lstBombesForaServei = new JList<>();
        panelBombesForaServei.add(new JLabel("Bombes fora de servei:"), BorderLayout.NORTH);
        panelBombesForaServei.add(new JScrollPane(lstBombesForaServei), BorderLayout.CENTER);

        panelBombes.add(panelBombesActives);
        panelBombes.add(panelBombesForaServei);

        // Botons
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAplicar = new JButton("Aplicar Canvis");
        btnCancelar = new JButton("Cancel·lar");

        btnAplicar.addActionListener(e -> aplicarCanvis());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnAplicar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(panelBarres);
        panelPrincipal.add(panelReactor);
        panelPrincipal.add(panelBombes);

        add(panelPrincipal, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void aplicarCanvis() {
        JOptionPane.showMessageDialog(this, "Canvis aplicats correctament");
        dispose();
    }
}