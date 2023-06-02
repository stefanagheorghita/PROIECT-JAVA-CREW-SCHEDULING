package example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.DayOfWeek;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "flights")
public class Flight implements Comparable<Flight>,Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "departure_city_id",
            nullable = false)
    private City departureCity;

    @ManyToOne
    @JoinColumn(name = "arrival_city_id",
            nullable = false)
    private City arrivalCity;

    @Column(name = "departure_day")
    private String departureDayStr;

    @Transient
    private DayOfWeek departureDay;

    @Column(name = "departure_hour")
    private String departureHour;

    @Column(name = "arrival_hour")
    private String arrivalHour;

    @Transient
    private LocalTime departureTime;

    @Transient
    private LocalTime arrivalTime;



    @PostLoad
    private void convertTimes() {
        System.out.println(departureHour);
        departureTime = LocalTime.parse(departureHour);
        System.out.println(departureTime);
        arrivalTime = LocalTime.parse(arrivalHour);
        switch(departureDayStr){
            case "Monday":
                departureDay = DayOfWeek.MONDAY;
                break;
            case "Tuesday":
                departureDay = DayOfWeek.TUESDAY;
                break;
        }
    }

    @Column(name = "airplane_id")
    private Integer airplaneId;

    @Column(name = "employees_no")
    private Integer employeesNo;

    @Column(name="aprox_passengers")
    private Integer aproxPassengers;

    @Column(name = "pilot_id")
    private Integer pilotId;

    @Transient
    private Integer copilotId;


    @Override
    public int compareTo(Flight o) {
        if(this.getDepartureDay().compareTo(o.getDepartureDay())!=0)
            return this.getDepartureDay().compareTo(o.getDepartureDay());
        else
        if(this.getDepartureTime().compareTo(o.getDepartureTime())!=0)
            return this.getDepartureTime().compareTo(o.getDepartureTime());
        else
            return this.getArrivalTime().compareTo(o.getArrivalTime());
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", departureCity=" + departureCity +
                ", arrivalCity=" + arrivalCity +
                ", departureDay=" + departureDay +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", airplaneId=" + airplaneId +
                ", employeesNo=" + employeesNo +
                ", aproxPassengers=" + aproxPassengers +
                '}';
    }
}
