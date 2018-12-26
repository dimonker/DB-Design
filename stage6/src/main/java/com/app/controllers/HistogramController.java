package com.app.controllers;

import com.app.DAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class HistogramController {
    @FXML
    private BarChart histogram;

    @FXML
    private DatePicker firstDate;

    @FXML
    private DatePicker secondDate;

    @FXML
    private Button backBtn;

    @FXML
    private void refresh(){
        histogram.getData().clear();
        histogram.setAnimated(false);
        XYChart.Series series1 = new XYChart.Series<>();

        try{
            LocalDate date1 = firstDate.getValue();
            LocalDate date2 = secondDate.getValue();

            if (date1 == null || date2 == null)
                throw new Exception("Выберите период");

            DAO service = new DAO();
            Double income = service.getIncome(date1, date2);
            Double expenses = service.getExpenses(date1, date2);

            series1.getData().add(new XYChart.Data("Доход", income));
            series1.getData().add(new XYChart.Data("Расход", expenses));

            histogram.getData().addAll(series1);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("EXCEPTION");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    private void back(){
        Stage stageCur = (Stage) backBtn.getScene().getWindow();
        stageCur.close();

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Start.fxml"));
            Stage stage = new Stage();
            stage.setTitle("start");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
