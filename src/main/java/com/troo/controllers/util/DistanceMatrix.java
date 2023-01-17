package com.troo.controllers.util;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.github.cdimascio.dotenv.Dotenv;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.HttpUrl.Builder;

public class DistanceMatrix {

    public static double getTime(String fromAddress, String toAddress) {

        JSONParser parser = new JSONParser();
        Object obj;
        JSONObject jsonObject;
        JSONArray results;
        JSONObject result;

        // Open a new dotenv file to get the Google Maps API key
        Dotenv dotenv = Dotenv.load();

        // https://maps.googleapis.com/maps/api/distancematrix/json?origins=Washington%2C%20DC&destinations=New%20York%20City%2C%20NY&units=imperial&key=YOUR_API_KEY

        // Create an HTTPs request to maps.googleapis.com to predict the address
        OkHttpClient client = new OkHttpClient();

        // Sends a request to the Google Maps API to predict the address
        Builder url = new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegment("maps")
                .addPathSegment("api")
                .addPathSegment("distancematrix")
                .addPathSegment("json")
                .addQueryParameter("origins", fromAddress)
                .addQueryParameter("destinations", toAddress)
                .addQueryParameter("key", dotenv.get("GOOGLE_MAPS_API_KEY"));

        // Create a new request object
        Request request = new Request.Builder()
                .url(url.build())
                .build();

        // Try to send the request
        try {
            Response response = client.newCall(request).execute();

            // Parse the response
            obj = parser.parse(response.body().string());
            jsonObject = (JSONObject) obj;
            JSONArray rows = (JSONArray) jsonObject.get("rows");
            JSONObject firstRow = (JSONObject) rows.get(0);
            JSONArray elements = (JSONArray) firstRow.get("elements");
            JSONObject firstElement = (JSONObject) elements.get(0);
            JSONObject duration = (JSONObject) firstElement.get("duration");
            String durationText = (String) duration.get("text");

            // Extract the number from the text
            String durationNumber = durationText.substring(0, durationText.indexOf(" "));

            // Parse the number into a double
            double durationValue = Double.parseDouble(durationNumber);

            // Return the response
            return durationValue;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Return an empty string if the request fails
        return 0;
    }
}
