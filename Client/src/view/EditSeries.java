package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class EditSeries extends JFrame {
    private JButton buttonSaveSeries;
    private JComboBox series_cb;
    private JButton buttonBackAdmin;
    private JPanel pane;

    public EditSeries(){

        setContentPane(pane);

        Controller.getInstance().initialize(this);

        buttonBackAdmin.setActionCommand("backFromEditSeries");
        buttonBackAdmin.addActionListener(Controller.getInstance());
        buttonSaveSeries.setActionCommand("saveSeries");
        buttonSaveSeries.addActionListener(Controller.getInstance());
    }

    public JComboBox getSeries_cb() {
        return series_cb;
    }
}
