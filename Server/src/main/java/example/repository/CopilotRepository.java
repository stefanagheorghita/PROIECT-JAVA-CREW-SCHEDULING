package example.repository;

import example.model.entity.Copilot;
import example.model.entity.Flight;
import example.model.entity.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CopilotRepository extends JpaRepository<Copilot, Integer> {
    @Query(value = "SELECT p FROM Copilot p WHERE p.assignments = 0 ORDER BY p.id", nativeQuery = true)
    List<Pilot> findAvailablePilots();

    @Query(value = "SELECT p FROM Copilot p WHERE p.employeeId = :employeeId", nativeQuery = true)
    Optional<Pilot> findByEmployeeId(@Param("employeeId") int employeeId);

    @Query(value = "SELECT id FROM Copilot", nativeQuery = true)
    List<Copilot> findAll();
}