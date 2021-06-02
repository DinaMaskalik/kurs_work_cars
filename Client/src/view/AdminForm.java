package view;

import controller.Controller;

import javax.swing.*;

public class AdminForm extends JFrame{
    private AdminForm dialog;
    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
    private JPanel panelChange;//экспериментальная
    private JScrollPane scrollPanePassenger;
    private JScrollPane scrollPanePlane;
    private JPanel graficPane;

    private JComboBox brand_cb;
    private JComboBox series_cb;
    private JComboBox configuration_cb;
    private JButton buttonShow;
    private JTable carTable;
    private JScrollPane pane;
    private JButton buttonCreate;
    private JButton buttonDelete;
    private JButton buttonDeleteContract;

    private JTable startCarTable;
    private JComboBox brandDoc_cb;

    private JTable tableContract;
    private JButton buttonAdd;
    private JComboBox contractNumber_cb;

    private JComboBox correctBrand_cb;
    private JComboBox correctConfiguration_cb;
    private JComboBox correctSeries_cb;
    private JButton editBrand;
    private JButton editConfiguration;
    private JButton editSeries;
    private JButton editMotor;
    private JButton editPrice;
    private JButton editCount;
    private JTable tableEdit;
    private JComboBox year_cb;
    private JTextField from_tf;
    private JTextField to_tf;
    private JButton buttonShowCorrect;
    private JButton buttonShowContract;
    private JButton editNewBrand;
    private JTextField newBrand_tf;
    private JTable tableBrand;
    private JButton editNewSeries;
    private JTextField newSeries_tf;
    private JTable tableSeries;
    private JTable tableConfiguration;
    private JTextField newConfiguration_tf;
    private JButton editNewConfiguration;
    private JComboBox month_cb;
    private JButton showDiagram;


    public AdminForm(){
        setContentPane(contentPane);
        //setUndecorated(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        Controller.getInstance().initialize(this);
        buttonCreate.setActionCommand("createCar");
        buttonCreate.addActionListener(Controller.getInstance());
        buttonShow.setActionCommand("showAdminCar");
        buttonShow.addActionListener(Controller.getInstance());

        buttonDelete.setEnabled(false);
        buttonDelete.setActionCommand("deleteCar");
        buttonDelete.addActionListener(Controller.getInstance());

        correctBrand_cb.setActionCommand("showAdminSeries");
        correctBrand_cb.addActionListener(Controller.getInstance());
        correctSeries_cb.setActionCommand("showAdminConfiguration");
        correctSeries_cb.addActionListener(Controller.getInstance());
        showDiagram.setActionCommand("showDiagram");
        showDiagram.addActionListener(Controller.getInstance());

        buttonShowCorrect.setActionCommand("showCorrect");
        buttonShowCorrect.addActionListener(Controller.getInstance());

        editBrand.setEnabled(false);
        editBrand.setActionCommand("editBrand");
        editBrand.addActionListener(Controller.getInstance());

        editSeries.setEnabled(false);
        editSeries.setActionCommand("editSeries");
        editSeries.addActionListener(Controller.getInstance());

        editConfiguration.setEnabled(false);
        editConfiguration.setActionCommand("editConfiguration");
        editConfiguration.addActionListener(Controller.getInstance());

        editMotor.setEnabled(false);
        editMotor.setActionCommand("editMotor");
        editMotor.addActionListener(Controller.getInstance());

        editCount.setEnabled(false);
        editCount.setActionCommand("editCount");
        editCount.addActionListener(Controller.getInstance());

        editPrice.setEnabled(false);
        editPrice.setActionCommand("editPrice");
        editPrice.addActionListener(Controller.getInstance());

        buttonAdd.setActionCommand("addContractShowPanel");
        buttonAdd.addActionListener(Controller.getInstance());

        buttonDeleteContract.setEnabled(false);
        buttonDeleteContract.setActionCommand("deleteContract");
        buttonDeleteContract.addActionListener(Controller.getInstance());
        buttonShowContract.setActionCommand("showAdminContract");
        buttonShowContract.addActionListener(Controller.getInstance());

        editNewBrand.setActionCommand("editNewBrand");
        editNewBrand.addActionListener(Controller.getInstance());
        editNewSeries.setActionCommand("editNewSeries");
        editNewSeries.addActionListener(Controller.getInstance());
        editNewConfiguration.setActionCommand("editNewConfiguration");
        editNewConfiguration.addActionListener(Controller.getInstance());


//        buttonDoText.setActionCommand("doText");
//        buttonDoText.addActionListener(Controller.getInstance());
    }

    public JComboBox getMonth_cb() {
        return month_cb;
    }

    public JTextField getNewBrand_tf() {
        return newBrand_tf;
    }

    public JTable getTableBrand() {
        return tableBrand;
    }

    public JTextField getNewSeries_tf() {
        return newSeries_tf;
    }

    public JTable getTableSeries() {
        return tableSeries;
    }

    public JTable getTableConfiguration() {
        return tableConfiguration;
    }

    public JTextField getNewConfiguration_tf() {
        return newConfiguration_tf;
    }

    public JTable getStartCarTable() {
        return startCarTable;
    }

    public JPanel getGraficPane() {
        return graficPane;
    }

    public JComboBox getBrandDoc_cb() {
        return brandDoc_cb;
    }

    public JTable getTableEdit() {
        return tableEdit;
    }

    public JTable getTableContract() {
        return tableContract;
    }

    public JComboBox getContractNumber_cb() {
        return contractNumber_cb;
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

    public JComboBox getCorrectBrand_cb() {
        return correctBrand_cb;
    }

    public JComboBox getCorrectConfiguration_cb() {
        return correctConfiguration_cb;
    }

    public JComboBox getCorrectSeries_cb() {
        return correctSeries_cb;
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

    public JButton getEditBrand() {
        return editBrand;
    }

    public JButton getEditConfiguration() {
        return editConfiguration;
    }

    public JButton getEditSeries() {
        return editSeries;
    }

    public JButton getEditMotor() {
        return editMotor;
    }

    public JButton getEditPrice() {
        return editPrice;
    }

    public JButton getEditCount() {
        return editCount;
    }

    public JButton getButtonDelete() {
        return buttonDelete;
    }

    public JButton getButtonDeleteContract() {
        return buttonDeleteContract;
    }
}
