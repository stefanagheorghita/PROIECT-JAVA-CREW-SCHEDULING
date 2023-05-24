package example.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employees")
@NamedStoredProcedureQuery(
        name = "Employee.findPilots",
        procedureName = "get_employees_by_occupation",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "occupation_id", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "result", type = void.class)
        }
)
public class Employee  {
    @GeneratedValue
    @Id
    private int id;
    @Column(name = "first_name",
            nullable = false)
    private String firstName;

    @Column(name = "last_name",
            nullable = false)
    private String lastName;


    @Column(name = "birthdate",
            nullable = false)
    private Timestamp birthDate;


    @Column(name = "gender",
            nullable = false)
    private String gender;

    @ManyToOne
    @JoinColumn(name = "crew_id",
            nullable = false)
    private Crew crew;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;



}
