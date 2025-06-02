package org.example.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiHandler {

    public String getPokemons(String pokemonName) {
        StringBuilder jsonResponse = new StringBuilder();
        try {
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + pokemonName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                jsonResponse.append(response);
                System.out.println(jsonResponse);
            } else {
                System.out.println("Chyba: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResponse.toString();
    }
}
