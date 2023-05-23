package example.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

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
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthdate")
    private Timestamp birthDate;

    @Column(name = "gender")
    private String gender;

    @ManyToOne
    @JoinColumn(name = "crew_id")
    private Crew crew;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;



}
