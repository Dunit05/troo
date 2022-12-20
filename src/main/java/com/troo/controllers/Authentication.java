// By: Tommy
// Sprint: 2

package com.troo.controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import com.troo.controllers.util.AddressAutocomplete;
import com.troo.controllers.util.Controller;
import com.troo.controllers.util.Email;
import com.troo.controllers.util.Error;
import com.troo.controllers.util.GenerateCode;
import com.troo.controllers.util.Encrypt;
import com.troo.controllers.util.ValidateForm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// Class to handle the authentication
public class Authentication {
    // Get the FXML elements
    @FXML
    private TextField emailField, firstNameField, lastNameField, phoneField, addressField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button showPasswordButton;

    @FXML
    private ListView<String> addressList;

    @FXML
    private CheckBox darkModeCheckBox;

    // Get a random UUID for the address autocomplete (to increase security & add
    // session)
    private final UUID UUID_CODE = UUID.randomUUID();

    // Login method
    public void login(ActionEvent event) {
        // Get the FXMLELements values and set them to the local variables
        String email = "", password = "";
        email = emailField.getText();
        email = email.toLowerCase();
        password = passwordField.getText();

        // Validate the email and password
        if (!ValidateForm.validateEmail(email, errorLabel, emailField)) {
            return;
        }

        if (!ValidateForm.validatePassword(password, errorLabel, passwordField)) {
            return;
        }

        // Encrypt the password
        password = Encrypt.hash(password);

        // Check if the user exists
        if (!ValidateForm.checkIfUserExists(email, password, errorLabel, emailField, passwordField)) {
            return;
        }

        // Change the scene to the home screen
        Controller.changeScene("/com/troo/screens/HomeScreen.fxml", event);
    }

    // Register method, takes an ActionEvent listener as a parameter
    public void register(ActionEvent event) {
        // Get the FXMLElements values and set them to the local variables
        String email = "", password = "", firstName = "", lastName = "", phone = "", address = "";
        Email emailSender = new Email();
        email = emailField.getText();
        email = email.trim();
        email = email.toLowerCase();
        password = passwordField.getText();
        firstName = firstNameField.getText();
        lastName = lastNameField.getText();
        phone = phoneField.getText();
        address = addressField.getText();

        // Validate the inputs
        if (!ValidateForm.validateName(firstName, errorLabel, firstNameField)) {
            return;
        }

        if (!ValidateForm.validateName(lastName, errorLabel, lastNameField)) {
            return;
        }

        if (!ValidateForm.validateEmail(email, errorLabel, emailField)) {
            return;
        }

        if (!ValidateForm.validatePhone(phone, errorLabel, phoneField)) {
            return;
        }

        if (!ValidateForm.validateAddress(address, errorLabel, addressField)) {
            return;
        }

        if (!ValidateForm.validatePassword(password, errorLabel, passwordField)) {
            return;
        }

        // Check if the user already exists
        if (!ValidateForm.checkIfUserAlreadyExists(email, phone, errorLabel, emailField, phoneField)) {
            return;
        }

        // Encrypt the password
        password = Encrypt.hash(password);

        // Write the user data to file users.txt
        try {
            FileWriter fw = new FileWriter("src/main/resources/com/troo/data/users.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("[Email: " + email + "\nPassword: " + password + "\nFirst Name: " + firstName + "\nLast Name: "
                    + lastName + "\nPhone: " + phone + "\nAddress: " + address + "]");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            Error.setError("We couldn't create your account", errorLabel);
        }

        // Send the welcome email
        emailSender.sendEmail(email, "Welcome to tróo, " + firstName + "!",
                "Thank you for registering with tróo. We hope you enjoy your experience with us.");

        // Change the scene to the home screen
        Controller.changeScene("/com/troo/screens/HomeScreen.fxml", event);
    }

    // Change the scene to the register screen method, takes an ActionEvent
    // lisenener as a parameter
    public void toRegister(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Register.fxml", event);
    }

    // Change the scene back to the login screen method, takes an ActionEvent
    // lisenener as a parameter
    public void back(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Login.fxml", event);
        GenerateCode code = new GenerateCode();
        System.out.println(code.receiptCode());
        System.out.println(code.transactionCode());
    }

    // Autocomplete address method, takes an KeyEvent lisenener as a parameter
    public void findAddress(KeyEvent event) {
        // Create a new JSONParser object
        JSONParser parser = new JSONParser();
        Object obj;
        JSONObject jsonObject;
        JSONArray results;
        JSONObject result;

        // Get the address from the addressField and set it to the local variable
        String description = "", address = "", predictedAddress = "";
        address = addressField.getText();

        // Get and return the predicted address
        predictedAddress = AddressAutocomplete.autocompleteAddress(address, UUID_CODE);

        // clear the list for new results
        addressList.getItems().clear();

        // add the predicted address to the list
        try {
            // parse the JSON
            obj = parser.parse(predictedAddress);
            jsonObject = (JSONObject) obj;
            results = (JSONArray) jsonObject.get("predictions");

            // loop through the results and add them to the addressList
            for (int i = 0; i < results.size(); i++) {
                result = (JSONObject) results.get(i);
                description = (String) result.get("description");
                addressList.getItems().add(description);
            }

        } catch (Exception e) {
            Error.setError("We couldn't find your address", errorLabel);
        }

        // add a listener to the list
        addressList.getSelectionModel().selectedItemProperty().addListener((obs,
                oldVal, newVal) -> {
            // set the addressField to the selected address
            addressField.setText(newVal);
        });
    }

    // Show password method, takes an MouseEvent lisenener as a parameter
    public void showPassword(MouseEvent event) {
        // Set the passwordField to show the password
        Controller.showPassword(passwordField);
    }

    // Hide password method, takes an MouseEvent lisenener as a parameter
    public void hidePassword(MouseEvent event) {
        // Set the passwordField to hide the password
        Controller.hidePassword(passwordField);
    }

    // Andrew
    // Dark mode method, takes an ActionEvent lisenener as a parameter
    public void setDarkModeLoginScreen(ActionEvent event) {
        if (darkModeCheckBox.isSelected()) {
            emailField.getStyleClass().add("dark-textField");
            passwordField.getStyleClass().add("dark-textField");
            passwordField.getStyleClass().add("showPassword-text-dark");
        } else {
            emailField.getStyleClass().remove("dark-textField");
            passwordField.getStyleClass().remove("dark-textField");
            passwordField.getStyleClass().remove("showPassword-text-dark");
        }
    }

    // Dark Mode method, takes an ActionEvent lisenener as a parameter
    public void setDarkModeRegisterScreen(ActionEvent event) {
        if (darkModeCheckBox.isSelected()) {
            emailField.getStyleClass().add("dark-textField");
            passwordField.getStyleClass().add("dark-textField");
            firstNameField.getStyleClass().add("dark-textField");
            lastNameField.getStyleClass().add("dark-textField");
            phoneField.getStyleClass().add("dark-textField");
            addressField.getStyleClass().add("dark-textField");
            addressList.getStyleClass().add("dark-textField");
            passwordField.getStyleClass().add("showPassword-text-dark");
        } else {
            emailField.getStyleClass().remove("dark-textField");
            passwordField.getStyleClass().remove("dark-textField");
            firstNameField.getStyleClass().remove("dark-textField");
            lastNameField.getStyleClass().remove("dark-textField");
            phoneField.getStyleClass().remove("dark-textField");
            addressField.getStyleClass().remove("dark-textField");
            addressList.getStyleClass().remove("dark-textField");
            passwordField.getStyleClass().remove("showPassword-text-dark");
        }
    }
}
