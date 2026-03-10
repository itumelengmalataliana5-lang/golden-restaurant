package com.example.rastuarent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignupController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    @FXML
    private void handleSignup(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if(username.isEmpty() || password.isEmpty()){
            showAlert("Enter username and password.");
            return;
        }

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();

            showAlert("User registered successfully!");

            txtUsername.clear();
            txtPassword.clear();

        } catch (Exception e){
            e.printStackTrace();
            showAlert("Error: " + e.getMessage());
        }
    }

    @FXML
    private void backToLogin(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/rastuarent/login.fxml"));
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root, 400, 300));
        } catch (Exception e){
            e.printStackTrace();
            showAlert("Error loading login screen.");
        }
    }

    private void showAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Signup");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}