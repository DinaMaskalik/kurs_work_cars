package controller;

import connection.Client;
import model.Car;
import model.Customer;
import model.Date;
import model.User;
import view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Controller implements ActionListener {

    private static Controller instance;
    private EnterDialog objEnterDialog;
    private AdminForm objAdminForm;
    private RegistrationUsers objRegistrarionUsers;
    private UsersForm objUsersForm;
    private SellerForm objSellerForm;
    private CreateCarForm objCreateCarForm;
    private EditBrand objEditBrand;
    private EditSeries objEditSeries;
    private EditConfiguration objEditConfiguration;
    private EditMotor objEditMotor;
    private EditCount objEditCount;
    private EditPrice objEditPrice;
    private CreateContract objCreateContract;
    private Client client;
    private String action;
    User user;

    private Controller() {
        client = new Client("127.0.0.1", "9006");
    }


    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void initialize(EnterDialog obj) {
        objEnterDialog = obj;
    }

    public void initialize(EditBrand obj) {
        objEditBrand = obj;
    }

    public void initialize(CreateContract obj) {
        objCreateContract = obj;
    }

    public void initialize(AdminForm obj) {
        objAdminForm = obj;
    }

    public void initialize(EditConfiguration obj) {
        objEditConfiguration = obj;
    }

    public void initialize(EditCount obj) {
        objEditCount = obj;
    }

    public void initialize(EditPrice obj) {
        objEditPrice = obj;
    }

    public void initialize(EditMotor obj) {
        objEditMotor = obj;
    }

    public void initialize(EditSeries obj) {
        objEditSeries = obj;
    }

    public void initialize(RegistrationUsers obj) {
        objRegistrarionUsers = obj;
    }

    public void initialize(UsersForm obj) {
        objUsersForm = obj;
    }

    public void initialize(SellerForm obj) {
        objSellerForm = obj;
    }

    public void initialize(CreateCarForm obj) {
        objCreateCarForm = obj;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        action = e.getActionCommand();
        try {
            switch (action) {
                case "Вход":
                    authorization();
                    break;
                case "registrationUsers": {

                    String msgLogin = objRegistrarionUsers.getLogin_tf().getText();
                    String msgPassword = objRegistrarionUsers.getPasswordField().getText();
                    String msgReturnPassword = objRegistrarionUsers.getReturnPasswordField().getText();

                    if ("".equals(msgLogin) || "".equals(msgPassword))
                        JOptionPane.showMessageDialog(objRegistrarionUsers,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    else {
                        if (msgLogin.equals(msgReturnPassword)) {
                            JOptionPane.showMessageDialog(objRegistrarionUsers,
                                    "Пароли не совпадают!",
                                    "Ошибка ввода",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            User user = new User();
                            user.setLogin(msgLogin);
                            user.setPassword(msgPassword);

                            client.sendMessage("registrationUser");
                            client.sendObject(user);
                            String message = client.readMessage();

                            if ("This user is already existed".equals(message)) {
                                JOptionPane.showMessageDialog(objRegistrarionUsers,
                                        "Такой пользователь уже существует!",
                                        "Ошибка регистрации",
                                        JOptionPane.ERROR_MESSAGE);

                            } else {
                                JOptionPane.showMessageDialog(objRegistrarionUsers,
                                        "Пользователь успешно зарегистрирован",
                                        "Регистрация пользователя",
                                        JOptionPane.PLAIN_MESSAGE);


                                objRegistrarionUsers.dispose();
                                EnterDialog dialog = new EnterDialog();
                                dialog.setTitle("Авторизация");
                                dialog.pack();
                                dialog.setLocationRelativeTo(null);
                                dialog.setVisible(true);
                            }
                        }
                    }
                    break;
                }

                case "backToAuthorization": {
                    objRegistrarionUsers.dispose();
                    EnterDialog dialog = new EnterDialog();
                    dialog.setTitle("Авторизация");
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                    break;
                }
                case "showRegistrationFrame": {
                    objEnterDialog.dispose();
                    RegistrationUsers formReg = new RegistrationUsers();
                    formReg.setTitle("Регистрация пользователя");
                    formReg.pack();
                    formReg.setLocationRelativeTo(null);
                    formReg.setVisible(true);
                    break;
                }
                case "showSearchCars": {
                    client.sendMessage("showSearchCars");

                    Car car = new Car();
                    car.setBrand(objUsersForm.getBrand_cb().getSelectedItem().toString());
                    car.setSeries(objUsersForm.getSeries_cb().getSelectedItem().toString());
                    car.setConfiguration(objUsersForm.getConfiguration_cb().getSelectedItem().toString());

                    if (!"".equals(objUsersForm.getYear_cb().getSelectedItem().toString())) {
                        car.setYearCB(Integer.parseInt(objUsersForm.getYear_cb().getSelectedItem().toString()));
                    }

                    if (!"".equals(objUsersForm.getFrom_tf().getText())) {
                        car.setPriseFrom(Double.parseDouble(objUsersForm.getFrom_tf().getText()));
                    }

                    if (!"".equals(objUsersForm.getTo_tf().getText())) {
                        car.setPriseTo(Double.parseDouble(objUsersForm.getTo_tf().getText()));
                    }

                    client.sendObject(car);

                    createCarTable();
                }
                break;
                case "showSeries": {
                    client.sendMessage("showSeries");
                    client.sendMessage(objUsersForm.getBuyBrand_cb().getSelectedItem().toString());

                    String[] series = (String[]) client.readObject();

                    if (objUsersForm.getBuySeries_cb().getItemCount() != 0) {
                        objUsersForm.getBuySeries_cb().removeAllItems();
                    }

                    for (int i = 0; i < series.length; i++) {
                        objUsersForm.getBuySeries_cb().addItem(series[i]);
                    }

                }
                break;
                case "showConfiguration": {
                    client.sendMessage("showConfiguration");

                    client.sendMessage(objUsersForm.getBuyBrand_cb().getSelectedItem().toString());
                    if (objUsersForm.getBuySeries_cb().getItemCount() != 0) {
                        client.sendMessage(objUsersForm.getBuySeries_cb().getSelectedItem().toString());

                    } else {
                        client.sendMessage("");
                    }

                    String[] configuration = (String[]) client.readObject();

                    if (objUsersForm.getBuyConfiguration_cb().getItemCount() != 0)
                        objUsersForm.getBuyConfiguration_cb().removeAllItems();

                    for (int i = 0; i < configuration.length; i++) {
                        objUsersForm.getBuyConfiguration_cb().addItem(configuration[i]);
                    }
                }
                break;
                case "buyCar": {

                    if ("".equals(objUsersForm.getName_tf().getText()) ||
                            "".equals(objUsersForm.getSecondName_tf().getText()) ||
                            "".equals(objUsersForm.getPatronymic_tf().getText()) ||
                            "".equals(objUsersForm.getPassportSeries_tf().getText()) ||
                            "".equals(objUsersForm.getNumber_tf().getText()) ||
                            "".equals(objUsersForm.getBuyBrand_cb().getSelectedItem().toString()) ||
                            "".equals(objUsersForm.getBuySeries_cb().getSelectedItem().toString()) ||
                            "".equals(objUsersForm.getBuyConfiguration_cb().getSelectedItem().toString())) {
                        JOptionPane.showMessageDialog(objUsersForm,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        client.sendMessage("buyCar");

                        Customer customer = new Customer();
                        customer.setName(objUsersForm.getName_tf().getText());
                        customer.setSecondName(objUsersForm.getSecondName_tf().getText());
                        customer.setMiddleName(objUsersForm.getPatronymic_tf().getText());
                        customer.setSeries(objUsersForm.getPassportSeries_tf().getText());
                        customer.setNumber(objUsersForm.getNumber_tf().getText());

                        client.sendObject(customer);

                        Car car = new Car();
                        car.setBrand(objUsersForm.getBuyBrand_cb().getSelectedItem().toString());
                        car.setSeries(objUsersForm.getBuySeries_cb().getSelectedItem().toString());
                        car.setConfiguration(objUsersForm.getBuyConfiguration_cb().getSelectedItem().toString());

                        client.sendObject(car);

                        String massage = client.readMessage();

                        if ("Successful insert".equals(massage)) {
                            JOptionPane.showMessageDialog(objUsersForm,
                                    "Поздравляем! Покупка оформлена!",
                                    "Покупка автомобиля",
                                    JOptionPane.PLAIN_MESSAGE);

                            objUsersForm.getName_tf().setText("");
                            objUsersForm.getSecondName_tf().setText("");
                            objUsersForm.getPassportSeries_tf().setText("");
                            objUsersForm.getPatronymic_tf().setText("");
                            objUsersForm.getNumber_tf().setText("");

                            objUsersForm.getBuyBrand_cb().setSelectedItem("");

                            client.sendMessage("createTableUsersCars");
                            client.sendObject(user);
                            createTableUsersCars();
                        } else
                            JOptionPane.showMessageDialog(objUsersForm,
                                    "Ошибка покупки!",
                                    "Покупка автомобиля",
                                    JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
                case "exitUser": {
                    objUsersForm.dispose();
                    EnterDialog dialog = new EnterDialog();
                    dialog.setTitle("Авторизация");
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                }
                break;
                case "showSellerCar": {

                    client.sendMessage("showSearchCars");

                    Car car = new Car();
                    car.setBrand(objSellerForm.getBrand_cb().getSelectedItem().toString());
                    car.setSeries(objSellerForm.getSeries_cb().getSelectedItem().toString());
                    car.setConfiguration(objSellerForm.getConfiguration_cb().getSelectedItem().toString());

                    if (!"".equals(objSellerForm.getYear_cb().getSelectedItem().toString())) {
                        car.setYearCB(Integer.parseInt(objSellerForm.getYear_cb().getSelectedItem().toString()));
                    }

                    if (!"".equals(objSellerForm.getFrom_tf().getText())) {
                        car.setPriseFrom(Double.parseDouble(objSellerForm.getFrom_tf().getText()));
                    }

                    if (!"".equals(objSellerForm.getTo_tf().getText())) {
                        car.setPriseTo(Double.parseDouble(objSellerForm.getTo_tf().getText()));
                    }

                    client.sendObject(car);

                    createSellerCarTable();

                }
                break;

                case "showSellerSeries": {
                    client.sendMessage("showSeries");
                    client.sendMessage(objSellerForm.getBuyBrand_cb().getSelectedItem().toString());

                    String[] series = (String[]) client.readObject();

                    if (objSellerForm.getBuySeries_cb().getItemCount() != 0) {
                        objSellerForm.getBuySeries_cb().removeAllItems();
                    }

                    for (int i = 0; i < series.length; i++) {
                        objSellerForm.getBuySeries_cb().addItem(series[i]);
                    }

                }
                break;

                case "showSellerConfiguration": {
                    client.sendMessage("showConfiguration");

                    client.sendMessage(objSellerForm.getBuyBrand_cb().getSelectedItem().toString());
                    if (objSellerForm.getBuySeries_cb().getItemCount() != 0) {
                        client.sendMessage(objSellerForm.getBuySeries_cb().getSelectedItem().toString());

                    } else {
                        client.sendMessage("");
                    }

                    String[] configuration = (String[]) client.readObject();

                    if (objSellerForm.getBuyConfiguration_cb().getItemCount() != 0)
                        objSellerForm.getBuyConfiguration_cb().removeAllItems();

                    for (int i = 0; i < configuration.length; i++) {
                        objSellerForm.getBuyConfiguration_cb().addItem(configuration[i]);
                    }

                }
                break;
                case "orderCarSeller": {

                    if ("".equals(objSellerForm.getName_tf().getText()) ||
                            "".equals(objSellerForm.getSecondName_tf().getText()) ||
                            "".equals(objSellerForm.getPatronymic_tf().getText()) ||
                            "".equals(objSellerForm.getSeries_tf().getText()) ||
                            "".equals(objSellerForm.getNumber_tf().getText()) ||
                            "".equals(objSellerForm.getBuyBrand_cb().getSelectedItem().toString()) ||
                            "".equals(objSellerForm.getBuySeries_cb().getSelectedItem().toString()) ||
                            "".equals(objSellerForm.getBuyConfiguration_cb().getSelectedItem().toString()) ||
                            "".equals(objSellerForm.getNumberContract_tf().getText())) {
                        JOptionPane.showMessageDialog(objSellerForm,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        client.sendMessage("orderCarSeller");

                        Customer customer = new Customer();
                        customer.setName(objSellerForm.getName_tf().getText());
                        customer.setSecondName(objSellerForm.getSecondName_tf().getText());
                        customer.setMiddleName(objSellerForm.getPatronymic_tf().getText());
                        customer.setSeries(objSellerForm.getSeries_tf().getText());
                        customer.setNumber(objSellerForm.getNumber_tf().getText());

                        client.sendObject(customer);

                        Car car = new Car();
                        car.setBrand(objSellerForm.getBuyBrand_cb().getSelectedItem().toString());
                        car.setSeries(objSellerForm.getBuySeries_cb().getSelectedItem().toString());
                        car.setConfiguration(objSellerForm.getBuyConfiguration_cb().getSelectedItem().toString());

                        client.sendObject(car);
                        client.sendMessage(objSellerForm.getNumberContract_tf().getText());

                        String massage = client.readMessage();

                        if ("Successful insert".equals(massage)) {
                            JOptionPane.showMessageDialog(objSellerForm,
                                    "           Покупка оформлена!",
                                    "Покупка автомобиля",
                                    JOptionPane.PLAIN_MESSAGE);

                            objSellerForm.getName_tf().setText("");
                            objSellerForm.getSecondName_tf().setText("");
                            objSellerForm.getSeries_tf().setText("");
                            objSellerForm.getPatronymic_tf().setText("");
                            objSellerForm.getNumber_tf().setText("");
                            objSellerForm.getNumberContract_tf().setText("");

                            objSellerForm.getBuyBrand_cb().setSelectedItem("");

                        } else
                            JOptionPane.showMessageDialog(objUsersForm,
                                    "Ошибка покупки!",
                                    "Покупка автомобиля",
                                    JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
                case "searchContract": {

                    if ("".equals(objSellerForm.getSearchNumberContract_tf().getText())) {
                        JOptionPane.showMessageDialog(objSellerForm,
                                "         Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        client.sendMessage("searchContract");
                        client.sendMessage(objSellerForm.getSearchNumberContract_tf().getText());

                        if ("Found".equals(client.readMessage())) {
                            createTableContract();
                            objSellerForm.getButtonReturnCar().setEnabled(true);
                        } else
                            JOptionPane.showMessageDialog(objSellerForm,
                                    " По этому номеру ничего не найдено!",
                                    "Ошибка поиска",
                                    JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
                case "returnCar": {

                    client.sendMessage("returnCar");
                    client.sendMessage(objSellerForm.getSearchNumberContract_tf().getText());

                    if ("OK".equals(client.readMessage()))
                        JOptionPane.showMessageDialog(objSellerForm,
                                "           Возврат оформлен!",
                                "Возврат автомобиля",
                                JOptionPane.PLAIN_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(objSellerForm,
                                " Ошибка удаления!",
                                "Ошибка поиска",
                                JOptionPane.ERROR_MESSAGE);

                    objSellerForm.getButtonReturnCar().setEnabled(false);

                    objSellerForm.getSearchNumberContract_tf().setText("");

                    TableModel tableModel = new DefaultTableModel(new String[0][0], new String[0]);
                    objSellerForm.getTableContract().setModel(tableModel);

                }
                break;
                case "showAdminCar": {
                    client.sendMessage("showSearchCars");

                    Car car = new Car();
                    car.setBrand(objAdminForm.getBrand_cb().getSelectedItem().toString());
                    car.setSeries(objAdminForm.getSeries_cb().getSelectedItem().toString());
                    car.setConfiguration(objAdminForm.getConfiguration_cb().getSelectedItem().toString());

                    if (!"".equals(objAdminForm.getYear_cb().getSelectedItem().toString())) {
                        car.setYearCB(Integer.parseInt(objAdminForm.getYear_cb().getSelectedItem().toString()));
                    }

                    if (!"".equals(objAdminForm.getFrom_tf().getText())) {
                        car.setPriseFrom(Double.parseDouble(objAdminForm.getFrom_tf().getText()));
                    }

                    if (!"".equals(objAdminForm.getTo_tf().getText())) {
                        car.setPriseTo(Double.parseDouble(objAdminForm.getTo_tf().getText()));
                    }

                    client.sendObject(car);
                    createAdminCarTable();

                    objAdminForm.getButtonDelete().setEnabled(true);

                }
                break;
                case "showAdminSeries": {
                    client.sendMessage("showSeries");
                    client.sendMessage(objAdminForm.getCorrectBrand_cb().getSelectedItem().toString());

                    String[] series = (String[]) client.readObject();

                    if (objAdminForm.getCorrectSeries_cb().getItemCount() != 0) {
                        objAdminForm.getCorrectSeries_cb().removeAllItems();
                    }

                    for (int i = 0; i < series.length; i++) {
                        objAdminForm.getCorrectSeries_cb().addItem(series[i]);
                    }

                }
                break;

                case "showAdminConfiguration": {
                    client.sendMessage("showConfiguration");

                    client.sendMessage(objAdminForm.getCorrectBrand_cb().getSelectedItem().toString());
                    if (objAdminForm.getCorrectSeries_cb().getItemCount() != 0) {
                        client.sendMessage(objAdminForm.getCorrectSeries_cb().getSelectedItem().toString());

                    } else {
                        client.sendMessage("");
                    }

                    String[] configuration = (String[]) client.readObject();

                    if (objAdminForm.getCorrectConfiguration_cb().getItemCount() != 0) {
                        objAdminForm.getCorrectConfiguration_cb().removeAllItems();

                    }

                    for (int i = 0; i < configuration.length; i++) {
                        objAdminForm.getCorrectConfiguration_cb().addItem(configuration[i]);
                    }


                }
                break;

                case "showCorrect": {
                    client.sendMessage("showSearchCars");

                    Car car = new Car();
                    car.setBrand(objAdminForm.getCorrectBrand_cb().getSelectedItem().toString());
                    car.setSeries(objAdminForm.getCorrectSeries_cb().getSelectedItem().toString());
                    car.setConfiguration(objAdminForm.getCorrectConfiguration_cb().getSelectedItem().toString());

                    objAdminForm.getEditBrand().setEnabled(true);
                    objAdminForm.getEditSeries().setEnabled(true);
                    objAdminForm.getEditConfiguration().setEnabled(true);
                    objAdminForm.getEditMotor().setEnabled(true);
                    objAdminForm.getEditCount().setEnabled(true);
                    objAdminForm.getEditPrice().setEnabled(true);

                    client.sendObject(car);
                    createEditTable();

                }
                break;

                case "createCar": {
                    CreateCarForm form = new CreateCarForm();
                    form.setTitle("Добавление записи");
                    form.pack();
                    form.setLocationRelativeTo(null);
                    form.setVisible(true);

                    createCarFormCB();
                }
                break;

                case "backFromAddCar": {
                    objCreateCarForm.dispose();
                }
                break;
                case "saveCar": {

                    String brand = objCreateCarForm.getBrand_cb().getSelectedItem().toString();
                    String series = objCreateCarForm.getSeries_cb().getSelectedItem().toString();
                    String configuration = objCreateCarForm.getConfiguration_cb().getSelectedItem().toString();
                    String motor = objCreateCarForm.getMotor_tf().getText();
                    String price = objCreateCarForm.getPrice_tf().getText();
                    String count = objCreateCarForm.getCount_tf().getText();
                    String engine = objCreateCarForm.getEngine_tf().getText();
                    String horsepower = objCreateCarForm.getHorsepower_tf().getText();
                    String year = objCreateCarForm.getYear_tf().getText();

                    if ("".equals(brand) || "".equals(series) || "".equals(configuration) || "".equals(motor) || "".equals(price)
                            || "".equals(count) || "".equals(engine) || "".equals(horsepower) || "".equals(year))
                        JOptionPane.showMessageDialog(objCreateCarForm,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);

                    else {
                        client.sendMessage("saveCar");

                        Car car = new Car();
                        car.setBrand(brand);
                        car.setSeries(series);
                        car.setConfiguration(configuration);
                        car.setMotor(motor);
                        car.setPrice(Integer.parseInt(price));
                        car.setCount(Integer.parseInt(count));
                        car.setEngine(Double.parseDouble(engine));
                        car.setHorsepower(Integer.parseInt(horsepower));
                        car.setYear(Integer.parseInt(year));

                        client.sendObject(car);

                        String msg = client.readMessage();

                        if ("OK".equals(msg)) {
                            JOptionPane.showMessageDialog(objCreateCarForm,
                                    "              Автомобиль добавлен!",
                                    "Добавление автомобиля",
                                    JOptionPane.PLAIN_MESSAGE);

                            objCreateCarForm.dispose();

                            client.sendMessage("createCarTable");
                            createAdminCarStartTable();

                            client.sendMessage("createCarTable");
                            createAdminCarTable();


                        } else {
                            objCreateCarForm.getBrand_cb().setSelectedItem("");
                            objCreateCarForm.getSeries_cb().setSelectedItem("");
                            objCreateCarForm.getConfiguration_cb().setSelectedItem("");
                            objCreateCarForm.getMotor_tf().setText("");
                            objCreateCarForm.getPrice_tf().setText("");
                            objCreateCarForm.getCount_tf().setText("");
                            objCreateCarForm.getEngine_tf().setText("");
                            objCreateCarForm.getHorsepower_tf().setText("");
                            objCreateCarForm.getYear_tf().setText("");

                            JOptionPane.showMessageDialog(objCreateCarForm,
                                    "Такой автомобиль уже существует!",
                                    "Ошибка добавления",
                                    JOptionPane.ERROR_MESSAGE);

                        }

                    }
                }
                break;

                case "deleteCar": {
                    client.sendMessage("deleteCar");

                    Car car = new Car();
                    car.setBrand(objAdminForm.getBrand_cb().getSelectedItem().toString());
                    car.setSeries(objAdminForm.getSeries_cb().getSelectedItem().toString());
                    car.setConfiguration(objAdminForm.getConfiguration_cb().getSelectedItem().toString());

                    if (!"".equals(objAdminForm.getYear_cb().getSelectedItem().toString())) {
                        car.setYearCB(Integer.parseInt(objAdminForm.getYear_cb().getSelectedItem().toString()));
                    }

                    if (!"".equals(objAdminForm.getFrom_tf().getText())) {
                        car.setPriseFrom(Double.parseDouble(objAdminForm.getFrom_tf().getText()));
                    }

                    if (!"".equals(objAdminForm.getTo_tf().getText())) {
                        car.setPriseTo(Double.parseDouble(objAdminForm.getTo_tf().getText()));
                    }

                    client.sendObject(car);

                    if ("Ok".equals(client.readMessage())) {
                        JOptionPane.showMessageDialog(objCreateCarForm,
                                "              Автомобил(ь/и) удален(ы)!",
                                "Удаление автомобиля",
                                JOptionPane.PLAIN_MESSAGE);
                    }

                    objAdminForm.getBrand_cb().setSelectedItem("");
                    objAdminForm.getSeries_cb().setSelectedItem("");
                    objAdminForm.getConfiguration_cb().setSelectedItem("");

                    objAdminForm.getYear_cb().setSelectedItem("");
                    objAdminForm.getFrom_tf().setText("");
                    objAdminForm.getTo_tf().setText("");

                    client.sendMessage("createCarTable");
                    createAdminCarTable();

                    client.sendMessage("createCarTable");
                    createAdminCarStartTable();

                }
                break;
                case "editBrand": {
                    String brand = objAdminForm.getCorrectBrand_cb().getSelectedItem().toString();
                    String series = objAdminForm.getCorrectSeries_cb().getSelectedItem().toString();
                    String configuration = objAdminForm.getCorrectConfiguration_cb().getSelectedItem().toString();

                    if ("".equals(brand) || "".equals(series) || "".equals(configuration))
                        JOptionPane.showMessageDialog(objAdminForm,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);

                    else {
                        EditBrand form = new EditBrand();
                        form.setTitle(" Изменение бренда");
                        form.pack();
                        form.setLocationRelativeTo(null);
                        form.setVisible(true);

                        createBrandCB();
                    }

                }
                break;
                case "editSeries": {
                    String brand = objAdminForm.getCorrectBrand_cb().getSelectedItem().toString();
                    String series = objAdminForm.getCorrectSeries_cb().getSelectedItem().toString();
                    String configuration = objAdminForm.getCorrectConfiguration_cb().getSelectedItem().toString();

                    if ("".equals(brand) || "".equals(series) || "".equals(configuration))
                        JOptionPane.showMessageDialog(objAdminForm,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);

                    else {
                        EditSeries form = new EditSeries();
                        form.setTitle(" Изменение серии");
                        form.pack();
                        form.setLocationRelativeTo(null);
                        form.setVisible(true);

                        createSeriesCB();
                    }
                }
                break;
                case "editConfiguration": {
                    String brand = objAdminForm.getCorrectBrand_cb().getSelectedItem().toString();
                    String series = objAdminForm.getCorrectSeries_cb().getSelectedItem().toString();
                    String configuration = objAdminForm.getCorrectConfiguration_cb().getSelectedItem().toString();

                    if ("".equals(brand) || "".equals(series) || "".equals(configuration))
                        JOptionPane.showMessageDialog(objAdminForm,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);

                    else {
                        EditConfiguration form = new EditConfiguration();
                        form.setTitle(" Изменение комплектации");
                        form.pack();
                        form.setLocationRelativeTo(null);
                        form.setVisible(true);

                        createConfigurationCB();
                    }
                }
                break;
                case "editMotor": {
                    String brand = objAdminForm.getCorrectBrand_cb().getSelectedItem().toString();
                    String series = objAdminForm.getCorrectSeries_cb().getSelectedItem().toString();
                    String configuration = objAdminForm.getCorrectConfiguration_cb().getSelectedItem().toString();

                    if ("".equals(brand) || "".equals(series) || "".equals(configuration))
                        JOptionPane.showMessageDialog(objAdminForm,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);

                    else {
                        EditMotor form = new EditMotor();
                        form.setTitle("Изменение мотора");
                        form.pack();
                        form.setLocationRelativeTo(null);
                        form.setVisible(true);

                    }
                }
                break;
                case "editPrice": {
                    String brand = objAdminForm.getCorrectBrand_cb().getSelectedItem().toString();
                    String series = objAdminForm.getCorrectSeries_cb().getSelectedItem().toString();
                    String configuration = objAdminForm.getCorrectConfiguration_cb().getSelectedItem().toString();

                    if ("".equals(brand) || "".equals(series) || "".equals(configuration))
                        JOptionPane.showMessageDialog(objAdminForm,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);

                    else {
                        EditPrice form = new EditPrice();
                        form.setTitle("Изменение цены");
                        form.pack();
                        form.setLocationRelativeTo(null);
                        form.setVisible(true);

                    }
                }
                break;
                case "editCount": {
                    String brand = objAdminForm.getCorrectBrand_cb().getSelectedItem().toString();
                    String series = objAdminForm.getCorrectSeries_cb().getSelectedItem().toString();
                    String configuration = objAdminForm.getCorrectConfiguration_cb().getSelectedItem().toString();

                    if ("".equals(brand) || "".equals(series) || "".equals(configuration))
                        JOptionPane.showMessageDialog(objAdminForm,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);

                    else {
                        EditCount form = new EditCount();
                        form.setTitle("Изменение количества");
                        form.pack();
                        form.setLocationRelativeTo(null);
                        form.setVisible(true);

                    }
                }
                break;

                case "backFromEditBrand": {
                    objEditBrand.dispose();
                    buttonEdit();
                }
                break;
                case "backFromEditSeries": {
                    objEditSeries.dispose();
                    buttonEdit();

                }
                break;
                case "backFromEditConfiguration": {
                    objEditConfiguration.dispose();
                    buttonEdit();

                }
                break;
                case "backFromEditMotor": {
                    objEditMotor.dispose();
                    buttonEdit();
                }
                break;
                case "backFromEditPrice": {
                    objEditPrice.dispose();
                    buttonEdit();

                }
                break;
                case "backFromEditCount": {
                    objEditCount.dispose();
                    buttonEdit();

                }
                break;

                case "saveBrand": {

                    String brand = objAdminForm.getCorrectBrand_cb().getSelectedItem().toString();
                    String series = objAdminForm.getCorrectSeries_cb().getSelectedItem().toString();
                    String configuration = objAdminForm.getCorrectConfiguration_cb().getSelectedItem().toString();

                    if ("".equals(objEditBrand.getBrand_cb().getSelectedItem().toString())) {
                        JOptionPane.showMessageDialog(objEditBrand,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        client.sendMessage("editBrand");

                        Car car = new Car();
                        car.setBrand(brand);
                        car.setSeries(series);
                        car.setConfiguration(configuration);

                        client.sendObject(car);
                        client.sendMessage(objEditBrand.getBrand_cb().getSelectedItem().toString());

                        if ("Ok".equals(client.readMessage())) {
                            objEditBrand.dispose();

                            client.sendMessage("createCarTable");
                            createAdminCarTable();

                            client.sendMessage("createCarTable");
                            createAdminCarStartTable();

                            objAdminForm.getCorrectBrand_cb().setSelectedItem("");
                            buttonEdit();

                        }
                    }
                }
                break;
                case "saveConfiguration": {

                    String brand = objAdminForm.getCorrectBrand_cb().getSelectedItem().toString();
                    String series = objAdminForm.getCorrectSeries_cb().getSelectedItem().toString();
                    String configuration = objAdminForm.getCorrectConfiguration_cb().getSelectedItem().toString();

                    if ("".equals(objEditConfiguration.getConfiguration_cb().getSelectedItem().toString())) {
                        JOptionPane.showMessageDialog(objEditConfiguration,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        client.sendMessage("editConfiguration");

                        Car car = new Car();
                        car.setBrand(brand);
                        car.setSeries(series);
                        car.setConfiguration(configuration);

                        client.sendObject(car);
                        client.sendMessage(objEditConfiguration.getConfiguration_cb().getSelectedItem().toString());

                        if ("Ok".equals(client.readMessage())) {
                            objEditConfiguration.dispose();

                            client.sendMessage("createCarTable");
                            createAdminCarTable();

                            client.sendMessage("createCarTable");
                            createAdminCarStartTable();

                            objAdminForm.getCorrectBrand_cb().setSelectedItem("");
                            buttonEdit();

                        }
                    }
                }
                break;
                case "saveSeries": {
                    String brand = objAdminForm.getCorrectBrand_cb().getSelectedItem().toString();
                    String series = objAdminForm.getCorrectSeries_cb().getSelectedItem().toString();
                    String configuration = objAdminForm.getCorrectConfiguration_cb().getSelectedItem().toString();

                    if ("".equals(objEditSeries.getSeries_cb().getSelectedItem().toString())) {
                        JOptionPane.showMessageDialog(objEditSeries,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        client.sendMessage("editSeries");

                        Car car = new Car();
                        car.setBrand(brand);
                        car.setSeries(series);
                        car.setConfiguration(configuration);

                        client.sendObject(car);
                        client.sendMessage(objEditSeries.getSeries_cb().getSelectedItem().toString());

                        if ("Ok".equals(client.readMessage())) {
                            objEditSeries.dispose();

                            client.sendMessage("createCarTable");
                            createAdminCarTable();

                            client.sendMessage("createCarTable");
                            createAdminCarStartTable();

                            objAdminForm.getCorrectBrand_cb().setSelectedItem("");
                            buttonEdit();

                        }
                    }

                }
                break;
                case "saveMotor": {
                    String brand = objAdminForm.getCorrectBrand_cb().getSelectedItem().toString();
                    String series = objAdminForm.getCorrectSeries_cb().getSelectedItem().toString();
                    String configuration = objAdminForm.getCorrectConfiguration_cb().getSelectedItem().toString();

                    if ("".equals(objEditMotor.getMotor_tf().getText())) {
                        JOptionPane.showMessageDialog(objEditMotor,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        client.sendMessage("editMotor");

                        Car car = new Car();
                        car.setBrand(brand);
                        car.setSeries(series);
                        car.setConfiguration(configuration);

                        client.sendObject(car);
                        client.sendMessage(objEditMotor.getMotor_tf().getText());

                        if ("Ok".equals(client.readMessage())) {
                            objEditMotor.dispose();

                            JOptionPane.showMessageDialog(objAdminForm,
                                    "              Мотор изменён!",
                                    "Изменение мотора автомобиля",
                                    JOptionPane.PLAIN_MESSAGE);

                            client.sendMessage("createCarTable");
                            createAdminCarTable();

                            client.sendMessage("createCarTable");
                            createAdminCarStartTable();

                            objAdminForm.getCorrectBrand_cb().setSelectedItem("");
                            buttonEdit();

                        }
                    }

                }
                break;
                case "savePrice": {
                    String brand = objAdminForm.getCorrectBrand_cb().getSelectedItem().toString();
                    String series = objAdminForm.getCorrectSeries_cb().getSelectedItem().toString();
                    String configuration = objAdminForm.getCorrectConfiguration_cb().getSelectedItem().toString();

                    if ("".equals(objEditPrice.getPrice_tf().getText())) {
                        JOptionPane.showMessageDialog(objEditPrice,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        client.sendMessage("editPrice");

                        Car car = new Car();
                        car.setBrand(brand);
                        car.setSeries(series);
                        car.setConfiguration(configuration);

                        client.sendObject(car);
                        client.sendMessage(objEditPrice.getPrice_tf().getText());

                        if ("Ok".equals(client.readMessage())) {
                            objEditPrice.dispose();

                            client.sendMessage("createCarTable");
                            createAdminCarTable();

                            client.sendMessage("createCarTable");
                            createAdminCarStartTable();

                            objAdminForm.getCorrectBrand_cb().setSelectedItem("");
                            buttonEdit();

                        }
                    }

                }
                break;
                case "saveCount": {
                    String brand = objAdminForm.getCorrectBrand_cb().getSelectedItem().toString();
                    String series = objAdminForm.getCorrectSeries_cb().getSelectedItem().toString();
                    String configuration = objAdminForm.getCorrectConfiguration_cb().getSelectedItem().toString();

                    if ("".equals(objEditCount.getCount_tf().getText())) {
                        JOptionPane.showMessageDialog(objEditCount,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        client.sendMessage("editCount");

                        Car car = new Car();
                        car.setBrand(brand);
                        car.setSeries(series);
                        car.setConfiguration(configuration);

                        client.sendObject(car);
                        client.sendMessage(objEditCount.getCount_tf().getText());

                        if ("Ok".equals(client.readMessage())) {
                            objEditCount.dispose();

                            client.sendMessage("createCarTable");
                            createAdminCarTable();

                            client.sendMessage("createCarTable");
                            createAdminCarStartTable();

                            objAdminForm.getCorrectBrand_cb().setSelectedItem("");
                            buttonEdit();

                        }
                    }

                }
                break;
                case "showAdminContract": {
                    if ("".equals(objAdminForm.getContractNumber_cb().getSelectedItem().toString())) {
                        JOptionPane.showMessageDialog(objAdminForm,
                                "         Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        client.sendMessage("searchContract");
                        client.sendMessage(objAdminForm.getContractNumber_cb().getSelectedItem().toString());

                        if ("Found".equals(client.readMessage())) {
                            createAdminTableContract();
                            objAdminForm.getButtonDeleteContract().setEnabled(true);
                        } else
                            JOptionPane.showMessageDialog(objAdminForm,
                                    " По этому номеру ничего не найдено!",
                                    "Ошибка поиска",
                                    JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
                case "deleteContract": {
                    if ("".equals(objAdminForm.getContractNumber_cb().getSelectedItem().toString())) {
                        JOptionPane.showMessageDialog(objAdminForm,
                                " Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        client.sendMessage("returnCar");
                        client.sendMessage(objAdminForm.getContractNumber_cb().getSelectedItem().toString());

                        if ("OK".equals(client.readMessage()))
                            JOptionPane.showMessageDialog(objAdminForm,
                                    "           Договор удалён!",
                                    "Удаление договора",
                                    JOptionPane.PLAIN_MESSAGE);
                        else
                            JOptionPane.showMessageDialog(objAdminForm,
                                    " Ошибка удаления!",
                                    "Ошибка поиска",
                                    JOptionPane.ERROR_MESSAGE);

                        objAdminForm.getButtonDeleteContract().setEnabled(true);

                        objAdminForm.getContractNumber_cb().setSelectedItem("");

                        client.sendMessage("createCarTable");
                        createAdminCarStartTable();

                        client.sendMessage("createAllContract");
                        createAdminTableContract();

                        createContractNumberCB();

//                        client.sendMessage("diagram");
//                        createDiagram();

                    }
                }
                break;
                case "addContractShowPanel": {
                    CreateContract form = new CreateContract();
                    form.setTitle("Добавление контракта");
                    form.pack();
                    form.setLocationRelativeTo(null);
                    form.setVisible(true);

                    createBrandCBCreateContract();
                }
                break;
                case "addContract": {
                    if ("".equals(objCreateContract.getName_tf().getText()) ||
                            "".equals(objCreateContract.getSecondName_tf().getText()) ||
                            "".equals(objCreateContract.getPatronymic_tf().getText()) ||
                            "".equals(objCreateContract.getSeries_tf().getText()) ||
                            "".equals(objCreateContract.getNumber_tf().getText()) ||
                            "".equals(objCreateContract.getBuyBrand_cb().getSelectedItem().toString()) ||
                            "".equals(objCreateContract.getBuySeries_cb().getSelectedItem().toString()) ||
                            "".equals(objCreateContract.getBuyConfiguration_cb().getSelectedItem().toString()) ||
                            "".equals(objCreateContract.getNumberContract_tf().getText())) {
                        JOptionPane.showMessageDialog(objCreateContract,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        client.sendMessage("orderCarSeller");

                        Customer customer = new Customer();
                        customer.setName(objCreateContract.getName_tf().getText());
                        customer.setSecondName(objCreateContract.getSecondName_tf().getText());
                        customer.setMiddleName(objCreateContract.getPatronymic_tf().getText());
                        customer.setSeries(objCreateContract.getSeries_tf().getText());
                        customer.setNumber(objCreateContract.getNumber_tf().getText());

                        client.sendObject(customer);

                        Car car = new Car();
                        car.setBrand(objCreateContract.getBuyBrand_cb().getSelectedItem().toString());
                        car.setSeries(objCreateContract.getBuySeries_cb().getSelectedItem().toString());
                        car.setConfiguration(objCreateContract.getBuyConfiguration_cb().getSelectedItem().toString());

                        client.sendObject(car);
                        client.sendMessage(objCreateContract.getNumberContract_tf().getText());

                        String massage = client.readMessage();

                        if ("Successful insert".equals(massage)) {
                            JOptionPane.showMessageDialog(objCreateContract,
                                    "           Покупка оформлена!",
                                    "Покупка автомобиля",
                                    JOptionPane.PLAIN_MESSAGE);

                            objCreateContract.getName_tf().setText("");
                            objCreateContract.getSecondName_tf().setText("");
                            objCreateContract.getSeries_tf().setText("");
                            objCreateContract.getPatronymic_tf().setText("");
                            objCreateContract.getNumber_tf().setText("");
                            objCreateContract.getNumberContract_tf().setText("");

                            objCreateContract.getBuyBrand_cb().setSelectedItem("");

                            client.sendMessage("createCarTable");
                            createAdminCarStartTable();

                            client.sendMessage("createAllContract");
                            createAdminTableContract();

                            createContractNumberCB();

//                            client.sendMessage("diagram");
//                            createDiagram();
                            objAdminForm.getMonth_cb().setSelectedItem(" ");
                            objAdminForm.getGraficPane().removeAll();

                        } else
                            JOptionPane.showMessageDialog(objUsersForm,
                                    "Ошибка покупки!",
                                    "Покупка автомобиля",
                                    JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
                case "showCreateContractSeries": {

                    client.sendMessage("showSeries");
                    client.sendMessage(objCreateContract.getBuyBrand_cb().getSelectedItem().toString());

                    String[] series = (String[]) client.readObject();

                    if (objCreateContract.getBuySeries_cb().getItemCount() != 0) {
                        objCreateContract.getBuySeries_cb().removeAllItems();
                    }

                    for (int i = 0; i < series.length; i++) {
                        objCreateContract.getBuySeries_cb().addItem(series[i]);
                    }

                }
                break;
                case "showCreateContractConfiguration": {
                    client.sendMessage("showConfiguration");

                    client.sendMessage(objCreateContract.getBuyBrand_cb().getSelectedItem().toString());
                    if (objCreateContract.getBuySeries_cb().getItemCount() != 0) {
                        client.sendMessage(objCreateContract.getBuySeries_cb().getSelectedItem().toString());

                    } else {
                        client.sendMessage("");
                    }

                    String[] configuration = (String[]) client.readObject();

                    if (objCreateContract.getBuyConfiguration_cb().getItemCount() != 0)
                        objCreateContract.getBuyConfiguration_cb().removeAllItems();

                    for (int i = 0; i < configuration.length; i++) {
                        objCreateContract.getBuyConfiguration_cb().addItem(configuration[i]);
                    }

                }
                break;
                case "backFromCreateContract":{
                    objCreateContract.dispose();
                    objAdminForm.getMonth_cb().setSelectedItem(" ");
                    objAdminForm.getGraficPane().removeAll();
//                    client.sendMessage("diagram");
//                    createDiagram();
                    objAdminForm.getMonth_cb().setSelectedItem(" ");
                    objAdminForm.getGraficPane().removeAll();
                }break;

                case "editNewBrand":{
                    if("".equals(objAdminForm.getNewBrand_tf().getText())){
                        JOptionPane.showMessageDialog(objAdminForm,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        client.sendMessage("editNewBrand");

                        client.sendMessage(objAdminForm.getNewBrand_tf().getText());

                        if("Error".equals(client.readMessage())){
                            JOptionPane.showMessageDialog(objAdminForm,
                                    "Такой бренд уже существует!",
                                    "Ошибка ввода",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            if("insert".equals(client.readMessage())){
                                JOptionPane.showMessageDialog(objAdminForm,
                                        "           Бренд добавлен!",
                                        "Добавление бренда",
                                        JOptionPane.PLAIN_MESSAGE);
                            }

                            createBrandTable();
                        }
                    }
                }break;

                case "editNewSeries":{
                    if("".equals(objAdminForm.getNewSeries_tf().getText())){
                        JOptionPane.showMessageDialog(objAdminForm,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        client.sendMessage("editNewSeries");

                        client.sendMessage(objAdminForm.getNewSeries_tf().getText());

                        if("Error".equals(client.readMessage())){
                            JOptionPane.showMessageDialog(objAdminForm,
                                    "Такая серия автомобилей уже существует!",
                                    "Ошибка ввода",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            if("insert".equals(client.readMessage())){
                                JOptionPane.showMessageDialog(objAdminForm,
                                        "       Серия автомобиля добавлена!",
                                        "Добавление серии",
                                        JOptionPane.PLAIN_MESSAGE);
                            }

                            createSeriesTable();
                        }
                    }
                }break;

                case "editNewConfiguration":{
                    if("".equals(objAdminForm.getNewConfiguration_tf().getText())){
                        JOptionPane.showMessageDialog(objAdminForm,
                                "Заполните все поля!",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        client.sendMessage("editNewConfiguration");

                        client.sendMessage(objAdminForm.getNewConfiguration_tf().getText());

                        if("Error".equals(client.readMessage())){
                            JOptionPane.showMessageDialog(objAdminForm,
                                    "Такая комплектация автомобиля уже существует!",
                                    "Ошибка ввода",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            if("insert".equals(client.readMessage())){
                                JOptionPane.showMessageDialog(objAdminForm,
                                        "    Комплектация автомобиля добавлена!",
                                        "Добавление комплектации",
                                        JOptionPane.PLAIN_MESSAGE);
                            }

                            createConfigurationTable();
                        }
                    }
                }break;
                case "showDiagram":{
                    if(" ".equals(objAdminForm.getMonth_cb().getSelectedItem().toString())){

                    }
                    else{
                        client.sendMessage("diagram");
                        Date date = new Date();
                        switch (objAdminForm.getMonth_cb().getSelectedItem().toString()){
                            case "Январь": date.setMonth(1); break;
                            case "Февраль": date.setMonth(2); break;
                            case "Март": date.setMonth(3); break;
                            case "Апрель": date.setMonth(4); break;
                            case "Май": date.setMonth(5); break;
                            case "Июнь": date.setMonth(6); break;
                            case "Июль": date.setMonth(7); break;
                            case "Август": date.setMonth(8); break;
                            case "Сентябрь": date.setMonth(9); break;
                            case "Октябрь": date.setMonth(10); break;
                            case "Ноябрь": date.setMonth(11); break;
                            case "Декабрь": date.setMonth(12); break;
                            default: date.setMonth(0);
                        }

                            client.sendObject(date);

                    }
                }break;

                case "backFromSeller":{
                    objSellerForm.dispose();
                    EnterDialog dialog = new EnterDialog();
                    dialog.setTitle("Авторизация");
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);

                }


            }
        } catch (IOException ioException) {

            ioException.printStackTrace();
        }

    }


    public void authorization() {
        try {

            String msgLogin = objEnterDialog.getTextLogin().getText();
            String msgPassword = objEnterDialog.getPasswordField1().getText();
            if (msgLogin.equals("") || msgPassword.equals(""))
                JOptionPane.showMessageDialog(objEnterDialog,
                        "Заполните все поля!",
                        "Ошибка ввода",
                        JOptionPane.ERROR_MESSAGE);
            else {
                user = new User();

                user.setLogin(msgLogin);
                user.setPassword(msgPassword);

                client.sendMessage("enter");
                client.sendObject(user);

                String servMsg = client.readMessage();
                switch (servMsg) {
                    case "error": {
                        JOptionPane.showMessageDialog(objEnterDialog,
                                "Такой пользователь не зарегистрирован",
                                "Ошибка авторизации",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                    case "errorInput": {
                        JOptionPane.showMessageDialog(objEnterDialog,
                                "Проверьте введенные данные",
                                "Ошибка ввода",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                    case "ok":
                        String status = client.readMessage();
                        //user.
                        if (status.equals("admin")) {
                            objEnterDialog.setVisible(false);
                            AdminForm adminForm = new AdminForm();

                            adminForm.setTitle("Меню администратора");
                            adminForm.pack();
                            adminForm.setLocationRelativeTo(null);
                            adminForm.setVisible(true);

                            createAdminFormCB();

                            client.sendMessage("createCarTable");
                            createAdminCarStartTable();

                            client.sendMessage("createAllContract");
                            createAdminTableContract();

                            createContractNumberCB();

                            createBrandTable();
                            createSeriesTable();
                            createConfigurationTable();

                        }
                        if (status.equals("seller")) {
                            objEnterDialog.setVisible(false);
                            SellerForm sellerForm = new SellerForm();

                            sellerForm.setTitle("Меню продавца");
                            sellerForm.pack();
                            sellerForm.setLocationRelativeTo(null);
                            sellerForm.setVisible(true);

                            createSellerFormCB();

                            client.sendMessage("createCarTable");
                            createSellerCarTable();


                        }
                        if (status.equals("user")) {
                            objEnterDialog.setVisible(false);

                            UsersForm usersForm = new UsersForm();
                            usersForm.setTitle("Меню пользователя");
                            usersForm.pack();
                            usersForm.setLocationRelativeTo(null);
                            usersForm.setVisible(true);

                            createFormCB();

                            client.sendMessage("createCarTable");
                            createCarTable();

                            client.sendMessage("createTableUsersCars");
                            client.sendObject(user);
                            createTableUsersCars();
                        }
                        break;


                }
            }
        } catch (IOException error) {
            error.printStackTrace();
        }

    }

    public void createBrandCB() {

        client.sendMessage("createBrandCB");

        String[] brand = (String[]) client.readObject();

        for (int i = 0; i < brand.length; i++) {
            objEditBrand.getBrand_cb().addItem(brand[i]);
        }
    }

    public void createBrandTable() {

        int count=0;
        client.sendMessage("createBrandCB");

        String[] brand = (String[]) client.readObject();
        String[][] tableBrand = new String[brand.length-1][1];

        for (int i = 1; i < brand.length; i++) {
            tableBrand[count][0]=brand[i];
            count++;
        }

        String[] columnNames = {
                "Марка",
        };

        TableModel tableModel = new DefaultTableModel(tableBrand, columnNames);
        objAdminForm.getTableBrand().setModel(tableModel);

    }

    public void createBrandCBCreateContract() {

        client.sendMessage("createBrandCB");

        String[] brand = (String[]) client.readObject();

        for (int i = 0; i < brand.length; i++) {
            objCreateContract.getBuyBrand_cb().addItem(brand[i]);
        }
    }

    public void createSeriesCB() {

        client.sendMessage("createSeriesCB");

        String[] series = (String[]) client.readObject();

        for (int i = 0; i < series.length; i++) {
            objEditSeries.getSeries_cb().addItem(series[i]);
            //objUsersForm.getBuySeries_cb().addItem(series[i]);
        }
    }

    public void createSeriesTable() {

        int count=0;

        client.sendMessage("createSeriesCB");

        String[] series = (String[]) client.readObject();

        String[][] tableSeries = new String[series.length-1][1];

        for (int i = 1; i < series.length; i++) {
            tableSeries[count][0]=series[i];
            count++;
        }

        String[] columnNames = {
                "Серия",
        };

        TableModel tableModel = new DefaultTableModel(tableSeries, columnNames);
        objAdminForm.getTableSeries().setModel(tableModel);

    }

    public void createConfigurationCB() {
        client.sendMessage("createConfigurationCB");

        String[] configuration = (String[]) client.readObject();

        for (int i = 0; i < configuration.length; i++) {
            objEditConfiguration.getConfiguration_cb().addItem(configuration[i]);
        }
    }

    public void createConfigurationTable() {
        int count=0;

        client.sendMessage("createConfigurationCB");

        String[] configuration = (String[]) client.readObject();

        String[][] tableConfiguration = new String[configuration.length-1][1];

        for (int i = 1; i < configuration.length; i++) {
            tableConfiguration[count][0]=configuration[i];
            count++;
        }

        String[] columnNames = {
                "Комплектация",
        };

        TableModel tableModel = new DefaultTableModel(tableConfiguration, columnNames);
        objAdminForm.getTableConfiguration().setModel(tableModel);

    }

    public void createFormCB() {
        client.sendMessage("createFormCB");

        String[] brand = (String[]) client.readObject();
        String[] series = (String[]) client.readObject();
        String[] configuration = (String[]) client.readObject();


        //objUsersForm.getBrand_cb().removeAllItems();
        //objUsersForm.getBuyBrand_cb().removeAllItems();
        for (int i = 0; i < brand.length; i++) {
            objUsersForm.getBrand_cb().addItem(brand[i]);
            objUsersForm.getBuyBrand_cb().addItem(brand[i]);
        }

        objUsersForm.getBuySeries_cb().addItem("");
        objUsersForm.getSeries_cb().removeAllItems();
        for (int i = 0; i < series.length; i++) {
            objUsersForm.getSeries_cb().addItem(series[i]);
            //objUsersForm.getBuySeries_cb().addItem(series[i]);
        }

        objUsersForm.getConfiguration_cb().addItem("");
        objUsersForm.getConfiguration_cb().removeAllItems();
        for (int i = 0; i < configuration.length; i++) {
            objUsersForm.getConfiguration_cb().addItem(configuration[i]);
            //objUsersForm.getBuyConfiguration_cb().addItem(configuration[i]);
        }

        objUsersForm.getYear_cb().addItem("");
        for (int i = 30; i > 0; i--) {
            objUsersForm.getYear_cb().addItem(String.valueOf(1990 + i));
        }

    }

    public void createCarFormCB() {
        client.sendMessage("createFormCB");

        String[] brand = (String[]) client.readObject();
        String[] series = (String[]) client.readObject();
        String[] configuration = (String[]) client.readObject();

        objCreateCarForm.getBrand_cb().removeAllItems();
        for (int i = 0; i < brand.length; i++) {
            objCreateCarForm.getBrand_cb().addItem(brand[i]);
        }

        objCreateCarForm.getSeries_cb().removeAllItems();
        for (int i = 0; i < series.length; i++) {
            objCreateCarForm.getSeries_cb().addItem(series[i]);
        }

        objCreateCarForm.getConfiguration_cb().removeAllItems();
        for (int i = 0; i < configuration.length; i++) {
            objCreateCarForm.getConfiguration_cb().addItem(configuration[i]);
        }
    }

    public void createSellerFormCB() {
        client.sendMessage("createFormCB");

        String[] brand = (String[]) client.readObject();
        String[] series = (String[]) client.readObject();
        String[] configuration = (String[]) client.readObject();

        objSellerForm.getBrand_cb().removeAllItems();
        objSellerForm.getBuyBrand_cb().removeAllItems();
        for (int i = 0; i < brand.length; i++) {
            objSellerForm.getBrand_cb().addItem(brand[i]);
            objSellerForm.getBuyBrand_cb().addItem(brand[i]);
        }

        objSellerForm.getBuySeries_cb().addItem("");
        objSellerForm.getSeries_cb().removeAllItems();
        for (int i = 0; i < series.length; i++) {
            objSellerForm.getSeries_cb().addItem(series[i]);
            //objUsersForm.getBuySeries_cb().addItem(series[i]);
        }

        objSellerForm.getConfiguration_cb().addItem("");
        objSellerForm.getConfiguration_cb().removeAllItems();
        for (int i = 0; i < configuration.length; i++) {
            objSellerForm.getConfiguration_cb().addItem(configuration[i]);
            //objUsersForm.getBuyConfiguration_cb().addItem(configuration[i]);
        }

        objSellerForm.getYear_cb().addItem("");
        for (int i = 30; i > 0; i--) {
            objSellerForm.getYear_cb().addItem(String.valueOf(1990 + i));
        }

    }

    public void createAdminFormCB() {
        client.sendMessage("createFormCB");

        String[] brand = (String[]) client.readObject();
        String[] series = (String[]) client.readObject();
        String[] configuration = (String[]) client.readObject();

        objAdminForm.getBrand_cb().removeAllItems();
        objAdminForm.getCorrectBrand_cb().removeAllItems();
        objAdminForm.getBrandDoc_cb().removeAllItems();
        for (int i = 0; i < brand.length; i++) {
            objAdminForm.getBrand_cb().addItem(brand[i]);
            objAdminForm.getCorrectBrand_cb().addItem(brand[i]);
            objAdminForm.getBrandDoc_cb().addItem(brand[i]);
        }

        objAdminForm.getSeries_cb().removeAllItems();
        for (int i = 0; i < series.length; i++) {
            objAdminForm.getSeries_cb().addItem(series[i]);
        }

        objAdminForm.getConfiguration_cb().removeAllItems();
        for (int i = 0; i < configuration.length; i++) {
            objAdminForm.getConfiguration_cb().addItem(configuration[i]);

        }

        objAdminForm.getYear_cb().addItem("");
        for (int i = 30; i > 0; i--) {
            objAdminForm.getYear_cb().addItem(String.valueOf(1990 + i));
        }

        objAdminForm.getMonth_cb().removeAllItems();
        objAdminForm.getMonth_cb().addItem(" ");
        objAdminForm.getMonth_cb().addItem("Январь");
        objAdminForm.getMonth_cb().addItem("Февраль");
        objAdminForm.getMonth_cb().addItem("Март");
        objAdminForm.getMonth_cb().addItem("Апрель");
        objAdminForm.getMonth_cb().addItem("Май");
        objAdminForm.getMonth_cb().addItem("Июнь");
        objAdminForm.getMonth_cb().addItem("Июль");
        objAdminForm.getMonth_cb().addItem("Август");
        objAdminForm.getMonth_cb().addItem("Сентябрь");
        objAdminForm.getMonth_cb().addItem("Октябрь");
        objAdminForm.getMonth_cb().addItem("Ноябрь");
        objAdminForm.getMonth_cb().addItem("Декабрь");



    }

    public TableModel createColumnsCarTable() {
        String[][] carTable = (String[][]) client.readObject();
        String[] columnNames = {
                "Марка",
                "Серия",
                "Ком-ция",
                "Мотор",
                "Объём мотора",
                "Цена",
                "Кол-во",
                "Год",
                "Лошадиные силы",
        };

        TableModel tableModel = new DefaultTableModel(carTable, columnNames);

        return tableModel;
    }

    public void createCarTable() {

        TableModel tableModel = createColumnsCarTable();
        objUsersForm.getCarTable().setModel(tableModel);

    }

    public void createEditTable() {

        TableModel tableModel = createColumnsCarTable();
        objAdminForm.getTableEdit().setModel(tableModel);
    }


    public void createSellerCarTable() {

        TableModel tableModel = createColumnsCarTable();
        objSellerForm.getCarTable().setModel(tableModel);
    }

    public void createAdminCarStartTable() {
        TableModel tableModel = createColumnsCarTable();
        objAdminForm.getStartCarTable().setModel(tableModel);
    }

    public void createAdminCarTable() {
        TableModel tableModel = createColumnsCarTable();
        objAdminForm.getCarTable().setModel(tableModel);
    }

    public TableModel createColumnsContractTable() {
        String[][] contractTable = (String[][]) client.readObject();
        String[] columnNames = {                                                    //10
                "Номер договора",
                "Фамилия",
                "Имя",
                "Марка",
                "Серия",
                "Комплектация",
                "Мотор",
                "Цена",
                "Дата",
                "Кол-во",
        };

        TableModel tableModel = new DefaultTableModel(contractTable, columnNames);

        return tableModel;
    }

    public void createTableUsersCars() {

        TableModel tableModel = createColumnsContractTable();
        objUsersForm.getTableUserCars().setModel(tableModel);
    }

    public void createTableContract() {

        TableModel tableModel = createColumnsContractTable();
        objSellerForm.getTableContract().setModel(tableModel);
    }

    public void createAdminTableContract() {

        TableModel tableModel = createColumnsContractTable();
        objAdminForm.getTableContract().setModel(tableModel);
    }

    public void createContractNumberCB() {
        client.sendMessage("createContractNumber");

        String[] contractNumber = (String[]) client.readObject();

        objAdminForm.getContractNumber_cb().removeAllItems();
        for (int i = 0; i < contractNumber.length; i++) {
            objAdminForm.getContractNumber_cb().addItem(contractNumber[i]);

        }


    }

    private void buttonEdit(){
        objAdminForm.getEditBrand().setEnabled(false);
        objAdminForm.getEditSeries().setEnabled(false);
        objAdminForm.getEditConfiguration().setEnabled(false);
        objAdminForm.getEditMotor().setEnabled(false);
        objAdminForm.getEditCount().setEnabled(false);
        objAdminForm.getEditPrice().setEnabled(false);
    }

}
