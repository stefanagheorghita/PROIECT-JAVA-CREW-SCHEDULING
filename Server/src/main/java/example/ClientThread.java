package example;

import example.model.entity.City;
import example.model.entity.Employee;
import example.model.entity.Flight;
import example.model.entity.Country;
import example.repository.*;
import org.jheaps.annotations.Beta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.DayOfWeek;

import java.time.LocalTime;

@Component
public class ClientThread extends Thread {

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    private Socket clientSocket;

    private ServerApplication serverApplication;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private FlightRepository flightRepository;

    public ClientThread(Socket clientSocket, ServerApplication gameServer, CrewRepository crewRepository, EmployeeRepository employeeRepository, CityRepository cityRepository, CountryRepository countryRepository, FlightRepository flightRepository) {
        this.clientSocket = clientSocket;
        this.serverApplication = gameServer;
        this.crewRepository = crewRepository;
        this.employeeRepository = employeeRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.flightRepository = flightRepository;
    }


    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String line = in.readLine();
            System.out.println(line);
            switch (line) {
                case "addEmployee":
                    System.out.println("addEmployee");
                    handleAddEmployee(in, out);
                    break;
                case "addFlight":
                    System.out.println("addFlight");
                    handleAddFlight(in, out);
                    break;
                case "visualize":
                    System.out.println("visualize");
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleAddFlight(BufferedReader in, PrintWriter out) {
        try {
            String departureCity = in.readLine();
            String departureCountry = in.readLine();
            String arrivalCity = in.readLine();
            String arrivalCountry = in.readLine();
            String day = in.readLine();
            String hourD = in.readLine();
            String hourA = in.readLine();
            String number = in.readLine();
            int nr = Integer.parseInt(number);
            Flight f = new Flight();
            Country departureCountry1 = countryRepository.findCountryByName(departureCountry);
            Country arrivalCountry1 = countryRepository.findCountryByName(arrivalCountry);
            if (departureCountry1 == null || arrivalCountry1 == null) {
                out.println("Country not found");
                return;
            }

            City c = cityRepository.findCityByNameCountry(departureCity, countryRepository.findCountryByName(departureCountry).getId());
            if (c == null) {
                out.println("City not found");
                return;
            }
            f.setDepartureCity(c);

            City e = cityRepository.findCityByNameCountry(arrivalCity, countryRepository.findCountryByName(arrivalCountry).getId());
            if (e == null) {
                out.println("City not found");
                return;
            }
            f.setArrivalCity(e);
            f.setDepartureDayStr(day);
            f.setDepartureDay(DayOfWeek.valueOf((day.toUpperCase())));
            f.setDepartureHour(hourD);
            LocalTime departureDate = LocalTime.parse(hourD);
            LocalTime arrivalDate = LocalTime.parse(hourA);
            f.setDepartureTime(departureDate);
            f.setArrivalTime(arrivalDate);
            f.setArrivalHour(hourA);
            f.setAproxPassengers(nr);
            f.setPilotId(null);
            flightRepository.save(f);
            out.println("Flight added successfully");

        } catch (IOException e) {
            out.println("Flight not added");
            throw new RuntimeException(e);
        }
    }

    private void handleAddEmployee(BufferedReader in, PrintWriter out) {

        try {
            String firstName = in.readLine();
            String lastName = in.readLine();
            String date = in.readLine();
            String gender = in.readLine();
            String job = in.readLine();
            System.out.println(firstName);
            System.out.println(lastName);
            System.out.println(date);
            Employee e = new Employee();
            e.setFirstName(firstName);
            e.setLastName(lastName);
            e.setGender(gender);
            e.setCrew(crewRepository.findCrewByName(job));
            e.setBirthDate(Timestamp.valueOf(date));
            e.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            e.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            employeeRepository.save(e);
            out.println("Employee added successfully");

        } catch (IOException e) {
            out.println("Employee not added");
            throw new RuntimeException(e);
        }

    }
}


