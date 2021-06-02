package db.sql;

import db.ConnectionDB;
import model.Contract;
import model.User;

import java.util.ArrayList;

public class SQLContract {
    private static SQLCar instance;
    private ConnectionDB dbConnection;

    public SQLContract() {
        dbConnection = ConnectionDB.getInstance();
    }

    public void insert(Contract contract) {
        String str = "INSERT INTO contract (id_users, id_customer, id_car, id_date, number_contract, buyCount)" +
                "VALUES ('" + contract.getIdUser() + "', '" + contract.getIdCustomer() + "', '" +
                contract.getIdCar() + "', '" + contract.getIdDate() + "', '" + contract.getNumberContract() + "', '"+contract.getCount()+"')";
        dbConnection.execute(str);
    }

    public ArrayList<String[]> getAllContract() {
        String str = "SELECT number_contract, customer.first_name, customer.second_name, brand.brand, series.series, configuration.configuration, motor, prise, date.day, date.month, date.year, buyCount " +
                "From ((((((contract " +
                "INNER JOIN car ON contract.id_car=car.id_car)" +
                "INNER JOIN brand ON car.id_brand=brand.id_brand)" +
                "INNER JOIN series ON car.id_series=series.id_series)" +
                "INNER JOIN configuration ON car.id_configuration=configuration.id_configuration)" +
                "INNER JOIN date ON contract.id_date=date.id_date)" +
                "INNER JOIN customer ON contract.id_customer=customer.id_customer)" +
                "INNER JOIN users ON contract.id_users=users.id_users";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        return result;
    }

    public ArrayList<String[]> getAllContractNumber() {
        String str = "SELECT number_contract " +
                "From contract ";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        return result;
    }

    public ArrayList<String[]> getUserContract(User user) {
        String str = "SELECT number_contract, customer.first_name, customer.second_name, brand.brand, series.series, configuration.configuration, motor, prise, date.day, date.month, date.year, buyCount " +
                "From (((((((contract " +
                "INNER JOIN car ON contract.id_car=car.id_car)" +
                "INNER JOIN brand ON car.id_brand=brand.id_brand)" +
                "INNER JOIN series ON car.id_series=series.id_series)" +
                "INNER JOIN configuration ON car.id_configuration=configuration.id_configuration)" +
                "INNER JOIN date ON contract.id_date=date.id_date)" +
                "INNER JOIN customer ON contract.id_customer=customer.id_customer)" +
                "INNER JOIN users ON contract.id_users=users.id_users) " +
                "Where users.id_users='" + user.getIdUser() + "'";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        return result;
    }

    public ArrayList<String[]> getContractByNumber(String contractNumber) {
        String str = "SELECT number_contract, customer.first_name, customer.second_name, brand.brand, series.series, configuration.configuration, motor, prise, date.day, date.month, date.year, buyCount " +
                "From (((((((contract " +
                "INNER JOIN car ON contract.id_car=car.id_car)" +
                "INNER JOIN brand ON car.id_brand=brand.id_brand)" +
                "INNER JOIN series ON car.id_series=series.id_series)" +
                "INNER JOIN configuration ON car.id_configuration=configuration.id_configuration)" +
                "INNER JOIN date ON contract.id_date=date.id_date)" +
                "INNER JOIN customer ON contract.id_customer=customer.id_customer)" +
                "INNER JOIN users ON contract.id_users=users.id_users) " +
                "Where number_contract='" + contractNumber + "'";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        return result;

    }

    public void delete(String contractNumber) {
        String str = "DELETE FROM contract WHERE number_contract = '" + contractNumber+"'";
        dbConnection.execute(str);
    }

    public ArrayList<String[]> getContractForDiagram(int month) {
        String str = "SELECT brand.brand " +
                "From (((((((contract " +
                "INNER JOIN car ON contract.id_car=car.id_car)" +
                "INNER JOIN brand ON car.id_brand=brand.id_brand)" +
                "INNER JOIN series ON car.id_series=series.id_series)" +
                "INNER JOIN configuration ON car.id_configuration=configuration.id_configuration)" +
                "INNER JOIN date ON contract.id_date=date.id_date)" +
                "INNER JOIN customer ON contract.id_customer=customer.id_customer)" +
                "INNER JOIN users ON contract.id_users=users.id_users) " +
                "Where date.month=" + month;

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        return result;

    }

}
