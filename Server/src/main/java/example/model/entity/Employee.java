package example.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE101")
    @SequenceGenerator(name = "SEQUENCE101", sequenceName = "SEQUENCE101", allocationSize = 1)
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
    @Transient
    private Integer assignments=0;

    @Transient
    private List<Flight> flights=new ArrayList<>();

    public Employee(int id, String firstName, String lastName, Timestamp birthdate, String gender, Crew crew, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthdate;
        this.gender=gender;
        this.crew = crew;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    public Integer getAssignments() {
        return assignments;
    }

    public void setAssignments(Integer assignments) {
        this.assignments = assignments;
    }

    public void incrementAssignments() {
        assignments++;
    }
}
