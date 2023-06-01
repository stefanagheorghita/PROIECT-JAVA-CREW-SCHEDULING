package example.solution;

import example.model.entity.Flight;

import java.util.List;
public class Schedule {

    private List<Flight> flights;

    private List<Flight> unallocatedFlights;

    public Schedule(List<Flight> flights) {
        this.flights = flights;
        unallocatedFlights= flights;
    }

    public List<Flight> getUnallocatedFlights() {
        return unallocatedFlights;
    }

    public void setUnallocatedFlights(List<Flight> unallocatedFlights) {
        this.unallocatedFlights = unallocatedFlights;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
