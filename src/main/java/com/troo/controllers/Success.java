package com.troo.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.troo.controllers.util.Controller;
import com.troo.controllers.util.SetDarkMode;
import com.troo.controllers.util.StorageBucket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Success implements Initializable {
    @FXML
    Label orderNumber, greetingLabel, deliveryTime, orLabel, helpLabel;

    @FXML
    ImageView qrCode;

    @FXML
    CheckBox darkModeCheckBox;

    @FXML
    Button homeButton, logoutButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orderNumber.setText("Order Number #" + StorageBucket.getReceiptNumber());
        deliveryTime.setText("Your order will be delivered in " + StorageBucket.getDeliveryTime() + " minutes");

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
        StorageBucket.resetCart();
        StorageBucket.resetUser();
    }

    public void setDarkModeSuccessScreen(ActionEvent event) {
        if (darkModeCheckBox.isSelected()) {
            SetDarkMode.setDarkModeLabel(orderNumber);
            SetDarkMode.setDarkModeLabel(greetingLabel);
            SetDarkMode.setDarkModeLabel(deliveryTime);
            SetDarkMode.setDarkModeLabel(orLabel);
            SetDarkMode.setDarkModeLabel(helpLabel);
            SetDarkMode.setDarkModeCheckBox(darkModeCheckBox);
            SetDarkMode.setPrimaryDarkModeButton(homeButton);
            SetDarkMode.setSecondaryDarkModeButton(logoutButton);
        } else {
            SetDarkMode.removeDarkModeLabel(orderNumber);
            SetDarkMode.removeDarkModeLabel(greetingLabel);
            SetDarkMode.removeDarkModeLabel(deliveryTime);
            SetDarkMode.removeDarkModeLabel(orLabel);
            SetDarkMode.removeDarkModeLabel(helpLabel);
            SetDarkMode.removeDarkModeCheckBox(darkModeCheckBox);
            SetDarkMode.removePrimaryDarkModeButton(homeButton);
            SetDarkMode.removeSecondaryDarkModeButton(logoutButton);
        }
    }
}
