package example.service;

import example.model.entity.Employee;
import example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void registerEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee findEmployeeById(Long id) {
      return employeeRepository.findEmployeeById(id);
    }


}
