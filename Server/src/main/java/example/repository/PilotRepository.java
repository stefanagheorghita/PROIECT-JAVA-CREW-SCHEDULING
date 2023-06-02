package example.repository;

import example.model.entity.Flight;
import example.model.entity.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PilotRepository extends JpaRepository<Pilot, Integer> {
    @Query(value = "SELECT p FROM Pilot p WHERE p.assignments = 0 ORDER BY p.id", nativeQuery = true)
    List<Pilot> findAvailablePilots();

    @Query(value = "SELECT p FROM Pilot p WHERE p.employeeId = :employeeId", nativeQuery = true)
    Optional<Pilot> findByEmployeeId(@Param("employeeId") int employeeId);

    @Query(value = "SELECT id FROM Pilot", nativeQuery = true)
    List<Pilot> findAll();
}
