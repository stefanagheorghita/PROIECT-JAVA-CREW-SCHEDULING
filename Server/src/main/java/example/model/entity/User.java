package example.model.entity;

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
@Table(name = "users")
public class User {
    @GeneratedValue
    @Id
    private Long id;

    @Column(name = "password",
            nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "employee_id",
            nullable = false)
    private Employee employee;



}
