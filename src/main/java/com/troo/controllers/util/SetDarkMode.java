package com.troo.controllers.util;

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
