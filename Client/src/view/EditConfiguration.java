package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class EditConfiguration extends JFrame {
    private JButton buttonBackAdmin;
    private JButton buttonSaveConfiguration;
    private JComboBox configuration_cb;
    private JPanel pane;

    public EditConfiguration(){

        setContentPane(pane);

        Controller.getInstance().initialize(this);

        buttonBackAdmin.setActionCommand("backFromEditConfiguration");
        buttonBackAdmin.addActionListener(Controller.getInstance());
        buttonSaveConfiguration.setActionCommand("saveConfiguration");
        buttonSaveConfiguration.addActionListener(Controller.getInstance());
    }

    public JComboBox getConfiguration_cb() {
        return configuration_cb;
    }
}
