package example.solution;

import example.model.entity.City;
import example.model.entity.Flight;
import example.model.entity.Copilot;
import example.repository.CityRepository;
import example.repository.FlightRepository;
import example.repository.CopilotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class CopilotsDistribution {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private CopilotRepository copilotRepository;

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

        // Fetch cities from the database using JPA
        // Replace "CityEntity" with your actual entity class name for cities

        List<City> cityEntities = cityRepository.findAll();

        for (City cityEntity : cityEntities) {
            City city = new City(cityEntity.getId(), cityEntity.getName());
            cities.add(city);
        }

        return cities;
    }

    public Graph buildGraph(List<Flight> flights, List<City> cities) {
        Graph graph = new Graph();

        // Add cities as nodes to the graph
        for (City city : cities) {
            graph.addNode(city.getId());
        }

        // Add flights as edges to the graph
        for (Flight flight : flights) {
            graph.addEdge(flight.getDepartureCity(), flight.getArrivalCity(), flight.getId());
        }

        return graph;
    }

    public void distributeCopilots(List<Copilot> copilots) {
        for (int nodeId : graph.getNodes()) {
            List<Edge> edges = graph.getEdges(nodeId);

            // Sort the flights based on their priorities
            List<Flight> flights = new ArrayList<>();
            for (Edge edge : edges) {
                Flight flight = findFlightById(edge.getFlightId());
                flights.add(flight);
            }
            Collections.sort(flights, new CopilotsDistribution.FlightPriorityComparator());

            // Assign the copilot to each flight in the city
            for (Flight flight : flights) {
                Copilot copilot = findAvailableCopilot(copilots);
                flight.setCopilotId(copilot.getId());////
                copilot.incrementAssignments();
            }
        }
    }

    // Custom comparator to compare flights based on priority
    private static class FlightPriorityComparator implements Comparator<Flight> {
        @Override
        public int compare(Flight f1, Flight f2) {
            if (f1.getDepartureDay().compareTo(f2.getDepartureDay()) != 0) {
                return f1.getDepartureDay().compareTo(f2.getDepartureDay());
            } else if (f1.getDepartureTime().compareTo(f2.getDepartureTime()) != 0) {
                return f1.getDepartureTime().compareTo(f2.getDepartureTime());
            } else {
                return f1.getArrivalTime().compareTo(f2.getArrivalTime());
            }
        }
    }

    public Copilot findAvailableCopilot(List<Copilot> copilots) {
        // Find an available pilot based on your criteria
        // Here's a simple example that finds the pilot with the fewest assignments

        Copilot availableCopilot = null;
        int minAssignments = Integer.MAX_VALUE;

        for (Copilot copilot : copilots) {
            if (copilot.getAssignments() < minAssignments) {
                availableCopilot = copilot;
                minAssignments = copilot.getAssignments();
            }
        }

        return availableCopilot;
    }

    public Flight findFlightById(int flightId) {
        // Find a flight by its ID from the loaded flights list
        // Here's a simple example using JPA

        Optional<Flight> optionalFlight = Optional.ofNullable(flightRepository.findFlightById(flightId));
        return optionalFlight.orElse(null);
    }
}
