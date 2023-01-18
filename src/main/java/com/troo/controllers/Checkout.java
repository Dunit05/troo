// Name: Tommy
// Sprint:

package com.troo.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.troo.controllers.util.Controller;
import com.troo.controllers.util.DistanceMatrix;
import com.troo.controllers.util.GenerateCode;
import com.troo.controllers.util.GenerateReceipt;
import com.troo.controllers.util.QRCode;
import com.troo.controllers.util.SetDarkMode;
import com.troo.controllers.util.StorageBucket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.troo.controllers.util.Email;
import com.troo.controllers.util.Error;

public class Checkout implements Initializable {

    // Get all the FXML elements
    @FXML
    Label errorLabel, subtotal, tax, total, checkoutLabel, checkoutInfoLabel, cardNumberLabel, cardDateLabel,
            cardCVCLabel, cardInfoLabel, helpLabel, nameLabel, emailLabel, phoneLabel, addressLabel;

    @FXML
    TextField name, email, phone, address, cardNumber, cardDate, cvc;

    @FXML
    CheckBox darkModeCheckBox;

    // Override the initialize method to load all the page data
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Prefill the users information
        name.setText(StorageBucket.getUserName());
        email.setText(StorageBucket.getUserEmail());
        phone.setText(StorageBucket.getUserPhone());
        address.setText(StorageBucket.getUserAddress());

