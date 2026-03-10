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
import java.sql.ResultSet;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Enter username and password.");
            return;
        }

        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Login success, open restaurant UI
                Parent root = FXMLLoader.load(getClass().getResource("restaurant.fxml"));
                Stage stage = (Stage) txtUsername.getScene().getWindow();
                stage.setScene(new Scene(root, 700, 500));
            } else {
                showAlert("Invalid username or password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error: " + e.getMessage());
        }
    }

    @FXML
    private void goToSignup(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root, 400, 300));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading signup screen.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}