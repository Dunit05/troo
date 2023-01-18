// Sprint:
// Name: Suchir
package com.troo.controllers.util;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SetDarkMode {

    public static void setDarkModeTextField(TextField textbox) {
        // set the style to .textbox-dark
        textbox.getStyleClass().add("textbox-dark");
    }

    public static void setDarkModePasswordField(PasswordField passwordField) {
        // set the style to .textbox-dark
        passwordField.getStyleClass().add("textbox-dark");
        passwordField.getStyleClass().add(".showPassword-textbox-dark");
    }

    public static void setDarkModeListView(ListView listView) {
        // set the style to .listview-dark
        listView.getStyleClass().add("textbox-dark");
    }

    public static void setDarkModeLabel(Label label) {
        // set the style to .label-dark
        label.getStyleClass().add("label-dark");
    }

    public static void setPrimaryDarkModeButton(Button button) {
        // set the style to .button-dark
        button.getStyleClass().add("btn-primary-dark");
    }

    public static void setSecondaryDarkModeButton(Button button) {
        // set the style to .button-dark
        button.getStyleClass().add("btn-secondary-dark");
    }

    public static void setShowPasswordButtonDark(Button button) {
        // set the style to .button-dark
        button.getStyleClass().add("btn-showPassword-dark");
    }

    public static void setDarkModeCheckBox(CheckBox checkBox) {
        // set the style to .checkbox-dark
        checkBox.getStyleClass().add("checkbox-dark");
    }

    public static void removeShowPasswordButtonDark(Button button) {
        // remove the style .button-dark
        button.getStyleClass().remove("btn-showPassword-dark");
    }

    public static void removePrimaryDarkModeButton(Button button) {
        // remove the style .button-dark
        button.getStyleClass().remove("btn-primary-dark");
    }

    public static void removeSecondaryDarkModeButton(Button button) {
        // remove the style .button-dark
        button.getStyleClass().remove("btn-secondary-dark");
    }

    public static void removeDarkModeCheckBox(CheckBox checkBox) {
        // remove the style .checkbox-dark
        checkBox.getStyleClass().remove("checkbox-dark");
    }

    public static void removeDarkModeLabel(Label label) {
        // remove the style .label-dark
        label.getStyleClass().remove("label-dark");
    }

    public static void removeDarkModeTextField(TextField textbox) {
        // remove the style .textbox-dark
        textbox.getStyleClass().remove("textbox-dark");
    }

    public static void removeDarkModePasswordField(PasswordField passwordField) {
        // remove the style .textbox-dark
        passwordField.getStyleClass().remove("textbox-dark");
        passwordField.getStyleClass().remove(".showPassword-textbox-dark");
    }

    public static void removeDarkModeListView(ListView listView) {
        // remove the style .listview-dark
        listView.getStyleClass().remove("textbox-dark");
    }
}
