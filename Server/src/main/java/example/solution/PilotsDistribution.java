package example.solution;

import example.model.entity.City;
import example.model.entity.Employee;
import example.model.entity.Flight;
import example.model.entity.Pilot;
import example.repository.CityRepository;
import example.repository.FlightRepository;
import example.repository.PilotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class PilotsDistribution {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PilotRepository pilotRepository;

    private Graph graph;

    @Autowired
    CityRepository cityRepository;

    @PostConstruct
    public void initialize() {
        List<Flight> flights = flightRepository.findAll();
        List<City> cities = loadCitiesFromDatabase();

        graph = buildGraph(flights, cities);
    }


    public List<City> loadCitiesFromDatabase() {
        List<City> cities = new ArrayList<>();

        List<City> cityEntities = cityRepository.findAll();

        for (City cityEntity : cityEntities) {
            City city = new City(cityEntity.getId(), cityEntity.getName());
            cities.add(city);
        }

        return cities;
    }

    public Graph buildGraph(List<Flight> flights, List<City> cities) {
        Graph graph = new Graph();

        for (City city : cities) {
            graph.addNode(city.getId());
        }

        for (Flight flight : flights) {
            graph.addEdge(flight.getDepartureCity(), flight.getArrivalCity(), flight.getId());
        }

        return graph;
    }

    /**
     * Distributes pilots to flights
     * @param pilots
     */

    public void distributePilots(List<Employee> pilots) {
        for (int nodeId : graph.getNodes()) {
            List<Edge> edges = graph.getEdges(nodeId);
            List<Flight> flights = flightRepository.findAll();
            for (Edge edge : edges) {
                Flight flight = findFlightById(edge.getFlightId());
                flights.add(flight);
            }
            Collections.sort(flights, new FlightPriorityComparator());
            for (Flight flight : flights) {
                List<Employee> maybePilots = verifyFree(pilots, flight);
                Employee pilot = findAvailablePilot(maybePilots);
                flight.setPilotId(pilot.getId());
                System.out.println("Pilot " + pilot.getId() + " assigned to flight " + flight.getId());
                pilot.incrementAssignments();
                pilot.getFlights().add(flight);
                flight.setPilotId(pilot.getId());
            }
        }
    }
    /**
     * Verify if a pilot is free at that time
     * @param pilots
     * @return
     */

    private List<Employee> verifyFree(List<Employee> pilots, Flight flight) {
        List<Employee> maybePilots = new ArrayList<>(pilots);
        for (Employee pilot : pilots) {

            if (pilot.getFlights() != null) {
                if (pilot.getFlights().contains(flight)) {
                    maybePilots.remove(pilot);
                    continue;
                }
                for (Flight flight1 : pilot.getFlights()) {
                    if (flight1.getDepartureDay().equals(flight.getDepartureDay())) {
                        if (flight1.getDepartureTime().compareTo(flight.getDepartureTime()) < 0) {
                            if (flight1.getArrivalTime().compareTo(flight.getDepartureTime()) > 0) {
                                maybePilots.remove(pilot);
                                break;
                            }
                        }
                        if (flight1.getDepartureTime().compareTo(flight.getDepartureTime()) > 0) {
                            if (flight1.getDepartureTime().compareTo(flight.getArrivalTime()) < 0) {
                                maybePilots.remove(pilot);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return pilots;
    }

    private static class FlightPriorityComparator implements Comparator<Flight> {
        @Override
        public int compare(Flight f1, Flight f2) {
            if (f1.getDepartureDay() != null && f2.getDepartureDay() != null)
                if (f1.getDepartureDay().compareTo(f2.getDepartureDay()) != 0) {
                    return f1.getDepartureDay().compareTo(f2.getDepartureDay());
                } else if (f1.getDepartureTime().compareTo(f2.getDepartureTime()) != 0) {
                    return f1.getDepartureTime().compareTo(f2.getDepartureTime());
                } else {
                    return f1.getArrivalTime().compareTo(f2.getArrivalTime());
                }
            return 0;
        }
    }


    public Employee findAvailablePilot(List<Employee> pilots) {
        Employee availablePilot = null;
        int minAssignments = Integer.MAX_VALUE;

        for (Employee pilot : pilots) {
            if (pilot.getAssignments() < minAssignments) {
                availablePilot = pilot;
                minAssignments = pilot.getAssignments();
            }
        }

        return availablePilot;
    }

    public Flight findFlightById(int flightId) {
        Optional<Flight> optionalFlight = Optional.ofNullable(flightRepository.findFlightById(flightId));
        return optionalFlight.orElse(null);
    }


}

