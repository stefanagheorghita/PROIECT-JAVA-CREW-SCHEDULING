package example.solution;

import example.model.entity.City;

public class Edge {
    private City destination;
    private int flightId;

    public Edge(City destination, int flightId) {
        this.destination = destination;
        this.flightId = flightId;
    }

    public City getDestination() {
        return destination;
    }

    public int getFlightId() {
        return flightId;
    }


}
