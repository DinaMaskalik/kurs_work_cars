package view;

import controller.Controller;

import javax.swing.*;

public class EditBrand extends JFrame {
    private JButton buttonSaveBrand;
    private JComboBox brand_cb;
    private JButton buttonBackAdmin;
    private JPanel pane;

    public EditBrand() {
        setContentPane(pane);

        Controller.getInstance().initialize(this);

        buttonBackAdmin.setActionCommand("backFromEditBrand");
        buttonBackAdmin.addActionListener(Controller.getInstance());
        buttonSaveBrand.setActionCommand("saveBrand");
        buttonSaveBrand.addActionListener(Controller.getInstance());
    }

    public JButton getButtonSaveBrand() {
        return buttonSaveBrand;
    }

    public JComboBox getBrand_cb() {
        return brand_cb;
    }

    public JButton getButtonBackAdmin() {
        return buttonBackAdmin;
    }
}
