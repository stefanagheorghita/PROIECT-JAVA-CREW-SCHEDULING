package example.repository;

import example.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users where employee_id = :employee_id", nativeQuery = true)
    User findByEmployeeId(@Param("employee_id") Long employee_id);

}
