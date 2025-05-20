package prog2.vistaGrafica;

import javax.swing.*;

public class GestioComponentsCentral extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public GestioComponentsCentral() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }




}
