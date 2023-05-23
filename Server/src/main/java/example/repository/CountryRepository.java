package example.repository;

import example.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {

    @Query(value = "SELECT * FROM countries", nativeQuery = true)
    List<Country> findAllCountries();

    @Query(value="SELECT * FROM countries where id=:id", nativeQuery = true)
    Country findCountryById(@Param("id") Long id);

    @Query(value="SELECT * FROM countries where name=:name", nativeQuery = true)
    Country findCountryByName(@Param("name") String name);
}
