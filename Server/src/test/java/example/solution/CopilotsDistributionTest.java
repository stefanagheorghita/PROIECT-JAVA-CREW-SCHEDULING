package example.solution;

import example.model.entity.City;
import example.model.entity.Country;
import example.model.entity.Employee;
import example.model.entity.Flight;
import example.repository.CityRepository;
import example.repository.CopilotRepository;
import example.repository.FlightRepository;
import example.repository.PilotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CopilotsDistributionTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private CopilotRepository copilotRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CopilotsDistribution copilotsDistribution;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.initMocks(this);
        //pilotsDistribution = new PilotsDistribution();
        //pilotsDistribution.setFlightRepository(flightRepository);
        //pilotsDistribution.setPilotRepository(pilotRepository);
        //pilotsDistribution.setCityRepository(cityRepository);
    }

    @Test
    public void testLoadCitiesFromDatabase() {
        List<City> cityEntities = new ArrayList<>();
        cityEntities.add(new City(1, "City1", new Country(1,"Country1")));
        cityEntities.add(new City(2, "City2", new Country(2, "Country2")));

        when(cityRepository.findAll()).thenReturn(cityEntities);

        List<City> cities = copilotsDistribution.loadCitiesFromDatabase();

        assertEquals(2, cities.size());
        assertEquals("City1", cities.get(0).getName());
        //assertEquals("Country1", cities.get(0).getCountry().getName());
        assertEquals("City2", cities.get(1).getName());
        //assertEquals("Country2", cities.get(1).getCountry().getName());

        verify(cityRepository, times(1)).findAll();
    }

//    @Test
//    public void testBuildGraph() {
//        List<Flight> flights = new ArrayList<>();
//        flights.add(new Flight(1, new City(1, "City1", new Country(1, "Country1")), new City(2, "City2", new Country(2, "Country2")), DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0), 100));
//        flights.add(new Flight(2, new City(2, "City2", new Country(2, "Country2")), new City(3, "City3", new Country(3, "Country3")), DayOfWeek.TUESDAY, LocalTime.of(13, 0), LocalTime.of(15, 0), 150));
//
//        List<City> cities = new ArrayList<>();
//        cities.add(new City(1, "City1", new Country(1, "Country1")));
//        cities.add(new City(2, "City2", new Country(2, "Country2")));
//        cities.add(new City(3, "City3", new Country(3, "Country3")));
//
//        Graph graph = copilotsDistribution.buildGraph(flights, cities);
//
//        assertNotNull(graph);
//
//        assertEquals(3, graph.getNodes().size());
//        assertTrue(graph.getNodes().contains(1));
//        assertTrue(graph.getNodes().contains(2));
//        assertTrue(graph.getNodes().contains(3));
//
//        //assertEquals(2, graph.getEdges(1).size());
//        assertEquals(1, graph.getEdges(2).size());
//        assertEquals(0, graph.getEdges(3).size());
//
//        verify(flightRepository, times(1)).findAll();
//    }

    @Test
    public void testFindAvailablePilot() {
        List<Employee> copilots = new ArrayList<>();
        copilots.add(new Employee(1, "Pavel", "Adrian", null, null, null, null, null));
        copilots.add(new Employee(2, "Andries", "Victor", null, null, null, null, null));

        copilots.get(0).setAssignments(3);
        copilots.get(1).setAssignments(5);

        Employee availablePilot = copilotsDistribution.findAvailableCopilot(copilots);

        assertNotNull(availablePilot);
        assertEquals(1, availablePilot.getId());
    }

    @Test
    public void testFindFlightById() {
        Flight flight = new Flight(1, new City(1, "City1", new Country(1, "Country1")), new City(2, "City2", new Country(2, "Country2")), DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0), 100);

        when(flightRepository.findFlightById(1)).thenReturn(flight);

        Flight foundFlight = copilotsDistribution.findFlightById(1);

        assertNotNull(foundFlight);
        assertEquals(1, foundFlight.getId());
        assertEquals("City1", foundFlight.getDepartureCity().getName());
        assertEquals("City2", foundFlight.getArrivalCity().getName());
        assertEquals(DayOfWeek.MONDAY, foundFlight.getDepartureDay());
        assertEquals(LocalTime.of(10, 0), foundFlight.getDepartureTime());
        assertEquals(LocalTime.of(12, 0), foundFlight.getArrivalTime());
        assertEquals(100, foundFlight.getAproxPassengers());

        verify(flightRepository, times(1)).findFlightById(1);
    }

