package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.github.javafaker.Faker;

public class DataGenerator {
    public static void main(String[] args) {
        // Set up database connection
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521/xe";
        String username = "proiect";
        String password = "PROIECT";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create Faker instance
        Faker faker = new Faker();

        // Insert data into the "countries" table
        int numCountries = 193; // Number of countries to generate
        String countriesInsertQuery = "INSERT INTO countries (id, name, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement countriesStatement = connection.prepareStatement(countriesInsertQuery);
            int countryId = 1;
            Set<String> generatedCountryNames = new HashSet<>();

            while (generatedCountryNames.size() < numCountries) {
                String countryName = faker.country().name();
                if (!generatedCountryNames.contains(countryName)) {
                    generatedCountryNames.add(countryName);
                    countriesStatement.setInt(1, countryId++);
                    countriesStatement.setString(2, countryName);
                    countriesStatement.setDate(3, new java.sql.Date(new Date().getTime()));
                    countriesStatement.setDate(4, new java.sql.Date(new Date().getTime()));
                    countriesStatement.executeUpdate();
                }
            }

            countriesStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Insert data into the "cities" table
        String citiesInsertQuery = "INSERT INTO cities (id, name, country_id, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement citiesStatement = connection.prepareStatement(citiesInsertQuery);
            int cityId = 1;

            // Retrieve country IDs
            String countryIdsQuery = "SELECT id FROM countries";
            PreparedStatement countryIdsStatement = connection.prepareStatement(countryIdsQuery);
            ResultSet countryIdsResult = countryIdsStatement.executeQuery();

            Set<Integer> countryIds = new HashSet<>();
            while (countryIdsResult.next()) {
                int countryId = countryIdsResult.getInt("id");
                countryIds.add(countryId);
            }

            for (int countryId : countryIds) {
                for (int i = 1; i <= 5; i++) { // 5 cities per country
                    citiesStatement.setInt(1, cityId++);
                    citiesStatement.setString(2, faker.address().city());
                    citiesStatement.setInt(3, countryId);
                    citiesStatement.setDate(4, new java.sql.Date(new Date().getTime()));
                    citiesStatement.setDate(5, new java.sql.Date(new Date().getTime()));
                    citiesStatement.executeUpdate();
                }
            }

            citiesStatement.close();
            countryIdsResult.close();
            countryIdsStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Data inserted successfully!");
    }
}



