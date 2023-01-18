// By: Tommy
// Sprint: 5

package com.troo.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.io.IOException;

import com.troo.controllers.restaurants.Restaurant;
import com.troo.controllers.util.Controller;
import com.troo.controllers.util.SetDarkMode;
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
    private Label greetingNameLabel, greetingLabel, searchLabel, searchedLabel, helpLabel;

    @FXML
    private CheckBox darkModeCheckBox;

    @FXML
    private Button checkoutButton, logoutButton;

    @FXML
    private TextField searchField;

    // Data Fields
    private ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();

    // Override the initialize method to load all the page data
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the greeting
        String userName = StorageBucket.getUserName();
        greetingNameLabel.setText("Hello, " + userName + "!");
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

        // Loop through the files in the restaurants folder
        for (File file : files) {
            if (file.isFile()) {
                String fileName = "", imagePath = "", restaurantName = "", restaurantRating = "",
                        restaurantDescription = "", restaurantLocation = "";
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
                            restaurantLocation = br.readLine();

                        }
                        line = br.readLine();
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // add the nameless object to the restaurants listview
                restaurants.getItems().add(
                        new Restaurant(restaurantName, imagePath, restaurantRating, fileName, restaurantDescription,
                                restaurantLocation));
                restaurantList.add(
                        new Restaurant(restaurantName, imagePath, restaurantRating, fileName, restaurantDescription,
                                restaurantLocation));

            }
        }

        // Set the cell factory for the restaurants listview to customize the listview
        // cells
        restaurants.setCellFactory(param -> new ListCell<Restaurant>() {
            @Override
            public void updateItem(Restaurant restaurant, boolean empty) {
                super.updateItem(restaurant, empty);

                // If the item is empty, set the text and graphic to null, meaning no data will
                // be shown in that cell
                if (empty || restaurant == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // If the item is not empty, set the text and graphic
                    String text = restaurant.getName() + "\n" + restaurant.getRating() + " Restaurant Rating\n"
                            + restaurant.getDescription() + "\n" + restaurant.getLocation();
                    String imagePath = StorageBucket.serverUrl + restaurant.getImagePath() + ".png";
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

        // Add an event listener to the search field to search for restaurants
        searchField.setOnKeyReleased(event -> {
            // If the backspace or delete key is pressed, clear the listview and add all the
            // default restaurants
            if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
                restaurants.getItems().clear();
                for (Restaurant restaurant : restaurantList) {
                    restaurants.getItems().add(restaurant);
                }
            } else {
                // If any other key is pressed, clear the listview and add the restaurants that
                // match the search
                ObservableList<Restaurant> items = FXCollections.observableArrayList();
                for (Restaurant restaurant : restaurants.getItems()) {
                    if (restaurant.getName().toLowerCase().contains(search.toLowerCase())) {
                        items.add(restaurant);
                    }
                }
                // Add the restaurants that match the search to the listview
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

    // Andrew's code
    // Set the dark mode for the Home screen
    public void setDarkModeHomeScreen(ActionEvent event) {
        if (darkModeCheckBox.isSelected()) {
            SetDarkMode.setDarkModeTextField(searchField);
            SetDarkMode.setDarkModeListView(restaurants);
            SetDarkMode.setDarkModeLabel(greetingLabel);
            SetDarkMode.setDarkModeLabel(greetingNameLabel);
            SetDarkMode.setDarkModeLabel(searchLabel);
            SetDarkMode.setDarkModeLabel(searchedLabel);
            SetDarkMode.setDarkModeLabel(helpLabel);
            SetDarkMode.setPrimaryDarkModeButton(checkoutButton);
            SetDarkMode.setSecondaryDarkModeButton(logoutButton);
            SetDarkMode.setDarkModeCheckBox(darkModeCheckBox);
        } else {
            SetDarkMode.removeDarkModeTextField(searchField);
            SetDarkMode.removeDarkModeListView(restaurants);
            SetDarkMode.removeDarkModeLabel(greetingLabel);
            SetDarkMode.removeDarkModeLabel(greetingNameLabel);
            SetDarkMode.removeDarkModeLabel(searchLabel);
            SetDarkMode.removeDarkModeLabel(searchedLabel);
            SetDarkMode.removeDarkModeLabel(helpLabel);
            SetDarkMode.removePrimaryDarkModeButton(checkoutButton);
            SetDarkMode.removeSecondaryDarkModeButton(logoutButton);
            SetDarkMode.removeDarkModeCheckBox(darkModeCheckBox);
        }
    }
}