//    @Test
//    public void testDistributeCopilots() {
//        List<Employee> copilots = new ArrayList<>();
//        Employee pilot1 = new Employee(1, "Pavel", "Adrian", null, null, null, null, null);
//        Employee pilot2 = new Employee(2, "Andries", "Victor", null, null, null, null, null);
//        copilots.add(pilot1);
//        copilots.add(pilot2);
//
//        List<Flight> flights = new ArrayList<>();
//        Flight flight1 = new Flight(1, new City(1, "City1", new Country(1, "Country1")), new City(2, "City2", new Country(2, "Country2")), DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0), 100);
//        Flight flight2 = new Flight(2, new City(2, "City2", new Country(2, "Country2")), new City(3, "City3", new Country(3, "Country3")), DayOfWeek.TUESDAY, LocalTime.of(13, 0), LocalTime.of(15, 0), 150);
//        flights.add(flight1);
//        flights.add(flight2);
//
//        when(flightRepository.findAll()).thenReturn(flights);
//        //when(pilotRepository.findAll()).thenReturn(copilots);
//
//        //copilotsDistribution.distributeCopilots(copilots);
//
//        //assertEquals(1, flight1.getPilotId());
//        //assertEquals(2, flight2.getPilotId());
//
//        assertEquals(1, pilot1.getAssignments());
//        assertEquals(2, pilot2.getAssignments());
//
//        verify(flightRepository, times(1)).findAll();
//    }

    @Test
    public void testVerifyFree_CopilotAvailable() {
        List<Employee> copilots = new ArrayList<>();
        Employee pilot1 = new Employee(1, "Iftimie", "Cerasela", null, null, null, null, null);
        Employee pilot2 = new Employee(2, "Munteanu", "Alex", null, null, null, null, null);
        copilots.add(pilot1);
        copilots.add(pilot2);

        Flight flight = new Flight(1, new City(1, "City1", new Country(1, "Country1")), new City(2, "City2", new Country(2,"Country2")), DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0), 100);

        pilot1.getFlights().add(flight);

        //List<Employee> availablePilots = pilotsDistribution.verifyFree(pilots, flight);

        //assertEquals(1, availablePilots.size());
        //assertEquals(pilot2, availablePilots.get(0));
    }

    @Test
    public void testVerifyFree_CopilotNotAvailable() {
        List<Employee> copilots = new ArrayList<>();
        Employee pilot1 = new Employee(1, "John", "Doe", null, null, null, null, null);
        Employee pilot2 = new Employee(2, "Alice", "Smith", null, null, null, null, null);
        copilots.add(pilot1);
        copilots.add(pilot2);

        Flight flight = new Flight(1, new City(1, "City1", new Country(1,"Country1")), new City(2, "City2", new Country(2, "Country2")), DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0), 100);

        pilot1.getFlights().add(flight);
        pilot2.getFlights().add(flight);

        //List<Employee> availablePilots = pilotsDistribution.verifyFree(pilots, flight);

        //assertTrue(availablePilots.isEmpty());
    }

    @Test
    public void testFindAvailableCopilot_CopilotAvailable() {
        List<Employee> copilots = new ArrayList<>();
        Employee pilot1 = new Employee(1, "Nastasa", "Vlad", null, null, null, null, null);
        Employee pilot2 = new Employee(2, "Stoian", "Viorela", null, null, null, null, null);
        copilots.add(pilot1);
        copilots.add(pilot2);

        pilot1.setAssignments(2);
        pilot2.setAssignments(1);

        Employee availablePilot = copilotsDistribution.findAvailableCopilot(copilots);

        assertEquals(pilot2, availablePilot);
    }

    @Test
    public void testFindFlightById_FlightFound() {
        Flight flight = new Flight(1, new City(1, "City1", new Country(1, "Country1")), new City(2, "City2", new Country(2, "Country2")), DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0), 100);
        when(flightRepository.findFlightById(1)).thenReturn(flight);

        Flight foundFlight = copilotsDistribution.findFlightById(1);

        assertEquals(flight, foundFlight);
    }

}
