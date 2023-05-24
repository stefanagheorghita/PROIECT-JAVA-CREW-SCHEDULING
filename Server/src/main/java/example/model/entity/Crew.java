package example.model.entity;

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
@Table(name = "crew")
public class Crew {

    @GeneratedValue
    @Id
    private Long id;

    @Column(name = "name",
            unique = true)
    private String name;

    @Column(name = "max_hours",
            nullable = false)
    private Integer maxHours;



}
