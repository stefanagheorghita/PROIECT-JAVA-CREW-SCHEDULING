package com.example.client2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

public class FlightFormController {

    private static final String SERVER_IP = "localhost";

    private static final int SERVER_PORT = 8085;

    private Socket socket;

    private PrintWriter out;

    private BufferedReader in;

    @FXML
    private TextField DepartureCityField;
    @FXML
    private TextField DepartureCountryField;

    @FXML
    private TextField ArrivalCityField;
    @FXML
    private TextField ArrivalCountryField;

    @FXML
    private ChoiceBox<String> days;

    @FXML
    private ChoiceBox<String> departureTime;

    @FXML
    private ChoiceBox<String> arrivalTime;

    @FXML
    private Button submitButton;

    @FXML
    private Spinner<Integer> number;


    public void setDays(String... types) {
        days.getItems().addAll(types);
    }

    public void setDepartureTime(String... types) {
        departureTime.getItems().addAll(types);
    }

    public void setArrivalTime(String... types) {
        arrivalTime.getItems().addAll(types);
    }

    @FXML
    private void handleSubmitButton() {
        try {
            String City1 = DepartureCityField.getText();
            String Country1 = DepartureCountryField.getText();
            String City2 = ArrivalCityField.getText();
            String Country2 = ArrivalCountryField.getText();
            String day = days.getValue();
            String departure = departureTime.getValue();
            String arrival = arrivalTime.getValue();
            int number1 = number.getValue();
            out.println(City1);
            out.println(Country1);
            out.println(City2);
            out.println(Country2);
            out.println(day);
            out.println(departure);
            out.println(arrival);
            out.println(number1);

        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();

        }
        try {
            String response = in.readLine();
            if(response.equals("Flight added successfully")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Flight added successfully");
                alert.showAndWait();
                Stage stage = (Stage) submitButton.getScene().getWindow();
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("FLight could not be added");
                alert.showAndWait();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Employee added");
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }
}
