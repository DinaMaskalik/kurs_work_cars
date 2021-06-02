package view;

import controller.Controller;

import javax.swing.*;

public class CreateCarForm extends JFrame{
    private JPanel contentPane;
    private JButton buttonBackAdmin;
    private JButton buttonSaveCar;
    private JComboBox brand_cb;
    private JComboBox series_cb;
    private JComboBox configuration_cb;
    private JTextField motor_tf;
    private JTextField engine_tf;
    private JTextField price_tf;
    private JTextField year_tf;
    private JTextField horsepower_tf;
    private JTextField count_tf;

    public CreateCarForm(){
        setContentPane(contentPane);
        //setUndecorated(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        Controller.getInstance().initialize(this);

        buttonBackAdmin.setActionCommand("backFromAddCar");
        buttonBackAdmin.addActionListener(Controller.getInstance());
        buttonSaveCar.setActionCommand("saveCar");
        buttonSaveCar.addActionListener(Controller.getInstance());
    }

    public JTextField getMotor_tf() {
        return motor_tf;
    }

    public JTextField getEngine_tf() {
        return engine_tf;
    }

    public JTextField getPrice_tf() {
        return price_tf;
    }

    public JTextField getYear_tf() {
        return year_tf;
    }

    public JTextField getHorsepower_tf() {
        return horsepower_tf;
    }

    public JTextField getCount_tf() {
        return count_tf;
    }

    public JComboBox getBrand_cb() {
        return brand_cb;
    }

    public JComboBox getSeries_cb() {
        return series_cb;
    }

    public JComboBox getConfiguration_cb() {
        return configuration_cb;
    }
}
