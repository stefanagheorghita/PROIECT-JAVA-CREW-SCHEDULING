package example.solution;


import example.model.entity.City;
import example.model.entity.Crew;
import example.model.entity.Employee;
import example.model.entity.Flight;
import example.repository.EmployeeRepository;
import example.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import static org.mockito.Mockito.when;

public class FlightEngineerDistributionTest {


    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private FlightRepository flightRepository;


    private FlightEngineerDistribution flightEngineerDistribution;

    List<Flight> flights;

    List<Employee> flightEngineers;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        EmployeeRepository employeeRepositoryMock = Mockito.mock(EmployeeRepository.class);
        FlightRepository flightRepositoryMock = Mockito.mock(FlightRepository.class);
        flightEngineerDistribution = new FlightEngineerDistribution();
        flightEngineerDistribution.setEmployeeRepository(employeeRepositoryMock);
        flightEngineerDistribution.setFlightRepository(flightRepositoryMock);
        flights = new ArrayList<>();
        flightEngineers = new ArrayList<>();
        Flight flight1 = new Flight();
        flight1.setDepartureCity(new City(1, "city1"));
        flight1.setArrivalCity(new City(2, "city2"));
        flight1.setDepartureDay(DayOfWeek.MONDAY);
        flight1.setDepartureTime(LocalTime.of(11, 0));
        flight1.setArrivalTime(LocalTime.of(12, 0));
        Flight flight2 = new Flight();
        flight2.setDepartureCity(new City(1, "city1"));
        flight2.setArrivalCity(new City(2, "city2"));
        flight2.setDepartureDay(DayOfWeek.MONDAY);
        flight2.setDepartureTime(LocalTime.of(10, 0));
        flight2.setArrivalTime(LocalTime.of(13, 0));
        flights.add(flight1);
        flights.add(flight2);
        when(flightRepository.findAll()).thenReturn(flights);
        Employee employee1 = new Employee();
        employee1.setFirstName("employee1");
        employee1.setLastName("employee1");
        employee1.setBirthDate(new Timestamp(1990, 1, 1, 0, 0, 0, 0));
        employee1.setGender("F");
        employee1.setCrew(new Crew(1, "crew1", 60));
        flightEngineers.add(employee1);
        when(employeeRepository.findEmployeesByCrewName("Flight Engineer")).thenReturn(flightEngineers);
    }

    @Test
    public void testFlightEngineerDistribution() {
        FlightRepository flightRepositoryMock = Mockito.mock(FlightRepository.class);

        EmployeeRepository employeeRepositoryMock = Mockito.mock(EmployeeRepository.class);
        flightEngineerDistribution = new FlightEngineerDistribution();
        flightEngineerDistribution.setFlightRepository(flightRepositoryMock);
        flightEngineerDistribution.setEmployeeRepository(employeeRepositoryMock);
        Mockito.when(flightRepositoryMock.findAll()).thenReturn(flights);
        Mockito.when(employeeRepositoryMock.findEmployeesByCrewName("Flight Engineer")).thenReturn(flightEngineers);
        flightEngineerDistribution.initial();
    }
}
