package example.mapper;

import example.model.dto.UserDto;
import example.model.entity.User;

public class UserMapper {
    public static UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .employeeDto(EmployeeMapper.toDto(user.getEmployee()))
                .build();
    }

    public static User fromDto(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .employee(EmployeeMapper.fromDto(userDto.getEmployeeDto()))
                .build();
    }
}
