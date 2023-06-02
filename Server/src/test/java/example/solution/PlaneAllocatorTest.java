package example.solution;

import example.model.entity.*;
import example.repository.AirplaneRepository;
import example.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
class PlaneAllocatorTest {

    @Mock
    private AirplaneRepository airplaneRepository;

    @Mock
    private FlightRepository flightRepository;

    private PlaneAllocator planeAllocator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        planeAllocator = new PlaneAllocator();
        AirplaneRepository airplaneRepositoryMock = Mockito.mock(AirplaneRepository.class);
        FlightRepository flightRepositoryMock = Mockito.mock(FlightRepository.class);
        planeAllocator.setAirplaneRepository(airplaneRepositoryMock);
        planeAllocator.setFlightRepository(flightRepositoryMock);
    }


    @Test
    void testAllocatePlanes() {
        Flight flight1 = new Flight(/* flight details */);
        Flight flight2 = new Flight(/* flight details */);

        Airplane airplane1 = new Airplane(/* airplane details */);
        Airplane airplane2 = new Airplane(/* airplane details */);

        List<Flight> flights = new ArrayList<>();
        flights.add(flight1);
        flights.add(flight2);
        when(flightRepository.findAll()).thenReturn(flights);
        List<Airplane> airplanes = new ArrayList<>();
        airplanes.add(airplane1);
        airplanes.add(airplane2);
        when(airplaneRepository.findAll()).thenReturn(airplanes);

        HashMap<Flight, Airplane> allocationResult = planeAllocator.allocatePlanes();

        assertEquals(airplane1, allocationResult.get(flight1));
    }

    @Test
    void testVerifyAvailability() {

        Flight flight1 = new Flight(1,new City(1,"Iasi",new Country(1L,"Romania")),new City(2,"Berlin",new Country(2L,"Germania")),DayOfWeek.MONDAY,LocalTime.of(12,0),LocalTime.of(14,0),100);
        Airplane airplane1 = new Airplane(1,100);
        Airplane airplane2 = new Airplane(2,120);
        PlaneLocation planeLocation1 = new PlaneLocation(1,2,DayOfWeek.MONDAY,LocalTime.of(14,0));
        PlaneLocation planeLocation2 = new PlaneLocation(2,3,DayOfWeek.MONDAY,LocalTime.of(15,0));
        HashSet<PlaneLocation> planeLocations = new HashSet<>();
        planeLocations.add(planeLocation1);
        planeLocations.add(planeLocation2);
        airplane1.setFlights(new ArrayList<>());
        airplane2.setFlights(new ArrayList<>());
        airplane2.getFlights().add(new Flight(2,new City(1,"Iasi",new Country(1L,"Romania")),new City(2,"Berlin",new Country(2L,"Germania")),DayOfWeek.MONDAY,LocalTime.of(11,0),LocalTime.of(14,0),100));
        boolean test1 = planeAllocator.verifyAvailability(airplane1,flight1.getDepartureDay(),flight1.getDepartureTime(),flight1.getArrivalTime(),planeLocations);
        assertEquals(true,test1);
        boolean test2 = planeAllocator.verifyAvailability(airplane2,flight1.getDepartureDay(),flight1.getDepartureTime(),flight1.getArrivalTime(),planeLocations);
        assertEquals(false,test2);
    }

    @Test
    void testFindAvailablePlane() {
        Flight flight1 = new Flight(1,new City(2,"Berlin",new Country(2L,"Germania")),new City(3,"Munchen",new Country(2L,"Germania")),DayOfWeek.MONDAY,LocalTime.of(12,0),LocalTime.of(14,0),150);
        Flight flight2 = new Flight(2,new City(1,"Iasi",new Country(1L,"Romania")),new City(2,"Berlin",new Country(2L,"Germania")),DayOfWeek.MONDAY,LocalTime.of(11,0),LocalTime.of(14,0),100);
        Flight flight3 = new Flight(3,new City(1,"Iasi",new Country(1L,"Romania")),new City(2,"Berlin",new Country(2L,"Germania")),DayOfWeek.TUESDAY,LocalTime.of(11,0),LocalTime.of(14,0),100);
        Flight flight4 = new Flight(4,new City(1,"Bucuresti",new Country(1L,"Romania")),new City(2,"Berlin",new Country(2L,"Germania")),DayOfWeek.MONDAY,LocalTime.of(10,0),LocalTime.of(11,0),100);
        Airplane airplane1 = new Airplane(1,100);
        Airplane airplane2 = new Airplane(2,120);
        Airplane airplane3 = new Airplane(3,50);
        Airplane airplane4 = new Airplane(4,150);
        Airplane airplane5 = new Airplane(5,200);
        airplane1.setFlights(new ArrayList<>());
        airplane2.setFlights(new ArrayList<>());
        airplane3.setFlights(new ArrayList<>());
        airplane4.setFlights(new ArrayList<>());
        airplane5.setFlights(new ArrayList<>());
        airplane1.getFlights().add(flight4);
        airplane2.getFlights().add(flight3);
        airplane4.getFlights().add(flight2);
        PlaneLocation planeLocation1 = new PlaneLocation(1,2,DayOfWeek.MONDAY,LocalTime.of(11,0));
        PlaneLocation planeLocation2 = new PlaneLocation(2,3,DayOfWeek.TUESDAY,LocalTime.of(15,0));
        PlaneLocation planeLocation3 = new PlaneLocation(3,4,DayOfWeek.MONDAY,LocalTime.of(15,0));
        HashSet<PlaneLocation> planeLocations = new HashSet<>();
        planeLocations.add(planeLocation1);
        planeLocations.add(planeLocation2);
        planeLocations.add(planeLocation3);
        List<Airplane> airplanes = new ArrayList<>();
        AirplaneRepository airplaneRepository = Mockito.mock(AirplaneRepository.class);
        PlaneAllocator planeAllocator = new PlaneAllocator();
        planeAllocator.setAirplaneRepository(airplaneRepository);
        Mockito.when(airplaneRepository.findById(Mockito.anyInt()))
                .thenAnswer(invocation -> {
                    System.out.println("invocation = " + invocation);
                    int airplaneId = invocation.getArgument(0);
                    switch (airplaneId) {
                        case 1:

                            return Optional.of(airplane1);
                        case 2:
                            return Optional.of(airplane2);
                        case 3:
                            return Optional.of(airplane3);
                        case 4:
                            return Optional.of(airplane4);
                        case 5:
                            return Optional.of(airplane5);
                        default:
                            return Optional.empty();
                    }
                });

        airplanes=planeAllocator.findAvailablePlane(planeLocations,2,DayOfWeek.MONDAY,LocalTime.of(12,0),LocalTime.of(14,0),100);
        assertEquals(airplane1,airplanes.get(0));
    }



}
