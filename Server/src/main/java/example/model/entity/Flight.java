package example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class Flight implements Comparable<Flight>, Node , Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE5")
    @SequenceGenerator(name = "SEQUENCE5", sequenceName = "SEQUENCE5", allocationSize = 1)
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
        if (departureHour != null)
            departureTime = LocalTime.parse(departureHour);
        if (arrivalHour != null)
            arrivalTime = LocalTime.parse(arrivalHour);
        if (departureDayStr != null)
            switch (departureDayStr) {
                case "Monday":
                    departureDay = DayOfWeek.MONDAY;
                    break;
                case "Tuesday":
                    departureDay = DayOfWeek.TUESDAY;
                    break;
                case "Wednesday":
                    departureDay = DayOfWeek.WEDNESDAY;
                    break;
                case "Thursday":
                    departureDay = DayOfWeek.THURSDAY;
                    break;
                case "Friday":
                    departureDay = DayOfWeek.FRIDAY;
                    break;
                case "Saturday":
                    departureDay = DayOfWeek.SATURDAY;
                    break;
                case "Sunday":
                    departureDay = DayOfWeek.SUNDAY;
                    break;
            }
    }

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @Column(name = "employees_no")
    private Integer employeesNo;

    @Column(name = "aprox_passengers")
    private Integer aproxPassengers;

   @Column(name = "pilot_id")
    private Integer pilotId;

    @Transient
    private Integer copilotId;


    @Override
    public int compareTo(Flight o) {
        if(this.getDepartureDay()!=null && o.getDepartureDay()!=null)
        if (this.getDepartureDay().compareTo(o.getDepartureDay()) != 0)
            return this.getDepartureDay().compareTo(o.getDepartureDay());
        else if (this.getDepartureTime().compareTo(o.getDepartureTime()) != 0)
            return this.getDepartureTime().compareTo(o.getDepartureTime());
        else
            return this.getArrivalTime().compareTo(o.getArrivalTime());

        if(this.getDepartureDayStr()!=null && o.getDepartureDayStr()!=null)
            if (this.getDepartureDayStr().compareTo(o.getDepartureDayStr()) != 0)
            return this.getDepartureDayStr().compareTo(o.getDepartureDayStr());
        else if (this.getDepartureHour().compareTo(o.getDepartureHour()) != 0)
            return this.getDepartureHour().compareTo(o.getDepartureHour());
        else
            return this.getArrivalHour().compareTo(o.getArrivalHour());

        return 0;
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
                ", airplane=" + airplane +
                ", employeesNo=" + employeesNo +
                ", aproxPassengers=" + aproxPassengers +
                '}';
    }

    public Flight(int id, City departureCity, City arrivalCity, DayOfWeek departureDay, LocalTime departureTime, LocalTime arrivalTime, Integer aproxPassengers) {
        this.id = id;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDay = departureDay;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.aproxPassengers = aproxPassengers;
    }

    public Flight(int id) {
        this.id = id;
    }
}
