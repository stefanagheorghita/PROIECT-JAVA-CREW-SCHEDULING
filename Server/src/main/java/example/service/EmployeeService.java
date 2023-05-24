package example.service;

import example.mapper.EmployeeMapper;
import example.mapper.UserMapper;
import example.model.dto.EmployeeDto;
import example.model.entity.Employee;
import example.repository.EmployeeRepository;
import example.repository.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeRepositoryImpl employeeRepositoryImpl;

    public void registerEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee findEmployeeById(int id) {
      return employeeRepository.findById(id).get();
    }


    public List<EmployeeDto> findAll() {
      return  employeeRepository.findAll().stream().map(EmployeeMapper::toDto).toList();
    }

    public List<EmployeeDto> findPilots() {
      //  List<Employee> pilots=employeeRepositoryImpl.getPilots(1);
        //System.out.println(pilots);
     //   List<Employee> pilots=new ArrayList<>();
      //  return employeeRepository.findPilots(1,pilots).stream().map(EmployeeMapper::toDto).toList();
        return employeeRepository.findEmployeesByCrewName("Pilot").stream().map(EmployeeMapper::toDto).toList();
    }


    public List<EmployeeDto> findEmployeesByCrewName(String crewName) {
        return employeeRepository.findEmployeesByCrewName(crewName).stream().map(EmployeeMapper::toDto).toList();
    }
}
