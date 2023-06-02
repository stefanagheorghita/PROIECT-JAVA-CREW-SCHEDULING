package example.repository;

import example.model.entity.Airport;
import example.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query(value = "SELECT * FROM cities WHERE country_id = :countryId", nativeQuery = true)
    List<City> findByCountryId(int countryId);

    @Query(value = "SELECT * FROM cities", nativeQuery = true)
    List<City> findAll();

    @Query(value = "SELECT * FROM cities where id=:id", nativeQuery = true)
    City findCityById(int id);

    @Query(value = "SELECT * FROM cities where name=:name", nativeQuery = true)
    List<City> findCityByName(@Param("name")String name);}
