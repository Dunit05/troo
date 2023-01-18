// By: Andrew
// Sprint: 2

package com.troo.controllers.util;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

// Class to change scenes
public class Controller {
    // Change the scene, takes in the path to the fxml file and the ActionEven

    public static void changeScene(String path, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Controller.class.getResource(path));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeToOrderScene(String path, MouseEvent event) {
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

    // Show password method, takes in a passwordfield as a parameter
    public static void showPassword(PasswordField passwordField) {
        // Set the prompt text to the password field text
        passwordField.setPromptText(passwordField.getText());
        passwordField.setText("");
        passwordField.getStyleClass().add("showPassword-text");
    }

    // Hide password method, takes in a passwordfield as a parameter
    public static void hidePassword(PasswordField passwordField) {
        // Set the password field text to the prompt text
        passwordField.setText(passwordField.getPromptText());
        passwordField.setPromptText("");
    }
}
