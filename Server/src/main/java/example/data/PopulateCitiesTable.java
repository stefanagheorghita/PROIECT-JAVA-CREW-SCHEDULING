package example.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import example.model.entity.Country;
import org.json.JSONArray;
import org.json.JSONObject;

public class PopulateCitiesTable {
    private static final String API_KEY = "eb84c5818976bf7d43d545f74cb72b35"; // Replace with your OpenWeatherMap API key

    public static void main(String[] args) {
        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "proiect", "PROIECT");

            // Fetch countries from the database
            List<Country> countries = fetchCountries(connection);

            // Insert cities for each country
            insertCities(connection, countries);

            // Close the database connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<Country> fetchCountries(Connection connection) throws SQLException {
        List<Country> countries = new ArrayList<>();

        // Fetch countries from the database
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id, name FROM countries");

        // Create Country objects and add them to the list
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            countries.add(new Country(id, name));
        }

        // Close the statement and result set
        statement.close();
        resultSet.close();

        return countries;
    }

    private static void insertCities(Connection connection, List<Country> countries) throws SQLException {
        // Insert cities into the cities table
        PreparedStatement statement = connection.prepareStatement("INSERT INTO cities (id, name, country_id) VALUES (SEQUENCE2.nextval, ?, ?)");

        for (Country country : countries) {
            try {
                // Create the request URL for fetching cities from OpenWeatherMap
                String requestUrl = "https://api.openweathermap.org/data/2.5/find?q=" + country.getName() + "&type=accurate&appid=" + API_KEY;

                // Send the HTTP request and get the response
                HttpURLConnection apiConnection = (HttpURLConnection) new URL(requestUrl).openConnection();
                apiConnection.setRequestMethod("GET");
                apiConnection.connect();

                int responseCode = apiConnection.getResponseCode();
                if (responseCode != 200) {
                    throw new IOException("Unexpected response code: " + responseCode);
                }

                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                reader.close();

                // Parse the response JSON
                JSONObject json = new JSONObject(responseBuilder.toString());
                JSONArray cities = json.getJSONArray("list");

                // Limit the number of cities to insert per country
                int cityCount = Math.min(cities.length(), 5);

                if (cityCount == 0) {
                    System.out.println("No cities found for country: " + country.getName());
                } else {
                    // Iterate over the cities and insert them into the database
                    for (int i = 0; i < cityCount; i++) {
                        JSONObject city = cities.getJSONObject(i);
                        String cityName = city.getString("name");

                        // Set the values for the prepared statement
                        statement.setString(1, cityName);
                        statement.setLong(2, country.getId());

                        // Execute the prepared statement
                        statement.executeUpdate();
                    }
                }

                // Close the API connection
                apiConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Close the statement
        statement.close();
    }
}





//import java.io.IOException;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import example.model.entity.Country;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import org.json.JSONArray;
//import org.json.JSONObject;
//public class PopulateCitiesTable {
//    private static final String API_KEY = "e1f42e839a8345f1942429a46fd36f77"; // Replace with your OpenCage API key
//
//    public static void main(String[] args) {
//        try {
//            // Connect to the database
//            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "proiect", "PROIECT");
//
//            // Fetch countries from the database
//            List<Country> countries = fetchCountries(connection);
//
//            // Insert cities for each country
//            insertCities(connection, countries);
//
//            // Close the database connection
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static List<Country> fetchCountries(Connection connection) throws SQLException {
//        List<Country> countries = new ArrayList<>();
//
//        // Fetch countries from the database
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT id, name FROM countries");
//
//        // Create Country objects and add them to the list
//        while (resultSet.next()) {
//            long id = resultSet.getLong("id");
//            String name = resultSet.getString("name");
//            countries.add(new Country(id, name));
//        }
//
//        // Close the statement and result set
//        statement.close();
//        resultSet.close();
//
//        return countries;
//    }
//
//    private static void insertCities(Connection connection, List<Country> countries) throws SQLException {
//        // Insert cities into the cities table
//        PreparedStatement statement = connection.prepareStatement("INSERT INTO cities (id, name, country_id) VALUES (SEQUENCE2.nextval, ?, ?)");
//
//        // Configure OkHttpClient
//        OkHttpClient client = new OkHttpClient();
//
//        for (Country country : countries) {
//            try {
//                // Create the request URL for geocoding API
//                String requestUrl = "https://api.opencagedata.com/geocode/v1/json?key=" + API_KEY + "&q=" + country.getName() + "&limit=5";
//
//                // Create the request object
//                Request request = new Request.Builder()
//                        .url(requestUrl)
//                        .build();
//
//                // Execute the request and get the response
//                Response response = client.newCall(request).execute();
//                if (!response.isSuccessful()) {
//                    throw new IOException("Unexpected response code: " + response);
//                }
//
//                // Parse the response JSON
//                JSONObject json = new JSONObject(response.body().string());
//                JSONArray results = json.getJSONArray("results");
//
//                // Iterate over the results and insert cities into the database
//                for (int i = 0; i < results.length(); i++) {
//                    JSONObject result = results.getJSONObject(i);
//                    String cityName = result.getString("formatted");
//
//                    // Set the values for the prepared statement
//                    statement.setString(1, cityName);
//                    statement.setLong(2, country.getId());
//
//                    // Execute the prepared statement
//                    statement.executeUpdate();
//                }
//
//                // Close the response
//                response.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // Close the statement
//        statement.close();
//    }
//
////    private static class Country {
////        private final int id;
////        private final String name;
////
////        public Country(int id, String name) {
////            this.id = id;
////            this.name = name;
////        }
////
////        public int getId() {
////            return id;
////        }
////
////        public String getName() {
////            return name;
////        }
////    }
//}











//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//
//public class PopulateCitiesTable {
//    private static final String COUNTRIES_TABLE = "countries";
//    private static final String CITIES_TABLE = "cities";
//    private static final int MAX_CITIES_PER_COUNTRY = 5;
//    private static final String OPEN_CAGE_API_KEY = "e1f42e839a8345f1942429a46fd36f77";
//
//    public static void main(String[] args) {
//        try {
//            // Establish the database connection
//            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "proiect", "PROIECT");
//
//            // Query the countries table to retrieve the country data
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT id, name FROM " + COUNTRIES_TABLE);
//
//            // Iterate through the countries and populate the cities table
//            while (rs.next()) {
//                int countryId = rs.getInt("id");
//                String countryName = rs.getString("name");
//                insertCitiesForCountry(conn, countryId, countryName);
//            }
//
//            // Close the database connection
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void insertCitiesForCountry(Connection conn, int countryId, String countryName) throws SQLException {
//        // Prepare the SQL statement for inserting cities
//        String sql = "INSERT INTO " + CITIES_TABLE + " (id, name, country_id, created_at, updated_at) VALUES (?, ?, ?, SYSDATE, SYSDATE)";
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//
//        try {
//            // Encode the country name for use in the API URL
//            String encodedCountryName = URLEncoder.encode(countryName, "UTF-8");
//
//            // Query the OpenCage Geocoding API to fetch cities for the given country
//            String apiUrl = "https://api.opencagedata.com/geocode/v1/json?key=" + OPEN_CAGE_API_KEY + "&q=" + encodedCountryName;
//            String response = fetchDataFromAPI(apiUrl);
//
//            // Parse the JSON response
//            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
//            JsonArray results = jsonObject.getAsJsonArray("results");
//
//            // Iterate through the cities and insert them into the cities table
//            int count = 0;
//            for (JsonElement result : results) {
//                if (count >= MAX_CITIES_PER_COUNTRY) {
//                    break;
//                }
//
//                JsonObject cityObject = result.getAsJsonObject();
//                String cityName = cityObject.getAsJsonObject("components").get("city").getAsString();
//
//                pstmt.setInt(1, ++count);
//                pstmt.setString(2, cityName);
//                pstmt.setInt(3, countryId);
//                pstmt.executeUpdate();
//                System.out.println("Inserted city: " + cityName + " for country: " + countryName);
//            }
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        } finally {
//            // Close the prepared statement
//            pstmt.close();
//        }
//    }
//
//    private static String fetchDataFromAPI(String apiUrl) throws SQLException {
//        try {
//            HttpClient client = HttpClientBuilder.create().build();
//            HttpGet request = new HttpGet(apiUrl);
//            HttpResponse response = client.execute(request);
//            HttpEntity entity = response.getEntity();
//
//            if (entity != null) {
//                return EntityUtils.toString(entity);
//            }
//        } catch (Exception e) {
//            throw new SQLException("Failed to fetch data from API", e);
//        }
//
//        throw new SQLException("Failed to fetch data from API");
//    }
//}













//import java.io.UnsupportedEncodingException;
//import java.sql.*;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//
//public class PopulateCitiesTable {
//    private static final String COUNTRIES_TABLE = "countries";
//    private static final String CITIES_TABLE = "cities";
//    private static final int MAX_CITIES_PER_COUNTRY = 5;
//    private static final String OPEN_CAGE_API_KEY = "e1f42e839a8345f1942429a46fd36f77";
//
//    public static void main(String[] args) {
//        try {
//            // Establish the database connection
//            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "proiect", "PROIECT");
//
//            // Query the countries table to retrieve the country data
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT id, name FROM " + COUNTRIES_TABLE);
//
//            // Iterate through the countries and populate the cities table
//            while (rs.next()) {
//                int countryId = rs.getInt("id");
//                String countryName = rs.getString("name");
//                insertCitiesForCountry(conn, countryId, countryName);
//            }
//
//            // Close the database connection
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void insertCitiesForCountry(Connection conn, int countryId, String countryName) throws SQLException {
//        // Prepare the SQL statement for inserting cities
//        String sql = "INSERT INTO " + CITIES_TABLE + " (id, name, country_id, created_at, updated_at) VALUES (?, ?, ?, SYSDATE, SYSDATE)";
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//
//        try {
//            // Encode the country name for use in the API URL
//            String encodedCountryName = URLEncoder.encode(countryName, "UTF-8");
//
//            // Query the OpenCage Geocoding API to fetch cities for the given country
//            String apiUrl = "https://api.opencagedata.com/geocode/v1/json?key=" + OPEN_CAGE_API_KEY + "&country=" + encodedCountryName;
//            String response = fetchDataFromAPI(apiUrl);
//
//            // Parse the JSON response
//            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
//            JsonArray results = jsonObject.getAsJsonArray("results");
//
//            // Iterate through the cities and insert them into the cities table
//            int count = 0;
//            for (JsonElement result : results) {
//                if (count >= MAX_CITIES_PER_COUNTRY) {
//                    break;
//                }
//
//                JsonObject cityObject = result.getAsJsonObject();
//                String cityName = cityObject.get("formatted").getAsString();
//
//                pstmt.setInt(1, ++count);
//                pstmt.setString(2, cityName);
//                pstmt.setInt(3, countryId);
//                pstmt.executeUpdate();
//                System.out.println("Inserted city: " + cityName + " for country: " + countryName);
//            }
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        } finally {
//            // Close the prepared statement
//            pstmt.close();
//        }
//    }
//
//    private static String fetchDataFromAPI(String apiUrl) throws SQLException {
//        try {
//            String encodedUrl = URLEncoder.encode(apiUrl, "UTF-8");
//            HttpClient client = HttpClientBuilder.create().build();
//            HttpGet request = new HttpGet(encodedUrl);
//            HttpResponse response = client.execute(request);
//            HttpEntity entity = response.getEntity();
//
//            if (entity != null) {
//                return EntityUtils.toString(entity);
//            }
//        } catch (Exception e) {
//            throw new SQLException("Failed to fetch data from API", e);
//        }
//
//        throw new SQLException("Failed to fetch data from API");
//    }
//
//}


