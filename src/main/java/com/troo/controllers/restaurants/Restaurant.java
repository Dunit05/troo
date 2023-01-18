// By: Andrew
// Sprint: 4

package com.troo.controllers.restaurants;

public class Restaurant {

    // Data Fields
    private String name, imagePath, rating, fileName, description, location;

    // Constructors
    public Restaurant() {
        this.name = "";
        this.fileName = "";
        this.imagePath = "";
        this.rating = "";
        this.description = "";
        this.location = "";
    }

    public Restaurant(String name, String imagePath, String rating, String fileName, String description,
            String loaction) {
        this.name = name;
        this.imagePath = imagePath;
        this.rating = rating;
        this.fileName = fileName;
        this.description = description;
        this.location = loaction;
    }

    // Getters and Setters
    public String getName() {
        return this.name;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public String getRating() {
        return this.rating;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLocation() {
        return this.location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // toString
    public String toString() {
        return getName() + " " + getRating();
    }

}
