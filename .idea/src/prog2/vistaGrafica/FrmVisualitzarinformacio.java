package prog2.vistaGrafica;

import javax.swing.*;

public class FrmVisualitzarinformacio extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public FrmVisualitzarinformacio() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }
}
