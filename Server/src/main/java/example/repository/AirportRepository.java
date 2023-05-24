package example.repository;
import example.model.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    @Query(value = "SELECT * FROM airports", nativeQuery = true)
    List<Airport> findAll();

    @Query(value = "SELECT * FROM airports where id=:id", nativeQuery = true)
    Airport findAirportById(Long id);

    @Query(value = "SELECT * FROM airports where city_id=:cityId", nativeQuery = true)
    Airport findUserByCityId(Integer cityId);

    @Query(value = "SELECT * FROM airports where name=:name", nativeQuery = true)
    List<Airport> findAirportByName(@Param("name")String name);
}
