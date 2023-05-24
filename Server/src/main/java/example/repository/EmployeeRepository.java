package example.repository;

import example.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    @Query(value = "SELECT * FROM employees where id=:id", nativeQuery = true)
    Employee findEmployeeById(@Param("id") int id);

    @Query(value = "SELECT * FROM employees where first_name=:name", nativeQuery = true)
    List<Employee> findEmployeeByName(@Param("name")String name);

    @Query(value = "SELECT * FROM employees where last_name=:name", nativeQuery = true)
    List<Employee> findEmployeeByLastName(@Param("name")String name);

    @Query(value = "SELECT * FROM employees where first_name=:name and last_name=:last_name", nativeQuery = true)
    List<Employee> findEmployeeByFullName(@Param("name")String name,@Param("last_name")String last_name);



    @Query(value = "DECLARE " +
            "  v_employees SYS_REFCURSOR; " +
            "BEGIN " +
            "  v_employees \\:= get_employees_by_occupation(:occupationId); " +
            "  OPEN :result FOR v_employees; " +
            "END;", nativeQuery = true)
    List<Employee> findPilots(@Param("occupationId") int occupationId, @Param("result") Object result);

    @Query(value="select * from employees where crew_id in (select id from crew where name = :name)", nativeQuery = true)
    List<Employee> findEmployeesByCrewName(@Param("name") String name);
}
