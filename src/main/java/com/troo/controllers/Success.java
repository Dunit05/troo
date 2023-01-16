package com.troo.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.troo.controllers.util.Controller;
import com.troo.controllers.util.StorageBucket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Success implements Initializable {
    @FXML
    Label orderNumber;

    @FXML
    ImageView qrCode;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orderNumber.setText("Order Number #" + StorageBucket.getReceiptNumber());

        // set the qr code image to the qr code (transaction code.png)
        String filePath = "src/main/resources/com/troo/data/transaction_data/" + StorageBucket.getTransactionNumber()
                + ".png";
        File file = new File(filePath);
        Image image = new Image(file.toURI().toString());
        qrCode.setImage(image);

    }

    public void home(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Home.fxml", event);
    }

    public void logout(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Login.fxml", event);
    }

}
