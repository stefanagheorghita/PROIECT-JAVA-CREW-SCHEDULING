package example.solution;

import example.model.entity.Airplane;
import example.model.entity.Flight;
import example.model.entity.PlaneLocation;
import example.repository.AirplaneRepository;
import example.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
                        flight.setAirplane(airplane);
                        break;
                    }
                }
            }
        }
        System.out.println("flightAirplaneHashMap: ");
        for (Map.Entry<Flight, Airplane> entry : flightAirplaneHashMap.entrySet()) {
            Flight flight = entry.getKey();
            Airplane airplane = entry.getValue();

            System.out.println("Flight: " + flight.getId() + ", Airplane: " + airplane.getId());
            System.out.println("Departure: " + flight.getDepartureCity().getName() + " " + flight.getDepartureTime());
            System.out.println("Arrival: " + flight.getArrivalCity().getName() + " "+flight.getArrivalTime());
            System.out.println("Capacity: " + airplane.getCapacity());
            System.out.println("Passengers aprox: " + flight.getAproxPassengers());
            System.out.println();
        }
        System.out.println();
        System.out.println("Total flights: "+flights.size());
        System.out.println("Flights allocated: "+flightAirplaneHashMap.size());
        try {
            FileOutputStream fileOut = new FileOutputStream("flightAirplaneData.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(flightAirplaneHashMap);
            objectOut.close();
            fileOut.close();
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flightAirplaneHashMap;
    }

    public boolean verifyAvailability(Airplane airplane, DayOfWeek dayOfWeek, LocalTime departureTime, LocalTime arrivalTime, HashSet<PlaneLocation> planeLocations) {
        if(airplane.getFlights()==null)
            return true;
        for (Flight flight : airplane.getFlights())
            if (flight.getDepartureDay() == dayOfWeek && (flight.getArrivalTime().compareTo(departureTime) >= 0))
                return false;
        return true;
    }

    public List<Airplane> findAvailablePlane(HashSet<PlaneLocation> planeLocations, int departureCityId, DayOfWeek dayOfWeek, LocalTime departureTime, LocalTime arrivalTime, int flightCapacity) {
        List<Airplane> availablePlanes = new ArrayList<>();
        for (PlaneLocation planeLocation : planeLocations) {
            if (planeLocation.getCityId() == departureCityId && planeLocation.getDay() == dayOfWeek && planeLocation.getTime().compareTo(departureTime) <= 0) {
                Airplane airplane = airplaneRepository.findById(planeLocation.getPlaneId()).get();
                if (airplane.getCapacity() >= flightCapacity * 0.8) {
                    availablePlanes.add(airplane);
                }
            }
        }
        return availablePlanes;
    }

    public void setAirplaneRepository(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    public void setFlightRepository(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

}
