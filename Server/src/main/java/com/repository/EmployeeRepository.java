package com.repository;

import com.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query(value = "SELECT * FROM employees where id=:id", nativeQuery = true)
    Employee findEmployeeById(Long id);

    @Query(value = "SELECT * FROM employees where first_name=:name", nativeQuery = true)
    List<Employee> findEmployeeByName(@Param("name")String name);

    @Query(value = "SELECT * FROM employees where last_name=:name", nativeQuery = true)
    List<Employee> findEmployeeByLastName(@Param("name")String name);

    @Query(value = "SELECT * FROM employees where first_name=:name and last_name=:last_name", nativeQuery = true)
    List<Employee> findEmployeeByFullName(@Param("name")String name,@Param("last_name")String last_name);



}
