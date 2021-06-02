package db.sql;

import db.ConnectionDB;
import model.Car;

import java.util.ArrayList;

public class SQLCar {

    private final String START_SQL = "SELECT brand.brand, series.series, configuration.configuration, motor, engine_capacity, prise, count, year, horsepower " +
            "From (((car " +
            "INNER JOIN brand ON car.id_brand=brand.id_brand)" +
            "INNER JOIN series ON car.id_series=series.id_series)" +
            "INNER JOIN configuration ON car.id_configuration=configuration.id_configuration) ";

    private static SQLCar instance;
    private ConnectionDB dbConnection;

    public SQLCar() {
        dbConnection = ConnectionDB.getInstance();
    }

    public ArrayList<String[]> selectAllCars() {

        String str = "SELECT brand.brand, series.series, configuration.configuration, motor, engine_capacity, prise, count, year, horsepower " +
                "From ((car " +
                "INNER JOIN brand ON car.id_brand=brand.id_brand)" +
                "INNER JOIN series ON car.id_series=series.id_series)" +
                "INNER JOIN configuration ON car.id_configuration=configuration.id_configuration";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        return result;
    }


    public ArrayList<String[]> findCars(Car car) {
        String str;
        ArrayList<String[]> result;

        if (car.getYearCB() == 0 && car.getPriseFrom() == -1 && car.getPriseTo() == -1) {

            if ("".equals(car.getBrand()) && "".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                return result = selectAllCars();
            }

            if ("".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                str = START_SQL +
                        "Where brand.brand=" + "'" + car.getBrand() + "'";

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getConfiguration()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "'";

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getSeries()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "'";

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "series.series=" + "'" + car.getSeries() + "'";

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getSeries())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "'";

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getConfiguration())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "'";

                return result = dbConnection.getArrayResult(str);
            }

            str = START_SQL +
                    "Where series.series=" + "'" + car.getSeries() + "' AND " +
                    "brand.brand=" + "'" + car.getBrand() + "' AND " +
                    "configuration.configuration=" + "'" + car.getConfiguration() + "'";

