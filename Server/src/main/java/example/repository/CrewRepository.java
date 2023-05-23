package example.repository;

import example.model.entity.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrewRepository extends JpaRepository<Crew,Long> {

    @Query(value = "SELECT * FROM crew where id=:id", nativeQuery = true)
    Crew findCrewById(@Param("id")Long id);

    @Query(value = "SELECT * FROM crew where name=:name", nativeQuery = true)
    Crew findCrewByName(@Param("name")String name);

    @Query(value = "SELECT * FROM crew", nativeQuery = true)
    List<Crew> findAllCrews();



}
