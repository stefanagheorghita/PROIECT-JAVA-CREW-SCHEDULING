package example.data;

import example.model.entity.Flight;
import example.repository.CityRepository;
import example.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Random;


@Component
public class FlightGeneratorLargeInstance {
    @Autowired
    CityRepository cityRepository;

    @Autowired
    FlightRepository flightRepository;


    public void main(String[] args) {
        generate();
    }


    public void generate() {
        LocalTime time = LocalTime.of(0, 0);
        Random rand = new Random();
        for (int i = 0; i < 700; i++) {
            Flight flight = new Flight(i + 1);
            int depart = rand.nextInt(1, cityRepository.findAll().size() + 1);
            int arrive = rand.nextInt(1, 1 + cityRepository.findAll().size());

            while (depart == arrive)
                arrive = rand.nextInt(cityRepository.findAll().size());
            flight.setDepartureCity(cityRepository.findCityById(depart));
            flight.setArrivalCity(cityRepository.findCityById(arrive));
            flight.setDepartureTime(LocalTime.of(rand.nextInt(24), rand.nextInt(60)));
            flight.setArrivalTime(LocalTime.of(rand.nextInt(24), rand.nextInt(60)));
            if (flight.getDepartureTime().isAfter(flight.getArrivalTime())) {
                time = flight.getDepartureTime();
                flight.setDepartureTime(flight.getArrivalTime());
                flight.setArrivalTime(time);
            }
            flight.setDepartureHour(flight.getDepartureTime().toString());
            flight.setArrivalHour(flight.getArrivalTime().toString());
            int day = rand.nextInt(7);
            flight.setDepartureDay(DayOfWeek.of(day + 1));
            flight.setDepartureDayStr(flight.getDepartureDay().toString());
            flight.setAproxPassengers(rand.nextInt(20, 500));
            flightRepository.save(flight);
        }
    }
}