        // Set the cartInfo
        subtotal.setText("Subtotal: $" + StorageBucket.getCartTotal());
        tax.setText("Tax: " + StorageBucket.TAX + "%");
        total.setText("Total: $" + StorageBucket.getCartTotalWithTax());
    }

    // Method to handel the users payment
    public void pay(ActionEvent event) {
        // Check if the user has entered all the information
        if (name.getText().isEmpty() || email.getText().isEmpty()
                || phone.getText().isEmpty() || address.getText().isEmpty()
                || cardNumber.getText().isEmpty() || cardDate.getText().isEmpty()
                || cvc.getText().isEmpty()) {
            Error.setError("Please fill out all the information", errorLabel);
            return;
        } else {
            Error.removeError(errorLabel);
        }
        // Check if the card number is valid
        if (cardNumber.getText().length() != 16) {
            Error.setError("Please enter a valid card number", errorLabel);
            return;
        } else {
            Error.removeError(errorLabel);
        }

        // Check if the card date is valid
        if (cardDate.getText().length() != 5) {
            Error.setError("Please enter a valid card date", errorLabel);
            return;
        } else {
            Error.removeError(errorLabel);
        }

        // Check if the cvc is valid
        if (cvc.getText().length() != 3) {
            Error.setError("Please enter a valid cvc", errorLabel);
            return;
        } else {
            Error.removeError(errorLabel);
        }

        String receiptNumber = GenerateCode.receiptCode();
        String transactionNumber = GenerateCode.transactionCode();

        // ! Get Distance
        // get the preperaion time from each item in cart, put it in an arry using
        // Collection.sort() and get the largest number and store it in a variable
        double[] times = new double[StorageBucket.getCart().size()];
        for (int i = 0; i < StorageBucket.getCart().size(); i++) {
            times[i] = StorageBucket.getCart().get(i).getPrepTime();
        }

        // Sort the array using bubble sort
        for (int i = 0; i < times.length; i++) {
            for (int j = 0; j < times.length - 1; j++) {
                if (times[j] > times[j + 1]) {
                    double temp = times[j];
                    times[j] = times[j + 1];
                    times[j + 1] = temp;
                }
            }
        }

        System.out.println("Times: " + times);

        // Get the largest number
        double largest = times[times.length - 1];

        System.out.println("Largest: " + largest);

        ArrayList<String> restaurants = new ArrayList<String>();

        // go through each item in the cart and see what restaurant it is from, and make
        // an arrayList of the restaurants, if there are more than one restaurant in the
        // cart, keep only on
        for (int i = 0; i < StorageBucket.getCart().size(); i++) {
            if (!restaurants.contains(StorageBucket.getCart().get(i).getRestaurantName())) {
                restaurants.add(StorageBucket.getCart().get(i).getRestaurantName());
            }
        }

        double totalDisTime = 0;
        for (int i = 0; i < restaurants.size(); i++) {
            double dis = 0;
            dis = DistanceMatrix.getTime(restaurants.get(i), StorageBucket.getUserAddress());
            totalDisTime += dis;
        }

        // !
        System.out.println("Total Distance Time: " + totalDisTime);

        // Add up the total time
        double totalTime = largest + totalDisTime;

        StorageBucket.setDeliveryTime(totalTime);

        // Write the transaction to the file
        try {
            File file = new File("src/main/resources/com/troo/data/transaction_data/transactions.txt");
            FileWriter writer = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(writer);
            pw.println("[Account: " + StorageBucket.getUserEmail() + "\nTransaction: " + transactionNumber
                    + "\nReceipt: " + receiptNumber + "]");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StorageBucket.setReceiptNumber(receiptNumber);
        StorageBucket.setTransactionNumber(transactionNumber);

        String qrCodeText = "Transaction Number: " + transactionNumber;
        String filePath = "src/main/resources/com/troo/data/transaction_data/" + transactionNumber + ".png";
        File qrFile = new File(filePath);
        QRCode.createQRImage(qrFile, qrCodeText);
        GenerateReceipt.generateReceipt();

        GenerateReceipt.generateReceipt();

        // Send the email
        Email email = new Email();
        email.sendEmailWithAttachment(StorageBucket.getUserEmail(), "Here is your receipt #" + receiptNumber,
                "src/main/resources/com/troo/data/transaction_data/" + StorageBucket.getReceiptNumber() + ".pdf");

        // Clear the cart
        StorageBucket.resetCart();

        // Change to thankyou screen
        Controller.changeScene("/com/troo/screens/Success.fxml", event);

    }

    public void back(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Cart.fxml", event);
    }

    // Set the dark mode for the Home screen
    public void setDarkModeCheckoutScreen(ActionEvent event) {
        if (darkModeCheckBox.isSelected()) {
            SetDarkMode.setDarkModeTextField(name);
            SetDarkMode.setDarkModeTextField(email);
            SetDarkMode.setDarkModeTextField(phone);
            SetDarkMode.setDarkModeTextField(address);
            SetDarkMode.setDarkModeTextField(cardNumber);
            SetDarkMode.setDarkModeTextField(cardDate);
            SetDarkMode.setDarkModeTextField(cvc);
            SetDarkMode.setDarkModeLabel(errorLabel);
            SetDarkMode.setDarkModeLabel(subtotal);
            SetDarkMode.setDarkModeLabel(tax);
            SetDarkMode.setDarkModeLabel(total);
            SetDarkMode.setDarkModeLabel(checkoutLabel);
            SetDarkMode.setDarkModeLabel(checkoutInfoLabel);
            SetDarkMode.setDarkModeLabel(cardNumberLabel);
            SetDarkMode.setDarkModeLabel(cardDateLabel);
            SetDarkMode.setDarkModeLabel(cardCVCLabel);
            SetDarkMode.setDarkModeLabel(cardInfoLabel);
            SetDarkMode.setDarkModeLabel(helpLabel);
            SetDarkMode.setDarkModeLabel(nameLabel);
            SetDarkMode.setDarkModeLabel(emailLabel);
            SetDarkMode.setDarkModeLabel(phoneLabel);
            SetDarkMode.setDarkModeLabel(addressLabel);
        } else {
            SetDarkMode.removeDarkModeTextField(name);
            SetDarkMode.removeDarkModeTextField(email);
            SetDarkMode.removeDarkModeTextField(phone);
            SetDarkMode.removeDarkModeTextField(address);
            SetDarkMode.removeDarkModeTextField(cardNumber);
            SetDarkMode.removeDarkModeTextField(cardDate);
            SetDarkMode.removeDarkModeTextField(cvc);
            SetDarkMode.removeDarkModeLabel(errorLabel);
            SetDarkMode.removeDarkModeLabel(subtotal);
            SetDarkMode.removeDarkModeLabel(tax);
            SetDarkMode.removeDarkModeLabel(total);
            SetDarkMode.removeDarkModeLabel(checkoutLabel);
            SetDarkMode.removeDarkModeLabel(checkoutInfoLabel);
            SetDarkMode.removeDarkModeLabel(cardNumberLabel);
            SetDarkMode.removeDarkModeLabel(cardDateLabel);
            SetDarkMode.removeDarkModeLabel(cardCVCLabel);
            SetDarkMode.removeDarkModeLabel(cardInfoLabel);
            SetDarkMode.removeDarkModeLabel(helpLabel);
            SetDarkMode.removeDarkModeLabel(nameLabel);
            SetDarkMode.removeDarkModeLabel(emailLabel);
            SetDarkMode.removeDarkModeLabel(phoneLabel);
            SetDarkMode.removeDarkModeLabel(addressLabel);
        }
    }
}
