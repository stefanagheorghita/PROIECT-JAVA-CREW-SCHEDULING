package com.example.client2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainPageController {

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button addFlightButton;

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8085;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    @FXML
    private void handleAddFlightButton() {
        try {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println("addFlight");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage formStage = new Stage();
            formStage.setTitle("Add Flight");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("flight-form.fxml"));
            Parent root = loader.load();
            FlightFormController formController = loader.getController();
            formController.setSocket(socket);
            formController.setOut(out);
            formController.setIn(in);
            formController.setDays("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
            formController.setArrivalTime("00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
                    "06:00", "07:00", "08:00", "09:00", "10:00",
                    "11:00", "12:00", "13:00", "14:00", "15:00",
                    "16:00", "17:00", "18:00", "19:00", "20:00",
                    "21:00", "22:00", "23:00");
            formController.setDepartureTime("00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
                    "06:00", "07:00", "08:00", "09:00", "10:00",
                    "11:00", "12:00", "13:00", "14:00", "15:00",
                    "16:00", "17:00", "18:00", "19:00", "20:00",
                    "21:00", "22:00", "23:00");
            formStage.setScene(new Scene(root));
            formStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddEmployeeButton() {
        try {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println("addEmployee");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage formStage = new Stage();
            formStage.setTitle("Add Employee");
            System.out.println("handleAddEmployeeButton");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("employee-form.fxml"));
            Parent root = loader.load();
            EmployeeFormController formController = loader.getController();
            formController.setSocket(socket);
            formController.setOut(out);
            formController.setIn(in);
            formController.setEmployeeTypes("Pilot", "Copilot", "Flight Attendant", "Flight Engineer");
            formController.setGenderTypes("F", "M");
            formStage.setScene(new Scene(root));
            formStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
