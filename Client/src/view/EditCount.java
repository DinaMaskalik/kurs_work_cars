package view;

import controller.Controller;

import javax.swing.*;

public class EditCount extends JFrame{
    private JButton buttonBackAdmin;
    private JButton buttonEditCount;
    private JTextField count_tf;
    private JPanel pane;

    public EditCount() {
        setContentPane(pane);

        Controller.getInstance().initialize(this);

        buttonBackAdmin.setActionCommand("backFromEditCount");
        buttonBackAdmin.addActionListener(Controller.getInstance());
        buttonEditCount.setActionCommand("saveCount");
        buttonEditCount.addActionListener(Controller.getInstance());
    }

    public JTextField getCount_tf() {
        return count_tf;
    }
}
