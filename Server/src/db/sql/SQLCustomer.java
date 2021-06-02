package db.sql;

import db.ConnectionDB;
import model.Customer;

import java.util.ArrayList;

public class SQLCustomer {
    private static SQLCar instance;
    private ConnectionDB dbConnection;

    public SQLCustomer() {
        dbConnection = ConnectionDB.getInstance();
    }

    public void insert(Customer customer){
        String str="INSERT INTO customer (first_name, second_name, middle_name, passport_series, passport_number)"+
                "VALUES ('" +customer.getName()+"', '"+ customer.getSecondName()+"', '"+
                customer.getMiddleName()+"', '"+ customer.getSeries()+"', '"+ customer.getNumber()+"')";
        dbConnection.execute(str);
    }

    public String findIdCustomer(Customer customer){
        String idCustomer="";
        String str = "SELECT id_customer "+
                "From customer "+
                "Where first_name='" +customer.getName()+"' AND second_name='"+ customer.getSecondName()+
                "' AND middle_name= '"+ customer.getMiddleName()+"' AND passport_series='"+ customer.getSeries()+
                "' AND passport_number='"+ customer.getNumber()+"'";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        for (String[] item : result) {
            idCustomer=item[0];
        }
        return idCustomer;
    }
}
