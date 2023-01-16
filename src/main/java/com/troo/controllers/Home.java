// By: Tommy
// Sprint:

package com.troo.controllers;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import java.io.IOException;

import java.awt.Toolkit;

import com.troo.controllers.restaurants.Restaurant;
import com.troo.controllers.util.Controller;
import com.troo.controllers.util.StorageBucket;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class Home implements Initializable {

    // Get all the FXML elements
    @FXML
    private ListView<Restaurant> restaurants;

    @FXML
    private Label greeting;

    @FXML
    private Button checkoutButton;

    @FXML
    private TextField searchField;

    private ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();

    // Override the initialize method to load all the page data
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the greeting
        String userName = StorageBucket.getUserName();
        greeting.setText("Hello, " + userName + "!");
        // Set the checkout button text
        if (StorageBucket.getCartAmount() == 0) {
            checkoutButton.setText("Checkout");
            checkoutButton.setDisable(true);
        } else {
            checkoutButton.setText("Checkout (" + StorageBucket.getCartAmount() + ")");
        }
        // Loop through the restaurants folder and grab each file name and add make a
        // new restaurant object and add it to the list
        String folderPath = "src/main/resources/com/troo/data/restaurants";
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                String fileName = "", imagePath = "", restaurantName = "", restaurantRating = "",
                        restaurantDescription = "";
                fileName = file.getName();
                imagePath = fileName.substring(0, fileName.length() - 4);
                restaurantName = fileName.substring(0, fileName.length() - 4);
                restaurantName = restaurantName.replace("_", " ");

                String[] words = restaurantName.split(" ");
                restaurantName = "";
                for (String word : words) {
                    restaurantName += word.substring(0, 1).toUpperCase() + word.substring(1) + " ";
                }

                // Read setup file data
                try {
                    FileReader reader = new FileReader(file);
                    BufferedReader br = new BufferedReader(reader);
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.contains("--Data--")) {
                            restaurantRating = br.readLine();
                            restaurantDescription = br.readLine();
                        }
                        line = br.readLine();
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // add the nameless object to the restaurants listview
                restaurants.getItems().add(
                        new Restaurant(restaurantName, imagePath, restaurantRating, fileName, restaurantDescription));
                restaurantList.add(
                        new Restaurant(restaurantName, imagePath, restaurantRating, fileName, restaurantDescription));

            }
        }

        // Set the cell factory for the restaurants listview to customize the listview
        // cells
        restaurants.setCellFactory(param -> new ListCell<Restaurant>() {
            @Override
            public void updateItem(Restaurant restaurant, boolean empty) {
                super.updateItem(restaurant, empty);
                if (empty || restaurant == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String text = restaurant.getName() + "\n" + restaurant.getRating() + " Restaurant Rating\n"
                            + restaurant.getDescription();
                    String imagePath = "https://images-furot-tech.netlify.app/" + restaurant.getImagePath() + ".png";
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

    // Search for a restaurant method
    public void search() {
        String search = searchField.getText();

        searchField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
                restaurants.getItems().clear();
                for (Restaurant restaurant : restaurantList) {
                    restaurants.getItems().add(restaurant);
                }
            } else {
                ObservableList<Restaurant> items = FXCollections.observableArrayList();
                for (Restaurant restaurant : restaurants.getItems()) {
                    if (restaurant.getName().toLowerCase().contains(search.toLowerCase())) {
                        items.add(restaurant);
                    }
                }
                restaurants.setItems(items);
            }
        });

    }

    // Go to the dynamic restaurant page to order
    public void order(MouseEvent e) {
        restaurants.setOnMouseClicked(event -> {
            Restaurant restaurant = restaurants.getSelectionModel().getSelectedItem();
            StorageBucket.setChoosenRestaurant(restaurant);
            Controller.changeToOrderScene("/com/troo/screens/Order.fxml", e);
        });
    }

    // Logout method
    public void logout(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Login.fxml", event);
        StorageBucket.resetUser();
        StorageBucket.resetCart();
    }

    // Go to the cart page
    public void toCart(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Cart.fxml", event);
    }

}
