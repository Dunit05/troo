package com.troo.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.troo.controllers.menu.Item;
import com.troo.controllers.util.Controller;
import com.troo.controllers.util.StorageBucket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cart implements Initializable {

    @FXML
    Label amount, subtotal;

    @FXML
    ListView<Item> cartList;

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
                            + item.getNutritionNotes();
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

    public void back(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Login.fxml", event);
    }

    public void toCheckout(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Checkout.fxml", event);
    }

}
