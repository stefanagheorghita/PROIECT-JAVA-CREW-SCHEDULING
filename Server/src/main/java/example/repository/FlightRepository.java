package example.repository;

import example.model.entity.Airport;
import example.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query(value = "SELECT * FROM flights", nativeQuery = true)
    List<Flight> findAll();

    @Query(value = "SELECT * FROM flights where id=:id", nativeQuery = true)
    Flight findFlightById(Long id);

    @Query(value = "SELECT * FROM flights where destination=:destination", nativeQuery = true)
    List<Flight> findFlightByDestination(@Param("destination")String destination);

    @Query(value = "SELECT * FROM airports where departure_city_id=:departureId", nativeQuery = true)
    Flight findFlightByDepartureId(Integer departureId);

    @Query(value = "SELECT * FROM airports where arrival_city_id=:arrivalId", nativeQuery = true)
    Flight findFlightByArrivalId(Integer arrivalId);

    @Query(value = "SELECT * FROM airports where airplane_id=:airplaneId", nativeQuery = true)
    Flight findFlightByAirplaneId(Integer airplaneId);

}
