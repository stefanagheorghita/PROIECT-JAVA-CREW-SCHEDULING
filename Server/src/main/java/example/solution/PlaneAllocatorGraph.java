package example.solution;

import example.model.entity.*;
import example.repository.AirplaneRepository;
import example.repository.FlightRepository;
import org.jgrapht.Graph;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.time.Duration;
import java.time.LocalTime;

@Component
public class PlaneAllocatorGraph {

    @Autowired
    private AirplaneRepository airplaneRepository;

    @Autowired
    private FlightRepository flightRepository;

    private static final double TIME_DIFFERENCE_WEIGHT = 0.5;
    private static final double DESTINATION_PRIORITY_WEIGHT = 0.5;

    private static final int MAX_TIME_DIFFERENCE_MINUTES = 60;

    private Map<Flight, HashMap<Airplane, Double>> flightAirplaneWeightMap = new HashMap<>();

    private Set<PlaneLocation> planeLocations = new HashSet<>();

    Graph<Node, DefaultWeightedEdge> graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);

    public HashMap<Flight, Airplane> allocatePlanes() {
        allocateWeights();
        max();
        return null;
    }

    public void allocateWeights() {
        for (Flight flight : flightRepository.findAll()) {
            for (Airplane airplane : airplaneRepository.findAll()) {

                double weight = calculateWeight(flight, airplane);
                if (flightAirplaneWeightMap.containsKey(flight)) {
                    flightAirplaneWeightMap.get(flight).put(airplane, weight);
                } else {
                    HashMap<Airplane, Double> airplaneDoubleHashMap = new HashMap<>();
                    airplaneDoubleHashMap.put(airplane, weight);
                    flightAirplaneWeightMap.put(flight, airplaneDoubleHashMap);
                }
            }
        }
    }

    public void max() {
        for (Flight flight : flightAirplaneWeightMap.keySet()) {
            HashMap<Airplane, Double> airplaneDoubleHashMap = flightAirplaneWeightMap.get(flight);
            Airplane airplane = null;
            double maxWeight = 0.0;
            for (Airplane airplane1 : airplaneDoubleHashMap.keySet()) {
                if (airplaneDoubleHashMap.get(airplane1) > maxWeight) {
                    maxWeight = airplaneDoubleHashMap.get(airplane1);
                    airplane = airplane1;
                }
            }
        }
    }

    public void assignments() {
        List<Airplane> airplanes = airplaneRepository.findAll();
        List<Flight> flights = flightRepository.findAll();
        allocatePlanes();
        for (Airplane airplane : airplanes) {
            graph.addVertex(airplane);
        }
        for (Flight flight : flights) {
            graph.addVertex(flight);
        }
        for (Flight flight : flights) {
            for (Airplane airplane : airplanes) {
                HashMap<Airplane, Double> edgeWeights = flightAirplaneWeightMap.get(flight);
                double weight = edgeWeights.getOrDefault(airplane, 0.0);
                DefaultWeightedEdge e = graph.addEdge(flight, airplane);
                graph.setEdgeWeight(e, weight);
            }
        }


        Node source = new NodeImpl(true);
        Node sink = new NodeImpl(false);

        graph.addVertex(source);
        graph.addVertex(sink);
        for (Flight flight : flights) {
            DefaultWeightedEdge e = graph.addEdge(source, flight);
            graph.setEdgeWeight(e, 0.0);
        }

        for (Airplane airplane : airplanes) {
            DefaultWeightedEdge e = graph.addEdge(airplane, sink);
            graph.setEdgeWeight(e, 0.0);
        }
        EdmondsKarpMFImpl<Node, DefaultWeightedEdge> solver = new EdmondsKarpMFImpl<>(graph);
        double maximumFlowValue = solver.calculateMaximumFlow(source, sink);

        Map<Flight, Airplane> assignments = new HashMap<>();

        for (Flight flight : flights) {
            Airplane assignedAirplane = null;
            double maxWeight = 0.0;

            for (Airplane airplane : airplanes) {
                planeLocations.add(new PlaneLocation(airplane.getId(), flight.getArrivalCity().getId(), flight.getDepartureDay(), flight.getDepartureTime()));

                double weight = calculateWeight(flight, airplane);

                if (weight > maxWeight) {
                    assignedAirplane = airplane;
                    maxWeight = weight;
                }
            }

            if (assignedAirplane != null) {
                assignments.put(flight, assignedAirplane);
            }
        }

        TreeMap<Flight, Airplane> sortedMap = new TreeMap<>(new Comparator<Flight>() {
            @Override
            public int compare(Flight flight1, Flight flight2) {
                Airplane airplane1 = assignments.get(flight1);
                Airplane airplane2 = assignments.get(flight2);

                return Integer.compare(airplane1.getId(), airplane2.getId());
            }
        });
        sortedMap.putAll(assignments);
        for (Map.Entry<Flight, Airplane> entry : sortedMap.entrySet()) {
            Flight flight = entry.getKey();
            Airplane airplane = entry.getValue();

            System.out.println("Flight: " + flight.getId() + ", Airplane: " + airplane.getId());
            System.out.println("Departure: " + flight.getDepartureCity().getName() + " " + flight.getDepartureTime());
            System.out.println("Arrival: " + flight.getArrivalCity().getName() + " "+flight.getArrivalTime());
            System.out.println("Weight: " + calculateWeight(flight, airplane));
            System.out.println("Capacity: " + airplane.getCapacity());
            System.out.println("Passengers aprox: " + flight.getAproxPassengers());
            System.out.println();
        }
        System.out.println();
        System.out.println("Total flights: "+flights.size());
        System.out.println("Flights allocated: "+sortedMap.size());

    }

    private double calculateWeight(Flight flight, Airplane airplane) {

        PlaneLocation planeLocation = findPlaneLocation(airplane.getId());
        if (planeLocation == null) {
            return 2.0;
        }
        if (airplane.getCapacity() < flight.getAproxPassengers() * 0.6) {
            return 0.0;
        }
        Duration timeDifference = calculateTimeDifference(flight.getDepartureTime(), planeLocation.getTime());

        double timeDifferenceWeight = (timeDifference.toMinutes() <= MAX_TIME_DIFFERENCE_MINUTES) ? 1.0 : 0.0;

        double destinationPriorityWeight = (planeLocation.getCityId() == (flight.getArrivalCity().getId())) ? 2.0 : 1.0;

        return timeDifferenceWeight * TIME_DIFFERENCE_WEIGHT + destinationPriorityWeight * DESTINATION_PRIORITY_WEIGHT;
    }

    private PlaneLocation findPlaneLocation(int id) {
        for (PlaneLocation location : planeLocations) {
            if (location.getPlaneId()==(id)) {
                return location;

            }
        }
        return null;
    }

    private Duration calculateTimeDifference(LocalTime time1, LocalTime time2) {
        return Duration.between(time1, time2);
    }
}
