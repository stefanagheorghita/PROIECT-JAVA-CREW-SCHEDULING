package example.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@Builder
@Entity
@Table(name = "Copilot")
public class Copilot  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "employeeId")
    private Integer employeeId;

    @Column(name = "assignments")
    private Integer assignments;

    public Copilot() {
    }

    public Copilot(Integer employeeId) {
        this.employeeId = employeeId;
        this.assignments = 0;
    }

    public int getId() {
        return id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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

