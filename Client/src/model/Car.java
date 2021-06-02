package model;

import java.io.Serializable;

public class Car implements Serializable {
    private String brand="";
    private String series="";
    private String configuration="";
    private double price;
    private int count;
    private double engine;
    private int horsepower;
    private String motor;
    private double priseFrom=-1;
    private double priseTo=-1;
    private int yearCB =0;
    private int year=0;
    private int idBrand;
    private int idConfiguration;
    private int idSeries;

    public int getIdBrand() {
        return idBrand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setIdBrand(int idBrand) {
        this.idBrand = idBrand;
    }

    public int getIdConfiguration() {
        return idConfiguration;
    }

    public void setIdConfiguration(int idConfiguration) {
        this.idConfiguration = idConfiguration;
    }

    public int getIdSeries() {
        return idSeries;
    }

    public void setIdSeries(int idSeries) {
        this.idSeries = idSeries;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getEngine() {
        return engine;
    }

    public void setEngine(double engine) {
        this.engine = engine;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public int getYearCB() {
        return yearCB;
    }

    public double getPriseFrom() {
        return priseFrom;
    }

    public void setPriseFrom(double priseFrom) {
        this.priseFrom = priseFrom;
    }

    public double getPriseTo() {
        return priseTo;
    }

    public void setPriseTo(double priseTo) {
        this.priseTo = priseTo;
    }

    public void setYearCB(int yearCB) {
        this.yearCB = yearCB;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }
}
