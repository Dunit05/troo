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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Order implements Initializable {
    @FXML
    private Label restaurantName;

    @FXML
    private Label errorLabel;

    @FXML
    private Button addToCartButton;

    @FXML
    private ListView<Item> appetizersList;

    @FXML
    private ListView<Item> entreesList;

    @FXML
    private ListView<Item> dessertsList;

    @FXML
    private ListView<Item> drinksList;

    @FXML
    private TextField appetizersQuantityField;

    @FXML
    private TextField entreesQuantityField;

    @FXML
    private TextField dessertsQuantityField;

    @FXML
    private TextField drinksQuantityField;

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

        if (appetizersQuantityField.getText().length() > 0 && selectedAppetizersItem == null) {
            Error.setError("Please select an appetizer and enter a valid quantity (Above 0)", errorLabel);
            return;
        }

        if (entreesQuantityField.getText().length() > 0 && selectedEntreesItem == null) {
            Error.setError("Please select an entree and enter a valid quantity (Above 0)", errorLabel);
            return;
        }

        if (dessertsQuantityField.getText().length() > 0 && selectedDessertsItem == null) {
            Error.setError("Please select a dessert and enter a valid quantity (Above 0)", errorLabel);
            return;
        }

        if (drinksQuantityField.getText().length() > 0 && selectedDrinksItem == null) {
            Error.setError("Please select a drink and enter a valid quantity (Above 0)", errorLabel);
            return;
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

    public void cart(ActionEvent event) {
        Controller.changeScene("/com/troo/screens/Cart.fxml", event);
    }
}
