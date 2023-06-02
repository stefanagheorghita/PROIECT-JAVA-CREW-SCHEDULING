package example.model.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "airplanes")
public class Airplane implements Comparable<Airplane>, Node, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE6")
    @SequenceGenerator(name = "SEQUENCE6", sequenceName = "SEQUENCE6", allocationSize = 1)
    private int id;


    @Column(name = "num_passengers")
    private Integer capacity;

    @OneToMany
    @JsonIgnore
    @Transient
    private List<Flight> flights;

    @Override
    public int compareTo(Airplane o) {
        return this.getCapacity().compareTo(o.getCapacity());
    }

    public Airplane(int id, Integer capacity) {
        this.id = id;
        this.capacity = capacity;
    }
}
