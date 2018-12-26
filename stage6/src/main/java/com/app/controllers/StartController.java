package com.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.IOException;

public class StartController {
    @FXML
    private Button menuBtn;

    @FXML
    private Button histogramBtn;

    @FXML
    private Button ingredientBtn;

    @FXML
    private void showMenu(){
        try {
            Stage stageCur = (Stage) menuBtn.getScene().getWindow();
            stageCur.close();

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MenuTable.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Menu");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void showHistogram(){
        try {
            Stage stageCur = (Stage) histogramBtn.getScene().getWindow();
            stageCur.close();

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Histogram.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Histogram");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showIngredients(){
        try {
            Stage stageCur = (Stage) ingredientBtn.getScene().getWindow();
            stageCur.close();

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Ingredients.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Ingredients");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
