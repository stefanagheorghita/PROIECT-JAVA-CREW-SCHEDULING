package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class GenderPrediction {
    public String predict(String name) {
        try {
            URL url = new URL("https://api.genderize.io?name=" + name);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                reader.close();

                JSONObject json = new JSONObject(response);
                String gender = json.optString("gender");
                double probability = json.optDouble("probability");

                System.out.println("Name: " + name);
                System.out.println("Predicted Gender: " + gender);
                System.out.println("Probability: " + probability);
                if(gender.equals("male")) {
                    return "M";
                } else {
                    return "F";
                }
            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "M";
    }
}
