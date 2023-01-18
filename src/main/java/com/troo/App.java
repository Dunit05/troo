// Names: Tommy, Andrew, Suchir
// Date due: Janurary, 18th, 2023
// tróo by Furot
// Sprint: 3

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
    // Sets the stage and scene to the Login.fxml file
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/troo/screens/Login.fxml"));
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResourceAsStream("/com/troo/img/icon.png"));
            stage.getIcons().add(icon);
            stage.setTitle("tróo");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Launch the application
    public static void main(String[] args) {
        launch(args);
    }
}