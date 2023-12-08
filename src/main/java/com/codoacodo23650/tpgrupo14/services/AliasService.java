package com.codoacodo23650.tpgrupo14.services;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@UtilityClass
public class AliasService {
    public String getAliasFromApi(String nombre) {
        try {
            String apiUrl = "http://localhost:3000/api/alias/"+nombre;
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            nombre = response.toString().split(":")[1].replaceAll("[\"{}]", "").trim();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

            return nombre;

    }
}
