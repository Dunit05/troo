// By: Suchir
// Sprint: 2

package com.troo.controllers.util;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

// Class to set error messages and error borders
public class Error {
    // Set the error message and add the error to the label
    public static void setError(String error, Label label) {
        label.setText(error);
        label.getStyleClass().add("error");
    }

    // Set the error border to the textfield
    public static void setTextFieldErrorBorder(TextField textField) {
        textField.getStyleClass().add("error-border");
    }

    // Set the error border to the passwordfield
    public static void setPasswordFieldErrorBorder(TextField textField) {
        textField.getStyleClass().add("error-border");
    }

    // Remove the error message and error from the label
    public static void removeError(Label label) {
        label.setText("");
        label.getStyleClass().remove("error");
    }

    // Remove the error border from the textfield
    public static void removeTextFieldErrorBorder(TextField textField) {
        textField.getStyleClass().remove("error-border");
    }

    // Remove the error border from the passwordfield
    public static void removePasswordFieldErrorBorder(TextField textField) {
        textField.getStyleClass().remove("error-border");
    }

}