            return result = dbConnection.getArrayResult(str);
        }

        if (car.getYearCB() == 0 && car.getPriseFrom() == -1) {
            if ("".equals(car.getBrand()) && "".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                str = START_SQL +
                        "Where prise<=" + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                str = START_SQL +
                        "Where brand.brand=" + "'" + car.getBrand() + "' AND prise<=" + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getConfiguration()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "' AND prise<=" + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getSeries()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() +
                        "' AND prise<=" + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "series.series=" + "'" + car.getSeries() + "' AND prise<=" + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getSeries())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "' AND prise<=" + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getConfiguration())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "' AND prise<=" + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            str = START_SQL +
                    "Where series.series=" + "'" + car.getSeries() + "' AND " +
                    "brand.brand=" + "'" + car.getBrand() + "' AND " +
                    "configuration.configuration=" + "'" + car.getConfiguration() + "' AND prise<=" + car.getPriseTo();

            return result = dbConnection.getArrayResult(str);
        }

        if (car.getYearCB() == 0 && car.getPriseTo() == -1) {
            if ("".equals(car.getBrand()) && "".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                str = START_SQL +
                        "Where prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                str = START_SQL +
                        "Where brand.brand=" + "'" + car.getBrand() + "' AND prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getConfiguration()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "' AND prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getSeries()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() +
                        "' AND prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "series.series=" + "'" + car.getSeries() + "' AND prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getSeries())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "' AND prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getConfiguration())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "' AND prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }

            str = START_SQL +
                    "Where series.series=" + "'" + car.getSeries() + "' AND " +
                    "brand.brand=" + "'" + car.getBrand() + "' AND " +
                    "configuration.configuration=" + "'" + car.getConfiguration() + "' AND prise>=" + car.getPriseFrom();

            return result = dbConnection.getArrayResult(str);

        }

        if (car.getYearCB() == 0) {
            if ("".equals(car.getBrand()) && "".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                str = START_SQL +
                        "Where prise BETWEEN" + car.getPriseFrom() + " AND " + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                str = START_SQL +
                        "Where brand.brand=" + "'" + car.getBrand() + "' AND prise BETWEEN" + car.getPriseFrom() +
                        " AND " + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getConfiguration()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "' AND prise BETWEEN" + car.getPriseFrom() +
                        " AND " + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getSeries()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND prise BETWEEN" + car.getPriseFrom() +
                        " AND " + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "series.series=" + "'" + car.getSeries() + "' AND prise BETWEEN" + car.getPriseFrom() +
                        " AND " + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getSeries())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "' AND prise BETWEEN" + car.getPriseFrom() + " AND " + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getConfiguration())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "' AND prise BETWEEN" + car.getPriseFrom() +
                        " AND " + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            str = START_SQL +
                    "Where series.series=" + "'" + car.getSeries() + "' AND " +
                    "brand.brand=" + "'" + car.getBrand() + "' AND " +
                    "configuration.configuration=" + "'" + car.getConfiguration() + "' AND prise BETWEEN" + car.getPriseFrom() +
                    " AND " + car.getPriseTo();

            return result = dbConnection.getArrayResult(str);
        }

        if (car.getPriseFrom() == -1 && car.getPriseTo() == -1) {
            if ("".equals(car.getBrand()) && "".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                str = START_SQL +
                        "Where year=" + car.getYearCB();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                str = START_SQL +
                        "Where brand.brand=" + "'" + car.getBrand() + "' AND year=" + car.getYearCB();

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getConfiguration()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "' AND year=" + car.getYearCB();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getSeries()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND year=" + car.getYearCB();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "series.series=" + "'" + car.getSeries() + "' AND year=" + car.getYearCB();

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getSeries())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "' AND year=" + car.getYearCB();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getConfiguration())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "' AND year=" + car.getYearCB();

                return result = dbConnection.getArrayResult(str);
            }

            str = START_SQL +
                    "Where series.series=" + "'" + car.getSeries() + "' AND " +
                    "brand.brand=" + "'" + car.getBrand() + "' AND " +
                    "configuration.configuration=" + "'" + car.getConfiguration() + "' AND year=" + car.getYearCB();

            return result = dbConnection.getArrayResult(str);
        }

        if (car.getPriseFrom() == -1) {
            if ("".equals(car.getBrand()) && "".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                str = START_SQL +
                        "Where year=" + car.getYearCB() + " AND prise<=" + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                str = START_SQL +
                        "Where brand.brand=" + "'" + car.getBrand() + "' AND year=" + car.getYearCB() +
                        " AND prise<=" + car.getPriseTo();
                ;

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getConfiguration()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "' AND year=" + car.getYearCB() +
                        " AND prise<=" + car.getPriseTo();
                ;

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getSeries()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND year=" + car.getYearCB()
                        + " AND prise<=" + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "series.series=" + "'" + car.getSeries() + "' AND year=" + car.getYearCB()
                        + " AND prise<=" + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getSeries())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "' AND year=" + car.getYearCB()
                        + " AND prise<=" + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getConfiguration())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "' AND year=" + car.getYearCB()
                        + " AND prise<=" + car.getPriseTo();

                return result = dbConnection.getArrayResult(str);
            }

            str = START_SQL +
                    "Where series.series=" + "'" + car.getSeries() + "' AND " +
                    "brand.brand=" + "'" + car.getBrand() + "' AND " +
                    "configuration.configuration=" + "'" + car.getConfiguration() + "' AND year=" + car.getYearCB()
                    + " AND prise<=" + car.getPriseTo();

            return result = dbConnection.getArrayResult(str);
        }

        if (car.getPriseTo() == -1) {
            if ("".equals(car.getBrand()) && "".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                str = START_SQL +
                        "Where year=" + car.getYearCB() + " AND prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
                str = START_SQL +
                        "Where brand.brand=" + "'" + car.getBrand() + "' AND year=" + car.getYearCB() +
                        " AND prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getConfiguration()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "' AND year=" + car.getYearCB() +
                        " AND prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getSeries()) && "".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND year=" + car.getYearCB()
                        + " AND prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getBrand())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "series.series=" + "'" + car.getSeries() + "' AND year=" + car.getYearCB()
                        + " AND prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }
            if ("".equals(car.getSeries())) {
                str = START_SQL +
                        "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "' AND year=" + car.getYearCB()
                        + " AND prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }

            if ("".equals(car.getConfiguration())) {
                str = START_SQL +
                        "Where series.series=" + "'" + car.getSeries() + "' AND " +
                        "brand.brand=" + "'" + car.getBrand() + "' AND year=" + car.getYearCB()
                        + " AND prise>=" + car.getPriseFrom();

                return result = dbConnection.getArrayResult(str);
            }

            str = START_SQL +
                    "Where series.series=" + "'" + car.getSeries() + "' AND " +
                    "brand.brand=" + "'" + car.getBrand() + "' AND " +
                    "configuration.configuration=" + "'" + car.getConfiguration() + "' AND year=" + car.getYearCB()
                    + " AND prise>=" + car.getPriseFrom();

            return result = dbConnection.getArrayResult(str);
        }


        if ("".equals(car.getBrand()) && "".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
            str = START_SQL +
                    "Where year=" + car.getYearCB() + " AND  prise BETWEEN" + car.getPriseFrom() +
                    " AND " + car.getPriseTo();

            return result = dbConnection.getArrayResult(str);
        }

        if ("".equals(car.getConfiguration()) && "".equals(car.getSeries())) {
            str = START_SQL +
                    "Where brand.brand=" + "'" + car.getBrand() + "' AND year=" + car.getYearCB() +
                    " AND  prise BETWEEN" + car.getPriseFrom() + " AND " + car.getPriseTo();

            return result = dbConnection.getArrayResult(str);
        }

        if ("".equals(car.getConfiguration()) && "".equals(car.getBrand())) {
            str = START_SQL +
                    "Where series.series=" + "'" + car.getSeries() + "' AND year=" + car.getYearCB() +
                    " AND  prise BETWEEN" + car.getPriseFrom() + " AND " + car.getPriseTo();

            return result = dbConnection.getArrayResult(str);
        }

        if ("".equals(car.getSeries()) && "".equals(car.getBrand())) {
            str = START_SQL +
                    "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND year=" + car.getYearCB()
                    + " AND  prise BETWEEN" + car.getPriseFrom() + " AND " + car.getPriseTo();

            return result = dbConnection.getArrayResult(str);
        }

        if ("".equals(car.getBrand())) {
            str = START_SQL +
                    "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                    "series.series=" + "'" + car.getSeries() + "' AND year=" + car.getYearCB()
                    + " AND  prise BETWEEN" + car.getPriseFrom() + " AND " + car.getPriseTo();

            return result = dbConnection.getArrayResult(str);
        }

        if ("".equals(car.getSeries())) {
            str = START_SQL +
                    "Where configuration.configuration=" + "'" + car.getConfiguration() + "' AND " +
                    "brand.brand=" + "'" + car.getBrand() + "' AND year=" + car.getYearCB()
                    + " AND  prise BETWEEN" + car.getPriseFrom() + " AND " + car.getPriseTo();

            return result = dbConnection.getArrayResult(str);
        }

        if ("".equals(car.getConfiguration())) {
            str = START_SQL +
                    "Where series.series=" + "'" + car.getSeries() + "' AND " +
                    "brand.brand=" + "'" + car.getBrand() + "' AND year=" + car.getYearCB()
                    + " AND  prise BETWEEN" + car.getPriseFrom() + " AND " + car.getPriseTo();

            return result = dbConnection.getArrayResult(str);
        }

        str = START_SQL +
                "Where series.series=" + "'" + car.getSeries() + "' AND " +
                "brand.brand=" + "'" + car.getBrand() + "' AND " +
                "configuration.configuration=" + "'" + car.getConfiguration() + "' AND year=" + car.getYearCB()
                + " AND  prise BETWEEN" + car.getPriseFrom() + " AND " + car.getPriseTo();

        return result = dbConnection.getArrayResult(str);

    }


    public String findIdCar(Car car) {
        String idCar = "";
        String str = "SELECT id_car " +
                "From (((car " +
                "INNER JOIN brand ON car.id_brand=brand.id_brand)" +
                "INNER JOIN series ON car.id_series=series.id_series)" +
                "INNER JOIN configuration ON car.id_configuration=configuration.id_configuration) " +
                "Where series.series=" + "'" + car.getSeries() + "' AND " +
                "brand.brand=" + "'" + car.getBrand() + "' AND " +
                "configuration.configuration=" + "'" + car.getConfiguration() + "'";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        for (String[] item : result) {
            idCar = item[0];
        }
        return idCar;

    }


    public int selectCarCount(String idCar) {
        int count = 0;

        String str = "SELECT count " +
                "From car " +
                "Where id_car='" + idCar + "'";

        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        for (String[] item : result) {
            count = Integer.parseInt(item[0]);
        }

        return count;
    }

    public void updateCount(int idCar, int newCount) {
        String str = "UPDATE car SET car.count=" + newCount
                + "  WHERE car.id_car=" + idCar;
        dbConnection.execute(str);
    }

    public void updateBrand(int idCar, int newBrand) {
        String str = "UPDATE car SET car.id_brand=" + newBrand
                + "  WHERE car.id_car=" + idCar;
        dbConnection.execute(str);
    }

    public void updateSeries(int idCar, int newSeries) {
        String str = "UPDATE car SET car.id_series=" + newSeries
                + "  WHERE car.id_car=" + idCar;
        dbConnection.execute(str);
    }

    public void updateConfiguration(int idCar, int newConfiguration) {
        String str = "UPDATE car SET car.id_configuration=" + newConfiguration
                + "  WHERE car.id_car=" + idCar;
        dbConnection.execute(str);
    }

    public void updateMotor(int idCar, String newMotor) {
        String str = "UPDATE car SET car.motor='" + newMotor + "'"
                + "  WHERE car.id_car=" + idCar;
        dbConnection.execute(str);
    }

    public void updatePrice(int idCar, double newPrice) {
        String str = "UPDATE car SET car.prise=" + newPrice
                + "  WHERE car.id_car=" + idCar;
        dbConnection.execute(str);
    }

    public void insert(Car car) {
        String str = "INSERT INTO car (id_brand, id_series, id_configuration, motor, engine_capacity, prise, count, year, horsepower)" +
                "VALUES ('" + car.getIdBrand() + "', '" + car.getIdSeries() + "', '" +
                car.getIdConfiguration() + "', '" + car.getMotor() + "', '" + car.getEngine() + "', '" + car.getPrice() + "', '" +
                car.getCount() + "', '" + car.getYearCB() + "', '" + car.getHorsepower() + "')";
        dbConnection.execute(str);
    }

    public void delete(int idCar) {
        String str = "DELETE FROM car WHERE id_car = " + idCar;
        dbConnection.execute(str);
    }

}
