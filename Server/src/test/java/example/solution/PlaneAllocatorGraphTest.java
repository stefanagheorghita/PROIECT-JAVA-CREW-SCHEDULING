package example.solution;

import example.model.entity.Airplane;
import example.model.entity.Flight;
import example.model.entity.PlaneLocation;
import example.repository.AirplaneRepository;
import example.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PlaneAllocatorGraphTest {

    @Mock
    private AirplaneRepository airplaneRepository;

    @Mock
    private FlightRepository flightRepository;

    private PlaneAllocatorGraph planeAllocatorGraph;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        planeAllocatorGraph = new PlaneAllocatorGraph();
        //planeAllocatorGraph.setAirplaneRepository(airplaneRepository);
        //planeAllocatorGraph.setFlightRepository(flightRepository);
    }

    @Test
    public void testAllocatePlanes() {
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight(1, null, null, null, null, null, null));

        List<Airplane> airplanes = new ArrayList<>();
        airplanes.add(new Airplane(1, 100));

        when(flightRepository.findAll()).thenReturn(flights);
        when(airplaneRepository.findAll()).thenReturn(airplanes);

        //HashMap<Flight, Airplane> allocations = planeAllocatorGraph.allocatePlanes();

    }


    @Test
    public void testAllocateWeights() {
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight(1, null, null, null, null, null, null));

        List<Airplane> airplanes = new ArrayList<>();
        airplanes.add(new Airplane(1, 100));

        when(flightRepository.findAll()).thenReturn(flights);
        when(airplaneRepository.findAll()).thenReturn(airplanes);

        //planeAllocatorGraph.allocateWeights();

        //Map<Flight, HashMap<Airplane, Double>> flightAirplaneWeightMap = planeAllocatorGraph.getFlightAirplaneWeightMap();
        //assertEquals(1, flightAirplaneWeightMap.size());

        //HashMap<Airplane, Double> airplaneWeightMap = flightAirplaneWeightMap.get(flights.get(0));
        //assertEquals(1, airplaneWeightMap.size());

        //Double weight = airplaneWeightMap.get(airplanes.get(0));
        //assertEquals(2.0, weight);
    }

    @Test
    public void testMax() {

    }

    @Test
    public void testAssignments() {
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight(1, null, null, null, null, null, null));

        List<Airplane> airplanes = new ArrayList<>();
        airplanes.add(new Airplane(1, 100));

        when(flightRepository.findAll()).thenReturn(flights);
        when(airplaneRepository.findAll()).thenReturn(airplanes);

        //planeAllocatorGraph.assignments();


    }

    @Test
    public void testCalculateWeight() {
        Flight flight = new Flight(1, null, null, DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0), 80);
        Airplane airplane = new Airplane(1, 100);

        PlaneLocation planeLocation = new PlaneLocation(1, 2, DayOfWeek.MONDAY, LocalTime.of(9, 0));
        //planeAllocatorGraph.getPlaneLocations().add(planeLocation);

        //double weight = planeAllocatorGraph.calculateWeight(flight, airplane);

        //assertEquals(1.5, weight);
    }

    @Test
    public void testFindPlaneLocation() {
        PlaneLocation planeLocation1 = new PlaneLocation(1, 2, DayOfWeek.MONDAY, LocalTime.of(9, 0));
        PlaneLocation planeLocation2 = new PlaneLocation(2, 3, DayOfWeek.TUESDAY, LocalTime.of(13, 0));
        //planeAllocatorGraph.getPlaneLocations().add(planeLocation1);
        //planeAllocatorGraph.getPlaneLocations().add(planeLocation2);

        //PlaneLocation foundLocation = planeAllocatorGraph.findPlaneLocation(1);

        //assertEquals(planeLocation1, foundLocation);
    }

    @Test
    public void testCalculateTimeDifference() {
        LocalTime time1 = LocalTime.of(9, 0);
        LocalTime time2 = LocalTime.of(10, 0);

        //Duration difference = planeAllocatorGraph.calculateTimeDifference(time1, time2);

        //assertEquals(1, difference.toHours());
    }


    private void setAirplaneRepository(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    private void setFlightRepository(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }



    //private Map<Flight, HashMap<Airplane, Double>> getFlightAirplaneWeightMap() {
        //return flightAirplaneWeightMap;
    //}

    //private Set<PlaneLocation> getPlaneLocations() {
        //return planeLocations;
    //}
}
