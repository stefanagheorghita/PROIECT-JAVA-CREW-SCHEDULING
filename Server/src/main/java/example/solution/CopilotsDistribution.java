package example.solution;

import example.model.entity.City;
import example.model.entity.Employee;
import example.model.entity.Flight;
import example.model.entity.Copilot;
import example.repository.CityRepository;
import example.repository.EmployeeRepository;
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

    @Autowired
    private EmployeeRepository employeeRepository;

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

    public void distributeCopilots(List<Employee> copilots) {
        for (int nodeId : graph.getNodes()) {
            List<Edge> edges = graph.getEdges(nodeId);

            List<Flight> flights = new ArrayList<>();
            for (Edge edge : edges) {
                Flight flight = findFlightById(edge.getFlightId());
                flights.add(flight);
            }
            Collections.sort(flights, new CopilotsDistribution.FlightPriorityComparator());

            for (Flight flight : flights) {
                List<Employee> maybeCopilots = verifyFree(copilots, flight);
                Employee copilot = findAvailableCopilot(maybeCopilots);
                flight.setCopilotId(copilot.getId());
                copilot.incrementAssignments();
                System.out.println("Copilot " + copilot.getId() + " assigned to flight " + flight.getId());
            }
        }
    }

    private List<Employee> verifyFree(List<Employee> copilots, Flight flight) {
        List<Employee> maybePilots = new ArrayList<>(copilots);
        for (Employee copilot : copilots) {

            if (copilot.getFlights() != null) {
                if (copilot.getFlights().contains(flight)) {
                    maybePilots.remove(copilot);
                    continue;
                }
                for (Flight flight1 : copilot.getFlights()) {
                    if (flight1.getDepartureDay().equals(flight.getDepartureDay())) {
                        if (flight1.getDepartureTime().compareTo(flight.getDepartureTime()) < 0) {
                            if (flight1.getArrivalTime().compareTo(flight.getDepartureTime()) > 0) {
                                maybePilots.remove(copilot);
                                break;
                            }
                        }
                        if (flight1.getDepartureTime().compareTo(flight.getDepartureTime()) > 0) {
                            if (flight1.getDepartureTime().compareTo(flight.getArrivalTime()) < 0) {
                                maybePilots.remove(copilot);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return copilots;
    }

    private static class FlightPriorityComparator implements Comparator<Flight> {
        @Override
        public int compare(Flight f1, Flight f2) {
            if (f1.getDepartureDay() == null || f2.getDepartureDay() == null) return 0;
            if (f1.getDepartureDay().compareTo(f2.getDepartureDay()) != 0) {
                return f1.getDepartureDay().compareTo(f2.getDepartureDay());
            } else if (f1.getDepartureTime().compareTo(f2.getDepartureTime()) != 0) {
                return f1.getDepartureTime().compareTo(f2.getDepartureTime());
            } else {
                return f1.getArrivalTime().compareTo(f2.getArrivalTime());
            }
        }
    }

    public Employee findAvailableCopilot(List<Employee> copilots) {

        Employee availableCopilot = null;
        int minAssignments = Integer.MAX_VALUE;

        for (Employee copilot : copilots) {
            if (copilot.getAssignments() < minAssignments) {
                availableCopilot = copilot;
                minAssignments = copilot.getAssignments();
            }
        }

        return availableCopilot;
    }

    public Flight findFlightById(int flightId) {
        Optional<Flight> optionalFlight = Optional.ofNullable(flightRepository.findFlightById(flightId));
        return optionalFlight.orElse(null);
    }
}
