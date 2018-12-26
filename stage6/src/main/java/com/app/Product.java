package com.app;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    public Product(String n, Double p){
        name = new SimpleStringProperty(n);
        price = new SimpleDoubleProperty(p);
    }


    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleDoubleProperty getPrice() {
        return price;
    }
}
