package example.mapper;

import example.model.dto.EmployeeDto;
import example.model.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto toDto(Employee employee) {
       return EmployeeDto.builder()
               .id(employee.getId())
               .firstName(employee.getFirstName())
               .lastName(employee.getLastName())
               .birthDate(employee.getBirthDate())
               .gender(employee.getGender())
               .crew(CrewMapper.toDto(employee.getCrew()))
               .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
               .build();
    }

    public static Employee fromDto(EmployeeDto employeeDto) {
        return Employee.builder()
                .id(employeeDto.getId())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .birthDate(employeeDto.getBirthDate())
                .gender(employeeDto.getGender())
                .crew(CrewMapper.fromDto(employeeDto.getCrew()))
                .createdAt(employeeDto.getCreatedAt())
                .updatedAt(employeeDto.getUpdatedAt())
                .build();
    }
}
