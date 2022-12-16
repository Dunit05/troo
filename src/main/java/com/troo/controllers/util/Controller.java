// By: Andrew
// Sprint: 2

package com.troo.controllers.util;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Class to change scenes
public class Controller {
    // Change the scene, takes in the path to the fxml file and the ActionEvent
    public static void changeScene(String path, ActionEvent event) {
        // Try to load the fxml file and change the scene
        try {
            Parent root = FXMLLoader.load(Controller.class.getResource(path));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
