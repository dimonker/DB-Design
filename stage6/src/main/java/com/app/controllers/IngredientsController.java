package com.app.controllers;

import com.app.DAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;


public class IngredientsController {
    @FXML
    private Button inputBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TextField textField;

    @FXML
    private TextFlow textFlow;

    @FXML
    private void getIngredients(){
        DAO dao = new DAO();
        try {
            String str = dao.getIngredients(textField.getText());
            if (str.equals(""))
                throw new Exception("Нет такой пиццы");

            Text text = new Text(String.join("\n", Arrays.asList(str.split(", "))));
            textFlow.getChildren().clear();
            textFlow.getChildren().add(text);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            e.printStackTrace();
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
            stage.setTitle("stаrt");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
