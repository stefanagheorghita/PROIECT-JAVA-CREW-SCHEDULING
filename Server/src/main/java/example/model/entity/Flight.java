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
@Table(name = "flights")
public class Flight {
    @GeneratedValue
    @Id
    private Long id;

    @Column(name = "destination")
    private String destination;

    @ManyToOne
    @JoinColumn(name = "departure_city_id",
            nullable = false)
    private City departureCity;

    @ManyToOne
    @JoinColumn(name = "arrival_city_id",
            nullable = false)
    private City arrivalCity;

    @Column(name = "arrival_date")
    private Timestamp arrivalDate;

    @Column(name = "airplane_id",
            nullable = false)
    private int airplaneId;

    @Column(name = "employees_no",
            nullable = false)
    private Integer employeesNo;



}
