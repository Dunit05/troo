// Names: Tommy, Andrew, Suchir
// Date due: Janurary, 18th, 2023
// Troo by Furot

package com.troo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    // Override the start method in the Application class
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/troo/screens/Login.fxml"));
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/com/troo/img/icon.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Troo");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Launch the application
    public static void main(String[] args) {
        launch(args);
    }
}