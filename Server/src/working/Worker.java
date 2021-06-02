package working;

import db.sql.*;
import model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Worker implements Runnable {

    protected Socket clientSocket = null;
    ObjectInputStream sois;
    ObjectOutputStream soos;
    User user;

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            sois = new ObjectInputStream(clientSocket.getInputStream());
            soos = new ObjectOutputStream(clientSocket.getOutputStream());

            while (true) {

                String choice = null;
                choice = sois.readObject().toString();

                switch (choice) {
                    case "enter":
                        user = (User) sois.readObject();
                        SQLUser sqlUser = new SQLUser();

                        //SQLFactory sqlFactory = new SQLFactory();
                        String status = sqlUser.findUser(user);
                        if (status == "")
                            soos.writeObject("error");
                        else {
                            soos.writeObject("ok");
                            soos.writeObject(status);

                        }
                        break;

                    case "registrationUser": {
                        System.out.println("Запрос к БД на проверку пользователя(таблица User), клиент: " + clientSocket.getInetAddress().toString());
                        User user = (User) sois.readObject();
                        SQLUser sqlUser1 = new SQLUser();

                        if (sqlUser1.findUser(user).equals("")) {
                            soos.writeObject("ok");
                            sqlUser1.insert(user);
                        } else {
                            soos.writeObject("This user is already existed");
                        }
                    }
                    break;
                    case "createFormCB": {
                        SQLBrand sqlBrand = new SQLBrand();
                        String[] brand = sqlBrand.getAllBrand();
                        soos.writeObject(brand);

                        SQLSeries sqlSeries = new SQLSeries();
                        String[] series = sqlSeries.getAllSeries();
                        soos.writeObject(series);

                        SQLConfiguration sqlConfiguration = new SQLConfiguration();
                        String[] configuration = sqlConfiguration.getAllConfiguration();
                        soos.writeObject(configuration);
                    }
                    break;
                    case "createCarTable": {
                        int count = 0;
                        SQLCar sqlCar = new SQLCar();
                        ArrayList<String[]> table = sqlCar.selectAllCars();

                        String[][] carTable = new String[table.size()][9];

                        for (String[] item : table) {
                            carTable[count][0] = item[0];
                            carTable[count][1] = item[1];
                            carTable[count][2] = item[2];
                            carTable[count][3] = item[3];
                            carTable[count][4] = item[4];
                            carTable[count][5] = item[5];
                            carTable[count][6] = item[6];
                            carTable[count][7] = item[7];
                            carTable[count][8] = item[8];
                            count++;
                        }

                        soos.writeObject(carTable);
                        break;
                    }

                    case "showSearchCars": {
                        int count = 0;

                        Car car = (Car) sois.readObject();

                        SQLCar sqlCar = new SQLCar();
                        ArrayList<String[]> table = sqlCar.findCars(car);

                        String[][] carTable = new String[table.size()][9];

                        for (String[] item : table) {
                            carTable[count][0] = item[0];
                            carTable[count][1] = item[1];
                            carTable[count][2] = item[2];
                            carTable[count][3] = item[3];
                            carTable[count][4] = item[4];
                            carTable[count][5] = item[5];
                            carTable[count][6] = item[6];
                            carTable[count][7] = item[7];
                            carTable[count][8] = item[8];
                            count++;
                        }


                        soos.writeObject(carTable);
                        break;
                    }
                    case "showSeries": {
                        String brand = sois.readObject().toString();

                        SQLSeries sqlSeries = new SQLSeries();
                        String[] series = sqlSeries.getSeriesForBuy(brand);

                        soos.writeObject(series);
                    }
                    break;
                    case "showConfiguration": {
                        String brand = sois.readObject().toString();
                        String series = sois.readObject().toString();

                        SQLConfiguration sqlConfiguration = new SQLConfiguration();

                        String[] configuration = sqlConfiguration.getConfigurationForBuy(brand, series);

                        soos.writeObject(configuration);
                    }
                    break;
                    case "buyCar": {

                        Contract contract = new Contract();
                        Customer customer = (Customer) sois.readObject();
                        Car car = (Car) sois.readObject();

                        SQLCar sqlCar = new SQLCar();
                        String idCar = sqlCar.findIdCar(car);

                        SQLCustomer sqlCustomer = new SQLCustomer();
                        sqlCustomer.insert(customer);
                        String idCustomer = sqlCustomer.findIdCustomer(customer);

                        contract.setIdUser(String.valueOf(user.getIdUser()));
                        contract.setIdCustomer(idCustomer);
                        contract.setIdCar(idCar);
                        contract.setIdDate("1");                                    //сделать правильно
                        contract.setNumberContract("TFHJ675SDHG87");                //сделать рандом
                        contract.setCount("1");

                        int carCount = sqlCar.selectCarCount(idCar);
                        int newCarCount = carCount - 1;
                        sqlCar.updateCount(Integer.parseInt(idCar), newCarCount);

                        SQLContract sqlContract = new SQLContract();
                        sqlContract.insert(contract);

                        soos.writeObject("Successful insert");                      //подумать

                    }
                    break;

                    case "createTableUsersCars": {
                        int count = 0;

                        User user = (User) sois.readObject();
                        SQLContract sqlContract = new SQLContract();

                        ArrayList<String[]> result = sqlContract.getUserContract(user);

                        String[][] table = new String[result.size()][10];

                        for (String[] item : result) {
                            table[count][0] = item[0];
                            table[count][1] = item[2];
                            table[count][2] = item[1];
                            table[count][3] = item[3];
                            table[count][4] = item[4];
                            table[count][5] = item[5];
                            table[count][6] = item[6];
                            table[count][7] = item[7];
                            String date = item[8] + "." + item[9] + "." + item[10];
                            table[count][8] = date;
                            table[count][9] = item[11];
                            count++;
                        }


                        soos.writeObject(table);

                    }
                    break;
                    case "orderCarSeller": {
                        Contract contract = new Contract();
                        Customer customer = (Customer) sois.readObject();
                        Car car = (Car) sois.readObject();
                        String contractNumber = (String) sois.readObject();

                        SQLCar sqlCar = new SQLCar();
                        String idCar = sqlCar.findIdCar(car);

                        SQLCustomer sqlCustomer = new SQLCustomer();
                        sqlCustomer.insert(customer);
                        String idCustomer = sqlCustomer.findIdCustomer(customer);

                        contract.setIdUser(String.valueOf(user.getIdUser()));
                        contract.setIdCustomer(idCustomer);
                        contract.setIdCar(idCar);
                        contract.setIdDate("1");                                             //сделать правильно
                        contract.setNumberContract(contractNumber);                          //сделать рандом
                        contract.setCount("1");

                        int carCount = sqlCar.selectCarCount(idCar);
                        int newCarCount = carCount - 1;
                        sqlCar.updateCount(Integer.parseInt(idCar), newCarCount);

                        SQLContract sqlContract = new SQLContract();
                        sqlContract.insert(contract);

                        soos.writeObject("Successful insert");                               //подумать

                    }
                    break;

                    case "searchContract": {
                        int count = 0;
                        String contractNumber = (String) sois.readObject();

                        SQLContract sqlContract = new SQLContract();
                        ArrayList<String[]> contract = sqlContract.getContractByNumber(contractNumber);
                        String[][] table = new String[contract.size()][10];


                        if (contract.size() == 0) {
                            soos.writeObject("Contract not found");
                        } else {
                            soos.writeObject("Found");
                            for (String[] item : contract) {
                                table[count][0] = item[0];
                                table[count][1] = item[2];
                                table[count][2] = item[1];
                                table[count][3] = item[3];
                                table[count][4] = item[4];
                                table[count][5] = item[5];
                                table[count][6] = item[6];
                                table[count][7] = item[7];
                                String date = item[8] + "." + item[9] + "." + item[10];
                                table[count][8] = date;
                                table[count][9] = item[11];
                                count++;
                            }

                            soos.writeObject(table);
                        }
                    }
                    break;
                    case "createAllContract": {
                        int count = 0;

                        SQLContract sqlContract = new SQLContract();
                        ArrayList<String[]> contract = sqlContract.getAllContract();
                        String[][] table = new String[contract.size()][10];

                        for (String[] item : contract) {
                            table[count][0] = item[0];
                            table[count][1] = item[2];
                            table[count][2] = item[1];
                            table[count][3] = item[3];
                            table[count][4] = item[4];
                            table[count][5] = item[5];
                            table[count][6] = item[6];
                            table[count][7] = item[7];
                            String date = item[8] + "." + item[9] + "." + item[10];
                            table[count][8] = date;
                            table[count][9] = item[11];
                            count++;
                        }
                        soos.writeObject(table);
                    }
                    break;

                    case "returnCar": {
                        String contractNumber = (String) sois.readObject();

                        SQLContract sqlContract = new SQLContract();
                        ArrayList<String[]> contract = sqlContract.getContractByNumber(contractNumber);
                        Car car = new Car();
                        for (String[] item : contract) {
                            car.setBrand(item[3]);
                            car.setSeries(item[4]);
                            car.setConfiguration(item[5]);
                        }

                        SQLCar sqlCar = new SQLCar();
                        String idCar = sqlCar.findIdCar(car);


                        int carCount = sqlCar.selectCarCount(idCar);
                        int newCarCount = carCount + 1;
                        sqlCar.updateCount(Integer.parseInt(idCar), newCarCount);

                        sqlContract.delete(contractNumber);

                        ArrayList<String[]> result = sqlContract.getContractByNumber(contractNumber);

                        if (result.size() == 0)
                            soos.writeObject("OK");
                        else
                            soos.writeObject("Error");
                    }
                    break;

                    case "saveCar": {
                        Car car = (Car) sois.readObject();
                        SQLCar sqlCar = new SQLCar();

                        ArrayList<String[]> result = sqlCar.findCars(car);

                        if (result.size() == 0) {
                            SQLBrand sqlBrand = new SQLBrand();
                            car.setIdBrand(sqlBrand.getIdBrand(car.getBrand()));

                            SQLSeries sqlSeries = new SQLSeries();
                            car.setIdSeries(sqlSeries.getIdSeries(car.getSeries()));

                            SQLConfiguration sqlConfiguration = new SQLConfiguration();
                            car.setIdConfiguration(sqlConfiguration.getIdConfiguration(car.getConfiguration()));

                            sqlCar.insert(car);

                            soos.writeObject("OK");
                        } else {
                            soos.writeObject("Error");
                        }

                    }
                    break;

                    case "deleteCar": {
                        int idCar = 0;
                        Car car = (Car) sois.readObject();

                        SQLCar sqlCar = new SQLCar();
                        ArrayList<String[]> delete = sqlCar.findCars(car);
                        for (String[] item : delete) {
                            car.setBrand(item[0]);
                            car.setSeries(item[1]);
                            car.setConfiguration(item[2]);
                            idCar = Integer.parseInt(sqlCar.findIdCar(car));
                            sqlCar.delete(idCar);
                        }

                        soos.writeObject("Ok");

                    }
                    break;

                    case "editBrand": {
                        Car car = (Car) sois.readObject();
                        String newBrand = (String) sois.readObject();

                        SQLBrand sqlBrand = new SQLBrand();
                        int idBrand = sqlBrand.getIdBrand(newBrand);

                        SQLCar sqlCar = new SQLCar();
                        int idCar = Integer.parseInt(sqlCar.findIdCar(car));
                        sqlCar.updateBrand(idCar, idBrand);

                        soos.writeObject("Ok");

                    }
                    break;

                    case "editSeries": {
                        Car car = (Car) sois.readObject();
                        String newSeries = (String) sois.readObject();

                        SQLSeries sqlSeries = new SQLSeries();
                        int idSeries = sqlSeries.getIdSeries(newSeries);

                        SQLCar sqlCar = new SQLCar();
                        int idCar = Integer.parseInt(sqlCar.findIdCar(car));
                        sqlCar.updateSeries(idCar, idSeries);

                        soos.writeObject("Ok");
                    }
                    break;
                    case "editConfiguration": {
                        Car car = (Car) sois.readObject();
                        String newConfiguration = (String) sois.readObject();

                        SQLConfiguration sqlConfiguration = new SQLConfiguration();
                        int idConfiguration = sqlConfiguration.getIdConfiguration(newConfiguration);

                        SQLCar sqlCar = new SQLCar();
                        int idCar = Integer.parseInt(sqlCar.findIdCar(car));
                        sqlCar.updateConfiguration(idCar, idConfiguration);

                        soos.writeObject("Ok");
                    }
                    break;
                    case "editMotor": {
                        Car car = (Car) sois.readObject();
                        String newMotor = (String) sois.readObject();

                        SQLCar sqlCar = new SQLCar();
                        int idCar = Integer.parseInt(sqlCar.findIdCar(car));
                        sqlCar.updateMotor(idCar, newMotor);

                        soos.writeObject("Ok");
                    }
                    break;

                    case "editPrice": {
                        Car car = (Car) sois.readObject();
                        String newPrice = (String) sois.readObject();

                        SQLCar sqlCar = new SQLCar();
                        int idCar = Integer.parseInt(sqlCar.findIdCar(car));
                        sqlCar.updatePrice(idCar, Double.parseDouble(newPrice));

                        soos.writeObject("Ok");
                    }
                    break;

                    case "editCount": {
                        Car car = (Car) sois.readObject();
                        String newCount = (String) sois.readObject();

                        SQLCar sqlCar = new SQLCar();
                        int idCar = Integer.parseInt(sqlCar.findIdCar(car));
                        sqlCar.updateCount(idCar, Integer.parseInt(newCount));

                        soos.writeObject("Ok");
                    }
                    break;


                    case "createBrandCB": {
                        SQLBrand sqlBrand = new SQLBrand();
                        String[] brand = sqlBrand.getAllBrand();
                        soos.writeObject(brand);
                    }
                    break;
                    case "createSeriesCB": {
                        SQLSeries sqlSeries = new SQLSeries();
                        String[] series = sqlSeries.getAllSeries();
                        soos.writeObject(series);

                    }
                    break;
                    case "createConfigurationCB": {
                        SQLConfiguration sqlConfiguration = new SQLConfiguration();
                        String[] configuration = sqlConfiguration.getAllConfiguration();
                        soos.writeObject(configuration);
                    }
                    break;
                    case "createContractNumber": {
                        int count = 1;

                        SQLContract sqlContract = new SQLContract();
                        ArrayList<String[]> result = sqlContract.getAllContractNumber();
                        String[] contractNumber = new String[result.size() + 1];

                        contractNumber[0] = "";
                        for (String[] item : result) {
                            contractNumber[count] = item[0];
                            count++;
                        }

                        soos.writeObject(contractNumber);
                    }
                    break;
                    case "editNewBrand": {
                        String brand = (String) sois.readObject();

                        SQLBrand sqlBrand = new SQLBrand();
                        int idBrand = sqlBrand.getIdBrand(brand);

                        if (idBrand != 0) {
                            soos.writeObject("Error");
                        } else {
                            soos.writeObject("Ok");
                            sqlBrand.insert(brand);
                            soos.writeObject("insert");
                        }
                    }
                    break;
                    case "editNewSeries": {
                        String series = (String) sois.readObject();

                        SQLSeries sqlSeries = new SQLSeries();
                        int idSeries = sqlSeries.getIdSeries(series);

                        if (idSeries != 0) {
                            soos.writeObject("Error");
                        } else {
                            soos.writeObject("Ok");
                            sqlSeries.insert(series);
                            soos.writeObject("insert");
                        }
                    }
                    break;
                    case "editNewConfiguration": {
                        String configuration = (String) sois.readObject();

                        SQLConfiguration sqlConfiguration = new SQLConfiguration();
                        int idConfiguration = sqlConfiguration.getIdConfiguration(configuration);

                        if (idConfiguration != 0) {
                            soos.writeObject("Error");
                        } else {
                            soos.writeObject("Ok");
                            sqlConfiguration.insert(configuration);
                            soos.writeObject("insert");
                        }
                    }
                    break;
//                    case "diagram": {
//                        int count = 0;
//
//                        Date date = (Date) sois.readObject();
//
//                        SQLContract sqlContract = new SQLContract();
//
//                        ArrayList<String[]> result = sqlContract.getContractForDiagram(date.getMonth());
//                        ArrayList<String> allBrand = new ArrayList<>();
//
//                        Map<String, Integer> brand = new HashMap<>();
//
//                        for (String[] item : result) {
//                            if (count == 0) {
//                                brand.put(item[0], 1);
//                                allBrand.add(item[0]);
//                                count++;
//                                continue;
//                            }
//                            if (brand.containsKey(item[0])) {
//                                Integer i = brand.get(item[0]);
//                                i++;
//                                brand.put(item[0], i);
//                            } else {
//                                brand.put(item[0], 1);
//                                allBrand.add(item[0]);
//                                count++;
//                            }
//                        }
//
//                        String[] mas = new String[allBrand.size()];
//                        count = 0;
//
//                        for (String s : allBrand) {
//                            mas[count] = s;
//                            count++;
//                        }
//
//                        Integer[] countBrand = new Integer[mas.length];
//
//                        for (int i = 0; i < countBrand.length; i++) {
//                            countBrand[i] = brand.get(mas[i]);
//                        }
//
//                        soos.writeObject(mas);
//                        soos.writeObject(countBrand);
//
//
//                    }
//                    break;

                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
