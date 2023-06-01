package example.model.entity;

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
public class Airplane implements Comparable<Airplane>, Node {

    @Id
    @GeneratedValue
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
}
