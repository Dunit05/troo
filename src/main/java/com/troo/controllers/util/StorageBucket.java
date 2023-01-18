// Name: Andrew
// Sprint: 4

package com.troo.controllers.util;

import java.util.ArrayList;

import com.troo.controllers.menu.Item;
import com.troo.controllers.restaurants.Restaurant;

public class StorageBucket {

    // Data Fields / Storage
    private static ArrayList<String> userData = new ArrayList<String>();
    private static ArrayList<Item> cart = new ArrayList<Item>();
    private static Restaurant chosenRestaurant;
    private static String userReciptNumber;
    private static String transactionNumber;
    private static double totalTime;
    public final static double TAX = 0.13;
    public final static double DELIVERY_FEE = 0.25;
    public final static String serverUrl = "https://images-furot-tech.netlify.app/";

    // Methods (Setters and Getters)
    public static void setUser(String email, String firstName, String lastName, String phone, String address) {
        userData.add(email);
        userData.add(firstName);
        userData.add(lastName);
        userData.add(phone);
        userData.add(address);
    }

    public static void setChoosenRestaurant(Restaurant restaurant) {
        chosenRestaurant = restaurant;
    }

    public static void setReceiptNumber(String reciptNumber) {
        userReciptNumber = reciptNumber;
    }

    public static void setTransactionNumber(String number) {
        transactionNumber = number;
    }

    public static void setDeliveryTime(double time) {
        totalTime = time;
    }

    public static void addToCart(Item item) {
        cart.add(item);
    }

    public static Restaurant getChoosenRestaurant() {
        return chosenRestaurant;
    }

    public static String getUserName() {
        return userData.get(1) + " " + userData.get(2);
    }

    public static String getUserEmail() {
        return userData.get(0);
    }

    public static String getUserPhone() {
        return userData.get(3);
    }

    public static String getUserAddress() {
        return userData.get(4);
    }

    public static int getCartAmount() {
        return cart.size();
    }

    public static String getCartTotal() {
        double total = 0;
        for (Item item : cart) {
            total += item.getPrice();
        }
        return String.format("%.2f", total);
    }

    public static double getDeliveryFee() {
        double total = 0;
        for (Item item : cart) {
            total += item.getPrice();
        }
        total = total * DELIVERY_FEE;
        return total;
    }

    public static String getCartTotalWithTax() {
        double total = 0, feeTotal = 0;
        for (Item item : cart) {
            total += item.getPrice();
        }
        feeTotal = total * DELIVERY_FEE;
        total = total + feeTotal;
        return String.format("%.2f", total);
    }

    public static ArrayList<Item> getCart() {
        return cart;
    }

    public static String getReceiptNumber() {
        return userReciptNumber;
    }

    public static String getTransactionNumber() {
        return transactionNumber;
    }

    public static double getDeliveryTime() {
        return totalTime;
    }

    // Methods (Other)
    public static void resetUser() {
        userData.clear();
    }

    public static void resetCart() {
        cart.clear();
    }

    // toString
    public String toString() {
        return "User: " + userData + " Cart: " + cart;
    }
}
