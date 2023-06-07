package com.example.client2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDate;

public class EmployeeFormController {

    private static final String SERVER_IP = "localhost";

    private static final int SERVER_PORT = 8085;

    private Socket socket;

    private PrintWriter out;

    private BufferedReader in;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker birthDateField;

    @FXML
    private ChoiceBox<String> gender;

    @FXML
    private ChoiceBox<String> job;

    @FXML
    private Button submitButton;


    public void setEmployeeTypes(String... types) {
        job.getItems().addAll(types);
    }

    public void setGenderTypes(String... types) {
        gender.getItems().addAll(types);
    }

    @FXML
    private void handleSubmitButton() {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            //Timestamp birthDate= Timestamp.valueOf(birthDateField.getValue().atStartOfDay());
            LocalDate selectedDate = birthDateField.getValue();
            LocalDate currentDate = LocalDate.now();
            LocalDate minimumAgeDate = currentDate.minusYears(18);

            if (selectedDate.isAfter(minimumAgeDate)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Date");
                alert.setHeaderText(null);
                alert.setContentText("The birth date is invalid.");
                alert.showAndWait();
                return;
            }
            Timestamp birthDate = Timestamp.valueOf(selectedDate.atStartOfDay());
            String gen = gender.getValue();
            String employeeType = job.getValue();
            out.println(firstName);
            out.println(lastName);
            out.println(birthDate);
            out.println(gen);
            out.println(employeeType);
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
            if(response.equals("Employee added successfully")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Employee added successfully");
                alert.showAndWait();
                Stage stage = (Stage) submitButton.getScene().getWindow();
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Employee could not be added");
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
