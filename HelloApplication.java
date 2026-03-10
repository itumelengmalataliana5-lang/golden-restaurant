package com.example.rastuarent;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Correct path to FXML in resources
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/rastuarent/welcome.fxml"));
        Parent root = loader.load();

        HomeController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Restaurant System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}