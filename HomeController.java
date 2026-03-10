package com.example.rastuarent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    private Stage primaryStage;

    // Setter for stage
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void openLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent loginRoot = loader.load();

            // Optionally, pass stage to LoginController if needed
            LoginController loginController = loader.getController();

            Scene scene = new Scene(loginRoot, 700, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login - Restaurant System");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}