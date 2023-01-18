// By: Tommy
// Sprint: 3

package com.troo.controllers.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.util.UUID;

// Import the required libraries
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.HttpUrl.Builder;

public class AddressAutocomplete {

    // Auto complete address function, takes in a String address and a UUID
    public static String autocompleteAddress(String address, UUID uuid) {
        // Open a new dotenv file to get the Google Maps API key
        Dotenv dotenv = Dotenv.load();

        // API URL:
        // https://maps.googleapis.com/maps/api/place/autocomplete/json?input=Paris&types=geocode&key=YOUR_API_KEY"

        // Create an HTTPs request to maps.googleapis.com to predict the address
        OkHttpClient client = new OkHttpClient();

        // Sends a request to the Google Maps API to predict the address
        Builder url = new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegment("maps")
                .addPathSegment("api")
                .addPathSegment("place")
                .addPathSegment("autocomplete")
                .addPathSegment("json")
                .addQueryParameter("input", address)
                .addQueryParameter("sessiontoken", uuid.toString())
                .addQueryParameter("key", dotenv.get("GOOGLE_MAPS_API_KEY"));

        // Create a new request object
        Request request = new Request.Builder()
                .url(url.build())
                .build();
        // Try to send the request
        try {
            Response response = client.newCall(request).execute();
            // Return the response
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return an empty string if the request fails
        return "";
    }
}
