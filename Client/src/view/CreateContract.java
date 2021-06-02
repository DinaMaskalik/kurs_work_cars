package view;

import controller.Controller;

import javax.swing.*;

public class CreateContract extends JFrame {
    private JTextField name_tf;
    private JTextField patronymic_tf;
    private JTextField series_tf;
    private JTextField number_tf;
    private JComboBox buyBrand_cb;
    private JComboBox buySeries_cb;
    private JComboBox buyConfiguration_cb;
    private JTextField secondName_tf;
    private JButton buttonOrder;
    private JTextField numberContract_tf;
    private JPanel pane;
    private JButton buttonBackAdmin;

    public CreateContract() {

        setContentPane(pane);
        //setUndecorated(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        Controller.getInstance().initialize(this);

        buttonOrder.setActionCommand("addContract");
        buttonOrder.addActionListener(Controller.getInstance());

        buyBrand_cb.setActionCommand("showCreateContractSeries");
        buyBrand_cb.addActionListener(Controller.getInstance());
        buySeries_cb.setActionCommand("showCreateContractConfiguration");
        buySeries_cb.addActionListener(Controller.getInstance());
        buttonBackAdmin.setActionCommand("backFromCreateContract");
        buttonBackAdmin.addActionListener(Controller.getInstance());


    }

    public JTextField getName_tf() {
        return name_tf;
    }

    public JTextField getPatronymic_tf() {
        return patronymic_tf;
    }

    public JTextField getSeries_tf() {
        return series_tf;
    }

    public JTextField getNumber_tf() {
        return number_tf;
    }

    public JComboBox getBuyBrand_cb() {
        return buyBrand_cb;
    }

    public JComboBox getBuySeries_cb() {
        return buySeries_cb;
    }

    public JComboBox getBuyConfiguration_cb() {
        return buyConfiguration_cb;
    }

    public JTextField getSecondName_tf() {
        return secondName_tf;
    }

    public JTextField getNumberContract_tf() {
        return numberContract_tf;
    }
}
