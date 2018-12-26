package com.app.controllers;

import com.app.Product;
import com.app.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;


public class MenuController {

    @FXML
    private TableView<Product> tbData;

    @FXML
    private TableColumn<Product, String> name;

    @FXML
    private TableColumn<Product, Double> price;

    @FXML
    public void initialize() {
        name.setCellValueFactory(cellData -> cellData.getValue().getName());
        price.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
    }

    @FXML
    private Button backBtn;

    @FXML
    private void back(){
        Stage stageCur = (Stage) backBtn.getScene().getWindow();
        stageCur.close();

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Start.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Start");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void refreshData(){
        ObservableList<Product> products = FXCollections.observableArrayList();

        try{
            DAO service = new DAO();
            products.addAll(service.getProducts());
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("EXCEPTION");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        tbData.setItems(products);
    }


}
