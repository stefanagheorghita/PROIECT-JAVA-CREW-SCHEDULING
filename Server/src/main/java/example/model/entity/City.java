package example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cities")
public class City implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE2")
    @SequenceGenerator(name="SEQUENCE2", sequenceName="SEQUENCE2", allocationSize=1)
    private int id;

    @Column(name = "name",
            nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn (name = "country_id",
            nullable = false)
    private Country country;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public City(int id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
}
