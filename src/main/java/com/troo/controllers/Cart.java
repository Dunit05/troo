// Name: Tommy
// Sprint:
package com.troo.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.troo.controllers.menu.Item;
import com.troo.controllers.util.Controller;
import com.troo.controllers.util.SetDarkMode;
import com.troo.controllers.util.StorageBucket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cart implements Initializable {

    // Get all the FXML elements
    @FXML
    Label amount, subtotal, greetingLabel, greetingLabel2, infoLabel, helpLabel;

    @FXML
    Button payButton, backButton, deleteButton;

    @FXML
    ListView<Item> cartList;

    @FXML
    CheckBox darkModeCheckBox;

    // Override the initialize method to load all the page data
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // for every item in the StorageBucket.cart, add it to the cartList
        for (Item item : StorageBucket.getCart()) {
            cartList.getItems().add(item);
        }

        // Set the cartInfo
        amount.setText(
                "Amount of Items: " + StorageBucket.getCartAmount());
        subtotal.setText("Subtotal: $" + StorageBucket.getCartTotal());

        // Set the cell factory for the cartList
        cartList.setCellFactory(param -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getImagePath() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String text = "Dish: " + item.getName() + "\nPrice: $" + item.getPrice() + "\nDescription: "
                            + item.getDescription() + "\nNutrition Notes: "
                            + item.getNutritionNotes() + "\nRestaurant: " + item.getRestaurantName();
                    String imagePath = "https://images-furot-tech.netlify.app/" + item.getImagePath() + ".png";
                    Image image = new Image(imagePath);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(80);
                    imageView.setFitWidth(80);
                    setText(text);
                    setGraphic(imageView);
                }
            }
        });
    }

    // Method to delete an item from the cart
    public void delete(ActionEvent event) {
        // Get the selected item
        Item item = cartList.getSelectionModel().getSelectedItem();

        // Remove the item from the cart
        StorageBucket.getCart().remove(item);

        // Remove the item from the cartList
        cartList.getItems().remove(item);

        // Update the cartList
        cartList.refresh();

        // Update the cartInfo
        amount.setText(
                "Amount of Items: " + StorageBucket.getCartAmount());
        subtotal.setText("Subtotal: $" + StorageBucket.getCartTotal());
    }

    // Method to go back to the menu
    public void back(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Login.fxml", event);
    }

    // Method to go to the checkout screen
    public void toCheckout(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Checkout.fxml", event);
    }

    // Method to set the dark mode on the cart screen
    public void setDarkModeCartScreen(ActionEvent event) {
        // see if the checkbox is selected
        if (darkModeCheckBox.isSelected()) {
            SetDarkMode.setDarkModeListView(cartList);
            SetDarkMode.setDarkModeLabel(amount);
            SetDarkMode.setDarkModeLabel(subtotal);
            SetDarkMode.setDarkModeLabel(greetingLabel);
            SetDarkMode.setDarkModeLabel(greetingLabel2);
            SetDarkMode.setDarkModeLabel(infoLabel);
            SetDarkMode.setDarkModeLabel(helpLabel);
            SetDarkMode.setPrimaryDarkModeButton(payButton);
            SetDarkMode.setSecondaryDarkModeButton(deleteButton);
            SetDarkMode.setSecondaryDarkModeButton(backButton);
            SetDarkMode.setDarkModeCheckBox(darkModeCheckBox);
        } else {
            SetDarkMode.removeDarkModeListView(cartList);
            SetDarkMode.removeDarkModeLabel(amount);
            SetDarkMode.removeDarkModeLabel(subtotal);
            SetDarkMode.removeDarkModeLabel(greetingLabel);
            SetDarkMode.removeDarkModeLabel(greetingLabel2);
            SetDarkMode.removeDarkModeLabel(infoLabel);
            SetDarkMode.removeDarkModeLabel(helpLabel);
            SetDarkMode.removePrimaryDarkModeButton(payButton);
            SetDarkMode.removeSecondaryDarkModeButton(deleteButton);
            SetDarkMode.removeSecondaryDarkModeButton(backButton);
            SetDarkMode.removeDarkModeCheckBox(darkModeCheckBox);
        }
    }
}
