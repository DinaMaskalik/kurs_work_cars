package db.sql;

import db.ConnectionDB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SQLConfiguration {

    private static SQLConfiguration instance;
    private ConnectionDB dbConnection;

    public SQLConfiguration() {
        dbConnection = ConnectionDB.getInstance();
    }

    public String[] getAllConfiguration() {
        int i = 1;
        String str = "SELECT configuration " +
                "From configuration";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        String[] configuration = new String[result.size() + 1];

        configuration[0] = "";
        for (String[] item : result) {
            configuration[i] = item[0];
            i++;
        }

        return configuration;
    }

    public String[] getConfigurationForBuy(String brand, String series) {
        String[] masConfiguration;

        if (!"".equals(brand)) {
            int count = 1;

            String str = "SELECT brand.brand, series.series, configuration.configuration " +
                    "From (((car " +
                    "INNER JOIN brand ON car.id_brand=brand.id_brand)" +
                    "INNER JOIN series ON car.id_series=series.id_series)" +
                    "INNER JOIN configuration ON car.id_configuration=configuration.id_configuration) " +
                    "Where series.series=" + "'" + series + "' AND " +
                    "brand.brand=" + "'" + brand + "'";

            ArrayList<String[]> result = dbConnection.getArrayResult(str);

            Set<String> configuration = new HashSet<>();

            for (String[] item : result) {
                configuration.add(item[2]);
            }

            masConfiguration = new String[configuration.size() + 1];

            masConfiguration[0] = "";
            for (String s : configuration) {

                masConfiguration[count] = s;
                count++;
            }

            return masConfiguration;
        }

        return masConfiguration = getAllConfiguration();

    }

    public int getIdConfiguration(String configuration) {
        int i = 0;
        String str = "SELECT id_configuration " +
                "From configuration " +
                "Where configuration='" + configuration + "'";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        for (String[] item : result) {
            i=Integer.parseInt(item[0]);
        }

        return i;
    }

    public void insert(String configuration){
        String str="INSERT INTO configuration (configuration)"+
                "VALUES ('" +configuration+"')";
        dbConnection.execute(str);
    }

}
