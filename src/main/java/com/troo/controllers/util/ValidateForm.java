// By: Andrew
// Sprint: 2

package com.troo.controllers.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

// Class to validate forms
public class ValidateForm {
    // Validate email method, takes email as a string, error label and textfield as
    // parameters
    public static boolean validateEmail(String email, Label errorLabel, TextField textField) {
        // Check if email is in the correct format
        if (!email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") || email.equals("")) {
            // if email is not in the correct format, set the error label and textfield
            // error
            Error.setError("Please enter a valid email address", errorLabel);
            Error.setTextFieldErrorBorder(textField);
            return false;
        }
        // If email is in the correct format, remove the error label and textfield
        Error.removeError(errorLabel);
        Error.removeTextFieldErrorBorder(textField);
        return true;
    }

    // Validate password method, takes password as a string, error label and
    // passwordfield as parameters
    public static boolean validatePassword(String password, Label errorLabel, PasswordField passwordField) {
        // Check if password is at least 8 characters and matches the password regex
        if (password.length() < 8 || password.equals("")) {
            // If password is not at least 8 characters, set the error label and textfield
            // error
            Error.setError("Password must be at least 8 characters", errorLabel);
            Error.setPasswordFieldErrorBorder(passwordField);
            return false;
        } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")) {
            // If password does not match the password regex, set the error label and
            // textfield error
            Error.setError("Password must contain the required characters", errorLabel);
            Error.setPasswordFieldErrorBorder(passwordField);
            return false;
        }
        // If password is at least 8 characters and matches the password regex, remove
        // the error label and textfield
        Error.removeError(errorLabel);
        Error.removePasswordFieldErrorBorder(passwordField);
        return true;
    }

    // Validate phone method, takes phone as a string, error label and textfield as
    // perameters
    public static boolean validatePhone(String phone, Label errorLabel, TextField textField) {
        // Check if phone is 10 digits and matches the phone regex
        if (!phone.matches("[0-9]+") || phone.equals("")) {
            // If the phone does not match the phone regex, set the error label and
            // textfield error
            Error.setError("Enter a valid phone number, without parentheses", errorLabel);
            Error.setTextFieldErrorBorder(textField);
            return false;
        } else if (!(phone.length() == 10)) {
            // If phone is not 10 digits, set the error label and textfield error
            Error.setError("Phone number must be 10 digits", errorLabel);
            Error.setTextFieldErrorBorder(textField);
            return false;
        }
        Error.removeError(errorLabel);
        Error.removeTextFieldErrorBorder(textField);
        return true;
    }

    // Validate name method, takes name as a string, error label and textfield as
    // parameters
    public static boolean validateName(String name, Label errorLabel, TextField textField) {
        // Check if name is only letters and not empty
        if (!name.matches("[a-zA-Z]+") || name.equals("")) {
            // If name is not only letters or is empty, set the error label and textfield
            // error
            Error.setError("Please enter a valid name", errorLabel);
            Error.setTextFieldErrorBorder(textField);
            return false;
        }
        // If name is only letters and not empty, remove the error label and textfield
        // error
        Error.removeError(errorLabel);
        Error.removeTextFieldErrorBorder(textField);
        return true;
    }

    // Validate address method, takes address as a string, error label and textfield
    // as parameters
    public static boolean validateAddress(String address, Label errorLabel, TextField textField) {
        // Check if address is empty
        if (address.equals("")) {
            // If address is empty, set the error label and textfield error
            Error.setError("Please enter a valid address", errorLabel);
            Error.setTextFieldErrorBorder(textField);
            return false;
        }
        // If address is not empty, remove the error label and textfield error
        Error.removeError(errorLabel);
        Error.removeTextFieldErrorBorder(textField);
        return true;
    }

    // Check if user exists method, so they can login, takes email, password
    // (Encrypted), error label
    // and textfield
    // and passwordfield as parameters
    public static boolean checkIfUserExists(String email, String password, Label errorLabel, TextField textField,
            PasswordField passwordField) {
        // Try to read the users.txt file
        try {
            FileReader fr = new FileReader("src/main/resources/com/troo/data/users.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = "", emailLine = "", passwordLine = "";
            while ((line = br.readLine()) != null) {
                // Check if the line contains the email and password
                if (line.contains("[Email: ")) {
                    emailLine = line.substring(8);
                } else if (line.contains("Password: ")) {
                    passwordLine = line.substring(10);
                }
                if (emailLine.equals(email) && passwordLine.equals(password)) {
                    // If the email and password match, remove the error label and textfield error
                    Error.removeError(errorLabel);
                    Error.removeTextFieldErrorBorder(textField);
                    Error.removePasswordFieldErrorBorder(passwordField);
                    br.close();
                    return true;
                }
            }
            br.close();
            // If the email and password do not match, set the error label and textfield
            // error
            Error.setError("Email or password is incorrect", errorLabel);
            Error.setTextFieldErrorBorder(textField);
            Error.setPasswordFieldErrorBorder(passwordField);
            return false;
        } catch (IOException e) {
            // If there is an error, set the error label
            Error.setError("There was an error", errorLabel);
        }
        return false;
    }

    // Checks if user already exists, to make sure the user is unique, takes email,
    // phone, error label,
    // textfield as parameters
    public static boolean checkIfUserAlreadyExists(String email, String phone, Label errorLabel,
            TextField emailTextField,
            TextField phoneTextField) {
        // Try to read the users.txt file
        try {
            FileReader fr = new FileReader("src/main/resources/com/troo/data/users.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = "", emailLine = "", phoneLine = "";
            while ((line = br.readLine()) != null) {
                // Check if the line contains the email and phone
                if (line.contains("[Email: ")) {
                    emailLine = line.substring(8);
                } else if (line.contains("Phone: ")) {
                    phoneLine = line.substring(7);
                }
                if (emailLine.equals(email) && phoneLine.equals(phone)) {
                    // If the email and phone match, set the error label and textfield error
                    Error.setError("Email and password must be unique", errorLabel);
                    Error.setTextFieldErrorBorder(phoneTextField);
                    Error.setTextFieldErrorBorder(emailTextField);
                    br.close();
                    return false;
                }
            }
            br.close();
            // If the email and phone do not match, remove the error label and textfield
            // error
            Error.removeError(errorLabel);
            Error.removeTextFieldErrorBorder(emailTextField);
            Error.removeTextFieldErrorBorder(phoneTextField);
            return true;
        } catch (IOException e) {
            // If there is an error, set the error label
            Error.setError("There was an error", errorLabel);
        }
        return false;

    }
}
