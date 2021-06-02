package db.sql;

import db.ConnectionDB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SQLSeries {

    private static SQLSeries instance;
    private ConnectionDB dbConnection;

    public SQLSeries() {
        dbConnection = ConnectionDB.getInstance();
    }

    public String[] getAllSeries() {
        int i=1;
        String str = "SELECT series " +
                "From series";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        String[] series = new String[result.size()+1];

        series[0]="";
        for (String[] item : result) {
            series[i]=item[0];
            i++;
        }

        return series;
    }

    public String[] getSeriesForBuy (String brand){
        String[] masSeries;

        if(!"".equals(brand)) {
            int count = 1;

            String str = "SELECT brand.brand, series.series " +
                    "From ((car " +
                    "INNER JOIN brand ON car.id_brand=brand.id_brand)" +
                    "INNER JOIN series ON car.id_series=series.id_series)" +
                    "Where brand.brand=" + "'" + brand + "'";

            ArrayList<String[]> result = dbConnection.getArrayResult(str);

            Set<String> series = new HashSet<>();

            for (String[] item : result) {
                series.add(item[1]);
            }
            masSeries = new String[series.size()+1];

            masSeries[0] = "";
            for (String s : series) {

                masSeries[count] = s;
                count++;
            }
            return masSeries;
        }

        return masSeries = getAllSeries();
    }

    public int getIdSeries(String series) {
        int i = 0;
        String str = "SELECT id_series " +
                "From series " +
                "Where series='" + series + "'";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        for (String[] item : result) {
            i=Integer.parseInt(item[0]);
        }

        return i;
    }

    public void insert(String series){
        String str="INSERT INTO series (series)"+
                "VALUES ('" +series+"')";
        dbConnection.execute(str);
    }
}
