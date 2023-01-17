package com.troo.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;

import com.troo.controllers.menu.Item;
import com.troo.controllers.util.Controller;
import com.troo.controllers.util.StorageBucket;
import com.troo.controllers.util.Error;
import com.troo.controllers.util.SetDarkMode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Order implements Initializable {
    @FXML
    private Label restaurantName, errorLabel, infoLabel1, infoLabel2, infoLabel3, helpLabel, appetizerLabel1,
            entreeLabel1,
            dessertLabel1, drinkLabel1, quantityLabel1, quantityLabel2, quantityLabel3, quantityLabel4;

    @FXML
    private Button addToCartButton, backButton;

    @FXML
    private ListView<Item> appetizersList, entreesList, dessertsList, drinksList;

    @FXML
    private TextField appetizersQuantityField, entreesQuantityField, dessertsQuantityField, drinksQuantityField;

    @FXML
    private CheckBox darkModeCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the restaurant name
        String restaurantName = StorageBucket.getChoosenRestaurant().getName(),
                fileName = StorageBucket.getChoosenRestaurant().getFileName();
        this.restaurantName.setText("Order Now from " + restaurantName + "...");

        // Set the appetizers list
        String folderPath = "src/main/resources/com/troo/data/restaurants";
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        // Go to the file an to read the data
        for (File file : files) {
            if (file.getName().equals(fileName)) {
                // Read the appetizers
                try {
                    FileReader reader = new FileReader(file);
                    BufferedReader br = new BufferedReader(reader);
                    String line = "", itemName = "", itemDescription = "", itemNutritionNotes = "",
                            itemImagePath = "";
                    double itemPrepTime = 0, itemPrice = 0;

                    while ((line = br.readLine()) != null) {
                        if (line.equals("--Appetizer--")) {
                            itemName = br.readLine();
                            itemName = itemName.substring(1, itemName.length());
                            itemPrice = Double.parseDouble(br.readLine());
                            itemDescription = br.readLine();
                            itemNutritionNotes = br.readLine();
                            itemPrepTime = Double.parseDouble(br.readLine());
                            itemImagePath = br.readLine();
                            itemImagePath = itemImagePath.substring(0, itemImagePath.length() - 1);

                            appetizersList.getItems().add(new Item(itemName, itemDescription, itemPrice,
                                    itemNutritionNotes, itemPrepTime, itemImagePath, restaurantName));
                        }

                        if (line.equals("--Entree--")) {
                            itemName = br.readLine();
                            itemName = itemName.substring(1, itemName.length());
                            itemPrice = Double.parseDouble(br.readLine());
                            itemDescription = br.readLine();
                            itemNutritionNotes = br.readLine();
                            itemPrepTime = Double.parseDouble(br.readLine());
                            itemImagePath = br.readLine();
                            itemImagePath = itemImagePath.substring(0, itemImagePath.length() - 1);

                            entreesList.getItems().add(new Item(itemName, itemDescription, itemPrice,
                                    itemNutritionNotes, itemPrepTime, itemImagePath, restaurantName));
                        }

                        if (line.equals("--Dessert--")) {
                            itemName = br.readLine();
                            itemName = itemName.substring(1, itemName.length());
                            itemPrice = Double.parseDouble(br.readLine());
                            itemDescription = br.readLine();
                            itemNutritionNotes = br.readLine();
                            itemPrepTime = Double.parseDouble(br.readLine());
                            itemImagePath = br.readLine();
                            itemImagePath = itemImagePath.substring(0, itemImagePath.length() - 1);

                            dessertsList.getItems().add(new Item(itemName, itemDescription, itemPrice,
                                    itemNutritionNotes, itemPrepTime, itemImagePath, restaurantName));
                        }

                        if (line.equals("--Drink--")) {
                            itemName = br.readLine();
                            itemName = itemName.substring(1, itemName.length());
                            itemPrice = Double.parseDouble(br.readLine());
                            itemDescription = br.readLine();
                            itemNutritionNotes = br.readLine();
                            itemPrepTime = Double.parseDouble(br.readLine());
                            itemImagePath = br.readLine();
                            itemImagePath = itemImagePath.substring(0, itemImagePath.length() - 1);

                            drinksList.getItems().add(new Item(itemName, itemDescription, itemPrice,
                                    itemNutritionNotes, itemPrepTime, itemImagePath, restaurantName));
                        }
                    }
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        // Set the cell factory for the appetizersList listview to customize the
        // listview cells
        appetizersList.setCellFactory(param -> new ListCell<Item>() {
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

        // Set the cell factory for the entreesList listview to customize the listview
        // cells
        entreesList.setCellFactory(param -> new ListCell<Item>() {
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

        // Set the cell factory for the dessertsList listview to customize the listview
        // cells
        dessertsList.setCellFactory(param -> new ListCell<Item>() {
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

        // Set the cell factory for the drinksList listview to customize the listview
        // cells
        drinksList.setCellFactory(param -> new ListCell<Item>() {
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

    public void addToCart(ActionEvent event) {
        int appetizersQuantity = 0, entreesQuantity = 0, dessertsQuantity = 0, drinksQuantity = 0;
        // Get the selected item from the listview
        Item selectedAppetizersItem = appetizersList.getSelectionModel().getSelectedItem();
        Item selectedEntreesItem = entreesList.getSelectionModel().getSelectedItem();
        Item selectedDessertsItem = dessertsList.getSelectionModel().getSelectedItem();
        Item selectedDrinksItem = drinksList.getSelectionModel().getSelectedItem();

        if (appetizersQuantityField.getText().length() < 0 && selectedAppetizersItem == null) {
            Error.setError("Please select an appetizer and enter a valid quantity (Above 0)", errorLabel);
            Error.setTextFieldErrorBorder(appetizersQuantityField);
            return;
        } else {
            Error.removeError(errorLabel);
            Error.removeTextFieldErrorBorder(appetizersQuantityField);
        }

        if (entreesQuantityField.getText().length() < 0 && selectedEntreesItem == null) {
            Error.setError("Please select an entree and enter a valid quantity (Above 0)", errorLabel);
            Error.setTextFieldErrorBorder(entreesQuantityField);
            return;
        } else {
            Error.removeError(errorLabel);
            Error.removeTextFieldErrorBorder(entreesQuantityField);
        }

        if (dessertsQuantityField.getText().length() < 0 && selectedDessertsItem == null) {
            Error.setError("Please select a dessert and enter a valid quantity (Above 0)", errorLabel);
            Error.setTextFieldErrorBorder(dessertsQuantityField);
            return;
        } else {
            Error.removeError(errorLabel);
            Error.removeTextFieldErrorBorder(dessertsQuantityField);
        }

        if (drinksQuantityField.getText().length() < 0 && selectedDrinksItem == null) {
            Error.setError("Please select a drink and enter a valid quantity (Above 0)", errorLabel);
            Error.setTextFieldErrorBorder(drinksQuantityField);
            return;
        } else {
            Error.removeError(errorLabel);
            Error.removeTextFieldErrorBorder(drinksQuantityField);
        }

        if (appetizersQuantityField.getText().length() > 0 && selectedAppetizersItem != null) {
            appetizersQuantity = Integer.parseInt(appetizersQuantityField.getText());
        }

        if (entreesQuantityField.getText().length() > 0 && selectedEntreesItem != null) {
            entreesQuantity = Integer.parseInt(entreesQuantityField.getText());
        }

        if (dessertsQuantityField.getText().length() > 0 && selectedDessertsItem != null) {
            dessertsQuantity = Integer.parseInt(dessertsQuantityField.getText());
        }

        if (drinksQuantityField.getText().length() > 0 && selectedDrinksItem != null) {
            drinksQuantity = Integer.parseInt(drinksQuantityField.getText());
        }

        // Get the corresponding item from the listview and the quantity from the
        // quantity field and add the Item to the cart that many times
        for (int i = 0; i < appetizersQuantity; i++) {
            StorageBucket.addToCart(selectedAppetizersItem);
        }

        for (int i = 0; i < entreesQuantity; i++) {
            StorageBucket.addToCart(selectedEntreesItem);
        }

        for (int i = 0; i < dessertsQuantity; i++) {
            StorageBucket.addToCart(selectedDessertsItem);
        }

        for (int i = 0; i < drinksQuantity; i++) {
            StorageBucket.addToCart(selectedDrinksItem);
        }

        Controller.changeScene("/com/troo/screens/Home.fxml", event);
    }

    // Back button
    public void back(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Home.fxml", event);
    }

    // To Cart Button
    public void cart(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Cart.fxml", event);
    }

    public void setDarkModeOrderScreen(ActionEvent event) {
        // see if the checkbox is selected
        if (darkModeCheckBox.isSelected()) {
            SetDarkMode.setDarkModeTextField(appetizersQuantityField);
            SetDarkMode.setDarkModeTextField(entreesQuantityField);
            SetDarkMode.setDarkModeTextField(dessertsQuantityField);
            SetDarkMode.setDarkModeTextField(drinksQuantityField);
            SetDarkMode.setDarkModeListView(appetizersList);
            SetDarkMode.setDarkModeListView(entreesList);
            SetDarkMode.setDarkModeListView(dessertsList);
            SetDarkMode.setDarkModeListView(drinksList);
            SetDarkMode.setDarkModeLabel(errorLabel);
            SetDarkMode.setDarkModeLabel(restaurantName);
            SetDarkMode.setDarkModeLabel(infoLabel1);
            SetDarkMode.setDarkModeLabel(infoLabel2);
            SetDarkMode.setDarkModeLabel(infoLabel3);
            SetDarkMode.setDarkModeLabel(appetizerLabel1);
            SetDarkMode.setDarkModeLabel(entreeLabel1);
            SetDarkMode.setDarkModeLabel(dessertLabel1);
            SetDarkMode.setDarkModeLabel(drinkLabel1);
            SetDarkMode.setDarkModeLabel(quantityLabel1);
            SetDarkMode.setDarkModeLabel(quantityLabel2);
            SetDarkMode.setDarkModeLabel(quantityLabel3);
            SetDarkMode.setDarkModeLabel(quantityLabel4);
            SetDarkMode.setDarkModeLabel(helpLabel);
            SetDarkMode.setDarkModeCheckBox(darkModeCheckBox);
            SetDarkMode.setPrimaryDarkModeButton(addToCartButton);
            SetDarkMode.setSecondaryDarkModeButton(backButton);

        } else {
            SetDarkMode.removeDarkModeTextField(appetizersQuantityField);
            SetDarkMode.removeDarkModeTextField(entreesQuantityField);
            SetDarkMode.removeDarkModeTextField(dessertsQuantityField);
            SetDarkMode.removeDarkModeTextField(drinksQuantityField);
            SetDarkMode.removeDarkModeListView(appetizersList);
            SetDarkMode.removeDarkModeListView(entreesList);
            SetDarkMode.removeDarkModeListView(dessertsList);
            SetDarkMode.removeDarkModeListView(drinksList);
            SetDarkMode.removeDarkModeLabel(errorLabel);
            SetDarkMode.removeDarkModeLabel(restaurantName);
            SetDarkMode.removeDarkModeLabel(infoLabel1);
            SetDarkMode.removeDarkModeLabel(infoLabel2);
            SetDarkMode.removeDarkModeLabel(infoLabel3);
            SetDarkMode.removeDarkModeLabel(appetizerLabel1);
            SetDarkMode.removeDarkModeLabel(entreeLabel1);
            SetDarkMode.removeDarkModeLabel(dessertLabel1);
            SetDarkMode.removeDarkModeLabel(drinkLabel1);
            SetDarkMode.removeDarkModeLabel(quantityLabel1);
            SetDarkMode.removeDarkModeLabel(quantityLabel2);
            SetDarkMode.removeDarkModeLabel(quantityLabel3);
            SetDarkMode.removeDarkModeLabel(quantityLabel4);
            SetDarkMode.removeDarkModeLabel(helpLabel);
            SetDarkMode.removeDarkModeCheckBox(darkModeCheckBox);
            SetDarkMode.removePrimaryDarkModeButton(addToCartButton);
            SetDarkMode.removeSecondaryDarkModeButton(backButton);
        }
    }
}
