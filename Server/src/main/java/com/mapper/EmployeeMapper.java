package com.mapper;

import com.model.dto.EmployeeDto;
import com.model.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto toDto(Employee employee) {
       return EmployeeDto.builder()
               .id(employee.getId())
               .firstName(employee.getFirstName())
               .lastName(employee.getLastName())
               .birthDate(employee.getBirthDate())
               .crew(CrewMapper.toDto(employee.getCrew()))
               .build();
    }

    public static Employee fromDto(EmployeeDto employeeDto) {
        return Employee.builder()
                .id(employeeDto.getId())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .birthDate(employeeDto.getBirthDate())
                .crew(CrewMapper.fromDto(employeeDto.getCrew()))
                .build();
    }
}
