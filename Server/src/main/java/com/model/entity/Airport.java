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
@Table(name = "airports")
public class Airport {
    @GeneratedValue
    @Id
    private Long id;

    @Column (name = "name",
            nullable = false)
    private String name;

    @ManyToOne
    @Column (name = "city_id",
            nullable = false)
    private City city;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;



}
