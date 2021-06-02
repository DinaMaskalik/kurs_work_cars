package view;

import controller.Controller;

import javax.swing.*;

public class SellerForm extends JFrame{

    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
    private JTextField secondName_tf;
    private JTextField name_tf;
    private JTextField patronymic_tf;
    private JTextField series_tf;
    private JTextField number_tf;
    private JTextField numberContract_tf;
    private JTextField searchNumberContract_tf;
    private JButton buttonSearch;
    private JButton buttonShow;
    private JScrollPane scrollPaneReturnCar;
    private JButton buttonReturnCar;
    private JTable tableContract;
//    private JTextField newFlight_tf;

    private JComboBox buyBrand_cb;
    private JComboBox buySeries_cb;
    private JComboBox buyConfiguration_cb;

    private JComboBox brand_cb;
    private JComboBox series_cb;
    private JComboBox configuration_cb;
    private JTable carTable;
    private JButton buttonOrder;
    private JComboBox year_cb;
    private JTextField to_tf;
    private JTextField from_tf;
    private JButton backButton;

    public SellerForm(){
        setContentPane(contentPane);
//        setUndecorated(true);
//        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);


        Controller.getInstance().initialize(this);
        buttonShow.setActionCommand("showSellerCar");
        buttonShow.addActionListener(Controller.getInstance());
        buyBrand_cb.setActionCommand("showSellerSeries");
        buyBrand_cb.addActionListener(Controller.getInstance());
        buySeries_cb.setActionCommand("showSellerConfiguration");
        buySeries_cb.addActionListener(Controller.getInstance());
        buttonOrder.setActionCommand("orderCarSeller");
        buttonOrder.addActionListener(Controller.getInstance());
        buttonSearch.setActionCommand("searchContract");
        buttonSearch.addActionListener(Controller.getInstance());
        buttonReturnCar.setActionCommand("returnCar");
        buttonReturnCar.addActionListener(Controller.getInstance());
        buttonReturnCar.setEnabled(false);
        backButton.setActionCommand("backFromSeller");
        backButton.addActionListener(Controller.getInstance());

//        buttonAddFutureFlight.setActionCommand("addFutureFlight");
//        buttonAddFutureFlight.addActionListener(Controller.getInstance());
    }

    public JComboBox getYear_cb() {
        return year_cb;
    }

    public JTextField getTo_tf() {
        return to_tf;
    }

    public JTextField getFrom_tf() {
        return from_tf;
    }

    public JTextField getName_tf() {
        return name_tf;
    }

    public JTextField getSecondName_tf() {
        return secondName_tf;
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

    public JTextField getNumberContract_tf() {
        return numberContract_tf;
    }

    public JTextField getSearchNumberContract_tf() {
        return searchNumberContract_tf;
    }

    public JButton getButtonReturnCar() {
        return buttonReturnCar;
    }

    public JTable getTableContract() {
        return tableContract;
    }

//    public JTextField getNewFlight_tf() {
//        return newFlight_tf;
//    }

    public JComboBox getBuyBrand_cb() {
        return buyBrand_cb;
    }

    public JComboBox getBuySeries_cb() {
        return buySeries_cb;
    }

    public JComboBox getBuyConfiguration_cb() {
        return buyConfiguration_cb;
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

    public JTable getCarTable() {
        return carTable;
    }
}
