package view;

import controller.Controller;

import javax.swing.*;

public class EditPrice extends JFrame {
    private JButton buttonBackAdmin;
    private JButton buttonSavePrice;
    private JTextField price_tf;
    private JPanel pane;

    public EditPrice() {
        setContentPane(pane);

        Controller.getInstance().initialize(this);

        buttonBackAdmin.setActionCommand("backFromEditPrice");
        buttonBackAdmin.addActionListener(Controller.getInstance());
        buttonSavePrice.setActionCommand("savePrice");
        buttonSavePrice.addActionListener(Controller.getInstance());
    }

    public JTextField getPrice_tf() {
        return price_tf;
    }
}
