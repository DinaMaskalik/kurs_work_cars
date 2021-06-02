package db.sql;

import db.ConnectionDB;
import model.User;

import java.util.ArrayList;

public class SQLUser {
    private static SQLUser instance;
    private ConnectionDB dbConnection;

    public SQLUser(){
        dbConnection = ConnectionDB.getInstance();
    }

//    private SQLUser() {
//        dbConnection = ConnectionDB.getInstance();
//    }


    public static synchronized SQLUser getInstance() {
        if (instance == null) {
            instance = new SQLUser();
        }
        return instance;
    }

    public String findUser(User obj) {
        String str = "SELECT id_users, login, password, status.status " +
                "From users " +
                "INNER JOIN status ON users.id_status = status.id_status "+
                "Where login = '" + obj.getLogin() +
                "' and password = '" + obj.getPassword() + "'";


        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        String status = "";
        if(result.size()==1){
            //User user = new User();
            for (String[] item: result) {
                status = item[3];
                obj.setIdUser(Integer.parseInt(item[0]));
            }
        }


        return status;
    }

    public void insert(User obj) {
        String str = "INSERT INTO users (login, password, id_status) VALUES('" + obj.getLogin()
                + "', '" + obj.getPassword() + "', '3')";
        dbConnection.execute(str);
    }
    

}
