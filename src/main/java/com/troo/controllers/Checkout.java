// Name: Tommy
// Sprint: 6

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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.troo.controllers.util.Email;
import com.troo.controllers.util.Error;

public class Checkout implements Initializable {

    // Get all the FXML elements
    @FXML
    Label errorLabel, subtotal, tax, total, deliveryFee, checkoutLabel, checkoutInfoLabel, cardNumberLabel,
            cardDateLabel, cardCVCLabel, cardInfoLabel, helpLabel, nameLabel, emailLabel, phoneLabel, addressLabel;

    @FXML
    TextField name, email, phone, address, cardNumber, cardDate, cvc;

    @FXML
    CheckBox darkModeCheckBox;

    @FXML
    Button payButton, backButton;

    // Override method to override the default page data with the checkout data
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
        deliveryFee.setText("Delivery Fee: $" + StorageBucket.getDeliveryFee());
    }

    // Method to handel the users payment
    public void pay(ActionEvent event) {
        // Variables
        double[] times = new double[StorageBucket.getCart().size()];
        ArrayList<String> restaurants = new ArrayList<String>();
        double largest = 0, totalDisTime = 0, totalTime = 0;
        int timesLength = times.length;
        String receiptNumber = "", transactionNumber = "", qrCodeText = "", filePath = "";

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

        // Generate the receipt number and transaction number
        receiptNumber = GenerateCode.receiptCode();
        transactionNumber = GenerateCode.transactionCode();

        // Set the transaction data
        StorageBucket.setReceiptNumber(receiptNumber);
        StorageBucket.setTransactionNumber(transactionNumber);

        // ---- Complex algorithm to calculate the delivery time ----

        // Get the prep times of each item in the cart, and set them to an array
        for (int i = 0; i < StorageBucket.getCart().size(); i++) {
            times[i] = StorageBucket.getCart().get(i).getPrepTime();
        }

        // Sort the prep times array using bubble sort
        for (int i = 0; i < timesLength; i++) {
            for (int j = 0; j < timesLength - 1; j++) {
                if (times[j] > times[j + 1]) {
                    double temp = times[j];
                    times[j] = times[j + 1];
                    times[j + 1] = temp;
                }
            }
        }

        // Get the largest number, as that will be the longest prep time, and will be
        // the prep time we use to caculate the delivery time
        largest = times[times.length - 1];

        // Go through each item in the cart and see what restaurant it is from, and make
        // an arrayList of the restaurants
        for (int i = 0; i < StorageBucket.getCart().size(); i++) {
            if (!restaurants.contains(StorageBucket.getCart().get(i).getRestaurantName())) {
                restaurants.add(StorageBucket.getCart().get(i).getRestaurantName());
            }
        }

        // Go through each restaurant and get the distance from the user to the
        // restaurant, and add it to the total distance time
        for (int i = 0; i < restaurants.size(); i++) {
            double dis = 0;
            dis = DistanceMatrix.getTime(restaurants.get(i), StorageBucket.getUserAddress());
            totalDisTime += dis;
        }

        // Add up the total time, which is the largest prep time added to the total
        // distance time
        totalTime = largest + totalDisTime;

        // Set the delivery time
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

        // Create the QR code
        qrCodeText = "Transaction Number: " + transactionNumber;
        filePath = "src/main/resources/com/troo/data/transaction_data/" + transactionNumber + ".png";
        File qrFile = new File(filePath);
        QRCode.createQRImage(qrFile, qrCodeText);

        // Generate the receipt
        GenerateReceipt.generateReceipt();

        // Send the email, containing the receipt
        Email email = new Email();
        email.sendEmailWithAttachment(StorageBucket.getUserEmail(), "Here is your receipt #" + receiptNumber,
                "src/main/resources/com/troo/data/transaction_data/" + StorageBucket.getReceiptNumber() + ".pdf");

        // Clear the cart
        StorageBucket.resetCart();

        // Change to thankyou screen
        Controller.changeScene("/com/troo/screens/Success.fxml", event);
    }

    // Go back to the cart screen
    public void back(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Cart.fxml", event);
    }

    // Andrew's code
    // Set the dark mode for the home screen
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
            SetDarkMode.setDarkModeLabel(deliveryFee);
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
            SetDarkMode.setPrimaryDarkModeButton(payButton);
            SetDarkMode.setSecondaryDarkModeButton(backButton);
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
            SetDarkMode.removeDarkModeLabel(deliveryFee);
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
            SetDarkMode.removePrimaryDarkModeButton(payButton);
            SetDarkMode.removeSecondaryDarkModeButton(backButton);
        }
    }
}
