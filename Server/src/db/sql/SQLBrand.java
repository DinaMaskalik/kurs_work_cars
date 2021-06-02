package db.sql;

import db.ConnectionDB;
import model.Customer;
import model.User;

import java.util.ArrayList;

public class SQLBrand {
    private static SQLBrand instance;
    private ConnectionDB dbConnection;

    public SQLBrand() {
        dbConnection = ConnectionDB.getInstance();
    }

    public String[] getAllBrand() {
        int i = 1;
        String str = "SELECT brand " +
                "From brand";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        String[] brand = new String[result.size() + 1];

        brand[0] = "";
        for (String[] item : result) {
            brand[i] = item[0];
            i++;
        }
        return brand;
    }

    public int getIdBrand(String brand) {
        int i = 0;
        String str = "SELECT id_brand " +
                "From brand " +
                "Where brand='" + brand + "'";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        for (String[] item : result) {
            i=Integer.parseInt(item[0]);
        }

        return i;
    }


    public void insert(String brand){
        String str="INSERT INTO brand (brand)"+
                "VALUES ('" +brand+"')";
        dbConnection.execute(str);
    }
}
