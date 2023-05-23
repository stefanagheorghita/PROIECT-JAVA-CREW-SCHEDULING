package com.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class Employee extends Person {
    @GeneratedValue
    @Id
    private Long id;
    @Column(name = "first_name",
            nullable = false)
    private String firstName;

    @Column(name = "last_name",
            nullable = false)
    private String lastName;

    @Column(name = "birthdate",
            nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender",
            nullable = false)
    private String gender;

    @ManyToOne
    @JoinColumn(name = "crew_id",
            nullable = false)
    private Crew crew;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;



}
