//Name: Suchir
//Sprint: 4

package com.troo.controllers.menu;

public class Item {

    private String name, descprition, itemImagePath, nutritionNotes, restaurantName;
    private double price, prepTime;

    // constructors
    public Item() {
        this.name = "";
        this.price = 0;
        this.descprition = "";
        this.nutritionNotes = "";
        this.prepTime = 0;
        this.itemImagePath = "";
        this.restaurantName = "";
    }

    public Item(String name, String descprition, double price, String nutritionNotes, double prepTime,
            String itemImagePath, String restaurantName) {
        this.name = name;
        this.price = price;
        this.descprition = descprition;
        this.nutritionNotes = nutritionNotes;
        this.prepTime = prepTime;
        this.itemImagePath = itemImagePath;
        this.restaurantName = restaurantName;
    }

    // accessors
    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public String getDescription() {
        return this.descprition;
    }

    public String getNutritionNotes() {
        return this.nutritionNotes;
    }

    public double getPrepTime() {
        return this.prepTime;
    }

    public String getImagePath() {
        return this.itemImagePath;
    }

    public String getRestaurantName() {
        return this.restaurantName;
    }

    // mutators
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String descprition) {
        this.descprition = descprition;
    }

    public void setNutritionNotes(String nutritionNotes) {
        this.nutritionNotes = nutritionNotes;
    }

    public void setPrepTime(double prepTime) {
        this.prepTime = prepTime;
    }

    public void setItemImagePath(String itemImagePath) {
        this.itemImagePath = itemImagePath;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String toString() {
        return ("Dish: " + name + "\nPrice: " + price + "\nDescription: " + descprition + "\nNutrition List: "
                + nutritionNotes + "\nPrep Time: " + prepTime + "\nItem Image Path: " + itemImagePath
                + "\nRestaurant Name: " + getRestaurantName());
    }

}
