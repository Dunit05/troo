package com.troo.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import com.troo.controllers.util.Controller;
import com.troo.controllers.util.GenerateCode;
import com.troo.controllers.util.GenerateReceipt;
import com.troo.controllers.util.QRCode;
import com.troo.controllers.util.StorageBucket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.troo.controllers.util.Email;

public class Checkout implements Initializable {

    @FXML
    Label errorLabel, subtotal, tax, total;

    @FXML
    TextField name, email, phone, address, cardNumber, cardDate, cvc;

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

    public void pay(ActionEvent event) {
        // Check if the user has entered all the information
        if (name.getText().isEmpty() || email.getText().isEmpty()
                || phone.getText().isEmpty() || address.getText().isEmpty()
                || cardNumber.getText().isEmpty() || cardDate.getText().isEmpty()
                || cvc.getText().isEmpty()) {
            errorLabel.setText("Please fill out all the information");
            return;
        }

        // Check if the card number is valid
        if (cardNumber.getText().length() != 16) {
            errorLabel.setText("Please enter a valid card number");
            return;
        }

        // Check if the card date is valid
        if (cardDate.getText().length() != 5) {
            errorLabel.setText("Please enter a valid card date");
            return;
        }

        // Check if the cvc is valid
        if (cvc.getText().length() != 3) {
            errorLabel.setText("Please enter a valid cvc");
            return;
        }

        String receiptNumber = GenerateCode.receiptCode();
        String transactionNumber = GenerateCode.transactionCode();

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
        StorageBucket.getCart().clear();

        // Change to thankyou screen
        Controller.changeScene("/com/troo/screens/Thankyou.fxml", event);

    }

    public void back(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Cart.fxml", event);
    }
}
