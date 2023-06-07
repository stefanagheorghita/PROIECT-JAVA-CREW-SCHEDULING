package example;

import example.repository.*;
import example.solution.PlaneAllocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class ServerApplication
        implements CommandLineRunner {

    @Autowired
    ApplicationContext context;

    private ServerSocket serverSocket;
    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
     private CountryRepository countryRepository;

    @Autowired
    private FlightRepository flightRepository;

    public ServerApplication(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        startServer();
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(8085);
            System.out.println("Server started on port 8085.");
            listenForClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForClients() {
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                ClientThread clientThread =new ClientThread(clientSocket,this,crewRepository,employeeRepository,cityRepository,countryRepository,flightRepository);
                clientThread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }



}