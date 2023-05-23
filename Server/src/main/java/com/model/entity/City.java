package com.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cities")
public class City {
    @GeneratedValue
    @Id
    private Long id;

    @Column(name = "name",
            nullable = false)
    private String name;

    @ManyToOne
    @Column (name = "country_id",
            nullable = false)
    private Country country;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;



}