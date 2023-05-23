package com.model.dto;

import com.model.entity.Crew;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {

    private Long id;

    private String firstName;

    private String lastName;
    private Timestamp birthDate;

    private CrewDto crew;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
