package com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "crew")
public class Crew {

    @GeneratedValue
    @Id
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(name = "max_hours")
    private int maxHours;
}
