package view;

import controller.Controller;

import javax.swing.*;

public class UsersForm extends JFrame{
    private JPanel pane;
    private JTabbedPane tabbedPane1;
    private JButton buttonShow;
    private JScrollPane scrollPane;
    private JButton buttonOrder;

    private JTextField secondName_tf;
    private JTextField name_tf;
    private JTextField patronymic_tf;
    private JTextField number_tf;
    private JTextField passportSeries_tf;

    private JButton buttonExit;
    private JScrollPane scrollPaneCar;
    private JTable tableUserCars;
    private JTable carTable;

    private JComboBox brand_cb;
    private JComboBox series_cb;
    private JComboBox configuration_cb;

    private JComboBox buyConfiguration_cb;
    private JComboBox buySeries_cb;
    private JComboBox buyBrand_cb;
    private JComboBox year_cb;
    private JTextField from_tf;
    private JTextField to_tf;

    public UsersForm() {
        setContentPane(pane);
  //      setUndecorated(true);
 //       getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        Controller.getInstance().initialize(this);
        buttonShow.setActionCommand("showSearchCars");
        buttonShow.addActionListener(Controller.getInstance());
        buyBrand_cb.setActionCommand("showSeries");
        buyBrand_cb.addActionListener(Controller.getInstance());
        buySeries_cb.setActionCommand("showConfiguration");
        buySeries_cb.addActionListener(Controller.getInstance());
        buttonOrder.setActionCommand("buyCar");
        buttonOrder.addActionListener(Controller.getInstance());
        buttonExit.setActionCommand("exitUser");
        buttonExit.addActionListener(Controller.getInstance());
    }


    public JComboBox getYear_cb() {
        return year_cb;
    }

    public JTextField getFrom_tf() {
        return from_tf;
    }

    public JTextField getTo_tf() {
        return to_tf;
    }

    public JTextField getSecondName_tf() {
        return secondName_tf;
    }

    public JTextField getName_tf() {
        return name_tf;
    }

    public JTextField getPatronymic_tf() {
        return patronymic_tf;
    }

    public JTextField getNumber_tf() {
        return number_tf;
    }

    public JTextField getPassportSeries_tf() {
        return passportSeries_tf;
    }

    public JComboBox getBuyConfiguration_cb() {
        return buyConfiguration_cb;
    }

    public JComboBox getBuySeries_cb() {
        return buySeries_cb;
    }

    public JComboBox getBuyBrand_cb() {
        return buyBrand_cb;
    }

    public JTable getCarTable() {
        return carTable;
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

    public JTable getTableUserCars() {
        return tableUserCars;
    }
}
