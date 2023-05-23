package com.mapper;

import com.model.dto.AirportDto;
import com.model.dto.CityDto;
import com.model.dto.UserDto;
import com.model.entity.Airport;
import com.model.entity.City;
import com.model.entity.User;

public class UserMapper {
    public static UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .employeeDto(EmployeeMapper.toDto(user.getEmployee()))
                .build();
    }

    public static User fromDto(UserDto userDto) {
        return Airport.builder()
                .id(userDto.getId())
                .employee(EmployeeMapper.fromDto(employeeDto.getEmployeeDto()))
                .build();
    }
}
