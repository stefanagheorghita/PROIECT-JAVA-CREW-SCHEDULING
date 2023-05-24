package example.repository;

import example.model.entity.Employee;
import example.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> findAll();

    @Query(value = "SELECT * FROM users where id=:id", nativeQuery = true)
    User findUserById(Long id);

    @Query(value = "SELECT * FROM users where employee_id=:employeeId", nativeQuery = true)
    User findUserByEmployeeId(Integer employeeId);
}
