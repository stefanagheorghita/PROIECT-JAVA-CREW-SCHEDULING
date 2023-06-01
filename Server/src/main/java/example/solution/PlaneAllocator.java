package example.solution;

import example.model.entity.Airplane;
import example.model.entity.Flight;
import example.model.entity.PlaneLocation;
import example.repository.AirplaneRepository;
import example.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

@Component
public class PlaneAllocator {

    @Autowired
    private AirplaneRepository airplaneRepository;

    @Autowired
    private FlightRepository flightRepository;


    public HashMap<Flight, Airplane> allocatePlanes() {
        List<Flight> flights=flightRepository.findAll();
        List<Airplane> airplanes = airplaneRepository.findAll();
        Flight flight1 = flightRepository.findFlightById(1);
        System.out.println("airplanes: " + airplanes);
        System.out.println("flights: " + flights);
        HashMap<Flight, Airplane> flightAirplaneHashMap = new HashMap<>();
        Collections.sort(flights);
        Collections.sort(airplanes);
        HashSet<PlaneLocation> planeLocations = new HashSet<>();
        for (Flight flight : flights) {

            int departureCityId = flight.getDepartureCity().getId();
            int arrivalCityId = flight.getArrivalCity().getId();
            int flightCapacity = flight.getAproxPassengers();
            LocalTime departureTime = flight.getDepartureTime();
            LocalTime arrivalTime = flight.getArrivalTime();
            DayOfWeek dayOfWeek = flight.getDepartureDay();
            List<Airplane> availablePlane = findAvailablePlane(planeLocations, departureCityId, dayOfWeek, departureTime, arrivalTime, flightCapacity);
            if (availablePlane != null && availablePlane.size() > 0) {
                planeLocations.add(new PlaneLocation(availablePlane.get(0).getId(), arrivalCityId, dayOfWeek, arrivalTime));
                flightAirplaneHashMap.put(flight, availablePlane.get(0));
            } else {
                for (Airplane airplane : airplanes) {
                    if (airplane.getCapacity() >= flightCapacity * 0.8) {
                        if (verifyAvailability(airplane, dayOfWeek, departureTime, arrivalTime, planeLocations) == false)
                            continue;
                        planeLocations.add(new PlaneLocation(airplane.getId(), arrivalCityId, dayOfWeek, arrivalTime));
                        flightAirplaneHashMap.put(flight, airplane);
                        break;
                    }
                }
            }
        }
        System.out.println("flightAirplaneHashMap: " + flightAirplaneHashMap);
        return flightAirplaneHashMap;
    }

    private boolean verifyAvailability(Airplane airplane, DayOfWeek dayOfWeek, LocalTime departureTime, LocalTime arrivalTime, HashSet<PlaneLocation> planeLocations) {
        if(airplane.getFlights()==null)
            return true;
        for (Flight flight : airplane.getFlights())
            if (flight.getDepartureDay() == dayOfWeek && (flight.getArrivalTime().compareTo(departureTime) >= 0))
                return false;
        return true;
    }

    private List<Airplane> findAvailablePlane(HashSet<PlaneLocation> planeLocations, int departureCityId, DayOfWeek dayOfWeek, LocalTime departureTime, LocalTime arrivalTime, int flightCapacity) {
        List<Airplane> availablePlanes = new ArrayList<>();
        for (PlaneLocation planeLocation : planeLocations) {
            if (planeLocation.getCityId() == departureCityId && planeLocation.getDay() == dayOfWeek && planeLocation.getTime().compareTo(departureTime) <= 0) {
                Airplane airplane = airplaneRepository.findById(planeLocation.getPlaneId()).get();
                if (airplane.getCapacity() >= flightCapacity * 0.8) {
                    availablePlanes.add(airplane);
                }
            }
        }
        return null;
    }

}
