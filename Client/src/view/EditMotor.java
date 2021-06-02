package view;

import controller.Controller;

import javax.swing.*;

public class EditMotor extends JFrame {
    private JPanel pane;
    private JButton buttonBackAdmin;
    private JButton saveMotor;
    private JTextField motor_tf;

    public EditMotor() {
        setContentPane(pane);

        Controller.getInstance().initialize(this);

        buttonBackAdmin.setActionCommand("backFromEditMotor");
        buttonBackAdmin.addActionListener(Controller.getInstance());
        saveMotor.setActionCommand("saveMotor");
        saveMotor.addActionListener(Controller.getInstance());
    }

    public JTextField getMotor_tf() {
        return motor_tf;
    }
}
